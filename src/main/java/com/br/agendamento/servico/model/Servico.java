package com.br.agendamento.servico.model;

import com.br.agendamento.profissional.model.Profissional;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    private BigDecimal valor;

}
