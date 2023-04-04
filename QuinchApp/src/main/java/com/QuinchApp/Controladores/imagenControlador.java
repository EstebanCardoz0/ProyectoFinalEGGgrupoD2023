package com.QuinchApp.Controladores;

import com.QuinchApp.Entidades.Imagen;
import com.QuinchApp.Entidades.Propiedad;
import com.QuinchApp.Entidades.Usuario;
import com.QuinchApp.Servicios.ImagenServicio;
import com.QuinchApp.Servicios.PropiedadServicio;
import com.QuinchApp.Servicios.UsuarioServicio;
import java.util.ArrayList;
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
    private ImagenServicio imagenServicio;
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

//    @GetMapping("/imagenesPropiedad/{id}")
//    public ResponseEntity<List<ResponseEntity<byte[]>>> imagenesPropiedad(@PathVariable Integer id) {
//        Propiedad propiedad = propiedadServicio.getOne(id);
//        List<Imagen> imagenes = propiedad.getImagenes();
//        List<ResponseEntity<byte[]>> respuestas = new ArrayList<>();
//        for (Imagen imagen : imagenes) {
//            byte[] contenido = imagen.getContenido();
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.IMAGE_JPEG);
//            respuestas.add(new ResponseEntity<>(contenido, headers, HttpStatus.OK));
//        }
//        return new ResponseEntity<>(respuestas, HttpStatus.OK);
//    }
////
    @GetMapping("/imgPropiedad/{id}")
    public ResponseEntity<byte[]> imagenPropiedad(@PathVariable Integer id) {
        Imagen imagen = imagenServicio.getOne(id);
        byte[] contenidoImagen = imagen.getContenido();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setContentLength(contenidoImagen.length);
        return new ResponseEntity<byte[]>(contenidoImagen, headers, HttpStatus.OK);
    }
//    
//    
//       @GetMapping("/imgPropiedad/{id}")
//public List<ResponseEntity<byte[]>> imagenPropiedad(@PathVariable Integer id) {
//    Propiedad propiedad = propiedadServicio.getOne(id);
//    List<Imagen> imagenes = propiedad.getImagenes();
//    List<ResponseEntity<byte[]>> responseEntityList = new ArrayList<>();
//    imagenes.stream().map((imagen) -> imagen.getContenido()).map((imagenPropiedad) -> {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.IMAGE_JPEG);
//        headers.setContentLength(imagenPropiedad.length);
//        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(imagenPropiedad, headers, HttpStatus.OK);
//            return responseEntity;
//        }).forEachOrdered((responseEntity) -> {
//            responseEntityList.add(responseEntity);
//        });
//    return responseEntityList;
//}

//    @GetMapping("/imgPropiedad/{id}")
//public ResponseEntity<List<byte[]>> imagenPropiedad(@PathVariable Integer id) {
//    Propiedad propiedad = propiedadServicio.getOne(id);
//    List<byte[]> bytesImagenes = new ArrayList<>();
//    for (int i=0; i < propiedad.getImagenes().size(); i++) {
//        bytesImagenes.add(propiedad.getImagenes().get(i).getContenido());
//    }
//    HttpHeaders headers = new HttpHeaders();
//    headers.setContentType(MediaType.IMAGE_JPEG);
//    return new ResponseEntity<List<byte[]>>(bytesImagenes, headers, HttpStatus.OK);
//}
}
