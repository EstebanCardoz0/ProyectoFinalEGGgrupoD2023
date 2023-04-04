package com.QuinchApp.Controladores;

import com.QuinchApp.Entidades.Propiedad;
import com.QuinchApp.Enums.PropiedadEnum;
import com.QuinchApp.Enums.ServicioEnum;
import com.QuinchApp.Servicios.PropiedadServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/propiedad")
public class PropiedadControlador {

    @Autowired
    private PropiedadServicio propiedadServicio;

    @GetMapping("/registroPropiedad")
    public String registrarPropiedad() {
        return "registroPropiedad.html";
    }

    @GetMapping("/")
    public String index() {
        return "redirect: /detallePropiedad";
    }

    @GetMapping("/verPropiedad/{id}")
    public String verPropiedad(@PathVariable int id, ModelMap modelo) {
        Propiedad propiedad = propiedadServicio.getOne(id);
        modelo.addAttribute("propiedad", propiedad);
        return "vistaPropiedad.html";
    }

//    @PostMapping("/registroPropiedad")
//public String registroPropiedad(@RequestParam("nombre") String nombre, @RequestParam("ubicacion") String ubicacion,
//        @RequestParam("descripcion") String descripcion, @RequestParam("valor") double valor, @RequestParam("capacidad") int capacidad,
//        @RequestParam("tipoDePropiedad") PropiedadEnum tipoDePropiedad, @RequestParam("imagen") MultipartFile imagen, @RequestParam("servicio") ServicioEnum servicio, ModelMap modelo, Authentication authentication) {
//    try {
//   //   autenticacion que verifica cual es el usuario logueado y guarda el email
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//        String idPropietario = userDetails.getUsername();
//        propiedadServicio.registrarPropiedad(nombre, ubicacion, descripcion, valor, capacidad, tipoDePropiedad,
//                idPropietario, imagen, servicio);
//        modelo.put("exito", "La propiedad fue registrada correctamente!");
//    } catch (Exception e) {
//        System.out.println(e);
//        modelo.put("nombre", nombre);
//        modelo.put("ubicacion", ubicacion);
//        modelo.put("descripcion", descripcion);
//        modelo.put("valor", valor);
//        modelo.put("capacidad", capacidad);
//        modelo.put("tipoDePropiedad", tipoDePropiedad);
//        modelo.put("imagen", imagen);
//        modelo.put("servicio", servicio);
//        modelo.put("error", "Verifique que los datos hayan sido cargado correctamente.");
//    }
//    return "registroPropiedad.html";
//}
    @PostMapping("/registroPropiedad")
    public String registroPropiedad(@RequestParam("nombre") String nombre, @RequestParam("ubicacion") String ubicacion,
            @RequestParam("descripcion") String descripcion, @RequestParam("valor") double valor, @RequestParam("capacidad") int capacidad,
            @RequestParam("tipoDePropiedad") PropiedadEnum tipoDePropiedad, @RequestParam("imagen") List<MultipartFile> imagenes, @RequestParam("servicio") List<ServicioEnum> servicios, ModelMap modelo, Authentication authentication) {
        try {
            //   autenticacion que verifica cual es el usuario logueado y guarda el email
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String email = userDetails.getUsername();
            System.out.println("Email del usuario autenticado: " + email);
            propiedadServicio.registrarPropiedad(nombre, ubicacion, descripcion, valor, capacidad, tipoDePropiedad,
                    email, imagenes, servicios);
            modelo.put("exito", "La propiedad fue registrada correctamente!");
        } catch (Exception e) {
            System.out.println(e);
            modelo.put("nombre", nombre);
            modelo.put("ubicacion", ubicacion);
            modelo.put("descripcion", descripcion);
            modelo.put("valor", valor);
            modelo.put("capacidad", capacidad);
            modelo.put("tipoDePropiedad", tipoDePropiedad);
            modelo.put("imagen", imagenes);
            modelo.put("servicio", servicios);
            modelo.put("error", "Verifique que los datos hayan sido cargado correctamente.");
        }
        return "registroPropiedad.html";
    }

    @PostMapping("/actualizarPropiedad/{id}")
    public String actualizarPropiedad(@PathVariable int id, @RequestParam("nombre") String nombre, @RequestParam("descripcion") String descripcion,
            @RequestParam("valor") double valor, @RequestParam("capacidad") int capacidad,
            @RequestParam("imagen") MultipartFile imagen, @RequestParam("servicio") ServicioEnum servicio, ModelMap modelo) {
        try {
            propiedadServicio.actualizarPropiedad(id, nombre, descripcion, valor, capacidad, imagen, servicio);
            modelo.put("exito", "La propiedad fue actualizada correctamente!");
        } catch (Exception e) {
            System.out.println(e);
            modelo.put("nombre", nombre);
            modelo.put("descripcion", descripcion);
            modelo.put("valor", valor);
            modelo.put("capacidad", capacidad);
            modelo.put("imagen", imagen);
            modelo.put("servicio", servicio);
            modelo.put("error", "Verifique que los datos hayan sido cargado correctamente.");
        }
        return "actualizarPropiedad.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/listarPropiedades")
    public String listarPropiedades(ModelMap modelo, @Param("palabraClave") String palabraClave) {
        List<Propiedad> propiedad = propiedadServicio.listarPropiedades(palabraClave);
        modelo.addAttribute("propiedad", propiedad);
        modelo.addAttribute("palabraClave", palabraClave);
        return "listadoPropiedadesAdmin.html";
    }

    @GetMapping("/borrarPropiedad/{id}")
    public String borrarPropiedad(@PathVariable int id) {
        try {
            propiedadServicio.borrar(id);
        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:/propiedad/listarPropiedades";
    }

    @GetMapping("/verUbicacion/{id}")
    public String verUbicacion(@PathVariable int id) {
        try {
            propiedadServicio.verUbicacion(id);
            return "Exito!";
        } catch (Exception e) {
            System.out.println(e);
            return "Error";
        }
    }

    @GetMapping("/detallePropiedad")
    public String mostrarPropiedades(ModelMap modelo) {
        List<Propiedad> quinchoList = propiedadServicio.buscarPropiedadPorTipo(PropiedadEnum.QUINCHO);
        modelo.addAttribute("QUINCHO", quinchoList);
        List<Propiedad> quintaList = propiedadServicio.buscarPropiedadPorTipo(PropiedadEnum.CASA_QUINTA);
        modelo.addAttribute("CASA_QUINTA", quintaList);
        List<Propiedad> salonesList = propiedadServicio.buscarPropiedadPorTipo(PropiedadEnum.SALON_DE_FIESTA);
        modelo.addAttribute("SALON_DE_FIESTA", salonesList);
        List<Propiedad> patiioaList = propiedadServicio.buscarPropiedadPorTipo(PropiedadEnum.PATIO);
        modelo.addAttribute("PATIO", patiioaList);
        return "detallePropiedad";
    }

//@GetMapping("/detallePropiedad/quinchos")
//public String mostrarPropiedadesQuinchos(ModelMap modelo) {
//    List<Propiedad> propiedadesQuinchos = propiedadServicio.buscarPropiedadPorTipo(PropiedadEnum.QUINCHO);
//    modelo.addAttribute("propiedades", propiedadesQuinchos);
//    
//    return "detallePropiedad";
//}
}
