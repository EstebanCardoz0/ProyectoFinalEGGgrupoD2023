package com.QuinchApp.Repositorios;

import com.QuinchApp.Entidades.Propiedad;
import com.QuinchApp.Entidades.Reserva;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservaRepositorio extends JpaRepository<Reserva, Integer> {

    public Optional<Reserva> findById(Integer id);

    @Query("SELECT r FROM Reserva r WHERE "
            + "LOWER(CONCAT(r.idReserva, r.propiedad.nombre, r.cliente.nombre ), r.fechaDelEvento, r.confirmada) "
            + "LIKE LOWER(CONCAT('%', :name, '%'))")
    public List<Reserva> findAll(@Param("name") String palabraClave);

}
