package com.QuinchApp.Entidades;

import com.QuinchApp.Enums.Rol;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Cliente extends Usuario {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente", orphanRemoval = true)
    private List<Reserva> reservas = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comentario> comentarios = new ArrayList<>();

    public Cliente() {
    }

    public Cliente(Integer id, String nombre, String nombreUsuario, String email, String password, long telefono, Rol rol, Imagen fotoPerfil, Date fechaAlta, boolean activo) {
        super(id, nombre, nombreUsuario, email, password, telefono, rol, fotoPerfil, fechaAlta, activo);
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }

    public void addReserva(Reserva reserva) {
        reservas.add(reserva);
        reserva.setCliente(this);
    }

    public void removeReserva(Reserva reserva) {
        reservas.remove(reserva);
        reserva.setCliente(null);
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    @Override
    public String toString() {
        return "Cliente{"
                + "id=" + getId()
                + ", nombre='" + getNombre() + '\''
                + ", nombreUsuario='" + getNombreUsuario() + '\''
                + ", email='" + getEmail() + '\''
                + ", password='" + getPassword() + '\''
                + ", telefono=" + getTelefono()
                + ", rol=" + getRol()
                + ", fotoPerfil=" + getFotoPerfil()
                + ", fechaAlta=" + getFechaAlta()
                + ", activo=" + isActivo()
                + '}';
    }

    public Cliente(Integer id, String nombre, String nombreUsuario) {
        super(id, nombre, nombreUsuario);
    }

}
