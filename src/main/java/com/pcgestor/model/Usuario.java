package com.pcgestor.model;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private int idUsuario;
    private String nombre;
    private String correo;
    private String contrasena;
    private String telefono;
    private Rol rol;
    private List<Asignacion> asignaciones;
    
    public Usuario(String nombre, String correo, String contrasena, String telefono) {
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
        this.telefono = telefono;
        this.asignaciones = new ArrayList<>();
    }
    
    // Getters y Setters
    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    
    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
    
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    
    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }
    
    public List<Asignacion> getAsignaciones() { return asignaciones; }
    public void agregarAsignacion(Asignacion asignacion) {
        this.asignaciones.add(asignacion);
    }
    
    // Métodos de negocio
    public boolean autenticar(String contrasena) {
        return this.contrasena.equals(contrasena);
    }
    
    public boolean tienePermiso(String permiso) {
        return rol != null && rol.tienePermiso(permiso);
    }
    
    public boolean esAdministrador() {
        return rol != null && "Administrador".equals(rol.getNombreRol());
    }
    
    @Override
    public String toString() {
        return String.format("Usuario{id=%d, nombre='%s', correo='%s', rol=%s}", 
                idUsuario, nombre, correo, rol != null ? rol.getNombreRol() : "Sin rol");
    }
}