package com.QuinchApp.Repositorios;

import com.QuinchApp.Entidades.Propietario;
import java.util.List;
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

    @Query("SELECT p FROM Propietario p WHERE p.nombreUsuario = :nombreUsuario")
    public Propietario buscarPorNombreUsuario(@Param("nombreUsuario") String nombreUsuario);

    @Query("SELECT p FROM Propietario p WHERE p.id = :id")
    public Propietario buscarPorId(@Param("id") Integer idUsuario);

}
