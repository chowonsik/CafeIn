package com.cafein.dao;

import com.cafein.dto.bookmark.seleteBookmark.SelectBookmarkInput;
import com.cafein.dto.bookmark.seleteBookmark.SelectBookmarkOutput;
import com.cafein.dto.cafe.search.CafeSearchInput;
import com.cafein.dto.cafe.search.CafeSearchOutput;
import com.cafein.dto.cafe.selectCafeDetail.SelectCafeDetailOutput;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookmarkRepositoryCustom {
    Page<SelectBookmarkOutput> findByUserIdCustum(SelectBookmarkInput selectBookmarkInput, Pageable pageable);
}
