package com.QuinchApp.Controladores;

import com.QuinchApp.Entidades.Comentario;
import com.QuinchApp.Entidades.Propiedad;
import com.QuinchApp.Entidades.Usuario;
import com.QuinchApp.Servicios.ComentarioServicio;
import com.QuinchApp.Servicios.PropiedadServicio;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
        @Autowired
    private PropiedadServicio propiedadServicio;


    @PostMapping("/comentar")
public String comentar(@RequestParam("idPropiedad") Integer idPropiedad, @ModelAttribute("propiedad") Propiedad propiedad, @RequestParam("comentario") String coment, @RequestParam("calificacion") Integer calificacion, HttpSession session) throws Exception {
    Usuario usuario = (Usuario) session.getAttribute("usuariosession");
    Integer idUsuario = usuario.getId();
    Integer idPropietario = propiedad.getPropietario().getId();
    System.out.print("idUsuario: " + idUsuario);
    System.out.print("idPropiedad: " + idPropiedad);
    System.out.print("calificacion: " + calificacion);
    System.out.print("idPropietario: " + idPropietario);

    comentarioServicio.crearComentario(idPropiedad, idUsuario, coment, calificacion);
    return "redirect:/propiedad/detallePropiedad";
}
    
    @GetMapping("/listar")
    public String listarComentarios() {        
        List<Comentario> comentarios = comentarioServicio.listarComentarios();
        return null;
    }
    
//    @GetMapping("/ListarComentariosPorPropiedad/{id}")
//    public String verComentarioPorPropiedad(@PathVariable int id, ModelMap modelo) {
//        Propiedad propiedad = propiedadServicio.getOne(id);
//        List<Comentario> comentarios = propiedad.getComentarios();
//        modelo.addAttribute("propiedad", propiedad);
//        modelo.addAttribute("comentarios", comentarios);
//        return "comentariosListar";
//    }

    
    
    
    @DeleteMapping("/borrar/{id}")
    public String borrarComentario(@PathVariable Integer id) {
        comentarioServicio.borrarComentario(id);
        return null;
    }
    
}
