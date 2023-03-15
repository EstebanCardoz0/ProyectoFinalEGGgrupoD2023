package com.QuinchApp.Servicios;

import com.QuinchApp.Entidades.Propiedad;
import com.QuinchApp.Entidades.Propietario;
import com.QuinchApp.Entidades.Usuario;
import com.QuinchApp.Repositorios.UsuarioRepositorio;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PropietarioServicio extends UsuarioServicio {
    

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Transactional
    public void convertirAPropietario(Integer idUsuario, List<Propiedad> propiedades) throws Exception {
        Optional<Usuario> usuarioOptional = usuarioRepositorio.findById(idUsuario);
        if (usuarioOptional.isPresent()) {
            Usuario usuarioNormal = usuarioOptional.get();
            Propietario propietario = new Propietario();
            BeanUtils.copyProperties(usuarioNormal, propietario, "id");
            propietario.setPropiedades(propiedades);
            usuarioRepositorio.delete(usuarioNormal);
            usuarioRepositorio.save(propietario);
        } else {
            throw new Exception("Usuario no encontrado");
        }
    }
}