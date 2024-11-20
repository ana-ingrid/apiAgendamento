package com.br.agendamento.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MensagensDeErros {

    CLIENTENAOEXISTE("Cliente não existe"),
    CLIENTEJACADASTRADO("Cliente já cadastrado"),
    PROFISSIONALJACADASTRADO("Profissional não existe"),
    PROFISSIONALNAOEXISTE("Profissional já cadastrado");

    final String descricao;

}
