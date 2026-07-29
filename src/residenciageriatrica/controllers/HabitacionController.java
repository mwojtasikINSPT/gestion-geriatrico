package residenciageriatrica.controllers;

import utils.Validaciones;
import java.util.ArrayList;
import java.util.List;
import residenciageriatrica.daos.HabitacionDAO;
import residenciageriatrica.dtos.HabitacionDTO;
import residenciageriatrica.models.Estado;

public class HabitacionController {

    private HabitacionDAO habitacionDAO;
    private List<String> codigosHistoricos; 

    public HabitacionController() {
        this.habitacionDAO = new HabitacionDAO();
        this.codigosHistoricos = new ArrayList<>(); // Podés cargar históricos si los manejás en archivo
    }

    public String generarSiguienteCodigo() {
        List<HabitacionDTO> habitacionesActivas = habitacionDAO.obtenerRegistros();
        List<String> codigosActivos = new ArrayList<>();
        for (HabitacionDTO h : habitacionesActivas) {
            codigosActivos.add(h.getCodHabitacion());
        }
        // Usa el utilitario compartido para generar el siguiente ID con prefijo "H"
        return Validaciones.generarSiguienteId(codigosActivos, codigosHistoricos, "H");
    }

    public boolean agregarHabitacion() {
        // El código se genera automáticamente, no se recibe por parámetro
        String nuevoCodigo = generarSiguienteCodigo();

        // Se crea directamente con estado LIBRE
        HabitacionDTO nuevaHabitacion = new HabitacionDTO(nuevoCodigo, Estado.LIBRE);
        habitacionDAO.agregar(nuevaHabitacion);
        return true;
    }

    public int eliminarHabitacion(String codigo) {
        HabitacionDTO habitacion = habitacionDAO.obtenerPorId(codigo);
        
        if (habitacion == null) {
            return 1; // No encontrado
        }

        if (habitacion.getEstado() == Estado.OCUPADA) {
            return 2; // Está ocupada, no se puede eliminar
        }

        habitacionDAO.eliminar(codigo);
        codigosHistoricos.add(codigo);
        return 0; // Éxito
    }

    public List<HabitacionDTO> obtenerTodas() {
        return habitacionDAO.obtenerRegistros();
    }
}