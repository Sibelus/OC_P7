package com.nnk.springboot.service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.service.exceptions.NonExistantCurvePointException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurvePointService implements ICurvePointService {

    @Autowired
    private CurvePointRepository curvePointRepository;
    Logger logger  = LoggerFactory.getLogger(CurvePointService.class);


    @Override
    public CurvePoint getCurvePointById(int id) throws NonExistantCurvePointException {
        if (curvePointRepository.existsById(id) == false) {
            throw new NonExistantCurvePointException("CurvePoint id: " + id + " doesn't have a match in db");
        }
        logger.debug("Get BidList with id: " + id);
        CurvePoint curvePoint = curvePointRepository.findById(id).get();
        return curvePoint;
    }

    @Override
    public List<CurvePoint> getAllCurvePoints() {
        logger.debug("Get all curve points");
        List<CurvePoint> curvePoints = curvePointRepository.findAll();
        return curvePoints;
    }

    @Override
    public void addCurvePoint(CurvePoint curvePoint) {
        logger.debug("Save new curvePoint");
        curvePointRepository.save(curvePoint);
    }

    @Override
    public void updateCurvePoint(CurvePoint curvePoint) {
        logger.debug("Update new curvePoint");
        curvePointRepository.save(curvePoint);
    }

    @Override
    public void deleteCurvePoint(CurvePoint curvePoint) {
        logger.debug("Delete curvePoint from db");
        curvePointRepository.delete(curvePoint);
    }
}
