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
    @WithMockUser(value = "admin")
    @Test
    public void testGetHome() throws Exception {
        mockMvc.perform(get("/rating/list")).andExpect(status().isOk());
    }


    /* ------- addRatingForm() ------- */
    @WithMockUser(value = "admin")
    @Test
    public void testGetAddRatingForm() throws Exception {
        mockMvc.perform(get("/rating/add")).andExpect(status().isOk());
    }


    /* ------- validate() ------- */
    @WithMockUser(value = "admin")
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

    @WithMockUser(value = "admin")
    @Test
    public void testPostValidate_WithEmptyOrderNumber() throws Exception {
        mockMvc.perform(post("/rating/validate")
                        .param("moodysRating", "moodysRating")
                        .param("sandPRating", "sandPRating")
                        .param("fitchRating", "fitchRating")
                        .param("orderNumber", "")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeHasFieldErrorCode("rating", "orderNumber", "typeMismatch"));
    }

    @WithMockUser(value = "admin")
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
    @WithMockUser(value = "admin")
    @Test
    public void testGetshowUpdateForm() throws Exception {
        mockMvc.perform(get("/rating/update/100")).andExpect(status().isOk());
    }


    /* ------- updateRating() ------- */
    @WithMockUser(value = "admin")
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

    @WithMockUser(value = "admin")
    @Test
    public void testPostUpdateRating_WithEmptyOrderNumber() throws Exception {
        mockMvc.perform(post("/rating/update/100")
                        .param("moodysRating", "moodysRating")
                        .param("sandPRating", "sandPRating")
                        .param("fitchRating", "fitchRating")
                        .param("orderNumber", "")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeHasFieldErrorCode("rating", "orderNumber", "typeMismatch"));
    }

    @WithMockUser(value = "admin")
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
    @WithMockUser(value = "admin")
    @Test
    public void testGetDeleteRating() throws Exception {
        mockMvc.perform(get("/rating/delete/100"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/rating/list"));
    }
}
