package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.service.exceptions.NonExistantBidlistException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IBidListService {

    BidList getBidlistById(int id) throws NonExistantBidlistException;
    List<BidList> getAllBidlists();
    void addBidlist(BidList bidList);
    void updateBidlist(BidList bidList);
    void deleteBidlist(BidList bidList);
}
