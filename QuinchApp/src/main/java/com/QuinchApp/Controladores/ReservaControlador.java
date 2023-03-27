package com.QuinchApp.Controladores;

import com.QuinchApp.Entidades.Cliente;
import com.QuinchApp.Entidades.Propiedad;
import com.QuinchApp.Entidades.Reserva;
import com.QuinchApp.Servicios.ReservaServicio;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
    
    @GetMapping("/registrar")
    public String registrar() {
        return "Formulario_Reservas.html";
    }

    @PostMapping("/registro")
    public String regristro(@RequestParam("fechaInicio") Date fechaInicio, @RequestParam("fechaSalida") Date fechaSalida, @RequestParam("propiedad") Propiedad propiedad,
            @RequestParam("cliente") Cliente cliente, ModelMap modelo) throws Exception {
        try {
            reservaServicio.registrar(fechaInicio, fechaSalida, propiedad, cliente);
            modelo.put("exito", "La reserva fue registrada correctamente!");
        } catch (Exception ex) {
            System.out.println(ex);
            modelo.put("error", ex.getMessage());
            modelo.put("fechaInicio", fechaInicio);
            modelo.put("fechaSalida", fechaSalida);
            modelo.put("propiedad", propiedad);
            modelo.put("cliente", cliente);
            modelo.put("error", "Verifique que los datos hayan sido cargado correctamente");
            return "formRegistrar";
        }
        return "formRegistrar";
    }

    @GetMapping("/modificarUno/{id}")
    public String modificarUno(@PathVariable Integer id, ModelMap modelo) {
        modelo.put("reservaLista", reservaServicio.getOne(id));
        return "formModificarReserva";
    }

    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable Integer id, @RequestParam("FechaSalida") Date FechaSalida, @RequestParam("propiedad") Propiedad propiedad,
            @RequestParam("cliente") Cliente cliente, Boolean confirmada, ModelMap modelo) throws Exception {
        try {
            reservaServicio.actualizar(Integer.SIZE, FechaSalida, propiedad, cliente, confirmada);
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
