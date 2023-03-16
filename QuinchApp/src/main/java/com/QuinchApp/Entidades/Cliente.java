package com.QuinchApp.Entidades;

import com.QuinchApp.Enums.Rol;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "cliente")
public class Cliente extends Usuario {
    //ESPERAR LA RESERVA
    //private List <Reserva> reserva

    public Cliente() {
    }

    public Cliente(Integer id, String nombre, String nombreUsuario, String email, String password, long telefono, Rol rol, Imagen fotoPerfil, Date fechaAlta, boolean activo) {
        super(id, nombre, nombreUsuario, email, password, telefono, rol, fotoPerfil, fechaAlta, activo);
        //agregar reserva a esto
    }

}
