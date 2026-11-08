package CITIM21_09.Persistencia;

import CITIM21_09.model.Admin;
import CITIM21_09.model.Jugador;
import CITIM21_09.model.JugadorHumano;
import CITIM21_09.model.Puntuacion;
import es.upm.etsisi.fis.model.IPuntuacion;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class JugadorPersistencia implements IJugadorPersistencia {

    private static final String RUTA_DATOS = "src/main/resources/Users/datos.txt";
    private static final String RUTA_PUNTUACIONES_BASE = "src/main/resources/";


    public JugadorPersistencia() {
    }

    @Override
    public List<Jugador> cargarTodosLosJugadores() {
        List<Jugador> jugadores = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(RUTA_DATOS))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty() || linea.startsWith("#")) continue;

                String[] partes = linea.split(" ");
                if (partes.length >= 2) {
                    String nombre = partes[0];
                    String ldapId = partes[1];
                    Jugador nuevoJugador;

                    if (partes.length == 3 && "ADMIN".equalsIgnoreCase(partes[2].trim()))
                        nuevoJugador = new Admin(nombre, ldapId);
                    else nuevoJugador = new JugadorHumano(nombre, ldapId);
                    cargarPuntuacionesJugador(nuevoJugador);
                    jugadores.add(nuevoJugador);
                } else System.err.println("Línea con formato incorrecto en " + RUTA_DATOS + ": '" + linea + "'");
            }
        } catch (FileNotFoundException e) {
            // Lista vacía
        } catch (IOException e) {
            System.err.println("Error al cargar jugadores desde " + RUTA_DATOS + ": " + e.getMessage());
        }
        return jugadores;
    }

    @Override
    public void guardarTodosLosJugadores(List<Jugador> jugadores) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RUTA_DATOS, false))) {
            for (Jugador jugador : jugadores) {
                writer.write(jugador.getNombre() + " " + jugador.getId());
                if (jugador.isAdmin()) {
                    writer.write(" ADMIN");
                }
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al guardar jugadores en " + RUTA_DATOS + ": " + e.getMessage());
        }
    }

    private void cargarPuntuacionesJugador(Jugador jugador) {
        List<IPuntuacion> loadedPuntuaciones = new ArrayList<>();
        String puntuacionFilePath = RUTA_PUNTUACIONES_BASE + jugador.getNombre() + "_puntuaciones.txt";
        long contPartidaId = 1;
        try (BufferedReader br = new BufferedReader(new FileReader(puntuacionFilePath))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty() || linea.startsWith("#")) {
                    continue;
                }
                String playerIdentifierPrefix = jugador.getNombre() + "puntuaciones";

                if (linea.startsWith(playerIdentifierPrefix)) {
                    String scoresSubString = null;
                    int scoresStartIndex = -1;
                    for (int i = playerIdentifierPrefix.length(); i < linea.length(); i++) {
                        if (Character.isDigit(linea.charAt(i)) || (linea.charAt(i) == '-' && i + 1 < linea.length() &&
                                Character.isDigit(linea.charAt(i + 1)))) {
                            scoresStartIndex = i;
                            break;
                        }
                    }

                    if (scoresStartIndex != -1) {
                        scoresSubString = linea.substring(scoresStartIndex);
                        String[] scoreValues = scoresSubString.trim().split("\\s+");
                        for (String scoreStr : scoreValues) {
                            if (scoreStr.isEmpty()) continue;
                            try {
                                long puntos = Long.parseLong(scoreStr);
                                loadedPuntuaciones.add(new Puntuacion(contPartidaId++, puntos));
                            } catch (NumberFormatException nfe) {
                                System.err.println("Error de formato en un valor de puntuación para " + jugador.getNombre() +
                                        ": '" + scoreStr + "' en línea '" + linea + "'");
                            }
                        }
                    } else
                        System.err.println("No se encontraron puntuaciones numéricas en la línea formateada para " + jugador.getNombre() + ": '" + linea + "'");
                    break;
                }
            }
        } catch (FileNotFoundException e_fnf) {
            // vacio
        } catch (IOException e) {
            System.err.println("Error al leer el archivo de puntuaciones para " + jugador.getNombre() + ": " + e.getMessage());
        }
        jugador.setPuntuacionesList(loadedPuntuaciones);
    }

    @Override
    public void guardarPuntuacionesDeUnJugador(String nombreJugador, List<IPuntuacion> puntuaciones) {
        String scoreFilePath = RUTA_PUNTUACIONES_BASE + nombreJugador + "_puntuaciones.txt";
        //PARA DEBUG
        System.out.println("DEBUG: Guardando puntuaciones en: " + scoreFilePath);
        System.out.println("DEBUG: Puntuaciones a guardar para " + nombreJugador + ": " + puntuaciones.size() + " entradas.");
        for(IPuntuacion pDebug : puntuaciones) {
            System.out.println("DEBUG:   - " + pDebug.toString());
        }
        //

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(scoreFilePath, false))) {
            bw.write("#Datos de puntuaciones");
            bw.newLine();
            String exampleFormatComment = "# " + nombreJugador + " puntuaciones ...................... S1 S2 S3... formato";
            bw.write(exampleFormatComment);
            bw.newLine();
            bw.write("#");
            bw.newLine();

            StringJoiner scoresJoiner = new StringJoiner(" ");
            for (IPuntuacion p : puntuaciones) scoresJoiner.add(String.valueOf(p.getPuntos()));

            String prefix = nombreJugador + "puntuaciones ...................... ";
            String lineToWrite = prefix + scoresJoiner.toString();
            System.out.println("DEBUG: Línea a escribir: " + lineToWrite); //DEBUG
            bw.write(lineToWrite);
            bw.newLine();
            System.out.println("DEBUG: Puntuaciones guardadas exitosamente para " + nombreJugador); // DEBUG
        } catch (IOException e) {
            System.err.println("Error al guardar puntuaciones para " + nombreJugador + " en " + scoreFilePath + ": " + e.getMessage());
            e.printStackTrace(); // DEBUG
        }
    }

    @Override
    public void eliminarArchivoPuntuaciones(String nombreJugador) {
        File scoreFile = new File(RUTA_PUNTUACIONES_BASE + nombreJugador + "_puntuaciones.txt");
        try {
            if (scoreFile.exists())
                if (!scoreFile.delete())
                    System.err.println("Error: No se pudo eliminar el archivo de puntuaciones para '"
                            + nombreJugador + "'.");
        } catch (SecurityException se) {
            System.err.println("Error de seguridad al intentar eliminar el archivo de puntuaciones para '" +
                    nombreJugador + "': " + se.getMessage());
        }
    }

}
