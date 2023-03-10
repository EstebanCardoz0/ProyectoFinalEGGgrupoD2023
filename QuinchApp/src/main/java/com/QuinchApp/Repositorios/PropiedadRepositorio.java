package com.QuinchApp.Repositorios;

import com.QuinchApp.Entidades.Propiedad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropiedadRepositorio extends JpaRepository<Propiedad, Integer> {

}
