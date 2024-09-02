package bdd.practica;

import java.io.IOException;
import bdd.practica.csv.ClienteBDD;
/**
 * Hello world!
 */
public final class App {
    private App() {
    }

    /**
     * Says hello to the world.
     * @param args The arguments of the program.
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        ClienteBDD bdd = new ClienteBDD();
        //bdd.cargarBaseDeDatos("ENTRENADORES");
        BDDVista base = new BDDVista(bdd);
        base.mostrar();

    }
}
