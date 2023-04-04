package com.QuinchApp.Repositorios;

import com.QuinchApp.Entidades.Propiedad;
import com.QuinchApp.Entidades.Usuario;
import com.QuinchApp.Enums.PropiedadEnum;
import java.util.List;
import java.util.Optional;
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

    @Query("SELECT pr FROM Propiedad pr WHERE pr.idPropiedad = :idPropiedad")
    public Optional<Propiedad> buscarPorIdPropiedad(@Param("idPropiedad") int idPropiedad);

    @Query("SELECT pr FROM Propiedad pr WHERE pr.tipoDePropiedad = :tipoDePropiedad")
    public List<Propiedad> buscarPropiedadPorTipo(@Param("tipoDePropiedad") PropiedadEnum tipoDePropiedad);

    @Query("SELECT p FROM Propiedad p WHERE "
            + "LOWER(CONCAT(p.idPropiedad, p.nombre, p.ubicacion, p.descripcion, p.valor, p.capacidad,  "
            + "CASE p.tipoDePropiedad "
            + "WHEN 'QUINCHO' THEN 'quincho' "
            + "WHEN 'SALON_DE_FIESTA' THEN 'salon_de_fiestas' "
            + "WHEN 'CASA_QUINTA' THEN 'casa_quinta' "
            + "WHEN 'PATIO' THEN 'patio' "
            + "ELSE '' "
            + "END, p.disponibilidad)) "
            + "LIKE LOWER(CONCAT('%', :name, '%'))")
    public List<Propiedad> findAll(@Param("name") String palabraClave);

}
