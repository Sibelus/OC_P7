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

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
public class LoginControllerTest {

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


    /* ------- login() ------- */
    @WithMockUser(username = "admin")
    @Test
    public void testGetLogin() throws Exception {
        mockMvc.perform(get("/login")).andExpect(status().isOk());
    }


    /* ------- getAllUserArticles() ------- */
    @WithMockUser(username = "admin")
    @Test
    public void testGetGetAllUserArticles() throws Exception {
        mockMvc.perform(get("/app/secure/article-details")).andExpect(status().isOk());
    }

    /* ------- error() ------- */
    @WithMockUser(username = "admin")
    @Test
    public void testGetError() throws Exception {
        mockMvc.perform(get("/app/error")).andExpect(status().isOk());
    }
}
