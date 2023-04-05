package com.QuinchApp.Repositorios;

import com.QuinchApp.Entidades.Comentario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ComentarioRepositorio extends JpaRepository<Comentario, Integer> {

    @Query("SELECT c FROM Comentario c WHERE c.cliente.id = :idCliente")
    List<Comentario> buscarComentariosPorIdCliente(@Param("idCliente") Integer idCliente);

@Query("SELECT c FROM Comentario c WHERE c.cliente.id = :idCliente AND (LOWER(c.comentario) LIKE %LOWER(:palabraClave) OR LOWER(c.calificacion) LIKE %LOWER(:palabraClave))")
    List<Comentario> findByCliente_IdClienteAndPalabraClave(@Param("idCliente") Integer idCliente, @Param("palabraClave") String palabraClave);
}
