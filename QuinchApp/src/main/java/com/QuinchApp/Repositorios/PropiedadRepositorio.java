package com.QuinchApp.Repositorios;

import com.QuinchApp.Entidades.Propiedad;
import com.QuinchApp.Enums.PropiedadEnum;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PropiedadRepositorio extends JpaRepository<Propiedad, Integer> {

    @Query("SELECT pr FROM Propiedad pr WHERE pr.nombre = :nombre")
    public Propiedad buscarPorNombre(@Param("nombre") String nombre);
    
    @Query("SELECT pr FROM Propiedad pr WHERE pr.nombre = :nombre")
    public List<Propiedad> buscarPorPropietario(@Param("nombre") String nombre);
    
    @Query("SELECT pr FROM Propiedad pr WHERE pr.tipoDePropiedad = :tipoDePropiedad")
    public List<Propiedad> buscarPropiedadPorTipo(@Param("tipoDePropiedad") PropiedadEnum tipoDePropiedad);
}
