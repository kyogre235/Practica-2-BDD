package bdd.practica;

public class TiposExcepcion extends Exception {

    // Constructor sin argumentos
    public TiposExcepcion() {
        super();
    }

    // Constructor que acepta un mensaje
    public TiposExcepcion(String mensaje) {
        super(mensaje);
    }
}