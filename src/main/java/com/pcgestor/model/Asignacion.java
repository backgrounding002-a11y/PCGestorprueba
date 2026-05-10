package com.pcgestor.model;

import java.time.LocalDate;

public class Asignacion {
    private int idAsignacion;
    private LocalDate fechaAsignacion;
    private String motivo;
    private Equipo equipo;
    private Usuario usuario;
    
    public Asignacion(LocalDate fechaAsignacion, String motivo) {
        this.fechaAsignacion = fechaAsignacion;
        this.motivo = motivo;
    }
    
    // Getters y Setters
    public int getIdAsignacion() { return idAsignacion; }
    public void setIdAsignacion(int idAsignacion) { this.idAsignacion = idAsignacion; }
    
    public LocalDate getFechaAsignacion() { return fechaAsignacion; }
    public void setFechaAsignacion(LocalDate fechaAsignacion) { this.fechaAsignacion = fechaAsignacion; }
    
    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
    
    public Equipo getEquipo() { return equipo; }
    public void setEquipo(Equipo equipo) { this.equipo = equipo; }
    
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    
    public boolean esVigente() {
        return fechaAsignacion != null && fechaAsignacion.isAfter(LocalDate.now().minusMonths(6));
    }
    
    @Override
    public String toString() {
        return String.format("Asignacion{id=%d, fecha=%s, motivo='%s', usuario=%s, equipo=%s}", 
                idAsignacion, fechaAsignacion, motivo, 
                usuario != null ? usuario.getNombre() : "Sin usuario",
                equipo != null ? equipo.getSerial() : "Sin equipo");
    }
}