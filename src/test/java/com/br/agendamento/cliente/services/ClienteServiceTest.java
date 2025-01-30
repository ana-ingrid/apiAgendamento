package com.br.agendamento.cliente.services;


import com.br.agendamento.config.MensagensDeErros;
import com.br.agendamento.usuario.exceptions.UsuarioCadastradoException;
import com.br.agendamento.usuario.exceptions.UsuarioNaoExisteException;
import com.br.agendamento.cliente.model.Cliente;
import com.br.agendamento.cliente.model.dtos.CadastraClienteDTO;
import com.br.agendamento.usuario.repository.UsuarioRepository;
import com.br.agendamento.cliente.service.ClienteService;
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
        Mockito.when(clienteService.cadastraCliente(clienteDTO)).thenReturn(cliente);

        Cliente clienteCadastrado = clienteService.cadastraCliente(clienteDTO);

        assertNotNull(clienteCadastrado);
        assertEquals(clienteDTO.getNome(), clienteCadastrado.getNome());
        assertEquals(clienteDTO.getEmail(), clienteCadastrado.getEmail());
        assertEquals(clienteDTO.getCodigoPessoa(), clienteCadastrado.getCodigoPessoa());
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

        assertEquals(cliente.getNome(), resultado.getNome());
        assertEquals(cliente.getCodigoPessoa(), resultado.getCodigoPessoa());

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
    void deletaClienteComSucessoNaBase() {

        Cliente cliente = objetoCliente();

        Mockito.when(usuarioRepository.findByCliente(cliente.getCodigoPessoa())).thenReturn(cliente);
        clienteService.deletaCliente(cliente.getCodigoPessoa());

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


}
