package residenciageriatrica;

import residenciageriatrica.views.MenuPrincipalView;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) {
        // Forzar la salida de la consola en UTF-8
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        
        MenuPrincipalView menuPrincipal = new MenuPrincipalView();
        menuPrincipal.iniciar();
    }
}
