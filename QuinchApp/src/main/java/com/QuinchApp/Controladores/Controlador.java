package com.QuinchApp.Controladores;

import com.QuinchApp.Entidades.Propietario;
import com.QuinchApp.Entidades.Usuario;
import com.QuinchApp.Repositorios.PropietarioRepositorio;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Controller
@RequestMapping("/")
public class Controlador {

    @Autowired
    private PropietarioRepositorio propietarioRepositorio;

    @GetMapping("/index")
    public String index(ModelMap modelo) {
        return "index.html";
    }

    @GetMapping("/dashboardCliente")
    public String dashboardCliente(ModelMap modelo) {
        return "dashboardCliente.html";
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, String email, String password, ModelMap modelo) {
        if (error != null) {
            modelo.put("email", email);
            modelo.put("password", password);
            modelo.put("error", "El usuario o la contraseña es incorrecto, vuelva a intentarlo");
        }
        return "login";
    }

    @GetMapping("/registro")
    public String registro(@RequestParam(required = false) String error, ModelMap modelo) {
        if (error != null) {
            modelo.put("Error", "El usuario o la contraseña es incorrecto");
        }
        return "registro";
    }

    @GetMapping("/quienes-somos")
    public String quienesSomos() {
        return "quienes-somos";
    }

    public UserDetails cargarPropietarioPorNombre(String nombre) {

        Propietario propietario = propietarioRepositorio.buscarPorNombre(nombre);

        if (propietario != null) {
            
            List<GrantedAuthority> permisos = new ArrayList();
            
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE"+propietario.getRol().toString());
            
            permisos.add(p);
            
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession session = attr.getRequest().getSession(true);

            session.setAttribute("propietariosession", propietario);
            
            return new User(propietario.getNombre(), propietario.getPassword(), permisos);
            
        } else {
            return null;
        }
        
    }

}
