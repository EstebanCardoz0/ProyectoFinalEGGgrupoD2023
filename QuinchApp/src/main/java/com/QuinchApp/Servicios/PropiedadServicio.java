package com.QuinchApp.Servicios;

import com.QuinchApp.Entidades.Imagen;
import com.QuinchApp.Entidades.Propiedad;
import com.QuinchApp.Entidades.Propietario;
import com.QuinchApp.Enums.PropiedadEnum;
import com.QuinchApp.Enums.ServicioEnum;
import com.QuinchApp.Repositorios.PropiedadRepositorio;
import com.QuinchApp.Repositorios.PropietarioRepositorio;
import com.QuinchApp.Repositorios.UsuarioRepositorio;
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
    private PropietarioRepositorio propietarioRepositorio;
    @Autowired
    private ImagenServicio imagenServicio;

    @Transactional
    public void registrarPropiedad(String nombre, String ubicacion, String descripcion, double valor, int capacidad,
            PropiedadEnum tipoDePropiedad, String email, List<MultipartFile> imagenes, List<ServicioEnum> servicios) throws Exception {
        validar(nombre, ubicacion, descripcion, valor, capacidad, tipoDePropiedad, email);
        Propietario usuarioPropietario = propietarioRepositorio.buscarPorEmail(email);

        if (!usuarioPropietario.isActivo()) {
            throw new Exception("El usuario " + email + " no existe");
        }
        Propiedad propiedad = new Propiedad();
        propiedad.setNombre(nombre);
        propiedad.setUbicacion(ubicacion);
        propiedad.setDescripcion(descripcion);
        propiedad.setValor(valor);
        propiedad.setCapacidad(capacidad);
        propiedad.setDisponibilidad(Boolean.TRUE);
        propiedad.setTipoDePropiedad(tipoDePropiedad);
        propiedad.setPropietario(usuarioPropietario);
        List<ServicioEnum> serviciosPropiedad = propiedad.getServicios();
        if (serviciosPropiedad == null) {
            serviciosPropiedad = new ArrayList();
            propiedad.setServicios(serviciosPropiedad);
        }
        serviciosPropiedad.addAll(servicios);
        List<Imagen> listaImagenes = new ArrayList<>();
        for (MultipartFile imagen : imagenes) {
            Imagen miImagen = imagenServicio.guardar(imagen);
            listaImagenes.add(miImagen);
        }
        propiedad.setImagenes(listaImagenes);
        List<Propiedad> miPropiedad = new ArrayList();
        if (usuarioPropietario != null) {
            for (int i = 0; i < usuarioPropietario.getPropiedades().size(); i++) {
                miPropiedad.add(usuarioPropietario.getPropiedades().get(i));
            }
            miPropiedad.add(propiedad);
            usuarioPropietario.setPropiedades(miPropiedad);
            propiedadRepositorio.save(propiedad);
        } else {
            throw new Exception("no existe el propietario");
        }
        propiedad.setServicios(servicios);
    }

    @Transactional
    public void actualizarPropiedad(int idPropiedad, String nombre, String ubicacion, String descripcion, double valor, int capacidad,
            PropiedadEnum tipoDePropiedad, List<MultipartFile> imagenes, List<ServicioEnum> servicios) throws Exception {
        if (idPropiedad < 0) {
            throw new Exception("Ingrese un id");
        }
        Optional<Propiedad> respuesta = propiedadRepositorio.findById(idPropiedad);
        if (respuesta.isPresent()) {
            Propiedad propiedad = respuesta.get();
            propiedad.setNombre(nombre);
            propiedad.setUbicacion(ubicacion);
            propiedad.setDescripcion(descripcion);
            propiedad.setValor(valor);
            propiedad.setCapacidad(capacidad);
            propiedad.setDisponibilidad(Boolean.TRUE);
            propiedad.setTipoDePropiedad(tipoDePropiedad);
            List<ServicioEnum> serviciosPropiedad = propiedad.getServicios();
            if (serviciosPropiedad == null) {
                serviciosPropiedad = new ArrayList();
                propiedad.setServicios(serviciosPropiedad);
            }
            serviciosPropiedad.addAll(servicios);
            propiedad.setServicios(servicios);

            // Actualizar la lista de imágenes
            List<Imagen> imagenesPropiedad = propiedad.getImagenes();
            if (imagenesPropiedad == null) {
                imagenesPropiedad = new ArrayList<>();
                propiedad.setImagenes(imagenesPropiedad);
            } else {
                // Eliminar las imágenes que ya no se necesitan
                List<Imagen> imagenesAEliminar = new ArrayList<>();
                for (Imagen imagen : imagenesPropiedad) {
                    if (!imagenes.contains(imagen.getContenido())) {
                        imagenesAEliminar.add(imagen);
                    }
                }
                imagenesPropiedad.removeAll(imagenesAEliminar);
            }

            // Agregar las nuevas imágenes
            for (MultipartFile archivo : imagenes) {
                Imagen imagen = imagenServicio.actualizar(archivo, 0);
                imagenesPropiedad.add(imagen);
            }

            propiedadRepositorio.save(propiedad);
        }
    }

    public List<Propiedad> listarPropiedades(String palabraClave) {
        if (palabraClave != null) {
            List<Propiedad> propiedades = propiedadRepositorio.findAll();
            return propiedades;
        } else {
            List<Propiedad> propiedades = propiedadRepositorio.findAll();
            return propiedades;
        }

    }

    public List<Propiedad> buscarPropiedadPorTipo(PropiedadEnum tipo) {
        List<Propiedad> propiedades = propiedadRepositorio.buscarPropiedadPorTipo(tipo);
        System.out.println(propiedades);
        return propiedades;
    }

    @Transactional
    public void borrar(int id) {
        propiedadRepositorio.deleteById(id);
    }

    public Propiedad bajaAlta(int id) {
        Optional<Propiedad> optionalPropiedad = propiedadRepositorio.findById(id);
        Propiedad propiedad = new Propiedad();
        if (optionalPropiedad.isPresent()) {
            propiedad = optionalPropiedad.get();
            if (propiedad.isDisponibilidad() == false) {
                propiedad.setDisponibilidad(Boolean.TRUE);
                propiedadRepositorio.save(propiedad);
            } else {
                propiedad.setDisponibilidad(Boolean.FALSE);
                propiedadRepositorio.save(propiedad);
            }
        }
        return propiedad;
    }

    public Propiedad getOne(int id) {
        return propiedadRepositorio.getOne(id);
    }

    private void validar(String nombre, String ubicacion, String descripcion, double valor, int capacidad,
            PropiedadEnum tipoDePropiedad, String propietario) throws Exception {
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
