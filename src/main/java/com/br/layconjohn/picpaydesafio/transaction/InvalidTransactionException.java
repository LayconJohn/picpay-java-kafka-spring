package com.br.layconjohn.picpaydesafio.transaction;

public class InvalidTransactionException extends RuntimeException {
    public InvalidTransactionException(String message) {
        super(message);
    }
}
