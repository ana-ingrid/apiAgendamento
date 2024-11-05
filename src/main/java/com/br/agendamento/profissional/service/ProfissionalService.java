package com.br.agendamento.profissional.service;

import com.br.agendamento.profissional.model.Profissional;
import com.br.agendamento.usuario.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@AllArgsConstructor
@Service
public class ProfissionalService {


    private UsuarioRepository usuarioRepository;

    public boolean consultaSeProfissionalExiste(String codigoPessoa){
         Profissional buscaProfissional = usuarioRepository.findByUsuarioDoTipoProfissional(codigoPessoa);
        return Objects.isNull(buscaProfissional);
    }




}
