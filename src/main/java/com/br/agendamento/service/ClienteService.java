package com.br.agendamento.service;


import com.br.agendamento.exceptions.ClienteCadastradoException;
import com.br.agendamento.exceptions.ClienteNaoExisteException;
import com.br.agendamento.model.Cliente;
import com.br.agendamento.model.dtos.CadastraClienteDTO;
import com.br.agendamento.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import org.springframework.stereotype.Service;

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

        return modelMapper.map(cliente, Cliente.class);
    }

}



