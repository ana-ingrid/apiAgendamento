package com.br.agendamento.servico.model.dtos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
@Builder
public class CadastraServicoDTO {

    @NotBlank(message = "O nome do serviço é obrigatório")
    @Size(min = 3, max = 50, message = "O nome do serviço deve ter entre 3 e 50 caracteres")
    private String nomeServico;

    @NotBlank(message = "A descrição do serviço é obrigatória")
    @Size(max = 200, message = "O serviço deve ter uma descrição até 200 caracteres")
    private String descricao;

    @NotNull(message = "O valor é obrigatório")
    @DecimalMin(value = "0.0", inclusive = false, message = "O valor deve ser maior que zero")
    @Digits(integer = 5, fraction = 2, message = "O valor deve ter no máximo 5 dígitos inteiros e 2 casas decimais")
    private BigDecimal valor;

}
