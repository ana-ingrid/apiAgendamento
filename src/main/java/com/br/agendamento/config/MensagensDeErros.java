package com.br.agendamento.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MensagensDeErros {

    CLIENTENAOEXISTE("Cliente não existe"),
    CLIENTEJACADASTRADO("Cliente já cadastrado"),
    PROFISSIONALJACADASTRADO("Profissional já cadastrado"),
    PROFISSIONALNAOEXISTE("Profissional não existe");

    final String descricao;

}
