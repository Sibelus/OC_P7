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
public class CurvePointControllerTest {

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
        mockMvc.perform(get("/curvePoint/list")).andExpect(status().isOk());
    }


    /* ------- addBidForm() ------- */
    @WithMockUser(value = "admin")
    @Test
    public void testGetAddBidForm() throws Exception {
        mockMvc.perform(get("/curvePoint/add")).andExpect(status().isOk());
    }


    /* ------- validate() ------- */
    @WithMockUser(value = "admin")
    @Test
    public void testPostValidate() throws Exception {
        mockMvc.perform(post("/curvePoint/validate")
                        .param("curveId", "10")
                        .param("term", "11")
                        .param("value", "12")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/curvePoint/list"));
    }

    @WithMockUser(value = "admin")
    @Test
    public void testPostValidate_WithEmptyCurveId() throws Exception {
        mockMvc.perform(post("/curvePoint/validate")
                        .param("curveId", "")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeHasFieldErrorCode("curvePoint", "curveId", "typeMismatch"));
    }

    @WithMockUser(value = "admin")
    @Test
    public void testPostValidate_WithLetterCurveId() throws Exception {
        mockMvc.perform(post("/curvePoint/validate")
                        .param("curveId", "ten")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeHasFieldErrorCode("curvePoint", "curveId", "typeMismatch"));
    }

    @WithMockUser(value = "admin")
    @Test
    public void testPostValidate_WithEmptyTerm() throws Exception {
        mockMvc.perform(post("/curvePoint/validate")
                        .param("curveId", "10")
                        .param("term", "")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeHasFieldErrorCode("curvePoint", "term", "typeMismatch"));
    }

    @WithMockUser(value = "admin")
    @Test
    public void testPostValidate_WithLetterTerm() throws Exception {
        mockMvc.perform(post("/curvePoint/validate")
                        .param("curveId", "10")
                        .param("term", "eleven")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeHasFieldErrorCode("curvePoint", "term", "typeMismatch"));
    }

    @WithMockUser(value = "admin")
    @Test
    public void testPostValidate_WithEmptyValue() throws Exception {
        mockMvc.perform(post("/curvePoint/validate")
                        .param("curveId", "10")
                        .param("term", "11")
                        .param("value", "")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeHasFieldErrorCode("curvePoint", "value", "typeMismatch"));
    }

    @WithMockUser(value = "admin")
    @Test
    public void testPostValidate_WithLetteryValue() throws Exception {
        mockMvc.perform(post("/curvePoint/validate")
                        .param("curveId", "10")
                        .param("term", "11")
                        .param("value", "twelve")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeHasFieldErrorCode("curvePoint", "value", "typeMismatch"));
    }


    /* ------- showUpdateForm() ------- */
    @WithMockUser(value = "admin")
    @Test
    public void testGetshowUpdateForm() throws Exception {
        mockMvc.perform(get("/curvePoint/update/100")).andExpect(status().isOk());
    }


    /* ------- updateCurvePoint() ------- */
    @WithMockUser(value = "admin")
    @Test
    public void testPostUpdateCurvePoint() throws Exception {
        mockMvc.perform(post("/curvePoint/update/100")
                        .param("curveId", "10")
                        .param("term", "11")
                        .param("value", "12")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/curvePoint/list"));
    }

    @WithMockUser(value = "admin")
    @Test
    public void testPostUpdateCurvePoint_WithEmptyCurveId() throws Exception {
        mockMvc.perform(post("/curvePoint/update/100")
                        .param("curveId", "")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeHasFieldErrorCode("curvePoint", "curveId", "typeMismatch"));
    }

    @WithMockUser(value = "admin")
    @Test
    public void testPostUpdateCurvePoint_WithLetterCurveId() throws Exception {
        mockMvc.perform(post("/curvePoint/update/100")
                        .param("curveId", "ten")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeHasFieldErrorCode("curvePoint", "curveId", "typeMismatch"));
    }

    @WithMockUser(value = "admin")
    @Test
    public void testPostUpdateCurvePoint_WithEmptyTerm() throws Exception {
        mockMvc.perform(post("/curvePoint/update/100")
                        .param("curveId", "10")
                        .param("term", "")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeHasFieldErrorCode("curvePoint", "term", "typeMismatch"));
    }

    @WithMockUser(value = "admin")
    @Test
    public void testPostUpdateCurvePoint_WithLetterTerm() throws Exception {
        mockMvc.perform(post("/curvePoint/update/100")
                        .param("curveId", "10")
                        .param("term", "eleven")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeHasFieldErrorCode("curvePoint", "term", "typeMismatch"));
    }

    @WithMockUser(value = "admin")
    @Test
    public void testPostUpdateCurvePoint_WithEmptyValue() throws Exception {
        mockMvc.perform(post("/curvePoint/update/100")
                        .param("curveId", "10")
                        .param("term", "11")
                        .param("value", "")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeHasFieldErrorCode("curvePoint", "value", "typeMismatch"));
    }

    @WithMockUser(value = "admin")
    @Test
    public void testPostUpdateCurvePoint_WithLetterValue() throws Exception {
        mockMvc.perform(post("/curvePoint/update/100")
                        .param("curveId", "10")
                        .param("term", "11")
                        .param("value", "twelve")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeHasFieldErrorCode("curvePoint", "value", "typeMismatch"));
    }


    /* ------- deleteBid() ------- */
    @WithMockUser(value = "admin")
    @Test
    public void testGetDeleteBid() throws Exception {
        mockMvc.perform(get("/curvePoint/delete/100"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/curvePoint/list"));
    }
}
