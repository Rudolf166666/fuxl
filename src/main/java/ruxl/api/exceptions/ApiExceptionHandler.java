package ruxl.api.exceptions;

import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalTime;

@ControllerAdvice
public class ApiExceptionHandler {

    private final class ErrorPayload {
        private final String message;
        private final HttpStatus statusCode;
        private final LocalTime timestamp;

        private final String path;
        private final int code;

        public String getMessage() {
            return message;
        }

        public HttpStatus getStatusCode() {
            return statusCode;
        }

        public LocalTime getTimestamp() {
            return timestamp;
        }

        public String getPath() {
            return path;
        }

        public int getCode() {
            return code;
        }

        public ErrorPayload(String message, HttpStatus statusCode, int code, String path) {
            this.message = message;
            this.statusCode = statusCode;
            this.timestamp = LocalTime.now();
            this.code=code;
            this.path=path;
        }
    }

    @ExceptionHandler(value = {ApiException.class})
    public ResponseEntity<ErrorPayload> handleException(ApiException e, WebRequest req){
        String path=((ServletWebRequest)req).getRequest().getRequestURI();
        ErrorPayload payload =new ErrorPayload(e.getMessage(),
                e.getHttpStatus(),
                e.getCode(),
                path);
        return ResponseEntity.status(e.getHttpStatus()).body(payload);
    }
}
