package com.QuinchApp.Servicios;

import com.QuinchApp.Entidades.Cliente;
import com.QuinchApp.Entidades.Propiedad;
import com.QuinchApp.Entidades.Reserva;
import com.QuinchApp.Entidades.Usuario;
import com.QuinchApp.Repositorios.ReservaRepositorio;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ReservaServicio {

    @Autowired
    private ReservaRepositorio reservaRepositorio;

    @Transactional
    public void registrar(Date fechaInicio, Date fechaSalida, Propiedad propiedad, Usuario cliente) throws Exception {
        Reserva reserva = new Reserva();
        boolean activo = Boolean.TRUE;
        reserva.setConfirmada(activo);
        reserva.setFechaInicio(fechaInicio);
        reserva.setFechaSalida(fechaSalida);
        reserva.setCliente((Cliente) cliente);
        reservaRepositorio.save(reserva);
    }

    @Transactional
    public void actualizar(Integer id, Date FechaSalida, Propiedad propiedad, Cliente Cliente, Boolean confirmada) throws Exception {
        Optional<Reserva> respuesta = reservaRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Reserva reserva = respuesta.get();
            reserva.setCliente(Cliente);
            if (confirmada == true) {
                reserva.setConfirmada(Boolean.TRUE);
            } else {
                reserva.setConfirmada(Boolean.FALSE);
            }
            reserva.setFechaSalida(FechaSalida);
            reserva.setPropiedad(propiedad);
            reservaRepositorio.save(reserva);
        }
    }

    public List<Reserva> listarResevas() {
        List<Reserva> reserva = reservaRepositorio.findAll();
        return reserva;
    }

    public Reserva getOne(Integer id) {
        return reservaRepositorio.getOne(id);
    }

    @Transactional
    public void borrar(Integer id) {
        reservaRepositorio.deleteById(id);
    }

    public Reserva bajaAlta(Integer id) {
        Optional<Reserva> reservaOptinal = reservaRepositorio.findById(id);
        Reserva reserva = new Reserva();
        if (reservaOptinal.isPresent()) {
            reserva = reservaOptinal.get();
            if (reserva.getConfirmada() == false) {
                reserva.setConfirmada(Boolean.TRUE);
                reservaRepositorio.save(reserva);
            } else {
                reserva.setConfirmada(Boolean.FALSE);
                reservaRepositorio.save(reserva);
            }
        }
        return reserva;
    }
}
