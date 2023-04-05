package com.QuinchApp.Servicios;

import com.QuinchApp.Entidades.Cliente;
import com.QuinchApp.Entidades.Comentario;
import com.QuinchApp.Entidades.Propiedad;
import com.QuinchApp.Entidades.Usuario;
import com.QuinchApp.Repositorios.ComentarioRepositorio;
import com.QuinchApp.Repositorios.PropiedadRepositorio;
import com.QuinchApp.Repositorios.PropietarioRepositorio;
import com.QuinchApp.Repositorios.UsuarioRepositorio;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComentarioServicio {

    @Autowired
    private ComentarioRepositorio comentarioRepositorio;
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private PropiedadRepositorio propiedadRepositorio;



   @Transactional
public void crearComentario(Integer idPropiedad, Integer idCliente, String comentario, Integer calificacion) {
    Optional<Propiedad> propiedadOpt = propiedadRepositorio.findById(idPropiedad);
    if (!propiedadOpt.isPresent()) {
        throw new IllegalArgumentException("No se encontró la propiedad con el ID proporcionado");
    }
    Propiedad propiedad = propiedadOpt.get();
    Optional<Usuario> clienteOpt = usuarioRepositorio.findById(idCliente);
    if (!clienteOpt.isPresent() || !(clienteOpt.get() instanceof Cliente)) {
        throw new IllegalArgumentException("No se encontró el cliente con el ID proporcionado");
    }
    Cliente cliente = (Cliente) clienteOpt.get();
    // Verificar que la propiedad y el cliente existen antes de crear el comentario
    if (propiedad.isDisponibilidad()!= true) {
        throw new IllegalArgumentException("La propiedad no está habilitada");
    }
    if (cliente.isActivo()!= true) {
        throw new IllegalArgumentException("El cliente no está habilitado");
    }
    Comentario nuevoComentario = new Comentario();
    nuevoComentario.setPropiedad(propiedad);
    nuevoComentario.setCliente(cliente);
    nuevoComentario.setComentario(comentario);
    nuevoComentario.setCalificacion(calificacion);
    comentarioRepositorio.save(nuevoComentario);
    propiedad.getComentarios().add(nuevoComentario);
    propiedadRepositorio.save(propiedad);
    cliente.getComentarios().add(nuevoComentario);
    usuarioRepositorio.save(cliente);
}

    public List<Comentario> listarComentarios() {
        return comentarioRepositorio.findAll();
    }

    @Transactional
    public void borrarComentario(int id) {
        comentarioRepositorio.deleteById(id);
    }

    public List<Comentario> buscarComentariosPorIdCliente(Integer idCliente) {
        return comentarioRepositorio.buscarComentariosPorIdCliente(idCliente);
    }

    public List<Comentario> buscarComentariosPorIdClienteYPalabraClave(Integer idCliente, String palabraClave) {
        if (palabraClave == null || palabraClave.isEmpty()) {
            return buscarComentariosPorIdCliente(idCliente);
        }
        try {
            int calificacion = Integer.parseInt(palabraClave);
            return comentarioRepositorio.findByCliente_IdClienteAndCalificacion(idCliente, calificacion);
        } catch (NumberFormatException e) {
            return comentarioRepositorio.findByCliente_IdClienteAndPalabraClave(idCliente, palabraClave);
        }
    }

    @Transactional
    public void borrarComentario(Integer idComentario) {
        comentarioRepositorio.deleteById(idComentario);
    }
}
