package com.cafein.controller;

import com.cafein.dto.user.email.EmailInput;
import com.cafein.dto.user.signin.SignInInput;
import com.cafein.dto.user.signup.SignUpInput;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import static com.cafein.ApiDocumentUtils.getDocumentRequest;
import static com.cafein.ApiDocumentUtils.getDocumentResponse;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;

@AutoConfigureRestDocs
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(RestDocumentationExtension.class)
@ActiveProfiles("junit")
@Transactional
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup(WebApplicationContext webApplicationContext,
                      RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @DisplayName("회원가입 - 모든 유효성 검사에 통과했다면 회원가입 성공")
    @Test
    public void 유저_회원가입() throws Exception {
        //given
        SignUpInput signUpInput = SignUpInput
                .builder()
                .email("cwscms@naver.com")
                .password("test1234")
                .nickname("test용")
                .build();
        //when
        ResultActions result = mockMvc.perform(post("/api/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpInput)).accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.code", is(201)))
                .andDo(
                        document(
                                "users/signup/successful",
                                getDocumentRequest(),
                                getDocumentResponse(),
                                requestFields(
                                        fieldWithPath("email").type(JsonFieldType.STRING)
                                                .description("사용자 이메일 주소")
                                                .attributes(key("constraint")
                                                        .value("최소 3글자, 최대 50글자 이내로 입력해주세요. @*.com의 양식을 갖추어야 합니다.")),
                                        fieldWithPath("password").type(JsonFieldType.STRING)
                                                .description("사용자 비밀번호")
                                                .attributes(key("constraint")
                                                        .value("최소 3글자, 최대 20글자 이내로 입력해주세요.")),
                                        fieldWithPath("nickname").type(JsonFieldType.STRING)
                                                .description("사용자 닉네임")
                                                .attributes(key("constraint")
                                                        .value("최소 2글자, 최대 10글자 이내로 입력해주세요."))
                                ),
                                responseFields(
                                        fieldWithPath("isSuccess").type(JsonFieldType.BOOLEAN)
                                                .description("요청 성공 여부"),
                                        fieldWithPath("status").type(JsonFieldType.NUMBER)
                                                .description("응답 상태"),
                                        fieldWithPath("code").type(JsonFieldType.NUMBER)
                                                .description("응답 코드"),
                                        fieldWithPath("message").type(JsonFieldType.STRING)
                                                .description("응답 메시지"),
                                        fieldWithPath("result.userId").type(JsonFieldType.NUMBER)
                                                .description("유저 번호"),
                                        fieldWithPath("result.accessToken").type(JsonFieldType.STRING)
                                                .description("유저 JWT"),
                                        fieldWithPath("timestamp").type(JsonFieldType.STRING)
                                                .description("api 호출 일시")
                                )
                        ));
    }

    @DisplayName("로그인 - 모든 유효성 검사에 통과했다면 로그인 성공")
    @Test
    public void 유저_로그인() throws Exception {
        //given
        SignInInput signInInput = SignInInput
                .builder()
                .email("test@naver.com")
                .password("test1234")
                .build();
        //when

        ResultActions result = mockMvc.perform(post("/api/users/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signInInput)).accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(201)))
                .andDo(
                        document(
                                "users/signin/successful",
                                getDocumentRequest(),
                                getDocumentResponse(),
                                requestFields(
                                        fieldWithPath("email").type(JsonFieldType.STRING)
                                                .description("사용자 이메일 주소")
                                                .attributes(key("constraint")
                                                        .value("최소 3글자, 최대 50글자 이내로 입력해주세요. @*.com의 양식을 갖추어야 합니다.")),
                                        fieldWithPath("password").type(JsonFieldType.STRING)
                                                .description("사용자 비밀번호")
                                                .attributes(key("constraint")
                                                        .value("최소 3글자, 최대 20글자 이내로 입력해주세요."))
                                ),
                                responseFields(
                                        fieldWithPath("isSuccess").type(JsonFieldType.BOOLEAN)
                                                .description("요청 성공 여부"),
                                        fieldWithPath("status").type(JsonFieldType.NUMBER)
                                                .description("응답 상태"),
                                        fieldWithPath("code").type(JsonFieldType.NUMBER)
                                                .description("응답 코드"),
                                        fieldWithPath("message").type(JsonFieldType.STRING)
                                                .description("응답 메시지"),
                                        fieldWithPath("result.userId").type(JsonFieldType.NUMBER)
                                                .description("유저 번호"),
                                        fieldWithPath("result.accessToken").type(JsonFieldType.STRING)
                                                .description("유저 JWT"),
                                        fieldWithPath("result.oauth").type(JsonFieldType.OBJECT).optional()
                                                .description("유저 Oauth"),
                                        fieldWithPath("timestamp").type(JsonFieldType.STRING)
                                                .description("api 호출 일시")
                                )
                        ));
    }

    @DisplayName("회원탈퇴 - JWT토큰안의 유저 id를 통해 회원탈퇴 성공")
    @Test
    public void 유저_회원탈퇴() throws Exception {
        //given
        String JWT = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjQ5LCJpYXQiOjE2MzI4MDgyMDF9.ImwkfxLW84OCWp2hBqYiJzGnZqUO6Ni-GskrZZyoTgM";
        //when
        ResultActions result = mockMvc.perform(patch("/api/users/deactivate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-ACCESS-TOKEN", JWT)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        result.andExpect(status().isNoContent())
                .andExpect(jsonPath("$.code", is(444)))
                .andDo(
                        document(
                                "users/deactivate/successful",
                                getDocumentRequest(),
                                getDocumentResponse(),
                                responseFields(
                                        fieldWithPath("isSuccess").type(JsonFieldType.BOOLEAN)
                                                .description("요청 성공 여부"),
                                        fieldWithPath("status").type(JsonFieldType.NUMBER)
                                                .description("응답 상태"),
                                        fieldWithPath("code").type(JsonFieldType.NUMBER)
                                                .description("응답 코드"),
                                        fieldWithPath("message").type(JsonFieldType.STRING)
                                                .description("응답 메시지"),
                                        fieldWithPath("timestamp").type(JsonFieldType.STRING)
                                                .description("api 호출 일시")
                                )
                        ));
    }

    @DisplayName("JWT 토큰 검증 - 모든 유효성 검사에 통과했다면 JWT 토큰 검증 성공")
    @Test
    public void 유저_jwt_토큰_검증() throws Exception {
        //given
        String JWT = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjQ5LCJpYXQiOjE2MzI4MDgyMDF9.ImwkfxLW84OCWp2hBqYiJzGnZqUO6Ni-GskrZZyoTgM";
        //when
        ResultActions result = mockMvc.perform(post("/api/users/jwt")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-ACCESS-TOKEN", JWT)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(201)))
                .andDo(
                        document(
                                "users/jwt/successful",
                                getDocumentRequest(),
                                getDocumentResponse(),
                                responseFields(
                                        fieldWithPath("isSuccess").type(JsonFieldType.BOOLEAN)
                                                .description("요청 성공 여부"),
                                        fieldWithPath("status").type(JsonFieldType.NUMBER)
                                                .description("응답 상태"),
                                        fieldWithPath("code").type(JsonFieldType.NUMBER)
                                                .description("응답 코드"),
                                        fieldWithPath("message").type(JsonFieldType.STRING)
                                                .description("응답 메시지"),
                                        fieldWithPath("result.userId").type(JsonFieldType.NUMBER)
                                                .description("유저 번호"),
                                        fieldWithPath("timestamp").type(JsonFieldType.STRING)
                                                .description("api 호출 일시")
                                )
                        ));
    }

    @DisplayName("인증번호 전송 - 모든 유효성 검사에 통과했다면 이메일로 인증번호 전송 성공")
    @Test
    public void 유저_이메일인증() throws Exception {
        //given
        EmailInput emailInput = EmailInput
                .builder()
                .email("test2@naver.com")
                .build();
        //when

        ResultActions result = mockMvc.perform(post("/api/users/email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(emailInput)).accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        result.andExpect(status().isOk())
                .andDo(
                        document(
                                "users/email/successful",
                                getDocumentRequest(),
                                getDocumentResponse(),
                                requestFields(
                                        fieldWithPath("email").type(JsonFieldType.STRING)
                                                .description("사용자 이메일 주소")
                                                .attributes(key("constraint")
                                                        .value("최소 3글자, 최대 50글자 이내로 입력해주세요. @*.com의 양식을 갖추어야 합니다."))
                                ),
                                responseFields(
                                        fieldWithPath("isSuccess").type(JsonFieldType.BOOLEAN)
                                                .description("요청 성공 여부"),
                                        fieldWithPath("status").type(JsonFieldType.NUMBER)
                                                .description("응답 상태"),
                                        fieldWithPath("code").type(JsonFieldType.NUMBER)
                                                .description("응답 코드"),
                                        fieldWithPath("message").type(JsonFieldType.STRING)
                                                .description("응답 메시지"),
                                        fieldWithPath("result.auth").type(JsonFieldType.STRING)
                                                .description("인증 번호"),
                                        fieldWithPath("timestamp").type(JsonFieldType.STRING)
                                                .description("api 호출 일시")
                                )
                        ));
    }

}
