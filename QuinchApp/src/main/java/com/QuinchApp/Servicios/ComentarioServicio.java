/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.QuinchApp.Servicios;

import com.QuinchApp.Entidades.Cliente;
import com.QuinchApp.Entidades.Comentario;
import com.QuinchApp.Repositorios.ComentarioRepositorio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Usuario
 */
@Service
public class ComentarioServicio {

    @Autowired
    private ComentarioRepositorio comentarioRepositorio;

    public void crearComentario(Comentario comentario) {

        comentarioRepositorio.save(comentario);

    }
    
    public void borrarComentario (int id){
    
    comentarioRepositorio.deleteById(id);
    }
    
    public List <Comentario> listarComentarios(){
    
        return comentarioRepositorio.findAll();
    }

}
