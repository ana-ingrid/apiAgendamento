package com.br.agendamento.profissional.services;

import com.br.agendamento.profissional.model.Profissional;
import com.br.agendamento.profissional.model.dtos.CadastraProfissionalDTO;
import com.br.agendamento.profissional.service.ProfissionalService;
import com.br.agendamento.usuario.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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


        CadastraProfissionalDTO profissionalDTO = new CadastraProfissionalDTO();
        profissionalDTO.setNome("teste");
        profissionalDTO.setCodigoPessoa("123456789");
        profissionalDTO.setEmail("teste@gmail.com");

        Profissional profissional = new Profissional();
        profissional.setNome("teste");
        profissional.setCodigoPessoa("123456789");
        profissional.setEmail("teste@gmail.com");

        Mockito.when(usuarioRepository.findByProfissional(profissionalDTO.getCodigoPessoa())).thenReturn(null);
        Mockito.when(modelMapper.map(profissionalDTO, Profissional.class)).thenReturn(profissional);
        Mockito.when(usuarioRepository.save(profissional)).thenReturn(profissional);


       Profissional resultado = profissionalService.cadastraProfissional(profissionalDTO);

       assertEquals(profissional, resultado);
       assertNotNull(resultado);
    }



}
