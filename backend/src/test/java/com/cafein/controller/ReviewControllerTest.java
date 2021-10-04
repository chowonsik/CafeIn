package com.cafein.controller;

import com.cafein.ApiDocumentationTest;
import com.cafein.dto.review.createreview.CreateReviewInput;
import com.cafein.dto.review.updatereview.UpdateReviewInput;
import com.cafein.entity.Review;
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

public class ReviewControllerTest extends ApiDocumentationTest {

    @DisplayName("리뷰 작성 - 모든 유효성 검사에 통과했다면 리뷰 작성 성공")
    @Test
    public void 리뷰_작성() throws Exception {
        //given
        String JWT = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjQ5LCJpYXQiOjE2MzI4MDgyMDF9.ImwkfxLW84OCWp2hBqYiJzGnZqUO6Ni-GskrZZyoTgM";
        CreateReviewInput createReviewInput = CreateReviewInput
                .builder()
                .cafeId(2)
                .content("커피가 맛있다.")
                .totalScore(5)
                .build();

        //when
        ResultActions result = mockMvc.perform(post("/api/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-ACCESS-TOKEN", JWT)
                        .content(objectMapper.writeValueAsString(createReviewInput)).accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        result.andExpect(status().isCreated())
                .andDo(
                        document(
                                "reviews/create/successful",
                                getDocumentRequest(),
                                getDocumentResponse(),
                                requestHeaders(headerWithName("X-ACCESS-TOKEN").description("JWT Token")),
                                requestFields(
                                        fieldWithPath("cafeId").type(JsonFieldType.NUMBER)
                                                .description("카페 번호")
                                                .attributes(key("constraint")
                                                        .value("")),
                                        fieldWithPath("content").type(JsonFieldType.STRING)
                                                .description("평가 내용")
                                                .attributes(key("constraint")
                                                        .value("최소 1글자 이상으로 입력해주세요.")),
                                        fieldWithPath("totalScore").type(JsonFieldType.NUMBER)
                                                .description("종합 별점")
                                                .attributes(key("constraint")
                                                        .value("1~5점 사이로 입력해주세요."))
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

    @DisplayName("리뷰 조회 - 카페 리뷰 조회")
    @Test
    public void 카페_리뷰_조회() throws Exception {
        //given

        //when
        ResultActions result = mockMvc.perform(get("/api/reviews")
                        .queryParam("cafeId", "91")
                        .queryParam("userId", "")
                        .queryParam("search", "")
                        .queryParam("size", "10")
                        .queryParam("page", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        result.andExpect(status().isOk())
                .andDo(
                        document(
                                "reviews/select/cafe/successful",
                                getDocumentRequest(),
                                getDocumentResponse(),
                                requestParameters(
                                        parameterWithName("cafeId").description("카페 번호").optional(),
                                        parameterWithName("userId").description("유저 번호").optional(),
                                        parameterWithName("search").description("리뷰 내용 검색어").optional(),
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
                                                .description("리뷰 조회 결과 리스트").optional(),
                                        fieldWithPath("result.[].cafeId").type(JsonFieldType.NUMBER)
                                                .description("카페 번호"),
                                        fieldWithPath("result.[].cafeName").type(JsonFieldType.STRING)
                                                .description("카페 이름"),
                                        fieldWithPath("result.[].reviewId").type(JsonFieldType.NUMBER)
                                                .description("리뷰 번호"),
                                        fieldWithPath("result.[].userId").type(JsonFieldType.NUMBER)
                                                .description("리뷰 작성 유저 번호"),
                                        fieldWithPath("result.[].reviewContent").type(JsonFieldType.STRING)
                                                .description("리뷰 내용"),
                                        fieldWithPath("result.[].reviewScore").type(JsonFieldType.NUMBER)
                                                .description("리뷰 점수"),
                                        fieldWithPath("result.[].reviewCreatedAt").type(JsonFieldType.STRING)
                                                .description("리뷰 작성일"),
                                        fieldWithPath("timestamp").type(JsonFieldType.STRING)
                                                .description("api 호출 일시")
                                )
                        ));
    }

    @DisplayName("리뷰 조회 - 유저 리뷰 조회")
    @Test
    public void 유저_리뷰_조회() throws Exception {
        //given

        //when
        ResultActions result = mockMvc.perform(get("/api/reviews")
                        .queryParam("cafeId", "")
                        .queryParam("userId", "49")
                        .queryParam("search", "")
                        .queryParam("size", "10")
                        .queryParam("page", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        result.andExpect(status().isOk())
                .andDo(
                        document(
                                "reviews/select/user/successful",
                                getDocumentRequest(),
                                getDocumentResponse(),
                                requestParameters(
                                        parameterWithName("cafeId").description("카페 번호").optional(),
                                        parameterWithName("userId").description("유저 번호").optional(),
                                        parameterWithName("search").description("리뷰 내용 검색어").optional(),
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
                                                .description("리뷰 조회 결과 리스트").optional(),
                                        fieldWithPath("result.[].cafeId").type(JsonFieldType.NUMBER)
                                                .description("카페 번호"),
                                        fieldWithPath("result.[].cafeName").type(JsonFieldType.STRING)
                                                .description("카페 이름"),
                                        fieldWithPath("result.[].reviewId").type(JsonFieldType.NUMBER)
                                                .description("리뷰 번호"),
                                        fieldWithPath("result.[].userId").type(JsonFieldType.NUMBER)
                                                .description("리뷰 작성 유저 번호"),
                                        fieldWithPath("result.[].reviewContent").type(JsonFieldType.STRING)
                                                .description("리뷰 내용"),
                                        fieldWithPath("result.[].reviewScore").type(JsonFieldType.NUMBER)
                                                .description("리뷰 점수"),
                                        fieldWithPath("result.[].reviewCreatedAt").type(JsonFieldType.STRING)
                                                .description("리뷰 작성일"),
                                        fieldWithPath("timestamp").type(JsonFieldType.STRING)
                                                .description("api 호출 일시")
                                )
                        ));
    }

    @DisplayName("리뷰 조회 - 워드클라우드 리뷰 조회")
    @Test
    public void 워드클라우드_리뷰_조회() throws Exception {
        //given

        //when
        ResultActions result = mockMvc.perform(get("/api/reviews")
                        .queryParam("cafeId", "91")
                        .queryParam("userId", "")
                        .queryParam("search", "주차")
                        .queryParam("size", "10")
                        .queryParam("page", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        result.andExpect(status().isOk())
                .andDo(
                        document(
                                "reviews/select/search/successful",
                                getDocumentRequest(),
                                getDocumentResponse(),
                                requestParameters(
                                        parameterWithName("cafeId").description("카페 번호").optional(),
                                        parameterWithName("userId").description("유저 번호").optional(),
                                        parameterWithName("search").description("리뷰 내용 검색어").optional(),
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
                                                .description("리뷰 조회 결과 리스트").optional(),
                                        fieldWithPath("result.[].cafeId").type(JsonFieldType.NUMBER)
                                                .description("카페 번호"),
                                        fieldWithPath("result.[].cafeName").type(JsonFieldType.STRING)
                                                .description("카페 이름"),
                                        fieldWithPath("result.[].reviewId").type(JsonFieldType.NUMBER)
                                                .description("리뷰 번호"),
                                        fieldWithPath("result.[].userId").type(JsonFieldType.NUMBER)
                                                .description("리뷰 작성 유저 번호"),
                                        fieldWithPath("result.[].reviewContent").type(JsonFieldType.STRING)
                                                .description("리뷰 내용"),
                                        fieldWithPath("result.[].reviewScore").type(JsonFieldType.NUMBER)
                                                .description("리뷰 점수"),
                                        fieldWithPath("result.[].reviewCreatedAt").type(JsonFieldType.STRING)
                                                .description("리뷰 작성일"),
                                        fieldWithPath("timestamp").type(JsonFieldType.STRING)
                                                .description("api 호출 일시")
                                )
                        ));
    }

    @DisplayName("리뷰 수정 - 모든 유효성 검사에 통과했다면 리뷰 수정 성공")
    @Test
    public void 리뷰_수정() throws Exception {
        //given
        String JWT = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjQ5LCJpYXQiOjE2MzI4MDgyMDF9.ImwkfxLW84OCWp2hBqYiJzGnZqUO6Ni-GskrZZyoTgM";
        UpdateReviewInput updateReviewInput = UpdateReviewInput
                .builder()
                .content("커피가 맛있다.")
                .totalScore(3)
                .build();

        //when
        ResultActions result = mockMvc.perform(patch("/api/reviews/{id}", 615589)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-ACCESS-TOKEN", JWT)
                        .content(objectMapper.writeValueAsString(updateReviewInput)).accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        result.andExpect(status().isOk())
                .andDo(
                        document(
                                "reviews/update/successful",
                                getDocumentRequest(),
                                getDocumentResponse(),
                                requestHeaders(headerWithName("X-ACCESS-TOKEN").description("JWT Token")),
                                pathParameters(
                                        parameterWithName("id").description("수정 요청할 리뷰 ID")
                                ),
                                requestFields(
                                        fieldWithPath("content").type(JsonFieldType.STRING)
                                                .description("평가 내용")
                                                .optional()
                                                .attributes(key("constraint")
                                                        .value("최소 1글자 이상으로 입력해주세요.")),
                                        fieldWithPath("totalScore").type(JsonFieldType.NUMBER)
                                                .description("종합 별점")
                                                .optional()
                                                .attributes(key("constraint")
                                                        .value("1~5점 사이로 입력해주세요."))
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

    @DisplayName("리뷰 삭제 - 모든 유효성 검사에 통과했다면 리뷰 삭제 성공")
    @Test
    public void 리뷰_삭제() throws Exception {
        //given
        String JWT = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjQ5LCJpYXQiOjE2MzI4MDgyMDF9.ImwkfxLW84OCWp2hBqYiJzGnZqUO6Ni-GskrZZyoTgM";

        //when
        ResultActions result = mockMvc.perform(delete("/api/reviews/{id}", 615589)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-ACCESS-TOKEN", JWT)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        result.andExpect(status().isOk())
                .andDo(
                        document(
                                "reviews/delete/successful",
                                getDocumentRequest(),
                                getDocumentResponse(),
                                requestHeaders(headerWithName("X-ACCESS-TOKEN").description("JWT Token")),
                                pathParameters(
                                        parameterWithName("id").description("삭제 요청할 리뷰 ID")
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
