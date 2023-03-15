package com.QuinchApp.Entidades;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "cliente")
public class Cliente extends Usuario {
    
    //private List <Reserva> reserva

    
    
    public Cliente() {
    }

    public Cliente(Integer id, String nombre, String nombreUsuario, String email, String password, long telefono, Imagen fotoPerfil, Date fechaAlta, boolean activo) {
        super(id, nombre, nombreUsuario, email, password, telefono, fotoPerfil, fechaAlta, activo);
        //agregar reserva a esto
    }

}
