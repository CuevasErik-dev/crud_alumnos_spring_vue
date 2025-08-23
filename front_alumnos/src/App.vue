<script setup>
import { ref, onMounted } from "vue";
import axios from "axios";
import Swal from "sweetalert2";

const alumnos = ref([]);
const nuevoAlumno = ref({
  nombre: "",
  apellido: "",
  carrera: "",
  telefono: "",
  imagenurl: "",
});

const limpiarFormulario = () => {
  nuevoAlumno.value = {
    nombre: "",
    apellido: "",
    carrera: "",
    telefono: "",
    imagenurl: "",
  };
};

const editado = ref(false);

const eliminarAlumno = async (id) => {
  await axios.delete(`http://localhost:8080/alumnos/eliminar-alumnos/${id}`);
  console.log("Alumno eliminado");
  await cargarAlumnos();
};

const cargarAlumnos = async () => {
  const response = await axios.get(
    "http://localhost:8080/alumnos/traer-alumnos"
  );
  alumnos.value = response.data;
  console.log(alumnos.value);
};

const agregarAlumno = async () => {
  if (editado.value){
    await axios.put(`http://localhost:8080/alumnos/editar-alumnos/${nuevoAlumno.value.id}`,nuevoAlumno.value);
    console.log("Alumno editado");
    editado.value=false;

  }else{
    await axios.post("http://localhost:8080/alumnos/insertar-alumno",nuevoAlumno.value);
  }
  
  await cargarAlumnos();
  limpiarFormulario();
};

const editarAlumnos = (alumno) => {
    Object.assign(nuevoAlumno.value, alumno);
    editado.value = true;
  };

onMounted(cargarAlumnos);

</script>

<template>

  <div class="container">
    <div class="row">
      <div class="col-md-12">
        <div class="card shadow p-4 mb-4 mt-4">
          <h2 class="text-center">Formulario de Alumnos</h2>
          <form @submit.prevent="agregarAlumno">
            <div class="row">
              <div class="col-md-6 mb-3">
                <label for="nombre" class="form-label">Nombre</label>
                <input type="text" id="nombre" v-model="nuevoAlumno.nombre" class="form-control" required />
              </div>

              <div class="col-md-6 mb-3">
                <label for="apellido" class="form-label">Apellidos</label>
                <input type="text" id="apellido" v-model="nuevoAlumno.apellido" class="form-control" required />
              </div>

              <div class="col-md-6 mb-3">
                <label for="carrera" class="form-label">Carrera</label>
                <input type="text" id="carrera" v-model="nuevoAlumno.carrera" class="form-control" required />
              </div>

              <div class="col-md-6 mb-3">
                <label for="telefono" class="form-label">Telefono</label>
                <input type="number" id="telefono" v-model="nuevoAlumno.telefono" class="form-control" required />
              </div>

              <div class="col-md-6 mb-3">
                <label for="imagenurl" class="form-label">Imagen URL</label>
                <input type="text" id="imagenurl" v-model="nuevoAlumno.imagenurl" class="form-control" required />
              </div>
            </div>

            <button type="submit" class="btn btn-primary mt-3">
              {{ editado ? "Actualizar Alumno" : "Agregar Alumno" }}
            </button>
          </form>
        </div>
      </div>

      <div class="col-md-12">
        <div class="card shadow">
          <div class="card-body">
            <h4 class="card-title mb-3">Tabla de Alumnos</h4>
            <table class="table table-hover align-middle">
              <thead class="table-light">
                <tr>
                  <th scope="col">ID</th>
                  <th scope="col">Nombre</th>
                  <th scope="col">Apellidos</th>
                  <th scope="col">Carrera</th>
                  <th scope="col">Telefono</th>
                  <th scope="col">Imagen</th>
                  <th scope="col">Acciones</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="alumno in alumnos" :key="alumno.id">
                  <td>{{ alumno.id }}</td>
                  <td>{{ alumno.nombre }}</td>
                  <td>{{ alumno.apellido }}</td>
                  <td>{{ alumno.carrera }}</td>
                  <td>{{ alumno.telefono }}</td>
                  <td>
                    <img :src="alumno.imagenurl" alt="Imagen del Alumno" width="50" />
                  </td>
                  <td>
                    <button @click="eliminarAlumno(alumno.id)" class="btn btn-danger mx-2">
                      <i class="bi bi-trash2-fill"></i>
                    </button>
                    <button @click="editarAlumnos(alumno)" class="btn btn-warning">
                      <i class="bi bi-pencil-fill"></i>
                    </button>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>


    </div>
  </div>
</template>

<style scoped></style>
