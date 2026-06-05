package com.example.ITAUtask.service;

// DTO recebido pela API para criação de uma transação
import com.example.ITAUtask.dto.TransacaoRequest;

// Exceção lançada quando uma transação é inválida
import com.example.ITAUtask.exception.TransacaoInvalidaException;

// Modelo da entidade Transacao
import com.example.ITAUtask.model.Transacao;

// Repositório em memória das transações
import com.example.ITAUtask.repository.TransacaoRepository;

// Anotação de teste do JUnit
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

// Métodos de validação do JUnit
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TransacaoServiceTest {

    /**
     * Testa se uma transação válida é salva corretamente.
     */
    @Test
    void deveSalvarTransacaoValida() {

        // Cria o repositório em memória
        TransacaoRepository repository =
                new TransacaoRepository();

        // Cria o serviço que será testado
        TransacaoService service =
                new TransacaoService(repository);

        // Monta uma requisição válida
        TransacaoRequest request =
                new TransacaoRequest(
                        BigDecimal.valueOf(100), // valor da transação
                        OffsetDateTime.now()     // data atual
                );

        // Executa o metodo de cadastro
        service.registrar(request);

        // Verifica se uma transação foi salva
        assertEquals(
                1,
                repository.buscarTodas().size()
        );
    }

    /**
     * Testa se o sistema rejeita uma transação
     * cuja data está no futuro.
     */
    @Test
    void deveLancarExcecaoParaDataFutura() {

        // Cria o repositório
        TransacaoRepository repository =
                new TransacaoRepository();

        // Cria o serviço
        TransacaoService service =
                new TransacaoService(repository);

        // Cria uma transação com data futura (+10 minutos)
        TransacaoRequest request =
                new TransacaoRequest(
                        BigDecimal.valueOf(100),
                        OffsetDateTime.now().plusMinutes(10)
                );

        // Verifica se a exceção esperada é lançada
        assertThrows(
                TransacaoInvalidaException.class,
                () -> service.registrar(request)
        );
    }

    /**
     * Testa se o metodo limpar()
     * remove todas as transações armazenadas.
     */
    @Test
    void deveLimparTodasAsTransacoes() {

        // Cria o repositório
        TransacaoRepository repository =
                new TransacaoRepository();

        // Cria o serviço
        TransacaoService service =
                new TransacaoService(repository);

        // Adiciona uma transação manualmente
        repository.salvar(
                new Transacao(
                        BigDecimal.valueOf(100),
                        OffsetDateTime.now()
                )
        );

        // Adiciona outra transação manualmente
        repository.salvar(
                new Transacao(
                        BigDecimal.valueOf(200),
                        OffsetDateTime.now()
                )
        );

        // Executa a limpeza do repositório
        service.limpar();

        // Verifica se nenhuma transação permaneceu armazenada
        assertEquals(
                0,
                repository.buscarTodas().size()
        );
    }
}