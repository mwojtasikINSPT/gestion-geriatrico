package residenciageriatrica.daos;

import residenciageriatrica.dtos.AsignacionDTO;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AsignacionDAO {

    private final String ARCHIVO = "asignaciones.txt";

    public List<AsignacionDTO> obtenerRegistros() {
        List<AsignacionDTO> asignaciones = new ArrayList<>();
        File archivo = new File(ARCHIVO);
        if (!archivo.exists()) {
            return asignaciones;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length >= 2) {
                    asignaciones.add(new AsignacionDTO(partes[0].trim(), partes[1].trim()));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo de asignaciones.", e);
        }
        return asignaciones;
    }

    public List<AsignacionDTO> obtenerPorIdEnfermero(String idEnfermero) {
        List<AsignacionDTO> listaEnfermero = new ArrayList<>();
        String idBuscado = idEnfermero.trim().toUpperCase();
        for (AsignacionDTO a : obtenerRegistros()) {
            if (a.getIdEnfermero().equalsIgnoreCase(idBuscado)) {
                listaEnfermero.add(a);
            }
        }
        return listaEnfermero;
    }

    public void agregar(AsignacionDTO asignacion) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARCHIVO, true))) {
            pw.println(asignacion.getIdEnfermero().trim().toUpperCase() + "," + asignacion.getCodHabitacion().trim().toUpperCase());
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar la asignación.", e);
        }
    }

    public void eliminarPorHabitacion(String codHabitacion) {
        List<AsignacionDTO> asignaciones = obtenerRegistros();
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARCHIVO, false))) {
            for (AsignacionDTO a : asignaciones) {
                if (!a.getCodHabitacion().equalsIgnoreCase(codHabitacion.trim())) {
                    pw.println(a.getIdEnfermero() + "," + a.getCodHabitacion());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al eliminar la asignación de la habitación.", e);
        }
    }

    public void eliminarTodasDeEnfermero(String idEnfermero) {
        List<AsignacionDTO> asignaciones = obtenerRegistros();
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARCHIVO, false))) {
            for (AsignacionDTO a : asignaciones) {
                if (!a.getIdEnfermero().equalsIgnoreCase(idEnfermero.trim())) {
                    pw.println(a.getIdEnfermero() + "," + a.getCodHabitacion());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al eliminar las asignaciones del enfermero.", e);
        }
    }

    public AsignacionDTO obtenerPorCodHabitacion(String codHabitacion) {
    String codBuscado = codHabitacion.trim().toUpperCase();

    for (AsignacionDTO asignacion : obtenerRegistros()) {
        if (asignacion.getCodHabitacion().equalsIgnoreCase(codBuscado)) {
            return asignacion;
        }
    }

    return null;
}
}
