package com.QuinchApp.Controladores;

import com.QuinchApp.Entidades.Propiedad;
import com.QuinchApp.Entidades.Propietario;
import com.QuinchApp.Entidades.Usuario;
import com.QuinchApp.Servicios.PropiedadServicio;
import com.QuinchApp.Servicios.PropietarioServicio;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
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

    @PreAuthorize("hasAnyRole('ROLE_PROPIETARIO')")
    @GetMapping("/verPropiedadesPr/{id}")
    public String verPropiedadesPr(@PathVariable int id, ModelMap modelo, @Param("palabraClave") String palabraClave) {
        Propietario propietario = propietarioServicio.getOne(id);
        List<Propiedad> propiedades = propietario.getPropiedades();
        modelo.addAttribute("propietario", propietario);
        modelo.addAttribute("propiedades", propiedades);
        modelo.addAttribute("palabraClave", palabraClave);
        return "listadoPropiedades";
    }

    @PreAuthorize("hasAnyRole('ROLE_PROPIETARIO')")
    @GetMapping("/ListarPropiedadesPropietario")
    public String listarPropiedadesPropietario(ModelMap modelo, @Param("palabraClave") String palabraClave, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
        int id = usuario.getId();
        List<Propiedad> propiedades = propietarioServicio.listarPropiedadesPropietario(palabraClave, id);
        modelo.addAttribute("propiedades", propiedades);
        modelo.addAttribute("palabraClave", palabraClave);
        return "listadoPropiedades";
    }

    @GetMapping("/ListarPropiedadesPropietario/Limpiar")
    public String limpiarFiltro(ModelMap modelo, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
        int id = usuario.getId();
        return "redirect:/propietario/verPropiedadesPr/" + id;
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
