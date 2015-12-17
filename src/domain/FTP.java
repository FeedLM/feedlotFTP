/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import static domain.Proceso.properties;
import static domain.Principal.log;
import static domain.Principal.ventana;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPFile;

/**
 *
 * @author Developer GAGS
 */
public class FTP {

    public String server;
    public int port;
    public String user;
    public String password;
    FTPClient ftpClient;
    String uploadpath;
    String rutaOrigenArchivo;
    String nombreArchivo;
    String downloadpath;
    String files = "Files.properties";
    public FTPFile[] archivosFTP;

    public FTP() {

        server = properties.getProperty("serverftp");
        user = properties.getProperty("userftp");
        password = properties.getProperty("passwordftp");
        port = Integer.parseInt(properties.getProperty("portftp"));
        downloadpath = properties.getProperty("downloadpath");
        ftpClient = new FTPClient();
    }

    public void descargarArchivos() {

        log.log("descargando Archivos de " + downloadpath, false);

        archivosFTP = null;
        try {

            ftpClient.connect(server, port);
            ftpClient.login(user, password);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(ftpClient.BINARY_FILE_TYPE);
            ftpClient.changeWorkingDirectory(downloadpath);

            archivosFTP = ftpClient.listFiles();

            ftpClient.logout();
            ftpClient.disconnect();
        } catch (IOException ex) {

            log.log(ex.getMessage(), true);
            Logger.getLogger(FTP.class.getName()).log(Level.SEVERE, null, ex);
        }

        Integer porcentaje = 0;
        Integer conteo = 1;
        Integer fin;
        /*
         2 - 9
         0 - 7
         */        
        fin = archivosFTP.length;        
        
        for (FTPFile arch : archivosFTP) {

            String ls_nombre_archivo = arch.getName();
            System.out.println("tempfiles\\" + ls_nombre_archivo);
            // log.log("Bajando Archivo: "+ls_nombre_archivo);            
            descargarArchivo(ls_nombre_archivo);
            
            porcentaje = ((conteo * 7) / fin) + 2;
            
            conteo++;
            
            ventana.setBar(porcentaje);
        }
    }

    void descargarArchivoProperties() {

        descargarArchivo(files);
    }

    public void descargarArchivos_2() {

        Integer countfiles;
        Properties filesProperties = new Properties();
        String archivoSql;

        try {

            filesProperties.load(new FileInputStream(files));
            countfiles = Integer.parseInt(filesProperties.getProperty("countfiles"));

            for (int i = 0; i < countfiles; i++) {

                archivoSql = filesProperties.getProperty("file" + (i + 1));
                descargarArchivo(archivoSql);
            }

        } catch (IOException ex) {

            Logger.getLogger(FTP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void descargarArchivo(String archivo) {

        log.log("Descargando el archivo " + archivo + " de " + downloadpath, false);
        try {

            ftpClient.connect(server, port);
            ftpClient.login(user, password);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(org.apache.commons.net.ftp.FTP.BINARY_FILE_TYPE);

            // APPROACH #2: using InputStream retrieveFileStream(String)
            String remoteFile2 = downloadpath + "\\" + archivo;
            File downloadFile2 = new File("tempfiles\\" + archivo);

            OutputStream outputStream2 = new BufferedOutputStream(new FileOutputStream(downloadFile2));
            InputStream inputStream = ftpClient.retrieveFileStream(remoteFile2);
            byte[] bytesArray = new byte[4096];
            int bytesRead = -1;
            while ((bytesRead = inputStream.read(bytesArray)) != -1) {

                outputStream2.write(bytesArray, 0, bytesRead);
            }

            boolean success = ftpClient.completePendingCommand();

            if (success) {

                log.log("El archivo " + archivo + " se descargo correctamente. a tempfiles", false);
            }
            outputStream2.close();
            inputStream.close();

        } catch (IOException ex) {

            Logger.getLogger(FTP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /*
     public void subirArchivo(String ruta, String archivo) {

     Integer cantidad_carpetas;
     rutaOrigenArchivo = ruta;
     nombreArchivo = archivo;

     cantidad_carpetas = Integer.parseInt(properties.getProperty("number_up"));

     for (int i = 0; i < cantidad_carpetas; i++) {

     uploadpath = properties.getProperty("uploadpath" + (i + 1));
     subirArchivo(ruta, archivo);
            
     //descargar Archivo properties de la carpeta
            
            
     //obtener el valor countfiles
     //actualizar el valor countfiles en el archivo + 1
     //insertar valor de "file+[countfiles + 1] = export.nombreArchivo"
     //insertar valor de "fileStatus +[countfile  + 1]
     }
     }
     */

    public void borrarArchivo(String archivo) {

        InputStream inputStream;

        try {
            ftpClient.connect(server, port);
            ftpClient.login(user, password);
            //  ftpClient.enterLocalPassiveMode();

//            ftpClient.setFileType(org.apache.commons.net.ftp.FTP.BINARY_FILE_TYPE);
            // APPROACH #2: uploads second file using an OutputStream
            //File LocalFile = new File(rutaOrigenArchivo + "\\" + nombreArchivo);
            //          File LocalFile = new File(archivo);
            String RemoteFile = downloadpath + "\\" + archivo;
            //        inputStream = new FileInputStream(LocalFile);

            log.log("Borrando  archivo " + archivo + " de " + downloadpath, false);

            boolean deleted = ftpClient.deleteFile(RemoteFile);

            if (deleted) {

                log.log("Archivo borrado...", false);
            }

            /*
             OutputStream outputStream = ftpClient.storeFileStream(RemoteFile);
             byte[] bytesIn = new byte[4096];
             int read = 0;

             while ((read = inputStream.read(bytesIn)) != -1) {

             outputStream.write(bytesIn, 0, read);
             }
            
             inputStream.close();
             outputStream.close();
             *//*
             boolean completed = ftpClient.completePendingCommand();
             if (completed) {

             log.log("el archivo a sido subido correctamente");
             }
             */
        } catch (IOException ex) {

            log.log(ex.toString(), true);
            Logger.getLogger(FTP.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            try {
                if (ftpClient.isConnected()) {

                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {

                log.log(ex.toString(), true);
                ex.printStackTrace();
            }
        }
    }

    public void subirArchivo(String archivo) {

        InputStream inputStream;
        String archivo_sin_ruta = "";
        StringTokenizer st = new StringTokenizer(archivo, "\\");

        while (st.hasMoreElements()) {

            archivo_sin_ruta = st.nextToken();
        }

        try {
            ftpClient.connect(server, port);
            ftpClient.login(user, password);
            ftpClient.enterLocalPassiveMode();

            ftpClient.setFileType(org.apache.commons.net.ftp.FTP.BINARY_FILE_TYPE);

            // APPROACH #2: uploads second file using an OutputStream
            //File LocalFile = new File(rutaOrigenArchivo + "\\" + nombreArchivo);
            File LocalFile = new File(archivo);
            String RemoteFile = uploadpath + "\\" + archivo_sin_ruta;
            inputStream = new FileInputStream(LocalFile);

            log.log("Subiendo archivo " + archivo + " a " + uploadpath, false);
            OutputStream outputStream = ftpClient.storeFileStream(RemoteFile);
            byte[] bytesIn = new byte[4096];
            int read = 0;

            while ((read = inputStream.read(bytesIn)) != -1) {

                outputStream.write(bytesIn, 0, read);
            }

            inputStream.close();
            outputStream.close();

            boolean completed = ftpClient.completePendingCommand();
            if (completed) {

                log.log("el archivo a sido subido correctamente", false);
            }
        } catch (IOException ex) {

            log.log(ex.toString(), true);
            Logger.getLogger(FTP.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            try {
                if (ftpClient.isConnected()) {

                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {

                log.log(ex.toString(), true);
                ex.printStackTrace();
            }
        }
    }
}
