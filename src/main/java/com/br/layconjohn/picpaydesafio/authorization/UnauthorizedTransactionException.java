package com.br.layconjohn.picpaydesafio.authorization;

public class UnauthorizedTransactionException extends RuntimeException{
    public UnauthorizedTransactionException(String message) {
        super(message);
    }
}
