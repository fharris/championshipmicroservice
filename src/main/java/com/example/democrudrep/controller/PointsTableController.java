package com.example.democrudrep.controller;

import com.example.democrudrep.service.PointsTableService;
import com.example.democrudrep.domain.PointsTableLine;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * Controller to get Points table from PointsTable service.
 */
@RestController
@RequestMapping("/leaders")
@RequiredArgsConstructor
class PointsTableController {

    private final PointsTableService pointsTableService;


    @GetMapping
    public List<PointsTableLine> getPointsTable() {
        int threshold = 3; //number of records to bring back, to filter - move this to properties
        return pointsTableService.getPointsTable(threshold);
    }
    
}
