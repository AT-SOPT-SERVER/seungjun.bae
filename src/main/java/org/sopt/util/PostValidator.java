package org.sopt.util;

import org.sopt.exception.ErrorCode;
import org.sopt.exception.InvalidRequestException;

import java.time.Duration;
import java.time.LocalDateTime;

public class PostValidator {

    public static void titleLength(String title){
        if (title.isEmpty()) {
            throw new InvalidRequestException(ErrorCode.EMPTY_TITLE);
        }
        if (title.length()>30){
            throw new InvalidRequestException(ErrorCode.LONG_TITLE);
        }
    }

    public static void postTime(LocalDateTime latestPostTime){
        if(latestPostTime==null || Duration.between(latestPostTime , LocalDateTime.now()).toMinutes()>=3) {
        } else {
            throw new InvalidRequestException(ErrorCode.POST_TIME_RESTRICTION);
        }
    }
}
