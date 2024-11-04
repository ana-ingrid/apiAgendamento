package com.br.agendamento.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MensagensDeErros {

    CLIENTENAOEXISTE("Cliente não existe"),
    CLIENTEJACADASTRADO("Cliente já cadastrado");

    final String descricao;

}
