package com.pcgestor.model;

public class EstadoEquipo {
    private int idEstado;
    private String nombreEstado;
    
    public EstadoEquipo(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }
    
    // Getters y Setters
    public int getIdEstado() { return idEstado; }
    public void setIdEstado(int idEstado) { this.idEstado = idEstado; }
    
    public String getNombreEstado() { return nombreEstado; }
    public void setNombreEstado(String nombreEstado) { this.nombreEstado = nombreEstado; }
    
    @Override
    public String toString() {
        return String.format("EstadoEquipo{id=%d, nombre='%s'}", idEstado, nombreEstado);
    }
}