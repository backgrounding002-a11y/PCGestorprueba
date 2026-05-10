package com.pcgestor.service;

import com.pcgestor.dao.EquipoDAO;
import com.pcgestor.model.Equipo;
import com.pcgestor.model.Mantenimiento;

import java.util.List;
import java.util.Optional;

public class EquipoService {
    private EquipoDAO equipoDAO;
    
    public EquipoService(EquipoDAO equipoDAO) {
        this.equipoDAO = equipoDAO;
    }
    
    public void registrarEquipo(Equipo equipo) {
        validarEquipo(equipo);
        
        // Verificar si el serial ya existe
        Optional<Equipo> equipoExistente = equipoDAO.buscarPorSerial(equipo.getSerial());
        if (equipoExistente.isPresent()) {
            throw new IllegalArgumentException("Ya existe un equipo con el serial: " + equipo.getSerial());
        }
        
        equipoDAO.crear(equipo);
    }
    
    public Optional<Equipo> obtenerEquipo(int id) {
        return equipoDAO.leer(id);
    }
    
    public List<Equipo> obtenerTodosEquipos() {
        return equipoDAO.leerTodos();
    }
    
    public List<Equipo> obtenerEquiposDisponibles() {
        return equipoDAO.buscarPorEstado("Disponible");
    }
    
    public List<Equipo> obtenerEquiposEnMantenimiento() {
        return equipoDAO.buscarPorEstado("En Mantenimiento");
    }
    
    public void actualizarEquipo(Equipo equipo) {
        validarEquipo(equipo);
        equipoDAO.actualizar(equipo);
    }
    
    public void eliminarEquipo(int id) {
        equipoDAO.eliminar(id);
    }
    
    public Optional<Equipo> buscarEquipoPorSerial(String serial) {
        return equipoDAO.buscarPorSerial(serial);
    }
    
    public double calcularCostoTotalMantenimientos(Equipo equipo) {
        return equipo.getMantenimientos().stream()
                .mapToDouble(Mantenimiento::getCosto)
                .sum();
    }
    
    // Método para generar serial único
    public String generarSerialUnico(String prefijo) {
        return prefijo + System.currentTimeMillis();
    }
    
    private void validarEquipo(Equipo equipo) {
        if (equipo.getMarca() == null || equipo.getMarca().trim().isEmpty()) {
            throw new IllegalArgumentException("La marca del equipo es requerida");
        }
        if (equipo.getModelo() == null || equipo.getModelo().trim().isEmpty()) {
            throw new IllegalArgumentException("El modelo del equipo es requerido");
        }
        if (equipo.getSerial() == null || equipo.getSerial().trim().isEmpty()) {
            throw new IllegalArgumentException("El serial del equipo es requerido");
        }
        if (equipo.getFechaCompra() == null) {
            throw new IllegalArgumentException("La fecha de compra es requerida");
        }
        if (equipo.getTipoEquipo() == null) {
            throw new IllegalArgumentException("El tipo de equipo es requerido");
        }
        if (equipo.getEstadoEquipo() == null) {
            throw new IllegalArgumentException("El estado del equipo es requerido");
        }
        if (equipo.getUbicacion() == null) {
            throw new IllegalArgumentException("La ubicación del equipo es requerida");
        }
    }
}