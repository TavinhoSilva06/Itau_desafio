package com.example.ITAUtask.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Service
public class EstatisticaConfiguracaoService {

    private final AtomicLong intervaloSegundos;

    public EstatisticaConfiguracaoService(
            @Value("${estatistica.intervalo-segundos}") long intervaloInicial
    ) {
        this.intervaloSegundos = new AtomicLong(intervaloInicial);
    }

    public long buscarIntervaloSegundos() {
        return intervaloSegundos.get();
    }

    public long atualizarIntervaloSegundos(long novoIntervaloSegundos) {
        intervaloSegundos.set(novoIntervaloSegundos);

        log.info(
                "Intervalo de estatisticas atualizado para {} segundos",
                novoIntervaloSegundos
        );

        return intervaloSegundos.get();
    }
}
