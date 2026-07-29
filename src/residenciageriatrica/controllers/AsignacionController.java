package residenciageriatrica.controllers;

import residenciageriatrica.daos.AsignacionDAO;
import residenciageriatrica.daos.EnfermeroDAO;
import residenciageriatrica.daos.HabitacionDAO;
import residenciageriatrica.dtos.AsignacionDTO;
import residenciageriatrica.dtos.EnfermeroDTO;
import residenciageriatrica.dtos.HabitacionDTO;
import utils.Validaciones;

import java.util.List;

public class AsignacionController {

    private final AsignacionDAO asignacionDAO;
    private final EnfermeroDAO enfermeroDAO;
    private final HabitacionDAO habitacionDAO;

    public AsignacionController() {
        this.asignacionDAO = new AsignacionDAO();
        this.enfermeroDAO = new EnfermeroDAO();
        this.habitacionDAO = new HabitacionDAO();
    }

    public void agregarAsignacion(String idEnfermero, String codHabitacion) throws IllegalArgumentException {
        if (!Validaciones.esTextoValido(idEnfermero) || !Validaciones.esTextoValido(codHabitacion)) {
            throw new IllegalArgumentException("El ID del enfermero y el código de la habitación son obligatorios.");
        }

        String idEnfNorm = idEnfermero.trim().toUpperCase();
        String codHabNorm = codHabitacion.trim().toUpperCase();

        // 1. Verificar que el enfermero exista
        EnfermeroDTO enfermero = enfermeroDAO.obtenerPorId(idEnfNorm);
        if (enfermero == null) {
            throw new IllegalArgumentException("No se encontró el enfermero con ID: " + idEnfNorm);
        }

        // 2. Verificar que la habitación exista
        HabitacionDTO habitacion = habitacionDAO.obtenerPorId(codHabNorm);
        if (habitacion == null) {
            throw new IllegalArgumentException("No se encontró la habitación con código: " + codHabNorm);
        }

        // 3. Verificar que la habitación no esté ya asignada a este mismo enfermero
        for (AsignacionDTO a : asignacionDAO.obtenerPorIdEnfermero(idEnfNorm)) {
            if (a.getCodHabitacion().equalsIgnoreCase(codHabNorm)) {
                throw new IllegalArgumentException("El enfermero ya tiene asignada la habitación " + codHabNorm + ".");
            }
        }

        // Crear y guardar la asignación (un enfermero puede tener varias)
        AsignacionDTO nuevaAsignacion = new AsignacionDTO(idEnfNorm, codHabNorm);
        asignacionDAO.agregar(nuevaAsignacion);
    }

    public void eliminarAsignacionPorHabitacion(String codHabitacion) throws IllegalArgumentException {
        if (!Validaciones.esTextoValido(codHabitacion)) {
            throw new IllegalArgumentException("El código de la habitación es obligatorio.");
        }

        String codHabNorm = codHabitacion.trim().toUpperCase();

        // Verificar si la habitación tiene asignaciones activas
        boolean encontrada = false;
        for (AsignacionDTO a : asignacionDAO.obtenerRegistros()) {
            if (a.getCodHabitacion().equalsIgnoreCase(codHabNorm)) {
                encontrada = true;
                break;
            }
        }

        if (!encontrada) {
            throw new IllegalArgumentException("No se encontró ninguna asignación activa para la habitación: " + codHabNorm);
        }

        asignacionDAO.eliminarPorHabitacion(codHabNorm);
    }

    public List<AsignacionDTO> obtenerTodas() {
        return asignacionDAO.obtenerRegistros();
    }
}