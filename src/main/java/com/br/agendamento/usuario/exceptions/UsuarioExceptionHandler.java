package com.br.agendamento.usuario.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UsuarioExceptionHandler {

    @ExceptionHandler(UsuarioCadastradoException.class)
    public ResponseEntity<String> clienteCadastradoException(UsuarioCadastradoException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(UsuarioNaoExisteException.class)
    public ResponseEntity<String> clienteNaoExisteException(UsuarioNaoExisteException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
