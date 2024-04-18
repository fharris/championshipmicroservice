package com.example.democrudrep.service;

import java.util.List;
import com.example.democrudrep.domain.PointsTableLine;

public interface PointsTableService {

    /**
     * @return an ordered list of "threshold" linespoints table lines, i.e, a points table
     */
    List<PointsTableLine> getPointsTable(int threshold);
    
}
