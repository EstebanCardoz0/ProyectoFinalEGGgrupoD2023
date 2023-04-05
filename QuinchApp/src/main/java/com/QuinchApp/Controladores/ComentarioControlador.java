package com.QuinchApp.Controladores;

import com.QuinchApp.Entidades.Propiedad;
import com.QuinchApp.Entidades.Usuario;
import com.QuinchApp.Servicios.ComentarioServicio;
import com.QuinchApp.Servicios.PropiedadServicio;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/comentario")
public class ComentarioControlador {

    @Autowired
    private ComentarioServicio comentarioServicio;

    @PostMapping("/comentar")
    public String comentar(@RequestParam("idPropiedad") Integer idPropiedad, @ModelAttribute("propiedad") Propiedad propiedad, @RequestParam("comentario") String coment, @RequestParam("calificacion") Integer calificacion, HttpSession session) throws Exception {
        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
        Integer idUsuario = usuario.getId();;
        comentarioServicio.crearComentario(idPropiedad, idUsuario, coment, calificacion);
        return "redirect:/propiedad/detallePropiedad";
    }

    @DeleteMapping("/borrar/{id}")
    public String borrarComentario(@PathVariable Integer id) {
        comentarioServicio.borrarComentario(id);
        return null;
    }

}
