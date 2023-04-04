/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.QuinchApp.Entidades;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Usuario
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comentario")
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idComentario;

//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)

    @ManyToOne(cascade = CascadeType.ALL)
    private Cliente cliente;

    private String comentario;
    private Integer calificacion;
    @ManyToOne(cascade = CascadeType.ALL)
    private Propiedad propiedad;



    public Comentario(Cliente cliente, String comentario, Integer calificacion, Propiedad propiedad) {
        this.cliente = cliente;
        this.comentario = comentario;
        this.calificacion = calificacion;
        this.propiedad = propiedad;
    }
    
}
