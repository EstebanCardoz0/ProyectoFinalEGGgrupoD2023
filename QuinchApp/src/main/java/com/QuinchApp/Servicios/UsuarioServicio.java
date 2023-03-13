package com.QuinchApp.Servicios;

import com.QuinchApp.Entidades.Imagen;
import com.QuinchApp.Entidades.Usuario;
import com.QuinchApp.Repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    public void registrar(String nombre, String nombreUsuario, String email, String password, long telefono, Date fechaAlta, boolean activo, MultipartFile archivo) throws Exception {
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setNombreUsuario(nombreUsuario);
        usuario.setEmail(email);
        //usuario.setPassword(new BCryptPasswordEncoder().encode(password));
        usuario.setPassword(password);
        // usuario.setRol(Rol.USER);
        usuario.setTelefono(telefono);
        usuario.setFechaAlta(fechaAlta);
        usuario.setActivo(activo);
        Imagen miImagen = imagenServicio.guardar(archivo);
        usuario.setFotoPerfil(miImagen);
        usuarioRepositorio.save(usuario);
    }
    
    @Transactional
    public void actualizar(int id, String nombre, String nombreUsuario, String email, String password, long telefono, MultipartFile archivo) throws Exception {
        if(id < 0) {
            throw new Exception("Ingrese un id");
        }
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if(respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            usuario.setNombre(nombre);
            usuario.setNombreUsuario(nombreUsuario);
            usuario.setEmail(email);
            usuario.setPassword(password);
            usuario.setTelefono(telefono);
            int idImagen = 0;
            if(usuario.getFotoPerfil() != null) {
                idImagen = usuario.getFotoPerfil().getIdImagen();
            }
            Imagen miImagen = imagenServicio.actualizar(archivo, idImagen);
            usuario.setFotoPerfil(miImagen);
            usuarioRepositorio.save(usuario);
        }
    }

    
  public List<Usuario> listarUsuarios(){
    List<Usuario> usuarios= usuarioRepositorio.findAll();
    return usuarios;
}

    
    
    
    
    
}
