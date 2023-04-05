package com.QuinchApp.Controladores;

import com.QuinchApp.Entidades.Propiedad;
import com.QuinchApp.Entidades.Propietario;
import com.QuinchApp.Servicios.PropiedadServicio;
import com.QuinchApp.Servicios.PropietarioServicio;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/propietario")
public class PropietarioControlador {

    @Autowired
    private PropietarioServicio propietarioServicio;

    @Autowired
    private PropiedadServicio propiedadServicio;

    @GetMapping("/registrar")
    public String registrar() {
        return "/perfil";
    }

    @GetMapping("/listar")
    public String listar(ModelMap modelo) {
        List<Propietario> propietarios = propietarioServicio.listarPropietario();
        modelo.addAttribute("propietarios", propietarios);
        return "propietarioList";
    }

    @GetMapping("/verPropiedadesPr/{id}")
    public String verPropiedadesPr(@PathVariable int id, ModelMap modelo) {
        Propietario propietario = propietarioServicio.getOne(id);
        List<Propiedad> propiedades = propietario.getPropiedades();
        modelo.addAttribute("propietario", propietario);
        modelo.addAttribute("propiedades", propiedades);
        return "listadoPropiedades";
    }

    @GetMapping("/borrarPropiedad/{id}")
    public String borrarPropiedad(@PathVariable int id, HttpSession session) {
        try {
            propiedadServicio.borrar(id);
        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:/dashboardCliente";
    }

    @RequestMapping("/altaBaja/{id}")
    public String altaBaja(@PathVariable int id) {
        propiedadServicio.bajaAlta(id);
        return "redirect:/dashboardCliente";
    }

}
