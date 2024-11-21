package com.br.agendamento.profissional.service;

import com.br.agendamento.usuario.exceptions.UsuarioCadastradoException;
import com.br.agendamento.usuario.exceptions.UsuarioNaoExisteException;
import com.br.agendamento.config.MensagensDeErros;
import com.br.agendamento.profissional.model.Profissional;
import com.br.agendamento.profissional.model.dtos.AlteraProfissionalDTO;
import com.br.agendamento.profissional.model.dtos.CadastraProfissionalDTO;
import com.br.agendamento.usuario.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Objects;

@AllArgsConstructor
@Service
public class ProfissionalService {


    private UsuarioRepository usuarioRepository;
    private ModelMapper modelMapper;

    public boolean consultaSeProfissionalExiste(String codigoPessoa){
         Profissional buscaProfissional = usuarioRepository.findByUsuarioDoTipoProfissional(codigoPessoa);
        return !Objects.isNull(buscaProfissional);
    }

    public Profissional cadastraProfissional(CadastraProfissionalDTO cadastraProfissionalDTO){
        if (consultaSeProfissionalExiste(cadastraProfissionalDTO.getCodigoPessoa())){
            throw new UsuarioCadastradoException(MensagensDeErros.PROFISSIONALJACADASTRADO.getDescricao());
        }
        Profissional profissional = modelMapper.map(cadastraProfissionalDTO, Profissional.class);
        return usuarioRepository.save(profissional);
    }


  }
