package domain;

import static domain.Principal.log;
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
public class Export {

    String nombreArchivo = "";
    FileWriter archivo;
    File archivo2;
    private String encabezado;// = "[animal]";
    SimpleDateFormat formatoDateTime = new SimpleDateFormat("yyyyMMdd_HHmmss");
    boolean esCambio = false;

    public void reg(String registro) {

        boolean esEncabezado = false;

        try {
            if (new File(nombreArchivo).exists() == false) {

                Calendar fechaActual = Calendar.getInstance();

                nombreArchivo = "tempfiles\\Export_" + formatoDateTime.format(fechaActual.getTime()) + ".sql";
                //nombreArchivo = "Export_" + formatoDateTime.format(fechaActual.getTime()) + ".sql";

                archivo = new FileWriter(new File(nombreArchivo), false);
                esEncabezado = true;
            }

            archivo = new FileWriter(new File(nombreArchivo), true);
            //Empieza a escribir en el archivo

            if (esEncabezado || esCambio) {

                archivo.write(encabezado + "\r\n");
            }

            archivo.write(registro + "\r\n");
            //Se cierra el archivo
            archivo.close();

            //archivo2 = new File(nombreArchivo);
            // log.log(archivo2.length() + "");
            //Si archivo supera los kilobytes crear otro
        } catch (IOException ex) {

            Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, ex);
        }

        esCambio = false;
    }

    /**
     * @param encabezado the encabezado to set
     */
    public void setEncabezado(String encabezado) {

        this.encabezado = encabezado;
        esCambio = true;
    }
}
