package com.br.agendamento.resource;

import com.br.agendamento.model.Cliente;
import com.br.agendamento.model.dtos.CadastraClienteDTO;
import com.br.agendamento.service.ClienteService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/cadastro")
@AllArgsConstructor
public class ClienteController {

    protected final ClienteService clienteService;

    @PostMapping
    public ResponseEntity<Cliente> cadastraCliente(@Valid @RequestBody CadastraClienteDTO cliente) {
        return ResponseEntity.status(201).body(clienteService.cadastraCliente(cliente));
    }



}


