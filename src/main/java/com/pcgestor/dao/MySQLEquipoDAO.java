package com.pcgestor.dao;

import com.pcgestor.model.Equipo;
import com.pcgestor.model.EstadoEquipo;
import com.pcgestor.model.TipoEquipo;
import com.pcgestor.model.Ubicacion;
import com.pcgestor.util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MySQLEquipoDAO implements EquipoDAO {
    private Connection connection;
    
    public MySQLEquipoDAO() {
        this.connection = DatabaseConnection.getConnection();
    }
    
    @Override
    public void crear(Equipo equipo) {
        // Primero verificar si el serial ya existe
        Optional<Equipo> equipoExistente = buscarPorSerial(equipo.getSerial());
        if (equipoExistente.isPresent()) {
            throw new RuntimeException("Ya existe un equipo con el serial: " + equipo.getSerial());
        }
        
        String sql = "INSERT INTO equipos (marca, modelo, serial, fecha_compra, tipo_id, estado_id, ubicacion_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, equipo.getMarca());
            stmt.setString(2, equipo.getModelo());
            stmt.setString(3, equipo.getSerial());
            stmt.setDate(4, Date.valueOf(equipo.getFechaCompra()));
            stmt.setInt(5, equipo.getTipoEquipo().getIdTipo());
            stmt.setInt(6, equipo.getEstadoEquipo().getIdEstado());
            stmt.setInt(7, equipo.getUbicacion().getIdUbicacion());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        equipo.setIdEquipo(generatedKeys.getInt(1));
                        System.out.println("✅ Equipo registrado exitosamente con ID: " + equipo.getIdEquipo());
                    }
                }
            }
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) { // Código de error para duplicados
                throw new RuntimeException("Error: Ya existe un equipo con el serial '" + equipo.getSerial() + "'");
            } else {
                throw new RuntimeException("Error al crear equipo: " + e.getMessage(), e);
            }
        }
    }
    
    @Override
    public Optional<Equipo> leer(int id) {
        String sql = "SELECT e.*, te.nombre_tipo, ee.nombre_estado, u.edificio, u.piso, u.sala " +
                    "FROM equipos e " +
                    "LEFT JOIN tipos_equipo te ON e.tipo_id = te.id_tipo " +
                    "LEFT JOIN estados_equipo ee ON e.estado_id = ee.id_estado " +
                    "LEFT JOIN ubicaciones u ON e.ubicacion_id = u.id_ubicacion " +
                    "WHERE e.id_equipo = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return Optional.of(mapResultSetToEquipo(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al leer equipo: " + e.getMessage(), e);
        }
        
        return Optional.empty();
    }
    
    @Override
    public List<Equipo> leerTodos() {
        List<Equipo> equipos = new ArrayList<>();
        String sql = "SELECT e.*, te.nombre_tipo, ee.nombre_estado, u.edificio, u.piso, u.sala " +
                    "FROM equipos e " +
                    "LEFT JOIN tipos_equipo te ON e.tipo_id = te.id_tipo " +
                    "LEFT JOIN estados_equipo ee ON e.estado_id = ee.id_estado " +
                    "LEFT JOIN ubicaciones u ON e.ubicacion_id = u.id_ubicacion";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                equipos.add(mapResultSetToEquipo(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al leer equipos: " + e.getMessage(), e);
        }
        
        return equipos;
    }
    
    @Override
    public void actualizar(Equipo equipo) {
        String sql = "UPDATE equipos SET marca = ?, modelo = ?, serial = ?, fecha_compra = ?, " +
                    "tipo_id = ?, estado_id = ?, ubicacion_id = ? WHERE id_equipo = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, equipo.getMarca());
            stmt.setString(2, equipo.getModelo());
            stmt.setString(3, equipo.getSerial());
            stmt.setDate(4, Date.valueOf(equipo.getFechaCompra()));
            stmt.setInt(5, equipo.getTipoEquipo().getIdTipo());
            stmt.setInt(6, equipo.getEstadoEquipo().getIdEstado());
            stmt.setInt(7, equipo.getUbicacion().getIdUbicacion());
            stmt.setInt(8, equipo.getIdEquipo());
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("✅ Equipo actualizado exitosamente");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar equipo: " + e.getMessage(), e);
        }
    }
    
    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM equipos WHERE id_equipo = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("✅ Equipo eliminado exitosamente");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar equipo: " + e.getMessage(), e);
        }
    }
    
    @Override
    public Optional<Equipo> buscarPorSerial(String serial) {
        String sql = "SELECT e.*, te.nombre_tipo, ee.nombre_estado, u.edificio, u.piso, u.sala " +
                    "FROM equipos e " +
                    "LEFT JOIN tipos_equipo te ON e.tipo_id = te.id_tipo " +
                    "LEFT JOIN estados_equipo ee ON e.estado_id = ee.id_estado " +
                    "LEFT JOIN ubicaciones u ON e.ubicacion_id = u.id_ubicacion " +
                    "WHERE e.serial = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, serial);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return Optional.of(mapResultSetToEquipo(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar equipo por serial: " + e.getMessage(), e);
        }
        
        return Optional.empty();
    }
    
    @Override
    public List<Equipo> buscarPorEstado(String estado) {
        List<Equipo> equipos = new ArrayList<>();
        String sql = "SELECT e.*, te.nombre_tipo, ee.nombre_estado, u.edificio, u.piso, u.sala " +
                    "FROM equipos e " +
                    "LEFT JOIN tipos_equipo te ON e.tipo_id = te.id_tipo " +
                    "LEFT JOIN estados_equipo ee ON e.estado_id = ee.id_estado " +
                    "LEFT JOIN ubicaciones u ON e.ubicacion_id = u.id_ubicacion " +
                    "WHERE ee.nombre_estado = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, estado);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                equipos.add(mapResultSetToEquipo(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar equipos por estado: " + e.getMessage(), e);
        }
        
        return equipos;
    }
    
    @Override
    public List<Equipo> buscarPorTipo(String tipo) {
        List<Equipo> equipos = new ArrayList<>();
        String sql = "SELECT e.*, te.nombre_tipo, ee.nombre_estado, u.edificio, u.piso, u.sala " +
                    "FROM equipos e " +
                    "LEFT JOIN tipos_equipo te ON e.tipo_id = te.id_tipo " +
                    "LEFT JOIN estados_equipo ee ON e.estado_id = ee.id_estado " +
                    "LEFT JOIN ubicaciones u ON e.ubicacion_id = u.id_ubicacion " +
                    "WHERE te.nombre_tipo = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, tipo);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                equipos.add(mapResultSetToEquipo(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar equipos por tipo: " + e.getMessage(), e);
        }
        
        return equipos;
    }
    
    private Equipo mapResultSetToEquipo(ResultSet rs) throws SQLException {
        Equipo equipo = new Equipo(
            rs.getString("marca"),
            rs.getString("modelo"),
            rs.getString("serial"),
            rs.getDate("fecha_compra").toLocalDate()
        );
        equipo.setIdEquipo(rs.getInt("id_equipo"));
        
        // Mapear TipoEquipo
        TipoEquipo tipoEquipo = new TipoEquipo(rs.getString("nombre_tipo"), "");
        tipoEquipo.setIdTipo(rs.getInt("tipo_id"));
        equipo.setTipoEquipo(tipoEquipo);
        
        // Mapear EstadoEquipo
        EstadoEquipo estadoEquipo = new EstadoEquipo(rs.getString("nombre_estado"));
        estadoEquipo.setIdEstado(rs.getInt("estado_id"));
        equipo.setEstadoEquipo(estadoEquipo);
        
        // Mapear Ubicacion
        Ubicacion ubicacion = new Ubicacion(
            rs.getString("edificio"),
            rs.getString("piso"),
            rs.getString("sala"),
            ""
        );
        ubicacion.setIdUbicacion(rs.getInt("ubicacion_id"));
        equipo.setUbicacion(ubicacion);
        
        return equipo;
    }
}