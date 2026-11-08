package CITIM21_09.view.vistaComandos.ComandosPublicos;

import CITIM21_09.view.vistaComandos.Command;

public class RSS implements Command {

    private final String name;


    public RSS() {
        name = "RSS";
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void execute() {
        System.out.println(getName() + " Funcion en mantenimiento: ");
    }

}
