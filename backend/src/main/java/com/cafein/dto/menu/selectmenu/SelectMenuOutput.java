package com.cafein.dto.menu.selectmenu;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SelectMenuOutput {
    private String menuName;
    private int menuPrice;
}
