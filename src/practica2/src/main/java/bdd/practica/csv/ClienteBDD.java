package bdd.practica.csv;

import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;
import com.opencsv.*;
import com.opencsv.exceptions.CsvValidationException;
import java.util.ArrayList;

import bdd.practica.TiposExcepcion;

/**
 * ClienteBDD
 * Clase que se encarga de la manipulacion de la base de datos.
 * @author TANGAMANDAPIO-TEAM
 * @version 1.0
 */
public class ClienteBDD {
    
    /* Tipo de entidad a revisar */
    String entidad;

    /* Archivo e información de la entidad */
    ArrayList<String[]> file;

    /* Estado del cliente */
    boolean estado;

    /* Tipos de datos de la entidad */
    ArrayList<String> tipos;

    /**
     * Constructor de la clase
     */
    public ClienteBDD() {
        entidad = "";
        file = null;
        estado = false;
        tipos = null;
    }

    /**
     * Carga la base de datos
     * @param entidad Entidad a cargar
     */
    public void cargarBaseDeDatos (String entidad){
        file = new ArrayList<String[]>();
        if (entidad.equals("ATLETAS")||entidad.equals("DISCIPLINAS")||entidad.equals("ENTRENADORES")){
            this.entidad = entidad;
            this.tipos(entidad);

            Ruta ruta = new Ruta();
            String rutaArchivos = ruta.ruta() + entidad.toLowerCase() + ".csv";
            try (CSVReader reader = new CSVReader(new FileReader(rutaArchivos))) {
                String[] linea;
                try {
                    while ((linea = reader.readNext()) != null) {
                        // Procesar cada línea (que es un array de Strings)
                        file.add(linea);
                    }
                    
                } catch (CsvValidationException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
            e.printStackTrace();
            }
            estado = true; 
    
        } else 
            estado = false;
        
    }

    public boolean getBaseDeDatosCargada (){
        return estado;
    }

    public void editarInstancia (String id, String[] valores) throws TiposExcepcion {
        int i = 0;
        for (String string : valores) {
            String tipo =  tipos.get(i);
            if(tipo.equals("int"))
                if(!ClienteBDD.esNumeroEntero(string)){
                    throw new TiposExcepcion("Has intentado poner un valor no numerico donde no toca");
                }
            i++;
        }
            i = 0;
        for (String[] filas : file) {
            if (filas[0].equals(id))
                break; 
            i++;
        }
        valores[0]=id;
        file.remove(i);
        file.add(valores);

        Ruta ruta = new Ruta();
        String rutaArchivos = ruta.ruta() + entidad.toLowerCase() + ".csv";
        try (CSVWriter writer = new CSVWriter(new FileWriter(rutaArchivos))) {
            writer.writeAll(file);
        } catch (IOException e) {
            System.out.println("No se ha podido guardar la informacion");
        }

    }

    public void eliminarPorLlave (String llave){
        for (String[] strings : file) {
            if(strings[0].equals(llave)){
                file.remove(strings);
                break;
            }   
        }
        Ruta ruta = new Ruta();
        String rutaArchivos = ruta.ruta()+entidad.toLowerCase()+".csv";
        try (CSVWriter writer = new CSVWriter(new FileWriter(rutaArchivos))) {
            writer.writeAll(file);
        } catch (IOException e) {
            System.out.println("No se ha podido guardar la informacion");
        }
    }

    public String[] getAtributos (){
        return file.get(0);
    }

    public void agregarInstancia(String[] valores) throws TiposExcepcion {
        int i = 0;
        for (String string : valores) {
            String tipo =  tipos.get(i);
            if(tipo.equals("int"))
                if(!ClienteBDD.esNumeroEntero(string)){
                    System.out.println("Fallo en " + string);
                    throw new TiposExcepcion("Has intentado poner un valor no numerico donde no toca");
                }
            i++;
        }
        
        Long id = System.currentTimeMillis();
        valores[0]=id.toString();
        file.add(valores);
        Ruta ruta = new Ruta();
        String rutaArchivos = ruta.ruta()+entidad.toLowerCase()+".csv";
        try (CSVWriter writer = new CSVWriter(new FileWriter(rutaArchivos))) {
            writer.writeAll(file);
        } catch (IOException e) {
            System.out.println("No se ha podido guardar la informacion");
        }

    }


    public String[] consultarPorLlave (String llave){
        for (String[] elemento : file) {
            if(elemento[0].equals(llave))
                return elemento;
        }
        
        return null;
    }
    public void imprimir (){
        if(file.equals(null)){
            System.out.println("No se a cargado ninguna base");
            return;
        }
        for (String[] line : file) {
            for(int i = 0;i<line.length;i++){
                System.out.print(line[i]+", ");
            }
            System.out.println();
        }
    }

    private void tipos (String entidad){
        String[] tipo1={"String","String","String","String","String","String","int","int","int","int","String","String","String"};
        String[] tipo2={"String","String","String","String"};
        String[] tipo3={"String","String","String","String","String","String","int","int","int","int","String","String"};
        if(entidad.equals("ATLETAS"))
            this.set(tipo1);
        else if(entidad.equals("DISCIPLINAS"))
            this.set(tipo2);
        else
            this.set(tipo3);

    }
    private void set (String[] tipos){
        this.tipos = new ArrayList<String>();
        for (String tipo : tipos) {
            this.tipos.add(tipo);
        }
    }
    private static boolean esNumeroEntero(String cadena) {
        return cadena.matches("^-?\\d+$");
    }
}



/**
 * ruta
 */
class Ruta {

    String rutaWin = "\\src\\main\\java\\bdd\\practica\\csv\\";
    String rutaLinux = "/src/main/java/bdd/practica/csv/";

    Ruta (){
        //contructor vacio
    }
    public String ruta (){
        String os = System.getProperty("os.name");
        String rutaActual = System.getProperty("user.dir");

        if(os.contains("win"))
            return rutaActual+rutaWin;
        else{
            return rutaActual+rutaLinux;
        }
    }
    
}

