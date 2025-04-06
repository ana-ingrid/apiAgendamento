package com.br.agendamento.servico.repository;

import com.br.agendamento.servico.model.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicoRepository extends JpaRepository<Servico, Integer> {

    @Query(value = "SELECT * FROM servico s WHERE nome_servico = :nomeServico", nativeQuery = true)
    Servico findByServico(@Param("nomeServico") String nomeServico);

}
