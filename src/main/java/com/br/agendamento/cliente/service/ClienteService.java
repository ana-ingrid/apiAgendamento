package com.br.agendamento.cliente.service;


import com.br.agendamento.cliente.exceptions.ClienteCadastradoException;
import com.br.agendamento.cliente.exceptions.ClienteNaoExisteException;
import com.br.agendamento.cliente.model.Cliente;
import com.br.agendamento.cliente.model.dtos.AlteraClienteDTO;
import com.br.agendamento.cliente.model.dtos.CadastraClienteDTO;
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


    public Boolean consultaSeUsuarioClienteNaoExiste(String codigo){
        List<String> tipoUsuario = usuarioRepository.findByCLientePeloCodigoPessoa(codigo);
        if (!tipoUsuario.isEmpty()){
            for (String tipo : tipoUsuario){
                if (tipo.equalsIgnoreCase("CLIENTE")){
                    return false;
                }
            }
        }
        return true;
    }

    public Cliente cadastraCliente(CadastraClienteDTO cliente){
        String codigo = cliente.getCodigoPessoa();
        if (Objects.nonNull(usuarioRepository.findByUsuarioDoTipoCliente(codigo))){
            throw new ClienteCadastradoException("Cliente já cadastrado");
        }

        if (Boolean.FALSE.equals(consultaSeUsuarioClienteNaoExiste(codigo))){
            throw new ClienteCadastradoException("Cliente já cadastrado");
        }

        Cliente clienteMapeado = modelMapper.map(cliente, Cliente.class);
        return usuarioRepository.save(clienteMapeado);
    }


    public Cliente consultaCliente(String codigo){
        Cliente cliente = usuarioRepository.findByUsuarioDoTipoCliente(codigo);
        if (Objects.isNull(cliente)){
            throw new ClienteNaoExisteException("Cliente não existe");
        }
        return cliente;
    }

    public Cliente alteraCliente(AlteraClienteDTO clienteDTO, String codigo){
        Cliente cliente = usuarioRepository.findByUsuarioDoTipoCliente(codigo);
        if (Objects.isNull(cliente)) throw new ClienteNaoExisteException("Cliente não existe");

        if (clienteDTO.getNome() != null) cliente.setNome(clienteDTO.getNome());
        if (clienteDTO.getEmail() != null) cliente.setEmail(clienteDTO.getEmail());
        if (clienteDTO.getDataNascimento() != null) cliente.setDataNascimento(clienteDTO.getDataNascimento());

        return usuarioRepository.save(cliente);
    }

    public void deletaCliente(String codigo){
        Cliente cliente = usuarioRepository.findByUsuarioDoTipoCliente(codigo);
        if (Objects.isNull(cliente)){
            throw new ClienteNaoExisteException("Cliente não existe");
        }
        usuarioRepository.delete(cliente);
    }



}



