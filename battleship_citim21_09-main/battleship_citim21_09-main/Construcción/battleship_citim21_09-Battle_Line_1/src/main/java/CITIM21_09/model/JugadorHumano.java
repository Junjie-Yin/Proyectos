package CITIM21_09.model;

import es.upm.etsisi.fis.model.IPuntuacion;

import java.util.List;

public class JugadorHumano extends Jugador {

    public JugadorHumano(String nombre, String id) {
        super(nombre, id);
    }

    @Override
    public void abstractverPuntos(List<Jugador> jugadores) {
        if (!super.getPuntuaciones().isEmpty()) {
            List<IPuntuacion> top10 = getTop10Puntuacion(getPuntuaciones());
            for (IPuntuacion puntuacion : top10) {
                System.out.println(puntuacion.toString());
            }
        } else {
            System.out.println("No tienes Puntuaciones. Juega una Partida!!!");
        }
        System.out.println("\n");
    }

    @Override
    public boolean isAdmin() {
        return false;
    }

}
