package com.example.ITAUtask.service;

import com.example.ITAUtask.dto.EstatisticaResponse;
import com.example.ITAUtask.repository.TransacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.util.DoubleSummaryStatistics;

@Service
@RequiredArgsConstructor
public class EstatisticaService {

    private final TransacaoRepository repository;

    @Value("${estatistica.intervalo-segundos}")
    private long intervaloSegundos;

    public EstatisticaResponse calcular() {

        OffsetDateTime limite = OffsetDateTime.now()
                .minusSeconds(intervaloSegundos);

        DoubleSummaryStatistics stats = repository.buscarTodas()
                .stream()
                .filter(t -> t.getDataHora().isAfter(limite))
                .mapToDouble(t -> t.getValor().doubleValue())
                .summaryStatistics();

        if (stats.getCount() == 0) {
            return new EstatisticaResponse(
                    0,
                    BigDecimal.ZERO,
                    BigDecimal.ZERO,
                    BigDecimal.ZERO,
                    BigDecimal.ZERO
            );
        }

        return new EstatisticaResponse(
                stats.getCount(),
                BigDecimal.valueOf(stats.getSum()),
                BigDecimal.valueOf(stats.getAverage())
                        .setScale(2, RoundingMode.HALF_UP),
                BigDecimal.valueOf(stats.getMin()),
                BigDecimal.valueOf(stats.getMax())
        );
    }
}