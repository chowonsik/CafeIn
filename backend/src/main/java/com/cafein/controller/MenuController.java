package com.cafein.controller;

import com.cafein.dto.menu.selectmenu.SelectMenuInput;
import com.cafein.dto.menu.selectmenu.SelectMenuOutput;
import com.cafein.response.Response;
import com.cafein.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menus")
@RequiredArgsConstructor
@Slf4j
public class MenuController {

    private final MenuService menuService;

    /**
     * 메뉴 조회 API [GET] /api/menus
     *
     * @return ResponseEntity<Response<List<SelectMenuOutput>>>
     */

    @GetMapping
    public ResponseEntity<Response<List<SelectMenuOutput>>> selectReview(SelectMenuInput selectMenuInput) {
        log.info("[GET] /api/menus");
        return menuService.selectMenu(selectMenuInput);
    }

}
