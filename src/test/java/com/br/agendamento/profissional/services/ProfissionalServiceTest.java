package com.br.agendamento.profissional.services;

import com.br.agendamento.config.MensagensDeErros;
import com.br.agendamento.profissional.model.Profissional;
import com.br.agendamento.profissional.model.dtos.AlteraProfissionalDTO;
import com.br.agendamento.profissional.model.dtos.CadastraProfissionalDTO;
import com.br.agendamento.profissional.service.ProfissionalService;
import com.br.agendamento.usuario.exceptions.UsuarioCadastradoException;
import com.br.agendamento.usuario.exceptions.UsuarioNaoExisteException;
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
class ProfissionalServiceTest {

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

    @Test
    void erroAoTentarCadastrarProfissionalJaExistenteNaBase(){

        CadastraProfissionalDTO profissionalDTO = objetoDeProfissionalDTO();

        Mockito.when(usuarioRepository.findByProfissional(profissionalDTO.getCodigoPessoa()))
                .thenThrow(new UsuarioCadastradoException(MensagensDeErros.PROFISSIONALJACADASTRADO.getDescricao()));

        UsuarioCadastradoException exception = assertThrows(
                UsuarioCadastradoException.class, () -> profissionalService.cadastraProfissional(profissionalDTO));

        assertEquals("Profissional já cadastrado", exception.getMessage());
        Mockito.verify(usuarioRepository, Mockito.times(1)).findByProfissional(profissionalDTO.getCodigoPessoa());
    }

    @Test
    void consultaProfissionalJaCadastradoNaBase(){

        Profissional profissional = objetoDeProfissional();

        String codigoPessoa = "123456789";

       Mockito.when(usuarioRepository.findByProfissional(codigoPessoa)).thenReturn(profissional);

       Profissional resultado = profissionalService.consultaProfissional(codigoPessoa);

       assertNotNull(resultado);
       Mockito.verify(usuarioRepository, Mockito.times(1)).findByProfissional(codigoPessoa);
       assertEquals(resultado.getCodigoPessoa(), codigoPessoa);

    }


    @Test
    void erroAoConsultarProfissionalQueNaoExisteNaBase() {

        String codigoPessoa = "123456789";

        Mockito.when(usuarioRepository.findByProfissional(codigoPessoa)).thenThrow(new UsuarioNaoExisteException(MensagensDeErros.PROFISSIONALNAOEXISTE.getDescricao()));

        UsuarioNaoExisteException exception = assertThrows(
                UsuarioNaoExisteException.class, () -> profissionalService.consultaProfissional(codigoPessoa));

        assertEquals(MensagensDeErros.PROFISSIONALNAOEXISTE.getDescricao(), exception.getMessage());
        Mockito.verify(usuarioRepository, Mockito.times(1)).findByProfissional(codigoPessoa);

    }

    @Test
    void alteraTodosOsDadosDeProfissionalComSucesso(){

        Profissional profissional = objetoDeProfissional();
        String codigoPessoa = "123456789";
        AlteraProfissionalDTO alteraProfissionalDTO = objetoDeAlteraProfissionalDTO();

        Mockito.when(usuarioRepository.findByProfissional(codigoPessoa)).thenReturn(profissional);
        Mockito.when(usuarioRepository.save(Mockito.any(Profissional.class))).
                thenAnswer(invocation -> invocation.getArgument(0));

        Profissional resultado = profissionalService.alteraProfissional(alteraProfissionalDTO,codigoPessoa);

        Mockito.verify(usuarioRepository, Mockito.times(1)).findByProfissional(codigoPessoa);
        Mockito.verify(usuarioRepository, Mockito.times(1)).save(profissional);
        assertEquals(resultado.getNome(),alteraProfissionalDTO.getNome());
        assertEquals(resultado.getEmail(),alteraProfissionalDTO.getEmail());
        assertEquals(resultado.getDataNascimento(),alteraProfissionalDTO.getDataNascimento());
        assertInstanceOf(Profissional.class, resultado);
    }

    @Test
    void NaoDeveAlterarProfissionalQuandoUsuarioNaoExistir() {
        String codigoPessoa = "123456789";
        AlteraProfissionalDTO alteraProfissionalDTO = objetoDeAlteraProfissionalDTO();

        Mockito.when(usuarioRepository.findByProfissional(codigoPessoa)).thenReturn(null);

        UsuarioNaoExisteException exception = assertThrows(
                UsuarioNaoExisteException.class, () -> profissionalService.alteraProfissional(alteraProfissionalDTO, codigoPessoa));

        Mockito.verify(usuarioRepository, Mockito.times(1)).findByProfissional(codigoPessoa);

        assertNotNull(exception);
        assertInstanceOf(UsuarioNaoExisteException.class, exception);
        assertEquals(MensagensDeErros.PROFISSIONALNAOEXISTE.getDescricao(), exception.getMessage());
    }



    @Test
    void deletaProfissionalNaBaseComSucesso() {
        Profissional profissional = objetoDeProfissional();
        String codigoPessoa = "123456789";

        Mockito.when(usuarioRepository.findByProfissional(codigoPessoa)).thenReturn(profissional);
        Mockito.doNothing().when(usuarioRepository).delete(profissional);

        profissionalService.deletaProfissional(codigoPessoa);

        Mockito.verify(usuarioRepository, Mockito.times(1)).findByProfissional(codigoPessoa);
        Mockito.verify(usuarioRepository, Mockito.times(1)).delete(profissional);
    }

    @Test
    void erroAoTentarDeletarProfissionalQueNaoExisteNaBase(){
        String codigoPessoa = "123456789";

        Mockito.when(usuarioRepository.findByProfissional(codigoPessoa))
                .thenThrow(new UsuarioNaoExisteException(MensagensDeErros.PROFISSIONALNAOEXISTE.getDescricao()));

        UsuarioNaoExisteException exception = assertThrows(
                UsuarioNaoExisteException.class, () -> profissionalService.deletaProfissional(codigoPessoa));

        assertEquals( MensagensDeErros.PROFISSIONALNAOEXISTE.getDescricao(), exception.getMessage());
        Mockito.verify(usuarioRepository, Mockito.times( 0)).delete(Mockito.any(Profissional.class));
        Mockito.verify(usuarioRepository, Mockito.times(1)).findByProfissional(codigoPessoa);
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

    AlteraProfissionalDTO objetoDeAlteraProfissionalDTO(){
        return AlteraProfissionalDTO.builder()
                .nome("AlteraTeste")
                .email("emailalterado@gmail.com")
                .dataNascimento(LocalDate.parse("2003-04-22"))
                .build();
    }

}
