package com.practica.crudempleadosbackend.exception;

public class EmpleadoNoEncontradoException extends  RuntimeException{
    public EmpleadoNoEncontradoException(Long id){
        super("No se pudo encontrar el usuario con el id: " + id);
    }
}

