package residenciageriatrica.controllers;

import residenciageriatrica.daos.AsignacionDAO;
import residenciageriatrica.daos.ReservaDAO;
import residenciageriatrica.dtos.AsignacionDTO;
import residenciageriatrica.dtos.ReservaDTO;
import utils.Validaciones;

import java.util.ArrayList;
import java.util.List;
import residenciageriatrica.daos.EnfermeroDAO;
import residenciageriatrica.daos.ResidenteDAO;
import residenciageriatrica.dtos.EnfermeroDTO;
import residenciageriatrica.dtos.ResidenteDTO;

public class ConsultasController {

    private final ReservaDAO reservaDAO;
    private final AsignacionDAO asignacionDAO;
    private final EnfermeroDAO enfermeroDAO;
    private final ResidenteDAO residenteDAO;

    public ConsultasController() {
        this.reservaDAO = new ReservaDAO();
        this.asignacionDAO = new AsignacionDAO();
        this.enfermeroDAO = new EnfermeroDAO();
        this.residenteDAO = new ResidenteDAO();
    }

    // Consulta 1:
    // Consultar la habitación reservada de un residente.
    public String buscarHabitacionDeResidente(String idResidente) {

        if (!Validaciones.esTextoValido(idResidente)) {
            throw new IllegalArgumentException(
                    "El ID del residente es obligatorio."
            );
        }

        ReservaDTO reserva
                = reservaDAO.obtenerPorIdResidente(
                        idResidente.trim().toUpperCase()
                );

        if (reserva == null) {
            return null;
        }

        return reserva.getCodHabitacion();
    }

    // Consulta 2:
    // Consultar las habitaciones asignadas a un enfermero.
    public List<String> buscarHabitacionesDeEnfermero(String idEnfermero) {

        if (!Validaciones.esTextoValido(idEnfermero)) {
            throw new IllegalArgumentException(
                    "El ID del enfermero es obligatorio."
            );
        }

        List<AsignacionDTO> asignaciones
                = asignacionDAO.obtenerPorIdEnfermero(
                        idEnfermero.trim().toUpperCase()
                );

        List<String> habitaciones = new ArrayList<>();

        for (AsignacionDTO asignacion : asignaciones) {
            habitaciones.add(asignacion.getCodHabitacion());
        }

        return habitaciones;
    }

    // Consulta 3:
    // Consultar el enfermero asignado a un residente.
    public EnfermeroDTO buscarEnfermeroDeResidente(String idResidente) {

        if (!Validaciones.esTextoValido(idResidente)) {
            throw new IllegalArgumentException(
                    "El ID del residente es obligatorio."
            );
        }

        ReservaDTO reserva
                = reservaDAO.obtenerPorIdResidente(
                        idResidente.trim().toUpperCase()
                );

        if (reserva == null) {
            return null;
        }

        AsignacionDTO asignacion
                = asignacionDAO.obtenerPorCodHabitacion(
                        reserva.getCodHabitacion()
                );

        if (asignacion == null) {
            return null;
        }

        return enfermeroDAO.obtenerPorId(
                asignacion.getIdEnfermero()
        );
    }

    public ResidenteDTO buscarResidente(String idResidente) {

        if (!Validaciones.esTextoValido(idResidente)) {
            throw new IllegalArgumentException(
                    "El ID del residente es obligatorio."
            );
        }

        return residenteDAO.obtenerPorId(
                idResidente.trim().toUpperCase()
        );
    }

    public EnfermeroDTO buscarEnfermero(String idEnfermero) {

        if (!Validaciones.esTextoValido(idEnfermero)) {
            throw new IllegalArgumentException(
                    "El ID del enfermero es obligatorio."
            );
        }

        return enfermeroDAO.obtenerPorId(
                idEnfermero.trim().toUpperCase()
        );
    }
}
