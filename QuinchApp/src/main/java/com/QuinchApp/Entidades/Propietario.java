package com.QuinchApp.Entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "propietario")
public class Propietario extends Usuario {

    @OneToMany
    private List<Propiedad> propiedades;

    public Propietario(Integer id, String nombre, String nombreUsuario, String email, String password, long telefono, Imagen fotoPerfil, Date fechaAlta, boolean activo) {
        super(id, nombre, nombreUsuario, email, password, telefono, fotoPerfil, fechaAlta, activo);
    }
    
}
