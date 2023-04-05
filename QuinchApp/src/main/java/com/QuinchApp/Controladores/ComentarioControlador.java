package com.QuinchApp.Controladores;

import com.QuinchApp.Entidades.Comentario;
import com.QuinchApp.Entidades.Propiedad;
import com.QuinchApp.Entidades.Usuario;
import com.QuinchApp.Servicios.ComentarioServicio;
import com.QuinchApp.Servicios.PropiedadServicio;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

@GetMapping("/misComentarios")
public String misComentarios(Model modelo, HttpSession session, @Param("palabraClave") String palabraClave) {
    Usuario usuario = (Usuario) session.getAttribute("usuariosession");
    Integer idUsuario = usuario.getId();
    List<Comentario> misComentarios;
    if (palabraClave != null && !palabraClave.isEmpty()) {
        misComentarios = comentarioServicio.buscarComentariosPorIdClienteYPalabraClave(idUsuario, palabraClave);
    } else {
        misComentarios = comentarioServicio.buscarComentariosPorIdCliente(idUsuario);
    }
    modelo.addAttribute("comentarios", misComentarios);
    modelo.addAttribute("palabraClave", palabraClave);
    return "comentariosPorCliente";
}

}
