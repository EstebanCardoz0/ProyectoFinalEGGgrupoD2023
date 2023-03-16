package com.QuinchApp.Entidades;

import com.QuinchApp.Enums.Rol;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "cliente")
public class Cliente extends Usuario {

    @OneToMany
    private List<Reserva> reserva;

    public Cliente() {
    }

    public Cliente(List<Reserva> reserva, Integer id, String nombre, String nombreUsuario, String email, String password, long telefono, Rol rol, Imagen fotoPerfil, Date fechaAlta, boolean activo) {
        super(id, nombre, nombreUsuario, email, password, telefono, rol, fotoPerfil, fechaAlta, activo);
        //this.reserva = reserva;
        this.reserva = new ArrayList();
    }

}
