package com.br.agendamento.pagamento.model;

import com.br.agendamento.agendamento.model.Agendamento;
import com.br.agendamento.pagamento.model.enums.EnumStatusPagamento;
import com.br.agendamento.pagamento.model.enums.EnumTipoPagamento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pagamento")
    private Integer pagamentoId;

    @Column(name = "tipo_pagamento")
    private EnumTipoPagamento tipoPagamento;

    @Column(name = "status_pagamento")
    private EnumStatusPagamento statusPagamento;

    @Column(name = "horario_pagamento")
    private Timestamp horarioPagamento;

    @OneToOne
    @JoinColumn(name = "agendamento_id")
    private Agendamento agendamento;


}
