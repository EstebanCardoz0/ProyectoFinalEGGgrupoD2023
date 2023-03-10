package com.QuinchApp.Servicio;

import com.QuinchApp.Entidades.Imagen;
import com.QuinchApp.Entidades.Usuario;
import com.QuinchApp.Repositorio.UsuarioRepositorio;
import java.util.Date;
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
//        usuario.setPassword(new BCryptPasswordEncoder().encode(password));
        usuario.setPassword(password);

        // usuario.setRol(Rol.USER);
        usuario.setTelefono(telefono);
        usuario.setFechaAlta(fechaAlta);
        usuario.setActivo(activo);
        Imagen miImagen = imagenServicio.guardar(archivo);
        usuario.setFotoPerfil(miImagen);
        usuarioRepositorio.save(usuario);
    }

}
