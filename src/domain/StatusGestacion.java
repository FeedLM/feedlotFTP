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
 * @author Marco
 */
class StatusGestacion extends ExportTable {

    String id_estatus_gestacion;
    String id_registro_empadre;
    String status;
    Date fecha_chequeo;
    String id_tipo_parto;

    public StatusGestacion() {
        super("[recepcion]");
    }

    public void actualizar(String cadena) {

        StringTokenizer st;

        log.log(cadena, false);

        st = new StringTokenizer(cadena, "|");

        try {
            id_estatus_gestacion = st.nextToken();
            id_registro_empadre = st.nextToken();
            status = st.nextToken();
            fecha_chequeo = formatoDateTime.parse(st.nextToken());
            id_tipo_parto = st.nextToken();

        } catch (ParseException ex) {
            log.log(ex.getMessage(), true);
            Logger.getLogger(StatusGestacion.class.getName()).log(Level.SEVERE, null, ex);
        }

        manejadorBD.parametrosSP = new ParametrosSP();

        manejadorBD.parametrosSP.agregarParametro(id_estatus_gestacion, "varIdEstatusGestacion", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(id_registro_empadre, "varIdRegistroEmpadre", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(status, "varStatus", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(formatoDateTime.format(fecha_chequeo), "varFechaChequeo", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(id_tipo_parto, "varIdTipoParto", "STRING", "IN");

        manejadorBD.ejecutarSP("{ call actualizarStatusGestacionRepl(?,?,?,?,?) }");
    }

    public void actualizar_1(ManejadorBD origen, ManejadorBD destino) {
        for (int i = 0; i < origen.getRowCount(); i++) {
            try {
                id_estatus_gestacion = origen.getValorString(i, 0);
                id_registro_empadre = origen.getValorString(i, 0);
                status = origen.getValorString(i, 0);
                fecha_chequeo = formatoDateTime.parse(origen.getValorString(i, 0));
                id_tipo_parto = origen.getValorString(i, 0);

            } catch (ParseException ex) {
                log.log(ex.getMessage(), true);
                Logger.getLogger(StatusGestacion.class.getName()).log(Level.SEVERE, null, ex);
            }

            manejadorBD.parametrosSP = new ParametrosSP();

            manejadorBD.parametrosSP.agregarParametro(id_estatus_gestacion, "varIdEstatusGestacion", "STRING", "IN");
            manejadorBD.parametrosSP.agregarParametro(id_registro_empadre, "varIdRegistroEmpadre", "STRING", "IN");
            manejadorBD.parametrosSP.agregarParametro(status, "varStatus", "STRING", "IN");
            manejadorBD.parametrosSP.agregarParametro(formatoDateTime.format(fecha_chequeo), "varFechaChequeo", "STRING", "IN");
            manejadorBD.parametrosSP.agregarParametro(id_tipo_parto, "varIdTipoParto", "STRING", "IN");

            manejadorBD.ejecutarSP("{ call actualizarStatusGestacionRepl(?,?,?,?,?) }");
        }
    }

    public void cargarDatos() {

        manejadorBD.consulta(""
                + "SELECT     s.id_estatus_gestacion,    s.id_registro_empadre,\n"
                + "    s.status,    s.fecha_chequeo,    s.id_tipo_parto\n"
                + "FROM    status_gestacion s,    repl_status_gestacion r\n"
                + "WHERE    s.id_estatus_gestacion = r.id_estatus_gestacion\n"
                + "        AND s.id_registro_empadre = r.id_registro_empadre\n"
                + "AND r.status = 'PR';");

    }

    public void cargarDatos_1(ManejadorBD bd, Date fecha) {

        bd.consulta(""
                + "SELECT     s.id_estatus_gestacion,    s.id_registro_empadre,\n"
                + "    s.status,    s.fecha_chequeo,    s.id_tipo_parto\n"
                + "FROM    status_gestacion s,    repl_status_gestacion r\n"
                + "WHERE    s.id_estatus_gestacion = r.id_estatus_gestacion\n"
                + "        AND s.id_registro_empadre = r.id_registro_empadre\n"
                + "AND      r.fecha >   '" + formatoDateTime.format(fecha) + "';");

    }

    public String toString() {
        return id_estatus_gestacion + " " + status + " " + formatoDateTime.format(fecha_chequeo);
    }
}
