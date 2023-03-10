package com.QuinchApp.Repositorios;

import com.QuinchApp.Entidades.Imagen;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagenRepositorio extends JpaRepository<Imagen, Integer> {

    @Override
    public Optional<Imagen> findById(Integer id);
}
