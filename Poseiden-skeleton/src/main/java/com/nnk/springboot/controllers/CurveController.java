package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.service.ICurvePointService;
import com.nnk.springboot.service.exceptions.NonExistantCurvePointException;
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
public class CurveController {
    @Autowired
    private ICurvePointService iCurvePointService;
    Logger logger = LoggerFactory.getLogger(CurveController.class);

    @RequestMapping("/curvePoint/list")
    public String home(Model model)
    {
        // TODO: find all Curve Point, add to model
        model.addAttribute("curvePoints", iCurvePointService.getAllCurvePoints());
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addBidForm(CurvePoint bid) {
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            iCurvePointService.addCurvePoint(curvePoint);
            logger.debug("New curvePoint save to db: {}", curvePoint);
            model.addAttribute("curvePoints", iCurvePointService.getAllCurvePoints());
            return "redirect:/curvePoint/list";
        }
        return "curvePoint/add";
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        try {
            CurvePoint curvePoint = iCurvePointService.getCurvePointById(id);
            logger.debug("Get curvePoint id: {} from db", curvePoint.getCurveId());
            model.addAttribute("curvePoint", curvePoint);
            return "curvePoint/update";
        } catch (NonExistantCurvePointException e) {
            String errorMsg = e.getMessage();
            model.addAttribute("errorMsg", errorMsg);
            logger.error("Try to update non-existent curvePoint id: " + id + " with URL input");
            return "403";
        }
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateCurvePoint(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                                   BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "curvePoint/update";
        }

        curvePoint.setId(id);
        iCurvePointService.updateCurvePoint(curvePoint);
        logger.debug("Update {} infos to db", curvePoint);
        model.addAttribute("curvePoints", iCurvePointService.getAllCurvePoints());
        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        try {
            CurvePoint curvePoint = iCurvePointService.getCurvePointById(id);
            logger.debug("Get curvePoint id: {} from db", curvePoint.getId());
            iCurvePointService.deleteCurvePoint(curvePoint);
            logger.debug("Delete {} from db", curvePoint);
        } catch (NonExistantCurvePointException e) {
            logger.error("Try to delete non-existent curvePoint id: " + id + " with URL input");
        }
        return "redirect:/curvePoint/list";
    }
}
