package bdd.practica;
import java.util.Scanner;
import java.lang.StringBuilder;
import bdd.practica.csv.ClienteBDD;

/**
 * Vista del prototipo CSV
 * @author TANGAMANDAPIO-TEAM
 * @version 1.0
 */
public class BDDVista {
    /* Lector para el input */
    Scanner scanner;

    /* Constructor de strings (para concatenar cadenas largas en menor tirmpo) */
    StringBuilder stringBuilder;

    /* Entidad seleccionada */
    String entidad;

    /* Bandera para realizar más acciones en el menú de interacción */
    boolean masAcciones;

    /* Cliente del consultor de la base de datos */
    ClienteBDD clienteBDD;

    /**
     * Constructor de la vista
     * @param clienteBDD Cliente de la base de datos
     */
    BDDVista(ClienteBDD clienteBDD) {
        this.clienteBDD = clienteBDD;
        scanner = new Scanner(System.in);
        stringBuilder = new StringBuilder();
        entidad = "";
        masAcciones = false;
    }

    /**
     * Muestra el menú de interacción
     */
    public void mostrar() {
        mostrarMensajeBienvenida();
        mostrarSeleccionBDD();
    }

    /**
     * Imprime el mensaje de bienvenida
     */
    private void mostrarMensajeBienvenida() {
        System.out.println("Comité Olímpico Internacional");
        System.out.println("Bienvenido, usuario.");
    }

    /**
     * Muestra la selección de la base de datos a utilizar
     */
    private void mostrarSeleccionBDD() {
        do {
            System.out.println("Por favor, escriba la opción que desea revisar, y pulse ENTER para continuar.");
            System.out.println("1. Atletas");
            System.out.println("2. Disciplinas");
            System.out.println("3. Entrenadores");
            System.out.println("0. Salir");
            if (!checarEntidad(scanner.nextLine()))
                continue;
            clienteBDD.cargarBaseDeDatos(entidad);
            if (clienteBDD.getBaseDeDatosCargada())
                break;
            System.out.println("No se ha podido cargar la base de datos. Intente de nuevo.");
            
        } while (!clienteBDD.getBaseDeDatosCargada());
        System.out.println("Cargada con éxito.");
        mostrarMenuAcciones();
    }

    /**
     * Checa la entidad seleccionada y la asigna a la variable entidad
     * @param input Input del usuario (palabra o número)
     * @return Si la entidad es válida
     */
    private boolean checarEntidad(String input) {
        boolean respuestaValida = false;
        input = input.toLowerCase();
        switch (input) {
            case "atletas":
            case "1":
                entidad = BDDConstrains.ATLETAS.toString();
                respuestaValida = true;
                break;
            case "disciplinas":
            case "2":
                entidad = BDDConstrains.DISCIPLINAS.toString();
                respuestaValida = true;
                break;
            case "entrenadores":
            case "3":
                entidad = BDDConstrains.ENTRENADORES.toString();
                respuestaValida = true;
                break;
            case "salir":
            case "0":
                terminar();
                break;
            default:
                System.out.println("Opción inválida. Escriba el nombre de la entidad o el número correspondiente.");
                break;
        }
        return respuestaValida;
    }

    /**
     * Muestra el menú de acciones
     */
    private void mostrarMenuAcciones() {
        do {
            System.out.println("Por favor, escriba la acción que desee realizar, y pulse ENTER para continuar.");
            System.out.println("1. Editar");
            System.out.println("2. Agregar");
            System.out.println("3. Consultar");
            System.out.println("4. Eliminar");
            System.out.println("5. imprimir");
            System.out.println("9. Cambiar (de entidad. Actual: " + entidad + ")");
            System.out.println("0. Salir");
            if (!realizarAccion(scanner.nextLine()))
                continue;
            realizarMasAcciones();
        } while (!masAcciones);
        terminar();
    }

    /**
     * Realiza la acción seleccionada
     * @param input Input del usuario (palabra o número)
     * @return Si la acción es válida
     */
    private boolean realizarAccion(String input) {
        boolean respuestaValida = false;
        input = input.toLowerCase();
        switch (input) {
            case "editar":
            case "1":
                respuestaValida = true;
                editar();
                break;
            case "agregar":
            case "2":
                respuestaValida = true;
                agregar();
                break;
            case "consultar":
            case "3":
                respuestaValida = true;
                consultar();
                break;
            case "eliminar":
            case "4":
                respuestaValida = true;
                eliminar();
                break;
            case "imprimir":
            case "5":
                respuestaValida = true;
                clienteBDD.imprimir();
                break;
            case "cambiar":
            case "9":
                mostrarSeleccionBDD();
                break;
            case "salir":
            case "0":
                terminar();
                break;
            default:
                System.out.println("Opción inválida. Escriba el nombre de la entidad o el número correspondiente.");
                break;
        }
        return respuestaValida;
    }

    /**
     * Edita una instancia a buscar de la entidad seleccionada
     */
    private void editar() {
        System.out.println("Editar");
        boolean otroEdit = false;

        do {
            String[] resultadoConsulta = consultaPorLlave();
            if (resultadoConsulta != null) {
                imprimirResultadoConsulta(resultadoConsulta);
            System.out.println("¿Desea editar esta instancia? (S/N)");
                if (scanner.nextLine().equalsIgnoreCase("S")) {
                    editarInstancia(resultadoConsulta);
                } else
                    System.out.println("Operación cancelada.");
            } else
                System.out.println("No se encontró la instancia con el identificador proporcionado.");

            System.out.println("¿Desea realizar otra edición? (S/N)");
            otroEdit = scanner.nextLine().equalsIgnoreCase("S");
        } while (otroEdit);
    }

    /**
     * Agrega una instancia a la entidad seleccionada
     */
    private void agregar() {
        System.out.println("Agregar");
        boolean otroAdd = false;
        do {
            agregarInstancia(getAtributos());
            System.out.println("¿Desea agregar otra instancia? (S/N)");
            otroAdd = scanner.nextLine().equalsIgnoreCase("S");
        } while (otroAdd);
    }

    /**
     * Consulta una instancia de la entidad seleccionada
     */	
    private void consultar() {
        System.out.println("Consultar");
        boolean otraElim = false;
        do {
        String[] resultadoConsulta = consultaPorLlave();

            if (resultadoConsulta != null)
                imprimirResultadoConsulta(resultadoConsulta);
            else
                System.out.println("No se encontró la instancia con el identificador proporcionado.");
            System.out.println("¿Desea realizar otra consulta? (S/N)");
            otraElim = scanner.nextLine().equalsIgnoreCase("S");
        } while (otraElim);
    }

    /**
     * Elimina una instancia a buscar de la entidad seleccionada
     */
    private void eliminar() {
        System.out.println("Eliminar");
        boolean otraConsulta = false;
        do {
            String[] resultadoConsulta = consultaPorLlave();

            if (resultadoConsulta != null)
                eliminarPorLlave(resultadoConsulta);
            else
                System.out.println("No se encontró la instancia con el identificador proporcionado.");
            System.out.println("¿Desea eliminar otra entidad? (S/N)");
            otraConsulta = scanner.nextLine().equalsIgnoreCase("S");
        } while (otraConsulta);
    }

    /**
     * Edita una instancia ya encontrada de la entidad seleccionada
     * @param resultadoConsulta Instancia a editar
     */
    private void editarInstancia(String[] resultadoConsulta) {
        String[] atributos = getAtributos();
        String[] valores = new String[atributos.length];

        System.out.println("Ingrese los nuevos valores de la instancia para cada atributo.");
        stringBuilder.setLength(0);
        for (int i = 1; i < atributos.length; i++) {
            String atributo = atributos[i] + ": ";
            System.out.println(atributo);
            System.out.println("Valor actual: " + resultadoConsulta[i]);
            System.out.println("¿Desea cambiar este valor? (S/N)");
            if (scanner.nextLine().equalsIgnoreCase("S")) {
                System.out.println("Nuevo valor: ");
                valores[i] = scanner.nextLine();
            } else
                valores[i] = resultadoConsulta[i];
            stringBuilder.append(atributo + valores[i]+" ");

            System.out.println("");
        }
        System.out.println("¿Está seguro de que desea editar la instancia con los valores proporcionados? (S/N)");
        System.out.println(stringBuilder.toString());
        if (scanner.nextLine().equalsIgnoreCase("S")) {
            try {
                clienteBDD.editarInstancia(resultadoConsulta[0], valores);
                System.out.println("Instancia editada con éxito.");
            } catch (TiposExcepcion e) {
                System.out.println(e.getMessage()); 
                System.out.println("vuelve a intentar");
            }
            
        } else
            System.out.println("Operación cancelada.");
    }

    /**
     * Elimina una instancia ya encontrada de la entidad seleccionada
     * @param resultadoConsulta Instancia a eliminar
     */
    private void eliminarPorLlave(String[] resultadoConsulta) {

        System.out.println("¿Está seguro de que desea eliminar la instancia con el identificador" + resultadoConsulta[0] + "? (S/N)");
        if (scanner.nextLine().equalsIgnoreCase("S")) {
            clienteBDD.eliminarPorLlave(resultadoConsulta[0]);
            System.out.println("Instancia eliminada con éxito.");
            return;
        }
        System.out.println("Operación cancelada.");
    }

    /**
     * Obtiene los atributos de la entidad seleccionada
     * @return Atributos de la entidad
     */
    private String[] getAtributos() {
        return clienteBDD.getAtributos();
    }

    /**
     * Agrega una instancia a la entidad seleccionada
     * @param atributos Atributos de la entidad
     */
    private void agregarInstancia(String[] atributos) {
        String[] valores = new String[atributos.length];
        System.out.println("Ingrese los valores de la instancia para cada atributo.");
        for (int i = 1; i < atributos.length; i++) {
            String atributo = atributos[i] + ": ";
            System.out.println(atributo);
            valores[i] = scanner.nextLine();
            stringBuilder.append(atributo + valores[i]);
            System.out.println("");
        }
        System.out.println("¿Está seguro de que desea agregar la instancia con los valores proporcionados? (S/N)");
        System.out.println(stringBuilder.toString());
        if (scanner.nextLine().equalsIgnoreCase("S")) {
            try {
                clienteBDD.agregarInstancia(valores);
                System.out.println("Instancia editada con éxito.");
            } catch (TiposExcepcion e) {
                System.out.println(e.getMessage());
                System.out.println("vuelve a intentar");
            }
            
        } else
            System.out.println("Operación cancelada.");
    }

    /**
     * Consulta una instancia con su identificador de la entidad seleccionada
     * @return Resultado de la consulta. <code>null</code> si no se encontró la instancia
     */
    private String[] consultaPorLlave() {
        System.out.println("Ingrese el identificador de la instancia que desea consultar.");
        return clienteBDD.consultarPorLlave(scanner.nextLine());
    }
    
    /**
     * Imprime el resultado de la consulta
     * @param resultado Resultado de la consulta
     */
    private void imprimirResultadoConsulta(String[] resultado) {
        stringBuilder.setLength(0);
        for (String s : resultado) {
            stringBuilder.append(s);
            stringBuilder.append(" ");
        }
        System.out.println(stringBuilder.toString());
    }
    
    /**
     * Pregunta si se desea realizar más acciones
     */	
    private void realizarMasAcciones() {
        System.out.println("¿Desea realizar otra acción? (S/N)");
        masAcciones = !scanner.nextLine().equalsIgnoreCase("S");
    }
    
    /**
     * Termina la ejecución del programa
     */
    private void terminar() {
        scanner.close();
        stringBuilder = null;
        System.out.println("Gracias por usar el sistema. Hasta luego.");
        System.exit(0);
    }

}
