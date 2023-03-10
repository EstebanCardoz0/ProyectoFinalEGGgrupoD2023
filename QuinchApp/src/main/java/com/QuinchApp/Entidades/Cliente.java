/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.QuinchApp.Entidades;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;

/**
 *
 * @author Esteban
 */
@Entity
@Data
@Table(name = "cliente")
public class Cliente extends Usuario {

    public Cliente(Integer id, String nombre, String nombreUsuario, String email, String password, long telefono, Imagen fotoPerfil, Date fechaAlta, boolean activo) {
        super(id, nombre, nombreUsuario, email, password, telefono, fotoPerfil, fechaAlta, activo);
    }

    
}
