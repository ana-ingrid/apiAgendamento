package com.br.agendamento.cliente.services;


import com.br.agendamento.cliente.model.Cliente;
import com.br.agendamento.cliente.model.dtos.AlteraClienteDTO;
import com.br.agendamento.cliente.model.dtos.CadastraClienteDTO;
import com.br.agendamento.cliente.service.ClienteService;
import com.br.agendamento.config.MensagensDeErros;
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
 class ClienteServiceTest {

    @InjectMocks
    protected ClienteService clienteService;

    @Mock
    protected ModelMapper modelMapper;

    @Mock
    protected UsuarioRepository usuarioRepository;


    @Test
    void cadastraClienteComSucessoNaBase() {

        CadastraClienteDTO clienteDTO = objetoClienteDTO();

        Cliente cliente = objetoCliente();

        Mockito.when(modelMapper.map(clienteDTO, Cliente.class)).thenReturn(cliente);
        Mockito.when(usuarioRepository.findByCliente(clienteDTO.getCodigoPessoa())).thenReturn(null);
        Mockito.when(usuarioRepository.save(cliente)).thenReturn(cliente);

        Cliente clienteCadastrado = clienteService.cadastraCliente(clienteDTO);

        assertNotNull(clienteCadastrado);
        Mockito.verify(usuarioRepository, Mockito.times(2)).findByCliente(cliente.getCodigoPessoa());
        Mockito.verify(modelMapper, Mockito.times(1)).map(clienteDTO, Cliente.class);
        Mockito.verify(usuarioRepository, Mockito.times(1)).save(cliente);
    }

    @Test
    void erroAoTentarCadastrarClienteJaExistenteNaBase() {
        CadastraClienteDTO clienteDTO = objetoClienteDTO();

        Mockito.when(usuarioRepository.findByCliente(clienteDTO.getCodigoPessoa())).thenThrow(new UsuarioCadastradoException(MensagensDeErros.CLIENTEJACADASTRADO.getDescricao()));

        UsuarioCadastradoException exception = assertThrows(
                UsuarioCadastradoException.class, () -> clienteService.cadastraCliente(clienteDTO));

        assertEquals(MensagensDeErros.CLIENTEJACADASTRADO.getDescricao(), exception.getMessage());
        Mockito.verify(usuarioRepository, Mockito.times(1)).findByCliente(clienteDTO.getCodigoPessoa());
    }

    @Test
     void consultaClienteJaCadastradoNaBase() {
        Cliente cliente = objetoCliente();

        Mockito.when(usuarioRepository.findByCliente(cliente.getCodigoPessoa())).thenReturn(cliente);

        Cliente resultado = clienteService.consultaCliente(cliente.getCodigoPessoa());

        assertNotNull(resultado);
        Mockito.verify(usuarioRepository, Mockito.times(1)).findByCliente(cliente.getCodigoPessoa());
    }

    @Test
    void deveLancarExcecaoQuandoClienteNaoForEncontrado() {
        String codigoPessoa = "123456789";

        Mockito.when(usuarioRepository.findByCliente(codigoPessoa)).thenThrow(new UsuarioNaoExisteException(MensagensDeErros.CLIENTENAOEXISTE.getDescricao()));

        UsuarioNaoExisteException exception = assertThrows(UsuarioNaoExisteException.class, () -> clienteService.consultaCliente(codigoPessoa));


        assertEquals(MensagensDeErros.CLIENTENAOEXISTE.getDescricao(),exception.getMessage());
        Mockito.verify(usuarioRepository, Mockito.times(1)).findByCliente(codigoPessoa);
    }

    @Test
    void alteraTodosOsDadosDeClienteComSucesso(){

        Cliente cliente = objetoCliente();
        String codigoPessoa = "123456789";
        AlteraClienteDTO alteraClienteDTO = objetoDeAlteraClienteDTO();

        Mockito.when(usuarioRepository.findByCliente(codigoPessoa)).thenReturn(cliente);
        Mockito.when(usuarioRepository.save(Mockito.any(Cliente.class))).
                thenAnswer(invocation -> invocation.getArgument(0));

        Cliente resultado = clienteService.alteraCliente(alteraClienteDTO,codigoPessoa);

        Mockito.verify(usuarioRepository, Mockito.times(1)).findByCliente(codigoPessoa);
        Mockito.verify(usuarioRepository, Mockito.times(1)).save(cliente);
        assertEquals(resultado.getNome(),alteraClienteDTO.getNome());
        assertEquals(resultado.getEmail(),alteraClienteDTO.getEmail());
        assertEquals(resultado.getDataNascimento(),alteraClienteDTO.getDataNascimento());
    }
    

    @Test
    void NaoLocalizaClienteNaBaseAoTentarLocalizarUsuarioNaBase() {
        String codigoPessoa = "123456789";
        AlteraClienteDTO alteraClienteDTO = objetoDeAlteraClienteDTO();

        Mockito.when(usuarioRepository.findByCliente(codigoPessoa)).thenThrow(new UsuarioNaoExisteException(MensagensDeErros.CLIENTENAOEXISTE.getDescricao()));

        UsuarioNaoExisteException exception = assertThrows(
              UsuarioNaoExisteException.class, () -> clienteService.alteraCliente(alteraClienteDTO,codigoPessoa));

        Mockito.verify(usuarioRepository, Mockito.times(1)).findByCliente(codigoPessoa);
        assertEquals(MensagensDeErros.CLIENTENAOEXISTE.getDescricao(), exception.getMessage());
    }

    @Test
    void deletaClienteComSucessoNaBase() {

        Cliente cliente = objetoCliente();

        Mockito.when(usuarioRepository.findByCliente(cliente.getCodigoPessoa())).thenReturn(cliente);
        clienteService.deletaCliente(cliente.getCodigoPessoa());

        Mockito.verify(usuarioRepository, Mockito.times(1)).findByCliente(cliente.getCodigoPessoa());
        Mockito.verify(usuarioRepository, Mockito.times(1)).delete(cliente);
    }

    @Test
    void erroDeClienteNaoLocalizadoAoTentarDeletarUsuarioNaBaseQueNaoExiste() {
        String codigoPessoa = "123456789";

        Mockito.when(usuarioRepository.findByCliente(codigoPessoa)).thenThrow(new UsuarioNaoExisteException(MensagensDeErros.CLIENTENAOEXISTE.getDescricao()));

        UsuarioNaoExisteException exception = assertThrows(UsuarioNaoExisteException.class, () -> clienteService.deletaCliente(codigoPessoa));

        assertEquals(MensagensDeErros.CLIENTENAOEXISTE.getDescricao(), exception.getMessage());
        Mockito.verify(usuarioRepository, Mockito.times(1)).findByCliente(codigoPessoa);
    }

    Cliente objetoCliente(){
        return Cliente.builder()
                .nome("teste")
                .email("teste@gmail.com")
                .codigoPessoa("123456789")
                .dataNascimento(LocalDate.parse("2003-04-22"))
                .build();
    }

    CadastraClienteDTO objetoClienteDTO(){
        return CadastraClienteDTO.builder()
                .nome("teste")
                .email("teste@gmail.com")
                .codigoPessoa("123456789")
                .dataNascimento(LocalDate.parse("2003-04-22"))
                .build();
    }

    AlteraClienteDTO objetoDeAlteraClienteDTO(){
        return AlteraClienteDTO.builder()
                .nome("AlteraTeste")
                .email("emailalterado@gmail.com")
                .dataNascimento(LocalDate.parse("2003-04-22"))
                .build();
    }


}
