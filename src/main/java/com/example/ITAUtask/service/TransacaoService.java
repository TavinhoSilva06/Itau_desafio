package com.example.ITAUtask.service;

import com.example.ITAUtask.exception.TransacaoInvalidaException;
import com.example.ITAUtask.model.Transacao;
import com.example.ITAUtask.dto.TransacaoRequest;
import com.example.ITAUtask.repository.TransacaoRepository;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Slf4j // Anotação do Lombok que cria o comando 'log' automaticamente
@Service
@RequiredArgsConstructor
public class TransacaoService {

    private final TransacaoRepository repository;

    public void registrar(TransacaoRequest request) {


        OffsetDateTime data = OffsetDateTime.now();

        log.info("Data recebida: {}", request.dataHora());
        log.info("Agora servidor: {}", data);

        if (request.dataHora().isAfter(data)) {

            log.warn(
                    "Tentativa de cadastrar transação futura: {}",
                    request.dataHora()
            );

            throw new TransacaoInvalidaException(
                    "Data e hora não podem estar no futuro"
            );
        }

        Transacao transacao = new Transacao(
                request.valor(),
                request.dataHora()
        );

        repository.salvar(transacao);

        log.info(
                "Transação salva com sucesso"
        );
    }

    public void limpar() {

        log.info(
                "Todas as transações foram removidas"
        );

        repository.limpar();
    }
}