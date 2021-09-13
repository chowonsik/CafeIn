package com.cafein.dto.user.signin;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Getter
public class SignInInput {
    private String email;
    private String password;
}
