package com.cafein.service;

import com.cafein.dto.bookmark.createBookmark.CreateBookmarkInput;
import com.cafein.dto.bookmark.seleteBookmark.SelectBookmarkOutput;
import com.cafein.response.PageResponse;
import com.cafein.response.Response;
import org.springframework.http.ResponseEntity;

public interface BookmarkService {
    ResponseEntity<Response<Object>> createBookmark(CreateBookmarkInput createBookmarkInput);
    ResponseEntity<PageResponse<SelectBookmarkOutput>> selectBookmarkListByUserId(int page, int size);
    ResponseEntity<Response<Object>> deleteBookmark(int id);
}
