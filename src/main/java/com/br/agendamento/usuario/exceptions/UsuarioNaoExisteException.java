package com.br.agendamento.usuario.exceptions;

public class UsuarioNaoExisteException extends RuntimeException {

    public UsuarioNaoExisteException(String mensagem){
        super(mensagem);
    }

}
