package com.pcgestor.model;

import java.util.HashSet;
import java.util.Set;

public class Rol {
    private int idRol;
    private String nombreRol;
    private String descripcion;
    private Set<String> permisos;
    
    public Rol(String nombreRol, String descripcion) {
        this.nombreRol = nombreRol;
        this.descripcion = descripcion;
        this.permisos = new HashSet<>();
    }
    
    // Getters y Setters
    public int getIdRol() { return idRol; }
    public void setIdRol(int idRol) { this.idRol = idRol; }
    
    public String getNombreRol() { return nombreRol; }
    public void setNombreRol(String nombreRol) { this.nombreRol = nombreRol; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    public Set<String> getPermisos() { return permisos; }
    public void agregarPermiso(String permiso) {
        this.permisos.add(permiso);
    }
    
    public boolean tienePermiso(String permiso) {
        return permisos.contains(permiso);
    }
    
    @Override
    public String toString() {
        return String.format("Rol{id=%d, nombre='%s', descripcion='%s'}", 
                idRol, nombreRol, descripcion);
    }
}