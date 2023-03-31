package com.QuinchApp.Servicios;

import com.QuinchApp.Entidades.Cliente;
import com.QuinchApp.Entidades.Propiedad;
import com.QuinchApp.Entidades.Reserva;
import com.QuinchApp.Repositorios.ClienteRepositorio;
import com.QuinchApp.Repositorios.PropiedadRepositorio;
import com.QuinchApp.Repositorios.ReservaRepositorio;
import java.text.SimpleDateFormat;
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
<<<<<<< HEAD
    public void registrar(String fechaInicio, String fechaSalida, int propiedad, int cliente) throws Exception {
        Reserva reserva = new Reserva();
        Cliente clienteReserva = new Cliente();
        Optional<Cliente> usuarioCliente = clienteRepositorio.buscarPorId(cliente);
        if (usuarioCliente.isPresent()) {
            clienteReserva = usuarioCliente.get();
=======
    public void registrar(String fechaInicio, String fechaSalida, String propiedad, String cliente) throws Exception {
        Reserva reserva = new Reserva();
        Cliente usuarioCliente = clienteRepositorio.buscarPorEmail(cliente);
        if (usuarioCliente.isActivo()) {
            reserva.setCliente(usuarioCliente);
>>>>>>> developer
        }
        Propiedad propiedadReserva = new Propiedad();
        Propiedad propiedadHaReservar = propiedadRepositorio.buscarPorNombre(propiedad);
        if (propiedadHaReservar != null) {
            propiedadReserva = propiedadHaReservar;
        }
        boolean activo = Boolean.TRUE;
        reserva.setConfirmada(activo);
        Date fechaDeInicio = new SimpleDateFormat("yyyy-MM-dd").parse(fechaInicio);
        reserva.setFechaInicio(fechaDeInicio);
        Date fechaDeSalida = new SimpleDateFormat("yyyy-MM-dd").parse(fechaSalida);
        reserva.setFechaSalida(fechaDeSalida);
        reserva.setPropiedad(propiedadReserva);
        reserva.setCliente(clienteReserva);
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
