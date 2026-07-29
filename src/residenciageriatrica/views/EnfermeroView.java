package residenciageriatrica.views;

import residenciageriatrica.controllers.EnfermeroController;
import residenciageriatrica.dtos.EnfermeroDTO;
import utils.Mensajes;
import utils.Mostrar;
import java.util.List;
import java.util.Scanner;

public class EnfermeroView {

    private final EnfermeroController controller;
    private final Scanner scanner;

    public EnfermeroView(Scanner scanner) {
        this.controller = new EnfermeroController();
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
        String menuTexto = "\n---- GESTIÓN DE ENFERMEROS ----\n" +
                           "1. Agregar Enfermero\n" +
                           "2. Modificar Enfermero\n" +
                           "3. Listar Enfermeros\n" +
                           "4. Eliminar Enfermero\n" +
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
        Mostrar.Titulo("Nuevo Enfermero");
        
        String dni = pedirDato("el DNI (8 dígitos)");
        String nombre = pedirDato("el nombre");
        String apellido = pedirDato("el apellido");

        try {
            controller.agregarEnfermero(dni, nombre, apellido);
            mostrarTexto(Mensajes.EXITO_GUARDAR);
        } catch (IllegalArgumentException e) {
            mostrarTexto("Error: " + e.getMessage());
        } catch (Exception e) {
            mostrarTexto(Mensajes.ERROR_GUARDAR);
        }
    }

    private void modificar() {
        Mostrar.Titulo("Modificar Enfermero");
        
        String id = pedirDato("el ID del enfermero a modificar (ej: E0001)");
        String nuevoDni = pedirDato("el NUEVO DNI (8 dígitos)");
        String nuevoNombre = pedirDato("el NUEVO nombre");
        String nuevoApellido = pedirDato("el NUEVO apellido");

        try {
            controller.modificarEnfermero(id, nuevoDni, nuevoNombre, nuevoApellido);
            mostrarTexto(Mensajes.EXITO_ACTUALIZAR);
        } catch (IllegalArgumentException e) {
            mostrarTexto("Error: " + e.getMessage());
        } catch (Exception e) {
            mostrarTexto(Mensajes.ERROR_ACTUALIZAR);
        }
    }

    private void listar() {
        Mostrar.Titulo("Lista de Enfermeros");
        List<EnfermeroDTO> lista = controller.obtenerTodos();
        if (lista.isEmpty()) {
            mostrarTexto(Mensajes.SIN_REGISTROS);
            return;
        }
        for (EnfermeroDTO e : lista) {
            mostrarTexto("ID: " + e.getId() + " | DNI: " + e.getDni() + " | Nombre: " + e.getNombre() + " " + e.getApellido());
        }
    }

    private void eliminar() {
        Mostrar.Titulo("Eliminar Enfermero");
        String id = pedirDato("el ID del enfermero a eliminar (ej: E0001)");

        try {
            controller.eliminarEnfermero(id);
            mostrarTexto(Mensajes.EXITO_ELIMINAR);
        } catch (IllegalArgumentException e) {
            mostrarTexto("Error: " + e.getMessage());
        } catch (Exception e) {
            mostrarTexto(Mensajes.ERROR_ELIMINAR);
        }
    }
}