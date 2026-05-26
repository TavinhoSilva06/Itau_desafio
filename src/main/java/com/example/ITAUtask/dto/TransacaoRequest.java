package com.example.ITAUtask.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record TransacaoRequest(

        @NotNull
        @PositiveOrZero
        BigDecimal valor,

        @NotNull
        OffsetDateTime dataHora

) {
}