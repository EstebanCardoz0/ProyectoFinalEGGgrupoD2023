package com.QuinchApp.Entidades;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reserva")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idReserva;
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Temporal(TemporalType.DATE)
    private Date fechaSalida;
    @OneToOne
    private Propiedad propiedad;
    @ManyToOne
    private Cliente cliente;
    private Boolean confirmada;

    public Reserva(Date fechaInicio,Date fechaSalida, Propiedad propiedad, Cliente Cliente) {
        this.fechaInicio = fechaInicio;
        this.fechaSalida = fechaSalida;
        this.propiedad = propiedad;
        this.cliente = cliente;
    }

}
