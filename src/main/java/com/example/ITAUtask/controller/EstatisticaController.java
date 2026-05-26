package com.example.ITAUtask.controller;

import com.example.ITAUtask.dto.EstatisticaResponse;
import com.example.ITAUtask.service.EstatisticaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EstatisticaController {

    private final EstatisticaService service;

    @GetMapping("/estatistica")
    public EstatisticaResponse buscar() {
        return service.calcular();
    }
}