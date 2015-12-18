/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import static domain.Principal.log;
import static domain.Principal.manejadorBD;
import static domain.Principal.manejadorBD_2;
import static domain.Proceso.muestraSQL;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Developer GAGS
 */
public class Proceso_2 {

    Medicina medicina;
    Animal animal;
    Rancho rancho;
    ConceptoMovimiento concepto_movimiento;
    Corral corral;
    CorralAnimal corral_animal;
    Movimiento movimiento;
    DetalleMovimiento detalle_movimiento;
    MedicinaAnimal medicina_animal;
    CorralDatos corral_datos;
    public static Properties properties;
    private static String database;
    //public static String maquina_local;
    public static Date fecha_ultima_replicacion;
    public static SimpleDateFormat formatoDateTime;
    Date fecha_actual;

    public Proceso_2() {

        fecha_actual = new Date();

        formatoDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        animal = new Animal();
        medicina = new Medicina();
        rancho = new Rancho();
        concepto_movimiento = new ConceptoMovimiento();
        corral = new Corral();
        corral_animal = new CorralAnimal();
        movimiento = new Movimiento();
        detalle_movimiento = new DetalleMovimiento();
        medicina_animal = new MedicinaAnimal();
        corral_datos = new CorralDatos();
    }

    public void inicio() {

        conectar();
        conectar_remoto();
        //buscar la ultima fecha de replicacion del servidor local
        datos_locales();
        //Seleccionar la tabla replicacion_log de servidor remoto
        //replicacionlog.obtenerDatos(manejadorBD_2);

        //Sube
        actualizar_cambios(manejadorBD, manejadorBD_2, fecha_ultima_replicacion);

        //Descarga
        actualizar_cambios(manejadorBD_2, manejadorBD, fecha_ultima_replicacion);

        actualizar_fecha();
    }

    public void actualizar_cambios(ManejadorBD origen, ManejadorBD destino, Date fecha) {

        rancho.cargarDatos_1(origen, fecha);
        rancho.actualizar_1(origen, destino);

        concepto_movimiento.cargarDatos_1(origen, fecha);
        concepto_movimiento.actualizar_1(origen, destino);

        corral.cargarDatos_1(origen, fecha);
        corral.actualizar_1(origen, destino);

        corral_animal.cargarDatos_1(origen, fecha);
        corral_animal.actualizar_1(origen, destino);

        animal.cargarDatos_1(origen, fecha);
        animal.actualizar_1(origen, destino);

        movimiento.cargarDatos_1(origen, fecha);
        movimiento.actualizar_1(origen, destino);

        detalle_movimiento.cargarDatos_1(origen, fecha);
        detalle_movimiento.actualizar_1(origen, destino);

        medicina.cargarDatos_1(origen, fecha);
        medicina.actualizar_1(origen, destino);

        medicina_animal.cargarDatos_1(origen, fecha);
        medicina_animal.actualizar_1(origen, destino);

        corral_datos.cargarDatos_1(origen, fecha);
        corral_datos.actualizar_1(origen, destino);
        /*
         cria.cargarDatos_1(origen, fecha); 
         cria.actualizar_1(origen, destino);
         
         proveedor.cargarDatos_1(origen, fecha); 
         proveedor.actualizar_1(origen, destino);
         
         raza.cargarDatos_1(origen, fecha); 
         raza.actualizar_1(origen, destino);
         
         tratamiento.cargarDatos_1(origen, fecha); 
         tratamiento.actualizar_1(origen, destino);
         
         medicina_tratamiento.cargarDatos_1(origen, fecha); 
         medicina_tratamiento.actualizar_1(origen, destino);
         */
    }

    public void datos_locales() {

        manejadorBD.consulta("\n"
                + "SELECT fecha_ultima_replicacion\n"
                + "FROM configuracion");

        try {
            fecha_ultima_replicacion = formatoDateTime.parse(manejadorBD.getValorString(0, 0));
        } catch (ParseException ex) {
            Logger.getLogger(Animal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void actualizar_fecha() {

        fecha_actual = new Date();

        manejadorBD.actualizacion("\n"
                + "update configuracion\n"
                + "set fecha_ultima_replicacion = '" + formatoDateTime.format(fecha_actual) + "';");
    }

    public void conectar() {

        muestraSQL = true;
        properties = new Properties();
        InputStream input = null;
        String database = "";
        String sContraseña;
        String nombre;
        String dir;
        String port;

        try {
            //input = ( this.getClass().getClassLoader().getResource("/resources/feedLot.properties")).toString();
            //properties.load(this.getClass().getResourceAsStream("/source/feedLot.properties"));

            FileInputStream in = new FileInputStream("feedLot.properties");
            properties.load(in);

            database = properties.getProperty("database");
            sContraseña = properties.getProperty("pass");
            nombre = properties.getProperty("user");
            dir = properties.getProperty("dir");
            port = properties.getProperty("port");

            log.log("conectando a: " + database, false);
            manejadorBD = new ManejadorBD(muestraSQL);
            manejadorBD.conectar("com.mysql.jdbc.Driver", "jdbc:mysql://" + dir + ":" + port + "/" + database, nombre, sContraseña);

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

    public void conectar_remoto() {

        muestraSQL = true;
        properties = new Properties();
        InputStream input = null;
        String database = "";
        String nombre = "";
        String sContraseña = "";
        String dir;
        String port;

        try {
            //input =  (this.getClass().getClassLoader().getResource("/resources/feedLot.properties")).toString();
            //properties.load(this.getClass().getResourceAsStream("/source/feedLot.properties"));

            FileInputStream in = new FileInputStream("feedLot.properties");
            properties.load(in);

            database = properties.getProperty("remote_database");
            nombre = properties.getProperty("remote_user");
            sContraseña = properties.getProperty("remote_pass");
            dir = properties.getProperty("remote_dir");
            port = properties.getProperty("remote_port");

            log.log("conectando a: " + database, false);
            manejadorBD_2 = new ManejadorBD(muestraSQL);
            manejadorBD_2.conectar("com.mysql.jdbc.Driver", "jdbc:mysql://" + dir + ":" + port + "/" + database, nombre, sContraseña);
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
}