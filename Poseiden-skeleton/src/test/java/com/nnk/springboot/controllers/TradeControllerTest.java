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
public class TradeControllerTest {

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
        mockMvc.perform(get("/trade/list")).andExpect(status().isOk());
    }


    /* ------- addBidForm() ------- */
    @WithMockUser(value = "admin")
    @Test
    public void testGetAddBidForm() throws Exception {
        mockMvc.perform(get("/trade/add")).andExpect(status().isOk());
    }


    /* ------- validate() ------- */
    @WithMockUser(value = "admin")
    @Test
    public void testPostValidate() throws Exception {
        mockMvc.perform(post("/trade/validate")
                        .param("account", "account")
                        .param("type", "type")
                        .param("buyQuantity", "42")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/trade/list"));
    }

    @WithMockUser(value = "admin")
    @Test
    public void testPostValidate_WithEmptyAccount() throws Exception {
        mockMvc.perform(post("/trade/validate")
                        .param("account", "")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeHasFieldErrorCode("trade", "account", "NotBlank"));
    }

    @WithMockUser(value = "admin")
    @Test
    public void testPostValidate_WithEmptyType() throws Exception {
        mockMvc.perform(post("/trade/validate")
                        .param("account", "account")
                        .param("type", "")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeHasFieldErrorCode("trade", "type", "NotBlank"));
    }

    @WithMockUser(value = "admin")
    @Test
    public void testPostValidate_WithEmptyBuyQuantity() throws Exception {
        mockMvc.perform(post("/trade/validate")
                        .param("account", "account")
                        .param("type", "type")
                        .param("buyQuantity", "")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeHasFieldErrorCode("trade", "buyQuantity", "typeMismatch"));
    }

    @WithMockUser(value = "admin")
    @Test
    public void testPostValidate_WithLetterBuyQuantity() throws Exception {
        mockMvc.perform(post("/trade/validate")
                        .param("account", "account")
                        .param("type", "type")
                        .param("buyQuantity", "fourty-two")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeHasFieldErrorCode("trade", "buyQuantity", "typeMismatch"));
    }


    /* ------- showUpdateForm() ------- */
    @WithMockUser(value = "admin")
    @Test
    public void testGetshowUpdateForm() throws Exception {
        mockMvc.perform(get("/trade/update/100")).andExpect(status().isOk());
    }


    /* ------- updateTrade() ------- */
    @WithMockUser(value = "admin")
    @Test
    public void testPostUpdateTrade() throws Exception {
        mockMvc.perform(post("/trade/update/100")
                        .param("account", "new account")
                        .param("type", "new type")
                        .param("buyQuantity", "42")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/trade/list"));
    }

    @WithMockUser(value = "admin")
    @Test
    public void testPostUpdateTrade_WithEmptyAccount() throws Exception {
        mockMvc.perform(post("/trade/update/100")
                        .param("account", "")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeHasFieldErrorCode("trade", "account", "NotBlank"));
    }

    @WithMockUser(value = "admin")
    @Test
    public void testPostUpdateTrade_WithEmptyType() throws Exception {
        mockMvc.perform(post("/trade/update/100")
                        .param("account", "account")
                        .param("type", "")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeHasFieldErrorCode("trade", "type", "NotBlank"));
    }

    @WithMockUser(value = "admin")
    @Test
    public void testPostUpdateTrade_WithEmptyBuyQuantity() throws Exception {
        mockMvc.perform(post("/trade/update/100")
                        .param("account", "account")
                        .param("type", "type")
                        .param("buyQuantity", "")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeHasFieldErrorCode("trade", "buyQuantity", "typeMismatch"));
    }

    @WithMockUser(value = "admin")
    @Test
    public void testPostUpdateTrade_WithLetterBuyQuantity() throws Exception {
        mockMvc.perform(post("/trade/update/100")
                        .param("account", "account")
                        .param("type", "type")
                        .param("buyQuantity", "fourty-two")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeHasFieldErrorCode("trade", "buyQuantity", "typeMismatch"));
    }


    /* ------- deleteTrade() ------- */
    @WithMockUser(value = "admin")
    @Test
    public void testGetDeleteBid() throws Exception {
        mockMvc.perform(get("/trade/delete/100"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/trade/list"));
    }
}
