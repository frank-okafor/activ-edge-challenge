package com.activedge.challenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.activedge.challenge.pojos.ServiceResponse;

@ResponseBody
@RestControllerAdvice
public class ServiceExceptionHandler {

    @ExceptionHandler(NullPointerException.class)
    public ServiceResponse nullException(NullPointerException e) {
	return new ServiceResponse(HttpStatus.EXPECTATION_FAILED, e.getMessage());
    }

    @ExceptionHandler(ServiceException.class)
    public ServiceResponse userServiceException(ServiceException e) {
	return new ServiceResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ServiceResponse illegalArgumentException(IllegalArgumentException e) {
	return new ServiceResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ServiceResponse generalException(Exception e) {
	return new ServiceResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

}
