package com.br.agendamento.servico.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ServicoDTO {

    private Integer id;
    private String nomeServico;
    private BigDecimal valor;

}
