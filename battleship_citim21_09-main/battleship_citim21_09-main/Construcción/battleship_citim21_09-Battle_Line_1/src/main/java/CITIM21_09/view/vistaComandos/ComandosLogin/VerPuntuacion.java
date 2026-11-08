package CITIM21_09.view.vistaComandos.ComandosLogin;

import CITIM21_09.controller.SesionControlador;
import CITIM21_09.model.JugadorHumano;
import CITIM21_09.view.vistaComandos.Command;

public class VerPuntuacion implements Command {

    private final String name;


    public VerPuntuacion() {

        if (SesionControlador.getInstance().getJugadoract() instanceof JugadorHumano) {
            this.name = "Ver top 10 puntuacion ";
        } else {
            this.name = "Ver puntuaciones ";
        }

    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void execute() {
        SesionControlador.getInstance().verPuntuacion();
    }
}
