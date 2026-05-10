package com.pcgestor;

import com.pcgestor.dao.MySQLEquipoDAO;
import com.pcgestor.model.*;
import com.pcgestor.service.EquipoService;
import com.pcgestor.util.DatabaseConnection;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("=== ️ SISTEMA PCGESTOR INICIADO ===");
            
            // Configurar servicios
            EquipoService equipoService = new EquipoService(new MySQLEquipoDAO());
            
            // DEMOSTRACIÓN DEL SISTEMA FUNCIONANDO
            
            System.out.println("\n--- 1.  EQUIPOS EXISTENTES ---");
            List<Equipo> equipos = equipoService.obtenerTodosEquipos();
            System.out.println("Total de equipos en sistema: " + equipos.size());
            equipos.forEach(System.out::println);
            
            System.out.println("\n--- 2.  EQUIPOS DISPONIBLES ---");
            List<Equipo> disponibles = equipoService.obtenerEquiposDisponibles();
            System.out.println("Equipos disponibles: " + disponibles.size());
            disponibles.forEach(e -> System.out.println("  - " + e.getMarca() + " " + e.getModelo()));
            
            System.out.println("\n--- 3.  BUSCAR EQUIPO POR SERIAL ---");
            Optional<Equipo> equipoEncontrado = equipoService.buscarEquipoPorSerial("DL5420X123456");
            if (equipoEncontrado.isPresent()) {
                System.out.println(" Equipo encontrado: " + equipoEncontrado.get());
                System.out.println("   Ubicación: " + equipoEncontrado.get().getUbicacion().getUbicacionCompleta());
                System.out.println("   Estado: " + equipoEncontrado.get().getEstadoEquipo().getNombreEstado());
            } else {
                System.out.println(" Equipo no encontrado");
            }
            
            System.out.println("\n--- 4.  REGISTRAR NUEVO EQUIPO (CON SERIAL ÚNICO) ---");
            
            // Generar serial único basado en timestamp
            String serialUnico = "LNV" + System.currentTimeMillis();
            
            Equipo nuevoEquipo = new Equipo("Lenovo", "ThinkPad X1", serialUnico, LocalDate.now());
            
            TipoEquipo tipoNuevo = new TipoEquipo("Portátil", "Computador portátil");
            tipoNuevo.setIdTipo(1);
            nuevoEquipo.setTipoEquipo(tipoNuevo);
            
            EstadoEquipo estadoNuevo = new EstadoEquipo("Disponible");
            estadoNuevo.setIdEstado(1);
            nuevoEquipo.setEstadoEquipo(estadoNuevo);
            
            Ubicacion ubicacionNueva = new Ubicacion("B", "2", "205", "Laboratorio");
            ubicacionNueva.setIdUbicacion(2);
            nuevoEquipo.setUbicacion(ubicacionNueva);
            
            // Verificar si el serial ya existe antes de registrar
            Optional<Equipo> equipoExistente = equipoService.buscarEquipoPorSerial(serialUnico);
            if (equipoExistente.isEmpty()) {
                equipoService.registrarEquipo(nuevoEquipo);
                System.out.println(" Nuevo equipo registrado: " + nuevoEquipo);
            } else {
                System.out.println("️ El equipo con serial " + serialUnico + " ya existe");
            }
            
            System.out.println("\n--- 5.  VERIFICAR NUEVO TOTAL ---");
            equipos = equipoService.obtenerTodosEquipos();
            System.out.println("Total de equipos en sistema: " + equipos.size());
            
        } catch (Exception e) {
            System.err.println(" Error en la aplicación: " + e.getMessage());
            e.printStackTrace();
        } finally {
            System.out.println("\n===  SISTEMA PCGESTOR FINALIZADO ===");
        }
    }
}