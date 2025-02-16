package com.br.agendamento.servico.model.dtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
@Builder
public class AlteraServicoDTO {

    @Size(min = 3, max = 50, message = "O nome do serviço deve ter entre 3 e 50 caracteres")
    private String nomeServico;

    @Size(max = 200, message = "O serviço deve ter uma descrição até 200 caracteres")
    private String descricao;

    @DecimalMin(value = "0.0", inclusive = false, message = "O valor precisa ser acima de zero")
    @Digits(fraction = 2, integer = 5, message = "O valor deve ter no máximo 5 dígitos inteiros e 2 casas decimais")
    private BigDecimal valor;

}
