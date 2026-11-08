package CITIM21_09.view.vistaComandos.ComandosLogin;

import CITIM21_09.controller.SesionControlador;
import CITIM21_09.view.vistaComandos.Command;

public class Jugar implements Command {

    private final String name;


    public Jugar() {
        this.name = "Jugar Partida";
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void execute() {
        SesionControlador.getInstance().jugar();
    }
}
