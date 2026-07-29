package residenciageriatrica.daos;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import residenciageriatrica.dtos.ResidenteDTO;

public class ResidenteDAO implements ICrud<ResidenteDTO, String> {

    private static final String ARCHIVO = "residentes.txt";

    @Override
    public void agregar(ResidenteDTO residente) {
        List<ResidenteDTO> lista = obtenerRegistros();
        lista.add(residente);
        guardarTodos(lista);
    }

    @Override
    public List<ResidenteDTO> obtenerRegistros() {
        List<ResidenteDTO> residentes = new ArrayList<>();
        File file = new File(ARCHIVO);
        if (!file.exists()) {
            return residentes;
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
                        residentes.add(new ResidenteDTO(id, dni, nombre, apellido));
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo de residentes.", e);
        }
        return residentes;
    }

    @Override
    public ResidenteDTO obtenerPorId(String idResidente) {
        for (ResidenteDTO r : obtenerRegistros()) {
            if (r.getId().equalsIgnoreCase(idResidente)) {
                return r;
            }
        }
        return null;
    }

    @Override
    public void modificar(ResidenteDTO residenteModificado) {
        List<ResidenteDTO> lista = obtenerRegistros();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId().equalsIgnoreCase(residenteModificado.getId())) {
                lista.set(i, residenteModificado);
                break;
            }
        }
        guardarTodos(lista);
    }

    @Override
    public void eliminar(String idResidente) {
        List<ResidenteDTO> lista = obtenerRegistros();
        lista.removeIf(r -> r.getId().equalsIgnoreCase(idResidente));
        guardarTodos(lista);
    }

    // Método auxiliar para sobreescribir todo el archivo plano
    private void guardarTodos(List<ResidenteDTO> residentes) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO, false))) {
            for (ResidenteDTO r : residentes) {
                bw.write(r.getId() + "," + r.getDni() + "," + r.getNombre() + "," + r.getApellido());
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar en el archivo de residentes.", e);
        }
    }
}