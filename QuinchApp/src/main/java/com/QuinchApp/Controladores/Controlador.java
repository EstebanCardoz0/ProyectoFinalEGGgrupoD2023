package com.QuinchApp.Controladores;

import com.QuinchApp.Servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class Controlador {

    @Autowired
    public UsuarioServicio usuarioServicio;

    @PreAuthorize("hasAnyRole('ROLE_CLIENTE', 'ROLE_PROPIETARIO')")
    @GetMapping("/dashboardCliente")
    public String dashboardCliente(ModelMap modelo) {
        return "dashboardCliente.html";
    }

    @GetMapping("/")
    public String index(ModelMap modelo) {
        return "redirect:/propiedad/detallePropiedad";
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, String email, String password, ModelMap modelo) {
        if (error != null) {
            modelo.put("email", email);
            modelo.put("password", password);
            modelo.put("error", "El usuario o la contraseña es incorrecto, vuelva a intentarlo");
            return "redirect:/logout";
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

}
