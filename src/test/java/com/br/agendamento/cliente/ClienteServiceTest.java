package com.br.agendamento.cliente;


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
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
 class ClienteServiceTest {

    @Spy
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

        Mockito.when(clienteService.consultaClienteOuValida(clienteDTO.getCodigoPessoa(), false)).thenReturn(null);
        Mockito.when(modelMapper.map(clienteDTO, Cliente.class)).thenReturn(cliente);
        Mockito.when(usuarioRepository.save(cliente)).thenReturn(cliente);

        Cliente clienteCadastrado = clienteService.cadastraCliente(clienteDTO);

        assertEquals(cliente, clienteCadastrado);
        assertNotNull(clienteCadastrado);

        Mockito.verify(clienteService, Mockito.times(1)).consultaClienteOuValida(clienteDTO.getCodigoPessoa(), false);
        Mockito.verify(usuarioRepository, Mockito.times(1)).findByCliente(cliente.getCodigoPessoa());
        Mockito.verify(modelMapper, Mockito.times(1)).map(clienteDTO, Cliente.class);
        Mockito.verify(usuarioRepository, Mockito.times(1)).save(cliente);
    }

    @Test
    void erroAoTentarCadastrarClienteJaExistenteNaBase() {

        CadastraClienteDTO clienteDTO = objetoClienteDTO();

        Mockito.when(clienteService.consultaClienteOuValida(clienteDTO.getCodigoPessoa(), false))
                .thenThrow(new UsuarioCadastradoException(MensagensDeErros.CLIENTEJACADASTRADO.getDescricao()));

        UsuarioCadastradoException exception = assertThrows(
                UsuarioCadastradoException.class, () -> clienteService.cadastraCliente(clienteDTO));

        assertEquals(MensagensDeErros.CLIENTEJACADASTRADO.getDescricao(), exception.getMessage());
        Mockito.verify(clienteService, Mockito.times(1)).consultaClienteOuValida(clienteDTO.getCodigoPessoa(),false);
        Mockito.verify(usuarioRepository, Mockito.times(1)).findByCliente(clienteDTO.getCodigoPessoa());
    }

    @Test
     void consultaClienteJaCadastradoNaBase() {

        Cliente cliente = objetoCliente();
        String codigoPessoa = "123456789";

        Mockito.when(usuarioRepository.findByCliente(codigoPessoa)).thenReturn(cliente);

        Cliente resultado = clienteService.consultaClienteOuValida(cliente.getCodigoPessoa(), true);

        assertNotNull(resultado);
        assertEquals(codigoPessoa, resultado.getCodigoPessoa());

        Mockito.verify(usuarioRepository, Mockito.times(1)).findByCliente(cliente.getCodigoPessoa());
    }

    @Test
    void deveLancarExcecaoQuandoClienteNaoForEncontrado() {
        String codigoPessoa = "123456789";

        Mockito.when(usuarioRepository.findByCliente(codigoPessoa)).thenThrow(new UsuarioNaoExisteException(MensagensDeErros.CLIENTENAOEXISTE.getDescricao()));

        UsuarioNaoExisteException exception = assertThrows(UsuarioNaoExisteException.class, () -> clienteService.consultaClienteOuValida(codigoPessoa, true));


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
    void NaoDeveAlterarClienteQuandoUsuarioNaoExistir() {
        String codigoPessoa = "123456789";
        AlteraClienteDTO alteraClienteDTO = objetoDeAlteraClienteDTO();

        Mockito.when(usuarioRepository.findByCliente(codigoPessoa)).thenReturn(null);

        UsuarioNaoExisteException exception = assertThrows(
              UsuarioNaoExisteException.class, () -> clienteService.alteraCliente(alteraClienteDTO,codigoPessoa));

        Mockito.verify(usuarioRepository, Mockito.times(1)).findByCliente(codigoPessoa);

        assertNotNull(exception);
        assertInstanceOf(UsuarioNaoExisteException.class, exception);
        assertEquals(MensagensDeErros.CLIENTENAOEXISTE.getDescricao(), exception.getMessage());
    }

    @Test
    void deletaClienteComSucessoNaBase() {

        Cliente cliente = objetoCliente();
        String codigoPessoa = "123456789";

        Mockito.when(usuarioRepository.findByCliente(codigoPessoa)).thenReturn(cliente);
        Mockito.doNothing().when(usuarioRepository).delete(cliente);
        clienteService.deletaCliente(codigoPessoa);

        Mockito.verify(usuarioRepository, Mockito.times(1)).findByCliente(codigoPessoa);
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
