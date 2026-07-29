package residenciageriatrica.views;

import residenciageriatrica.controllers.AsignacionController;
import residenciageriatrica.dtos.AsignacionDTO;
import utils.Mensajes;
import utils.Mostrar;

import java.util.List;
import java.util.Scanner;

public class AsignacionView {

    private final AsignacionController controller;
    private final Scanner scanner;

    public AsignacionView(Scanner scanner) {
        this.controller = new AsignacionController();
        this.scanner = scanner;
    }

    private void mostrarTexto(String texto) {
        System.out.println(texto);
    }

    private String pedirDato(String mensaje) {
        System.out.print(Mensajes.PEDIR_DATO + mensaje + ": ");
        return scanner.nextLine();
    }

    public void mostrarMenu() {
        int opcion;
        String menuTexto = "\n---- GESTIÓN DE ASIGNACIONES (ENFERMEROS) ----\n" +
                           "1. Asignar Habitación a Enfermero\n" +
                           "2. Listar Asignaciones\n" +
                           "3. Quitar Asignación (por Habitación)\n" +
                           "0. Volver";
        do {
            opcion = Mostrar.Menu(menuTexto, scanner);

            switch (opcion) {
                case 1:
                    agregar();
                    break;
                case 2:
                    listar();
                    break;
                case 3:
                    eliminar();
                    break;
                case 0:
                    mostrarTexto(Mensajes.VOLVIENDO);
                    break;
                default:
                    mostrarTexto(Mensajes.OPCION_INVALIDA);
            }
        } while (opcion != 0);
    }

    private void agregar() {
        Mostrar.Titulo("Nueva Asignación");
        
        String idEnfermero = pedirDato("el ID del enfermero (ej: E0001)");
        String codHabitacion = pedirDato("el código de la habitación (ej: H0001)");

        try {
            controller.agregarAsignacion(idEnfermero, codHabitacion);
            mostrarTexto(Mensajes.EXITO_GUARDAR);
        } catch (IllegalArgumentException e) {
            mostrarTexto("Error: " + e.getMessage());
        } catch (Exception e) {
            mostrarTexto(Mensajes.ERROR_GUARDAR);
        }
    }

    private void listar() {
        Mostrar.Titulo("Lista de Asignaciones");
        List<AsignacionDTO> lista = controller.obtenerTodas();
        if (lista.isEmpty()) {
            mostrarTexto(Mensajes.SIN_REGISTROS);
            return;
        }
        for (AsignacionDTO a : lista) {
            mostrarTexto("Enfermero ID: " + a.getIdEnfermero() + " | Habitación: " + a.getCodHabitacion());
        }
    }

    private void eliminar() {
        Mostrar.Titulo("Quitar Asignación");
        String codHabitacion = pedirDato("el código de la habitación cuya asignación querés quitar (ej: H0001)");

        try {
            controller.eliminarAsignacionPorHabitacion(codHabitacion);
            mostrarTexto(Mensajes.EXITO_ELIMINAR);
        } catch (IllegalArgumentException e) {
            mostrarTexto("Error: " + e.getMessage());
        } catch (Exception e) {
            mostrarTexto(Mensajes.ERROR_ELIMINAR);
        }
    }
}