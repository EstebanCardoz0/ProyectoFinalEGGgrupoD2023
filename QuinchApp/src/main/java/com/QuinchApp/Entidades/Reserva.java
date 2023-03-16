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
    private Date FechaSalida;
    @OneToOne
    private Propiedad propiedad;
    @ManyToOne
    private Cliente Cliente;
    private Boolean confirmada;

    public Reserva(Date FechaSalida, Propiedad propiedad, Cliente Cliente) {
        this.FechaSalida = FechaSalida;
        this.propiedad = propiedad;
        this.Cliente = Cliente;
    }

}