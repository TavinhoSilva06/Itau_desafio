package com.example.ITAUtask.dto;

import java.math.BigDecimal;

public record EstatisticaResponse(
        long count,
        BigDecimal sum,
        BigDecimal avg,
        BigDecimal min,
        BigDecimal max
) {
}