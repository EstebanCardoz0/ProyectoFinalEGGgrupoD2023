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
            + "LOWER(p.nombre) LIKE LOWER(CONCAT('%', :name, '%')) OR "
            + "LOWER(p.ubicacion) LIKE LOWER(CONCAT('%', :name, '%')) OR "
            + "LOWER(p.descripcion) LIKE LOWER(CONCAT('%', :name, '%')) OR "
            + "LOWER(CASE p.tipoDePropiedad "
            + "WHEN 'QUINCHO' THEN 'quincho' "
            + "WHEN 'SALON_DE_FIESTA' THEN 'salon_de_fiestas' "
            + "WHEN 'CASA_QUINTA' THEN 'casa_quinta' "
            + "WHEN 'PATIO' THEN 'patio' "
            + "ELSE '' "
            + "END) LIKE LOWER(CONCAT('%', :name, '%')) AND p.disponibilidad LIKE true "
            + "OR LOWER(p.ubicacion) LIKE LOWER(CONCAT('%', :name, '%'))")
    public List<Propiedad> findAllByPalabraClave(@Param("name") String palabraClave);

    @Query("SELECT p FROM Propiedad p WHERE "
            + "LOWER(p.nombre) LIKE LOWER(CONCAT('%', :name, '%')) OR "
            + "LOWER(p.ubicacion) LIKE LOWER(CONCAT('%', :name, '%')) OR "
            + "LOWER(p.descripcion) LIKE LOWER(CONCAT('%', :name, '%')) OR "
            + "LOWER(CASE p.tipoDePropiedad "
            + "WHEN 'QUINCHO' THEN 'quincho' "
            + "WHEN 'SALON_DE_FIESTA' THEN 'salon_de_fiestas' "
            + "WHEN 'CASA_QUINTA' THEN 'casa_quinta' "
            + "WHEN 'PATIO' THEN 'patio' "
            + "ELSE '' "
            + "END) LIKE LOWER(CONCAT('%', :name, '%')) AND p.disponibilidad LIKE true "
            + "AND p.propietario.id = :propietarioId "
            + "OR LOWER(p.ubicacion) LIKE LOWER(CONCAT('%', :name, '%'))")
    public List<Propiedad> findAllByPropietarioIdAndPalabraClave(@Param("propietarioId") int propietarioId, @Param("name") String palabraClave);

    @Query("SELECT p FROM Propietario p WHERE p.id = :propietarioId")
    public List<Propiedad> findAllByPropietarioId(@Param("propietarioId") int propietarioId);

}
