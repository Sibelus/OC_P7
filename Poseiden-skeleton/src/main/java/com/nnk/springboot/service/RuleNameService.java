package com.nnk.springboot.service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.service.exceptions.NonExistantRuleNameException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RuleNameService implements IRuleNameService {

    @Autowired
    private RuleNameRepository ruleNameRepository;
    Logger logger  = LoggerFactory.getLogger(RuleNameService.class);


    @Override
    public RuleName getRuleNameById(int id) throws NonExistantRuleNameException {
        if (ruleNameRepository.existsById(id) == false) {
            throw new NonExistantRuleNameException("RuleName id: " + id + " doesn't have a match in db");
        }
        logger.debug("Get Rating with id: " + id);
        RuleName ruleName = ruleNameRepository.findById(id).get();
        return ruleName;
    }

    @Override
    public List<RuleName> getAllRuleNames() {
        logger.debug("Get all ruleNames");
        List<RuleName> ruleNames = ruleNameRepository.findAll();
        return ruleNames;
    }

    @Override
    public void addRuleName(RuleName ruleName) {
        logger.debug("Save new ruleName");
        ruleNameRepository.save(ruleName);
    }

    @Override
    public void updateRuleName(RuleName ruleName) {
        logger.debug("Update new ruleName");
        ruleNameRepository.save(ruleName);
    }

    @Override
    public void deleteRuleName(RuleName ruleName) {
        logger.debug("Delete new ruleName");
        ruleNameRepository.delete(ruleName);
    }
}
