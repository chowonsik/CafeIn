package com.cafein.service;

import com.cafein.dto.menu.selectmenu.SelectMenuInput;
import com.cafein.dto.menu.selectmenu.SelectMenuOutput;
import com.cafein.response.Response;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MenuService {
    ResponseEntity<Response<List<SelectMenuOutput>>> selectMenu(SelectMenuInput selectMenuInput);
}
