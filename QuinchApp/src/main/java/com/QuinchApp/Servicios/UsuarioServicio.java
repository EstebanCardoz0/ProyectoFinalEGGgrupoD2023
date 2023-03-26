package com.QuinchApp.Servicios;

import com.QuinchApp.Entidades.Imagen;
import com.QuinchApp.Entidades.Usuario;
import com.QuinchApp.Enums.Rol;
import com.QuinchApp.Repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class UsuarioServicio implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private ImagenServicio imagenServicio;
    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void registrar(String nombre, String nombreUsuario, String email, String password, String password2, long telefono, MultipartFile archivo) throws Exception {
        validar(nombre, nombreUsuario, email, password, telefono, archivo, password2);
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setNombreUsuario(nombreUsuario);
        usuario.setEmail(email);
        usuario.setPassword(new BCryptPasswordEncoder().encode(password));
        usuario.setRol(Rol.CLIENTE);
        usuario.setTelefono(telefono);
        Date fechaAlta = new Date();
        usuario.setFechaAlta(fechaAlta);
        boolean activo = Boolean.TRUE;
        usuario.setActivo(activo);
        Imagen miImagen = imagenServicio.guardar(archivo);
        usuario.setFotoPerfil(miImagen);
        usuarioRepositorio.save(usuario);
    }

    @Transactional
    public void registrarPropietario(String nombre, String nombreUsuario, String email, String password, String password2, long telefono, MultipartFile archivo) throws Exception {
        validar(nombre, nombreUsuario, email, password, telefono, archivo, password2);
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setNombreUsuario(nombreUsuario);
        usuario.setEmail(email);
        usuario.setPassword(new BCryptPasswordEncoder().encode(password));
        usuario.setRol(Rol.PROPIETARIO);
        usuario.setTelefono(telefono);
        Date fechaAlta = new Date();
        usuario.setFechaAlta(fechaAlta);
        boolean activo = Boolean.TRUE;
        usuario.setActivo(activo);
        Imagen miImagen = imagenServicio.guardar(archivo);
        usuario.setFotoPerfil(miImagen);
        usuarioRepositorio.save(usuario);
    }

    private void validar(String nombre, String nombreUsuario, String email, String password, long telefono, MultipartFile archivo, String password2) throws Exception {
        if (nombre == null || nombre.isEmpty()) {
            throw new Exception("El nombre no puede estar estar vacío");
        }
        if (nombreUsuario == null || nombreUsuario.isEmpty() ) {
            throw new Exception("El nombre de usuareio no puede estar estar vacío");
        }
        if ( email == null || email.isEmpty() ) {
            throw new Exception("El email no puede estar vacio");
        }
        if (password.isEmpty() ) {
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

    @Transactional
    public List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = usuarioRepositorio.findAll();
        return usuarios;
    }

    @Transactional
    public Usuario getOne(Integer id) {
        return usuarioRepositorio.getOne(id);
    }

    @Transactional
    public void borrar(Integer id) {
        usuarioRepositorio.deleteById(id);
    }

//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        Usuario usuario = usuarioRepositorio.buscarPorEmail(email);
//        if (usuario != null) {
//            List<GrantedAuthority> permisos = new ArrayList();
//            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString());
//            permisos.add(p);
//            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
//            HttpSession session = attr.getRequest().getSession(true);
//            session.setAttribute("usuariosession", usuario);
//            return new User(usuario.getEmail(), usuario.getPassword(), permisos);
//        } else {
//            return null;
//        }
//    }
    
    
//     public UsuarioServicio(UsuarioRepositorio usuarioRepositorio, PasswordEncoder passwordEncoder) {
//        this.usuarioRepositorio = usuarioRepositorio;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        Usuario usuario = usuarioRepositorio.buscarPorEmail(email);
//        if (usuario != null) {
//            List<GrantedAuthority> permisos = new ArrayList();
//            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString());
//            permisos.add(p);
//            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
//            HttpSession session = attr.getRequest().getSession(true);
//            session.setAttribute("usuariosession", usuario);
//
//            // Verificar la contraseña
//            if (passwordEncoder.matches(usuario.getPassword(), usuario.getPassword())) {
//                return new User(usuario.getEmail(), usuario.getPassword(), permisos);
//            } else {
//                throw new BadCredentialsException("Contraseña incorrecta");
//            }
//        } else {
//            throw new UsernameNotFoundException("Usuario no encontrado");
//        }
//    }
    
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.buscarPorEmail(email);
        if (usuario != null) {
            List<GrantedAuthority> permisos = new ArrayList();
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString());
            permisos.add(p);
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usuariosession", usuario);
            return new User(usuario.getEmail(), usuario.getPassword(), permisos);
        } else {
            return null;
        }
    }

}
