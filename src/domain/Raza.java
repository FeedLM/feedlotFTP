/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import static domain.Principal.log;
import static domain.Principal.manejadorBD;
import java.util.Date;
import java.util.StringTokenizer;

/**
 *
 * @author Developer GAGS
 */
public class Raza extends ExportTable {

    public String id_raza;
    public String descripcion;
    public String seleccionar;

    public Raza() {

        super("[raza]");
    }

    public void cargarDatos() {

        manejadorBD.consulta(""
                + "SELECT a.id_raza,   a.descripcion, a.seleccionar \n"
                + "FROM	  raza a, repl_raza r   \n"
                + "WHERE  a.id_raza = r.id_raza \n"
                + "AND    r.status    = 'PR';");
    }

    public void cargarDatos_1(ManejadorBD bd, Date fecha) {

        bd.consulta(""
                + "SELECT a.id_raza,   a.descripcion, a.seleccionar \n"
                + "FROM	  raza a, repl_raza r   \n"
                + "WHERE  a.id_raza = r.id_raza \n"
                + "AND      r.fecha >   '" + formatoDateTime.format(fecha) + "';");
    }

    public void actualizar_1(ManejadorBD origen, ManejadorBD destino) {

        for (int i = 0; i < origen.getRowCount(); i++) {

            id_raza = origen.getValorString(i, 0);
            descripcion = origen.getValorString(i, 1);
            seleccionar = origen.getValorString(i, 2);

            manejadorBD.parametrosSP = new ParametrosSP();

            manejadorBD.parametrosSP.agregarParametro(id_raza, "varIdRaza", "STRING", "IN");
            manejadorBD.parametrosSP.agregarParametro(descripcion, "varDescripcion", "STRING", "IN");
            manejadorBD.parametrosSP.agregarParametro(seleccionar, "varSeleccionar", "STRING", "IN");

            manejadorBD.ejecutarSP("{ call actualizarRazaRepl(?,?) }");

        }
    }

    public void actualizar(String cadena) {

        StringTokenizer st;

        log.log(cadena, true);

        st = new StringTokenizer(cadena, "|");
        System.out.println("cadena " + cadena);
        id_raza = st.nextToken();
        descripcion = st.nextToken();
        seleccionar = st.nextToken();

        manejadorBD.parametrosSP = new ParametrosSP();

        manejadorBD.parametrosSP.agregarParametro(id_raza, "varIdRaza", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(descripcion, "varDescripcion", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(seleccionar, "varSeleccionar", "STRING", "IN");

        manejadorBD.ejecutarSP("{ call actualizarRazaRepl(?,?) }");
    }
}
