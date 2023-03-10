package com.QuinchApp.Repositorio;

import com.QuinchApp.Entidades.Propietario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropietarioRepositorio extends JpaRepository<Propietario, Integer> {

}
