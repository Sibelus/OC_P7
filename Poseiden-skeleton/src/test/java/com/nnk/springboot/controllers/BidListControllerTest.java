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
public class BidListControllerTest {

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
        mockMvc.perform(get("/bidList/list")).andExpect(status().isOk());
    }


    /* ------- addBidForm() ------- */
    @WithMockUser(value = "user", authorities = "USER")
    @Test
    public void testGetAddBidForm() throws Exception {
        mockMvc.perform(get("/bidList/add")).andExpect(status().isOk());
    }


    /* ------- validate() ------- */
    @WithMockUser(value = "user", authorities = "USER")
    @Test
    public void testPostValidate() throws Exception {
        mockMvc.perform(post("/bidList/validate")
                        .param("account", "account")
                        .param("type", "type")
                        .param("bidQuantity", "12")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/bidList/list"));
    }

    @WithMockUser(value = "user", authorities = "USER")
    @Test
    public void testPostValidate_WithEmptyAccount() throws Exception {
        mockMvc.perform(post("/bidList/validate")
                        .param("account", "")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeHasFieldErrorCode("bidList", "account", "NotBlank"));
    }

    @WithMockUser(value = "user", authorities = "USER")
    @Test
    public void testPostValidate_WithEmptyType() throws Exception {
        mockMvc.perform(post("/bidList/validate")
                        .param("account", "account")
                        .param("type", "")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeHasFieldErrorCode("bidList", "type", "NotBlank"));
    }

    @WithMockUser(value = "user", authorities = "USER")
    @Test
    public void testPostValidate_WithEmptyBidQuantity() throws Exception {
        mockMvc.perform(post("/bidList/validate")
                        .param("account", "account")
                        .param("type", "type")
                        .param("bidQuantity", "")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeHasFieldErrorCode("bidList", "bidQuantity", "typeMismatch"));
    }

    @WithMockUser(value = "user", authorities = "USER")
    @Test
    public void testPostValidate_WithLettersBidQuantity() throws Exception {
        mockMvc.perform(post("/bidList/validate")
                        .param("account", "account")
                        .param("type", "type")
                        .param("bidQuantity", "twenty two")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeHasFieldErrorCode("bidList", "bidQuantity", "typeMismatch"));
    }


    /* ------- showUpdateForm() ------- */
    @WithMockUser(value = "user", authorities = "USER")
    @Test
    public void testGetshowUpdateForm() throws Exception {
        mockMvc.perform(get("/bidList/update/100")).andExpect(status().isOk());
    }

    @WithMockUser(value = "user", authorities = "USER")
    @Test
    public void testGetshowUpdateForm_InvalidBidListId() throws Exception {
        mockMvc.perform(get("/bidList/update/1001"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/app/error"));
    }


    /* ------- updateBid() ------- */
    @WithMockUser(value = "user", authorities = "USER")
    @Test
    public void testPostUpdateBid() throws Exception {
        mockMvc.perform(post("/bidList/update/100")
                        .param("account", "new account")
                        .param("type", "new type")
                        .param("bidQuantity", "42")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/bidList/list"));
    }

    @WithMockUser(value = "user", authorities = "USER")
    @Test
    public void testPostUpdateBid_WithEmptyAccount() throws Exception {
        mockMvc.perform(post("/bidList/update/100")
                        .param("account", "")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeHasFieldErrorCode("bidList", "account", "NotBlank"));
    }

    @WithMockUser(value = "user", authorities = "USER")
    @Test
    public void testPostUpdateBid_WithEmptyType() throws Exception {
        mockMvc.perform(post("/bidList/update/100")
                        .param("account", "new account")
                        .param("type", "")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeHasFieldErrorCode("bidList", "type", "NotBlank"));
    }

    @WithMockUser(value = "user", authorities = "USER")
    @Test
    public void testPostUpdateBid_WithEmptyBidQuantity() throws Exception {
        mockMvc.perform(post("/bidList/update/100")
                        .param("account", "new account")
                        .param("type", "new type")
                        .param("bidQuantity", "")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeHasFieldErrorCode("bidList", "bidQuantity", "typeMismatch"));
    }

    @WithMockUser(value = "user", authorities = "USER")
    @Test
    public void testPostUpdateBid_WithLettersBidQuantity() throws Exception {
        mockMvc.perform(post("/bidList/update/100")
                        .param("account", "new account")
                        .param("type", "new type")
                        .param("bidQuantity", "Twenty two")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeHasFieldErrorCode("bidList", "bidQuantity", "typeMismatch"));
    }


    /* ------- deleteBid() ------- */
    @WithMockUser(value = "user", authorities = "USER")
    @Test
    public void testGetDeleteBid() throws Exception {
        mockMvc.perform(get("/bidList/delete/100"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/bidList/list"));
    }

    @WithMockUser(value = "user", authorities = "USER")
    @Test
    public void testGetDelete_InvalidBidListId() throws Exception {
        mockMvc.perform(get("/bidList/delete/1001"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/app/error"));
    }
}
