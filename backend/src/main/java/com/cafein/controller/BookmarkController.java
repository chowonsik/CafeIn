package com.cafein.controller;

import com.cafein.dto.bookmark.createBookmark.CreateBookmarkInput;
import com.cafein.dto.bookmark.seleteBookmark.SelectBookmarkInput;
import com.cafein.dto.bookmark.seleteBookmark.SelectBookmarkOutput;
import com.cafein.dto.cafe.search.CafeSearchInput;
import com.cafein.dto.cafe.search.CafeSearchOutput;
import com.cafein.dto.cafe.selectCafeDetail.SelectCafeDetailOutput;
import com.cafein.entity.Bookmark;
import com.cafein.response.PageResponse;
import com.cafein.response.Response;
import com.cafein.service.BookmarkService;
import com.cafein.service.CafeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookmarks")
@RequiredArgsConstructor
@Slf4j
public class BookmarkController {

    private final BookmarkService bookmarkService;

    /**
     * 카페 찜 등록 API
     * [POST] /api/bookmarks
     * @return ResponseEntity<Response<Object>>
     */
    // Params
    @PostMapping
    public ResponseEntity<Response<Object>> createBookmark(@RequestBody CreateBookmarkInput createBookmarkInput){
        log.info("[POST] /api/bookmarks");
        return bookmarkService.createBookmark(createBookmarkInput);
    }

    /**
     * 찜한 카페 list userId로 조회 API
     * [GET] /api/bookmarks
     * @return ResponseEntity<Response<SelectCafeDetailOutput>>
     */
    // Params
    @GetMapping
    public ResponseEntity<PageResponse<SelectBookmarkOutput>> getBookmarkList(@RequestParam(required = true) int page, @RequestParam(required = true) int size){
        log.info("[GET] /api/bookmarks");
        return bookmarkService.selectBookmarkListByUserId(page,size);
    }

}
