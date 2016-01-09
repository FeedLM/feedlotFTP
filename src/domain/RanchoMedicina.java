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
class RanchoMedicina extends ExportTable {

    String id_rancho;
    String id_medicina;
    Integer existencia_inicial;
    Integer existencia;
    Double costo_promedio;
    Double ultimo_costo;
    Date ultima_compra;

    public RanchoMedicina() {
        super("[rancho_medicina]");
    }

    public void actualizar(String cadena) {

        StringTokenizer st;

        log.log(cadena, false);

        st = new StringTokenizer(cadena, "|");

        id_rancho = st.nextToken();
        id_medicina = st.nextToken();
        existencia_inicial = Integer.parseInt(st.nextToken());
        existencia = Integer.parseInt(st.nextToken());
        costo_promedio = Double.parseDouble(st.nextToken());
        ultimo_costo = Double.parseDouble(st.nextToken());

        try {
            ultima_compra = formatoDateTime.parse(st.nextToken());
        } catch (ParseException ex) {
            log.log(ex.getMessage(), true);
            Logger.getLogger(RanchoMedicina.class.getName()).log(Level.SEVERE, null, ex);
        }

        manejadorBD.parametrosSP = new ParametrosSP();

        manejadorBD.parametrosSP.agregarParametro(id_rancho, "varIdRancho", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(id_medicina, "varIdMedicina", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(existencia_inicial.toString(), "varExistenciaInicial", "INT", "IN");
        manejadorBD.parametrosSP.agregarParametro(existencia.toString(), "varExistencia", "INT", "IN");
        manejadorBD.parametrosSP.agregarParametro(costo_promedio.toString(), "varCostoPromedio", "DOUBLE", "IN");
        manejadorBD.parametrosSP.agregarParametro(ultimo_costo.toString(), "varUltimoCosto", "DOUBLE", "IN");
        manejadorBD.parametrosSP.agregarParametro(formatoDateTime.format(ultima_compra), "varUltimaCompra", "STRING", "IN");

        manejadorBD.ejecutarSP("{ call actualizarRanchoMedicinaRepl(?,?,?,?,?,?,?) }");
    }

    public void actualizar_1(ManejadorBD origen, ManejadorBD destino) {
        for (int i = 0; i < origen.getRowCount(); i++) {
            id_rancho = origen.getValorString(i, 0);
            id_medicina = origen.getValorString(i, 1);
            existencia_inicial = Integer.parseInt(origen.getValorString(i, 2));
            existencia = Integer.parseInt(origen.getValorString(i, 3));
            costo_promedio = Double.parseDouble(origen.getValorString(i, 4));
            ultimo_costo = Double.parseDouble(origen.getValorString(i, 5));

            try {
                ultima_compra = formatoDateTime.parse(origen.getValorString(i, 6));
            } catch (ParseException ex) {
                log.log(ex.getMessage(), true);
                Logger.getLogger(RanchoMedicina.class.getName()).log(Level.SEVERE, null, ex);
            }

            manejadorBD.parametrosSP = new ParametrosSP();

            manejadorBD.parametrosSP.agregarParametro(id_rancho, "varIdRancho", "STRING", "IN");
            manejadorBD.parametrosSP.agregarParametro(id_medicina, "varIdMedicina", "STRING", "IN");
            manejadorBD.parametrosSP.agregarParametro(existencia_inicial.toString(), "varExistenciaInicial", "INT", "IN");
            manejadorBD.parametrosSP.agregarParametro(existencia.toString(), "varExistencia", "INT", "IN");
            manejadorBD.parametrosSP.agregarParametro(costo_promedio.toString(), "varCostoPromedio", "DOUBLE", "IN");
            manejadorBD.parametrosSP.agregarParametro(ultimo_costo.toString(), "varUltimoCosto", "DOUBLE", "IN");
            manejadorBD.parametrosSP.agregarParametro(formatoDateTime.format(ultima_compra), "varUltimaCompra", "STRING", "IN");

            manejadorBD.ejecutarSP("{ call actualizarRanchoMedicinaRepl(?,?,?,?,?,?,?) }");
        }
    }

    public void cargarDatos() {

        manejadorBD.consulta(""
                + "SELECT     m.id_rancho,    m.id_medicina,    m.existencia_inicial,    \n"
                + "m.existencia,    m.costo_promedio,    m.ultimo_costo,    m.ultima_compra\n"
                + "FROM    rancho_medicina m,    repl_rancho_medicina r\n"
                + "WHERE    m.id_rancho = r.id_rancho        AND m.id_medicina = r.id_medicina        \n"
                + "AND r.status = 'PR';");

    }

    public void cargarDatos_1(ManejadorBD bd, Date fecha) {

        bd.consulta(""
                + "SELECT     m.id_rancho,    m.id_medicina,    m.existencia_inicial,    \n"
                + "m.existencia,    m.costo_promedio,    m.ultimo_costo,    m.ultima_compra\n"
                + "FROM    rancho_medicina m,    repl_rancho_medicina r\n"
                + "WHERE    m.id_rancho = r.id_rancho        AND m.id_medicina = r.id_medicina        \n"
                + "AND      r.fecha >   '" + formatoDateTime.format(fecha) + "';");

    }

    public String toString() {
        return id_rancho + " " + id_medicina + " " + existencia+ " " + costo_promedio.toString();
    }
}
