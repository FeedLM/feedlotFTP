/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import static domain.Principal.ventana;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Developer GAGS
 */
public class Log {

    String nombreArchivo;
    FileWriter archivo;
    SimpleDateFormat formatoDateTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public Log(String archivo) {

        nombreArchivo = archivo;
    }

    public void log(String mensaje, boolean error) {

        System.out.println(mensaje);
        
        ventana.setText(mensaje);
        ventana.addLog(mensaje+"\n", error);
        
        try {
            if (new File(nombreArchivo).exists() == false) {

                archivo = new FileWriter(new File(nombreArchivo), false);
            }

            archivo = new FileWriter(new File(nombreArchivo), true);
            Calendar fechaActual = Calendar.getInstance(); //Para poder utilizar el paquete calendar     
            //Empieza a escribir en el archivo
            archivo.write(formatoDateTime.format(fechaActual.getTime()) + ": " + mensaje + "\r\n");
            archivo.close(); //Se cierra el archivo

        } catch (IOException ex) {
            Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
