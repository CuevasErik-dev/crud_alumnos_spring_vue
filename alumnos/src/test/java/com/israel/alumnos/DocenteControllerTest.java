package com.israel.alumnos;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;

import static org.hamcrest.Matchers.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.israel.alumnos.controllers.DocenteController;
import com.israel.alumnos.model.Alumno;
import com.israel.alumnos.model.Docente;
import com.israel.alumnos.repository.DocenteRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

@WebMvcTest(DocenteController.class)
class DocenteControllerTest{
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private DocenteRepository docenteRepository;

    @Test
    public void debeTraerTodosLosDocentes () throws Exception{
        Docente docente1 = new Docente();
        docente1.setId(1L);
        docente1.setNombre("Israel");
        docente1.setCarrera("Sistemas");

        Docente docente2 = new Docente();
        docente2.setId(2L);
        docente2.setNombre("Erik");
        docente2.setCarrera("Sistemdocente");
        when(docenteRepository.findAll()).thenReturn(java.util.Arrays.asList(docente1, docente2));

        mockMvc.perform(get("/docente/traer-docentes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nombre", is("Israel")));

    }

     @Test
    public void debeInsertarUnDocente() throws Exception {

        Docente docente = new Docente();
        docente.setNombre("Israel");

        when(docenteRepository.save(any(Docente.class)))
                .thenReturn(docente);

        mockMvc.perform(post("/docente/insertar-docente")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(docente)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Israel"));
    }

    @Test
    public void debeEditarUnDocente() throws Exception{
    Long idParaEditar = 1L;

    Docente docenteExistente = new Docente();
    docenteExistente.setId(idParaEditar);
    docenteExistente.setNombre("Israel");
    docenteExistente.setCarrera("Sistemdocente");
    Alumno docenteModificado = new Alumno();
    docenteModificado.setNombre("Israel Cuevas");
    docenteModificado.setCarrera("Ingeniería");


    when(docenteRepository.findById(idParaEditar)).thenReturn(Optional.of(docenteExistente));
    when(docenteRepository.save(any(Docente.class))).thenAnswer(invocation -> invocation.getArgument(0));

    mockMvc.perform(put("/docente/editar-docentes/{id}", idParaEditar)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(docenteModificado)))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.nombre", is("Israel Cuevas")))
           .andExpect(jsonPath("$.carrera", is("Ingeniería")));
    verify(docenteRepository, times(1)).save(any(Docente.class));
    }

    @Test
    public void debeEliminarUnDocente() throws Exception {
    Long idParaEliminar = 1L;

    mockMvc.perform(delete("/docente/eliminar-docente/{id}", idParaEliminar)
            .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk());

    verify(docenteRepository, times(1)).deleteById(idParaEliminar);
    }
}