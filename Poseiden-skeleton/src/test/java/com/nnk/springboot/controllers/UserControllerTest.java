package com.nnk.springboot.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserControllerTest {

    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }


    /* ------- home() ------- */
    @WithMockUser(username = "admin", password = "admin", authorities = "ADMIN")
    @Test
    public void testGetHome() throws Exception {
        mockMvc.perform(get("/user/list")).andExpect(status().isOk());
    }

    @WithMockUser(username = "user", password = "user", authorities = "USER")
    @Test
    public void testGetHome_WithWrongUserAuthorities() throws Exception {
        mockMvc.perform(get("/user/list"))
                .andExpect(status().is4xxClientError());
    }


    /* ------- addUser() ------- */
    @WithMockUser(username = "admin", password = "admin", authorities = "ADMIN")
    @Test
    public void testGetAddUser() throws Exception {
        mockMvc.perform(get("/user/add")).andExpect(status().isOk());
    }

    @WithMockUser(username = "user", password = "user", authorities = "USER")
    @Test
    public void testGetAddUser_WithWrongUserAuthorities() throws Exception {
        mockMvc.perform(get("/user/add"))
                .andExpect(status().is4xxClientError());
    }


    /* ------- validate() ------- */
    @WithMockUser(username = "admin", password = "admin", authorities = "ADMIN")
    @Test
    public void testPostValidate() throws Exception {
        mockMvc.perform(post("/user/validate")
                        .param("fullname", "fullname")
                        .param("username", "username")
                        .param("password", "Password123%")
                        .param("role", "ADMIN")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/user/list"));
    }

    @WithMockUser(username = "admin", password = "admin", authorities = "ADMIN")
    @Test
    public void testPostValidate_WithEmptyFullname() throws Exception {
        mockMvc.perform(post("/user/validate")
                        .param("fullname", "")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeHasFieldErrorCode("user", "fullname", "NotBlank"));
    }

    @WithMockUser(username = "admin", password = "admin", authorities = "ADMIN")
    @Test
    public void testPostValidate_WithEmptyUsername() throws Exception {
        mockMvc.perform(post("/user/validate")
                        .param("fullname", "fullname")
                        .param("username", "")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeHasFieldErrorCode("user", "username", "NotBlank"));
    }


    @WithMockUser(username = "admin", password = "admin", authorities = "ADMIN")
    @Test
    public void testPostValidate_WithEmptyPassword() throws Exception {
        mockMvc.perform(post("/user/validate")
                        .param("fullname", "fullname")
                        .param("username", "username")
                        .param("password", "")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeHasFieldErrorCode("user", "password", "Pattern"));
    }

    @WithMockUser(username = "admin", password = "admin", authorities = "ADMIN")
    @Test
    public void testPostValidate_WithPasswordWithoutSpecialchar() throws Exception {
        mockMvc.perform(post("/user/validate")
                        .param("fullname", "fullname")
                        .param("username", "username")
                        .param("password", "Password1")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeHasFieldErrorCode("user", "password", "Pattern"));
    }

    @WithMockUser(username = "admin", password = "admin", authorities = "ADMIN")
    @Test
    public void testPostValidate_WithPasswordWithoutUpperCase() throws Exception {
        mockMvc.perform(post("/user/validate")
                        .param("fullname", "fullname")
                        .param("username", "username")
                        .param("password", "password1$")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeHasFieldErrorCode("user", "password", "Pattern"));
    }

    @WithMockUser(username = "admin", password = "admin", authorities = "ADMIN")
    @Test
    public void testPostValidate_WithPasswordWithoutNumber() throws Exception {
        mockMvc.perform(post("/user/validate")
                        .param("fullname", "fullname")
                        .param("username", "username")
                        .param("password", "Password$")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeHasFieldErrorCode("user", "password", "Pattern"));
    }

    @WithMockUser(username = "admin", password = "admin", authorities = "ADMIN")
    @Test
    public void testPostValidate_WithPasswordWithoutCorrectLength() throws Exception {
        mockMvc.perform(post("/user/validate")
                        .param("fullname", "fullname")
                        .param("username", "username")
                        .param("password", "Pass$1")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeHasFieldErrorCode("user", "password", "Pattern"));
    }

    @WithMockUser(username = "admin", password = "admin", authorities = "ADMIN")
    @Test
    public void testPostValidate_WithEmptyRole() throws Exception {
        mockMvc.perform(post("/user/validate")
                        .param("fullname", "fullname")
                        .param("username", "username")
                        .param("password", "Password123")
                        .param("role", "")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeHasFieldErrorCode("user", "role", "NotBlank"));
    }


    /* ------- showUpdateForm() ------- */
    @WithMockUser(username = "admin", password = "admin", authorities = "ADMIN")
    @Test
    public void testGetShowUpdateForm() throws Exception {
        mockMvc.perform(get("/user/update/100")).andExpect(status().isOk());
    }

    @WithMockUser(username = "user", password = "user", authorities = "USER")
    @Test
    public void testGetShowUpdateForm_WithWrongUserAuthorities() throws Exception {
        mockMvc.perform(get("/user/update/100"))
                .andExpect(status().is4xxClientError());
    }

    @WithMockUser(username = "admin", password = "admin", authorities = "ADMIN")
    @Test
    public void testGetshowUpdateForm_InvalidUserId() throws Exception {
        mockMvc.perform(get("/user/update/1001"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/app/error"));
    }


    /* ------- updateUser() ------- */
    @WithMockUser(username = "admin", password = "admin", authorities = "ADMIN")
    @Test
    public void testPostUpdateUser() throws Exception {
        mockMvc.perform(post("/user/update/100")
                        .param("fullname", "fullname")
                        .param("username", "username")
                        .param("password", "Password123%")
                        .param("role", "ADMIN")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/user/list"));
    }

    @WithMockUser(username = "admin", password = "admin", authorities = "ADMIN")
    @Test
    public void testPostUpdateUser_WithEmptyFullname() throws Exception {
        mockMvc.perform(post("/user/update/100")
                        .param("fullname", "")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeHasFieldErrorCode("user", "fullname", "NotBlank"));
    }

    @WithMockUser(username = "admin", password = "admin", authorities = "ADMIN")
    @Test
    public void testPostUpdateUser_WithEmptyUsername() throws Exception {
        mockMvc.perform(post("/user/update/100")
                        .param("fullname", "fullname")
                        .param("username", "")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeHasFieldErrorCode("user", "username", "NotBlank"));
    }


    @WithMockUser(username = "admin", password = "admin", authorities = "ADMIN")
    @Test
    public void testPostUpdateUser_WithEmptyPassword() throws Exception {
        mockMvc.perform(post("/user/update/100")
                        .param("fullname", "fullname")
                        .param("username", "username")
                        .param("password", "")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeHasFieldErrorCode("user", "password", "Pattern"));
    }

    @WithMockUser(username = "admin", password = "admin", authorities = "ADMIN")
    @Test
    public void testPostUpdateUser_WithPasswordWithoutSpecialchar() throws Exception {
        mockMvc.perform(post("/user/update/100")
                        .param("fullname", "fullname")
                        .param("username", "username")
                        .param("password", "Password1")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeHasFieldErrorCode("user", "password", "Pattern"));
    }

    @WithMockUser(username = "admin", password = "admin", authorities = "ADMIN")
    @Test
    public void testPostUpdateUser_WithPasswordWithoutUpperCase() throws Exception {
        mockMvc.perform(post("/user/update/100")
                        .param("fullname", "fullname")
                        .param("username", "username")
                        .param("password", "password1$")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeHasFieldErrorCode("user", "password", "Pattern"));
    }

    @WithMockUser(username = "admin", password = "admin", authorities = "ADMIN")
    @Test
    public void testPostUpdateUser_WithPasswordWithoutNumber() throws Exception {
        mockMvc.perform(post("/user/update/100")
                        .param("fullname", "fullname")
                        .param("username", "username")
                        .param("password", "Password$")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeHasFieldErrorCode("user", "password", "Pattern"));
    }

    @WithMockUser(username = "admin", password = "admin", authorities = "ADMIN")
    @Test
    public void testPostUpdateUser_WithPasswordWithoutCorrectLength() throws Exception {
        mockMvc.perform(post("/user/update/100")
                        .param("fullname", "fullname")
                        .param("username", "username")
                        .param("password", "Pass$1")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeHasFieldErrorCode("user", "password", "Pattern"));
    }

    @WithMockUser(username = "admin", password = "admin", authorities = "ADMIN")
    @Test
    public void testPostUpdateUser_WithEmptyRole() throws Exception {
        mockMvc.perform(post("/user/update/100")
                        .param("fullname", "fullname")
                        .param("username", "username")
                        .param("password", "Password123")
                        .param("role", "")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeHasFieldErrorCode("user", "role", "NotBlank"));
    }


    /* ------- deleteUser() ------- */
    @WithMockUser(username = "admin", password = "admin", authorities = "ADMIN")
    @Test
    public void testGetDeleteUser() throws Exception {
        mockMvc.perform(get("/user/delete/100"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/user/list"));
    }

    @WithMockUser(username = "admin", password = "admin", authorities = "ADMIN")
    @Test
    public void testGetDelete_InvalidUserId() throws Exception {
        mockMvc.perform(get("/user/delete/1001"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/app/error"));
    }

    @WithMockUser(username = "user", password = "user", authorities = "USER")
    @Test
    public void testGetDeleteUser_WithWrongUserAuthorities() throws Exception {
        mockMvc.perform(get("/user/delete/100"))
                .andExpect(status().is4xxClientError());
    }
}
