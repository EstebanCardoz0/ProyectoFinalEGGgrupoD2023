package com.QuinchApp.Controladores;

import com.QuinchApp.Entidades.Propiedad;
import com.QuinchApp.Entidades.Propietario;
import com.QuinchApp.Enums.PropiedadEnum;
import com.QuinchApp.Enums.ServicioEnum;
import com.QuinchApp.Servicios.PropiedadServicio;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@RequestMapping("/propiedad")
public class PropiedadControlador {

    @Autowired
    private PropiedadServicio propiedadServicio;

    @GetMapping("/registrarPropiedad")
    public String registrarPropiedad() {
        return "registroPropiedad.html";
    }

    @PostMapping("/registroPropiedad")
    public String registroPropiedad(@RequestParam("nombre") String nombre, @RequestParam("ubicacion") String ubicacion,
            @RequestParam("descripcion") String descripcion, @RequestParam("valor") double valor, @RequestParam("capacidad") int capacidad,
            @RequestParam("tipoDePropiedad") PropiedadEnum tipoDePropiedad, @RequestParam("propietario") Propietario propietario,
            @RequestParam("imagen") MultipartFile imagen, @RequestParam("servicio") ServicioEnum servicio, ModelMap modelo) {
        try {
            propiedadServicio.registrarPropiedad(nombre, ubicacion, descripcion, valor, capacidad, tipoDePropiedad,
                    propietario, imagen, servicio);
            modelo.put("exito", "La propiedad fue registrada correctamente!");
        } catch (Exception e) {
            System.out.println(e);
            modelo.put("nombre", nombre);
            modelo.put("ubicacion", ubicacion);
            modelo.put("descripcion", descripcion);
            modelo.put("valor", valor);
            modelo.put("capacidad", capacidad);
            modelo.put("tipoDePropiedad", tipoDePropiedad);
            modelo.put("propietario", propietario);
            modelo.put("imagen", imagen);
            modelo.put("servicio", servicio);
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

    @GetMapping("/listarPropiedades")
    public String listarPropiedades(ModelMap modelo) {
        List<Propiedad> propiedades = propiedadServicio.listarPropiedades();
        modelo.addAttribute("propiedad", propiedades);
        return "listaPropiedades.html";
    }

    @DeleteMapping("/borrarPropiedad/{id}")
    public String borrarPropiedad(@PathVariable int id) {
        try {
            propiedadServicio.borrar(id);
            return "Exito!";
        } catch (Exception e) {
            System.out.println(e);
            return "Error";
        }
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

}