package com.br.agendamento.services;


import com.br.agendamento.model.Cliente;
import com.br.agendamento.model.dtos.CadastraClienteDTO;
import com.br.agendamento.repository.UsuarioRepository;
import com.br.agendamento.service.ClienteService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
 class ClienteServiceTest {

    @InjectMocks
    protected ClienteService clienteService;

    @Mock
    protected ModelMapper modelMapper;

    @Mock
    protected UsuarioRepository UsuarioRepository;


    @Test
    void cadastraClienteComSucessoNaBase() {

        CadastraClienteDTO clienteDTO = new CadastraClienteDTO();
        clienteDTO.setNome("nome");
        clienteDTO.setEmail("teste@gmail.com");
        clienteDTO.setCodigoPessoa("123456789");

        Cliente cliente = new Cliente();
        cliente.setNome("nome");
        cliente.setEmail("teste@gmail.com");
        cliente.setCodigoPessoa("123456789");

        Mockito.when(modelMapper.map(clienteDTO, Cliente.class)).thenReturn(cliente);
        Mockito.when(clienteService.cadastraCliente(clienteDTO)).thenReturn(cliente);

        Cliente clienteCadastrado = clienteService.cadastraCliente(clienteDTO);

        Assertions.assertNotNull(clienteCadastrado);
        Assertions.assertEquals(clienteDTO.getNome(), clienteCadastrado.getNome());
        Assertions.assertEquals(clienteDTO.getEmail(), clienteCadastrado.getEmail());
        Assertions.assertEquals(clienteDTO.getCodigoPessoa(), clienteCadastrado.getCodigoPessoa());

    }

}
