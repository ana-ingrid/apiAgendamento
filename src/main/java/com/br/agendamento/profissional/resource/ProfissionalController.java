package com.br.agendamento.profissional.resource;


import com.br.agendamento.profissional.model.Profissional;
import com.br.agendamento.profissional.model.dtos.AlteraProfissionalDTO;
import com.br.agendamento.profissional.model.dtos.CadastraProfissionalDTO;
import com.br.agendamento.profissional.service.ProfissionalService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @GetMapping("{codigo}")
    public ResponseEntity<Profissional> consultaProfissional(@PathVariable String codigoPessoa){
        return ResponseEntity.status(200).body(profissionalService.consultaProfissional(codigoPessoa));
    }

    @GetMapping
    public ResponseEntity<Page<Profissional>> filtraProfissional(@RequestParam(required = true) String nome,
                                                                 @RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "10") int size,
                                                                 @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return ResponseEntity.ok(profissionalService.filtraProfissional(nome, pageable));
    }


    @PutMapping("{codigo}")
    public ResponseEntity<Profissional> alteraProfissional( @Valid @RequestBody AlteraProfissionalDTO alteraProfissionalDTO, @PathVariable String codigo){
        return ResponseEntity.status(200).body(profissionalService.alteraProfissional(alteraProfissionalDTO, codigo));
    }

    @DeleteMapping("{codigo}")
    public ResponseEntity<Profissional> deletaProfissional(@PathVariable String codigoPessoa){
        profissionalService.deletaProfissional(codigoPessoa);
        return ResponseEntity.noContent().build();
    }


}
