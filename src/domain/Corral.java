/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import absttract.Table;
import static domain.Principal.log;
import static domain.Proceso.export;
import static domain.Principal.manejadorBD;
import static domain.Principal.ventana;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Developer GAGS
 */
public class Corral extends ExportTable {

    public String id_rancho;
    public String id_corral;
    public String nombre;
    public String localizacion;
    public Integer dias_corral;
    public Double alimento_ingresado;
    public String observaciones;
    public Double total_costo_flete;
    public Date fecha_inicio;
    public Date fecha_cierre;
    public Double medicina_promedio;
    public Double conversion_alimenticia;
    public Double merma;
    public String status;

    public Corral() {

        super("[corral]");
    }

    public void cargarDatos() {

        manejadorBD.consulta(""
                + "SELECT c.id_rancho,                        c.id_corral,                  \n"
                + "       c.nombre,                           COALESCE(c.localizacion,''),  \n"
                + "       c.status,                     \n"
                + "       COALESCE(c.alimento_ingresado,0.0), COALESCE(c.observaciones, '') \n"
                + "FROM	  corral c, repl_corral r                                           \n"
                + "WHERE  c.id_rancho = r.id_rancho                                         \n"
                + "AND	  c.id_corral = r.id_corral                                         \n"
                + "AND    r.status    = 'PR';");
    }

    public void cargarDatos_1(ManejadorBD bd, Date fecha) {

        bd.consulta(""
                + "SELECT c.id_rancho,                                   c.id_corral,                                  \n"
                + "       COALESCE(c.nombre,''),                                      COALESCE(c.localizacion,''),                  \n"
                + "       COALESCE(dias_corral, 0),                      COALESCE(c.observaciones, ''),                \n"
                + "       COALESCE(total_costo_flete,0.00),              COALESCE(fecha_inicio, '1900-01-01 00:00:00'),\n"
                + "       COALESCE(fecha_cierre, '1900-01-01 00:00:00'), COALESCE(medicina_promedio,0.00),             \n"
                + "       COALESCE(conversion_alimenticia,0.00),         COALESCE(merma,0.00),                         \n"
                + "       COALESCE(c.status,'')                  \n"
                + "FROM	  corral c, repl_corral r   \n"
                + "WHERE  c.id_rancho = r.id_rancho \n"
                + "AND	  c.id_corral = r.id_corral \n"
                + "AND    r.fecha >   '" + formatoDateTime.format(fecha) + "';");
    }

    public void actualizar_1(ManejadorBD origen, ManejadorBD destino) {

        for (int i = 0; i < origen.getRowCount(); i++) {

            try {
                id_rancho = origen.getValorString(i, 0);
                id_corral = origen.getValorString(i, 1);
                nombre = origen.getValorString(i, 2);
                localizacion = origen.getValorString(i, 3);
                dias_corral = origen.getValorInt(i, 4);
                observaciones = origen.getValorString(i, 5);
                total_costo_flete = origen.getValorDouble(i, 6);
                fecha_inicio = formatoDateTime.parse(origen.getValorString(i, 7));
                fecha_cierre = formatoDateTime.parse(origen.getValorString(i, 8));
                medicina_promedio = origen.getValorDouble(i, 9);
                conversion_alimenticia = origen.getValorDouble(i, 10);
                merma = origen.getValorDouble(i, 11);

                status = origen.getValorString(i, 12);

                destino.parametrosSP = new ParametrosSP();

                destino.parametrosSP.agregarParametro(id_rancho, "varIdRancho", "STRING", "IN");
                destino.parametrosSP.agregarParametro(id_corral, "varIdCorral", "STRING", "IN");
                destino.parametrosSP.agregarParametro(nombre, "varNombre", "STRING", "IN");
                destino.parametrosSP.agregarParametro(localizacion, "varLocalizacion", "STRING", "IN");
                destino.parametrosSP.agregarParametro(dias_corral.toString(), "varDiasCorral", "INT", "IN");
                destino.parametrosSP.agregarParametro(observaciones, "varObservaciones", "STRING", "IN");
                destino.parametrosSP.agregarParametro(total_costo_flete.toString(), "varTotalCostoFlete", "DOUBLE", "IN");
                destino.parametrosSP.agregarParametro(formatoDateTime.format(fecha_inicio), "varFechaInicio", "STRING", "IN");
                destino.parametrosSP.agregarParametro(formatoDateTime.format(fecha_cierre), "varFechaCierre", "STRING", "IN");
                destino.parametrosSP.agregarParametro(medicina_promedio.toString(), "varMedicinaPromedio", "DOUBLE", "IN");
                destino.parametrosSP.agregarParametro(conversion_alimenticia.toString(), "varConversionAlimenticia", "DOUBLE", "IN");
                destino.parametrosSP.agregarParametro(merma.toString(), "varMerma", "DOUBLE", "IN");
                destino.parametrosSP.agregarParametro(status, "varStatus", "STRING", "IN");

                log.log("agregando " + this.toString(), false);
                
                destino.ejecutarSP("{ call actualizarCorralRepl(?,?,?,?,?,?,?,?,?,?,?,?,?) }");
                
                ventana.avanzar();
            } catch (ParseException ex) {
                Logger.getLogger(Corral.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void actualizar(String cadena) {

        log.log(cadena, false);

        StringTokenizer st;

        String delete;

        st = new StringTokenizer(cadena, "|");
        System.out.println("cadena " + cadena);
        id_rancho = st.nextToken();
        id_corral = st.nextToken();
        nombre = st.nextToken();
        localizacion = StrStringTokenizer(st.nextToken());
        status = st.nextToken();
        alimento_ingresado = Double.parseDouble(st.nextToken());
        observaciones = st.nextToken();

        manejadorBD.parametrosSP = new ParametrosSP();

        manejadorBD.parametrosSP.agregarParametro(id_rancho, "varIdRancho", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(id_corral, "varIdCorral", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(nombre, "varNombre", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(localizacion, "varLocalizacion", "STRING", "IN");

        manejadorBD.parametrosSP.agregarParametro(status, "varStatus", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(alimento_ingresado.toString(), "varAlimentoIngresado", "DOUBLE", "IN");
        manejadorBD.parametrosSP.agregarParametro(observaciones, "varObservaciones", "STRING", "IN");

        manejadorBD.ejecutarSP("{ call actualizarCorralRepl(?,?,?,?,?,?,?) }");
    }

    public String toString() {
        return id_corral + " " + nombre + " " + localizacion;
    }
}
