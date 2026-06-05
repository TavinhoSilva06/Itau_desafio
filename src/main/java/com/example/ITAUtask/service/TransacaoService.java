package com.example.ITAUtask.service;

import com.example.ITAUtask.exception.TransacaoInvalidaException;
import com.example.ITAUtask.model.Transacao;
import com.example.ITAUtask.dto.TransacaoRequest;
import com.example.ITAUtask.repository.TransacaoRepository;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Slf4j // Anotação do Lombok que cria o comando 'log' automaticament
@Service
@RequiredArgsConstructor
public class TransacaoService {

    private final TransacaoRepository repository;

    public void registrar(TransacaoRequest request) {

        // Adicionando um limite de 5 segundos a frente do horário atual por conta do servidor Render
        OffsetDateTime agora = OffsetDateTime.now().plusSeconds(5);

        log.info("Data recebida: {}", request.dataHora());
        log.info("Agora servidor: {}", OffsetDateTime.now());

        if (request.dataHora().isAfter(agora)) {
            throw new TransacaoInvalidaException(
                    "Data e hora não podem estar no futuro"
            );
        }

        Transacao transacao = new Transacao(
                request.valor(),
                request.dataHora()
        );

        repository.salvar(transacao);
    }

    public void limpar() {

        repository.limpar();
    }
}