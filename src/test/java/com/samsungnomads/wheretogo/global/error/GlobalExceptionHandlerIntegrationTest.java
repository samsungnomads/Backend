package com.samsungnomads.wheretogo.global.error;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 글로벌 예외 처리 통합 테스트
 * 🧪 실제 HTTP 요청 시나리오에서 예외 처리 동작 검증
 */
@WebMvcTest(TestExceptionController.class)
@Import(GlobalExceptionHandler.class)
class GlobalExceptionHandlerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("비즈니스 예외 발생시 적절한 오류 응답을 반환한다")
    void handleBusinessException() throws Exception {
        // when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/test/business-exception"))
                .andDo(print()) // 🔍 응답 내용 출력하여 디버깅 용이하게 함
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is("C001")))
                .andExpect(jsonPath("$.message", is("잘못된 입력값입니다")))
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors").isEmpty());
    }

    @Test
    @DisplayName("엔티티 조회 실패 예외 발생시 적절한 오류 응답을 반환한다")
    void handleEntityNotFoundException() throws Exception {
        // when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/test/entity-not-found/123"))
                .andDo(print()) // 🔍 응답 내용 출력하여 디버깅 용이하게 함
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code", is("M002")))
                .andExpect(jsonPath("$.message", is("회원을 찾을 수 없습니다")))
                .andExpect(jsonPath("$.status", is(404)));
    }

    @Test
    @DisplayName("처리되지 않은 예외 발생시 서버 오류 응답을 반환한다")
    void handleUnexpectedException() throws Exception {
        // when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/test/unexpected-exception"))
                .andDo(print()) // 🔍 응답 내용 출력하여 디버깅 용이하게 함
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.code", is("C004")))
                .andExpect(jsonPath("$.message", is("서버 내부 오류가 발생했습니다")))
                .andExpect(jsonPath("$.status", is(500)));
    }
} 