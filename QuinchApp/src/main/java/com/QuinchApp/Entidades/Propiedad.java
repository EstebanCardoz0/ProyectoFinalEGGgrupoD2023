package com.QuinchApp.Entidades;

import com.QuinchApp.Enums.PropiedadEnum;
import com.QuinchApp.Enums.ServicioEnum;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
    private String ubicacion;
    private String descripcion;
    private double valor;
    private int capacidad;
    private boolean disponibilidad;
    @Enumerated(EnumType.STRING)
    private PropiedadEnum tipoDePropiedad;
    @ManyToOne
    @JoinColumn(name = "propietario_id")
    private Propietario propietario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "propiedad", orphanRemoval = true)
    private List<Reserva> reservas = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "propiedad_id")
    private List<Imagen> imagenes = new ArrayList<>();
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<ServicioEnum> servicios = new ArrayList<>();

    public Propiedad() {
    }

    public Propiedad(String nombre, String ubicacion, String descripcion, double valor, int capacidad, boolean disponibilidad, PropiedadEnum tipoDePropiedad, Propietario propietario) {
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.descripcion = descripcion;
        this.valor = valor;
        this.capacidad = capacidad;
        this.disponibilidad = disponibilidad;
        this.tipoDePropiedad = tipoDePropiedad;
        this.propietario = propietario;
    }

    @Override
    public String toString() {
        return "Propiedad [idPropiedad=" + idPropiedad + ", nombre=" + nombre + ", ubicacion=" + ubicacion
                + ", Descripcion=" + descripcion + ", valor=" + valor + ", capacidad=" + capacidad
                + ", disponibilidad=" + disponibilidad + ", tipoDePropiedad=" + tipoDePropiedad + "]";
    }

    public void addReserva(Reserva reserva) {
        reservas.add(reserva);
        reserva.setPropiedad(this);
    }

    public void removeReserva(Reserva reserva) {
        reservas.remove(reserva);
        reserva.setPropiedad(null);
    }

    public void addImagen(Imagen imagen) {
        imagenes.add(imagen);
    }

    public void removeImagen(Imagen imagen) {
        imagenes.remove(imagen);
    }

}
