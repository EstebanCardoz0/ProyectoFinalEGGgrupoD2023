package com.QuinchApp.Repositorios;

import com.QuinchApp.Entidades.Usuario;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer> {

    @Query("SELECT u FROM Usuario u WHERE u.email = :email")
    public Usuario buscarPorEmail(@Param("email") String email);

    @Query("SELECT u FROM Usuario u WHERE u.nombreUsuario = :nombreUsuario")
    public Optional<Usuario> buscarPorNombreUsuario(@Param("nombreUsuario") String nombreUsuario);

    @Query("SELECT u FROM Usuario u WHERE u.nombre = :nombre")
    public Usuario buscarPorNombre(@Param("nombre") String nombre);

    @Query("SELECT u FROM Usuario u WHERE u.id = :id")
    public Usuario buscarPorId(@Param("id") Integer idUsuario);

    Usuario findByEmail(String email);

@Query("SELECT u FROM Usuario u WHERE "
            + "LOWER(CONCAT(u.id, u.nombre, u.nombreUsuario, u.email, u.telefono, "
            + "CASE u.rol "
            + "WHEN 'CLIENTE' THEN 'cliente' "
            + "WHEN 'PROPIETARIO' THEN 'propietario' "
            + "WHEN 'ADMIN' THEN 'admin' "
            + "ELSE '' "
            + "END, u.fechaAlta, u.activo)) "
            + "LIKE LOWER(CONCAT('%', :name, '%'))")
    public List<Usuario> findAll(@Param("name") String palabraClave);

}
