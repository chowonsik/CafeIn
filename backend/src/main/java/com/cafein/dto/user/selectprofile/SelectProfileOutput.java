package com.cafein.dto.user.selectprofile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class SelectProfileOutput {
    private int userId;
    private String nickname;
}
