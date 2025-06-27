package com.company.controller;

import com.company.config.SecurityConfig;
import com.company.dto.UserRegisterDTO;
import com.company.repository.UserRepository;
import com.company.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SignupController.class)
@Import(SecurityConfig.class)
class SignupControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @MockitoBean
    private UserService userService;

    @MockitoBean
    private UserRepository userRepository;
    @Test
    void testGetSignUpReturnsRegisterViewWithModel() throws Exception {
        mockMvc.perform(get("/signup"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    void testPostSignUpWithValidDataRedirectsToSignup() throws Exception {
        mockMvc.perform(post("/signup")
                        .param("username", "testuser")
                        .param("password", "Password123")
                        .param("fullName", "TestU"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
        ArgumentCaptor<UserRegisterDTO> captor = ArgumentCaptor.forClass(UserRegisterDTO.class);
        verify(userService).save(captor.capture());
        assertEquals("testuser", captor.getValue().getUsername());
    }

    @Test
    void testPostSignUpWithInvalidDataReturnsRegisterView() throws Exception {
        mockMvc.perform(post("/signup")
                        .param("username", "")
                        .param("password", "123")
                        .param("fullName", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attributeHasFieldErrors("user",
                        "username", "fullName"));

        verify(userService, never()).save(any());
    }
}