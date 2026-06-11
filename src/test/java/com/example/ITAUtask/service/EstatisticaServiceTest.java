package com.example.ITAUtask.service;

import com.example.ITAUtask.dto.EstatisticaResponse;
import com.example.ITAUtask.model.Transacao;
import com.example.ITAUtask.repository.TransacaoRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EstatisticaServiceTest {

    @Test
    void deveRetornarZerosQuandoNaoExistemTransacoes() {

        TransacaoRepository repository =
                new TransacaoRepository();

        EstatisticaConfiguracaoService configuracaoService =
                new EstatisticaConfiguracaoService(60L);

        EstatisticaService service =
                new EstatisticaService(repository, configuracaoService);

        EstatisticaResponse response =
                service.calcular();

        assertEquals(0, response.count());
        assertEquals(0, response.sum().doubleValue());
        assertEquals(0, response.avg().doubleValue());
        assertEquals(0, response.min().doubleValue());
        assertEquals(0, response.max().doubleValue());
    }

    @Test
    void deveCalcularEstatisticasCorretamente() {

        TransacaoRepository repository =
                new TransacaoRepository();

        repository.salvar(
                new Transacao(
                        BigDecimal.valueOf(100),
                        OffsetDateTime.now()
                )
        );

        repository.salvar(
                new Transacao(
                        BigDecimal.valueOf(200),
                        OffsetDateTime.now()
                )
        );

        repository.salvar(
                new Transacao(
                        BigDecimal.valueOf(300),
                        OffsetDateTime.now()
                )
        );

        EstatisticaConfiguracaoService configuracaoService =
                new EstatisticaConfiguracaoService(60L);

        EstatisticaService service =
                new EstatisticaService(repository, configuracaoService);

        EstatisticaResponse response =
                service.calcular();

        assertEquals(3, response.count());
        assertEquals(600.0, response.sum().doubleValue());
        assertEquals(200.0, response.avg().doubleValue());
        assertEquals(100.0, response.min().doubleValue());
        assertEquals(300.0, response.max().doubleValue());
    }

    @Test
    void deveIgnorarTransacoesForaDaJanelaDeTempo() {

        TransacaoRepository repository =
                new TransacaoRepository();

        repository.salvar(
                new Transacao(
                        BigDecimal.valueOf(100),
                        OffsetDateTime.now()
                )
        );

        repository.salvar(
                new Transacao(
                        BigDecimal.valueOf(200),
                        OffsetDateTime.now().minusMinutes(2)
                )
        );

        EstatisticaConfiguracaoService configuracaoService =
                new EstatisticaConfiguracaoService(60L);

        EstatisticaService service =
                new EstatisticaService(repository, configuracaoService);

        EstatisticaResponse response =
                service.calcular();

        assertEquals(1, response.count());
        assertEquals(100.0, response.sum().doubleValue());
        assertEquals(100.0, response.avg().doubleValue());
        assertEquals(100.0, response.min().doubleValue());
        assertEquals(100.0, response.max().doubleValue());
    }

    @Test
    void deveUsarIntervaloAtualizadoDinamicamente() {

        TransacaoRepository repository =
                new TransacaoRepository();

        repository.salvar(
                new Transacao(
                        BigDecimal.valueOf(100),
                        OffsetDateTime.now().minusSeconds(90)
                )
        );

        EstatisticaConfiguracaoService configuracaoService =
                new EstatisticaConfiguracaoService(60L);

        EstatisticaService service =
                new EstatisticaService(repository, configuracaoService);

        assertEquals(0, service.calcular().count());

        configuracaoService.atualizarIntervaloSegundos(120L);

        assertEquals(1, service.calcular().count());
    }
}
