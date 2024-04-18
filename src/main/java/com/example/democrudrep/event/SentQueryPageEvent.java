package com.example.democrudrep.event;

import lombok.Value;

@Value
public class SentQueryPageEvent {
    Long queryPageid;
    Long userId;
    String userName;
    String originalQuery;
    String query;
}
