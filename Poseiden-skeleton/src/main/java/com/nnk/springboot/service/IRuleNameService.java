package com.nnk.springboot.service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.service.exceptions.NonExistantRuleNameException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IRuleNameService {

    RuleName getRuleNameById(int id) throws NonExistantRuleNameException;
    List<RuleName> getAllRuleNames();
    void addRuleName(RuleName ruleName);
    void updateRuleName(RuleName ruleName);
    void deleteRuleName(RuleName ruleName);
}
