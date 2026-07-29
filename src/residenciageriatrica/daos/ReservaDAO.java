package residenciageriatrica.daos;

import residenciageriatrica.dtos.ReservaDTO;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReservaDAO {

    private final String ARCHIVO = "reservas.txt";

    public List<ReservaDTO> obtenerRegistros() {
        List<ReservaDTO> reservas = new ArrayList<>();
        File archivo = new File(ARCHIVO);
        if (!archivo.exists()) {
            return reservas;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length >= 2) {
                    reservas.add(new ReservaDTO(partes[0].trim(), partes[1].trim()));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo de reservas.", e);
        }
        return reservas;
    }

    public ReservaDTO obtenerPorIdResidente(String idResidente) {
        String idBuscado = idResidente.trim().toUpperCase();
        for (ReservaDTO r : obtenerRegistros()) {
            if (r.getIdResidente().equalsIgnoreCase(idBuscado)) {
                return r;
            }
        }
        return null;
    }

    public void agregar(ReservaDTO reserva) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARCHIVO, true))) {
            pw.println(reserva.getIdResidente().trim().toUpperCase() + ";" + reserva.getCodHabitacion().trim().toUpperCase());
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar la reserva.", e);
        }
    }

    public void modificar(ReservaDTO reservaModificada) {
        List<ReservaDTO> reservas = obtenerRegistros();
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARCHIVO, false))) {
            for (ReservaDTO r : reservas) {
                if (r.getIdResidente().equalsIgnoreCase(reservaModificada.getIdResidente())) {
                    pw.println(reservaModificada.getIdResidente().trim().toUpperCase() + ";" + reservaModificada.getCodHabitacion().trim().toUpperCase());
                } else {
                    pw.println(r.getIdResidente() + "," + r.getCodHabitacion());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al modificar la reserva.", e);
        }
    }

    public void eliminar(String idResidente) {
        List<ReservaDTO> reservas = obtenerRegistros();
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARCHIVO, false))) {
            for (ReservaDTO r : reservas) {
                if (!r.getIdResidente().equalsIgnoreCase(idResidente.trim())) {
                    pw.println(r.getIdResidente() + "," + r.getCodHabitacion());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al eliminar la reserva.", e);
        }
    }
}