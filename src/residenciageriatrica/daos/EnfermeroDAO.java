package residenciageriatrica.daos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import residenciageriatrica.dtos.EnfermeroDTO;

public class EnfermeroDAO implements ICrud<EnfermeroDTO, String> {

    private static final String ARCHIVO = "enfermeros.txt";

    @Override
    public void agregar(EnfermeroDTO enfermero) {
        List<EnfermeroDTO> lista = obtenerRegistros();
        lista.add(enfermero);
        guardarTodos(lista);
    }

    @Override
    public List<EnfermeroDTO> obtenerRegistros() {
        List<EnfermeroDTO> enfermeros = new ArrayList<>();
        File file = new File(ARCHIVO);
        if (!file.exists()) {
            return enfermeros;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (!linea.trim().isEmpty()) {
                    String[] partes = linea.split(",");
                    if (partes.length == 4) {
                        String id = partes[0].trim();
                        String dni = partes[1].trim();
                        String nombre = partes[2].trim();
                        String apellido = partes[3].trim();
                        enfermeros.add(new EnfermeroDTO(id, dni, nombre, apellido));
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo de enfermeros.", e);
        }
        return enfermeros;
    }

    @Override
    public EnfermeroDTO obtenerPorId(String idEnfermero) {
        for (EnfermeroDTO e : obtenerRegistros()) {
            if (e.getId().equalsIgnoreCase(idEnfermero)) {
                return e;
            }
        }
        return null;
    }

    @Override
    public void modificar(EnfermeroDTO enfermeroModificado) {
        List<EnfermeroDTO> lista = obtenerRegistros();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId().equalsIgnoreCase(enfermeroModificado.getId())) {
                lista.set(i, enfermeroModificado);
                break;
            }
        }
        guardarTodos(lista);
    }

    @Override
    public void eliminar(String idEnfermero) {
        List<EnfermeroDTO> lista = obtenerRegistros();
        lista.removeIf(e -> e.getId().equalsIgnoreCase(idEnfermero));
        guardarTodos(lista);
    }

    // Método auxiliar para sobreescribir todo el archivo plano
    private void guardarTodos(List<EnfermeroDTO> enfermeros) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO, false))) {
            for (EnfermeroDTO e : enfermeros) {
                bw.write(e.getId() + "," + e.getDni() + "," + e.getNombre() + "," + e.getApellido());
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar en el archivo de enfermeros.", e);
        }
    }
}