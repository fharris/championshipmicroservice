package com.example.democrudrep.service;

import java.util.List;


import org.springframework.stereotype.Service;
//import com.example.democrudrep.domain.LeaderBoardRow;
import com.example.democrudrep.domain.PointsTableLine;
//import com.example.democrudrep.repository.ScoreRepository;
import com.example.democrudrep.repository.PointsRepository;
//import com.example.democrudrep.repository.BadgeRepository;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PointsTableServiceImpl implements PointsTableService {

    private final PointsRepository pointsRepository;

    @Override
    public List<PointsTableLine> getPointsTable(int threshold) {
        List<PointsTableLine> scoreOnly = null;
        try {
            if (pointsRepository.findFirst().size() < threshold) {
                threshold = pointsRepository.findFirst().size();
            }
            scoreOnly = pointsRepository.findFirst().subList(0, threshold);
        } catch (Exception e) {
            System.out.println("Error when running scoreRepository" + e.getMessage());
            System.out.println("recover from error by returing without threshold");
            scoreOnly = pointsRepository.findFirst();
        } 
        
       

        // List<LeaderBoardRow> scoreOnly = pointsRepository.findFirst().subList(0, threshold);
    return scoreOnly;
    }


    
}
