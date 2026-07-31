package residenciageriatrica.views;

import residenciageriatrica.controllers.ConsultasController;
import residenciageriatrica.utils.Mensajes;
import residenciageriatrica.utils.Mostrar;

import java.util.List;
import java.util.Scanner;
import residenciageriatrica.dtos.EnfermeroDTO;
import residenciageriatrica.dtos.ResidenteDTO;

public class ConsultasView {

    private final ConsultasController controller;
    private final Scanner scanner;

    public ConsultasView(Scanner scanner) {
        this.controller = new ConsultasController();
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

        String menuTexto = "\n---- CONSULTAS ----\n"
                + "1. Consultar habitación de residente\n"
                + "2. Consultar habitaciones asignadas a enfermero\n"
                + "3. Consultar enfermero por residente\n"
                + "0. Volver";

        do {
            opcion = Mostrar.Menu(menuTexto, scanner);

            switch (opcion) {
                case 1:
                    consultarHabitacionResidente();
                    break;
                case 2:
                    consultarHabitacionesEnfermero();
                    break;
                case 3:
                    consultarEnfermeroResidente();
                    break;
                case 0:
                    mostrarTexto(Mensajes.VOLVIENDO);
                    break;
                default:
                    mostrarTexto(Mensajes.OPCION_INVALIDA);
            }
        } while (opcion != 0);
    }

    private void consultarHabitacionResidente() {
        Mostrar.Titulo("Habitación de Residente");

        String idResidente = pedirDato("el ID del residente (ej: R0001)");

        try {
            ResidenteDTO residente
                    = controller.buscarResidente(idResidente);

            if (residente == null) {
                mostrarTexto("No se encontró un residente con el ID ingresado.");
                return;
            }

            mostrarTexto(
                    "Residente: "
                    + residente.getNombre()
                    + " "
                    + residente.getApellido()
                    + " ("
                    + residente.getId()
                    + ")"
            );

            String codHabitacion
                    = controller.buscarHabitacionDeResidente(idResidente);

            if (codHabitacion == null) {
                mostrarTexto(
                        "El residente no tiene una habitación reservada."
                );
            } else {
                mostrarTexto(
                        "Habitación asignada: " + codHabitacion
                );
            }

        } catch (IllegalArgumentException e) {
            mostrarTexto("Error: " + e.getMessage());
        } catch (Exception e) {
            mostrarTexto("Error al consultar la habitación del residente.");
        }
    }

    private void consultarEnfermeroResidente() {
        Mostrar.Titulo("Enfermero de Residente");

        String idResidente = pedirDato("el ID del residente (ej: R0001)");

        try {
            ResidenteDTO residente
                    = controller.buscarResidente(idResidente);

            if (residente == null) {
                mostrarTexto("No se encontró un residente con el ID ingresado.");
                return;
            }

            mostrarTexto(
                    "Residente: "
                    + residente.getNombre()
                    + " "
                    + residente.getApellido()
                    + " ("
                    + residente.getId()
                    + ")"
            );

            EnfermeroDTO enfermero
                    = controller.buscarEnfermeroDeResidente(idResidente);

            if (enfermero == null) {
                mostrarTexto(
                        "El residente no tiene un enfermero asignado."
                );
            } else {
                mostrarTexto(
                        "Enfermero asignado: "
                        + enfermero.getNombre()
                        + " "
                        + enfermero.getApellido()
                        + " ("
                        + enfermero.getId()
                        + ")"
                );
            }

        } catch (IllegalArgumentException e) {
            mostrarTexto("Error: " + e.getMessage());
        } catch (Exception e) {
            mostrarTexto("Error al consultar el enfermero del residente.");
        }
    }

    private void consultarHabitacionesEnfermero() {
        Mostrar.Titulo("Habitaciones Asignadas a Enfermero");

        String idEnfermero = pedirDato("el ID del enfermero (ej: E0001)");

        try {
            EnfermeroDTO enfermero
                    = controller.buscarEnfermero(idEnfermero);

            if (enfermero == null) {
                mostrarTexto("No se encontró un enfermero con el ID ingresado.");
                return;
            }

            mostrarTexto(
                    "Enfermero: "
                    + enfermero.getNombre()
                    + " "
                    + enfermero.getApellido()
                    + " ("
                    + enfermero.getId()
                    + ")"
            );

            List<String> habitaciones
                    = controller.buscarHabitacionesDeEnfermero(idEnfermero);

            if (habitaciones.isEmpty()) {
                mostrarTexto(
                        "Habitaciones asignadas: ninguna."
                );
            } else {
                mostrarTexto("Habitaciones asignadas:");

                for (String codHabitacion : habitaciones) {
                    mostrarTexto("- " + codHabitacion);
                }
            }

        } catch (IllegalArgumentException e) {
            mostrarTexto("Error: " + e.getMessage());
        } catch (Exception e) {
            mostrarTexto("Error al consultar las habitaciones del enfermero.");
        }
    }

}
