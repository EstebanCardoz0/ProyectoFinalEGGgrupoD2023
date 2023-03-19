package com.QuinchApp.Servicios;

import com.QuinchApp.Entidades.Imagen;
import com.QuinchApp.Entidades.Propiedad;
import com.QuinchApp.Entidades.Propietario;
import com.QuinchApp.Enums.PropiedadEnum;
import com.QuinchApp.Enums.ServicioEnum;
import com.QuinchApp.Repositorios.PropiedadRepositorio;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PropiedadServicio {

    @Autowired
    private PropiedadRepositorio propiedadRepositorio;
    @Autowired
    private ImagenServicio imagenServicio;

    @Transactional
    public void registrarPropiedad(String nombre, String ubicacion, String descripcion, double valor, int capacidad,
            PropiedadEnum tipoDePropiedad, Propietario propietario, MultipartFile imagen, ServicioEnum servicio) throws Exception {
        validar(nombre, ubicacion, descripcion, valor, capacidad, tipoDePropiedad, propietario);
        Propiedad propiedad = new Propiedad();
        propiedad.setNombre(nombre);
        propiedad.setUbicacion(ubicacion);
        propiedad.setDescripcion(descripcion);
        propiedad.setValor(valor);
        propiedad.setCapacidad(capacidad);
        propiedad.setTipoDePropiedad(tipoDePropiedad);
        propiedad.setPropietario(propietario);
        List<Imagen> imagenes = propiedad.getImagenes();
        if (imagenes == null) {
            imagenes = new ArrayList();
            propiedad.setImagenes(imagenes);
        }
        imagenes.add((Imagen) imagen);
        propiedad.setImagenes(imagenes);
        List<ServicioEnum> servicios = propiedad.getServicios();
        if (servicios == null) {
            servicios = new ArrayList();
            propiedad.setServicios(servicios);
        }
        servicios.add(servicio);
        propiedad.setServicios(servicios);
        propiedadRepositorio.save(propiedad);
    }

    @Transactional
    public void actualizarPropiedad(int id, String nombre, String descripcion, double valor, int capacidad, MultipartFile imagen, ServicioEnum servicio) throws Exception {
        if (id < 0) {
            throw new Exception("Ingrese un id");
        }
        Optional<Propiedad> respuesta = propiedadRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Propiedad propiedad = respuesta.get();
            propiedad.setNombre(nombre);
            propiedad.setDescripcion(descripcion);
            propiedad.setValor(valor);
            propiedad.setCapacidad(capacidad);
            List<Imagen> imagenes = propiedad.getImagenes();
            if (imagenes == null) {
                imagenes = new ArrayList();
                propiedad.setImagenes(imagenes);
            }
            imagenes.add((Imagen) imagen);
            propiedad.setImagenes(imagenes);
            List<ServicioEnum> servicios = propiedad.getServicios();
            if (servicios == null) {
                servicios = new ArrayList();
                propiedad.setServicios(servicios);
            }
            servicios.add(servicio);
            propiedad.setServicios(servicios);
            propiedadRepositorio.save(propiedad);
        }
    }

    public List<Propiedad> listarPropiedades() {
        List<Propiedad> propiedades = propiedadRepositorio.findAll();
        return propiedades;
    }

    @Transactional
    public void borrar(int id) {
        propiedadRepositorio.deleteById(id);
    }

    public void verUbicacion(int id) throws Exception {
        if (id < 0) {
            throw new Exception("Ingrese un id");
        }
        Optional<Propiedad> respuesta = propiedadRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Propiedad propiedad = respuesta.get();
            String location = propiedad.getUbicacion();
            String mapLink = "https://www.google.com/maps?q=" + location.replace(" ", "+");
            try {
                Desktop.getDesktop().browse(new URI(mapLink));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }
    
    public Propiedad getOne(int id) {
        return propiedadRepositorio.getOne(id);
    }

    private void validar(String nombre, String ubicacion, String descripcion, double valor, int capacidad,
            PropiedadEnum tipoDePropiedad, Propietario propietario) throws Exception {
        if (nombre.isEmpty() || nombre == null) {
            throw new Exception("El nombre no puede estar estar vacío");
        }
        if (ubicacion.isEmpty() || ubicacion == null) {
            throw new Exception("La ubicacion no puede estar estar vacía");
        }
        if (descripcion.isEmpty() || descripcion == null) {
            throw new Exception("La descripcion no puede estar estar vacía");
        }
        if (valor < 0) {
            throw new Exception("El valor no puede estar estar vacía");
        }
        if (capacidad < 0) {
            throw new Exception("La capacidad no puede estar estar vacía");
        }
        if (tipoDePropiedad == null) {
            throw new Exception("Indica que tipo de propiedad es");
        }
        if (propietario == null) {
            throw new Exception("Indica el propietario de la propiedad");
        }
    }

}
