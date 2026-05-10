package com.pcgestor.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Equipo {
    private int idEquipo;
    private String marca;
    private String modelo;
    private String serial;
    private LocalDate fechaCompra;
    private TipoEquipo tipoEquipo;
    private EstadoEquipo estadoEquipo;
    private Ubicacion ubicacion;
    private List<Mantenimiento> mantenimientos;
    
    // Constructor
    public Equipo(String marca, String modelo, String serial, LocalDate fechaCompra) {
        this.marca = marca;
        this.modelo = modelo;
        this.serial = serial;
        this.fechaCompra = fechaCompra;
        this.mantenimientos = new ArrayList<>();
    }
    
    // Getters y Setters
    public int getIdEquipo() { return idEquipo; }
    public void setIdEquipo(int idEquipo) { this.idEquipo = idEquipo; }
    
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    
    public String getSerial() { return serial; }
    public void setSerial(String serial) { this.serial = serial; }
    
    public LocalDate getFechaCompra() { return fechaCompra; }
    public void setFechaCompra(LocalDate fechaCompra) { this.fechaCompra = fechaCompra; }
    
    public TipoEquipo getTipoEquipo() { return tipoEquipo; }
    public void setTipoEquipo(TipoEquipo tipoEquipo) { this.tipoEquipo = tipoEquipo; }
    
    public EstadoEquipo getEstadoEquipo() { return estadoEquipo; }
    public void setEstadoEquipo(EstadoEquipo estadoEquipo) { this.estadoEquipo = estadoEquipo; }
    
    public Ubicacion getUbicacion() { return ubicacion; }
    public void setUbicacion(Ubicacion ubicacion) { this.ubicacion = ubicacion; }
    
    public List<Mantenimiento> getMantenimientos() { return mantenimientos; }
    public void agregarMantenimiento(Mantenimiento mantenimiento) {
        this.mantenimientos.add(mantenimiento);
    }
    
    // Métodos de negocio
    public boolean estaDisponible() {
        return estadoEquipo != null && "Disponible".equals(estadoEquipo.getNombreEstado());
    }
    
    public boolean necesitaMantenimiento() {
        return mantenimientos.stream()
                .noneMatch(m -> m.getTipo().equals("Preventivo") && 
                               m.getFecha().isAfter(LocalDate.now().minusMonths(6)));
    }
    
    @Override
    public String toString() {
        return String.format("Equipo{id=%d, marca='%s', modelo='%s', serial='%s', estado=%s}", 
                idEquipo, marca, modelo, serial, 
                estadoEquipo != null ? estadoEquipo.getNombreEstado() : "No asignado");
    }
}