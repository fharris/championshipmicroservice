package com.example.democrudrep.event;

public class NewSentQueryPageEvent {

    Long queryPageid;
    Long userId;
    String userName;
    String originalQuery;
    String query;
    
    public Long getQueryPageid() {
        return queryPageid;
    }
    public void setQueryPageid(Long queryPageid) {
        this.queryPageid = queryPageid;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getOriginalQuery() {
        return originalQuery;
    }
    public void setOriginalQuery(String originalQuery) {
        this.originalQuery = originalQuery;
    }
    public String getQuery() {
        return query;
    }
    public void setQuery(String query) {
        this.query = query;
    }
    @Override
    public String toString() {
        return "NewSentQueryPageEvent [originalQuery=" + originalQuery + ", query=" + query + ", queryPageid="
                + queryPageid + ", userId=" + userId + ", userName=" + userName + "]";
    }

   
    
    
}
