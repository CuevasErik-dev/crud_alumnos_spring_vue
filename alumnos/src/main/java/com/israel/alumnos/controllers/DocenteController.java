package com.israel.alumnos.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import com.israel.alumnos.model.Docente;
import com.israel.alumnos.repository.DocenteRepository;
@RestController
@RequestMapping("/docente")
@CrossOrigin(origins = "*") //permite la entrada de cualquier direccion
public class DocenteController {

    @Autowired
    private DocenteRepository docenteRepository;

    // metodo get para traer todos los docentes de la base de Datos
    @GetMapping("/traer-docentes")
    public List<Docente> TraerDocentes() {
        return docenteRepository.findAll();
    }

    // Metodo para traer un docente por numero de cont
    @GetMapping("/traer-docente/{id}")
    public ResponseEntity<Docente> TraerUnDocente(@PathVariable Long id) {
        return docenteRepository.findById(id)
                .map(docente -> ResponseEntity.ok(docente))
                .orElse(ResponseEntity.notFound().build());
    }

    // Metodo para Insertar un docente a la base de datos
    @PostMapping("/insertar-docente")
    public Docente insertarDocente(@RequestBody Docente docente) {
        return docenteRepository.save(docente);
    }

    // metodo para editar un docente
    @PutMapping("/editar-docentes/{id}")
    public ResponseEntity<Docente> actualizarDocente(@PathVariable long id, @RequestBody Docente docente) {
        return docenteRepository.findById(id).map(docenteExistente -> {
            docenteExistente.setNombre(docente.getNombre());
            docenteExistente.setApellido(docente.getApellido());
            docenteExistente.setCorreo(docente.getCorreo());
            docenteExistente.setCarrera(docente.getCarrera());
            Docente actualizado = docenteRepository.save(docenteExistente);
            return ResponseEntity.ok(actualizado);
        }).orElse(ResponseEntity.notFound().build());
    }

    // metodo para eliminar un docente
    @DeleteMapping("/eliminar-docente/{id}")
    public void eliminarDocente(@PathVariable long id) {docenteRepository.deleteById(id);
    }
}