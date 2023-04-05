package com.QuinchApp.Servicios;

import com.QuinchApp.Entidades.Cliente;
import com.QuinchApp.Entidades.Comentario;
import com.QuinchApp.Entidades.Propiedad;
import com.QuinchApp.Entidades.Propietario;
import com.QuinchApp.Entidades.Usuario;
import com.QuinchApp.Repositorios.ComentarioRepositorio;
import com.QuinchApp.Repositorios.PropiedadRepositorio;
import com.QuinchApp.Repositorios.PropietarioRepositorio;
import com.QuinchApp.Repositorios.UsuarioRepositorio;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ComentarioServicio {

    @Autowired
    private ComentarioRepositorio comentarioRepositorio;
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private PropiedadRepositorio propiedadRepositorio;
     @Autowired
    private PropietarioRepositorio propietarioRepositorio;

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
    
    
    
     private void validar(Integer id, Integer idUsuario, String coment, Integer calificacion) throws Exception {
        if (id == null ) {
            throw new Exception("El id de la propiedad no puede estar estar vacío");
        }
        if (idUsuario == null ) {
            throw new Exception("El id de usuareio no puede estar estar vacío");
        }
        if (coment == null || coment.isEmpty()) {
            throw new Exception("El coment no puede estar vacio");
        }       
        if (calificacion == null) {
            throw new Exception("La calificacion no puede estar vacía");
        }        
    }
    
    

    public List<Comentario> listarComentarios() {

        return comentarioRepositorio.findAll();
    }

    @Transactional
    public void borrarComentario(int id) {

        comentarioRepositorio.deleteById(id);
    }

}
