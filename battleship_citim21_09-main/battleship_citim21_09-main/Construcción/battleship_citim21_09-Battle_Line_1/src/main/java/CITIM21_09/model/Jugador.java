package CITIM21_09.model;

import es.upm.etsisi.fis.model.IJugador;
import es.upm.etsisi.fis.model.IMovimiento;
import es.upm.etsisi.fis.model.IPuntuacion;
import es.upm.etsisi.fis.model.TBarcoAccionComplementaria;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public abstract class Jugador implements IJugador {

    private final String id;
    private final String nombre;
    private List<IMovimiento> movimientos;
    private List<IPuntuacion> puntuaciones;

    public Jugador(String nombre, String credencial) {
        this.id = credencial;
        this.nombre = nombre;
        movimientos = new ArrayList<>();
        puntuaciones = new ArrayList<>();
    }

    public abstract void abstractverPuntos(List<Jugador> jugadors);

    public abstract boolean isAdmin();

    public IPuntuacion getNewPuntuacion(long id) {
        Puntuacion a = new Puntuacion(id, 0);
        return a;
    }

    public IMovimiento getNewMovimientos(long id) {   //prueba
        Movimientos b = new Movimientos(id, 1, 2, 2);
        return b;
    }

    public String getId() {
        return this.id;
    }

    @Override
    public boolean aceptarAccionComplementaria(TBarcoAccionComplementaria tBarcoAccionComplementaria, int i) {
        boolean res = false;
        if (i > 0) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Se puede activar la habilidad especial del barco " + tBarcoAccionComplementaria.toString()
                    + ". ¿Quieres utilizarla ahora? (si/no): ");
            String sol = scanner.nextLine();
            return sol.equalsIgnoreCase("si");
        }
        return res;
    }

    @Override
    public int[] realizaTurno(char[][] tablero) {
        int[] ab = new int[2];
        Scanner scanner = new Scanner(System.in);
        System.out.println("Fila(x):");
        ab[0] = dentroRango(scanner);
        System.out.println("Columna(y):");
        ab[1] = dentroRango(scanner);
        return ab;
    }

    private int dentroRango(Scanner scanner) {
        while (true) {
            try {
                int valor = Integer.parseInt(scanner.nextLine());
                if (valor >= 0 && valor < 10)
                    return valor;
                else System.out.println("El valor debe estar entre 0 y 9:");
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Se tiene que introducir un número entre 0 y 9:");
            }
        }
    }

    @Override
    public void addMovimiento(IMovimiento iMovimiento) {
        if (iMovimiento != null) {
            this.movimientos.add(iMovimiento.cloneMovimiento());
        }
    }

    @Override
    public void addPuntuacion(IPuntuacion iPuntuacion) {
        if (iPuntuacion != null) this.puntuaciones.add(iPuntuacion);
    }

    @Override
    public String getNombre() {
        return this.nombre;
    }

    @Override
    public List<IMovimiento> getMovimientos() {
        return this.movimientos;
    }

    @Override
    public List<IPuntuacion> getPuntuaciones() {
        return this.puntuaciones;
    }


    public void setPuntuacionesList(List<IPuntuacion> loadedPuntuaciones) {
        if (loadedPuntuaciones != null)
            this.puntuaciones = new ArrayList<>(loadedPuntuaciones);
        else this.puntuaciones = new ArrayList<>();
    }

    protected List<IPuntuacion> getTop10Puntuacion(List<IPuntuacion> p) {
        return p.stream()
                .sorted(Comparator.comparingLong(IPuntuacion::getPuntos).reversed())
                .limit(10)
                .collect(Collectors.toList());
    }


}
