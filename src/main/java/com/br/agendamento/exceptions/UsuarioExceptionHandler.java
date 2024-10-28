package com.br.agendamento.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UsuarioExceptionHandler {

    @ExceptionHandler(ClienteCadastradoException.class)
    public ResponseEntity<String> clienteCadastradoException(ClienteCadastradoException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(ClienteNaoExisteException.class)
    public ResponseEntity<String> clienteNaoExisteException(ClienteNaoExisteException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
