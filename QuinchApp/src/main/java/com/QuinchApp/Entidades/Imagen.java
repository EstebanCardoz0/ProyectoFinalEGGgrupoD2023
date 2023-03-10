package com.QuinchApp.Entidades;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "imagen")
public class Imagen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idImagen;
    private String mime;
    private String nombre;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] contenido;

}
