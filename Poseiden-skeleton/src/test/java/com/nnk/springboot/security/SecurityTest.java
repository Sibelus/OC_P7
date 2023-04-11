package com.nnk.springboot.security;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SecurityTest {

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

    @Test
    public void shouldReturnDefaultMessage() throws Exception {
        mockMvc.perform(get("/login")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void testUserLogin() throws Exception {
        mockMvc.perform(formLogin("/login").user("admin").password("admin")).andExpect(authenticated());
    }

    @Test
    public void testUserLoginFailed_WithWrongPassword() throws Exception {
        mockMvc.perform(formLogin("/login").user("admin").password("wrongpassword")).andExpect(unauthenticated());
    }

    @Test
    public void testUserLoginFailed_WithWrongUsername() throws Exception {
        mockMvc.perform(formLogin("/login").user("wrongadmin").password("password")).andExpect(unauthenticated());
    }

    @Test
    public void testUserLoginFailed_WithEmptyUsername() throws Exception {
        mockMvc.perform(formLogin("/login").user("").password("password"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/login?error"));
    }

    @Test
    public void testUserLoginFailed_WithEmptyPassword() throws Exception {
        mockMvc.perform(formLogin("/login").user("admin").password(""))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/login?error"));
    }
}
