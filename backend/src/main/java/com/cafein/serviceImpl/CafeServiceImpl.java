package com.cafein.serviceImpl;

import com.cafein.configuration.ValidationCheck;
import com.cafein.dao.BookmarkRepository;
import com.cafein.dao.CafeRepository;
import com.cafein.dao.TagsRepository;
import com.cafein.dto.cafe.search.CafeSearchOutput;
import com.cafein.dto.cafe.search.CafeSearchInput;
import com.cafein.dto.cafe.selectCafeDetail.SelectCafeDetailOutput;
import com.cafein.dto.cafe.suggest.CafeCurationInput;
import com.cafein.dto.cafe.suggest.CafeCurationOutput;
import com.cafein.entity.Bookmark;
import com.cafein.entity.Tags;
import com.cafein.response.PageResponse;
import com.cafein.response.Response;
import com.cafein.service.CafeService;
import com.cafein.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.cafein.response.ResponseStatus.*;

@Service("CafeService")
@RequiredArgsConstructor
@Slf4j
public class CafeServiceImpl implements CafeService {

    private final CafeRepository cafeRepository;
    private final JwtService jwtService;
    private final BookmarkRepository bookmarkRepository;
    private final TagsRepository tagsRepository;

    @Override
    public ResponseEntity<Response<SelectCafeDetailOutput>> selectCafe(int id) {
        // 값 형식 체크
        if(!ValidationCheck.isValidId(id))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Response<>(NO_VALUES));

        SelectCafeDetailOutput selectCafeDetailOutput = cafeRepository.findByIdCustom(id, jwtService.getUserId());
        if(selectCafeDetailOutput==null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Response<>(BAD_ID_VALUE));

        // 결과 return
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(selectCafeDetailOutput, SUCCESS_SELECT_CAFE));
    }

    @Override
    public ResponseEntity<PageResponse<CafeSearchOutput>> selectCafeListByWord(CafeSearchInput cafeSearchInput) {

        // 값 형식 체크
        if(cafeSearchInput == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new PageResponse<>(NO_VALUES));
        if(cafeSearchInput.getSearch() == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new PageResponse<>(NO_VALUES));

        Page<CafeSearchOutput> cafeSearchOutput;
        Pageable pageable = PageRequest.of(cafeSearchInput.getPage()-1, cafeSearchInput.getSize());

        try {
            cafeSearchOutput = cafeRepository.findByWordCustom(cafeSearchInput, jwtService.getUserId(), pageable);
        } catch (Exception e) {
            log.error("[GET]/cafe database error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new PageResponse<>(DATABASE_ERROR));
        }
        // 결과 return
        return ResponseEntity.status(HttpStatus.OK)
                .body(new PageResponse<>(cafeSearchOutput, SUCCESS_SELECT_CAFE));
    }

    @Override
    public ResponseEntity<PageResponse<CafeCurationOutput>> curationCafe(CafeCurationInput cafeCurationInput) {
        // 값 형식 체크
        if(cafeCurationInput == null
                || cafeCurationInput.getDistance() == null
                || cafeCurationInput.getLatitude() == null
                || cafeCurationInput.getLongitude() == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new PageResponse<>(NO_VALUES));

        if(cafeCurationInput.getType()==1
                && cafeCurationInput.getCategory() == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new PageResponse<>(NO_VALUES));

        if(!ValidationCheck.isValidId(jwtService.getUserId()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new PageResponse<>(NOT_FOUND_USER));

        // 추천 카페 구하기
        Page<CafeCurationOutput> curationOutput;
        Pageable pageable = PageRequest.of(cafeCurationInput.getPage()-1, cafeCurationInput.getSize());
        try{
            // type : 1 카테고리로 추천
            if(cafeCurationInput.getType()==1){
                log.info("[GET]/cafes/curation "+cafeCurationInput.getCategory()+" 카테고리로 추천합니다.");
                curationOutput = cafeRepository.curationCafe(cafeCurationInput, jwtService.getUserId(), pageable);
            }
            // type : 2 유저 찜 목록으로 추천
            else if(cafeCurationInput.getType()==2) {
                // 찜 목록 list
                List<Bookmark> bookmarkList = bookmarkRepository.findByUserId(jwtService.getUserId()).orElse(null);
                // 2-1.찜 목록이 없음
                if(bookmarkList==null){
                    log.info("[GET]/cafes/curation 찜한 카페가 없는 유저입니다. 점수 총합 순으로 추천합니다");
                    cafeCurationInput.setCategory(null);
                    curationOutput = cafeRepository.curationCafe(cafeCurationInput, jwtService.getUserId(), pageable);
                }
                // 2-2. 찜 목록이 있음
                else{
                    //속성별 점수 토탈 합이 가장 높은 카테고리 구하기
                    Map<String, Integer> totalSum = new HashMap<>();
                    for(Bookmark bookmark : bookmarkList){
                        Tags tags = tagsRepository.findByCafeId(bookmark.getCafe().getId()).orElse(null);
                        totalSum.put("taste", totalSum.getOrDefault("taste",0) + tags.getTaste());
                        totalSum.put("view", totalSum.getOrDefault("view",0) + tags.getView());
                        totalSum.put("mood", totalSum.getOrDefault("mood",0) + tags.getMood());
                        totalSum.put("wide", totalSum.getOrDefault("wide",0) + tags.getWide());
                        totalSum.put("study", totalSum.getOrDefault("study",0) + tags.getStudy());
                        totalSum.put("nice", totalSum.getOrDefault("nice",0) + tags.getNice());
                    }
                    String maxCategory="taste";
                    for(String key : totalSum.keySet()){
                        if(totalSum.get(key)>totalSum.get(maxCategory)) maxCategory = key;
                        System.out.println(key+" : " +totalSum.get(key));
                    }
                    log.info("[GET]/cafes/curation 찜한 카페 기반 가장 선호되는 속성은 " + maxCategory +"입니다.");
                    cafeCurationInput.setCategory(maxCategory);
                    curationOutput = cafeRepository.curationCafe(cafeCurationInput, jwtService.getUserId(), pageable);
                }
            }
            // 잘못된 type
            else return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new PageResponse<>(BAD_CURATION_TYPE_VALUE));
        } catch (Exception e) {
            log.error("[GET]/cafes/curation database error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new PageResponse<>(DATABASE_ERROR));
        }
        // 결과 return
        return ResponseEntity.status(HttpStatus.OK)
                .body(new PageResponse<>(curationOutput, SUCCESS_SUGGEST_CAFE));
    }


}
