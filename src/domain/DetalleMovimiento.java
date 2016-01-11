/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import static domain.Principal.log;
import static domain.Principal.manejadorBD;
import static domain.Principal.ventana;
import java.text.ParseException;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Developer GAGS
 */
public class DetalleMovimiento extends ExportTable {

    public String id_rancho;
    public String id_movimiento;
    public String id_concepto;
    public Integer id_detalle;
    public String id_animal;

    public DetalleMovimiento() {

        super("[detalle_movimiento]");
    }

    public void cargarDatos() {

        manejadorBD.consulta(""
                + "SELECT   d.id_rancho,	d.id_movimiento,            \n"
                + "         d.id_concepto,	d.id_detalle,               \n"
                + "         d.id_animal                                     \n"
                + "FROM     detalle_movimiento d, repl_detalle_movimiento r \n"
                + "WHERE    d.id_rancho     =	r.id_rancho                 \n"
                + "AND      d.id_movimiento =	r.id_movimiento             \n"
                + "AND      d.id_concepto   =	r.id_concepto               \n"
                + "AND      d.id_detalle    =	r.id_detalle                \n"
                + "AND      d.id_animal     =	r.id_animal                 \n"
                + "AND      r.status        =   'PR';");
    }

    public void cargarDatos_1(ManejadorBD bd, Date fecha) {

        bd.consulta(""
                + "SELECT   d.id_rancho,	d.id_movimiento,            \n"
                + "         d.id_concepto,	d.id_detalle,               \n"
                + "         d.id_animal                                     \n"
                + "FROM     detalle_movimiento d, repl_detalle_movimiento r \n"
                + "WHERE    d.id_rancho     =	r.id_rancho                 \n"
                + "AND      d.id_movimiento =	r.id_movimiento             \n"
                + "AND      d.id_concepto   =	r.id_concepto               \n"
                + "AND      d.id_detalle    =	r.id_detalle                \n"
                + "AND      d.id_animal     =	r.id_animal                 \n"
                + "AND      r.fecha >   '" + formatoDateTime.format(fecha) + "';");
    }

    public void actualizar_1(ManejadorBD origen, ManejadorBD destino) {
        for (int i = 0; i < origen.getRowCount(); i++) {

            try {
                id_rancho = origen.getValorString(i, 0);
                id_movimiento = origen.getValorString(i, 1);
                id_concepto = origen.getValorString(i, 2);
                id_detalle = Integer.parseInt(origen.getValorString(i, 3));
                id_animal = origen.getValorString(i, 4);

                destino.parametrosSP = new ParametrosSP();

                destino.parametrosSP.agregarParametro(id_rancho, "varIdRancho", "STRING", "IN");
                destino.parametrosSP.agregarParametro(id_movimiento, "varIdMovimiento", "STRING", "IN");
                destino.parametrosSP.agregarParametro(id_concepto, "varIdConcepto", "STRING", "IN");
                destino.parametrosSP.agregarParametro(id_detalle.toString(), "varIdDetalle", "INT", "IN");
                destino.parametrosSP.agregarParametro(id_animal, "varIdAnimal", "STRING", "IN");

                log.log("agregando " + this.toString(), false);
                
                destino.ejecutarSP(
                        "{ call actualizarDetalleMovimientoRepl(?,?,?,?,?) }");
                
                ventana.avanzar();
                
            } catch (Exception ex) {
                Logger.getLogger(DetalleMovimiento.class
                        .getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public void actualizar(String cadena) {

        StringTokenizer st;

        String delete;

        log.log(cadena, false);

        st = new StringTokenizer(cadena, "|");

        id_rancho = st.nextToken();
        id_movimiento = st.nextToken();
        id_concepto = st.nextToken();
        id_detalle = Integer.parseInt(st.nextToken());
        id_animal = st.nextToken();

        manejadorBD.parametrosSP = new ParametrosSP();

        manejadorBD.parametrosSP.agregarParametro(id_rancho, "varIdRancho", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(id_movimiento, "varIdMovimiento", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(id_concepto, "varIdConcepto", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(id_detalle.toString(), "varIdDetalle", "INT", "IN");
        manejadorBD.parametrosSP.agregarParametro(id_animal, "varIdAnimal", "STRING", "IN");

        manejadorBD.ejecutarSP("{ call actualizarDetalleMovimientoRepl(?,?,?,?,?) }");
    }

    public String toString() {
        return id_animal + " " + id_detalle + " " + id_movimiento;
    }
}
