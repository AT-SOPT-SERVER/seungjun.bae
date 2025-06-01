package org.sopt.util.Validator;

import org.sopt.exception.ErrorCode;
import org.sopt.exception.InvalidRequestException;

import java.time.Duration;
import java.time.LocalDateTime;

public class PostValidator {

    public static void isTitleValid(String title){
        if (title==null || title.trim().isEmpty()) {
            throw new InvalidRequestException(ErrorCode.EMPTY_TITLE);
        }
        if (title.length()>30){
            throw new InvalidRequestException(ErrorCode.LONG_TITLE);
        }
    }

    public static void isBodyValid(String body){
        if (body == null || body.trim().isEmpty()) {
            throw new InvalidRequestException(ErrorCode.EMPTY_BODY);
        }
        if (body.length()>1000){
            throw new InvalidRequestException(ErrorCode.LONG_BODY);
        }
    }

    public static void postTime(LocalDateTime latestPostTime){
        if(latestPostTime==null || Duration.between(latestPostTime , LocalDateTime.now()).toMinutes()>=3) {
        } else {
            throw new InvalidRequestException(ErrorCode.POST_TIME_RESTRICTION);
        }
    }
}
