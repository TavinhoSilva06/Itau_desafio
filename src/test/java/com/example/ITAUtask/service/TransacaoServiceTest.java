package com.example.ITAUtask.service;

import com.example.ITAUtask.dto.TransacaoRequest;
import com.example.ITAUtask.exception.TransacaoInvalidaException;
import com.example.ITAUtask.model.Transacao;
import com.example.ITAUtask.repository.TransacaoRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TransacaoServiceTest {

    @Test
    void deveSalvarTransacaoValida() {

        TransacaoRepository repository =
                new TransacaoRepository();

        EstatisticaConfiguracaoService configuracaoService =
                new EstatisticaConfiguracaoService(60L);

        TransacaoService service =
                new TransacaoService(repository, configuracaoService);

        TransacaoRequest request =
                new TransacaoRequest(
                        BigDecimal.valueOf(100),
                        OffsetDateTime.now()
                );

        service.registrar(request);

        assertEquals(
                1,
                repository.buscarTodas().size()
        );
    }

    @Test
    void deveLancarExcecaoParaDataFutura() {

        TransacaoRepository repository =
                new TransacaoRepository();

        EstatisticaConfiguracaoService configuracaoService =
                new EstatisticaConfiguracaoService(60L);

        TransacaoService service =
                new TransacaoService(repository, configuracaoService);

        TransacaoRequest request =
                new TransacaoRequest(
                        BigDecimal.valueOf(100),
                        OffsetDateTime.now().plusMinutes(10)
                );

        assertThrows(
                TransacaoInvalidaException.class,
                () -> service.registrar(request)
        );
    }

    @Test
    void deveLancarExcecaoParaDataForaDoLimite() {

        TransacaoRepository repository =
                new TransacaoRepository();

        EstatisticaConfiguracaoService configuracaoService =
                new EstatisticaConfiguracaoService(60L);

        TransacaoService service =
                new TransacaoService(repository, configuracaoService);

        TransacaoRequest request =
                new TransacaoRequest(
                        BigDecimal.valueOf(100),
                        OffsetDateTime.now().minusSeconds(61)
                );

        TransacaoInvalidaException exception = assertThrows(
                TransacaoInvalidaException.class,
                () -> service.registrar(request)
        );

        assertEquals(
                "Esta transação já passou do limite ",
                exception.getMessage()
        );
    }

    @Test
    void deveLimparTodasAsTransacoes() {

        TransacaoRepository repository =
                new TransacaoRepository();

        EstatisticaConfiguracaoService configuracaoService =
                new EstatisticaConfiguracaoService(60L);

        TransacaoService service =
                new TransacaoService(repository, configuracaoService);

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

        service.limpar();

        assertEquals(
                0,
                repository.buscarTodas().size()
        );
    }
}
