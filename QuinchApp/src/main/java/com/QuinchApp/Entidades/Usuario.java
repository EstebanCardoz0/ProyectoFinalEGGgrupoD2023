/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.QuinchApp.Entidades;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;

/**
 *
 * @author Esteban
 */
@Entity @Data @Table(name="usuario")
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private String nombreUsuario;
    private String email;
    private String password;
    private long telefono;
    //rol
    @OneToOne
    private Imagen fotoPerfil;
    @Temporal(TemporalType.DATE)
    private Date fechaAlta;
    private boolean activo;

    public Usuario(Integer id, String nombre, String nombreUsuario, String email, String password, long telefono, Imagen fotoPerfil, Date fechaAlta, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.password = password;
        this.telefono = telefono;
        this.fotoPerfil = fotoPerfil;
        this.fechaAlta = fechaAlta;
        this.activo = activo;
    }






 
    
    
    
}
