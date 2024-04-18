package com.example.democrudrep.domain;

import lombok.*;

/**
 * A line in the Points Table.
 */
@Value
public class PointsTableLine {
    
    Long userId;
    Long totalScore;

    public PointsTableLine(final Long userId, final Long totalScore) {
        this.userId = userId;
        this.totalScore = totalScore;
    }

    
}
