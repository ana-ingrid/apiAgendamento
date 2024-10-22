package com.br.agendamento.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Servico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
  
    @Column(name = "id_servico")
    private  Integer servicoId;

    @ManyToMany
    @JoinTable(name = "profissional_servico",
    joinColumns = @JoinColumn(name = "servico_id"),
    inverseJoinColumns = @JoinColumn(name = "profissional_id"))
    List<Profissional> profissionais;

    @Column(name = "nome_servico")
    private String nomeServico;

    private String descricao;
    private double valor;

}
