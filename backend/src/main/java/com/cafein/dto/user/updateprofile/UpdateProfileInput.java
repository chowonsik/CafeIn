package com.cafein.dto.user.updateprofile;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UpdateProfileInput {
    private String password;
    private String nickname;
}
