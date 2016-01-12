/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import domain.ManejadorBD;
import gui.Ventana;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Developer GAGS
 */
public class Principal {

    public static ManejadorBD manejadorBD = null;
    public static ManejadorBD manejadorBD_2 = null;
    public static String arhivo = "feetlotFTP.log";
    public static Log log;
    public static Ventana ventana;

    public static void main(String args[]) {

        Proceso proceso;
        Proceso_2 proceso_2;

        ventana = new Ventana();
        ventana.setVisible(true);
        ventana.setBar(0);

        log = new Log(arhivo);
        log.log("Iniciando sesion", false);
        
        System.setProperty("java.net.preferIPv4Stack", "true");

        /*
         proceso = new Proceso();        
         proceso.inicio();
        */

        proceso_2 = new Proceso_2();
        proceso_2.inicio();

       // ventana.setBar(100);
        ventana.terminado = true;
        log.log("Replicacion terminada", false);
    }
}
