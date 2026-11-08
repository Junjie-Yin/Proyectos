package CITIM21_09.model;

import es.upm.etsisi.fis.model.IPuntuacion;

import java.util.List;

public class Admin extends Jugador {

    public Admin(String nombre, String id) {
        super(nombre, id);
    }

    @Override
    public void abstractverPuntos(List<Jugador> jugadores) {
        System.out.println("Puntuaciones TOP 10 por jugador (vistas por Admin): "+ this.getNombre());

        for (Jugador jugador : jugadores) {
            if( !jugador.getId().equals(getId())){
                System.out.println("Jugador: " + jugador.getNombre());
                List<IPuntuacion> puntuaciones = jugador.getPuntuaciones();

                if (puntuaciones.isEmpty()) {
                    System.out.println("  No tiene puntuaciones registradas.");
                    continue;
                }
                List<IPuntuacion> top10 = getTop10Puntuacion(puntuaciones);
                for (IPuntuacion p : top10) {
                    System.out.println("  " + p.toString());
                }
                System.out.println("\n");
            }
        }
    }


    @Override
    public boolean isAdmin() {
        return true;
    }


}
