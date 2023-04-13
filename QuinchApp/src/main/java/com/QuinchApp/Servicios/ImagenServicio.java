package com.QuinchApp.Servicios;

import com.QuinchApp.Entidades.Imagen;
import com.QuinchApp.Repositorios.ImagenRepositorio;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImagenServicio {

    @Autowired
    private ImagenRepositorio imagenRepositorio;

    @Transactional
    public Imagen guardar(MultipartFile archivo) throws Exception {
        validar(archivo);
        Imagen imagen = new Imagen();
        if (archivo != null) {
            try {
                imagen.setMime(archivo.getContentType());
                imagen.setNombre(archivo.getName());
                imagen.setContenido(archivo.getBytes());
                return imagenRepositorio.save(imagen);
            } catch (IOException e) {
                System.err.println(e.getMessage());
                return null;
            }
        }
        return imagen;
    }

    @Transactional
    public Imagen actualizar(MultipartFile archivo, Integer id) throws Exception {
        if (archivo != null) {
            try {
                Imagen imagen = new Imagen();
                if (id != null) {
                    Optional<Imagen> respuesta = imagenRepositorio.findById(id);
                    if (respuesta.isPresent()) {
                        imagen = respuesta.get();
                    }
                }
                imagen.setMime(archivo.getContentType());
                imagen.setNombre(archivo.getName());
                imagen.setContenido(archivo.getBytes());
                return imagenRepositorio.save(imagen);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
    }

    @Transactional
    public List<Imagen> listarImagen() {
        List<Imagen> miLista = new ArrayList<>();
        miLista = imagenRepositorio.findAll();
        return miLista;
    }

    private void validar(MultipartFile archivo) throws Exception {
        if (archivo == null) {
            throw new Exception("El archivo no puede estar vacio");
        }
    }

    public Imagen getOne(int id) {
        return imagenRepositorio.getOne(id);
    }
    
   
}
