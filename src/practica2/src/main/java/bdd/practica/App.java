package bdd.practica;

import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import com.opencsv.*;
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
        System.out.println("Hello World!");

        File miDir = new File (".");
        try {
        System.out.println ("Directorio actual: " + miDir.getCanonicalPath());
        try (CSVWriter writer = new CSVWriter(new FileWriter((miDir.getCanonicalPath())+"/src/main/java/bdd/practica/csv/ejemplo.csv"))){
            System.out.println("lei el csv");
        } catch (Exception e){
            System.out.println("no pude leerlo");
        }
        }
        catch(Exception e) {
       e.printStackTrace();
       }

        

    }
}
