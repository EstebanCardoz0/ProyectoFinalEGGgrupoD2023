package com.QuinchApp.Servicios;

import com.QuinchApp.Entidades.Cliente;
import com.QuinchApp.Entidades.Imagen;
import com.QuinchApp.Entidades.Reserva;
import com.QuinchApp.Enums.Rol;
import com.QuinchApp.Repositorios.ClienteRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ClienteServicio {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    private ImagenServicio imagenServicio;

    @Transactional
    public void registrar(String nombre, String nombreCliente, String email, String password, String password2, long telefono, MultipartFile archivo) throws Exception {
        validar(nombre, nombreCliente, email, password, telefono, archivo, password2);
        Cliente cliente = new Cliente();
        cliente.setNombre(nombre);
        cliente.setNombreUsuario(nombreCliente);
        cliente.setEmail(email);
        cliente.setPassword(password);
        cliente.setRol(Rol.CLIENTE);
        cliente.setTelefono(telefono);
        Date fechaAlta = new Date();
        cliente.setFechaAlta(fechaAlta);
        boolean activo = Boolean.TRUE;
        cliente.setActivo(activo);
        Imagen miImagen = imagenServicio.guardar(archivo);
        cliente.setFotoPerfil(miImagen);
        clienteRepositorio.save(cliente);
    }

    @Transactional
    public void actualizar(Reserva reserva, int id, String nombre, String nombreCliente, String email, String password, long telefono, MultipartFile archivo) throws Exception {
        if (id < 0) {
            throw new Exception("Ingrese un id");
        }
        Optional<Cliente> respuesta = clienteRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Cliente usuario = respuesta.get();
            usuario.setNombre(nombre);
            usuario.setNombreUsuario(nombreCliente);
            usuario.setEmail(email);
            usuario.setPassword(password);
            usuario.setTelefono(telefono);

            List<Reserva> reser = usuario.getReserva();
            if (reser == null) {
                reser = new ArrayList();
                usuario.setReserva(reser);
            }
            reser.add(reserva);
            usuario.setReserva(reser);
            int idImagen = 0;
            if (usuario.getFotoPerfil() != null) {
                idImagen = usuario.getFotoPerfil().getIdImagen();
            }
            Imagen miImagen = imagenServicio.actualizar(archivo, idImagen);
            usuario.setFotoPerfil(miImagen);
            clienteRepositorio.save(usuario);
        }
    }

    public List<Cliente> listarClientes() {
        List<Cliente> clientes = clienteRepositorio.findAll();
        return clientes;
    }

    @Transactional
    public void borrar(Integer id) {
        clienteRepositorio.deleteById(id);
    }

    private void validar(String nombre, String nombreCliente, String email, String password, long telefono, MultipartFile archivo, String password2) throws Exception {
        if (nombre.isEmpty() || nombre == null) {
            throw new Exception("El nombre no puede estar estar vacío");
        }
        if (nombreCliente.isEmpty() || nombreCliente == null) {
            throw new Exception("El nombre del cliente no puede estar vacío");
        }
        if (email.isEmpty() || email == null) {
            throw new Exception("El email no puede estar vacio");
        }
        if (password.isEmpty()) {
            throw new Exception("La contraseña no puede estar vacía");
        }
        if (password2.isEmpty()) {
            throw new Exception("Debe repetir la contraseña");
        }
        if (!password.equals(password2)) {
            throw new Exception("Las contraseñas ingresadas deben ser iguales");
        }
        if (telefono == 0L) {
            throw new Exception("El telefono no puede estar vacío");
        }
        if (archivo == null) {
            throw new Exception("La imagen no puede estar vacía");
        }
        Cliente cliente = clienteRepositorio.buscarPorEmail(email);
        if (cliente != null) {
            throw new Exception("El email ya se encuentra registrado, pruebe con otro");
        }
    }

}
