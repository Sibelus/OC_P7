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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
public class RuleNameControllerTest {

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
        mockMvc.perform(get("/ruleName/list")).andExpect(status().isOk());
    }


    /* ------- addRatingForm() ------- */
    @WithMockUser(value = "admin")
    @Test
    public void testGetAddRatingForm() throws Exception {
        mockMvc.perform(get("/ruleName/add")).andExpect(status().isOk());
    }


    /* ------- validate() ------- */
    @WithMockUser(value = "admin")
    @Test
    public void testPostValidate() throws Exception {
        mockMvc.perform(post("/ruleName/validate")
                        .param("name", "name")
                        .param("description", "description")
                        .param("json", "json")
                        .param("template", "template")
                        .param("sqlStr", "sqlStr")
                        .param("sqlPart", "sqlPart")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/ruleName/list"));
    }


    /* ------- showUpdateForm() ------- */
    @WithMockUser(value = "admin")
    @Test
    public void testGetshowUpdateForm() throws Exception {
        mockMvc.perform(get("/ruleName/update/100")).andExpect(status().isOk());
    }


    /* ------- updateRuleName() ------- */
    @WithMockUser(value = "admin")
    @Test
    public void testPostUpdateRuleName() throws Exception {
        mockMvc.perform(post("/ruleName/update/100")
                        .param("moodysRating", "moodysRating")
                        .param("sandPRating", "sandPRating")
                        .param("fitchRating", "fitchRating")
                        .param("orderNumber", "42")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/ruleName/list"));
    }


    /* ------- deleteRuleName() ------- */
    @WithMockUser(value = "admin")
    @Test
    public void testGetDeleteRuleName() throws Exception {
        mockMvc.perform(get("/ruleName/delete/100"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/ruleName/list"));
    }
}
