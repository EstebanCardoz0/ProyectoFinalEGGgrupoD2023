package com.QuinchApp.Repositorios;

import com.QuinchApp.Entidades.Propietario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PropietarioRepositorio extends JpaRepository<Propietario, Integer> {

    @Query("SELECT p FROM Propietario p WHERE p.nombre = :nombre")
    public Propietario buscarPorNombre(@Param("nombre") String nombre);

    @Query("SELECT p FROM Propietario p WHERE p.email = :email")
    public Propietario buscarPorEmail(@Param("email") String email);

}
