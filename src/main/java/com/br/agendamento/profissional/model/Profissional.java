package com.br.agendamento.profissional.model;

import com.br.agendamento.agendamento.model.Agendamento;
import com.br.agendamento.servico.model.Servico;
import com.br.agendamento.usuario.model.Usuario;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@DiscriminatorValue("PROFISSIONAL")
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class Profissional extends Usuario {

    @OneToMany(mappedBy = "profissional", cascade = CascadeType.ALL)
    private List<Agendamento> agendamentos;

    @ManyToMany(mappedBy = "profissionais")
    private List<Servico> servicos;

}
