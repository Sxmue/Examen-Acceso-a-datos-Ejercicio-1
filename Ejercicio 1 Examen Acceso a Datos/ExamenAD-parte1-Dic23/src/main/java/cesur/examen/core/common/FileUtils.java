package cesur.examen.core.common;

import cesur.examen.core.worker.Worker;
import lombok.extern.java.Log;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * EXAMEN DE ACCESO A DATOS
 * Diciembre 2023
 *
 * Nombre del alumno:Samuel Leiva Álvarez
 * Fecha:11/12/23
 *
 * No se permite escribir en consola desde las clases DAO, Service y Utils usando System.out.
 * En su lugar, usa log.info(), log.warning() y log.severe() para mostrar información interna
 * o para seguir la traza de ejecución.
 */


@Log
public class FileUtils {

    public static void toCSV(String fileName, List<Worker> workers) {

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))){

            for (Worker w: workers) {

              String escritura =w.getName()+","+w.getDni()+","+w.getFrom()+"\n";

              bw.write(escritura);

            }

            log.info("Escrito correctamente");

        }catch (IOException e) {
            log.severe("ERROR DE ESCRITURA DEL ARCHIVO");
        }



        /*
        Uncomment and implement body method!...

        try (...) {

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        */
    }
}
