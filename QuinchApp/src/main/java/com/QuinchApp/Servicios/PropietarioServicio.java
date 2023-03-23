package com.QuinchApp.Servicios;

import com.QuinchApp.Entidades.Imagen;
import com.QuinchApp.Entidades.Propiedad;
import com.QuinchApp.Entidades.Propietario;
import com.QuinchApp.Enums.Rol;
import com.QuinchApp.Repositorios.PropietarioRepositorio;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;




@Service
public class PropietarioServicio{

    @Autowired
    private PropietarioRepositorio propietarioRepositorio;
    @Autowired
    private ImagenServicio imagenServicio;

    @Transactional
    public void registrar(String nombre, String nombreUsuario, String email, String password, String password2, long telefono, List<Propiedad> propiedades, MultipartFile archivo) throws Exception {
        validar(nombre, nombreUsuario, email, password, telefono, archivo, password2);
        Propietario propietario = new Propietario();
        propietario.setNombre(nombre);
        propietario.setNombreUsuario(nombreUsuario);
        propietario.setEmail(email);
        //propietario.setPassword(new BCryptPasswordEncoder().encode(password));
        propietario.setPassword(password);
        propietario.setRol(Rol.PROPIETARIO);
        propietario.setTelefono(telefono);
        Date fechaAlta = new Date();
        propietario.setFechaAlta(fechaAlta);
        boolean activo = Boolean.TRUE;
        propietario.setActivo(activo);
        Imagen miImagen = imagenServicio.guardar(archivo);
        propietario.setFotoPerfil(miImagen);
        propietario.setPropiedades(propiedades);//lista de propiedades
        propietarioRepositorio.save(propietario);

    }

    private void validar(String nombre, String nombreUsuario, String email, String password, long telefono, MultipartFile archivo, String password2) throws Exception {
        if (nombre.isEmpty() || nombre == null) {
            throw new Exception("El nombre no puede estar estar vacío");
        }
        if (nombreUsuario.isEmpty() || nombreUsuario == null) {
            throw new Exception("El nombre de usuario no puede estar estar vacío");
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
        Propietario propietario = propietarioRepositorio.buscarPorEmail(email);
        if (propietario != null) {
            throw new Exception("El email ya se encuentra registrado, pruebe con otro");
        }
    }

    @Transactional
    public void actualizar(int id, String nombre, String nombreUsuario, String email, String password, long telefono, MultipartFile archivo) throws Exception {
        if (id < 0) {
            throw new Exception("Ingrese un id");
        }
        Optional<Propietario> respuesta = propietarioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Propietario propietario = respuesta.get();
            propietario.setNombre(nombre);
            propietario.setNombreUsuario(nombreUsuario);
            propietario.setEmail(email);
            propietario.setPassword(password);
            propietario.setTelefono(telefono);
            int idImagen = 0;
            if (propietario.getFotoPerfil() != null) {
                idImagen = propietario.getFotoPerfil().getIdImagen();
            }
            Imagen miImagen = imagenServicio.actualizar(archivo, idImagen);
            propietario.setFotoPerfil(miImagen);
            propietarioRepositorio.save(propietario);
        }
    }

    public List<Propietario> listarPropietario() {
        List<Propietario> propietario = propietarioRepositorio.findAll();
        return propietario;
    }

    @Transactional
    public void borrar(Integer id) {
        propietarioRepositorio.deleteById(id);
    }
    
    @Transactional
    public Propietario buscarPropietarioPorNombre(String nombre) {
        return propietarioRepositorio.buscarPorNombre(nombre);
    }
    
//     @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//
//        Propietario propietario = propietarioRepositorio.buscarPorEmail(email);
//
//        if (propietario != null) {
//
//            List<GrantedAuthority> permisos = new ArrayList();
//
//            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + propietario.getRol().toString());
//
//            permisos.add(p);
//
//            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
//
//            HttpSession session = attr.getRequest().getSession(true);
//
//            session.setAttribute("usuariosession", propietario);
//
//            return new Propietario(propietario.getEmail(), propietario.getPassword(), permisos);
//        } else {
//            return null;
//        }
//}
}