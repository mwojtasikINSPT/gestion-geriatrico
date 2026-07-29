package residenciageriatrica.daos;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import residenciageriatrica.dtos.HabitacionDTO;
import residenciageriatrica.models.Estado;

public class HabitacionDAO implements ICrud<HabitacionDTO, String> {

    private static final String ARCHIVO = "habitaciones.txt";

    @Override
    public void agregar(HabitacionDTO habitacion) {
        List<HabitacionDTO> lista = obtenerRegistros();
        lista.add(habitacion);
        guardarTodos(lista);
    }

    @Override
    public List<HabitacionDTO> obtenerRegistros() {
        List<HabitacionDTO> habitaciones = new ArrayList<>();
        File file = new File(ARCHIVO);
        if (!file.exists()) {
            return habitaciones;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (!linea.trim().isEmpty()) {
                    String[] partes = linea.split(",");
                    if (partes.length == 2) {
                        String codHabitacion = partes[0].trim();
                        Estado estado = Estado.valueOf(partes[1].trim());
                        habitaciones.add(new HabitacionDTO(codHabitacion, estado));
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo de habitaciones.", e);
        }
        return habitaciones;
    }

    @Override
    public HabitacionDTO obtenerPorId(String codHabitacion) {
        for (HabitacionDTO h : obtenerRegistros()) {
            if (h.getCodHabitacion().equalsIgnoreCase(codHabitacion)) {
                return h;
            }
        }
        return null;
    }

    @Override
    public void modificar(HabitacionDTO habitacionModificada) {
        List<HabitacionDTO> lista = obtenerRegistros();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getCodHabitacion().equalsIgnoreCase(habitacionModificada.getCodHabitacion())) {
                lista.set(i, habitacionModificada);
                break;
            }
        }
        guardarTodos(lista);
    }

    @Override
    public void eliminar(String codHabitacion) {
        List<HabitacionDTO> lista = obtenerRegistros();
        lista.removeIf(h -> h.getCodHabitacion().equalsIgnoreCase(codHabitacion));
        guardarTodos(lista);
    }

    // Método auxiliar para sobreescribir todo el archivo plano
    private void guardarTodos(List<HabitacionDTO> habitaciones) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO, false))) {
            for (HabitacionDTO h : habitaciones) {
                bw.write(h.getCodHabitacion() + "," + h.getEstado());
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar en el archivo de habitaciones.", e);
        }
    }
}