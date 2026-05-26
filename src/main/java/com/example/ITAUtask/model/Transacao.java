package com.example.ITAUtask.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class Transacao {

    private final BigDecimal valor;
    private final OffsetDateTime dataHora;

    public Transacao(BigDecimal valor, OffsetDateTime dataHora) {
        this.valor = valor;
        this.dataHora = dataHora;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public OffsetDateTime getDataHora() {
        return dataHora;
    }
}