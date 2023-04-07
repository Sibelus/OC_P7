package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.service.IRatingService;
import com.nnk.springboot.service.exceptions.NonExistantBidlistException;
import com.nnk.springboot.service.exceptions.NonExistantRatingException;
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
public class RatingController {

    @Autowired
    private IRatingService iRatingService;
    Logger logger = LoggerFactory.getLogger(RatingController.class);

    @RequestMapping("/rating/list")
    public String home(Model model)
    {
        model.addAttribute("Ratings", iRatingService.getAllRatings());
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            iRatingService.addRating(rating);
            logger.debug("New bidList save to db: {}", rating);
            model.addAttribute("Ratings", iRatingService.getAllRatings());
            return "redirect:/rating/list";
        }
        return "rating/add";
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        try {
            Rating rating = iRatingService.getRatingById(id);
            logger.debug("Get rating id: {} from db", rating.getId());
            model.addAttribute("rating", rating);
            return "rating/update";
        } catch (NonExistantRatingException e) {
            String errorMsg = e.getMessage();
            model.addAttribute("errorMsg", errorMsg);
            logger.error("Try to update non-existent rating id: " + id + " with URL input");
            return "403";
        }
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "rating/update";
        }

        rating.setId(id);
        iRatingService.updateRating(rating);
        logger.debug("Update {} infos to db", rating);
        model.addAttribute("Ratings", iRatingService.getAllRatings());
        return "redirect:/rating/list";
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        try {
            Rating rating = iRatingService.getRatingById(id);
            logger.debug("Get bidList id: {} from db", rating.getId());
            iRatingService.deleteRating(rating);
            logger.debug("Delete {} from db", rating);
        } catch (NonExistantRatingException e) {
            logger.error("Try to delete non-existent rating id: " + id + " with URL input");
        }
        return "redirect:/rating/list";
    }
}
