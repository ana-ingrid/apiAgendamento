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

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Service
public class ProfissionalService {

    private UsuarioRepository usuarioRepository;
    private ModelMapper modelMapper;

    public Profissional consultaProfissionalOuValida(String codigoPessoa, Boolean deveLancarExcecao){
        Profissional profissional = usuarioRepository.findByProfissional(codigoPessoa);
        if (Objects.isNull(profissional) && deveLancarExcecao) {
            throw new UsuarioNaoExisteException(MensagensDeErros.PROFISSIONALNAOEXISTE.getDescricao());
        }
        return profissional;
    }


    public Profissional cadastraProfissional(CadastraProfissionalDTO cadastraProfissionalDTO){
        String codigoPessoa = cadastraProfissionalDTO.getCodigoPessoa();

        if (!Objects.isNull(consultaProfissionalOuValida(codigoPessoa, false))){
            throw new UsuarioCadastradoException(MensagensDeErros.PROFISSIONALJACADASTRADO.getDescricao());
        }
        Profissional profissional = modelMapper.map(cadastraProfissionalDTO, Profissional.class);
        return usuarioRepository.save(profissional);
    }


    public List<Profissional> consultaTodosProfissionais(){
        return usuarioRepository.findByTodosUsuarioDoTipoProfissional();
    }

    public Profissional alteraProfissional(AlteraProfissionalDTO alteraProfissionalDTO, String codigo){
        Profissional profissional = usuarioRepository.findByProfissional(codigo);
        if (Objects.isNull(profissional)) throw new UsuarioNaoExisteException(MensagensDeErros.PROFISSIONALNAOEXISTE.getDescricao());

        if (Objects.nonNull(alteraProfissionalDTO.getDataNascimento()))profissional.setDataNascimento(alteraProfissionalDTO.getDataNascimento());
        if (Objects.nonNull(alteraProfissionalDTO.getNome()))profissional.setNome(alteraProfissionalDTO.getNome());
        if (Objects.nonNull(alteraProfissionalDTO.getEmail()))profissional.setEmail(alteraProfissionalDTO.getEmail());
        return usuarioRepository.save(profissional);
    }

    public void deletaProfissional(String codigoPessoa){
        Profissional profissional = usuarioRepository.findByProfissional(codigoPessoa);
        if (Objects.isNull(profissional))throw new UsuarioNaoExisteException(MensagensDeErros.PROFISSIONALNAOEXISTE.getDescricao());
        usuarioRepository.delete(profissional);
    }

}
