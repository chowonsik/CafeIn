package com.cafein.dao;

import com.cafein.dto.bookmark.seleteBookmark.SelectBookmarkOutput;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookmarkRepositoryCustom {
    Page<SelectBookmarkOutput> findByUserIdCustum(int userId, Pageable pageable);
}
