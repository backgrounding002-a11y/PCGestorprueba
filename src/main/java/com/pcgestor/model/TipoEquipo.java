package com.pcgestor.model;

public class TipoEquipo {
    private int idTipo;
    private String nombreTipo;
    private String descripcion;
    
    public TipoEquipo() {}
    
    public TipoEquipo(String nombreTipo, String descripcion) {
        this.nombreTipo = nombreTipo;
        this.descripcion = descripcion;
    }
    
    // Getters y Setters
    public int getIdTipo() { return idTipo; }
    public void setIdTipo(int idTipo) { this.idTipo = idTipo; }
    
    public String getNombreTipo() { return nombreTipo; }
    public void setNombreTipo(String nombreTipo) { this.nombreTipo = nombreTipo; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    @Override
    public String toString() {
        return String.format("TipoEquipo{id=%d, nombre='%s'}", idTipo, nombreTipo);
    }
}