package com.cafein.serviceImpl;

import com.cafein.configuration.ValidationCheck;
import com.cafein.dao.BookmarkRepository;
import com.cafein.dao.CafeRepository;
import com.cafein.dao.UserRepository;
import com.cafein.dto.bookmark.createBookmark.CreateBookmarkInput;
import com.cafein.dto.bookmark.seleteBookmark.SelectBookmarkOutput;
import com.cafein.entity.Bookmark;
import com.cafein.entity.Cafe;
import com.cafein.entity.User;
import com.cafein.response.PageResponse;
import com.cafein.response.Response;
import com.cafein.service.BookmarkService;
import com.cafein.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static com.cafein.response.ResponseStatus.*;

@Service("BookmarkService")
@RequiredArgsConstructor
@Slf4j
public class BookmarkServiceImpl implements BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;
    private final CafeRepository cafeRepository;
    private final JwtService jwtService;

    @Override
    public ResponseEntity<Response<Object>> createBookmark(CreateBookmarkInput createBookmarkInput) {
        // 값 형식 체크
        if(createBookmarkInput == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Response<>(NO_VALUES));
        if (!ValidationCheck.isValidId(createBookmarkInput.getCafeId()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new Response<>(BAD_ID_VALUE));

        Bookmark bookmark;
        try {
            User user = userRepository.findById(jwtService.getUserId()).orElse(null);
            Cafe cafe = cafeRepository.findById(createBookmarkInput.getCafeId()).orElse(null);
            if (user == null)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new Response<>(BAD_REQUEST));
            if (cafe == null)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new Response<>(BAD_ID_VALUE));
            // 중복 체크
            if(bookmarkRepository.findByUserAndCafe(user,cafe).orElse(null)!=null)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new Response<>(EXISTS_BOOKMARK));

            bookmark = Bookmark.builder().user(user).cafe(cafe).build();
            bookmarkRepository.save(bookmark);

        } catch (Exception e) {
            log.error("[GET]/bookmarks database error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Response<>(DATABASE_ERROR));
        }
        bookmarkRepository.save(bookmark);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(CREATED_BOOKMARK));
    }

    @Override
    public ResponseEntity<PageResponse<SelectBookmarkOutput>> selectBookmarkListByUserId(int page, int size) {
        // 1. 값 형식 체크
        if (!ValidationCheck.isValidPage(page)
                || !ValidationCheck.isValidId(size))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new PageResponse<>(BAD_REQUEST));

        // 2. 일치하는 찜 목록 정보 가져오기
        Page<SelectBookmarkOutput> selectBookmarkOutput;
        Pageable pageable = PageRequest.of(page-1, size);

        try {
            // userId
            User loginUser = jwtService.getUserDB();
            if (loginUser == null)  {
                log.error("[GET]/bookmarks NOT FOUND LOGIN USER error");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new PageResponse<>(NOT_FOUND_USER));
            }
            
            selectBookmarkOutput = bookmarkRepository.findByUserIdCustum(loginUser.getId(), pageable);
        } catch (Exception e) {
            log.error("[GET]/bookmarks database error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new PageResponse<>(DATABASE_ERROR));
        }
        // 결과 return
        return ResponseEntity.status(HttpStatus.OK)
                .body(new PageResponse<>(selectBookmarkOutput, SUCCESS_SELECT_CAFE));
    }
}
