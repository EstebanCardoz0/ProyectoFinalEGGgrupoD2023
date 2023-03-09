/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.QuinchApp.Controlador;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Esteban
 */
@RestController
public class Controlador {
    
    @GetMapping("/alquiler")
    public String alquiler(){
    
    return "Hola grupo D, esto es una prueba";
    }
    
}
