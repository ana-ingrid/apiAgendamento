package com.br.agendamento.servico.controller;

import com.br.agendamento.servico.model.Servico;
import com.br.agendamento.servico.model.dtos.AlteraServicoDTO;
import com.br.agendamento.servico.model.dtos.CadastraServicoDTO;
import com.br.agendamento.servico.service.ServicoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "sevicos")
@AllArgsConstructor
public class ServicoController {

    private ServicoService servicoService;


    @GetMapping
    public ResponseEntity<Servico> consultaServico(@PathVariable String nomeServico){
        return ResponseEntity.status(200).body(servicoService.consultaServico(nomeServico, true));
    }

    @PostMapping
    public ResponseEntity<Servico> cadastraServico(@RequestBody @Valid CadastraServicoDTO cadastraServicoDTO){
        return ResponseEntity.status(201).body(servicoService.cadastraServico(cadastraServicoDTO));
    }

    @PutMapping
    public ResponseEntity<Servico> alteraServico(@RequestBody @Valid AlteraServicoDTO alteraServicoDTO){
        return ResponseEntity.status(200).body(servicoService.alteraServico(alteraServicoDTO));
    }



}
