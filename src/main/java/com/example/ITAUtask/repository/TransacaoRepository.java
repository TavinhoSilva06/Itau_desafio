package com.example.ITAUtask.repository;

import com.example.ITAUtask.model.Transacao;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TransacaoRepository {

    private final List<Transacao> transacoes = new ArrayList<>();

    public void salvar(Transacao transacao) {
        transacoes.add(transacao);
    }

    public List<Transacao> buscarTodas() {
        return transacoes;
    }

    public void limpar() {
        transacoes.clear();
    }
}