package com.br.agendamento.cliente.resource;

import com.br.agendamento.cliente.model.Cliente;
import com.br.agendamento.cliente.model.dtos.AlteraClienteDTO;
import com.br.agendamento.cliente.model.dtos.CadastraClienteDTO;
import com.br.agendamento.cliente.service.ClienteService;
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
    public ResponseEntity<Cliente> cadastraCliente(@Valid @RequestBody CadastraClienteDTO clienteDTO) {
        return ResponseEntity.status(201).body(clienteService.cadastraCliente(clienteDTO));
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Cliente> consultaCliente(@PathVariable String codigo){
        return ResponseEntity.status(200).body(clienteService.consultaCliente(codigo));
    }

    @PutMapping("{codigo}")
    public ResponseEntity<Cliente> alteraCliente(@Valid @RequestBody AlteraClienteDTO clienteDTO, @Valid @PathVariable String codigo){
        return ResponseEntity.status(200).body(clienteService.alteraCliente(clienteDTO, codigo));
    }

    public ResponseEntity<Void> deletaCliente(@PathVariable  String codigo){
        clienteService.deletaCliente(codigo);
        return ResponseEntity.noContent().build();
    }

}


