package com.br.agendamento.profissional.model.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class CadastraProfissionalDTO {

    @NotNull(message = "Nome obrigatório")
    @Size(min = 3, max = 50)
    private String nome;

    @NotNull(message = "Email obrigatório")
    @Email
    @Size(min = 10, max = 50)
    private String email;

    @NotNull(message = "Data de nascimento obrigatório")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;

    @NotNull(message = "CPF obrigatório")
    @CPF
    @Size(min = 11, max = 11)
    @Pattern(regexp = "^[1-9]+$", message = "CPF inválido")
    private String codigoPessoa;


}
