package com.example.ITAUtask.service;

import com.example.ITAUtask.exception.TransacaoInvalidaException;
import com.example.ITAUtask.model.Transacao;
import com.example.ITAUtask.dto.TransacaoRequest;
import com.example.ITAUtask.repository.TransacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class TransacaoService {

    private final TransacaoRepository repository;

    public void registrar(TransacaoRequest request) {

        if (request.dataHora().isAfter(OffsetDateTime.now())) {
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