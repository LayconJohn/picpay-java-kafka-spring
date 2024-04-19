package com.br.layconjohn.picpaydesafio.wallet;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;

public record Wallet(
    @Id Long id,
    String fullname,
    Long cpf,
    String email,
    String password,
    int type,
    BigDecimal balance
) {
    public Wallet debit(BigDecimal value) {
        return new Wallet(this.id, this.fullname, this.cpf, this.email, this.password, this.type, this.balance.subtract(value));
    }
}
