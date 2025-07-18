package com.israel.alumnos.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.israel.alumnos.model.Alumno;
import com.israel.alumnos.repository.AlumnoRepository;

@RestController
@RequestMapping("/alumnos")
public class AlumnoController { 

    @Autowired
    private AlumnoRepository alumnoRepository;

    //metodo get para traer todos los alumnos de la base de Datos 
    @GetMapping("/traer-alumnos")
    public List<Alumno> TraerAlumnos() {
        return alumnoRepository.findAll();
    }

    //Metodo para Insertar un alumno a la base de datos
    @PostMapping("/insertar-alumno")
    public Alumno insertarAlumno(@RequestBody Alumno alumno){
        return alumnoRepository.save(alumno);
    }

}
