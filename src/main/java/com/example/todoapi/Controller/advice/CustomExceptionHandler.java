package com.example.todoapi.Controller.advice;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.todoapi.Service.task.TaskEitityNotFoundException;
import com.example.todoapi.model.BadRequestError;
import com.example.todoapi.model.ResourceNotFoundError;


@RestControllerAdvice //自分たちが定義している処理を差し込みたい時に使う（今回は例外が発生した時に挟み込む）
public class CustomExceptionHandler extends ResponseEntityExceptionHandler{
    
    @ExceptionHandler(TaskEitityNotFoundException.class) //このメソッドはTaskEitityNotFoundExceptionが派生した時のhハンドラーメソッドである
    public ResponseEntity<ResourceNotFoundError>  handeleTaskEntityNotFoundException(TaskEitityNotFoundException e) {
        var error = new ResourceNotFoundError();
        error.setDetail(e.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, 
            HttpHeaders headers, 
            HttpStatusCode status, 
            WebRequest request
    ) {
        return ResponseEntity.badRequest().body(new BadRequestError());
    }
}
