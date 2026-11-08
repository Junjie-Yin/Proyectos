package CITIM21_09.controller;

import CITIM21_09.Persistencia.IJugadorPersistencia;
import CITIM21_09.Persistencia.JugadorPersistencia;
import CITIM21_09.model.Admin;
import CITIM21_09.model.Jugador;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CJugador implements ICJugador{

    private final static String RUTA_LISTA_NEGRA = "src/main/resources/Users/list_raw.txt";
    private static final List<String> LISTA_NEGRA = cargarListaNegra();
    private List<Jugador> jugadores;
    private IJugadorPersistencia persistencia;

    public CJugador() {
        this.persistencia = new JugadorPersistencia();
        this.jugadores = new ArrayList<>(this.persistencia.cargarTodosLosJugadores());

    }

    private static List<String> cargarListaNegra() {
        List<String> lista = new ArrayList<>();
        File listaNegra = new File(RUTA_LISTA_NEGRA);

        try (BufferedReader reader = new BufferedReader(new FileReader(listaNegra))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                linea = linea.trim();
                if (!linea.isEmpty() && !linea.startsWith("#")) lista.add(linea.toLowerCase());
            }
        } catch (IOException e) {
            System.err.println("Error al cargar la lista negra: " + e.getMessage());
        }
        return lista;
    }
    @Override
    public Jugador getJugador(String ldapId) {
        for (Jugador jugador : this.jugadores)
            if (jugador.getId().equals(ldapId))
                return jugador;
        return null;
    }

    public Jugador getJugadorPorNombreJuego(String username) {
        for (Jugador jugador : this.jugadores)
            if (jugador.getNombre().equalsIgnoreCase(username))
                return jugador;
        return null;
    }

    @Override
    public List<Jugador> getJugadores() {
        return new ArrayList<>(this.jugadores);
    }

    public void setJugadores(List<Jugador> jugadores) {
        this.jugadores = jugadores;
    }
    @Override
    public boolean nombreUsable(String username) {
        if (username == null || username.trim().isEmpty()) {
            System.out.println("El nombre de usuario no puede estar vacío.");
            return false;
        }
        username = username.trim();

        if (username.length() < 3 || username.length() > 10 || !username.matches("[a-zA-Z0-9]+")) {
            System.out.println("Nombre de usuario para el juego no válido. Debe tener entre 3-10 caracteres alfanuméricos.");
            return false;
        }
        if (LISTA_NEGRA.contains(username.toLowerCase())) {
            System.out.println("El nombre '" + username + "' no está permitido (lista negra).");
            return false;
        }
        if (getJugadorPorNombreJuego(username) != null) {
            System.out.println("El nombre de juego '" + username + "' ya está en uso.");
            return false;
        }
        return true;
    }

    @Override
    public void crearJugador(Jugador jugador) {
        if (jugador instanceof Admin) {
            System.err.println("Error: Intento de crear un cuenta con permisos altos.");
            return;
        }
        this.jugadores.add(jugador);
        this.persistencia.guardarTodosLosJugadores(this.jugadores);
        System.out.println("Jugador '" + jugador.getNombre() + "' registrado.");
    }

    @Override
    public void eliminar(Jugador jugador) {
        if (jugador == null) return;
        if (jugador.isAdmin()) {
            System.out.println("Acción no permitida: Los administradores no pueden ser eliminados.");
            return;
        }

        boolean removed = this.jugadores.remove(jugador);
        if (removed) {
            this.persistencia.guardarTodosLosJugadores(this.jugadores);
            this.persistencia.eliminarArchivoPuntuaciones(jugador.getNombre());
            System.out.println("Jugador '" + jugador.getNombre() + "' eliminado.");
        } else System.out.println("Jugador '" + jugador.getNombre() + "' no encontrado en la lista para eliminar.");
    }

    @Override
    public void actualizarPuntuacionesJugador(Jugador jugador) {
        if (jugador == null) return;
        this.persistencia.guardarPuntuacionesDeUnJugador(jugador.getNombre(), jugador.getPuntuaciones());
        //DEBUG
        System.out.println("INFO: Intento de guardar puntuaciones para " + jugador.getNombre() + " completado.");
        //
    }

}
