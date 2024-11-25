package com.br.agendamento.profissional.resource;


import com.br.agendamento.profissional.model.Profissional;
import com.br.agendamento.profissional.model.dtos.AlteraProfissionalDTO;
import com.br.agendamento.profissional.model.dtos.CadastraProfissionalDTO;
import com.br.agendamento.profissional.service.ProfissionalService;
import com.br.agendamento.usuario.repository.UsuarioRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/profissional")
@AllArgsConstructor
public class ProfissionalController {


    private ProfissionalService profissionalService;


    @PostMapping(value = "{codigo}")
    public ResponseEntity<Profissional> cadastraProfissional(@Valid @RequestBody CadastraProfissionalDTO cadastraProfissionalDTO){
        return ResponseEntity.status(201).body(profissionalService.cadastraProfissional(cadastraProfissionalDTO));
    }


}
