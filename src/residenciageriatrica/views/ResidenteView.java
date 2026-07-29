package residenciageriatrica.views;

import java.util.List;
import java.util.Scanner;
import residenciageriatrica.controllers.ResidenteController;
import residenciageriatrica.dtos.ResidenteDTO;
import utils.Mensajes;
import utils.Mostrar;

public class ResidenteView {

    private final ResidenteController controller;
    private final Scanner scanner;

    public ResidenteView(Scanner scanner) {
        this.controller = new ResidenteController();
        this.scanner = scanner;
    }

    // Métodos encapsulados para la interacción por consola
    private void mostrarTexto(String texto) {
        System.out.println(texto);
    }

    private String pedirDato(String mensaje) {
        System.out.print(Mensajes.PEDIR_DATO + mensaje + ": ");
        return scanner.nextLine();
    }

    public void mostrarMenu() {
        int opcion;
        String menuTexto = "\n---- GESTIÓN DE RESIDENTES ----\n" +
                           "1. Agregar Residente\n" +
                           "2. Modificar Residente\n" +
                           "3. Listar Residentes\n" +
                           "4. Eliminar Residente\n" +
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
        Mostrar.Titulo("Nuevo Residente");
        
        String dni = pedirDato("el DNI (8 dígitos)");
        String nombre = pedirDato("el nombre");
        String apellido = pedirDato("el apellido");

        try {
            controller.agregarResidente(dni, nombre, apellido);
            mostrarTexto(Mensajes.EXITO_GUARDAR);
        } catch (IllegalArgumentException e) {
            mostrarTexto("Error: " + e.getMessage());
        } catch (Exception e) {
            mostrarTexto(Mensajes.ERROR_GUARDAR);
        }
    }

    private void modificar() {
        Mostrar.Titulo("Modificar Residente");
        
        String id = pedirDato("el ID del residente a modificar (ej: R0001)");
        String nuevoDni = pedirDato("el NUEVO DNI (8 dígitos)");
        String nuevoNombre = pedirDato("el NUEVO nombre");
        String nuevoApellido = pedirDato("el NUEVO apellido");

        try {
            controller.modificarResidente(id, nuevoDni, nuevoNombre, nuevoApellido);
            mostrarTexto(Mensajes.EXITO_ACTUALIZAR);
        } catch (IllegalArgumentException e) {
            mostrarTexto("Error: " + e.getMessage());
        } catch (Exception e) {
            mostrarTexto(Mensajes.ERROR_ACTUALIZAR);
        }
    }

    private void listar() {
        Mostrar.Titulo("Lista de Residentes");
        List<ResidenteDTO> lista = controller.obtenerTodos();
        if (lista.isEmpty()) {
            mostrarTexto(Mensajes.SIN_REGISTROS);
            return;
        }
        for (ResidenteDTO r : lista) {
            mostrarTexto("ID: " + r.getId() + " | DNI: " + r.getDni() + " | Nombre: " + r.getNombre() + " " + r.getApellido());
        }
    }

    private void eliminar() {
        Mostrar.Titulo("Eliminar Residente");
        String id = pedirDato("el ID del residente a eliminar (ej: R0001)");

        try {
            controller.eliminarResidente(id);
            mostrarTexto(Mensajes.EXITO_ELIMINAR);
        } catch (IllegalArgumentException e) {
            mostrarTexto("Error: " + e.getMessage());
        } catch (Exception e) {
            mostrarTexto(Mensajes.ERROR_ELIMINAR);
        }
    }
}