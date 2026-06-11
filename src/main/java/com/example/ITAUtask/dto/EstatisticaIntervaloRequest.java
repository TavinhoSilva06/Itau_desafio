package com.example.ITAUtask.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record EstatisticaIntervaloRequest(

        @NotNull
        @Positive
        Long intervaloSegundos
) {
}
