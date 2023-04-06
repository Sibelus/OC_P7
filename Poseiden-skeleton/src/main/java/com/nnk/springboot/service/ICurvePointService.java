package com.nnk.springboot.service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.service.exceptions.NonExistantCurvePointException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ICurvePointService {

    CurvePoint getCurvePointById(int id) throws NonExistantCurvePointException;
    List<CurvePoint> getAllCurvePoints();
    void addCurvePoint(CurvePoint curvePoint);
    void updateCurvePoint(CurvePoint curvePoint);
    void deleteCurvePoint(CurvePoint curvePoint);
}
