package com.practica.crudempleadosbackend.repository;

import com.practica.crudempleadosbackend.model.Empleado;
import org.apache.coyote.Response;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface EmpleadoRepository extends JpaRepository <Empleado, Long> {
    //Consultar todos los empleados mediante el sp de empleados.
    @Query(nativeQuery = true, value = "call proc_consultarempleados")
    List<Empleado> consultarEmpleados();

    // Consultar un empleado mediante el id
    @Query(nativeQuery = true, value = "call proc_consultarporid(:emp_id)")
    Empleado consultarPorId(@Param("emp_id") Long emp_id);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "call proc_eliminarempleado(:emp_id)")
    void eliminarEmpleado(@Param("emp_id") Long emp_id);

    @Query(nativeQuery = true, value="{call proc_crearempleado(:emp_nombre, :emp_apellido, :emp_email, :emp_rfc)}")
    void crearEmpleado(@Param("emp_nombre") String emp_nombre,
                                 @Param("emp_apellido") String emp_apellido,
                                 @Param("emp_email") String emp_email,
                                 @Param("emp_rfc") String emp_rfc);

    @Query(nativeQuery = true, value = "call proc_editarempleado(:emp_id, :emp_nombre, :emp_apellido, :emp_email, :emp_rfc)")
    Empleado editarEmpleado(@Param("emp_id") Long emp_id,
                        @Param("emp_nombre") String emp_nombre,
                        @Param("emp_apellido") String emp_apellido,
                        @Param("emp_email") String emp_email,
                        @Param("emp_rfc") String emp_rfc);
}
