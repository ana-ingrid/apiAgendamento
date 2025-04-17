package com.br.agendamento.servico.service;

import com.br.agendamento.config.MensagensDeErros;
import com.br.agendamento.servico.exceptions.ServicoNaoEncontradoException;
import com.br.agendamento.servico.model.Servico;
import com.br.agendamento.servico.model.dtos.AlteraServicoDTO;
import com.br.agendamento.servico.model.dtos.CadastraServicoDTO;
import com.br.agendamento.servico.model.dtos.ServicoDTO;
import com.br.agendamento.servico.repository.ServicoRepository;
import com.br.agendamento.usuario.exceptions.UsuarioCadastradoException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ServicoService {

    protected ServicoRepository servicoRepository;
    protected ModelMapper modelMapper;

    public Servico consultaServico(Integer id) {
        return servicoRepository.findById(id)
                .orElseThrow(() -> new ServicoNaoEncontradoException("Serviço não localizado"));
    }

    public Servico cadastraServico(CadastraServicoDTO cadastraServicoDTO) {
        if (servicoRepository.existsByNomeServico(cadastraServicoDTO.getNomeServico())) {
            throw new UsuarioCadastradoException(MensagensDeErros.PROFISSIONALJACADASTRADO.getDescricao());
        }
        Servico objServico = modelMapper.map(cadastraServicoDTO, Servico.class);
        return servicoRepository.save(objServico);
    }

    public Servico alteraServico(Integer id, AlteraServicoDTO alteraServicoDTO) {
        Servico servicoExistente = consultaServico(id);
        modelMapper.map(alteraServicoDTO, servicoExistente);
        return servicoRepository.save(servicoExistente);
    }

    public void deletaServico(Integer id) {
        Servico servico = consultaServico(id);
        servicoRepository.delete(servico);
    }

    public List<ServicoDTO> listaServico() {
        return servicoRepository.findAll().stream()
                .map(servico -> modelMapper.map(servico, ServicoDTO.class))
                .collect(Collectors.toList());
    }

}

