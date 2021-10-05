package com.cafein.controller;

import com.cafein.ApiDocumentationTest;
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
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BhourControllerTest extends ApiDocumentationTest {

    @DisplayName("영업시간 조회")
    @Test
    public void 영업시간_조회() throws Exception {
        //given

        //when
        ResultActions result = mockMvc.perform(get("/api/bhours")
                        .queryParam("cafeId", "95")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        result.andExpect(status().isOk())
                .andDo(
                        document(
                                "bhours/select/successful",
                                getDocumentRequest(),
                                getDocumentResponse(),
                                requestParameters(
                                        parameterWithName("cafeId").description("영업시간 조회할 카페 번호")
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
                                        fieldWithPath("result").type(JsonFieldType.ARRAY)
                                                .description("영업시간 조회 결과 리스트").optional(),
                                        fieldWithPath("result.[].bhourType").type(JsonFieldType.NUMBER)
                                                .description("영업 시간 종류").optional(),
                                        fieldWithPath("result.[].bhourWeekType").type(JsonFieldType.NUMBER)
                                                .description("주 단위 종류").optional(),
                                        fieldWithPath("result.[].bhourMon").type(JsonFieldType.NUMBER)
                                                .description("월요일 포함유무 1 - 포함, 0 - 미포함").optional(),
                                        fieldWithPath("result.[].bhourTue").type(JsonFieldType.NUMBER)
                                                .description("화요일 포함유무 1 - 포함, 0 - 미포함").optional(),
                                        fieldWithPath("result.[].bhourWed").type(JsonFieldType.NUMBER)
                                                .description("수요일 포함유무 1 - 포함, 0 - 미포함").optional(),
                                        fieldWithPath("result.[].bhourThu").type(JsonFieldType.NUMBER)
                                                .description("목요일 포함유무 1 - 포함, 0 - 미포함").optional(),
                                        fieldWithPath("result.[].bhourFri").type(JsonFieldType.NUMBER)
                                                .description("금요일 포함유무 1 - 포함, 0 - 미포함").optional(),
                                        fieldWithPath("result.[].bhourSat").type(JsonFieldType.NUMBER)
                                                .description("토요일 포함유무 1 - 포함, 0 - 미포함").optional(),
                                        fieldWithPath("result.[].bhourSun").type(JsonFieldType.NUMBER)
                                                .description("일요일 포함유무 1 - 포함, 0 - 미포함").optional(),
                                        fieldWithPath("result.[].bhourStartTime").type(JsonFieldType.STRING)
                                                .description("시작시간").optional(),
                                        fieldWithPath("result.[].bhourEndTime").type(JsonFieldType.STRING)
                                                .description("종료시간").optional(),
                                        fieldWithPath("result.[].bhourEtc").type(JsonFieldType.STRING)
                                                .description("기타정보 ex) 연중휴무").optional(),
                                        fieldWithPath("timestamp").type(JsonFieldType.STRING)
                                                .description("api 호출 일시")
                                )
                        ));
    }

}
