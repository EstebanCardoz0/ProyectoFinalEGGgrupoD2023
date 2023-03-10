package com.QuinchApp.Controladores;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controlador {

    @GetMapping("/alquiler")
    public String alquiler(){
    
    return "Hola grupo D";
    }
    
}
