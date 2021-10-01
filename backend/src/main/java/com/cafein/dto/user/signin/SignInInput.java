package com.cafein.dto.user.signin;

import lombok.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class SignInInput {
    private String email;
    private String password;
}
