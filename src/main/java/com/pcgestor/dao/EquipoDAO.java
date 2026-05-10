package com.pcgestor.dao;

import com.pcgestor.model.Equipo;
import java.util.List;
import java.util.Optional;

public interface EquipoDAO {
    void crear(Equipo equipo);
    Optional<Equipo> leer(int id);
    List<Equipo> leerTodos();
    void actualizar(Equipo equipo);
    void eliminar(int id);
    Optional<Equipo> buscarPorSerial(String serial);
    List<Equipo> buscarPorEstado(String estado);
    List<Equipo> buscarPorTipo(String tipo);
}
