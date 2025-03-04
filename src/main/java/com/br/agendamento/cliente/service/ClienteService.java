package com.br.agendamento.cliente.service;


import com.br.agendamento.usuario.exceptions.UsuarioCadastradoException;
import com.br.agendamento.usuario.exceptions.UsuarioNaoExisteException;
import com.br.agendamento.cliente.model.Cliente;
import com.br.agendamento.cliente.model.dtos.AlteraClienteDTO;
import com.br.agendamento.cliente.model.dtos.CadastraClienteDTO;
import com.br.agendamento.config.MensagensDeErros;
import com.br.agendamento.usuario.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class ClienteService {

    protected final ModelMapper modelMapper;

    protected final UsuarioRepository usuarioRepository;


    public Cliente consultaClienteOuValida(String codigoPessoa, boolean deveLancarExcecao){
        Cliente cliente = usuarioRepository.findByCliente(codigoPessoa);
        if (Objects.isNull(cliente) && deveLancarExcecao){
            throw new UsuarioNaoExisteException(MensagensDeErros.CLIENTENAOEXISTE.getDescricao());
        }
        return cliente;
    }

    public Cliente cadastraCliente(CadastraClienteDTO cadastraClienteDTO){
        String codigoPessoa = cadastraClienteDTO.getCodigoPessoa();

        if (Objects.nonNull(consultaClienteOuValida(codigoPessoa, false))){
            throw new UsuarioCadastradoException(MensagensDeErros.CLIENTEJACADASTRADO.getDescricao());
        }
        Cliente cliente = modelMapper.map(cadastraClienteDTO, Cliente.class);
        return usuarioRepository.save(cliente);
    }

    public List<Cliente> consultaTodosClientes(){
        return usuarioRepository.findByTodosUsuarioDoTipoCliente();
    }

    public Cliente alteraCliente(AlteraClienteDTO clienteDTO, String codigo){
        Cliente cliente = usuarioRepository.findByCliente(codigo);
        if (Objects.isNull(cliente)) throw new UsuarioNaoExisteException(MensagensDeErros.CLIENTENAOEXISTE.getDescricao());

        if (clienteDTO.getNome() != null) cliente.setNome(clienteDTO.getNome());
        if (clienteDTO.getEmail() != null) cliente.setEmail(clienteDTO.getEmail());
        if (clienteDTO.getDataNascimento() != null) cliente.setDataNascimento(clienteDTO.getDataNascimento());

        return usuarioRepository.save(cliente);
    }

    public void deletaCliente(String codigo){
        Cliente cliente = usuarioRepository.findByCliente(codigo);
        if (Objects.isNull(cliente)){
            throw new UsuarioNaoExisteException(MensagensDeErros.CLIENTENAOEXISTE.getDescricao());
        }
        usuarioRepository.delete(cliente);
    }



}



