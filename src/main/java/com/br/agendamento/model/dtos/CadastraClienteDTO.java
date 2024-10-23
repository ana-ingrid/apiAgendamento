package com.br.agendamento.model.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CadastraClienteDTO {

    @NotNull(message = "Nome obrigatório")
    private String nome;

    @NotNull(message = "Email obrigatório")
    @Email
    private String email;

    @NotNull(message = "Data de nascimento obrigatória")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;

    @NotNull(message = "CPF obrigatório")
    @CPF
    private String codigoPessoa;

}
