/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import static domain.Principal.log;
import static domain.Principal.manejadorBD;
import java.text.ParseException;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Developer GAGS
 */
public class MovimientoAnimal extends ExportTable {

    /*
        DEROGADO
        EL TRIGGER DE DETALLE DE MOVIMIENTOS METE EL MOVIMIENTO ANIMAL
     */
    String id_rancho;
    String id_movimiento;
    String id_concepto;
    String id_animal;

    public MovimientoAnimal() {

        super("[movimientoAnimal]");
    }

    public void cargarDatos() {

        manejadorBD.consulta(""
                + "SELECT   m.id_rancho,    m.id_movimiento,                \n"
                + "         m.id_concepto,  m.id_animal                     \n"
                + "FROM     movimiento_animal m, repl_detalle_movimiento r  \n"
                + "WHERE    m.id_rancho     =	r.id_rancho                 \n"
                + "AND      m.id_movimiento =	r.id_movimiento             \n"
                + "AND      m.id_concepto   =	r.id_concepto               \n"
                + "AND      m.id_animal     =	r.id_animal                 \n"
                + "AND      r.status        =   'PR';");
    }

    public void actualizar(String cadena) {

        StringTokenizer st;

        String delete;

        log.log(cadena, false);

        st = new StringTokenizer(cadena, "|");

        id_rancho = st.nextToken();
        id_movimiento = st.nextToken();
        id_concepto = st.nextToken();
        id_animal = st.nextToken();

        manejadorBD.parametrosSP = new ParametrosSP();

        manejadorBD.parametrosSP.agregarParametro(id_rancho, "varIdRancho", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(id_movimiento, "varIdMovimiento", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(id_concepto, "varIdConcepto", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(id_animal, "varIdAnimalvar", "STRING", "IN");

        manejadorBD.ejecutarSP("{ call actualizarMovimientoAnimalRepl(?,?,?,?) }");
    }

    public String toString() {
        return id_movimiento + " " + id_concepto + " " + id_animal;
    }

}
