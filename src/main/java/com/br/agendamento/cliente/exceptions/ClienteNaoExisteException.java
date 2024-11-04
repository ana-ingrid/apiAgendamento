package com.br.agendamento.cliente.exceptions;

public class ClienteNaoExisteException extends RuntimeException {

    public ClienteNaoExisteException(String mensagem){
        super(mensagem);
    }

}
