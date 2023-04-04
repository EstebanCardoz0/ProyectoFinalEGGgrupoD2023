package com.QuinchApp.Entidades;

import com.QuinchApp.Enums.Rol;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Cliente extends Usuario {

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reserva> reserva;

    public Cliente() {
    }

    public Cliente(Integer id, String nombre, String nombreUsuario, String email, String password, long telefono, Rol rol, Imagen fotoPerfil, Date fechaAlta, boolean activo) {
        super(id, nombre, nombreUsuario, email, password, telefono, rol, fotoPerfil, fechaAlta, activo);
    }

    public List<Reserva> getReservas() {
        return reserva;
    }

    public void setReservas(List<Reserva> reserva) {
        this.reserva = reserva;
    }
    
@Override
    public String toString() {
        return "Cliente{" +
                "id=" + getId() +
                ", nombre='" + getNombre() + '\'' +
                ", nombreUsuario='" + getNombreUsuario() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", password='" + getPassword() + '\'' +
                ", telefono=" + getTelefono() +
                ", rol=" + getRol() +
                ", fotoPerfil=" + getFotoPerfil() +
                ", fechaAlta=" + getFechaAlta() +
                ", activo=" + isActivo() +
                '}';
    }
}
