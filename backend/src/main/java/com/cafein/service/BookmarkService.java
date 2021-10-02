package com.cafein.service;

import com.cafein.dto.bookmark.createBookmark.CreateBookmarkInput;
import com.cafein.dto.bookmark.seleteBookmark.SelectBookmarkInput;
import com.cafein.dto.bookmark.seleteBookmark.SelectBookmarkOutput;
import com.cafein.dto.cafe.search.CafeSearchInput;
import com.cafein.dto.cafe.search.CafeSearchOutput;
import com.cafein.dto.cafe.selectCafeDetail.SelectCafeDetailOutput;
import com.cafein.response.PageResponse;
import com.cafein.response.Response;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface BookmarkService {
    ResponseEntity<Response<Object>> createBookmark(CreateBookmarkInput createBookmarkInput);
    ResponseEntity<PageResponse<SelectBookmarkOutput>> selectBookmarkListByUserId(int page, int size);
    ResponseEntity<Response<Object>> deleteBookmark(int id);
}
