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
 * @author Home
 */
public class ControlGestacion extends ExportTable {

    String id_control_gestacion;
    String id_registro_empadre;
    String status;
    Date fecha;
    String tipo_parto;

    public ControlGestacion() {
        super("[control_gestacion]");
    }

    public void cargarDatos() {
        manejadorBD.consulta(
                "SELECT     cg.id_control_gestacion,\n"
                + "    cg.id_registro_empadre,    cg.status,\n"
                + "    cg.fecha,    cg.tipo_parto\n"
                + "FROM    control_gestacion cg,    repl_control_gestacion r\n"
                + "WHERE    cg.id_control_gestacion = r.id_control_gestacion\n"
                + "        AND cg.id_registro_empadre = r.id_registro_empadre\n"
                + "        AND r.status = 'PR';");
    }

    public void cargarDatos_1(ManejadorBD bd, Date fecha) {

        bd.consulta(""
                + "SELECT     cg.id_control_gestacion,\n"
                + "    cg.id_registro_empadre,    cg.status,\n"
                + "    cg.fecha,    cg.tipo_parto\n"
                + "FROM    control_gestacion cg,    repl_control_gestacion r\n"
                + "WHERE    cg.id_control_gestacion = r.id_control_gestacion\n"
                + "        AND cg.id_registro_empadre = r.id_registro_empadre\n"
                + "AND      r.fecha >   '" + formatoDateTime.format(fecha) + "';");
    }

    public void actualizar_1(ManejadorBD origen, ManejadorBD destino) {

        for (int i = 0; i < origen.getRowCount(); i++) {
            try {
                id_control_gestacion = origen.getValorString(i, 0);
                id_registro_empadre = origen.getValorString(i, 1);
                status = origen.getValorString(i, 2);
                fecha = formatoDateTime.parse(origen.getValorString(i, 3));
                tipo_parto = origen.getValorString(i, 4);

                destino.parametrosSP = new ParametrosSP();

                destino.parametrosSP.agregarParametro(id_control_gestacion, "varIdControlGestacion", "STRING", "IN");
                destino.parametrosSP.agregarParametro(id_registro_empadre, "varIdRegistroEmpadre", "STRING", "IN");
                destino.parametrosSP.agregarParametro(status, "varStatus", "STRING", "IN");
                destino.parametrosSP.agregarParametro(formatoDateTime.format(fecha), "varFecha", "STRING", "IN");
                destino.parametrosSP.agregarParametro(tipo_parto, "varTipoParto", "STRING", "IN");

                destino.ejecutarSP("{ call actualizarControlGestacionRepl(?,?,?,?) }");

            } catch (ParseException ex) {
                Logger.getLogger(ControlGestacion.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void actualizar(String cadena) {

        log.log(cadena, false);

        try {
            StringTokenizer st;

            st = new StringTokenizer(cadena, "|");

            System.out.println(cadena);
            
            id_control_gestacion = st.nextToken();
            id_registro_empadre = st.nextToken();
            status = st.nextToken();
            fecha = formatoDateTime.parse(st.nextToken());
            tipo_parto = st.nextToken();

            manejadorBD.parametrosSP = new ParametrosSP();

            manejadorBD.parametrosSP.agregarParametro(id_control_gestacion, "varIdControlGestacion", "STRING", "IN");
            manejadorBD.parametrosSP.agregarParametro(id_registro_empadre, "varIdRegistroEmpadre", "STRING", "IN");
            manejadorBD.parametrosSP.agregarParametro(status, "varStatus", "STRING", "IN");
            manejadorBD.parametrosSP.agregarParametro(formatoDateTime.format(fecha), "varFecha", "STRING", "IN");
            manejadorBD.parametrosSP.agregarParametro(tipo_parto, "varTipoParto", "STRING", "IN");

            manejadorBD.ejecutarSP("{ call actualizarControlGestacionRepl(?,?,?,?) }");

        } catch (ParseException ex) {
            log.log(ex.getMessage(), true);
            Logger
                    .getLogger(Animal.class
                            .getName()).log(Level.SEVERE, null, ex);
        }
    }
}
