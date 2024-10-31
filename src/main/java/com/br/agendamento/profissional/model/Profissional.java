package com.br.agendamento.profissional.model;

import com.br.agendamento.agendamento.model.Agendamento;
import com.br.agendamento.servico.model.Servico;
import com.br.agendamento.usuario.model.Usuario;
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
