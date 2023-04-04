package com.QuinchApp.Entidades;

import com.QuinchApp.Enums.Rol;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private String nombreUsuario;
    private String email;
    private String password;
    private long telefono;
    @Enumerated(EnumType.STRING)
    private Rol rol;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Imagen fotoPerfil;
    @Temporal(TemporalType.DATE)
    private Date fechaAlta;
    private boolean activo;


    public Usuario(String nombre, String nombreUsuario, String email, String password, long telefono, Rol rol, Imagen fotoPerfil, Date fechaAlta, boolean activo) {
        this.nombre = nombre;
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.password = password;
        this.telefono = telefono;
        this.rol = rol;
        this.fotoPerfil = fotoPerfil;
        this.fechaAlta = fechaAlta;
        this.activo = activo;
    }

}

