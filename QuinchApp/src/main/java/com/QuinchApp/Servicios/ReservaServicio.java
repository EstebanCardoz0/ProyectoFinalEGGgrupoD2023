package com.QuinchApp.Servicios;

import com.QuinchApp.Entidades.Cliente;
import com.QuinchApp.Entidades.Propiedad;
import com.QuinchApp.Entidades.Reserva;
import com.QuinchApp.Repositorios.ClienteRepositorio;
import com.QuinchApp.Repositorios.PropiedadRepositorio;
import com.QuinchApp.Repositorios.ReservaRepositorio;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    private PropiedadRepositorio propiedadRepositorio;

    @Transactional
    public void registrar(String fechaDelEvento, int propiedad, String cliente) throws Exception {
        Reserva reserva = new Reserva();
        Cliente usuarioCliente = clienteRepositorio.buscarPorEmail(cliente);
        if (usuarioCliente == null) {
            throw new Exception("No existe el cliente");
        }
        if (!usuarioCliente.isActivo()) {
            throw new Exception("El cliente no está activo");
        }
        reserva.setCliente(usuarioCliente);
        Propiedad propiedadReserva = propiedadRepositorio.buscarPorIdPropiedad(propiedad)
                .orElseThrow(() -> new Exception("No existe la propiedad"));
        reserva.setPropiedad(propiedadReserva);
        boolean activo = Boolean.TRUE;
        reserva.setConfirmada(activo);
        Date fecha = new SimpleDateFormat("yyyy-MM-dd").parse(fechaDelEvento);
        List<Reserva> reservas = propiedadReserva.getReservas();
        for (Reserva r : reservas) {
            if (r.getFechaDelEvento().equals(fecha)) {
                throw new Exception("error");
            }
        }
        reserva.setFechaDelEvento(fecha);
        List<Reserva> reservasCliente = usuarioCliente.getReservas();
        if (reservasCliente == null) {
            reservasCliente = new ArrayList<>();
            usuarioCliente.setReservas(reservasCliente);
        }
        reservasCliente.add(reserva);
        reservaRepositorio.save(reserva);
    }

    @Transactional
    public void actualizar(Integer id, Date fechaDelEvento, Propiedad propiedad, Cliente Cliente, Boolean confirmada) throws Exception {
        Optional<Reserva> respuesta = reservaRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Reserva reserva = respuesta.get();
            reserva.setCliente(Cliente);
            if (confirmada == true) {
                reserva.setConfirmada(Boolean.TRUE);
            } else {
                reserva.setConfirmada(Boolean.FALSE);
            }
            reserva.setFechaDelEvento(fechaDelEvento);
            reserva.setPropiedad(propiedad);
            reservaRepositorio.save(reserva);
        }
    }

    public List<Reserva> listarResevas() {
        List<Reserva> reserva = reservaRepositorio.findAll();
        return reserva;
    }

    @Transactional
    public List<Reserva> listarResevas(String palabraClave) {
        if (palabraClave != null) {
            List<Reserva> reservas = reservaRepositorio.findAll(palabraClave);
            return reservas;
        } else {
            List<Reserva> reservas = reservaRepositorio.findAll();
            return reservas;
        }
    }
    
    @Transactional
    public List<Reserva> listarResevasPorCliente(Long idCliente, String palabraClave) {
    if (palabraClave != null) {
        List<Reserva> reservas = reservaRepositorio.findAllByClienteIdAndPalabraClave(idCliente, palabraClave);
        return reservas;
    } else {
        List<Reserva> reservas = reservaRepositorio.findAllByClienteId(idCliente);
        return reservas;
    }
}


    public Reserva getOne(Integer id) {
        return reservaRepositorio.getOne(id);
    }

    @Transactional
    public void borrar(Integer id) throws Exception {
        Optional<Reserva> reservaOptional = reservaRepositorio.findById(id);
        if (reservaOptional.isPresent()) {
            reservaRepositorio.deleteById(id);
        } else {
            throw new Exception("La reserva con ID " + id + " no existe.");
        }
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
    
   @Transactional
    public void modificarFechaReserva(Integer idReserva, Date fechaDelEvento) throws Exception {
        Optional<Reserva> optionalReserva = reservaRepositorio.findById(idReserva);
        if (optionalReserva.isPresent()) {
            Reserva reserva = optionalReserva.get();
            reserva.setFechaDelEvento(fechaDelEvento);
            reservaRepositorio.save(reserva);
        } else {
            throw new Exception("No se encontró la reserva con el id " + idReserva);
        }
    }

}
