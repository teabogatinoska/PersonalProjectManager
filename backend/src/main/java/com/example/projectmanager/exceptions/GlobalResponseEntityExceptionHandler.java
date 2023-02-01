package com.example.projectmanager.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class GlobalResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public final ResponseEntity<Object> handleProjectIdAlreadyExistsException(ProjectIdAlreadyExistsException ex, WebRequest request){
        ProjectIdAlreadyExistsExceptionResponse exceptionResponse = new ProjectIdAlreadyExistsExceptionResponse(ex.getMessage());
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleProjectIdNotFoundException(ProjectIdNotFoundException ex, WebRequest request){
        ProjectIdNotFoundExceptionResponse exceptionResponse = new ProjectIdNotFoundExceptionResponse(ex.getMessage());
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleProjectNotFoundException(ProjectNotFoundException ex, WebRequest request){
        ProjectNotFoundExceptionResponse exceptionResponse = new ProjectNotFoundExceptionResponse(ex.getMessage());
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler
    public final ResponseEntity<Object> handleUsernameAlreadyExists(UsernameAlreadyExistsException ex, WebRequest request){
        UsernameAlreadyExistsResponse exceptionResponse = new UsernameAlreadyExistsResponse(ex.getMessage());
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleTaskEndDateIsNotValidException(TaskEndDateIsNotValidException ex, WebRequest request){
        TaskEndDateIsNotValidExceptionResponse exceptionResponse = new TaskEndDateIsNotValidExceptionResponse(ex.getMessage());
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleInvalidProjectStartDateException(InvalidProjectStartDateException ex, WebRequest request){
        InvalidProjectStartDateExceptionResponse exceptionResponse = new InvalidProjectStartDateExceptionResponse(ex.getMessage());
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleInvalidUserPermissionsException(InvalidUserPermissionsException ex, WebRequest request){
        InvalidUserPermissionsExceptionResponse exceptionResponse = new InvalidUserPermissionsExceptionResponse(ex.getMessage());
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleUserIdNotFoundException(UserIdNotFoundException ex, WebRequest request){
        UserIdNotFoundExceptionResponse exceptionResponse = new UserIdNotFoundExceptionResponse(ex.getMessage());
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
