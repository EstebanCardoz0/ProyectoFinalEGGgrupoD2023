package com.QuinchApp.Entidades;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comentario")
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idComentario;
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

    public Cliente getCliente() {
        return this.cliente;
    }
}
