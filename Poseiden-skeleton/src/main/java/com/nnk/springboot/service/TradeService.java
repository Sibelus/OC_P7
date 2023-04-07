package com.nnk.springboot.service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.service.exceptions.NonExistantTradeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeService implements ITradeService {

    @Autowired
    private TradeRepository tradeRepository;
    Logger logger  = LoggerFactory.getLogger(TradeService.class);


    @Override
    public Trade getTradeById(int id) throws NonExistantTradeException {
        if (tradeRepository.existsById(id) == false) {
            throw new NonExistantTradeException("Trade id: " + id + " doesn't have a match in db");
        }
        logger.debug("Get BidList with id: " + id);
        Trade trade = tradeRepository.findById(id).get();
        return trade;
    }

    @Override
    public List<Trade> getAllTrades() {
        logger.debug("Get all trades");
        List<Trade> trades = tradeRepository.findAll();
        return trades;
    }

    @Override
    public void addTrade(Trade trade) {
        logger.debug("Save new trade");
        tradeRepository.save(trade);
    }

    @Override
    public void updateTrade(Trade trade) {
        logger.debug("Update new trade");
        tradeRepository.save(trade);
    }

    @Override
    public void deleteTrade(Trade trade) {
        logger.debug("Delete trade from db");
        tradeRepository.delete(trade);
    }
}
