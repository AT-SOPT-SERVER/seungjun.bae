package org.sopt.validation;

import java.time.Duration;
import java.time.LocalDateTime;

public class PostValidator {

    public static void titleLength(String title){
        if (title.isEmpty()) {
            throw new IllegalArgumentException("제목을 입력해주세요");
        }
        if (title.length()>30){
            throw new IllegalArgumentException("제목은 최대 30자까지 작성할 수 있어요");
        }
    }

    public static void postTime(LocalDateTime latestPostTime){
        if(latestPostTime==null || Duration.between(latestPostTime , LocalDateTime.now()).getSeconds()>=1) {
        } else {
            throw new IllegalArgumentException((180-(int)Duration.between(latestPostTime , LocalDateTime.now()).getSeconds()) + "초 뒤에 게시물을 다시 작성할 수 있어요.");
        }
    }

}
