package residenciageriatrica.controllers;


import residenciageriatrica.daos.EnfermeroDAO;
import residenciageriatrica.dtos.EnfermeroDTO;
import utils.Validaciones;

import java.util.ArrayList;
import java.util.List;

public class EnfermeroController {

    private final EnfermeroDAO enfermeroDAO;
    private final List<String> idsHistoricos;

    public EnfermeroController() {
        this.enfermeroDAO = new EnfermeroDAO();
        this.idsHistoricos = new ArrayList<>();
    }

    public String generarSiguienteId() {
        List<EnfermeroDTO> enfermerosActivos = enfermeroDAO.obtenerRegistros();
        List<String> idsActivos = new ArrayList<>();
        for (EnfermeroDTO e : enfermerosActivos) {
            idsActivos.add(e.getId());
        }
        return Validaciones.generarSiguienteId(idsActivos, idsHistoricos, "E");
    }

    public void agregarEnfermero(String dni, String nombre, String apellido) throws IllegalArgumentException {
        if (!Validaciones.esDniValido(dni)) {
            throw new IllegalArgumentException("DNI inválido: debe tener exactamente 8 números.");
        }
        if (!Validaciones.esTextoValido(nombre) || !Validaciones.esTextoValido(apellido)) {
            throw new IllegalArgumentException("Nombre o apellido inválidos.");
        }

        String dniNormalizado = dni.trim();

        // Validar DNI repetido
        for (EnfermeroDTO e : enfermeroDAO.obtenerRegistros()) {
            if (e.getDni().equalsIgnoreCase(dniNormalizado)) {
                throw new IllegalArgumentException("Ya existe un enfermero registrado con ese DNI.");
            }
        }

        String nuevoId = generarSiguienteId();
        EnfermeroDTO nuevoEnfermero = new EnfermeroDTO(
                nuevoId, 
                dniNormalizado, 
                Validaciones.normalizarTexto(nombre), 
                Validaciones.normalizarTexto(apellido)
        );

        try {
            enfermeroDAO.agregar(nuevoEnfermero);
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar el enfermero en el sistema.", e);
        }
    }

    public void modificarEnfermero(String id, String nuevoDni, String nuevoNombre, String nuevoApellido) throws IllegalArgumentException {
        EnfermeroDTO enfermeroExistente = enfermeroDAO.obtenerPorId(id);
        if (enfermeroExistente == null) {
            throw new IllegalArgumentException("No se encontró el enfermero con ID: " + id);
        }

        if (!Validaciones.esDniValido(nuevoDni)) {
            throw new IllegalArgumentException("DNI inválido: debe tener exactamente 8 números.");
        }
        if (!Validaciones.esTextoValido(nuevoNombre) || !Validaciones.esTextoValido(nuevoApellido)) {
            throw new IllegalArgumentException("Nombre o apellido inválidos.");
        }

        String dniNormalizado = nuevoDni.trim();

        // Validar que el nuevo DNI no pertenezca a OTRO enfermero diferente
        for (EnfermeroDTO e : enfermeroDAO.obtenerRegistros()) {
            if (e.getDni().equalsIgnoreCase(dniNormalizado) && !e.getId().equalsIgnoreCase(id)) {
                throw new IllegalArgumentException("Ya existe otro enfermero registrado con ese DNI.");
            }
        }

        enfermeroExistente.setDni(dniNormalizado);
        enfermeroExistente.setNombre(Validaciones.normalizarTexto(nuevoNombre));
        enfermeroExistente.setApellido(Validaciones.normalizarTexto(nuevoApellido));

        try {
            enfermeroDAO.modificar(enfermeroExistente);
        } catch (Exception e) {
            throw new RuntimeException("Error al modificar el enfermero en el sistema.", e);
        }
    }

    public void eliminarEnfermero(String id) throws IllegalArgumentException {
        EnfermeroDTO enfermero = enfermeroDAO.obtenerPorId(id);
        if (enfermero == null) {
            throw new IllegalArgumentException("No se encontró el enfermero con ID: " + id);
        }

        try {
            enfermeroDAO.eliminar(id);
            idsHistoricos.add(id);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar el enfermero.", e);
        }
    }

    public List<EnfermeroDTO> obtenerTodos() {
        return enfermeroDAO.obtenerRegistros();
    }
}