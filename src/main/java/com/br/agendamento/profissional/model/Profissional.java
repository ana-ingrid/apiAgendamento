package com.br.agendamento.profissional.model;

import com.br.agendamento.agendamento.model.Agendamento;
import com.br.agendamento.servico.model.Servico;
import com.br.agendamento.usuario.model.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@DiscriminatorValue("PROFISSIONAL")
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Profissional extends Usuario {

    @OneToMany(mappedBy = "profissional", cascade = CascadeType.ALL)
    private List<Agendamento> agendamentos;

    @ManyToMany(mappedBy = "profissionais")
    private List<Servico> servicos;

}
