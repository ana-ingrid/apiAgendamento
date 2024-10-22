package com.br.agendamento.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@DiscriminatorValue("PROFISSIONAL")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Profissional extends Usuario {

    @OneToMany(mappedBy = "profissional", cascade = CascadeType.ALL)
    private List<Agendamento> agendamentos;

    @ManyToMany(mappedBy = "profissionais")
    private List<Servico> servicos;

}
