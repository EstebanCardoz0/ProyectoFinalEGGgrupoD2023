package com.QuinchApp.Controladores;

import com.QuinchApp.Entidades.Cliente;
import com.QuinchApp.Entidades.Propiedad;
import com.QuinchApp.Entidades.Reserva;
import com.QuinchApp.Entidades.Usuario;
import com.QuinchApp.Repositorios.UsuarioRepositorio;
import com.QuinchApp.Servicios.PropiedadServicio;
import com.QuinchApp.Servicios.ReservaServicio;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/reserva")
public class ReservaControlador {

    @Autowired
    private ReservaServicio reservaServicio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private PropiedadServicio propiedadServicio;

    @GetMapping("/registrar/{id}")
    public String registrar(@PathVariable int id, ModelMap modelo, HttpSession session) {
        Usuario cliente = (Usuario) session.getAttribute("usuariosession");
        modelo.put("propiedad", propiedadServicio.getOne(id));
        modelo.put("cliente", cliente);
        return "Formulario_Reservas.html";
    }

    @PostMapping("/registro")
    public String regristro(@RequestParam("fechaDelEvento") String fechaDelEvento, @RequestParam("propiedad") int propiedadId, ModelMap modelo, Authentication authentication, HttpSession session) throws Exception {
        try {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String idCliente = userDetails.getUsername();
            System.out.println("propiedadId: " + propiedadId);
            Propiedad propiedad = propiedadServicio.getOne(propiedadId);
            if (propiedad == null) {
                modelo.put("error", "No se pudo obtener la propiedad correctamente.");
                return "Formulario_Reservas";
            }
            reservaServicio.registrar(fechaDelEvento, propiedad.getIdPropiedad(), idCliente);
            modelo.put("exito", "La reserva fue registrada correctamente!");
            return "dashboardCliente";
        } catch (Exception ex) {
            System.out.println(ex);
            Usuario cliente = (Usuario) session.getAttribute("usuariosession");
            modelo.put("propiedad", propiedadServicio.getOne(propiedadId));
            modelo.put("cliente", cliente);
            modelo.put("error", ex.getMessage());
            modelo.put("fechaDelEvento", fechaDelEvento);
            modelo.put("idPropiedad", propiedadId);
            modelo.put("error", "La fecha no esta disponible, por favor elija otro dia.");
            return "Formulario_Reservas";
        }
    }

    @GetMapping("/modificarUno/{id}")
    public String modificarUno(@PathVariable Integer id, ModelMap modelo) {
        modelo.put("reservaLista", reservaServicio.getOne(id));
        return "formModificarReserva";
    }

    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable Integer id, @RequestParam("fechaDelEvento") Date fechaDelEvento, @RequestParam("propiedad") Propiedad propiedad,
            @RequestParam("cliente") Cliente cliente, Boolean confirmada, ModelMap modelo) throws Exception {
        try {
            reservaServicio.actualizar(Integer.SIZE, fechaDelEvento, propiedad, cliente, confirmada);
            return "redirect:../listar";
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
            return "reservaModificar";
        }
    }

    @GetMapping("/listar")
    public String listar(ModelMap modelo) {
        List<Reserva> reserva = reservaServicio.listarResevas();
        modelo.addAttribute("reserva", reserva);
        return "usuarioList";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id) throws Exception {
        reservaServicio.borrar(id);
        return "redirect:../listar";
    }

    @RequestMapping("/altaBaja/{id}")
    public String altaBaja(@PathVariable(name = "id") Integer id) {
        reservaServicio.bajaAlta(id);
        return "redirect:/reservas";
    }
}
