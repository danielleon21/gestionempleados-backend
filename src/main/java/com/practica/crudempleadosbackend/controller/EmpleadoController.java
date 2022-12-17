package com.practica.crudempleadosbackend.controller;

import com.practica.crudempleadosbackend.exception.EmpleadoNoEncontradoException;
import com.practica.crudempleadosbackend.model.Empleado;
import com.practica.crudempleadosbackend.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:3000")
@RestController
public class EmpleadoController {
    @Autowired
    private EmpleadoRepository empleadoRepository;

    @PostMapping("/empleado")
    Empleado newEmpleado(@RequestBody Empleado newEmpleado){
        return empleadoRepository.save(newEmpleado);
    }
    @GetMapping("/empleados")
    List<Empleado> obtenerTodos(){
        return empleadoRepository.findAll();
    }

    @GetMapping("/empleado/{id}")
    Empleado obtenerPorId(@PathVariable Long id){
        return empleadoRepository.findById(id)
                .orElseThrow(() -> new EmpleadoNoEncontradoException(id));
    }

    @PutMapping("/empleado/{id}")
    Empleado editar(@RequestBody Empleado nuevo, @PathVariable Long id){
        return empleadoRepository.findById(id)
                .map(empleado -> {
                    empleado.setNombre(nuevo.getNombre());
                    empleado.setApellido(nuevo.getApellido());
                    empleado.setEmail(nuevo.getEmail());
                    empleado.setRfc(nuevo.getRfc());
                    return empleadoRepository.save(empleado);
                }).orElseThrow(() -> new EmpleadoNoEncontradoException(id));
    }
    @DeleteMapping("/empleado/{id}")
    String eliminar(@PathVariable Long id){
        if (!empleadoRepository.existsById(id)){
            throw new EmpleadoNoEncontradoException(id);
        }
        empleadoRepository.deleteById(id);
        return "Empleado con el id " + id + " fue eliminado correctamente. ";
    }
}
