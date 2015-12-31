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
public class Proveedor extends ExportTable {

    public String id_proveedor;
    public String descripcion;

    public Proveedor() {

        super("[proveedor]");
    }

    public void cargarDatos() {

        manejadorBD.consulta(""
                + "SELECT p.id_proveedor,   p.descripcion \n"
                + "FROM	  proveedor p, repl_proveedor r   \n"
                + "WHERE  p.id_proveedor = r.id_proveedor \n"
                + "AND    r.status    = 'PR';");
    }

    public void actualizar(String cadena) {

        StringTokenizer st;

        String delete;

        log.log(cadena, true);

        st = new StringTokenizer(cadena, "|");
        System.out.println("cadena " + cadena);
        id_proveedor = st.nextToken();
        descripcion = st.nextToken();

        manejadorBD.parametrosSP = new ParametrosSP();

        manejadorBD.parametrosSP.agregarParametro(id_proveedor, "varIdProveedor", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(descripcion, "varDescripcion", "STRING", "IN");

        manejadorBD.ejecutarSP("{ call actualizarProveedorRepl(?,?) }");
    }

    public void cargarDatos_1(ManejadorBD bd, Date fecha) {

        manejadorBD.consulta(""
                + "SELECT p.id_proveedor,   p.descripcion \n"
                + "FROM	  proveedor p, repl_proveedor r   \n"
                + "WHERE  p.id_proveedor = r.id_proveedor \n"
                + "AND      r.fecha >   '" + formatoDateTime.format(fecha) + "';");
    }

    public void actualizar_1(ManejadorBD origen, ManejadorBD destino) {
        for (int i = 0; i < origen.getRowCount(); i++) {
            id_proveedor = origen.getValorString(i, 1);
            descripcion = origen.getValorString(i, 1);

            manejadorBD.parametrosSP = new ParametrosSP();

            manejadorBD.parametrosSP.agregarParametro(id_proveedor, "varIdProveedor", "STRING", "IN");
            manejadorBD.parametrosSP.agregarParametro(descripcion, "varDescripcion", "STRING", "IN");

            manejadorBD.ejecutarSP("{ call actualizarProveedorRepl(?,?) }");
        }
    }

    public String toString() {
        return id_proveedor + " " + descripcion;
    }
}
