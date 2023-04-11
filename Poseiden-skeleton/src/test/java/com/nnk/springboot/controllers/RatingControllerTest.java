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
public class RatingControllerTest {

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
        mockMvc.perform(get("/rating/list")).andExpect(status().isOk());
    }


    /* ------- addRatingForm() ------- */
    @WithMockUser(value = "user", authorities = "USER")
    @Test
    public void testGetAddRatingForm() throws Exception {
        mockMvc.perform(get("/rating/add")).andExpect(status().isOk());
    }


    /* ------- validate() ------- */
    @WithMockUser(value = "user", authorities = "USER")
    @Test
    public void testPostValidate() throws Exception {
        mockMvc.perform(post("/rating/validate")
                        .param("moodysRating", "moodysRating")
                        .param("sandPRating", "sandPRating")
                        .param("fitchRating", "fitchRating")
                        .param("orderNumber", "42")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/rating/list"));
    }

    @WithMockUser(value = "user", authorities = "USER")
    @Test
    public void testPostValidate_WithEmptyFields() throws Exception {
        mockMvc.perform(post("/rating/validate")
                        .param("moodysRating", "")
                        .param("sandPRating", "")
                        .param("fitchRating", "")
                        .param("orderNumber", "")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeHasFieldErrorCode("rating", "moodysRating", "NotBlank"))
                .andExpect(model().attributeHasFieldErrorCode("rating", "sandPRating", "NotBlank"))
                .andExpect(model().attributeHasFieldErrorCode("rating", "fitchRating", "NotBlank"))
                .andExpect(model().attributeHasFieldErrorCode("rating", "orderNumber", "typeMismatch"));
    }

    @WithMockUser(value = "user", authorities = "USER")
    @Test
    public void testPostValidate_WithLetterOrderNumber() throws Exception {
        mockMvc.perform(post("/rating/validate")
                        .param("moodysRating", "moodysRating")
                        .param("sandPRating", "sandPRating")
                        .param("fitchRating", "fitchRating")
                        .param("orderNumber", "forty-two")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeHasFieldErrorCode("rating", "orderNumber", "typeMismatch"));
    }


    /* ------- showUpdateForm() ------- */
    @WithMockUser(value = "user", authorities = "USER")
    @Test
    public void testGetshowUpdateForm() throws Exception {
        mockMvc.perform(get("/rating/update/100")).andExpect(status().isOk());
    }

    @WithMockUser(value = "user", authorities = "USER")
    @Test
    public void testGetshowUpdateForm_InvalidRatingId() throws Exception {
        mockMvc.perform(get("/rating/update/1001"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/app/error"));
    }


    /* ------- updateRating() ------- */
    @WithMockUser(value = "user", authorities = "USER")
    @Test
    public void testPostUpdateRating() throws Exception {
        mockMvc.perform(post("/rating/update/100")
                        .param("moodysRating", "moodysRating")
                        .param("sandPRating", "sandPRating")
                        .param("fitchRating", "fitchRating")
                        .param("orderNumber", "42")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/rating/list"));
    }

    @WithMockUser(value = "user", authorities = "USER")
    @Test
    public void testPostUpdateRating_WithEmptyFields() throws Exception {
        mockMvc.perform(post("/rating/update/100")
                        .param("moodysRating", "")
                        .param("sandPRating", "")
                        .param("fitchRating", "")
                        .param("orderNumber", "")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeHasFieldErrorCode("rating", "moodysRating", "NotBlank"))
                .andExpect(model().attributeHasFieldErrorCode("rating", "sandPRating", "NotBlank"))
                .andExpect(model().attributeHasFieldErrorCode("rating", "fitchRating", "NotBlank"))
                .andExpect(model().attributeHasFieldErrorCode("rating", "orderNumber", "typeMismatch"));
    }

    @WithMockUser(value = "user", authorities = "USER")
    @Test
    public void testPostUpdateRating_WithLetterOrderNumber() throws Exception {
        mockMvc.perform(post("/rating/update/100")
                        .param("moodysRating", "moodysRating")
                        .param("sandPRating", "sandPRating")
                        .param("fitchRating", "fitchRating")
                        .param("orderNumber", "forty-two")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeHasFieldErrorCode("rating", "orderNumber", "typeMismatch"));
    }


    /* ------- deleteBid() ------- */
    @WithMockUser(value = "user", authorities = "USER")
    @Test
    public void testGetDeleteRating() throws Exception {
        mockMvc.perform(get("/rating/delete/100"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/rating/list"));
    }

    @WithMockUser(value = "user", authorities = "USER")
    @Test
    public void testGetDelete_InvalidRatingId() throws Exception {
        mockMvc.perform(get("/rating/delete/1001"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/app/error"));
    }
}
