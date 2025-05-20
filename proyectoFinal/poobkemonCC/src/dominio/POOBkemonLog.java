package dominio;    

import java.io.File;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Clase utilitaria para el registro de excepciones en el proyecto POOBkemon.
 * Permite registrar excepciones en un archivo de log para su posterior análisis.
 */
public class POOBkemonLog {

    public static String nombre = "POOBkemon";
    
    /**
     * Registra una excepción en el archivo de log.
     * @param e La excepción a registrar
     */
    public static void record(Exception e) {
        try {
            // Crear directorio logs si no existe
            File logsDir = new File("logs");
            if (!logsDir.exists()) {
                logsDir.mkdir();
            }
            

            Logger logger = Logger.getLogger(nombre);
            logger.setUseParentHandlers(false);
            FileHandler file = new FileHandler("logs/" + nombre + ".log", true);
            file.setFormatter(new SimpleFormatter());
            logger.addHandler(file);
            

            logger.log(Level.SEVERE, e.toString(), e);
            file.close();
        } catch (Exception oe) {
            // Si hay un error al registrar la excepción
            System.err.println("Error al registrar excepción en el log: ");
            oe.printStackTrace();
            System.exit(0);
        }
    }
    
    /**
     * Registra un mensaje informativo en el archivo de log.
     * @param mensaje El mensaje a registrar
     */
    public static void info(String mensaje) {
        try {

            File logsDir = new File("logs");
            if (!logsDir.exists()) {
                logsDir.mkdir();
            }
            
            Logger logger = Logger.getLogger(nombre);
            logger.setUseParentHandlers(false);
            FileHandler file = new FileHandler("logs/" + nombre + ".log", true);
            file.setFormatter(new SimpleFormatter());
            logger.addHandler(file);
            

            logger.log(Level.INFO, mensaje);
            file.close();
        } catch (Exception e) {
            System.err.println("Error al registrar mensaje en el log: ");
            e.printStackTrace();
        }
    }
    
    /**
     * Registra un mensaje de advertencia en el archivo de log.
     * @param mensaje El mensaje de advertencia a registrar
     */
    public static void warning(String mensaje) {
        try {
            // Crear directorio logs si no existe
            File logsDir = new File("logs");
            if (!logsDir.exists()) {
                logsDir.mkdir();
            }
            

            Logger logger = Logger.getLogger(nombre);
            logger.setUseParentHandlers(false);
            FileHandler file = new FileHandler("logs/" + nombre + ".log", true);
            file.setFormatter(new SimpleFormatter());
            logger.addHandler(file);
            

            logger.log(Level.WARNING, mensaje);
            file.close();
        } catch (Exception e) {
            System.err.println("Error al registrar advertencia en el log: ");
            e.printStackTrace();
        }
    }
    
    /**
     * Registra un error en el archivo de log sin cerrar la aplicación.
     * @param e La excepción a registrar
     */
    public static void error(Exception e) {
        try {
            // Crear directorio logs si no existe
            File logsDir = new File("logs");
            if (!logsDir.exists()) {
                logsDir.mkdir();
            }
            
            // Configurar el logger
            Logger logger = Logger.getLogger(nombre);
            logger.setUseParentHandlers(false);
            FileHandler file = new FileHandler("logs/" + nombre + ".log", true);
            file.setFormatter(new SimpleFormatter());
            logger.addHandler(file);
            
            // Registrar el error
            logger.log(Level.SEVERE, e.toString(), e);
            file.close();
        } catch (Exception oe) {
            System.err.println("Error al registrar error en el log: ");
            oe.printStackTrace();
        }
    }
}