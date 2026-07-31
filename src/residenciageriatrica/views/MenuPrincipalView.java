package residenciageriatrica.views;

import utils.Mostrar;
import java.util.Scanner;
import utils.Mensajes;

public class MenuPrincipalView {

    private final Scanner scanner;

    public MenuPrincipalView() {
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        int opcion;

        do {
            String menuTexto = "\n=== SISTEMA DE GESTIÓN GERIÁTRICA ===\n"
                    + "1. Gestionar habitaciones\n"
                    + "2. Gestionar reservas\n"
                    + "3. Gestionar residentes\n"
                    + "4. Gestionar enfermeros\n"
                    + "5. Gestionar asignaciones\n"
                    + "6. Consultas\n"
                    + "0. Salir";
            opcion = Mostrar.Menu(menuTexto, scanner);

            switch (opcion) {
                case 1 ->
                    new HabitacionView(scanner).mostrarMenu();

                case 2 ->
                    new ReservaView(scanner).mostrarMenu();

                case 3 ->
                    new ResidenteView(scanner).mostrarMenu();

                case 4 ->
                    new EnfermeroView(scanner).mostrarMenu();

                case 5 ->
                    new AsignacionView(scanner).mostrarMenu();
                
                case 6 ->
                    new ConsultasView(scanner).mostrarMenu();

                case 0 ->
                    Mostrar.Mensaje(Mensajes.SALIENDO);

                default ->
                    Mostrar.Mensaje(Mensajes.OPCION_INVALIDA);
            }

        } while (opcion != 0);
    }

}
