package com.QuinchApp.Repositorios;

import com.QuinchApp.Entidades.Propiedad;
import com.QuinchApp.Entidades.Propietario;
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

    @Query("SELECT r FROM Reserva r WHERE r.cliente.id = :idCliente AND ("
            + "LOWER(r.idReserva) LIKE LOWER(CONCAT('%', :palabraClave, '%')) OR "
            + "LOWER(r.propiedad.nombre) LIKE LOWER(CONCAT('%', :palabraClave, '%')) OR "
            + "LOWER(r.cliente.nombre) LIKE LOWER(CONCAT('%', :palabraClave, '%')) OR "
            + "LOWER(r.fechaDelEvento) LIKE LOWER(CONCAT('%', :palabraClave, '%')) OR "
            + "LOWER(r.confirmada) LIKE LOWER(CONCAT('%', :palabraClave, '%'))"
            + ")")
    public List<Reserva> findAllByClienteIdAndPalabraClave(@Param("idCliente") Integer idCliente, @Param("palabraClave") String palabraClave);

    @Query("SELECT r FROM Reserva r WHERE r.cliente.id = :idCliente")
    public List<Reserva> findAllByClienteId(@Param("idCliente") Integer idCliente);

    @Query("SELECT r.propiedad FROM Reserva r WHERE r.propiedad.propietario.id = :idPropietario")
    List<Propiedad> obtenerPropiedadesReservadasPorPropietario(@Param("idPropietario") Integer idPropietario);

    @Query("SELECT r.propiedad FROM Reserva r WHERE r.propiedad.propietario.id = :idPropietario AND ("
            + "LOWER(r.idReserva) LIKE LOWER(CONCAT('%', :palabraClave, '%')) OR "
            + "LOWER(r.propiedad.nombre) LIKE LOWER(CONCAT('%', :palabraClave, '%')) OR "
            + "LOWER(r.cliente.nombre) LIKE LOWER(CONCAT('%', :palabraClave, '%')) OR "
            + "LOWER(r.fechaDelEvento) LIKE LOWER(CONCAT('%', :palabraClave, '%')) OR "
            + "LOWER(r.confirmada) LIKE LOWER(CONCAT('%', :palabraClave, '%'))"
            + ")")
    public List<Propiedad> findAllByPropietarioIdAndPalabraClave(@Param("idPropietario") Integer idPropietario, @Param("palabraClave") String palabraClave);

}
