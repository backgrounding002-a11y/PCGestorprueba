package com.pcgestor.model;

public class Ubicacion {
    private int idUbicacion;
    private String edificio;
    private String piso;
    private String sala;
    private String descripcion;
    
    public Ubicacion(String edificio, String piso, String sala, String descripcion) {
        this.edificio = edificio;
        this.piso = piso;
        this.sala = sala;
        this.descripcion = descripcion;
    }
    
    // Getters y Setters
    public int getIdUbicacion() { return idUbicacion; }
    public void setIdUbicacion(int idUbicacion) { this.idUbicacion = idUbicacion; }
    
    public String getEdificio() { return edificio; }
    public void setEdificio(String edificio) { this.edificio = edificio; }
    
    public String getPiso() { return piso; }
    public void setPiso(String piso) { this.piso = piso; }
    
    public String getSala() { return sala; }
    public void setSala(String sala) { this.sala = sala; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    public String getUbicacionCompleta() {
        return String.format("Edificio %s - Piso %s - Sala %s", edificio, piso, sala);
    }
    
    @Override
    public String toString() {
        return String.format("Ubicacion{id=%d, ubicacion='%s'}", idUbicacion, getUbicacionCompleta());
    }
}