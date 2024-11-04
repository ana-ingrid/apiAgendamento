package com.br.agendamento.pagamento.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EnumTipoPagamento {

    DEBITO("Débito"),
    CREDITO("Crédito"),
    BOLETO("Boleto");

    private String descricao;

}
