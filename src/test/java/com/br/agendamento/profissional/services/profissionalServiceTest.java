package com.br.agendamento.profissional.services;

import com.br.agendamento.config.MensagensDeErros;
import com.br.agendamento.profissional.model.Profissional;
import com.br.agendamento.profissional.model.dtos.CadastraProfissionalDTO;
import com.br.agendamento.profissional.service.ProfissionalService;
import com.br.agendamento.usuario.exceptions.UsuarioCadastradoException;
import com.br.agendamento.usuario.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class profissionalServiceTest {

    @InjectMocks
    protected ProfissionalService profissionalService;

    @Mock
    protected ModelMapper modelMapper;

    @Mock
    protected UsuarioRepository usuarioRepository;


    @Test
    void cadastraProfissionalComSucessoNaBase() {

        CadastraProfissionalDTO profissionalDTO = objetoDeProfissionalDTO();
        Profissional profissional = objetoDeProfissional();

        Mockito.when(usuarioRepository.findByProfissional(profissionalDTO.getCodigoPessoa())).thenReturn(null);
        Mockito.when(modelMapper.map(profissionalDTO, Profissional.class)).thenReturn(profissional);
        Mockito.when(usuarioRepository.save(profissional)).thenReturn(profissional);


       Profissional resultado = profissionalService.cadastraProfissional(profissionalDTO);

       assertEquals(profissional, resultado);
       assertNotNull(resultado);
    }



    Profissional objetoDeProfissional(){
        return Profissional.builder()
                .nome("teste")
                .email("teste@gmail.com")
                .codigoPessoa("123456789")
                .dataNascimento(LocalDate.parse("2003-04-22"))
                .build();

    }

    CadastraProfissionalDTO objetoDeProfissionalDTO(){
        return CadastraProfissionalDTO.builder()
                .nome("teste")
                .email("teste@gmail.com")
                .codigoPessoa("123456789")
                .dataNascimento(LocalDate.parse("2003-04-22"))
                .build();

    }


}
