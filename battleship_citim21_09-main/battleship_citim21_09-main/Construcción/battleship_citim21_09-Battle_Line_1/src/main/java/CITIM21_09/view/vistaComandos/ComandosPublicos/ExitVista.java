package CITIM21_09.view.vistaComandos.ComandosPublicos;

import CITIM21_09.controller.SesionControlador;
import CITIM21_09.view.vistaComandos.Command;

public class ExitVista implements Command {

    private final String name;


    public ExitVista() {
        name = "Exit";
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void execute() {
        System.out.println("exit");
        SesionControlador.getInstance().exit();
    }

}