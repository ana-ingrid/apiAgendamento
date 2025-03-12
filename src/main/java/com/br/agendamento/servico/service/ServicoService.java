package com.br.agendamento.servico.service;

import com.br.agendamento.config.MensagensDeErros;
import com.br.agendamento.servico.exceptions.ServicoNaoEncontradoException;
import com.br.agendamento.servico.model.Servico;
import com.br.agendamento.servico.model.dtos.CadastraServicoDTO;
import com.br.agendamento.servico.repository.ServicoRepository;
import com.br.agendamento.usuario.exceptions.UsuarioCadastradoException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class ServicoService {

    protected ServicoRepository servicoRepository;
    protected ModelMapper modelMapper;

    public Servico consultaServico(String nomeServico, boolean boleano) {
        Servico servico = servicoRepository.findByServico(nomeServico);
        if (Objects.isNull(servico) && boleano){
            throw new ServicoNaoEncontradoException("Objeto servico não encontrado");
        }
        return servico;
    }

    public Servico cadastraServico(CadastraServicoDTO cadastraServicoDTO){
        if (Boolean.TRUE.equals(consultaServico(cadastraServicoDTO.getNomeServico(), true))){
            Servico objServico = modelMapper.map(cadastraServicoDTO, Servico.class);
            servicoRepository.save(objServico);
        }
        throw new UsuarioCadastradoException(MensagensDeErros.PROFISSIONALJACADASTRADO.getDescricao());
    }





}
