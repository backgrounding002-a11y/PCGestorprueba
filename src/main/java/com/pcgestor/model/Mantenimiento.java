package com.pcgestor.model;

import java.time.LocalDate;

public class Mantenimiento {
    private int idMantenimiento;
    private LocalDate fecha;
    private String tipo;
    private String descripcion;
    private double costo;
    private Equipo equipo;
    private Usuario tecnico;
    
    public Mantenimiento(LocalDate fecha, String tipo, String descripcion, double costo) {
        this.fecha = fecha;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.costo = costo;
    }
    
    // Getters y Setters
    public int getIdMantenimiento() { return idMantenimiento; }
    public void setIdMantenimiento(int idMantenimiento) { this.idMantenimiento = idMantenimiento; }
    
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    public double getCosto() { return costo; }
    public void setCosto(double costo) { this.costo = costo; }
    
    public Equipo getEquipo() { return equipo; }
    public void setEquipo(Equipo equipo) { this.equipo = equipo; }
    
    public Usuario getTecnico() { return tecnico; }
    public void setTecnico(Usuario tecnico) { this.tecnico = tecnico; }
    
    // Métodos de negocio
    public boolean esPreventivo() {
        return "Preventivo".equalsIgnoreCase(tipo);
    }
    
    public boolean esCorrectivo() {
        return "Correctivo".equalsIgnoreCase(tipo);
    }
    
    public double calcularCostoConIva(double ivaPorcentaje) {
        return costo * (1 + ivaPorcentaje / 100);
    }
    
    @Override
    public String toString() {
        return String.format("Mantenimiento{id=%d, tipo='%s', fecha=%s, costo=%.2f, equipo=%s}", 
                idMantenimiento, tipo, fecha, costo, 
                equipo != null ? equipo.getSerial() : "Sin equipo");
    }
}