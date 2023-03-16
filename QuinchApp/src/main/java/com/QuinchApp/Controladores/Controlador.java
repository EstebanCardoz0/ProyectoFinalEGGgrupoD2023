package com.QuinchApp.Controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class Controlador {

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error,String  email,String password, ModelMap modelo) {
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

}


