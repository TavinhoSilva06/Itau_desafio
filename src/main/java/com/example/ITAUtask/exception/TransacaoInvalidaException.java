package com.example.ITAUtask.exception;

public class TransacaoInvalidaException extends RuntimeException {

    public TransacaoInvalidaException(String mensagem) {
        super(mensagem);
    }
}