/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import static domain.Principal.log;
import static domain.Proceso.export;
import static domain.Proceso.formatoDateTime;
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
public class ConceptoMovimiento extends ExportTable {

    public String id_rancho;
    public String id_concepto;
    public String descripcion;
    public String des_corta;
    public String tipo;

    public ConceptoMovimiento() {

        super("[concepto_movimiento]");
    }

    public void cargarDatos() {

        manejadorBD.consulta(""
                + "SELECT c.id_rancho,   c.id_concepto,     \n"
                + "       c.descripcion, c.des_corta,       \n"
                + "       c.tipo                            \n"
                + "FROM   concepto_movimiento	c,          \n"
                + "       repl_concepto_movimiento r        \n"
                + "WHERE  c.id_rancho     =   r.id_rancho   \n"
                + "AND    c.id_concepto   =   r.id_concepto \n"
                + "AND    r.status        =   'PR';");
    }

    public void cargarDatos_1(ManejadorBD bd, Date fecha) {

        bd.consulta(""
                + "SELECT c.id_rancho,   c.id_concepto,     \n"
                + "       c.descripcion, c.des_corta,       \n"
                + "       c.tipo                            \n"
                + "FROM   concepto_movimiento	c,          \n"
                + "       repl_concepto_movimiento r        \n"
                + "WHERE  c.id_rancho     =   r.id_rancho   \n"
                + "AND    c.id_concepto   =   r.id_concepto \n"
                + "AND    r.fecha >   '" + formatoDateTime.format(fecha) + "';");
    }

    public void actualizar_1(ManejadorBD origen, ManejadorBD destino) {

        for (int i = 0; i < origen.getRowCount(); i++) {

            id_rancho = origen.getValorString(i, 0);
            id_concepto = origen.getValorString(i, 1);
            descripcion = origen.getValorString(i, 2);
            des_corta = origen.getValorString(i, 3);
            tipo = origen.getValorString(i, 4);

            destino.parametrosSP = new ParametrosSP();

            destino.parametrosSP.agregarParametro(id_rancho, "varIdRancho", "STRING", "IN");
            destino.parametrosSP.agregarParametro(id_concepto, "varIdConcepto", "STRING", "IN");
            destino.parametrosSP.agregarParametro(descripcion, "varDescripcion", "STRING", "IN");
            destino.parametrosSP.agregarParametro(des_corta, "varDesCorta", "STRING", "IN");
            destino.parametrosSP.agregarParametro(tipo, "varTipo", "STRING", "IN");

            destino.ejecutarSP("{ call actualizarConceptoRepl(?,?,?,?,?) }");
        }
    }

    public void actualizar(String cadena) {

        log.log(cadena, false);

        StringTokenizer st;

        st = new StringTokenizer(cadena, "|");

        id_rancho = st.nextToken();
        id_concepto = st.nextToken();
        descripcion = st.nextToken();
        des_corta = st.nextToken();
        tipo = st.nextToken();

        manejadorBD.parametrosSP = new ParametrosSP();

        manejadorBD.parametrosSP.agregarParametro(id_rancho, "varIdRancho", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(id_concepto, "varIdConcepto", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(descripcion, "varDescripcion", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(des_corta, "varDesCorta", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(tipo, "varTipo", "STRING", "IN");

        manejadorBD.ejecutarSP("{ call actualizarConceptoRepl(?,?,?,?,?) }");
    }
}
