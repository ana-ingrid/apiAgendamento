package com.br.agendamento.cliente.model.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
    @Size(min = 3, max = 50)
    private String nome;

    @NotNull(message = "Email obrigatório")
    @Size(min = 10, max = 100)
    @Email
    private String email;

    @NotNull(message = "Data de nascimento obrigatória")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;

    @NotNull(message = "CPF obrigatório")
    @Pattern(regexp = "^[0-9]+$", message = "O código deve conter apenas números")
    @Size(min = 11, max = 11)
    @CPF
    private String codigoPessoa;

}
