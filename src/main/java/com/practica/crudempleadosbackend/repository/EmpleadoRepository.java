package com.practica.crudempleadosbackend.repository;

import com.practica.crudempleadosbackend.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpleadoRepository extends JpaRepository <Empleado, Long> {

}
