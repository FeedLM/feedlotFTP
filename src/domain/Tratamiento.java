/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import static domain.Principal.log;
import static domain.Principal.manejadorBD;
import java.text.ParseException;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Developer GAGS
 */
public class Tratamiento extends ExportTable {

    public String id_tratamiento;
    public String codigo;
    public String nombre;
    public String status;
    public Date fecha;

    public Tratamiento() {

        super("[tratamiento]");
    }

    public void cargarDatos() {

        manejadorBD.consulta(""
                + "SELECT t.id_tratamiento, t.codigo,           \n"
                + "       t.nombre,         t.status,           \n"
                + "       t.fecha                               \n"
                + "FROM   tratamiento t,    repl_tratamiento r  \n"
                + "WHERE  t.id_tratamiento =   r.id_tratamiento \n"
                + "AND    r.status    =   'PR';");
    }

    public void cargarDatos_1(ManejadorBD bd, Date fecha) {

        bd.consulta(""
                + "SELECT t.id_tratamiento, t.codigo,           \n"
                + "       t.nombre,         t.status,           \n"
                + "       t.fecha                               \n"
                + "FROM   tratamiento t,    repl_tratamiento r  \n"
                + "WHERE  t.id_tratamiento =   r.id_tratamiento \n"
                + "AND      r.fecha >   '" + formatoDateTime.format(fecha) + "';");
    }

    public void actualizar_1(ManejadorBD origen, ManejadorBD destino) {

        for (int i = 0; i < origen.getRowCount(); i++) {
            try {
                codigo = origen.getValorString(i, 0);
                nombre = origen.getValorString(i, 1);
                status = origen.getValorString(i, 2);
                fecha = formatoDateTime.parse(origen.getValorString(i, 3));

                manejadorBD.parametrosSP = new ParametrosSP();
                manejadorBD.parametrosSP.agregarParametro(id_tratamiento, "varIdTratamiento", "STRING", "IN");
                manejadorBD.parametrosSP.agregarParametro(codigo, "vaCodigo", "STRING", "IN");
                manejadorBD.parametrosSP.agregarParametro(nombre, "varNombre", "STRING", "IN");
                manejadorBD.parametrosSP.agregarParametro(status, "varStatus", "STRING", "IN");
                manejadorBD.parametrosSP.agregarParametro(formatoDateTime.format(fecha), "varFecha", "STRING", "IN");

                manejadorBD.ejecutarSP("{ call actualizarTratamientoRepl(?,?,?,?,?) }");
            } catch (ParseException ex) {

                log.log(ex.getMessage(), true);
                Logger.getLogger(Tratamiento.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void actualizar(String cadena) {

        log.log(cadena, true);

        try {
            StringTokenizer st;

            st = new StringTokenizer(cadena, "|");

            System.out.println(cadena);

            id_tratamiento = st.nextToken();
            codigo = st.nextToken();
            nombre = st.nextToken();
            status = st.nextToken();
            fecha = formatoDateTime.parse(st.nextToken());

            manejadorBD.parametrosSP = new ParametrosSP();
            manejadorBD.parametrosSP.agregarParametro(id_tratamiento, "varIdTratamiento", "STRING", "IN");
            manejadorBD.parametrosSP.agregarParametro(codigo, "vaCodigo", "STRING", "IN");
            manejadorBD.parametrosSP.agregarParametro(nombre, "varNombre", "STRING", "IN");
            manejadorBD.parametrosSP.agregarParametro(status, "varStatus", "STRING", "IN");
            manejadorBD.parametrosSP.agregarParametro(formatoDateTime.format(fecha), "varFecha", "STRING", "IN");

            manejadorBD.ejecutarSP("{ call actualizarTratamientoRepl(?,?,?,?,?) }");

        } catch (ParseException ex) {

            log.log(ex.getMessage(), true);
            Logger.getLogger(Tratamiento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
