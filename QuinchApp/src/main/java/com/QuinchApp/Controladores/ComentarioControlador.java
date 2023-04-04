///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package com.QuinchApp.Controladores;
//
//import com.QuinchApp.Entidades.Cliente;
//import com.QuinchApp.Entidades.Comentario;
//import com.QuinchApp.Servicios.ComentarioServicio;
//import java.util.List;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
///**
// *
// * @author Usuario
// */
//@Controller
//@RequestMapping("/comentario")
//public class ComentarioControlador {
//    
//    @Autowired
//    private ComentarioServicio comentarioServicio;
//    
//    @PostMapping("/comentar")
//    public String comentar(@RequestParam("id") Integer id, @RequestParam("cliente") Cliente cliente,
//            @RequestParam("comentario") String coment, @RequestParam("calificacion") Integer calificacion) {
//        
//        Comentario comentario = new Comentario(id, cliente, coment, calificacion);
//        
//        return null;
//    }
//    
//    @GetMapping("/listar")
//    public String listarComentarios() {
//        
//        List<Comentario> comentarios = comentarioServicio.listarComentarios();
//        return null;
//    }
//    
//    @DeleteMapping("/borrar/{id}")
//    public String borrarComentario(@PathVariable Integer id) {
//        comentarioServicio.borrarComentario(id);
//        return null;
//    }
//    
//}

