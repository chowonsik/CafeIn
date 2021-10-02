package com.cafein.serviceImpl;

import com.cafein.configuration.AES128;
import com.cafein.configuration.ValidationCheck;
import com.cafein.dto.user.email.EmailInput;
import com.cafein.dto.user.email.EmailOutput;
import com.cafein.dto.user.selectprofile.SelectProfileOutput;
import com.cafein.dto.user.signin.SignInInput;
import com.cafein.dto.user.signin.SocialLoginType;
import com.cafein.dto.user.signup.SignUpInput;
import com.cafein.dto.user.updateprofile.UpdateProfileInput;
import com.cafein.entity.User;
import com.cafein.response.Response;
import com.cafein.service.JwtService;
import com.cafein.service.UserService;
import com.cafein.dao.UserRepository;
import com.cafein.dto.user.signin.SignInOutput;
import com.cafein.dto.user.signup.SignUpOutput;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.RandomStringUtils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import static com.cafein.response.ResponseStatus.*;

@Service("UserService")
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    private final JavaMailSender mailSender;

    @Value("${custom.constant.user.info.password.key}")
    private String USER_INFO_PASSWORD_KEY;

    @Override
    public ResponseEntity<Response<SignInOutput>> signIn(SignInInput signInInput) {
        // 1. 값 형식 체크
        if (signInInput == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Response<>(NO_VALUES));
        if (!ValidationCheck.isValid(signInInput.getEmail()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Response<>(BAD_EMAIL_VALUE));
        if (!ValidationCheck.isValid(signInInput.getPassword()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Response<>(BAD_PASSWORD_VALUE));

        // 2. user 정보 가져오기
        User userDB;
        try {
            String email = signInInput.getEmail();
            String password = new AES128(USER_INFO_PASSWORD_KEY).encrypt(signInInput.getPassword());
            List<User> userDBs = userRepository.findByEmailAndStatus(email, "ACTIVATE");
            if (userDBs.size() == 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new Response<>(NOT_FOUND_USER));
            } else if (!userDBs.get(0).getPassword().equals(password)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new Response<>(FAILED_TO_SIGN_IN));
            } else {
                userDB = userDBs.get(0);
            }
        } catch (Exception e) {
            log.error("[users/signin/post] database error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Response<>(DATABASE_ERROR));
        }

        // 3. access token 생성
        String accessToken;
        try {
            accessToken = jwtService.createAccessToken(userDB.getId());
            if (accessToken.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new Response<>(FAILED_TO_CREATE_TOKEN));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Response<>(FAILED_TO_CREATE_TOKEN));
        }

        // 4. 결과 return
        SignInOutput signInOutput = SignInOutput.builder().userId(userDB.getId()).accessToken(accessToken).build();
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(signInOutput, SUCCESS_SIGN_IN));
    }

    @Override
    @Transactional
    public ResponseEntity<Response<SignUpOutput>> signUp(SignUpInput signUpInput) {
        // 1. 값 형식 체크
        if (signUpInput == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Response<>(NO_VALUES));
        if (!ValidationCheck.isValid(signUpInput.getEmail()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Response<>(BAD_EMAIL_VALUE));
        if (!ValidationCheck.isValid(signUpInput.getPassword()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Response<>(BAD_PASSWORD_VALUE));
        if (!ValidationCheck.isValid(signUpInput.getNickname()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Response<>(BAD_NAME_VALUE));

        // 2. 유저 생성
        User user;
        try {
            String email = signUpInput.getEmail();
            String nickname = signUpInput.getNickname();
            boolean existUsers = userRepository.existsByEmailAndStatus(email, "ACTIVATE");
            boolean existNickname = userRepository.existsByNicknameAndStatus(nickname, "ACTIVATE");
            String password = new AES128(USER_INFO_PASSWORD_KEY).encrypt(signUpInput.getPassword());
            user = User.builder().email(signUpInput.getEmail()).password(password)
                    .nickname(signUpInput.getNickname()).status("ACTIVATE").build();

            if (existUsers) { // 이메일 중복 제어
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new Response<>(EXISTS_EMAIL));
            } else if (existNickname) { // 닉네임 중복 제어
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new Response<>(EXISTS_NICKNAME));
            } else {
                user = userRepository.save(user);
            }

        } catch (Exception e) {
            log.error("[users/signup/post] database error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Response<>(DATABASE_ERROR));
        }

        // 3. 토큰 생성
        String accessToken;
        try {
            accessToken = jwtService.createAccessToken(user.getId());
            if (accessToken.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new Response<>(FAILED_TO_CREATE_TOKEN));
            }
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Response<>(FAILED_TO_CREATE_TOKEN));
        }

        // 4. 결과 return
        SignUpOutput signUpOutput = new SignUpOutput(user.getId(), accessToken);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new Response<>(signUpOutput, CREATED_USER));
    }

    @Override
    public ResponseEntity<Response<Object>> changeDeleteStatus() {
        // 1. 로그인한 유저 정보 가져오기
        try {
            User loginUserDB = jwtService.getUser();
            if(loginUserDB == null) {
                log.error("[users/patch] NOT FOUND LOGIN USER error");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new Response<>(NOT_FOUND_USER));
            }
            // 2. 로그인한 유저 상태 DELETED 로 변경
            loginUserDB.setStatus("DELETED");
            userRepository.save(loginUserDB);
        } catch (Exception e) {
            log.error("[users/patch] database error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Response<>(DATABASE_ERROR));
        }
        // 3. 결과 return
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(null, SUCCESS_DELETE_USER));
    }

    @Override
    public ResponseEntity<Response<EmailOutput>> sendMail(EmailInput emailInput) {
        // 1. 값 형식 체크
        if (emailInput == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Response<>(NO_VALUES));
        if (!ValidationCheck.isValid(emailInput.getEmail()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Response<>(BAD_REQUEST));
        // 2. 중복 메일인지 체크
        try {
            if (userRepository.existsByEmailAndStatus(emailInput.getEmail(), "ACTIVATE")) {
                log.error("[users/email/post] DUPLICATE EMAIL error");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new Response<>(EXISTS_EMAIL));
            }
        } catch (Exception e) {
            log.error("[users/email/post] database error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Response<>(DATABASE_ERROR));
        }
        // 3. 인증 메일 전송
        EmailOutput emailOutput;
        try {
            String generatedString = RandomStringUtils.random(10, true, true);
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(emailInput.getEmail());
            message.setSubject("[cafein] 회원가입 이메일 인증번호입니다.");
            message.setText(generatedString);
            emailOutput = EmailOutput.builder().auth(generatedString).build();
            mailSender.send(message);
        } catch (Exception e) {
            log.error("[users/email/post] send email error", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Response<>(FAILED_TO_SEND_EMAIL));
        }
        // 4. 결과 return
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(emailOutput, SUCCESS_SEND_MAIL));
    }

    @Override
    public ResponseEntity<Response<SelectProfileOutput>> selectProfile() {
        SelectProfileOutput selectProfileOutput;
        try {
            // 유저 id 가져오기
            int userId = jwtService.getUserId();
            selectProfileOutput = userRepository.findUserProfile(userId);
        } catch (Exception e) {
            log.error("[users/me/get] database error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Response<>(DATABASE_ERROR));
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(selectProfileOutput, SUCCESS_SELECT_PROFILE));
    }

    @Override
    public ResponseEntity<Response<Object>> updateProfile(UpdateProfileInput updateProfileInput) {
        try {
            // 유저 id 가져오기
            int userId = jwtService.getUserId();
            User selectUser = userRepository.findByIdAndStatus(userId, "ACTIVATE");

            // 입력 값 벨리데이션
            if (StringUtils.isNotBlank(updateProfileInput.getPassword()))
                selectUser.setPassword(new AES128(USER_INFO_PASSWORD_KEY).encrypt(updateProfileInput.getPassword()));
            if (StringUtils.isNotBlank(updateProfileInput.getNickname())) {
                boolean existNickname = userRepository.existsByNicknameAndStatus(updateProfileInput.getNickname(), "ACTIVATE");
                if (existNickname) { // 닉네임 중복 제어
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new Response<>(EXISTS_NICKNAME));
                }
                selectUser.setNickname(updateProfileInput.getNickname());
            }

            userRepository.save(selectUser);
        } catch (Exception e) {
            log.error("[users/patch] database error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Response<>(DATABASE_ERROR));
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(null, SUCCESS_UPDATE_PROFILE));
    }
}
