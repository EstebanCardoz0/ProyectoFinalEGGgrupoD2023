/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.QuinchApp.Entidades;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;

/**
 *
 * @author Esteban
 */
@Entity @Data @Table(name="propietario")
public class Propietario extends Usuario {
    
    private List<Propiedad> propiedades;

    public Propietario(Integer id, String nombre, String nombreUsuario, String email, String password, long telefono, Imagen fotoPerfil, Date fechaAlta, boolean activo) {
        super(id, nombre, nombreUsuario, email, password, telefono, fotoPerfil, fechaAlta, activo);
    }

   

   


    
    
    
    
    
}
