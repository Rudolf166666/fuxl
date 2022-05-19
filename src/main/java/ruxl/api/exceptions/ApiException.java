package ruxl.api.exceptions;

import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException{

    public static ApiException generate404Exception(String message){
        return new ApiException(message,HttpStatus.NOT_FOUND,404);
    }
    public static ApiException generate500Exception(String message){
        return new ApiException(message,HttpStatus.INTERNAL_SERVER_ERROR,500);
    }
    public static ApiException generate401Exception(String message){
        return new ApiException(message,HttpStatus.UNAUTHORIZED,401);
    }
    public static ApiException generate403Exception(String message){
        return new ApiException(message,HttpStatus.FORBIDDEN,403);
    }
    public static ApiException generate409Exception(String message){
        return new ApiException(message,HttpStatus.CONFLICT,409);
    }
    private final HttpStatus httpStatus;
    private final int code;
     ApiException(String message,HttpStatus httpStatus, int code) {
        super(message);
        this.httpStatus=httpStatus;
        this.code=code;
    }


     HttpStatus getHttpStatus() {
        return httpStatus;
    }

     int getCode() {
        return code;
    }
}
