package com.cafein.service.social;

import com.fasterxml.jackson.databind.JsonNode;
import com.cafein.dto.user.signin.SignInOutput;
import com.cafein.dto.user.signin.SocialLoginType;
import com.cafein.response.Response;
import com.cafein.serviceImpl.social.GoogleOauth;
import com.cafein.serviceImpl.social.KakaoOauth;

public interface SocialOauth {
    String getOauthRedirectURL();
    Response<SignInOutput> requestLogin(String code);
    String requestAccessToken(String code);
    JsonNode requestUserInfo(String accessToken);

    default SocialLoginType type() {
        if (this instanceof GoogleOauth) {
            return SocialLoginType.GOOGLE;
        } else if (this instanceof KakaoOauth) {
            return SocialLoginType.KAKAO;
        } else {
            return null;
        }
    }
}
