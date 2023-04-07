package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.service.IBidListService;
import com.nnk.springboot.service.exceptions.NonExistantBidlistException;
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
public class BidListController {

    @Autowired
    private IBidListService iBidListService;
    Logger logger = LoggerFactory.getLogger(BidListController.class);


    @RequestMapping("/bidList/list")
    public String home(Model model)
    {
        model.addAttribute("bids", iBidListService.getAllBidlists());
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(BidList bidList) {
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bidList, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            iBidListService.addBidlist(bidList);
            logger.debug("New bidList save to db: {}", bidList);
            model.addAttribute("bids", iBidListService.getAllBidlists());
            return "redirect:/bidList/list";
        }
        return "bidList/add";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        try {
            BidList bidList = iBidListService.getBidlistById(id);
            logger.debug("Get bidList id: {} from db", bidList.getBidListId());
            model.addAttribute("bidList", bidList);
            return "bidList/update";
        } catch (NonExistantBidlistException e) {
            logger.error("Try to update non-existent bidList id: " + id + " with URL input");
            return "redirect:/bidList/list";
        }
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                            BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "bidList/update";
        }

        bidList.setBidListId(id);
        iBidListService.updateBidlist(bidList);
        logger.debug("Update {} infos to db", bidList);
        model.addAttribute("bids", iBidListService.getAllBidlists());
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        try {
            BidList bidList = iBidListService.getBidlistById(id);
            logger.debug("Get bidList id: {} from db", bidList.getBidListId());
            iBidListService.deleteBidlist(bidList);
            logger.debug("Delete {} from db", bidList);
        } catch (NonExistantBidlistException e) {
            logger.error("Try to delete non-existent bidList id: " + id + " with URL input");
        }
        return "redirect:/bidList/list";
    }
}
