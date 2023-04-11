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
    @WithMockUser(value = "user", authorities = "USER")
    @Test
    public void testGetHome() throws Exception {
        mockMvc.perform(get("/ruleName/list")).andExpect(status().isOk());
    }


    /* ------- addRatingForm() ------- */
    @WithMockUser(value = "user", authorities = "USER")
    @Test
    public void testGetAddRatingForm() throws Exception {
        mockMvc.perform(get("/ruleName/add")).andExpect(status().isOk());
    }


    /* ------- validate() ------- */
    @WithMockUser(value = "user", authorities = "USER")
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

    @WithMockUser(value = "user", authorities = "USER")
    @Test
    public void testPostValidate_WithEmptyFields() throws Exception {
        mockMvc.perform(post("/ruleName/validate")
                        .param("name", "")
                        .param("description", "")
                        .param("json", "")
                        .param("template", "")
                        .param("sqlStr", "")
                        .param("sqlPart", "")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeHasFieldErrorCode("ruleName", "name", "NotBlank"))
                .andExpect(model().attributeHasFieldErrorCode("ruleName", "description", "NotBlank"))
                .andExpect(model().attributeHasFieldErrorCode("ruleName", "json", "NotBlank"))
                .andExpect(model().attributeHasFieldErrorCode("ruleName", "template", "NotBlank"))
                .andExpect(model().attributeHasFieldErrorCode("ruleName", "sqlStr", "NotBlank"))
                .andExpect(model().attributeHasFieldErrorCode("ruleName", "sqlPart", "NotBlank"));
    }


    /* ------- showUpdateForm() ------- */
    @WithMockUser(value = "user", authorities = "USER")
    @Test
    public void testGetshowUpdateForm() throws Exception {
        mockMvc.perform(get("/ruleName/update/100")).andExpect(status().isOk());
    }

    @WithMockUser(value = "user", authorities = "USER")
    @Test
    public void testGetshowUpdateForm_InvalidRuleNameId() throws Exception {
        mockMvc.perform(get("/ruleName/update/1001"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/app/error"));
    }


    /* ------- updateRuleName() ------- */
    @WithMockUser(value = "user", authorities = "USER")
    @Test
    public void testPostUpdateRuleName() throws Exception {
        mockMvc.perform(post("/ruleName/update/100")
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

    @WithMockUser(value = "user", authorities = "USER")
    @Test
    public void testPostUpdateRuleName_WithEmptyFields() throws Exception {
        mockMvc.perform(post("/ruleName/update/100")
                        .param("name", "")
                        .param("description", "")
                        .param("json", "")
                        .param("template", "")
                        .param("sqlStr", "")
                        .param("sqlPart", "")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeHasFieldErrorCode("ruleName", "name", "NotBlank"))
                .andExpect(model().attributeHasFieldErrorCode("ruleName", "description", "NotBlank"))
                .andExpect(model().attributeHasFieldErrorCode("ruleName", "json", "NotBlank"))
                .andExpect(model().attributeHasFieldErrorCode("ruleName", "template", "NotBlank"))
                .andExpect(model().attributeHasFieldErrorCode("ruleName", "sqlStr", "NotBlank"))
                .andExpect(model().attributeHasFieldErrorCode("ruleName", "sqlPart", "NotBlank"));
    }


    /* ------- deleteRuleName() ------- */
    @WithMockUser(value = "user", authorities = "USER")
    @Test
    public void testGetDeleteRuleName() throws Exception {
        mockMvc.perform(get("/ruleName/delete/100"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/ruleName/list"));
    }

    @WithMockUser(value = "user", authorities = "USER")
    @Test
    public void testGetDelete_InvalidRuleNameId() throws Exception {
        mockMvc.perform(get("/ruleName/delete/1001"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/app/error"));
    }
}
