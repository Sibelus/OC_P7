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
    @WithMockUser(value = "admin")
    @Test
    public void testGetHome() throws Exception {
        mockMvc.perform(get("/user/list")).andExpect(status().isOk());
    }


    /* ------- addUser() ------- */
    @WithMockUser(value = "admin")
    @Test
    public void testGetAddUser() throws Exception {
        mockMvc.perform(get("/user/add")).andExpect(status().isOk());
    }


    /* ------- validate() ------- */
    @WithMockUser(value = "admin")
    @Test
    public void testPostValidate() throws Exception {
        mockMvc.perform(post("/user/validate")
                        .param("fullname", "fullname")
                        .param("username", "username")
                        .param("password", "Password123")
                        .param("role", "ADMIN")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful());
    }

    @WithMockUser(value = "admin")
    @Test
    public void testPostValidate_WithEmptyFullname() throws Exception {
        mockMvc.perform(post("/user/validate")
                        .param("fullname", "")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeHasFieldErrorCode("user", "fullname", "NotBlank"));
    }

    @WithMockUser(value = "admin")
    @Test
    public void testPostValidate_WithEmptyUsername() throws Exception {
        mockMvc.perform(post("/user/validate")
                        .param("fullname", "fullname")
                        .param("username", "")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeHasFieldErrorCode("user", "username", "NotBlank"));
    }


    @WithMockUser(value = "admin")
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

    @WithMockUser(value = "admin")
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

    @WithMockUser(value = "admin")
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

    @WithMockUser(value = "admin")
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

    @WithMockUser(value = "admin")
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

    @WithMockUser(value = "admin")
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
    @WithMockUser(value = "admin")
    @Test
    public void testGetShowUpdateForm() throws Exception {
        mockMvc.perform(get("/user/update/100")).andExpect(status().isOk());
    }


    /* ------- updateUser() ------- */
    @WithMockUser(value = "admin")
    @Test
    public void testPostUpdateUser() throws Exception {
        mockMvc.perform(post("/user/update/100")
                        .param("fullname", "fullname")
                        .param("username", "username")
                        .param("password", "Password123")
                        .param("role", "ADMIN")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful());
    }

    @WithMockUser(value = "admin")
    @Test
    public void testPostUpdateUser_WithEmptyFullname() throws Exception {
        mockMvc.perform(post("/user/update/100")
                        .param("fullname", "")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeHasFieldErrorCode("user", "fullname", "NotBlank"));
    }

    @WithMockUser(value = "admin")
    @Test
    public void testPostUpdateUser_WithEmptyUsername() throws Exception {
        mockMvc.perform(post("/user/update/100")
                        .param("fullname", "fullname")
                        .param("username", "")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeHasFieldErrorCode("user", "username", "NotBlank"));
    }


    @WithMockUser(value = "admin")
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

    @WithMockUser(value = "admin")
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

    @WithMockUser(value = "admin")
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

    @WithMockUser(value = "admin")
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

    @WithMockUser(value = "admin")
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

    @WithMockUser(value = "admin")
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
    @WithMockUser(value = "admin")
    @Test
    public void testGetDeleteUser() throws Exception {
        mockMvc.perform(get("/user/delete/100"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/user/list"));
    }
}
