package com.cafein.service.social;

import com.cafein.dto.user.signin.SignInOutput;
import com.cafein.response.Response;

public interface OauthService {
    void request(String type);
    Response<SignInOutput> requestLogin(String type, String code);
    SocialOauth findSocialOauthByType(String type);
}
