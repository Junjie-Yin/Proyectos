package CITIM21_09.controller;

import CITIM21_09.model.Jugador;

import java.util.List;

public interface ICJugador {

    Jugador getJugador(String ldapId);
    boolean nombreUsable(String username);
    void crearJugador(Jugador jugador);
    void eliminar(Jugador jugador);
    List<Jugador> getJugadores();
    void actualizarPuntuacionesJugador(Jugador jugador);

}
