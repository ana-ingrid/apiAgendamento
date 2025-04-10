package com.br.agendamento.servico.service;

import com.br.agendamento.config.MensagensDeErros;
import com.br.agendamento.servico.exceptions.ServicoNaoEncontradoException;
import com.br.agendamento.servico.model.Servico;
import com.br.agendamento.servico.model.dtos.AlteraServicoDTO;
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

    public Servico consultaServico(Integer id) {
        return servicoRepository.findById(id)
                .orElseThrow(() -> new ServicoNaoEncontradoException("Serviço não localizado"));
    }

    public Servico cadastraServico(CadastraServicoDTO cadastraServicoDTO){
        if (!Objects.isNull(consultaServico(cadastraServicoDTO.getNomeServico(),false))){
            throw new UsuarioCadastradoException(MensagensDeErros.PROFISSIONALJACADASTRADO.getDescricao());
        }
        Servico objServico = modelMapper.map(cadastraServicoDTO, Servico.class);
        return servicoRepository.save(objServico);
    }

    public Servico alteraServico(AlteraServicoDTO alteraServicoDTO) {
        Servico servicoConsultado = consultaServico(alteraServicoDTO.getNomeServico(), true);
        Servico objMapeado = modelMapper.map(alteraServicoDTO, Servico.class);
        modelMapper.map(objMapeado, servicoConsultado);
        return servicoRepository.save(objMapeado);
    }
}
