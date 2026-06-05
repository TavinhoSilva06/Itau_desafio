package com.example.ITAUtask.service;

// DTO que contém os dados estatísticos retornados pelo service
import com.example.ITAUtask.dto.EstatisticaResponse;

// Modelo da transação
import com.example.ITAUtask.model.Transacao;

// Repositório em memória das transações
import com.example.ITAUtask.repository.TransacaoRepository;

// Anotação de teste do JUnit
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

// Métodos de asserção do JUnit
import static org.junit.jupiter.api.Assertions.*;

class EstatisticaServiceTest {

    /**
     * Testa o cenário onde não existem transações cadastradas.
     * O serviço deve retornar todos os valores estatísticos iguais a zero.
     */
    @Test
    void deveRetornarZerosQuandoNaoExistemTransacoes()
            throws Exception {

        // Cria um repositório vazio
        TransacaoRepository repository =
                new TransacaoRepository();

        // Injeta o repositório no service
        EstatisticaService service =
                new EstatisticaService(repository);

        // Obtém o atributo privado "intervaloSegundos"
        // usando Reflection ( Cria uma cópia do atributo para modifica-lo mesmo que seja private )
        Field campo =
                EstatisticaService.class
                        .getDeclaredField("intervaloSegundos");

        // Permite acessar um atributo privado
        campo.setAccessible(true);

        // Define o intervalo para 60 segundos
        campo.set(service, 60L);

        // Executa o cálculo das estatísticas
        EstatisticaResponse response =
                service.calcular();

        // Verifica se todos os valores retornados são zero
        assertEquals(0, response.count());
        assertEquals(0, response.sum().doubleValue());
        assertEquals(0, response.avg().doubleValue());
        assertEquals(0, response.min().doubleValue());
        assertEquals(0, response.max().doubleValue());
    }

    /**
     * Testa o cálculo correto das estatísticas
     * quando existem transações válidas.
     */
    @Test
    void deveCalcularEstatisticasCorretamente()
            throws Exception {

        // Cria o repositório
        TransacaoRepository repository =
                new TransacaoRepository();

        // Salva uma transação de R$100
        repository.salvar(
                new Transacao(
                        BigDecimal.valueOf(100),
                        OffsetDateTime.now()
                )
        );

        // Salva uma transação de R$200
        repository.salvar(
                new Transacao(
                        BigDecimal.valueOf(200),
                        OffsetDateTime.now()
                )
        );

        // Salva uma transação de R$300
        repository.salvar(
                new Transacao(
                        BigDecimal.valueOf(300),
                        OffsetDateTime.now()
                )
        );

        // Cria o serviço
        EstatisticaService service =
                new EstatisticaService(repository);

        // Acessa o atributo privado intervaloSegundos
        Field campo =
                EstatisticaService.class
                        .getDeclaredField("intervaloSegundos");


        // Permite acessar um atributo privado
        campo.setAccessible(true);

        // Define a janela de cálculo em 60 segundos
        campo.set(service, 60L);

        // Calcula as estatísticas
        EstatisticaResponse response =
                service.calcular();

        // Verifica os resultados esperados:
        // Soma = 600
        // Média = 200
        // Menor = 100
        // Maior = 300
        // Quantidade = 3
        assertEquals(3, response.count());
        assertEquals(600.0, response.sum().doubleValue());
        assertEquals(200.0, response.avg().doubleValue());
        assertEquals(100.0, response.min().doubleValue());
        assertEquals(300.0, response.max().doubleValue());
    }

    /**
     * Testa se o serviço ignora corretamente
     * transações que estão fora da janela de tempo.
     */
    @Test
    void deveIgnorarTransacoesForaDaJanelaDeTempo()
            throws Exception {

        // Cria o repositório
        TransacaoRepository repository =
                new TransacaoRepository();

        // Transação válida (agora)
        repository.salvar(
                new Transacao(
                        BigDecimal.valueOf(100),
                        OffsetDateTime.now()
                )
        );

        // Transação antiga (há 2 minutos)
        repository.salvar(
                new Transacao(
                        BigDecimal.valueOf(200),
                        OffsetDateTime.now().minusMinutes(2)
                )
        );

        // Cria o serviço
        EstatisticaService service =
                new EstatisticaService(repository);

        // Acessa o atributo privado intervaloSegundos
        Field campo =
                EstatisticaService.class
                        .getDeclaredField("intervaloSegundos");

        // Permite acessar um atributo privado
        campo.setAccessible(true);

        // Define a janela de cálculo para 60 segundos
        campo.set(service, 60L);

        // Executa o cálculo
        EstatisticaResponse response =
                service.calcular();

        // Apenas a transação de R$100 deve ser considerada
        assertEquals(1, response.count());
        assertEquals(100.0, response.sum().doubleValue());
        assertEquals(100.0, response.avg().doubleValue());
        assertEquals(100.0, response.min().doubleValue());
        assertEquals(100.0, response.max().doubleValue());
    }
}