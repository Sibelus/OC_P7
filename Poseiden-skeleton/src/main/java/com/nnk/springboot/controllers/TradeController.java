package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.service.ITradeService;
import com.nnk.springboot.service.exceptions.NonExistantTradeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class TradeController {

    @Autowired
    private ITradeService iTradeService;
    Logger logger = LoggerFactory.getLogger(TradeController.class);


    @RequestMapping("/trade/list")
    public String home(Model model)
    {
        model.addAttribute("trades", iTradeService.getAllTrades());
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addUser(Trade bid) {
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            iTradeService.addTrade(trade);
            logger.debug("New trade save to db: {}", trade);
            model.addAttribute("trades", iTradeService.getAllTrades());
            return "redirect:/trade/list";
        }
        return "trade/add";
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        try {
            Trade trade = iTradeService.getTradeById(id);
            logger.debug("Get trade id: {} from db", trade.getTradeId());
            model.addAttribute("trade", trade);
            return "trade/update";
        } catch (NonExistantTradeException e) {
            logger.error("Try to update non-existent trade id: " + id + " with URL input");
            return "redirect:/app/error";
        }
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "trade/update";
        }

        trade.setTradeId(id);
        iTradeService.updateTrade(trade);
        logger.debug("Update {} infos to db", trade);
        model.addAttribute("trades", iTradeService.getAllTrades());
        return "redirect:/trade/list";
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        try {
            Trade trade = iTradeService.getTradeById(id);
            logger.debug("Get trade id: {} from db", trade.getTradeId());
            iTradeService.deleteTrade(trade);
            logger.debug("Delete {} from db", trade);
            return "redirect:/trade/list";
        } catch (NonExistantTradeException e) {
            logger.error("Try to delete non-existent trade id: " + id + " with URL input");
            return "redirect:/app/error";
        }
    }
}
