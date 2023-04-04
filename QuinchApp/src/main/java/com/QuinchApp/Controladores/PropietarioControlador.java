package com.QuinchApp.Controladores;

import com.QuinchApp.Entidades.Propiedad;
import com.QuinchApp.Entidades.Propietario;
import com.QuinchApp.Entidades.Usuario;
import com.QuinchApp.Servicios.PropiedadServicio;
import com.QuinchApp.Servicios.PropietarioServicio;
import com.QuinchApp.Servicios.UsuarioServicio;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/propietario")
public class PropietarioControlador {

    @Autowired
    private PropietarioServicio propietarioServicio;

    @Autowired
    private PropiedadServicio propiedadServicio;

//
//    @PostMapping("/actualizar/{id}")
//    public String actualizar(@PathVariable int id, @RequestParam("nombre") String nombre, @RequestParam("nombreUsuario") String nombreUsuario,
//            @RequestParam("email") String email, @RequestParam("password") String password, @RequestParam("telefono") long telefono,
//            @RequestParam("archivo") MultipartFile archivo) throws Exception {
//        try {
//            usuarioServicio.actualizar(id, nombre, nombreUsuario, email, password, telefono, archivo);
//            return "Exito";
//        } catch (Exception exception) {
//            System.out.println(exception);
//            return "Error";
//        }
//    }
//
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

//
//    @DeleteMapping("/borrar/{id}")
//    public String borrarUsuario(@PathVariable Integer id) throws Exception {
//        try {
//            usuarioServicio.borrar(id);
//            return "Exito";
//        } catch (Exception exception) {
//            System.out.println(exception);
//            return "Error";
//        }
//    }
//
//    @GetMapping("/terminos")
//    public String terminos() {
//        return "terminos";
//    }

