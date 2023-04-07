package com.nnk.springboot.service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.service.exceptions.NonExistantTradeException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ITradeService {

    Trade getTradeById(int id) throws NonExistantTradeException;
    List<Trade> getAllTrades();
    void addTrade(Trade trade);
    void updateTrade(Trade trade);
    void deleteTrade(Trade trade);
}
