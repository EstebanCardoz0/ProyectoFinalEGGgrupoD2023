package com.QuinchApp.Entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "propiedad")
public class Propiedad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPropiedad;

    private String nombre;
    private String Descripcion;
    private double valor;
    private int capacidad;
    private boolean disponibilidad;
//- Enum tipo de propiedad
//- List<Imagen> imagenes
//- List <Servicio> servicios
//- Ubicacion ubicacion
//- Propietario propietario
//- Date calendario

    public Propiedad(Integer idPropiedad, String nombre, String Descripcion, double valor, int capacidad, boolean disponibilidad) {
        this.idPropiedad = idPropiedad;
        this.nombre = nombre;
        this.Descripcion = Descripcion;
        this.valor = valor;
        this.capacidad = capacidad;
        this.disponibilidad = disponibilidad;
    }

}
