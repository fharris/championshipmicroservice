package com.example.democrudrep.service;



import com.example.democrudrep.event.SentQueryPageEvent;


/**
 * Service to manage Championship details.
 * The ChampionshipService is a Java interface that defines a contract for a service to manage Championship details. 
 * It has one method:
 */
public interface ChampionshipService {

 
    /**
     * Process a new query page from a given user.
     *
     * @param sentQueryPageEvent the query page data with user details, query, original query, etc.
     * @return a {@link Boolean} is the process is well done
     */

     /**
     * Process a new query page from a given user as an event.
     *
     * @param sentQueryPageEvent the query page data with user details, query, original query, etc.
     * @return a Boolean confirming the success of the operation
     * 
     * This method is intended to process a new query page from a given user as an event.
     * The SentQueryPageEvent parameter contains the query page data with user details, query, original query, etc. 
     * The method returns a Boolean indicating the success of the operation.
     */
    Boolean processEvent(SentQueryPageEvent sentQueryPageEvent);

}
