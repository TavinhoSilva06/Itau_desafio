package com.example.ITAUtask.controller;

// Serviço utilizado pelo controller
import com.example.ITAUtask.exception.TransacaoInvalidaException;
import com.example.ITAUtask.service.TransacaoService;

// JUnit
import org.junit.jupiter.api.Test;

// Injeta objetos do Spring no teste
import org.springframework.beans.factory.annotation.Autowired;

// Carrega apenas a camada web (Controller)
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

// Utilizado para definir o Content-Type da requisição
import org.springframework.http.MediaType;

// Cria um mock do Service dentro do contexto Spring
import org.springframework.test.context.bean.override.mockito.MockitoBean;

// Simula requisições HTTP sem subir servidor real
import org.springframework.test.web.servlet.MockMvc;

// Mockito
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

// Métodos para construir requisições HTTP
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

// Métodos para validar respostas HTTP
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Carrega somente o TransacaoController.
 * Nenhum Service ou Repository real é carregado.
 */
@WebMvcTest(TransacaoController.class)
class TransacaoControllerTest {

    /**
     * Ferramenta que simula chamadas HTTP.
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * Mock do serviço.
     * Evita executar a lógica real durante o teste.
     */
    @MockitoBean
    private TransacaoService service;

    /**
     * Testa o endpoint:
     *
     * POST /transacao
     *
     * Deve retornar HTTP 201 Created.
     */
    @Test
    void deveCriarTransacao() throws Exception {

        // Simula comportamento do metodo registrar()
        doNothing().when(service)
                .registrar(any());

        // Corpo JSON enviado na requisição
        String json = """
                {
                    "valor": 100.50,
                    "dataHora": "2026-06-05T10:00:00-03:00"
                }
                """;

        // Simula uma requisição POST
        mockMvc.perform(
                        post("/transacao")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                // Espera receber HTTP 201
                .andExpect(status().isCreated());
    }

    /**
     * Testa o endpoint:
     *
     * DELETE /transacao
     *
     * Deve retornar HTTP 200 OK.
     */
    @Test
    void deveLimparTransacoes() throws Exception {

        // Simula o metodo limpar()
        doNothing().when(service).limpar();

        // Simula uma requisição DELETE
        mockMvc.perform(
                        org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                                .delete("/transacao")
                )
                // Espera HTTP 200
                .andExpect(status().isOk());
    }

    /**
     * Testa se o endpoint POST /transacao
     * retorna HTTP 422 quando a data da
     * transação está no futuro.
     */
    @Test
    void deveRetornar422QuandoDataForFutura() throws Exception {

        // Configura o mock do serviço para lançar
        // uma TransacaoInvalidaException sempre que
        // o metodo registrar() for chamado.
        doThrow(
                new TransacaoInvalidaException(
                        "Data e hora não podem estar no futuro"
                )
        )
                .when(service)
                .registrar(any());

        // JSON enviado na requisição.
        // A data está no futuro propositalmente.
        String json = """
        {
            "valor": 100,
            "dataHora": "2030-01-01T10:00:00-03:00"
        }
        """;

        // Simula uma requisição POST para /transacao
        mockMvc.perform(
                        post("/transacao")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                // Verifica se a API respondeu com HTTP 422
                .andExpect(status().isUnprocessableEntity());
    }
}