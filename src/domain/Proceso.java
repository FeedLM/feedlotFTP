/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import static domain.Principal.log;
import static domain.Principal.manejadorBD;
import static domain.Principal.ventana;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

/**
 *
 * @author Developer GAGS
 */
public class Proceso {

    Rancho rancho;
    ConceptoMovimiento concepto_movimiento;
    Corral corral;
    CorralAnimal corral_animal;
    Animal animal;
    Movimiento movimiento;
    DetalleMovimiento detalle_movimiento;

    Medicina medicina;

    MedicinaAnimal medicina_animal;
    CorralDatos corral_datos;
    Cria cria;
    Proveedor proveedor;
    Raza raza;
    Tratamiento tratamiento;
    MedicinaTratamiento medicina_tratamiento;

    FTP ftp;

    public static Export export;
    static boolean muestraSQL;
    public static Properties properties;
    private static String database;
    private boolean error = false;
    private String files = "Files.properties";

    public static SimpleDateFormat formatoDateTime;

    String encabezado;

    public enum Tables {

        rancho,
        concepto_movimiento,
        corral,
        corral_animal,
        animal,
        movimiento,
        detalle_movimiento,
        medicina,
        medicina_animal,
        corral_datos,
        cria,
        proveedor,
        raza,
        tratamiento,
        medicina_tratamiento
    }

    public void listarArchivos() {

    }

    public Proceso() {

        rancho = new Rancho();
        concepto_movimiento = new ConceptoMovimiento();
        corral = new Corral();
        corral_animal = new CorralAnimal();
        animal = new Animal();
        movimiento = new Movimiento();
        detalle_movimiento = new DetalleMovimiento();
        medicina = new Medicina();
        medicina_animal = new MedicinaAnimal();
        corral_datos = new CorralDatos();
        cria = new Cria();
        proveedor = new Proveedor();
        raza = new Raza();
        tratamiento = new Tratamiento();
        medicina_tratamiento = new MedicinaTratamiento();

        formatoDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    public void inicio() {

        export = new Export();

        log.log("conectando", false);

        File folder = new File("tempfiles");
        folder.mkdir();
        ventana.setBar(1);
        conectar();
        ventana.setBar(2);
        ftp = new FTP();

        /**
         * ***********************************
         */
        //descargarArchivos();
        ftp.descargarArchivos();
        ventana.setBar(10);

        cargarArchivos();
        ventana.setBar(60);

        borrarArchivos();
        ventana.setBar(70);
        /**
         * ***********************************
         */

        /**
         * ***********************************
         */
        // this.cambiaStatus("PE", "SC");
        this.cambiaStatus("PR", "PE");
        ventana.setBar(71);
        rancho.insertar();
        ventana.setBar(72);
        concepto_movimiento.insertar();
        ventana.setBar(73);
        corral.insertar();
        ventana.setBar(74);
        corral_animal.insertar();
        ventana.setBar(75);
        animal.insertar();
        ventana.setBar(76);
        movimiento.insertar();
        ventana.setBar(77);
        detalle_movimiento.insertar();
        ventana.setBar(78);
        medicina.insertar();
        ventana.setBar(79);
        medicina_animal.insertar();
        ventana.setBar(80);
        corral_datos.insertar();
        ventana.setBar(81);
        cria.insertar();
        ventana.setBar(82);
        proveedor.insertar();
        ventana.setBar(83);
        raza.insertar();
        ventana.setBar(84);
        tratamiento.insertar();
        ventana.setBar(85);
        medicina_tratamiento.insertar();
        ventana.setBar(86);
        //cerrar        
        subirArchivo(export.nombreArchivo);
        ventana.setBar(90);
        this.cambiaStatus("SC", "PR");
        /**
         * ***********************************
         */
        ventana.setBar(95);
        
        log.log("Finalizando", false);
    }

    public void borrarArchivos() {

        borrarArchivosTemporales();
        //Borrar Archivos del FTP
        borrarArchivosFTP();
    }

    public void borrarArchivosFTP() {

        log.log("Borrando Archivos del FTP", false);

        for (FTPFile arch : ftp.archivosFTP) {

            String ls_nombre_archivo = arch.getName();
            ftp.borrarArchivo(ls_nombre_archivo);
        }
    }

    public void borrarArchivosTemporales() {

        log.log("Borrando Archivos de tempfiles", false);

        String sourcePath = "tempfiles";
        File prueba = new File(sourcePath);
        File[] ficheros = prueba.listFiles();
        File f = null;

        if (prueba.exists()) {
            for (int i = 0; i < ficheros.length; i++) {

                log.log("Borrando Archivo: " + ficheros[i].toString(), false);
                f = new File(ficheros[i].toString());

                if (f.delete()) {

                    log.log("Archivo Borrado correctamente", false);
                } else {

                    log.log("Error al Borrar el Archivo", true);
                }
            }
        } else {

            log.log("No existe el directorio", true);
        }
    }

    public void subirArchivo(String archivo) {

        Properties filesProperties = new Properties();
        Integer cantidad_carpetas;

        //verificar si existe el archivo
        File fichero = new File(archivo);

        if (!fichero.exists()) {

            log.log("El Archivo \"" + archivo + "\" no existe", true);
            return;
        }

        cantidad_carpetas = Integer.parseInt(properties.getProperty("number_up"));

        for (int i = 0; i < cantidad_carpetas; i++) {

            ftp.uploadpath = properties.getProperty("uploadpath" + (i + 1));
            ftp.subirArchivo(archivo);
        }
    }

    public void subirArchivo_2(String archivo) {

        Properties filesProperties = new Properties();
        Integer countfiles;
        Integer cantidad_carpetas;
        FileInputStream file;
        // rutaOrigenArchivo = ruta;
        //nombreArchivo = archivo;

        cantidad_carpetas = Integer.parseInt(properties.getProperty("number_up"));

        for (int i = 0; i < cantidad_carpetas; i++) {

            try {
                ftp.uploadpath = properties.getProperty("uploadpath" + (i + 1));
                ftp.subirArchivo(archivo);

                File fichero = new File(files);

                if (fichero.delete()) {

                    log.log("Archivo properties de " + ftp.uploadpath + " eliminado", false);
                } else {
                    log.log("Error al Eliminar el archivo Files properties de carpeta a subir, en directorio local", true);
                }

                //descargar Archivo properties de la carpeta
                ftp.downloadpath = ftp.uploadpath;
                ftp.descargarArchivo(files);
                file = new FileInputStream(files);
                filesProperties.load(file);
                file.close();
                //Obtener el valor countfiles
                countfiles = Integer.parseInt(filesProperties.getProperty("countfiles"));
                //Actualizar el valor countfiles en el archivo + 1
                countfiles += 1;
                filesProperties.replace("countfiles", countfiles + "");
                //Insertar valor de "file+[countfiles + 1] = export.nombreArchivo"
                filesProperties.setProperty("file" + countfiles, archivo);
                //Insertar valor de "fileStatus +[countfile  + 1]
                filesProperties.setProperty("fileStatus" + countfiles, "S");

                // filesProperties.store(file, "");
                FileOutputStream fos = new FileOutputStream(files);
                filesProperties.store(fos, null);

                ftp.subirArchivo(files);

                filesProperties.clear();
                fos.close();

            } catch (IOException ex) {

                log.log("Error: " + ex.getMessage(), true);
                Logger.getLogger(Proceso.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void descargarArchivos() {

        Integer countfiles;
        Properties filesProperties = new Properties();
        String archivoSql;
        FileInputStream input;

        ftp.downloadpath = properties.getProperty("downloadpath");
        ftp.descargarArchivo(files);

        try {

            input = new FileInputStream(files);
            filesProperties.load(input);
            input.close();
            countfiles = Integer.parseInt(filesProperties.getProperty("countfiles"));

            for (int i = 0; i < countfiles; i++) {

                archivoSql = filesProperties.getProperty("file" + (i + 1));
                ftp.descargarArchivo(archivoSql);

                //cargar Archivo SQL
                cargarArchivoSql(archivoSql);
            }
        } catch (IOException ex) {
            log.log(ex.getMessage(), true);
            Logger.getLogger(FTP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cargarArchivos() {

        Integer porcentaje = 0;
        Integer conteo = 1;
        Integer fin;
        /*
         10 - 60
         0 - 50
         */
        
        fin = ftp.archivosFTP.length;
        
        for (FTPFile arch : ftp.archivosFTP) {

            String ls_nombre_archivo = arch.getName();
            log.log("Cargando el Archivo: tempfiles\\" + ls_nombre_archivo, false);
            cargarArchivoSql("tempfiles\\" + ls_nombre_archivo);
            
            porcentaje = ((conteo * 50) / fin) + 10;
            conteo++;
            ventana.setBar(porcentaje);
        }
    }

    public void cargarArchivoSql(String archivoSQL) {

        String linea;

        log.log("cargando Archivo " + archivoSQL, false);

        try {

            File archivo = null;
            FileReader fr = null;
            BufferedReader br = null;

            archivo = new File(archivoSQL);
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            // Lectura del fichero                    
            while ((linea = br.readLine()) != null) {

                //  System.out.println(linea);
                if (linea.contains("[") && linea.contains("]")) {

                    encabezado = linea.substring(1, linea.length() - 1);
                    //  System.out.println(encabezado);
                } else {
                    Tables tables = Tables.valueOf(encabezado);

                    switch (tables) {

                        case rancho:
                            rancho.actualizar(linea);
                            break;

                        case concepto_movimiento:
                            concepto_movimiento.actualizar(linea);
                            break;

                        case corral:
                            corral.actualizar(linea);
                            break;

                        case corral_animal:
                            corral_animal.actualizar(linea);
                            break;

                        case animal:
                            animal.actualizar(linea);
                            break;

                        case movimiento:
                            movimiento.actualizar(linea);
                            break;

                        case detalle_movimiento:
                            detalle_movimiento.actualizar(linea);
                            break;

                        case medicina:
                            medicina.actualizar(linea);
                            break;

                        case medicina_animal:
                            medicina_animal.actualizar(linea);
                            break;

                        case corral_datos:
                            corral_datos.actualizar(linea);
                            break;

                        case cria:
                            cria.actualizar(linea);
                            break;

                        case proveedor:
                            proveedor.actualizar(linea);
                            break;

                        case raza:
                            raza.actualizar(linea);
                            break;

                        case tratamiento:
                            tratamiento.actualizar(linea);
                            break;

                        case medicina_tratamiento:
                            medicina_tratamiento.actualizar(linea);
                            break;
                    }
                }
            }

            //cierra el archivo para poder borrarlo despues
            br.close();
        } catch (FileNotFoundException ex) {

            log.log("Error: " + ex.getMessage(), true);
            Logger.getLogger(Proceso.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {

            log.log("Error: " + ex.getMessage(), true);
            Logger.getLogger(Proceso.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void conectar() {

        muestraSQL = true;
        properties = new Properties();
        InputStream input = null;
        String sContraseña = "admin";
        String nombre = "admin";

        try {
            //input =  (this.getClass().getClassLoader().getResource("/resources/feedLot.properties")).toString();
            //  properties.load(this.getClass().getResourceAsStream("/source/feedLot.properties"));

            FileInputStream in = new FileInputStream("feedLot.properties");
            properties.load(in);
            database = properties.getProperty("database");
            log.log("conectando a: " + database, false);
            manejadorBD = new ManejadorBD(muestraSQL);
            manejadorBD.conectar("com.mysql.jdbc.Driver", "jdbc:mysql://127.0.0.1:3306/" + database, nombre, sContraseña);

        } catch (FileNotFoundException ex) {

            log.log(ex.toString(), true);
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {

            log.log(ex.toString(), true);
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {

            log.log(ex.toString(), true);
            Logger.getLogger(Proceso.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {

            log.log(ex.toString(), true);
            Logger.getLogger(Proceso.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {

            log.log(ex.toString(), true);
            Logger.getLogger(Proceso.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {

            log.log(ex.toString(), true);
            Logger.getLogger(Proceso.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean cambiaStatus(String statusNuevo, String statusAnterior) {

        log.log("cambiando Status de " + statusAnterior + " a " + statusNuevo, false);

        manejadorBD.parametrosSP = new ParametrosSP();
        manejadorBD.parametrosSP.agregarParametro(statusNuevo, "varStatusNuevo", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(statusAnterior, "varStatusAnterior", "STRING", "IN");

        if (manejadorBD.ejecutarSP("{ call updateStatusReplica(?,?) }") == 0) {

            return true;
        }
        return false;
    }
}
