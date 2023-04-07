package com.QuinchApp.Controladores;

import com.QuinchApp.Entidades.Usuario;
import com.QuinchApp.Servicios.ClienteServicio;
import com.QuinchApp.Servicios.PropietarioServicio;
import com.QuinchApp.Servicios.UsuarioServicio;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/usuario")
public class UsuarioControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;
    @Autowired
    private ClienteServicio clienteServicio;
    @Autowired
    private PropietarioServicio propietarioServicio;

    @GetMapping("/registrar")
    public String registrar() {
        return "/registro.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam("nombre") String nombre, @RequestParam("nombreUsuario") String nombreUsuario,
            @RequestParam("email") String email, @RequestParam("password") String password, @RequestParam("password") String password2, @RequestParam("telefono") long telefono,
            @RequestParam("archivo") MultipartFile archivo, @RequestParam("tipoUsuario") String tipo, ModelMap modelo) throws Exception {
        System.out.print(tipo);
        try {
            usuarioServicio.registrar(nombre, nombreUsuario, email, password, password2, telefono, archivo, tipo);
            modelo.put("exito", "El usuario fue registrado correctamente!");
        } catch (Exception exception) {
            System.out.println(exception);
            modelo.put("nombre", nombre);
            modelo.put("nombreUsuario", nombreUsuario);
            modelo.put("telefono", telefono);
            modelo.put("email", email);
            modelo.put("password", password);
            modelo.put("password2", password2);
            modelo.put("archivo", archivo);
            modelo.put("error", "Verifique que los datos hayan sido cargado correctamente y el email no este registrado");
            return "registro";
        }
        return "registro";

    }

    @GetMapping("/borrar/{id}/{rol}")
    public String borrarUsuario(@PathVariable Integer id, @PathVariable String rol, ModelMap modelo) throws Exception {
        try {
            if (rol.equalsIgnoreCase("CLIENTE")) {
                clienteServicio.borrar(id);
            } else {
                propietarioServicio.borrar(id);
            }
            modelo.put("exito", "El usuario fue registrado correctamente!");
            return "redirect:/usuario/listar";
        } catch (Exception exception) {
            modelo.put("error", "Verifique que los datos hayan sido cargado correctamente y el email no este registrado");
            return "redirect:/usuario/listar";
        }
    }

    @GetMapping("/terminos")
    public String terminos() {
        return "terminos";
    }

    @PreAuthorize("hasAnyRole('ROLE_CLIENTE', 'ROLE_ADMIN', 'ROLE_PROPIETARIO')")
    @GetMapping("/perfil")
    public String perfil(ModelMap modelo, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
        modelo.put("usuario", usuario);
        return "perfil";
    }

    @PreAuthorize("hasAnyRole('ROLE_CLIENTE', 'ROLE_ADMIN', 'ROLE_PROPIETARIO')")
    @GetMapping("/perfilModificar")
    public String perfilModificar(ModelMap modelo, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
        modelo.put("usuario", usuario);
        return "modiificarUsuario";
    }

    @PreAuthorize("hasAnyRole('ROLE_CLIENTE', 'ROLE_ADMIN', 'ROLE_PROPIETARIO')")
    @PostMapping("/perfilModificar/{id}")
    public String actualizar(HttpSession session, @PathVariable int id, @RequestParam("nombre") String nombre, @RequestParam("nombreUsuario") String nombreUsuario,
            @RequestParam("email") String email, @RequestParam("password") String password, @RequestParam("password2") String password2, @RequestParam("telefono") long telefono,
            @RequestParam(value = "archivo", required = false) MultipartFile archivo, @RequestParam("tipoUsuario") String tipo, ModelMap modelo) {
        modelo.addAttribute("usuario", new Usuario());
        try {
            if (archivo != null && !archivo.isEmpty()) {
                usuarioServicio.actualizar(id, nombre, nombreUsuario, email, password, password2, telefono, archivo, tipo);
            } else {
                usuarioServicio.actualizar(id, nombre, nombreUsuario, email, password, password2, telefono, null, tipo);
            }
            Usuario usuario = (Usuario) session.getAttribute("usuariosession");
            modelo.put("usuario", usuario);
            modelo.put("password", password);
            modelo.put("password2", password2);
            modelo.addAttribute("tipoUsuario", usuario.getRol().name().toLowerCase());
            modelo.put("exito", "Usuario actualizado correctamente!, vuelva a iniciar sesion para ver los cambios");
            return "modiificarUsuario";
        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
            Usuario usuario = (Usuario) session.getAttribute("usuariosession");
            modelo.put("usuario", usuario);
            modelo.put("password", password);
            modelo.put("password2", password2);
            modelo.addAttribute("tipoUsuario", usuario.getRol().name().toLowerCase());

            return "modiificarUsuario";
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/listar")
    public String listarUsuario(ModelMap modelo, @Param("palabraClave") String palabraClave) {
        List<Usuario> usuarios = usuarioServicio.listarUsuarios(palabraClave);
        modelo.addAttribute("usuario", usuarios);
        modelo.addAttribute("palabraClave", palabraClave);
        return "listadoUsuario.html";
    }

    @RequestMapping("/altaBaja/{id}")
    public String altaBaja(@PathVariable Integer id) {
        usuarioServicio.bajaAlta(id);
        return "redirect:/usuario/listar";
    }

}
