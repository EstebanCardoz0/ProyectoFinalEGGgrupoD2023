package com.QuinchApp.Repositorios;

import com.QuinchApp.Entidades.Propiedad;
import com.QuinchApp.Entidades.Reserva;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservaRepositorio extends JpaRepository<Reserva, Integer> {

   public Optional<Reserva> findById(Integer id);


}
