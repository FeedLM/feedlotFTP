/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import static domain.Principal.log;
import static domain.Principal.manejadorBD;
import static domain.Principal.manejadorBD_2;
import static domain.Proceso_2.formatoDateTime;
import java.text.ParseException;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Developer GAGS
 */
public class Medicina extends ExportTable {

    public String id_medicina;
    public Integer codigo;
    public String nombre;
    public Double costo;
    public String id_unidad;
    public Double presentacion;
    public Double costo_unitario;
    public String status;

    public Medicina() {

        super("[medicina]");
    }

    public void cargarDatos() {

        manejadorBD.consulta(""
                + "SELECT   m.id_medicina,	m.codigo,      \n"
                + "         m.nombre,           m.costo,       \n"
                + "         m.id_unidad,        m.presentacion, \n"
                + "         m.costo_unitario,   m.status       \n"
                + "FROM     medicina m, repl_medicina r        \n"
                + "WHERE    m.id_medicina   =	r.id_medicina  \n"
                + "AND      m.codigo        =	r.codigo       \n"
                + "AND      r.status        =   'PR';");
    }

     public void cargarDatos_1(ManejadorBD bd, Date fecha) {

        bd.consulta(""
                + "SELECT   m.id_medicina,                m.codigo,      \n"
                + "         m.nombre,                     m.costo,       \n"
                + "         m.id_unidad,                  m.presentacion, \n"
                + "         ifnull(m.costo_unitario,0.0), m.status       \n"
                + "FROM     medicina m,         repl_medicina r        \n"
                + "WHERE    m.id_medicina   =	r.id_medicina  \n"
                + "AND      m.codigo        =	r.codigo       \n"
                + "AND      r.fecha >   '" + formatoDateTime.format(fecha) + "';");
     }
            
    public void actualizar_1(ManejadorBD origen, ManejadorBD destino) {

        for (int i = 0; i < origen.getRowCount(); i++) {

            id_medicina = origen.getValorString(i, 0);
            codigo = origen.getValorInt(i, 1);
            nombre = origen.getValorString(i, 2);
            costo = origen.getValorDouble(i, 3);
            id_unidad = origen.getValorString(i, 4);
            presentacion = origen.getValorDouble(i, 5);
            costo_unitario = origen.getValorDouble(i, 6);
            status = origen.getValorString(i, 7);

            destino.parametrosSP = new ParametrosSP();

            destino.parametrosSP.agregarParametro(id_medicina, "varIdMedicina", "STRING", "IN");
            destino.parametrosSP.agregarParametro(codigo.toString(), "varCodigo", "INT", "IN");
            destino.parametrosSP.agregarParametro(nombre, "varNombre", "STRING", "IN");
            destino.parametrosSP.agregarParametro(costo.toString(), "varCosto", "DOUBLE", "IN");
            destino.parametrosSP.agregarParametro(id_unidad, "varIdUnidad", "STRING", "IN");
            destino.parametrosSP.agregarParametro(presentacion.toString(), "varPresentacion", "DOUBLE", "IN");
            destino.parametrosSP.agregarParametro(costo_unitario.toString(), "varCostoUnitario", "DOUBLE", "IN");
            destino.parametrosSP.agregarParametro(status, "varStatus", "STRING", "IN");

            destino.ejecutarSP("{ call actualizarMedicinaRepl(?,?,?,?,?,?,?,?) }");
        }
    }

    public void actualizar(String cadena) {

        log.log(cadena, false);

        StringTokenizer st;

        st = new StringTokenizer(cadena, "|");

        id_medicina = st.nextToken();
        codigo = Integer.parseInt(st.nextToken());
        nombre = st.nextToken();
        costo = Double.parseDouble(st.nextToken());
        id_unidad = st.nextToken();
        presentacion = Double.parseDouble(st.nextToken());
        costo_unitario = Double.parseDouble(st.nextToken());
        status = st.nextToken();

        manejadorBD.parametrosSP = new ParametrosSP();

        manejadorBD.parametrosSP.agregarParametro(id_medicina, "varIdMedicina", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(codigo.toString(), "varCodigo", "INT", "IN");
        manejadorBD.parametrosSP.agregarParametro(nombre, "varNombre", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(costo.toString(), "varCosto", "DOUBLE", "IN");
        manejadorBD.parametrosSP.agregarParametro(id_unidad, "varIdUnidad", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(presentacion.toString(), "varPresentacion", "DOUBLE", "IN");
        manejadorBD.parametrosSP.agregarParametro(costo_unitario.toString(), "varCostoUnitario", "DOUBLE", "IN");
        manejadorBD.parametrosSP.agregarParametro(status, "varStatus", "STRING", "IN");

        manejadorBD.ejecutarSP("{ call actualizarMedicinaRepl(?,?,?,?,?,?,?,?) }");
    }
}
