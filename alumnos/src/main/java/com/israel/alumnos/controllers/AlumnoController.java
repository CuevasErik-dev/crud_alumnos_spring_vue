package com.israel.alumnos.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    // metodo get para traer todos los alumnos de la base de Datos
    @GetMapping("/traer-alumnos")
    public List<Alumno> TraerAlumnos() {
        return alumnoRepository.findAll();
    }

    // Metodo para Insertar un alumno a la base de datos
    @PostMapping("/insertar-alumno")
    public Alumno insertarAlumno(@RequestBody Alumno alumno) {
        return alumnoRepository.save(alumno);
    }

    // metodo para editar un alumno
    @PutMapping("/editar-alumnos/{id}")
    public ResponseEntity<Alumno> actualizarAlumno(@PathVariable long id, @RequestBody Alumno alumno) {
        return alumnoRepository.findById(id).map(alumnoExistente -> {
            alumnoExistente.setNombre(alumno.getNombre());
            alumnoExistente.setApellido(alumno.getApellido());
            alumnoExistente.setGmail(alumno.getGmail());
            alumnoExistente.setNumeroControl(alumno.getNumeroControl());
            alumnoExistente.setTelefono(alumno.getTelefono());
            alumnoExistente.setCarrera(alumno.getCarrera());
            alumnoExistente.setImagenurl(alumno.getImagenurl());
            Alumno actualizado = alumnoRepository.save(alumnoExistente);
            return ResponseEntity.ok(actualizado);
        }).orElse(ResponseEntity.notFound().build());
    }

    //metodo para eliminar un alumno 
    @DeleteMapping("/eliminar-alumnos/{id}")
    public void eliminarAlumno(@PathVariable long id){
        alumnoRepository.deleteById(id);
    }
}
