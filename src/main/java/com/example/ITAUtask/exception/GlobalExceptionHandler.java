package com.example.ITAUtask.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TransacaoInvalidaException.class)
    public ResponseEntity<String> tratarTransacaoInvalida(
            TransacaoInvalidaException ex
    ) {

        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> tratarValidacao(
            MethodArgumentNotValidException ex
    ) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Dados inválidos");
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> tratarErroGenerico(
            Exception ex
    ) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro interno do servidor");
    }
}