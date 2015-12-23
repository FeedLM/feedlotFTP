/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import static domain.Principal.log;
import static domain.Principal.manejadorBD;
import static domain.Proceso.formatoDateTime;
import java.text.ParseException;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Home
 */
class IngresoAlimento {

    String id_ingreso_alimento;
    String numero_lote;
    String id_corral;
    Double total_alimento;
    Date fecha;
    Double costo_unitario;
    Double costo_total;
    String carro;

    public void actualizar(String cadena) {
        try {
            StringTokenizer st;

            String delete;

            log.log(cadena, true);

            st = new StringTokenizer(cadena, "|");
            System.out.println("cadena " + cadena);

            id_ingreso_alimento = st.nextToken();
            numero_lote = st.nextToken();
            id_corral = st.nextToken();
            total_alimento = Double.parseDouble(st.nextToken());
            fecha = formatoDateTime.parse(st.nextToken());
            costo_unitario = Double.parseDouble(st.nextToken());
            costo_total = Double.parseDouble(st.nextToken());
            carro = st.nextToken();

            manejadorBD.parametrosSP = new ParametrosSP();

            manejadorBD.parametrosSP.agregarParametro(id_ingreso_alimento, "varIdIngresoAlimento", "STRING", "IN");
            manejadorBD.parametrosSP.agregarParametro(numero_lote, "varNumeroLote", "STRING", "IN");
            manejadorBD.parametrosSP.agregarParametro(id_corral, "varIdCorral", "STRING", "IN");
            manejadorBD.parametrosSP.agregarParametro(total_alimento.toString(), "varTotalAlimento", "DOUBLE", "IN");
            manejadorBD.parametrosSP.agregarParametro(formatoDateTime.format(fecha), "varFecha", "STRING", "IN");
            manejadorBD.parametrosSP.agregarParametro(costo_unitario.toString(), "varCostoUnitario", "DOUBLE", "IN");
            manejadorBD.parametrosSP.agregarParametro(costo_total.toString(), "varCostoTotal", "DOUBLE", "IN");
            manejadorBD.parametrosSP.agregarParametro(carro, "varCarro", "STRING", "IN");

            manejadorBD.ejecutarSP("{ call actualizarIngresoAlimentoRepl(?,?,?,?,?,?,?,?) }");
        } catch (ParseException ex) {
            Logger.getLogger(IngresoAlimento.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cargarDatos() {

        manejadorBD.consulta(""
                + "SELECT  g.id_animal, g.id_madre, g.id_padre\n"
                + "FROM genealogia g, repl_genealogia x\n"
                + "WHERE x.id_animal = g.id_animal AND x.id_madre = g.id_madre \n"
                + "AND x.id_padre = g.id_padre \n"
                + "AND x.id_padre = g.id_padre AND r.status = 'PR';");
    }

    void actualizar_1(ManejadorBD origen, ManejadorBD destino) {
        for (int i = 0; i < origen.getRowCount(); i++) {
            try {
                id_ingreso_alimento = origen.getValorString(i, 0);
                numero_lote = origen.getValorString(i, 1);
                id_corral = origen.getValorString(i, 2);
                total_alimento = Double.parseDouble(origen.getValorString(i, 3));
                fecha = formatoDateTime.parse(origen.getValorString(i, 4));
                costo_unitario = Double.parseDouble(origen.getValorString(i, 5));
                costo_total = Double.parseDouble(origen.getValorString(i, 6));
                carro = origen.getValorString(i, 7);

                manejadorBD.parametrosSP = new ParametrosSP();

                manejadorBD.parametrosSP.agregarParametro(id_ingreso_alimento, "varIdIngresoAlimento", "STRING", "IN");
                manejadorBD.parametrosSP.agregarParametro(numero_lote, "varNumeroLote", "STRING", "IN");
                manejadorBD.parametrosSP.agregarParametro(id_corral, "varIdCorral", "STRING", "IN");
                manejadorBD.parametrosSP.agregarParametro(total_alimento.toString(), "varTotalAlimento", "DOUBLE", "IN");
                manejadorBD.parametrosSP.agregarParametro(formatoDateTime.format(fecha), "varFecha", "STRING", "IN");
                manejadorBD.parametrosSP.agregarParametro(costo_unitario.toString(), "varCostoUnitario", "DOUBLE", "IN");
                manejadorBD.parametrosSP.agregarParametro(costo_total.toString(), "varCostoTotal", "DOUBLE", "IN");
                manejadorBD.parametrosSP.agregarParametro(carro, "varCarro", "STRING", "IN");

                manejadorBD.ejecutarSP("{ call actualizarIngresoAlimentoRepl(?,?,?,?,?,?,?,?) }");
            } catch (ParseException ex) {
                Logger.getLogger(IngresoAlimento.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    void cargarDatos_1(ManejadorBD bd, Date fecha) {
        bd.consulta(""
                + "SELECT  g.id_animal, g.id_madre, g.id_padre\n"
                + "FROM genealogia g, repl_genealogia x\n"
                + "WHERE x.id_animal = g.id_animal AND x.id_madre = g.id_madre \n"
                + "AND x.id_padre = g.id_padre \n"
                + "AND      x.fecha >   '" + formatoDateTime.format(fecha) + "';");
    }

}
