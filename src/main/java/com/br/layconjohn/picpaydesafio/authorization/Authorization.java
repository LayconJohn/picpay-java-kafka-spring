package com.br.layconjohn.picpaydesafio.authorization;

public record Authorization(
    String message
) {
    public boolean isAuthorized() {
        return this.message.equals("Autorizado");
    }
}
