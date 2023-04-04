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
public class Propietario extends Usuario {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "propietario")
    private List<Propiedad> propiedades;

    public Propietario() {
    }

    public Propietario(String nombre, String nombreUsuario, String email, String password, long telefono, Rol rol, Imagen fotoPerfil, Date fechaAlta, boolean activo) {
        super(nombre, nombreUsuario, email, password, telefono, rol, fotoPerfil, fechaAlta, activo);
    }

    public Propietario(Integer id, String nombre, String nombreUsuario, String email, String password, long telefono, Rol rol, Imagen fotoPerfil, Date fechaAlta, boolean activo) {
        super(id, nombre, nombreUsuario, email, password, telefono, rol, fotoPerfil, fechaAlta, activo);
    }

    public List<Propiedad> getPropiedades() {
        return propiedades;
    }

    public void setPropiedades(List<Propiedad> propiedades) {
        this.propiedades = propiedades;
    }

    public Propietario(Integer id, String nombre, String nombreUsuario) {
        super(id, nombre, nombreUsuario);
    }
    
}