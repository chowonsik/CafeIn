package com.cafein.serviceImpl;

import com.cafein.configuration.ValidationCheck;
import com.cafein.dao.MenuRepository;
import com.cafein.dto.menu.selectmenu.SelectMenuInput;
import com.cafein.dto.menu.selectmenu.SelectMenuOutput;
import com.cafein.response.Response;
import com.cafein.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.cafein.response.ResponseStatus.*;

@Service("MenuService")
@RequiredArgsConstructor
@Slf4j
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;

    @Override
    public ResponseEntity<Response<List<SelectMenuOutput>>> selectMenu(SelectMenuInput selectMenuInput) {
        // 1. 값 형식 체크
        if (selectMenuInput == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Response<>(NO_VALUES));
        if (!ValidationCheck.isValidId(selectMenuInput.getCafeId()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Response<>(BAD_CAFE_ID));

        // 2. 메뉴 조회
        List<SelectMenuOutput> selectMenuOutput;
        try {
            selectMenuOutput = menuRepository.findByCafeId(selectMenuInput.getCafeId());

        } catch (Exception e) {
            log.error("[menus/get] database error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Response<>(DATABASE_ERROR));
        }

        // 3. 결과 return
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(selectMenuOutput, SUCCESS_SELECT_MENU));
    }
}
