package residenciageriatrica.controllers;

import residenciageriatrica.daos.ReservaDAO;
import residenciageriatrica.daos.ResidenteDAO;
import residenciageriatrica.daos.HabitacionDAO;
import residenciageriatrica.dtos.ReservaDTO;
import residenciageriatrica.dtos.ResidenteDTO;
import residenciageriatrica.dtos.HabitacionDTO;
import utils.Validaciones;

import java.util.List;

public class ReservaController {

    private final ReservaDAO reservaDAO;
    private final ResidenteDAO residenteDAO;
    private final HabitacionDAO habitacionDAO;

    public ReservaController() {
        this.reservaDAO = new ReservaDAO();
        this.residenteDAO = new ResidenteDAO();
        this.habitacionDAO = new HabitacionDAO();
    }

    public void agregarReserva(String idResidente, String codHabitacion) throws IllegalArgumentException {
        if (!Validaciones.esTextoValido(idResidente) || !Validaciones.esTextoValido(codHabitacion)) {
            throw new IllegalArgumentException("El ID del residente y el código de la habitación son obligatorios.");
        }

        String idResNorm = idResidente.trim().toUpperCase();
        String codHabNorm = codHabitacion.trim().toUpperCase();

        ResidenteDTO residente = residenteDAO.obtenerPorId(idResNorm);
        if (residente == null) {
            throw new IllegalArgumentException("No se encontró el residente con ID: " + idResNorm);
        }

        HabitacionDTO habitacion = habitacionDAO.obtenerPorId(codHabNorm);
        if (habitacion == null) {
            throw new IllegalArgumentException("No se encontró la habitación con código: " + codHabNorm);
        }

        ReservaDTO reservaExistente = reservaDAO.obtenerPorIdResidente(idResNorm);
        if (reservaExistente != null) {
            throw new IllegalArgumentException("El residente ya tiene una habitación asignada (" + reservaExistente.getCodHabitacion() + ").");
        }

        // Guardar reserva
        ReservaDTO nuevaReserva = new ReservaDTO(idResNorm, codHabNorm);
        reservaDAO.agregar(nuevaReserva);

        // TODO: Actualizar estado de la habitación a OCUPADA (asumiendo que HabitacionDAO tiene un método modificar)
        // habitacion.setEstado("OCUPADA");
        // habitacionDAO.modificar(habitacion);
    }

    public void modificarReserva(String idResidente, String nuevoCodHabitacion) throws IllegalArgumentException {
        if (!Validaciones.esTextoValido(idResidente) || !Validaciones.esTextoValido(nuevoCodHabitacion)) {
            throw new IllegalArgumentException("El ID del residente y el nuevo código de habitación son obligatorios.");
        }

        String idResNorm = idResidente.trim().toUpperCase();
        String nuevoCodHabNorm = nuevoCodHabitacion.trim().toUpperCase();

        ReservaDTO reservaActual = reservaDAO.obtenerPorIdResidente(idResNorm);
        if (reservaActual == null) {
            throw new IllegalArgumentException("No se encontró una reserva activa para el residente con ID: " + idResNorm);
        }

        HabitacionDTO nuevaHabitacion = habitacionDAO.obtenerPorId(nuevoCodHabNorm);
        if (nuevaHabitacion == null) {
            throw new IllegalArgumentException("No se encontró la habitación con código: " + nuevoCodHabNorm);
        }

        // Liberar la habitación vieja y ocupar la nueva...
        reservaActual.setCodHabitacion(nuevoCodHabNorm);
        reservaDAO.modificar(reservaActual);
    }

    public void eliminarReserva(String idResidente) throws IllegalArgumentException {
        if (!Validaciones.esTextoValido(idResidente)) {
            throw new IllegalArgumentException("El ID del residente es obligatorio para eliminar la reserva.");
        }

        String idResNorm = idResidente.trim().toUpperCase();

        ReservaDTO reserva = reservaDAO.obtenerPorIdResidente(idResNorm);
        if (reserva == null) {
            throw new IllegalArgumentException("No se encontró una reserva activa para el residente con ID: " + idResNorm);
        }

        // 1. Eliminar la reserva del archivo
        reservaDAO.eliminar(idResNorm);

        // 2. Liberar la habitación asociada
        HabitacionDTO habitacion = habitacionDAO.obtenerPorId(reserva.getCodHabitacion());
        if (habitacion != null) {
            habitacion.setEstado(null); // O el estado LIBRE que maneje tu entidad Habitacion
            habitacionDAO.modificar(habitacion);
        }
    }

    public List<ReservaDTO> obtenerTodas() {
        return reservaDAO.obtenerRegistros();
    }
}