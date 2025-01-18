package com.br.agendamento.usuario.repository;

import com.br.agendamento.cliente.model.Cliente;
import com.br.agendamento.profissional.model.Profissional;
import com.br.agendamento.usuario.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

     @Query(value = "SELECT * FROM usuario u WHERE u.tipo_usuario IN ('CLIENTE') AND codigo_pessoa = :codigo", nativeQuery = true)
     Cliente findByCliente(@Param("codigo") String codigo);

     @Query(value = "SELECT * FROM usuario u WHERE u.tipo_usuario IN ('PROFISSIONAL') AND codigo_pessoa = :codigo", nativeQuery = true)
     Profissional findByProfissional(@Param("codigo") String codigo);

     @Query(value = "SELECT * FROM usuario u WHERE u.tipo_usuario IN ('CLIENTE')", nativeQuery = true)
     List<Cliente> findByTodosUsuarioDoTipoCliente();

     @Query(value = "SELECT * FROM usuario u WHERE u.tipo_usuario IN ('PROFISSIONAL')", nativeQuery = true)
     List<Profissional> findByTodosUsuarioDoTipoProfissional();

}
