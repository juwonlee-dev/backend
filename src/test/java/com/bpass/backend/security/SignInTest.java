package com.bpass.backend.security;

import com.bpass.backend.api.common.BaseControllerTest;
import com.bpass.backend.security.model.UserRole;
import com.bpass.backend.security.request.SignInRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("인증 테스트")
class SignInTest extends BaseControllerTest {

    @Test
    @DisplayName("토큰 발급 받기(성공)")
    void signInSuccess() throws Exception {
        this.accountFactory.generateUser(1);
        SignInRequest signInRequest = SignInRequest.builder()
                .id("TestUser1")
                .password("password")
                .role(UserRole.ROLE_USER)
                .build();

        this.mockMvc.perform(RestDocumentationRequestBuilders.post("/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(signInRequest)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("signin"));
    }

    @Test
    @DisplayName("토큰 발급 받기(실패)")
    void signInFailBecauseUserNotFound() throws Exception {
        SignInRequest signInRequest = SignInRequest.builder()
                .id("TestUser1")
                .password("password")
                .build();

        this.mockMvc.perform(RestDocumentationRequestBuilders.post("/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(signInRequest)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("error").value("0002"))
                .andDo(document("0002"))
                .andDo(print());
    }
}