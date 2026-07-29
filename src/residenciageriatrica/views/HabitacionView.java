package residenciageriatrica.views;


import residenciageriatrica.controllers.HabitacionController;
import residenciageriatrica.dtos.HabitacionDTO;
import utils.Mensajes;
import utils.Mostrar;

import java.util.List;
import java.util.Scanner;

public class HabitacionView {

    private HabitacionController controller;
    private Scanner scanner;

    public HabitacionView(Scanner scanner) {
        this.controller = new HabitacionController();
        this.scanner = scanner;
    }

    public void mostrarMenu() {
        int opcion;
        String menuTexto = "\n---- GESTIÓN DE HABITACIONES ----\n" +
                           "1. Agregar Habitación\n" +
                           "2. Listar Habitaciones\n" +
                           "3. Eliminar Habitación\n" +
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
                    Mostrar.Mensaje(Mensajes.VOLVIENDO);
                    break;
                default:
                    Mostrar.Mensaje(Mensajes.OPCION_INVALIDA);
            }
        } while (opcion != 0);
    }

    private void agregar() {
        Mostrar.Titulo("Nueva Habitación");
        
        // Se ejecuta la creación automática sin pedirle datos al usuario
        boolean exito = controller.agregarHabitacion();
        if (exito) {
            Mostrar.Mensaje(Mensajes.EXITO_GUARDAR);
        } else {
            Mostrar.Mensaje(Mensajes.ERROR_GUARDAR);
        }
    }

    private void listar() {
        Mostrar.Titulo("Lista de Habitaciones");
        List<HabitacionDTO> lista = controller.obtenerTodas();
        if (lista.isEmpty()) {
            Mostrar.Mensaje(Mensajes.SIN_REGISTROS);
            return;
        }
        for (HabitacionDTO h : lista) {
            Mostrar.Mensaje("Código: " + h.getCodHabitacion() + " | Estado: " + h.getEstado());
        }
    }

    private void eliminar() {
        Mostrar.Titulo("Eliminar Habitación");
        System.out.print(Mensajes.PEDIR_DATO + "el código de la habitación a eliminar: ");
        String codigo = scanner.nextLine();

        int resultado = controller.eliminarHabitacion(codigo);
        switch (resultado) {
            case 0:
                Mostrar.Mensaje(Mensajes.EXITO_ELIMINAR);
                break;
            case 1:
                Mostrar.ErrorNoEncontrado("Habitación", codigo);
                break;
            case 2:
                Mostrar.ErrorOcupado("Habitación", codigo, "se encuentra ocupada y no se puede eliminar.");
                break;
            default:
                Mostrar.Mensaje(Mensajes.ERROR_ELIMINAR);
                break;
        }
    }
}