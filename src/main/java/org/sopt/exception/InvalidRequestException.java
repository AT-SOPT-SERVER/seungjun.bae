package org.sopt.exception;

public class InvalidRequestException extends CustomException{
    public InvalidRequestException(ErrorCode errorCode){
        super(errorCode);
    }
}
