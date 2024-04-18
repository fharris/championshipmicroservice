package com.example.democrudrep.service;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.example.democrudrep.domain.PointsTemplate;
import com.example.democrudrep.event.SentQueryPageEvent;
import com.example.democrudrep.repository.PointsRepository;


@Service
@Slf4j
@RequiredArgsConstructor
public class ChampionshipServiceImpl implements ChampionshipService {
    
    private final PointsRepository pointsRepository;


        @Override
        public Boolean processEvent(SentQueryPageEvent sentQueryPageEvent) {
             // We give points whenever we receive an event

                log.info("Got event...analysing it...");
                log.info("User {} with Query {} with query page id {}", sentQueryPageEvent.getUserName(), 
                                                                                sentQueryPageEvent.getQuery(), 
                                                                                sentQueryPageEvent.getQueryPageid());

                PointsTemplate pointsTemplate = new PointsTemplate(sentQueryPageEvent.getUserId(), 
                                                                        sentQueryPageEvent.getQueryPageid());
        
                log.info("User {} scored {} points for query page id {} with score card id {} ", pointsTemplate.getUserId(), 
                                                                                                        pointsTemplate.getScore(), 
                                                                                                        pointsTemplate.getQueryPageId(), 
                                                                                                        pointsTemplate.getCardId());

                pointsRepository.save(pointsTemplate);

                log.info("User {} scored {} points for query page id {}",
                sentQueryPageEvent.getUserName(), pointsTemplate.getScore(), sentQueryPageEvent.getQueryPageid());

                return true;
                    
        }
        
}