package com.QuinchApp.Controladores;

import com.QuinchApp.Servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/usuario")
public class UsuarioControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/registrar")
    public String registrar() {
        return "registro";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam("nombre") String nombre, @RequestParam("nombreUsuario") String nombreUsuario,
            @RequestParam("email") String email, @RequestParam("password") String password, @RequestParam("telefono") long telefono,
            @RequestParam("archivo") MultipartFile archivo) throws Exception {
        try {
            usuarioServicio.registrar(nombre, nombreUsuario, email, password, telefono, archivo);
            return "Exito";
        } catch (Exception exception) {
            System.out.println(exception);
            return "Error";
        }
    }

    @PostMapping("/actualizar/{id}")
    public String actualizar(@PathVariable int id, @RequestParam("nombre") String nombre, @RequestParam("nombreUsuario") String nombreUsuario,
            @RequestParam("email") String email, @RequestParam("password") String password, @RequestParam("telefono") long telefono,
            @RequestParam("archivo") MultipartFile archivo) throws Exception {
        try {
            usuarioServicio.actualizar(id, nombre, nombreUsuario, email, password, telefono, archivo);
            return "Exito";
        } catch (Exception exception) {
            System.out.println(exception);
            return "Error";
        }
    }
}
