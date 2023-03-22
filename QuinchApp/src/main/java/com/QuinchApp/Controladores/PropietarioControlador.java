/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.QuinchApp.Controladores;

import com.QuinchApp.Entidades.Usuario;
import com.QuinchApp.Servicios.UsuarioServicio;
import java.util.List;
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
    private UsuarioServicio usuarioServicio;

    @GetMapping("/registrar")
    public String registrar() {
        return "/perfil";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam("nombre") String nombre, @RequestParam("nombreUsuario") String nombreUsuario,
            @RequestParam("email") String email, @RequestParam("password") String password, @RequestParam("password") String password2, @RequestParam("telefono") long telefono,
            @RequestParam("archivo") MultipartFile archivo, ModelMap modelo) throws Exception {
        try {
            usuarioServicio.registrar(nombre, nombreUsuario, email, password, password2, telefono, archivo);
            modelo.put("exito", "El usuario fue registrado correctamente!");
        } catch (Exception exception) {
            System.out.println(exception);
            modelo.put("nombre", nombre);
            modelo.put("nombreUsuario", nombreUsuario);
            modelo.put("telefono", telefono);
            modelo.put("email", email);
            modelo.put("password", password);
            modelo.put("password2", password2);
            modelo.put("archivo", archivo);
            modelo.put("error", "Verifique que los datos hayan sido cargado correctamente y el email no este registrado");
            return "registro";
        }
        return "registro";
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

    @GetMapping("/listar")
    public String listar(ModelMap modelo) {
        List<Usuario> usuarios = usuarioServicio.listarUsuarios();
        modelo.addAttribute("usuario", usuarios);
        return "usuarioList";
    }

    @DeleteMapping("/borrar/{id}")
    public String borrarUsuario(@PathVariable Integer id) throws Exception {
        try {
            usuarioServicio.borrar(id);
            return "Exito";
        } catch (Exception exception) {
            System.out.println(exception);
            return "Error";
        }
    }

    @GetMapping("/terminos")
    public String terminos() {
        return "terminos";
    }

}
