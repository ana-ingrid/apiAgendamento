package com.br.agendamento.cliente.model;

import com.br.agendamento.agendamento.model.Agendamento;
import com.br.agendamento.usuario.model.Usuario;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@DiscriminatorValue("CLIENTE")
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class Cliente extends Usuario {

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Agendamento> agendamentos;

}
