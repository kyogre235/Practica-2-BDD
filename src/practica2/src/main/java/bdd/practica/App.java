package bdd.practica;

import java.io.IOException;
import bdd.practica.csv.ClienteBDD;
/**
 * Prototipo de la aplicación para CSV
 * @author TANGAMANDAPIO-TEAM
 * @version 1.0
 */
public final class App {
    /* Constructor */
    private App() {}

    /**
     * Empieza el programa.
     * @param args The arguments of the program.
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        ClienteBDD bdd = new ClienteBDD();
        BDDVista base = new BDDVista(bdd);
        base.mostrar();

    }
}
