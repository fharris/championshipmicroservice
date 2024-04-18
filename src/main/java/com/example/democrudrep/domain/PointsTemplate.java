package com.example.democrudrep.domain;


import lombok.*;
import javax.persistence.*;

/**
 * The PointsTemplate class is a Java entity class that represents a table in a database. 
 * It's used to register points with an associated user and the timestamp when the score is registered. 
 * This class is annotated with @Entity, which means it's a JPA entity and is mapped to a database table.
 * 
 * There's also a custom constructor that takes userId and queryPageId as parameters and initializes the fields. 
 * Notably, it sets both cardId and queryPageId to the provided queryPageId, which is a way to prevent repeated fields in the table. 
 * The scoreTimestamp is set to the current time, and score is set to DEFAULT_SCORE.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PointsTemplate {

     // The default score assigned to this card, if not specified.
     public static final int DEFAULT_SCORE = 2;

     @Id
     //@GeneratedValue
     private Long cardId;
     private Long queryPageId;
     private Long userId;
     @EqualsAndHashCode.Exclude
     private long scoreTimestamp;
     private int score;
 
     public PointsTemplate(final Long userId, final Long queryPageId) {
         // FH: using queryPageId twice in the constructor to not allow repeated fields in the table points_template!
         this(queryPageId,  queryPageId, userId,System.currentTimeMillis(), DEFAULT_SCORE);

     }
 
    
}
