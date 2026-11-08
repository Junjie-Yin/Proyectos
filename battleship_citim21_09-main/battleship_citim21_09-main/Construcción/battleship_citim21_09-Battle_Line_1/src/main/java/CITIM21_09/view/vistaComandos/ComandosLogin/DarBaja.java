package CITIM21_09.view.vistaComandos.ComandosLogin;

import CITIM21_09.controller.SesionControlador;
import CITIM21_09.view.vistaComandos.Command;

public class DarBaja implements Command {

    private final String nombre;


    public DarBaja() {
        this.nombre = "Dar de baja ";
    }

    @Override
    public String getName() {
        return nombre;
    }

    @Override
    public void execute() {
        SesionControlador.getInstance().darBaja();
    }
}
