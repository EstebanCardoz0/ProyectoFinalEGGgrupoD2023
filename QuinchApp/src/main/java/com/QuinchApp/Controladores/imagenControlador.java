package com.QuinchApp.Controladores;

import com.QuinchApp.Entidades.Imagen;
import com.QuinchApp.Entidades.Propiedad;
import com.QuinchApp.Entidades.Usuario;
import com.QuinchApp.Servicios.PropiedadServicio;
import com.QuinchApp.Servicios.UsuarioServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/imagen")
public class imagenControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private PropiedadServicio propiedadServicio;

    @GetMapping("/perfil/{id}")
    public ResponseEntity<byte[]> imagen(@PathVariable Integer id) {
        Usuario usuario = usuarioServicio.getOne(id);
        byte[] imagen = usuario.getFotoPerfil().getContenido();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
    }

    @GetMapping("/imgPropiedad/{id}")
    public ResponseEntity<byte[]> imagenPropiedad(@PathVariable Integer id) {
        Propiedad propiedad = propiedadServicio.getOne(id);
        List<Imagen> imagenes = propiedad.getImagenes();
        Imagen imagen = imagenes.get(0);
        byte[] imagenPropiedad = imagen.getContenido();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setContentLength(imagenPropiedad.length);
        return new ResponseEntity<byte[]>(imagenPropiedad, headers, HttpStatus.OK);
    }

}
