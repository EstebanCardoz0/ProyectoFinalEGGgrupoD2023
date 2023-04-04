/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.QuinchApp.Servicios;

import com.QuinchApp.Entidades.Cliente;
import com.QuinchApp.Entidades.Comentario;
import com.QuinchApp.Entidades.Propiedad;
import com.QuinchApp.Entidades.Propietario;
import com.QuinchApp.Entidades.Usuario;
import com.QuinchApp.Repositorios.ComentarioRepositorio;
import com.QuinchApp.Repositorios.PropiedadRepositorio;
import com.QuinchApp.Repositorios.UsuarioRepositorio;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Usuario
 */
@Service
public class ComentarioServicio {

    @Autowired
    private ComentarioRepositorio comentarioRepositorio;
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private PropiedadRepositorio propiedadRepositorio;

    @Transactional
    public void crearComentario(Integer id, Integer idUsuario, String coment, Integer calificacion) {
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        Cliente cliente = new Cliente();
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            cliente = new Cliente(usuario.getId(), usuario.getNombre(), usuario.getNombreUsuario());
        } else {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }

        Optional<Propiedad> respuesta2 = propiedadRepositorio.findById(id);
        Propietario propietario = new Propietario();
        Propiedad propiedad = new Propiedad();
        if (respuesta.isPresent()) {
            Propiedad propiedad2 = respuesta2.get();
            String nombrepropietario = propiedad.getPropietario().getNombre();
            Integer idpropietario = propiedad.getPropietario().getId();
            String nombreusuario = propiedad.getPropietario().getNombreUsuario();
            propietario = new Propietario(idpropietario, nombrepropietario, nombreusuario);
            propiedad = new Propiedad(propiedad2.getIdPropiedad(), propiedad2.getNombre(), propietario);
        } else {
            throw new UsernameNotFoundException("La propiedad no fue encontrada");
        }
        Comentario comentario = new Comentario(cliente, coment, calificacion, propiedad);

        comentarioRepositorio.save(comentario);

    }

    public List<Comentario> listarComentarios() {

        return comentarioRepositorio.findAll();
    }

    @Transactional
    public void borrarComentario(int id) {

        comentarioRepositorio.deleteById(id);
    }

}
