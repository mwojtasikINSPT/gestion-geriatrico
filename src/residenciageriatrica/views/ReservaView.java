package residenciageriatrica.views;

import residenciageriatrica.controllers.ReservaController;
import residenciageriatrica.dtos.ReservaDTO;
import residenciageriatrica.utils.Mensajes;
import residenciageriatrica.utils.Mostrar;

import java.util.List;
import java.util.Scanner;

public class ReservaView {

    private final ReservaController controller;
    private final Scanner scanner;

    public ReservaView(Scanner scanner) {
        this.controller = new ReservaController();
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
        String menuTexto = "\n---- GESTIÓN DE RESERVAS ----\n" +
                           "1. Crear Reserva (Asignar Habitación)\n" +
                           "2. Modificar Reserva (Cambiar Habitación)\n" +
                           "3. Listar Reservas\n" +
                           "4. Eliminar Reserva (Liberar Habitación)\n" +
                           "0. Volver";
        do {
            opcion = Mostrar.Menu(menuTexto, scanner);

            switch (opcion) {
                case 1:
                    agregar();
                    break;
                case 2:
                    modificar();
                    break;
                case 3:
                    listar();
                    break;
                case 4:
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
        Mostrar.Titulo("Nueva Reserva");
        
        String idResidente = pedirDato("el ID del residente (ej: R0001)");
        String codHabitacion = pedirDato("el código de la habitación (ej: H0001)");

        try {
            controller.agregarReserva(idResidente, codHabitacion);
            mostrarTexto(Mensajes.EXITO_GUARDAR);
        } catch (IllegalArgumentException e) {
            mostrarTexto("Error: " + e.getMessage());
        } catch (Exception e) {
            mostrarTexto(Mensajes.ERROR_GUARDAR);
        }
    }

    private void modificar() {
        Mostrar.Titulo("Modificar Reserva");
        
        String idResidente = pedirDato("el ID del residente de la reserva a modificar (ej: R0001)");
        String nuevoCodHabitacion = pedirDato("el NUEVO código de habitación (ej: H0002)");

        try {
            controller.modificarReserva(idResidente, nuevoCodHabitacion);
            mostrarTexto(Mensajes.EXITO_ACTUALIZAR);
        } catch (IllegalArgumentException e) {
            mostrarTexto("Error: " + e.getMessage());
        } catch (Exception e) {
            mostrarTexto(Mensajes.ERROR_ACTUALIZAR);
        }
    }

    private void listar() {
        Mostrar.Titulo("Lista de Reservas");
        List<ReservaDTO> lista = controller.obtenerTodas();
        if (lista.isEmpty()) {
            mostrarTexto(Mensajes.SIN_REGISTROS);
            return;
        }
        for (ReservaDTO r : lista) {
            mostrarTexto("Residente ID: " + r.getIdResidente() + " | Habitación: " + r.getCodHabitacion());
        }
    }

    private void eliminar() {
        Mostrar.Titulo("Cancelar Reserva");
        String idResidente = pedirDato("el ID del residente cuya reserva querés eliminar (ej: R0001)");

        try {
            controller.eliminarReserva(idResidente);
            mostrarTexto(Mensajes.EXITO_ELIMINAR);
        } catch (IllegalArgumentException e) {
            mostrarTexto("Error: " + e.getMessage());
        } catch (Exception e) {
            mostrarTexto(Mensajes.ERROR_ELIMINAR);
        }
    }
}