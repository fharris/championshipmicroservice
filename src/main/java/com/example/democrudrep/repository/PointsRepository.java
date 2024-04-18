package com.example.democrudrep.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

//import com.example.democrudrep.domain.LeaderBoardRow;
import com.example.democrudrep.domain.PointsTableLine;
//import com.example.democrudrep.domain.ScoreCard;
import com.example.democrudrep.domain.PointsTemplate;

/**
 * Handles CRUD operations with ScoreCards and other related score queries
 */
public interface PointsRepository extends CrudRepository<PointsTemplate, Long> {

       /**
     * Gets the total score for a given user: the sum of the scores of all
     * their PointsTemplate.
     *
     * @param userId the id of the user
     * @return the total score for the user, empty if the user doesn't exist
     */
    @Query("SELECT SUM(s.score) FROM PointsTemplate s WHERE s.userId = :userId GROUP BY s.userId")
    Optional<Integer> getTotalScoreForUser(@Param("userId") Long userId);

    /**
     * Retrieves a list of {@link LeaderBoardRow}s representing the Leader Board
     * of users and their total score.
     *
     * @return the leader board, sorted by highest score first.
     */ 
    @Query("SELECT NEW com.example.democrudrep.domain.PointsTableLine(s.userId, SUM(s.score)) " +
            "FROM PointsTemplate s " +
            "GROUP BY s.userId ORDER BY SUM(s.score) DESC")
    List<PointsTableLine> findFirst();

    /**
     * Retrieves all the PointsTemplate for a given user, identified by his user id.
     *
     * @param userId the id of the user
     * @return a list containing all the PointsTemplates for the given user,
     * sorted by most recent.
     */
    List<PointsTemplate> findByUserIdOrderByScoreTimestampDesc(final Long userId);

}
