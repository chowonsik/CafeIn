package com.cafein.controller;

import com.cafein.ApiDocumentationTest;
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
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ReportControllerTest extends ApiDocumentationTest {

    @DisplayName("리뷰 신고 둥록 - 모든 유효성 검사에 통과했다면 리뷰 신고 등록 성공")
    @Test
    public void 리뷰_신고_등록() throws Exception {
        //given
        String JWT = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjQ5LCJpYXQiOjE2MzI4MDgyMDF9.ImwkfxLW84OCWp2hBqYiJzGnZqUO6Ni-GskrZZyoTgM";
        CreateReportInput createReportInput = CreateReportInput
                .builder()
                .reviewId(2)
                .content("부적절한 내용이 포함되어 있습니다.")
                .build();

        //when
        ResultActions result = mockMvc.perform(post("/api/reports")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-ACCESS-TOKEN", JWT)
                        .content(objectMapper.writeValueAsString(createReportInput)).accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        result.andExpect(status().isCreated())
                .andDo(
                        document(
                                "reports/create/successful",
                                getDocumentRequest(),
                                getDocumentResponse(),
                                requestHeaders(headerWithName("X-ACCESS-TOKEN").description("JWT Token")),
                                requestFields(
                                        fieldWithPath("reviewId").type(JsonFieldType.NUMBER)
                                                .description("리뷰 번호")
                                                .attributes(key("constraint")
                                                        .value("")),
                                        fieldWithPath("content").type(JsonFieldType.STRING)
                                                .description("평가 내용")
                                                .attributes(key("constraint")
                                                        .value("최소 1글자 이상으로 입력해주세요."))
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

    @DisplayName("리뷰 신고 조회")
    @Test
    public void 리뷰_신고_조회() throws Exception {
        //given
        String JWT = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjQ5LCJpYXQiOjE2MzI4MDgyMDF9.ImwkfxLW84OCWp2hBqYiJzGnZqUO6Ni-GskrZZyoTgM";

        //when
        ResultActions result = mockMvc.perform(get("/api/reports")
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
                                "reports/select/successful",
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
                                        fieldWithPath("result.[].reportId").type(JsonFieldType.NUMBER)
                                                .description("리뷰 신고 번호"),
                                        fieldWithPath("result.[].userId").type(JsonFieldType.NUMBER)
                                                .description("리뷰 신고한 유저 번호"),
                                        fieldWithPath("result.[].reviewId").type(JsonFieldType.NUMBER)
                                                .description("신고된 리뷰 번호"),
                                        fieldWithPath("result.[].reportContent").type(JsonFieldType.STRING)
                                                .description("리뷰 신고 내용"),
                                        fieldWithPath("result.[].reportCreatedAt").type(JsonFieldType.STRING)
                                                .description("리뷰 신고 등록일"),
                                        fieldWithPath("timestamp").type(JsonFieldType.STRING)
                                                .description("api 호출 일시")
                                )
                        ));
    }

}
