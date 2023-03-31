package com.QuinchApp.Entidades;

import com.QuinchApp.Enums.Rol;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Cliente extends Usuario {

    @OneToMany
    private List<Reserva> reserva;

    public Cliente() {
        this.reserva = new ArrayList();
    }

    public Cliente(List<Reserva> reserva, Integer id, String nombre, String nombreUsuario, String email, String password, long telefono, Rol rol, Imagen fotoPerfil, Date fechaAlta, boolean activo) {
        super(id, nombre, nombreUsuario, email, password, telefono, rol, fotoPerfil, fechaAlta, activo);
        this.reserva = reserva;
    }

    public Cliente(Integer id, String nombre, String nombreUsuario, String email, String password, long telefono, Rol rol, Imagen fotoPerfil, Date fechaAlta, boolean activo) {
        super(id, nombre, nombreUsuario, email, password, telefono, rol, fotoPerfil, fechaAlta, activo);
        this.reserva = new ArrayList();
    }
}
