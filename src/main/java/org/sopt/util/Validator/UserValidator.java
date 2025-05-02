package org.sopt.util.Validator;

import org.sopt.exception.ErrorCode;
import org.sopt.exception.InvalidRequestException;

public class UserValidator {
    public static void isNameValid(String name){
        if(name == null || name.trim().isEmpty()){
            throw new InvalidRequestException(ErrorCode.EMPTY_NAME);
        }
        if(name.length()>10){
            throw new InvalidRequestException(ErrorCode.LONG_NAME);
        }
    }

    public static void isIdValid(Long id){
        if (id == null) {
            throw new InvalidRequestException(ErrorCode.EMPTY_USER);
        }
    }
}
