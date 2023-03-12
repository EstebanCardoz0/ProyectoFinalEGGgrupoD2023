package com.QuinchApp.Servicios;

import com.QuinchApp.Entidades.Imagen;
import com.QuinchApp.Entidades.Usuario;
import com.QuinchApp.Repositorios.UsuarioRepositorio;
import java.util.Date;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UsuarioServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private ImagenServicio imagenServicio;

    @Transactional
    public void registrar(String nombre, String nombreUsuario, String email, String password, long telefono,  MultipartFile archivo) throws Exception {
        validar(nombre, nombreUsuario, email, password, telefono, archivo);
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setNombreUsuario(nombreUsuario);
        usuario.setEmail(email);
        //usuario.setPassword(new BCryptPasswordEncoder().encode(password));
        usuario.setPassword(password);
        // usuario.setRol(Rol.USER);
        usuario.setTelefono(telefono);
        Date fechaAlta = new Date();
        usuario.setFechaAlta(fechaAlta);
        boolean activo = Boolean.TRUE;
        usuario.setActivo(activo);
        Imagen miImagen = imagenServicio.guardar(archivo);
        usuario.setFotoPerfil(miImagen);
        usuarioRepositorio.save(usuario);
    }

    private void validar(String nombre, String nombreUsuario, String email, String password, long telefono, MultipartFile archivo) throws Exception {
        if (nombre.isEmpty() || nombre == null) {
            throw new Exception("El nombre no puede estar estar vacío");
        }
        if (nombreUsuario.isEmpty() || nombreUsuario == null) {
            throw new Exception("El nombre de usuareio no puede estar estar vacío");
        }
        if (email.isEmpty() || email == null) {
            throw new Exception("El email no puede estar vacio");
        }
        if (password.isEmpty()) {
            throw new Exception("La contraseña no puede estar vacía");
        }
        if (telefono == 0L) {
            throw new Exception("El telefono no puede estar vacío");
        }
        if (archivo == null) {
            throw new Exception("La imagen no puede estar vacía");
        }
        Usuario usuario = usuarioRepositorio.buscarPorEmail(email);
        if (usuario != null) {
            throw new Exception("El email ya se encuentra registrado, pruebe con otro");
        }
    }

    @Transactional
    public void actualizar(int id, String nombre, String nombreUsuario, String email, String password, long telefono, MultipartFile archivo) throws Exception {
        if (id < 0) {
            throw new Exception("Ingrese un id");
        }
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            usuario.setNombre(nombre);
            usuario.setNombreUsuario(nombreUsuario);
            usuario.setEmail(email);
            usuario.setPassword(password);
            usuario.setTelefono(telefono);
            int idImagen = 0;
            if (usuario.getFotoPerfil() != null) {
                idImagen = usuario.getFotoPerfil().getIdImagen();
            }
            Imagen miImagen = imagenServicio.actualizar(archivo, idImagen);
            usuario.setFotoPerfil(miImagen);
            usuarioRepositorio.save(usuario);
        }
    }

}
