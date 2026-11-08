package CITIM21_09;


import junit.framework.TestCase;
import CITIM21_09.controller.CJugador;
import CITIM21_09.model.Jugador;
import CITIM21_09.model.JugadorHumano;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class CJugadorTest extends TestCase {


    private CJugador controlador;


    protected void setUp() throws Exception {
        super.setUp();
        controlador = new CJugador();


        List<Jugador> jugadores = new ArrayList<>();
        jugadores.add(new JugadorHumano("pepe69", "cred1")); // nombre ya en uso
        jugadores.add(new JugadorHumano("calendar", "cred2"));
        controlador.setJugadores(jugadores);
    }


    public void testCP1_V1V2V3() {
        assertTrue(controlador.nombreUsable("jos"));
        System.out.println("jos"+" aceptado");
    }


    public void testCP2_V1V2N4() {
        assertFalse(controlador.nombreUsable("pepe69")); // en uso
        System.out.println("pepe69"+" nombre ya existe");


    }


    public void testCP3_V1N3V3() {
        assertFalse(controlador.nombreUsable("?%”%/&(__.")); // caracteres inválidos
        System.out.println("?%<UNK>%/&(__. "+" caracteres invalidos");


    }


    public void testCP4_N1V2V3() {
        assertFalse(controlador.nombreUsable("A")); // longitud inválida
        System.out.println("A"+" longitud infeior a 3");
    }


    public void testCP5_N2V2V3() {
        assertFalse(controlador.nombreUsable("fuhdfjwkadfop")); // longitud inválida
        System.out.println("fuhdfjwkadfop"+" longitud superior a 10");

    }

    public void testEliminarJugador() {
        assertFalse(controlador.nombreUsable("pepe69"));

        Jugador jugador = controlador.getJugadorPorNombreJuego("pepe69");
        assertNotNull("Jugador no encontrado antes de eliminar", jugador);

        controlador.eliminar(jugador);

        assertTrue("El nombre debería estar disponible tras eliminar", controlador.nombreUsable("pepe69"));

        boolean sigueEnArchivo = false;
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/Users/datos.txt"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (linea.toLowerCase().startsWith("pepe69;")) {
                    sigueEnArchivo = true;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            fail("Error al leer el archivo de jugadores");
        }

        assertFalse("Jugador aún presente en el archivo", sigueEnArchivo);

        System.out.println("Test de eliminación completado correctamente.");
    }

}







