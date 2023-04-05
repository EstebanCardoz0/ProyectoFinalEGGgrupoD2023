package com.QuinchApp.Repositorios;

import com.QuinchApp.Entidades.Comentario;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ComentarioRepositorio extends JpaRepository<Comentario, Integer> {

      @Query("SELECT c FROM Comentario c WHERE c.cliente.id = :idCliente")
    List<Comentario> buscarComentariosPorIdCliente(@Param("idCliente") Integer idCliente);

    Optional<Comentario> findById(Integer id);

    @Query("SELECT c FROM Comentario c WHERE c.cliente.id = :idCliente AND c.calificacion = :calificacion")
    List<Comentario> findByCliente_IdClienteAndCalificacion(@Param("idCliente") Integer idCliente, @Param("calificacion") Integer calificacion);

    @Query("SELECT c FROM Comentario c WHERE c.cliente.id = :idCliente AND (LOWER(c.comentario) LIKE LOWER(concat('%', :palabraClave, '%')) OR LOWER(c.propiedad.nombre) LIKE LOWER(concat('%', :palabraClave, '%')))")
    List<Comentario> findByCliente_IdClienteAndPalabraClave(@Param("idCliente") Integer idCliente, @Param("palabraClave") String palabraClave);
}
