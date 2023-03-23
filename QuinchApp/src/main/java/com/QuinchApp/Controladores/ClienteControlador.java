/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.QuinchApp.Controladores;

import com.QuinchApp.Entidades.Cliente;
import com.QuinchApp.Entidades.Reserva;
import com.QuinchApp.Servicios.ClienteServicio;
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

/**
 *
 * @author Esteban
 */
@Controller
@RequestMapping("/cliente")
public class ClienteControlador {

    @Autowired
    private ClienteServicio clienteServicio;

    @GetMapping("/registrar")
    public String registrar() {
        return "/registro";//PREGUNTAR POR VISTAS
    }

    @PostMapping("/registro") //ESPERAR RESERVA
    public String registro(@RequestParam("nombre") String nombre, @RequestParam("nombreCliente") String nombreCliente,
            @RequestParam("email") String email, @RequestParam("password") String password, @RequestParam("password") String password2, @RequestParam("telefono") long telefono,
            @RequestParam("archivo") MultipartFile archivo, ModelMap modelo) throws Exception {
        try {
            clienteServicio.registrar(nombre, nombreCliente, email, password, password2, telefono, archivo);
            modelo.put("exito", "El cliente fue registrado correctamente!");
        } catch (Exception exception) {
            System.out.println(exception);
            modelo.put("nombre", nombre);
            modelo.put("nombreCliente", nombreCliente);
            modelo.put("telefono", telefono);
            modelo.put("email", email);
            modelo.put("password", password);
            modelo.put("password2", password2);
            modelo.put("archivo", archivo);
            modelo.put("error", "Verifique que los datos hayan sido cargado correctamente y el email no esté registrado");
            return "registro";
        }
        return "registro";
    }
//preguntar como suma reserva
    @PostMapping("/actualizar/{id}")
    public String actualizar(@PathVariable int id, @RequestParam("nombre") String nombre, @RequestParam("nombreCliente") String nombreCliente,
            @RequestParam("email") String email, @RequestParam("password") String password, @RequestParam("telefono") long telefono,
            @RequestParam("archivo") MultipartFile archivo, @RequestParam("reserva") Reserva reserva) throws Exception {
        try {
            clienteServicio.actualizar(reserva,id, nombre, nombreCliente, email, password, telefono, archivo);
            return "Exito";
        } catch (Exception exception) {
            System.out.println(exception);
            return "Error";
        }
    }

    @GetMapping("/listar")
    public String listar(ModelMap modelo) {
        List<Cliente> clientes = clienteServicio.listarClientes();
        modelo.addAttribute("cliente", clientes);
        return "usuarioList";//MODIFICAR
    }

    @DeleteMapping("/borrar/{id}")
    public String borrarCliente(@PathVariable Integer id) throws Exception {
        try {
            clienteServicio.borrar(id);
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
        
        @GetMapping("/dashboard")
    public String panelAdministrativo(){
        return "panel.html";
    }

}//final
