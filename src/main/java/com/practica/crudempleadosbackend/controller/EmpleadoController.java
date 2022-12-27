package com.practica.crudempleadosbackend.controller;

import com.practica.crudempleadosbackend.exception.EmpleadoNoEncontradoException;
import com.practica.crudempleadosbackend.model.Empleado;
import com.practica.crudempleadosbackend.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("http://localhost:4200")
@RestController
public class EmpleadoController {
    @Autowired
    private EmpleadoRepository empleadoRepository;

    @PostMapping("/empleado")
    ResponseEntity<HttpStatus> crear(@RequestBody Empleado nuevoEmpleado){
        String nombre = nuevoEmpleado.getNombre();
        String apellido = nuevoEmpleado.getApellido();
        String email = nuevoEmpleado.getEmail();
        String rfc = nuevoEmpleado.getRfc();
        try {
            empleadoRepository.crearEmpleado(nombre, apellido, email, rfc);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/empleados")
    public ResponseEntity<List> obtenerTodos(){
        try {
            return new ResponseEntity<>(empleadoRepository.consultarEmpleados(), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/empleado/{id}")
    public ResponseEntity<Empleado> obtenerPorId(@PathVariable Long id){
        try {
            Empleado empleado = obtenerEmpleado(id);

            if(empleado != null ) {
                return new ResponseEntity<>(empleado, HttpStatus.OK);
            }
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/empleado/{id}")
    ResponseEntity<Empleado> editar(@RequestBody Empleado nuevo, @PathVariable Long id){
        try {
            Empleado empleado = obtenerEmpleado(id);

            if(empleado != null) {
                return new ResponseEntity<>(empleadoRepository.editarEmpleado(id, nuevo.getNombre(), nuevo.getApellido(), nuevo.getEmail(), nuevo.getRfc()), HttpStatus.OK);
            }
        }catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return null;
    }
    @DeleteMapping("/empleado/{id}")
    public ResponseEntity<HttpStatus> eliminar(@PathVariable("id")long id){
        try {
            Empleado empleado = obtenerEmpleado(id);

            if(empleado != null ){
                empleadoRepository.eliminarEmpleado(id);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Empleado obtenerEmpleado(long id){
        Optional<Empleado> empleado = Optional
                .ofNullable
                        (empleadoRepository.consultarPorId(id));
        if (empleado.isPresent()){
            return empleado.get();
        }
        return null;
    }

}

