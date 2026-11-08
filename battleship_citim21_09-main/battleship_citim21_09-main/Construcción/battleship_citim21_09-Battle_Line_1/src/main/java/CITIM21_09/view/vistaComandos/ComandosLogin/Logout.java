package CITIM21_09.view.vistaComandos.ComandosLogin;

import CITIM21_09.controller.SesionControlador;
import CITIM21_09.view.vistaComandos.Command;

public class Logout implements Command {

    private final String name;


    public Logout() {
        this.name = "Logout";
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void execute() {
        SesionControlador.getInstance().logout();
    }
}
