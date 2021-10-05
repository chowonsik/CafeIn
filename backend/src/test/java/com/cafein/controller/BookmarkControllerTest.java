package com.cafein.controller;

import com.cafein.ApiDocumentationTest;
import com.cafein.dto.bookmark.createBookmark.CreateBookmarkInput;
import com.cafein.dto.report.createreport.CreateReportInput;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import static com.cafein.ApiDocumentUtils.getDocumentRequest;
import static com.cafein.ApiDocumentUtils.getDocumentResponse;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BookmarkControllerTest extends ApiDocumentationTest {

    @DisplayName("찜 생성 - 모든 유효성 검사에 통과했다면 찜 생성 성공")
    @Test
    public void 찜_생성() throws Exception {
        //given
        String JWT = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjQ5LCJpYXQiOjE2MzI4MDgyMDF9.ImwkfxLW84OCWp2hBqYiJzGnZqUO6Ni-GskrZZyoTgM";
        CreateBookmarkInput createBookmarkInput = CreateBookmarkInput
                .builder()
                .cafeId(601)
                .build();

        //when
        ResultActions result = mockMvc.perform(post("/api/bookmarks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-ACCESS-TOKEN", JWT)
                        .content(objectMapper.writeValueAsString(createBookmarkInput)).accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        result.andExpect(status().isCreated())
                .andDo(
                        document(
                                "bookmarks/create/successful",
                                getDocumentRequest(),
                                getDocumentResponse(),
                                requestHeaders(headerWithName("X-ACCESS-TOKEN").description("JWT Token")),
                                requestFields(
                                        fieldWithPath("cafeId").type(JsonFieldType.NUMBER)
                                                .description("카페 번호")
                                                .attributes(key("constraint")
                                                        .value(""))
                                ),
                                responseFields(
                                        fieldWithPath("isSuccess").type(JsonFieldType.BOOLEAN)
                                                .description("요청 성공 여부"),
                                        fieldWithPath("status").type(JsonFieldType.NUMBER)
                                                .description("응답 상태"),
                                        fieldWithPath("code").type(JsonFieldType.NUMBER)
                                                .description("응답 코드"),
                                        fieldWithPath("message").type(JsonFieldType.STRING)
                                                .description("응답 메시지"),
                                        fieldWithPath("timestamp").type(JsonFieldType.STRING)
                                                .description("api 호출 일시")
                                )
                        ));
    }

    @DisplayName("찜 목록 조회")
    @Test
    public void 찜_목록_조회() throws Exception {
        //given
        String JWT = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjQ5LCJpYXQiOjE2MzI4MDgyMDF9.ImwkfxLW84OCWp2hBqYiJzGnZqUO6Ni-GskrZZyoTgM";

        //when
        ResultActions result = mockMvc.perform(get("/api/bookmarks")
                        .queryParam("size", "10")
                        .queryParam("page", "1")
                        .header("X-ACCESS-TOKEN", JWT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        result.andExpect(status().isOk())
                .andDo(
                        document(
                                "bookmarks/select/successful",
                                getDocumentRequest(),
                                getDocumentResponse(),
                                requestHeaders(headerWithName("X-ACCESS-TOKEN").description("JWT Token")),
                                requestParameters(
                                        parameterWithName("page").description("페이지 번호"),
                                        parameterWithName("size").description("페이지 사이즈")
                                ),
                                responseFields(
                                        fieldWithPath("isSuccess").type(JsonFieldType.BOOLEAN)
                                                .description("요청 성공 여부"),
                                        fieldWithPath("status").type(JsonFieldType.NUMBER)
                                                .description("응답 상태"),
                                        fieldWithPath("code").type(JsonFieldType.NUMBER)
                                                .description("응답 코드"),
                                        fieldWithPath("message").type(JsonFieldType.STRING)
                                                .description("응답 메시지"),
                                        fieldWithPath("page.currentPage").type(JsonFieldType.NUMBER)
                                                .description("현재 페이지 번호"),
                                        fieldWithPath("page.pageSize").type(JsonFieldType.NUMBER)
                                                .description("페이지 사이즈"),
                                        fieldWithPath("page.totalPages").type(JsonFieldType.NUMBER)
                                                .description("전체 페이지 수"),
                                        fieldWithPath("page.totalElements").type(JsonFieldType.NUMBER)
                                                .description("전체 요소 수"),
                                        fieldWithPath("result").type(JsonFieldType.ARRAY)
                                                .description("찜 목록 조회 리스트").optional(),
                                        fieldWithPath("result.[].bookmarkId").type(JsonFieldType.NUMBER)
                                                .description("찜 번호"),
                                        fieldWithPath("result.[].userId").type(JsonFieldType.NUMBER)
                                                .description("유저 번호"),
                                        fieldWithPath("result.[].cafeId").type(JsonFieldType.NUMBER)
                                                .description("카페 번호"),
                                        fieldWithPath("result.[].cafeName").type(JsonFieldType.STRING)
                                                .description("카페 이름"),
                                        fieldWithPath("result.[].cafeBranch").type(JsonFieldType.STRING)
                                                .description("카페 지점명").optional(),
                                        fieldWithPath("result.[].cafeArea").type(JsonFieldType.STRING)
                                                .description("카페 지역명"),
                                        fieldWithPath("result.[].cafeTel").type(JsonFieldType.STRING)
                                                .description("카페 전화번호").optional(),
                                        fieldWithPath("result.[].cafeAddress").type(JsonFieldType.STRING)
                                                .description("카페 주소"),
                                        fieldWithPath("result.[].cafeLatitude").type(JsonFieldType.STRING)
                                                .description("카페 위도"),
                                        fieldWithPath("result.[].cafeLongitude").type(JsonFieldType.STRING)
                                                .description("카페 경도"),
                                        fieldWithPath("result.[].cafeImgUrl").type(JsonFieldType.STRING)
                                                .description("카페 이미지").optional(),
                                        fieldWithPath("result.[].isBookMark").type(JsonFieldType.NUMBER)
                                                .description("찜 여부 0 : 찜 x, 1 : 찜 o"),
                                        fieldWithPath("result.[].bookmarkCnt").type(JsonFieldType.NUMBER)
                                                .description("카페 찜 개수"),
                                        fieldWithPath("result.[].reviewCnt").type(JsonFieldType.NUMBER)
                                                .description("카페 리뷰 개수"),
                                        fieldWithPath("timestamp").type(JsonFieldType.STRING)
                                                .description("api 호출 일시")
                                )
                        ));
    }

    @DisplayName("찜 삭제 - 모든 유효성 검사에 통과했다면 찜 삭제 성공")
    @Test
    public void 찜_삭제() throws Exception {
        //given
        String JWT = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjQ5LCJpYXQiOjE2MzI4MDgyMDF9.ImwkfxLW84OCWp2hBqYiJzGnZqUO6Ni-GskrZZyoTgM";

        //when
        ResultActions result = mockMvc.perform(delete("/api/bookmarks/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-ACCESS-TOKEN", JWT)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        result.andExpect(status().isOk())
                .andDo(
                        document(
                                "bookmarks/delete/successful",
                                getDocumentRequest(),
                                getDocumentResponse(),
                                requestHeaders(headerWithName("X-ACCESS-TOKEN").description("JWT Token")),
                                pathParameters(
                                        parameterWithName("id").description("삭제 요청할 찜 ID")
                                ),
                                responseFields(
                                        fieldWithPath("isSuccess").type(JsonFieldType.BOOLEAN)
                                                .description("요청 성공 여부"),
                                        fieldWithPath("status").type(JsonFieldType.NUMBER)
                                                .description("응답 상태"),
                                        fieldWithPath("code").type(JsonFieldType.NUMBER)
                                                .description("응답 코드"),
                                        fieldWithPath("message").type(JsonFieldType.STRING)
                                                .description("응답 메시지"),
                                        fieldWithPath("timestamp").type(JsonFieldType.STRING)
                                                .description("api 호출 일시")
                                )
                        ));
    }

}
