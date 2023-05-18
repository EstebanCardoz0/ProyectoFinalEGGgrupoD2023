package com.QuinchApp.Controladores;

import com.QuinchApp.Entidades.Comentario;
import com.QuinchApp.Entidades.Imagen;
import com.QuinchApp.Entidades.Propiedad;
import com.QuinchApp.Entidades.Usuario;
import com.QuinchApp.Enums.PropiedadEnum;
import com.QuinchApp.Enums.ServicioEnum;
import com.QuinchApp.Servicios.ImagenServicio;
import com.QuinchApp.Servicios.PropiedadServicio;
import com.QuinchApp.Servicios.UsuarioServicio;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/propiedad")
public class PropiedadControlador {

    @Autowired
    private PropiedadServicio propiedadServicio;
    @Autowired
    private ImagenServicio imagenServicio;
    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/registroPropiedad")
    public String registrarPropiedad() {
        return "registroPropiedad.html";
    }

    @GetMapping("/")
    public String index() {
        return "redirect: /detallePropiedad";
    }

    @GetMapping("/verPropiedad/{id}")
    public String verPropiedad(@PathVariable int id, ModelMap modelo) {
        Propiedad propiedad = propiedadServicio.getOne(id);
        modelo.addAttribute("propiedad", propiedad);
        List<Comentario> comentarios = propiedad.getComentarios();
        Map<Integer, byte[]> imagenesCliente = new HashMap<>();
        for (Comentario comentario : comentarios) {
            int idCliente = comentario.getCliente().getId();
            Usuario miUsuario = usuarioServicio.getOne(idCliente);
            Imagen imagen = miUsuario.getFotoPerfil();
            if (imagen != null) {
                imagenesCliente.put(idCliente, imagen.getContenido());
            }
        }
        modelo.addAttribute("comentarios", comentarios);
        modelo.addAttribute("imagenesCliente", imagenesCliente); // Pasar el Map a la vista

        return "vistaPropiedad.html";
    }

    @PostMapping("/registroPropiedad")
    public String registroPropiedad(@RequestParam("nombre") String nombre, @RequestParam("ubicacion") String ubicacion,
            @RequestParam("descripcion") String descripcion, @RequestParam("valor") double valor, @RequestParam("capacidad") int capacidad,
            @RequestParam("tipoDePropiedad") PropiedadEnum tipoDePropiedad, @RequestParam("imagen") List<MultipartFile> imagenes, @RequestParam("servicio") List<ServicioEnum> servicios, ModelMap modelo, Authentication authentication) {
        try {
            //   autenticacion que verifica cual es el usuario logueado y guarda el email
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String email = userDetails.getUsername();
            System.out.println("Email del usuario autenticado: " + email);
            propiedadServicio.registrarPropiedad(nombre, ubicacion, descripcion, valor, capacidad, tipoDePropiedad,
                    email, imagenes, servicios);
            modelo.put("exito", "La propiedad fue registrada correctamente!");
        } catch (Exception e) {
            System.out.println(e);
            modelo.put("nombre", nombre);
            modelo.put("ubicacion", ubicacion);
            modelo.put("descripcion", descripcion);
            modelo.put("valor", valor);
            modelo.put("capacidad", capacidad);
            modelo.put("tipoDePropiedad", tipoDePropiedad);
            modelo.put("imagen", imagenes);
            modelo.put("servicio", servicios);
            modelo.put("error", "Verifique que los datos hayan sido cargado correctamente.");
        }
        return "registroPropiedad.html";
    }

    @GetMapping("/actualizarPropiedad/{id}")
    public String modificarPropiedad(@PathVariable int id, ModelMap modelo) {
        modelo.put("propiedad", propiedadServicio.getOne(id));
        return "modificarPropiedad";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PROPIETARIO')")
    @PostMapping("/actualizarPropiedad/{idPropiedad}")
    public String actualizarPropiedad(@PathVariable("idPropiedad") int idPropiedad, @RequestParam("nombre") String nombre, @RequestParam("ubicacion") String ubicacion,
            @RequestParam("descripcion") String descripcion, @RequestParam("valor") double valor, @RequestParam("capacidad") int capacidad,
            @RequestParam("tipoDePropiedad") PropiedadEnum tipoDePropiedad, @RequestParam("imagen") List<MultipartFile> imagenes, @RequestParam("servicio") List<ServicioEnum> servicios, ModelMap modelo, Authentication authentication) {
        try {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String email = userDetails.getUsername();
            propiedadServicio.actualizarPropiedad(idPropiedad, nombre, ubicacion, descripcion, valor, capacidad, tipoDePropiedad,
                    imagenes, servicios);
            modelo.put("exito", "La propiedad fue actualizada correctamente!");
        } catch (Exception e) {
            System.out.println(e);
            modelo.put("nombre", nombre);
            modelo.put("descripcion", descripcion);
            modelo.put("valor", valor);
            modelo.put("capacidad", capacidad);
            modelo.put("imagen", imagenes);
            modelo.put("servicio", servicios);
            modelo.put("error", "Verifique que los datos hayan sido cargado correctamente.");
        }
        return "redirect:/dashboardCliente";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/listarPropiedades")
    public String listarPropiedades(ModelMap modelo, @Param("palabraClave") String palabraClave) {
        List<Propiedad> propiedad = propiedadServicio.listarPropiedades(palabraClave);
        modelo.addAttribute("propiedad", propiedad);
        modelo.addAttribute("palabraClave", palabraClave);
        return "listadoPropiedadesAdmin.html";
    }

    @GetMapping("/borrarPropiedad/{id}")
    public String borrarPropiedad(@PathVariable int id) {
        System.err.println("propiedad id" + id);
        try {
            propiedadServicio.borrar(id);
        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:/propiedad/listarPropiedades";
    }

    @GetMapping("/detallePropiedad")
    public String mostrarPropiedades(ModelMap modelo) {
        List<Propiedad> quinchoList = propiedadServicio.buscarPropiedadPorTipo(PropiedadEnum.QUINCHO);
        modelo.addAttribute("QUINCHO", quinchoList);
        List<Propiedad> quintaList = propiedadServicio.buscarPropiedadPorTipo(PropiedadEnum.CASA_QUINTA);
        modelo.addAttribute("CASA_QUINTA", quintaList);
        List<Propiedad> salonesList = propiedadServicio.buscarPropiedadPorTipo(PropiedadEnum.SALON_DE_FIESTA);
        modelo.addAttribute("SALON_DE_FIESTA", salonesList);
        List<Propiedad> patiioaList = propiedadServicio.buscarPropiedadPorTipo(PropiedadEnum.PATIO);
        modelo.addAttribute("PATIO", patiioaList);
        return "detallePropiedad";
    }

}
