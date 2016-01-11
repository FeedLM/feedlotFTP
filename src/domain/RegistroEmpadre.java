package domain;

import static domain.Principal.log;
import static domain.Principal.manejadorBD;
import java.text.ParseException;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RegistroEmpadre extends ExportTable {

    String id_registro_empadre;
    Date fecha;
    String id_hembra;
    String id_semental;
    String status_gestacional;
    String aborto;
    String id_tipo_parto;
    String activo;

    public RegistroEmpadre() {
        super("[registro_empadre]");
    }

    public void cargarDatos() {

        manejadorBD.consulta(""
                + "SELECT     e.id_registro_empadre,    e.fecha,    e.id_hembra,    e.id_semental,\n"
                + "    e.status_gestacional,    e.aborto,    e.id_tipo_parto,    e.activo\n"
                + "FROM    registro_empadre e,    repl_registro_empadre r\n"
                + "WHERE e.id_registro_empadre = r.id_registro_empadre \n"
                + "AND e.id_hembra = r.id_hembra\n"
                + "AND e.id_semental = r.id_semental"
                + "AND      r.status    =   'PR';");
    }

    void cargarDatos_1(ManejadorBD bd, Date fecha) {
        bd.consulta(""
                + "SELECT     e.id_registro_empadre,    e.fecha,    e.id_hembra,    e.id_semental,\n"
                + "    e.status_gestacional,    e.aborto,    e.id_tipo_parto,    e.activo\n"
                + "FROM    registro_empadre e,    repl_registro_empadre r\n"
                + "WHERE e.id_registro_empadre = r.id_registro_empadre \n"
                + "AND e.id_hembra = r.id_hembra\n"
                + "AND e.id_semental = r.id_semental"
                + "AND      r.fecha >   '" + formatoDateTime.format(fecha) + "';");
    }

    void actualizar_1(ManejadorBD origen, ManejadorBD destino) {

        for (int i = 0; i < origen.getRowCount(); i++) {
            try {
                id_registro_empadre = origen.getValorString(i, 0);
                fecha = formatoDateTime.parse(origen.getValorString(i, 1));
                id_hembra = origen.getValorString(i, 2);
                id_semental = origen.getValorString(i, 3);
                status_gestacional = origen.getValorString(i, 4);
                aborto = origen.getValorString(i, 5);
                id_tipo_parto = origen.getValorString(i, 6);
                activo = origen.getValorString(i, 7);

                destino.parametrosSP = new ParametrosSP();

                destino.parametrosSP.agregarParametro(id_registro_empadre, "varIdRegistroEmpadre", "STRING", "IN");
                destino.parametrosSP.agregarParametro(formatoDateTime.format(fecha), "varFecha", "STRING", "IN");
                destino.parametrosSP.agregarParametro(id_hembra, "varIdHembra", "STRING", "IN");
                destino.parametrosSP.agregarParametro(id_semental, "varIdSemental", "STRING", "IN");
                destino.parametrosSP.agregarParametro(status_gestacional, "varStatusGestacional", "STRING", "IN");
                destino.parametrosSP.agregarParametro(aborto, "varAborto", "STRING", "IN");
                destino.parametrosSP.agregarParametro(id_tipo_parto, "varIdTipoParto", "STRING", "IN");
                destino.parametrosSP.agregarParametro(activo, "varActivo", "STRING", "IN");
                destino.ejecutarSP("{ call actualizarRegistroEmpadreRepl(?,?,?,?,?,?,?,?) }");
            } catch (ParseException ex) {
                Logger.getLogger(RegistroEmpadre.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void actualizar(String cadena) {

        log.log(cadena, false);

        StringTokenizer st;
        st = new StringTokenizer(cadena, "|");
        System.out.println(cadena);
        try {
            id_registro_empadre = st.nextToken();
            fecha = formatoDateTime.parse(st.nextToken());
            id_hembra = st.nextToken();
            id_semental = st.nextToken();
            status_gestacional = st.nextToken();
            aborto = st.nextToken();
            id_tipo_parto = st.nextToken();
            activo = st.nextToken();

            manejadorBD.parametrosSP = new ParametrosSP();

            manejadorBD.parametrosSP.agregarParametro(id_registro_empadre, "varIdRegistroEmpadre", "STRING", "IN");
            manejadorBD.parametrosSP.agregarParametro(formatoDateTime.format(fecha), "varFecha", "STRING", "IN");
            manejadorBD.parametrosSP.agregarParametro(id_hembra, "varIdHembra", "STRING", "IN");
            manejadorBD.parametrosSP.agregarParametro(id_semental, "varIdSemental", "STRING", "IN");
            manejadorBD.parametrosSP.agregarParametro(status_gestacional, "varStatusGestacional", "STRING", "IN");
            manejadorBD.parametrosSP.agregarParametro(aborto, "varAborto", "STRING", "IN");
            manejadorBD.parametrosSP.agregarParametro(id_tipo_parto, "varIdTipoParto", "STRING", "IN");
            manejadorBD.parametrosSP.agregarParametro(activo, "varActivo", "STRING", "IN");
            manejadorBD.ejecutarSP("{ call actualizarRegistroEmpadreRepl(?,?,?,?,?,?,?,?) }");
        } catch (ParseException ex) {
            Logger.getLogger(RegistroEmpadre.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }

    public String toString() {
        return id_registro_empadre+ " " + fecha + " " + id_hembra + " " + id_semental;
    }
}
