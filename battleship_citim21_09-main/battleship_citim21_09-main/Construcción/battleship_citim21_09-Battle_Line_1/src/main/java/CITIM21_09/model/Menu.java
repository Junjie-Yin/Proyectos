package CITIM21_09.model;

import CITIM21_09.view.MenuVista;
import CITIM21_09.view.vistaComandos.Command;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {

    private final List<Command> options;
    private List<Command> originalOptions;
    private final MenuVista vista;
    private boolean exit;


    public Menu() {
        options = new ArrayList<>();
        vista = new MenuVista();
        exit = false;
    }

    public void run() {
        int opcion;
        boolean ok;
        Scanner scanner = new Scanner(System.in);
        vista.printMenu();

        do {
            System.out.print("Seleccione una opción: \n");
            vista.printOpcion(this.options);
            opcion = scanner.nextInt();
            ok = 0 <= opcion && opcion <= this.options.size();

            if (!ok) {
                System.out.println("Numero introducido erroneo");
            } else {
                this.options.get(opcion - 1).execute();
            }
        } while (!exit);

        System.out.println("Saliendo...");
    }

    public void addOptions(Command command) {  //Rellenar comando
        this.options.add(command);
    }

    public void restoreOptions() {
        if (originalOptions != null) {
            options.clear();
            options.addAll(originalOptions);
            originalOptions = null;
        }
    }

    public void removeOptions() {
        originalOptions = new ArrayList<>(options);
        options.clear();
    }


    public void exitRequest() {
        this.exit = true;
    }


}



