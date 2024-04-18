package com.example.democrudrep.configuration;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.democrudrep.event.NewSentQueryPageEvent;
import com.example.democrudrep.event.SentQueryPageEvent;
import com.example.democrudrep.service.ChampionshipService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;



@Service
public class KafkaConsumerMyService {


    private final ChampionshipService championshipService;

    final Logger logger = LoggerFactory.getLogger(KafkaConsumerMyService.class);

    public KafkaConsumerMyService(ChampionshipService championshipService) {
        this.championshipService = championshipService;
    }


    @KafkaListener(topics = "myTopicCuriosity2", groupId = "myGroupCuriosity33")
    public void listen(String message) {
        System.out.println("Received Message: " + message);

        String consumedMessage = message;
        System.out.println("consumedMessage: " + consumedMessage);
        ObjectMapper objectMapper = new ObjectMapper();


        try {
            System.out.println("Received Message TRY-CATCH: " + message);
            //{"queryPageid":737,"userId":1,"userName":"Fernando","originalQuery":"Brno","query":"Brno–Tuřany Airport"}
            //SentQueryPageEvent(queryPageid=736, userId=1, userName=Fernando, originalQuery=Brno, query=Brno)

            NewSentQueryPageEvent newSentQueryPageEvent = objectMapper.readValue(consumedMessage, NewSentQueryPageEvent.class);  
            logger.info("Event Page Id {} and Event UserName {}", newSentQueryPageEvent.getQueryPageid(), newSentQueryPageEvent.getUserName());

            SentQueryPageEvent sentQueryPageEvent = new SentQueryPageEvent(newSentQueryPageEvent.getQueryPageid(), newSentQueryPageEvent.getUserId()
            , newSentQueryPageEvent.getUserName()  ,  newSentQueryPageEvent.getOriginalQuery()   , newSentQueryPageEvent.getQuery());

            logger.info("Preparing to call championshipService and persist in the database.");
            championshipService.processEvent(sentQueryPageEvent);
            // ...
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        

    }
}
