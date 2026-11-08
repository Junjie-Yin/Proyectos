package CITIM21_09.Persistencia;

import CITIM21_09.model.Jugador;
import es.upm.etsisi.fis.model.IPuntuacion;

import java.util.List;

public interface IJugadorPersistencia {
    List<Jugador> cargarTodosLosJugadores();

    void guardarTodosLosJugadores(List<Jugador> jugadores);

    void guardarPuntuacionesDeUnJugador(String nombreJugador, List<IPuntuacion> puntuaciones);

    void eliminarArchivoPuntuaciones(String nombreJugador);
}
