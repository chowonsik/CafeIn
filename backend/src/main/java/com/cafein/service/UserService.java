package com.cafein.service;

import com.cafein.dto.user.email.EmailInput;
import com.cafein.dto.user.email.EmailOutput;
import com.cafein.dto.user.signin.SignInInput;
import com.cafein.response.Response;
import com.cafein.dto.user.signin.SignInOutput;
import com.cafein.dto.user.signup.SignUpInput;
import com.cafein.dto.user.signup.SignUpOutput;

public interface UserService {
    Response<SignInOutput> signIn(SignInInput signInInput);
    Response<SignUpOutput> signUp(SignUpInput signUpInput);
    Response<EmailOutput> sendMail(EmailInput emailInput);
    Response<Object> changeDeleteStatus();
}
