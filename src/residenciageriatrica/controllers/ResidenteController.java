package residenciageriatrica.controllers;

import residenciageriatrica.daos.ResidenteDAO;
import residenciageriatrica.dtos.ResidenteDTO;
import utils.Validaciones;
import java.util.ArrayList;
import java.util.List;

public class ResidenteController {

    private final ResidenteDAO residenteDAO;
    private final List<String> idsHistoricos;

    public ResidenteController() {
        this.residenteDAO = new ResidenteDAO();
        this.idsHistoricos = new ArrayList<>();
    }

    public String generarSiguienteId() {
        List<ResidenteDTO> residentesActivos = residenteDAO.obtenerRegistros();
        List<String> idsActivos = new ArrayList<>();
        for (ResidenteDTO r : residentesActivos) {
            idsActivos.add(r.getId());
        }
        return Validaciones.generarSiguienteId(idsActivos, idsHistoricos, "R");
    }

    public void agregarResidente(String dni, String nombre, String apellido) throws IllegalArgumentException {
        if (!Validaciones.esDniValido(dni)) {
            throw new IllegalArgumentException("DNI inválido: debe tener exactamente 8 números.");
        }
        if (!Validaciones.esTextoValido(nombre) || !Validaciones.esTextoValido(apellido)) {
            throw new IllegalArgumentException("Nombre o apellido inválidos.");
        }

        String dniNormalizado = dni.trim();

        // Validar DNI repetido
        for (ResidenteDTO r : residenteDAO.obtenerRegistros()) {
            if (r.getDni().equalsIgnoreCase(dniNormalizado)) {
                throw new IllegalArgumentException("Ya existe un residente registrado con ese DNI.");
            }
        }

        String nuevoId = generarSiguienteId();
        ResidenteDTO nuevoResidente = new ResidenteDTO(
                nuevoId, 
                dniNormalizado, 
                Validaciones.normalizarTexto(nombre), 
                Validaciones.normalizarTexto(apellido)
        );

        try {
            residenteDAO.agregar(nuevoResidente);
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar el residente en el sistema.", e);
        }
    }

    public void modificarResidente(String id, String nuevoDni, String nuevoNombre, String nuevoApellido) throws IllegalArgumentException {
        ResidenteDTO residenteExistente = residenteDAO.obtenerPorId(id);
        if (residenteExistente == null) {
            throw new IllegalArgumentException("No se encontró el residente con ID: " + id);
        }

        if (!Validaciones.esDniValido(nuevoDni)) {
            throw new IllegalArgumentException("DNI inválido: debe tener exactamente 8 números.");
        }
        if (!Validaciones.esTextoValido(nuevoNombre) || !Validaciones.esTextoValido(nuevoApellido)) {
            throw new IllegalArgumentException("Nombre o apellido inválidos.");
        }

        String dniNormalizado = nuevoDni.trim();

        // Validar que el nuevo DNI no pertenezca a OTRO residente diferente
        for (ResidenteDTO r : residenteDAO.obtenerRegistros()) {
            if (r.getDni().equalsIgnoreCase(dniNormalizado) && !r.getId().equalsIgnoreCase(id)) {
                throw new IllegalArgumentException("Ya existe otro residente registrado con ese DNI.");
            }
        }

        // Actualizamos los datos del DTO
        residenteExistente.setDni(dniNormalizado);
        residenteExistente.setNombre(Validaciones.normalizarTexto(nuevoNombre));
        residenteExistente.setApellido(Validaciones.normalizarTexto(nuevoApellido));

        try {
            residenteDAO.modificar(residenteExistente);
        } catch (Exception e) {
            throw new RuntimeException("Error al modificar el residente en el sistema.", e);
        }
    }

    public void eliminarResidente(String id) throws IllegalArgumentException {
        ResidenteDTO residente = residenteDAO.obtenerPorId(id);
        if (residente == null) {
            throw new IllegalArgumentException("No se encontró el residente con ID: " + id);
        }

        try {
            residenteDAO.eliminar(id);
            idsHistoricos.add(id);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar el residente.", e);
        }
    }

    public List<ResidenteDTO> obtenerTodos() {
        return residenteDAO.obtenerRegistros();
    }
}