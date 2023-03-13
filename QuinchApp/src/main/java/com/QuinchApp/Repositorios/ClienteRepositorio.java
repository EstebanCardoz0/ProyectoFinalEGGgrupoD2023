package com.QuinchApp.Repositorios;

import com.QuinchApp.Entidades.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepositorio extends JpaRepository<Cliente, Integer> {

    @Query("SELECT c FROM Cliente c WHERE c.nombre = :nombre")
    public Cliente buscarPorNombre(@Param("nombre") String nombre);

}
