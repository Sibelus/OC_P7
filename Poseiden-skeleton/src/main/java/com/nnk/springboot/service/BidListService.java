package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.service.exceptions.NonExistantBidlistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BidListService implements IBidListService {

    @Autowired
    private BidListRepository bidListRepository;
    Logger logger  = LoggerFactory.getLogger(BidListService.class);


    @Override
    public BidList getBidlistById(int id) throws NonExistantBidlistException {
        if (bidListRepository.existsById(id) == false) {
            throw new NonExistantBidlistException("BidList id: " + id + " doesn't have a match in db");
        }
        logger.debug("Get BidList with id: " + id);
        BidList bidList = bidListRepository.findById(id).get();
        return bidList;
    }

    @Override
    public List<BidList> getAllBidlists() {
        logger.debug("Get all users");
        List<BidList> bidLists = bidListRepository.findAll();
        return bidLists;
    }

    @Override
    public void addBidlist(BidList bidList) {
        logger.debug("Save new bidList");
        bidListRepository.save(bidList);
    }

    @Override
    public void updateBidlist(BidList bidList) {
        logger.debug("Update new bidList");
        bidListRepository.save(bidList);
    }

    @Override
    public void deleteBidlist(BidList bidList) {
        logger.debug("Delete user from db");
        bidListRepository.delete(bidList);
    }
}
