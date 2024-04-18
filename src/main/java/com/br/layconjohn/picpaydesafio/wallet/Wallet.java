package com.br.layconjohn.picpaydesafio.wallet;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

public record Wallet(
    @Id Long id,
    String fullname,
    Long cpf,
    String email,
    String password,
    int type,
    BigDecimal balance
) {
    
}
