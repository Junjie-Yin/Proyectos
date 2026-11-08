package CITIM21_09.controller;

import CITIM21_09.model.Jugador;
import CITIM21_09.model.JugadorHumano;
import CITIM21_09.model.Menu;
import CITIM21_09.view.vistaComandos.ComandosLogin.DarBaja;
import CITIM21_09.view.vistaComandos.ComandosLogin.Jugar;
import CITIM21_09.view.vistaComandos.ComandosLogin.Logout;
import CITIM21_09.view.vistaComandos.ComandosLogin.VerPuntuacion;

import CITIM21_09.view.vistaComandos.ComandosPublicos.ExitVista;
import CITIM21_09.view.vistaComandos.ComandosPublicos.LDAP;
import CITIM21_09.view.vistaComandos.ComandosPublicos.RSS;
import es.upm.etsisi.fis.controller.ControladorPartida;
import es.upm.etsisi.fis.model.IMovimiento;
import es.upm.etsisi.fis.model.IPuntuacion;

import java.util.Random;
import java.util.Scanner;

public class SesionControlador {

    private static SesionControlador instance;
    private final Menu menu;
    private ICJugador cJugador;
    private Jugador jugadoract;
    private final Scanner scanner;

    private SesionControlador() {
        cJugador = new CJugador();
        scanner = new Scanner(System.in);
        jugadoract = null;
        this.menu = new Menu();
    }

    public static SesionControlador getInstance() {
        if (instance == null) {
            return instance = new SesionControlador();
        } else {
            return instance;
        }
    }

    public void start() {
        menu.addOptions(new LDAP());
        menu.addOptions(new RSS());
        menu.addOptions(new ExitVista());
        menu.run();
    }


    public Jugador getJugadoract() {
        return this.jugadoract;
    }

    public void login(String credencial) {
        if (credencial != null) {
            Jugador jugador = cJugador.getJugador(credencial);
            if (jugador == null) {
                System.out.print("Nombre de usuario: ");
                String nombre = scanner.nextLine();
                while (!cJugador.nombreUsable(nombre)) {
                    System.out.println("No se puede usar este nombre introduce de nuevo: ");
                    nombre = scanner.nextLine();
                }
                this.jugadoract = new JugadorHumano(nombre, credencial);
                cJugador.crearJugador(jugadoract);
            } else {
                this.jugadoract = jugador;
            }
            logged();
        }
    }

    private void logged() {
        System.out.println("Bienvenido " + this.jugadoract.getNombre());
        menu.removeOptions();
        menu.addOptions(new Jugar());
        menu.addOptions(new VerPuntuacion());
        if(!jugadoract.isAdmin()) menu.addOptions(new DarBaja());
        menu.addOptions(new Logout());
        menu.addOptions(new ExitVista());
    }

    public void logout() {
        this.jugadoract = null;
        menu.restoreOptions();
    }

    public void exit() {
        menu.exitRequest();
    }

    public void jugar() {
        if (jugadoract == null) {
            System.out.println("Debes iniciar sesión para jugar.");
            return;
        }
        Long a = new Random().nextLong();
        IPuntuacion puntuacion = jugadoract.getNewPuntuacion(a);
        IMovimiento movimientos = jugadoract.getNewMovimientos(a);

        System.out.println("Iniciando partida para " + jugadoract.getNombre() + "...");
        ControladorPartida.getInstance(scanner).crearPartida(jugadoract, puntuacion, movimientos);
        System.out.println("Partida finalizada para " + jugadoract.getNombre() + ".");

        cJugador.actualizarPuntuacionesJugador(jugadoract);
    }

    public void darBaja() {
        cJugador.eliminar(this.jugadoract);
        logout();
    }

    public void verPuntuacion(){
        jugadoract.abstractverPuntos(cJugador.getJugadores());
    }

}
