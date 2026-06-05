package com.example.ITAUtask.controller;

// DTO retornado pelo endpoint de estatísticas
import com.example.ITAUtask.dto.EstatisticaResponse;

// Serviço responsável por calcular as estatísticas
import com.example.ITAUtask.service.EstatisticaService;

// JUnit
import org.junit.jupiter.api.Test;

// Injeta componentes do Spring no teste
import org.springframework.beans.factory.annotation.Autowired;

// Carrega apenas a camada web para testes
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

// Cria mocks gerenciados pelo Spring
import org.springframework.test.context.bean.override.mockito.MockitoBean;

// Ferramenta para simular requisições HTTP
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

// Mockito
import static org.mockito.Mockito.when;

// Construtor de requisições GET
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

// Validação de respostas HTTP
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Carrega apenas o EstatisticaController
 * para testes da camada web.
 */
@WebMvcTest(EstatisticaController.class)
class EstatisticaControllerTest {

    /**
     * Simulador de chamadas HTTP.
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * Mock do serviço.
     * Não executa a lógica real.
     */
    @MockitoBean
    private EstatisticaService service;

    /**
     * Testa o endpoint:
     *
     * GET /estatistica
     *
     * Deve retornar HTTP 200 OK.
     */
    @Test
    void deveRetornarEstatisticas() throws Exception {

        // Define o comportamento do mock
        when(service.calcular())
                .thenReturn(
                        new EstatisticaResponse(
                                1,                          // count
                                BigDecimal.valueOf(100),   // sum
                                BigDecimal.valueOf(100),   // avg
                                BigDecimal.valueOf(100),   // min
                                BigDecimal.valueOf(100)    // max
                        )
                );

        // Simula uma chamada GET
        mockMvc.perform(
                        get("/estatistica")
                )
                // Espera HTTP 200
                .andExpect(status().isOk());
    }
}