package com.cafein.controller;

import com.cafein.dto.user.email.EmailInput;
import com.cafein.dto.user.email.EmailOutput;
import com.cafein.dto.user.jwt.JwtOutput;
import com.cafein.dto.user.signin.SignInInput;
import com.cafein.dto.user.signin.SignInOutput;
import com.cafein.dto.user.signup.SignUpOutput;
import com.cafein.dto.user.signup.SignUpInput;
import com.cafein.response.PageResponse;
import com.cafein.response.Response;
import com.cafein.service.JwtService;
import com.cafein.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static com.cafein.response.ResponseStatus.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    /**
     * 회원가입 API [POST] /users/signup
     * 
     * @return Response<SignUpOutput>
     */
    // Body
    @PostMapping("/signup")
    public Response<SignUpOutput> signUp(@RequestBody SignUpInput signUpInput) {
        log.info("[POST] /users/signup");
        return userService.signUp(signUpInput);
    }

    /**
     * 로그인 API [POST] /users/signin
     * 
     * @return Response<SignInOutput>
     */
    // Body
    @PostMapping("/signin")
    public Response<SignInOutput> signIn(@RequestBody SignInInput signInInput) {
        log.info("[POST] /users/signin");
        return userService.signIn(signInInput);
    }

    /**
     * 회원탈퇴 API
     * [PATCH] /users/deactivate
     * @return Response<Object>
     */
    // Body
    @PatchMapping("/deactivate")
    public Response<Object> changeDeleteStatus() {
        log.info("[PATCH] /users/deactivate");
        return userService.changeDeleteStatus();
    }

    @PostMapping("/jwt")
    public Response<JwtOutput> jwt() {
        System.out.println("[POST] /user/jwt");
        int userId = jwtService.getUserId();
        if (userId == -1)
            return new Response<>(UNAUTHORIZED_TOKEN);
        if (userId == -2)
            return new Response<>(BAD_ACCESS_TOKEN_VALUE);
        if (userId == -3)
            return new Response<>(FORBIDDEN_USER_ID);
        JwtOutput jwtOutput = new JwtOutput(userId);
        return new Response<>(jwtOutput, SUCCESS_SIGN_IN);
    }

    /**
     * 이메일 인증 API [POST] /users/email
     * 
     * @return Response<EmailOutput>
     */
    // Body
    @PostMapping("/email")
    public Response<EmailOutput> mailSend(@RequestBody EmailInput emailInput) {
        log.info("[POST] /users/email");
        return userService.sendMail(emailInput);
    }
}
