package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.service.IRuleNameService;
import com.nnk.springboot.service.exceptions.NonExistantRatingException;
import com.nnk.springboot.service.exceptions.NonExistantRuleNameException;
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
public class RuleNameController {

    @Autowired
    private IRuleNameService iRuleNameService;
    Logger logger = LoggerFactory.getLogger(RuleNameController.class);

    @RequestMapping("/ruleName/list")
    public String home(Model model)
    {
        model.addAttribute("rules", iRuleNameService.getAllRuleNames());
        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName bid) {
        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            iRuleNameService.addRuleName(ruleName);
            logger.debug("New bidList save to db: {}", ruleName);
            model.addAttribute("rules", iRuleNameService.getAllRuleNames());
            return "redirect:/ruleName/list";
        }
        return "ruleName/add";
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        try {
            RuleName ruleName = iRuleNameService.getRuleNameById(id);
            logger.debug("Get rating id: {} from db", ruleName.getId());
            model.addAttribute("ruleName", ruleName);
            return "ruleName/update";
        } catch (NonExistantRuleNameException e) {
            logger.error("Try to update non-existent ruleName id: " + id + " with URL input");
            return "redirect:/app/error";
        }
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "rating/update";
        }

        ruleName.setId(id);
        iRuleNameService.updateRuleName(ruleName);
        logger.debug("Update {} infos to db", ruleName);
        model.addAttribute("rules", iRuleNameService.getAllRuleNames());
        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        try {
            RuleName ruleName = iRuleNameService.getRuleNameById(id);
            logger.debug("Get ruleName id: {} from db", ruleName.getId());
            iRuleNameService.deleteRuleName(ruleName);
            logger.debug("Delete {} from db", ruleName);
        } catch (NonExistantRuleNameException e) {
            logger.error("Try to delete non-existent ruleName id: " + id + " with URL input");
        }
        return "redirect:/ruleName/list";
    }
}
