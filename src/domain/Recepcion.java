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
 * @author Marco
 */
public class Recepcion extends ExportTable {

    String id_recepcion;
    String id_proveedor;
    String id_origen;
    String folio;
    Date fecha_compra;
    Date fecha_recepcion;
    Integer animales;
    Integer animales_pendientes;
    Double peso_origen;
    Double limite_merma;
    Double merma;
    Double porcentaje_merma;
    Double peso_recepcion;
    String numero_lote;
    Double costo_flete;
    Integer devoluciones;
    String causa_devolucion;
    Double total_alimento;

    public String toString(){
        return id_recepcion + " " + folio+ " " + animales + " " + formatoDateTime.format(fecha_compra) + " " + formatoDateTime.format(fecha_recepcion);
    }
    public Recepcion() {
        super("[recepcion]");
    }

    public void actualizar(String cadena) {

        StringTokenizer st;

        log.log(cadena, false);

        st = new StringTokenizer(cadena, "|");

        try {
            id_recepcion = st.nextToken();
            id_proveedor = st.nextToken();
            id_origen = st.nextToken();
            folio = st.nextToken();
            fecha_compra = formatoDateTime.parse(st.nextToken());
            fecha_recepcion = formatoDateTime.parse(st.nextToken());
            animales = Integer.parseInt(st.nextToken());
            animales_pendientes = Integer.parseInt(st.nextToken());
            peso_origen = Double.parseDouble(st.nextToken());
            limite_merma = Double.parseDouble(st.nextToken());
            merma = Double.parseDouble(st.nextToken());
            porcentaje_merma = Double.parseDouble(st.nextToken());
            peso_recepcion = Double.parseDouble(st.nextToken());
            numero_lote = st.nextToken();
            costo_flete = Double.parseDouble(st.nextToken());
            devoluciones = Integer.parseInt(st.nextToken());
            causa_devolucion = st.nextToken();
            total_alimento = Double.parseDouble(st.nextToken());

        } catch (ParseException ex) {
            log.log(ex.getMessage(), true);
            Logger.getLogger(Recepcion.class.getName()).log(Level.SEVERE, null, ex);
        }

        manejadorBD.parametrosSP = new ParametrosSP();

        manejadorBD.parametrosSP.agregarParametro(id_recepcion, "varIdRecepcion", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(id_proveedor, "varIdProveedor", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(id_origen, "varIdOrigen", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(folio, "varFolio", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(formatoDateTime.format(fecha_compra), "varFechaCompra", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(formatoDateTime.format(fecha_recepcion), "varFechaRecepcion", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(animales.toString(), "varAnimales", "INT", "IN");
        manejadorBD.parametrosSP.agregarParametro(animales_pendientes.toString(), "varAnimalesPendientes", "INT", "IN");
        manejadorBD.parametrosSP.agregarParametro(peso_origen.toString(), "varPesoOrigen", "DOUBLE", "IN");
        manejadorBD.parametrosSP.agregarParametro(limite_merma.toString(), "varLimiteMerma", "DOUBLE", "IN");
        manejadorBD.parametrosSP.agregarParametro(merma.toString(), "varMerma", "DOUBLE", "IN");
        manejadorBD.parametrosSP.agregarParametro(porcentaje_merma.toString(), "varPorcentajeMerma", "DOUBLE", "IN");
        manejadorBD.parametrosSP.agregarParametro(peso_recepcion.toString(), "varPesoRecepcion", "DOUBLE", "IN");
        manejadorBD.parametrosSP.agregarParametro(numero_lote, "varNumeroLote", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(costo_flete.toString(), "varCostoFlete", "DOUBLE", "IN");
        manejadorBD.parametrosSP.agregarParametro(devoluciones.toString(), "varDevoluciones", "INT", "IN");
        manejadorBD.parametrosSP.agregarParametro(causa_devolucion, "varCausaDevolucion", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(total_alimento.toString(), "varTotalAlimento", "DOUBLE", "IN");

        manejadorBD.ejecutarSP("{ call actualizarRecepcionRepl(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }");
    }

    public void actualizar_1(ManejadorBD origen, ManejadorBD destino) {
        for (int i = 0; i < origen.getRowCount(); i++) {
            try {
                id_recepcion = origen.getValorString(i, 0);
                id_proveedor = origen.getValorString(i, 1);
                id_origen = origen.getValorString(i, 2);
                folio = origen.getValorString(i, 3);
                fecha_compra = formatoDateTime.parse(origen.getValorString(i, 4));
                fecha_recepcion = formatoDateTime.parse(origen.getValorString(i, 5));
                animales = Integer.parseInt(origen.getValorString(i, 6));
                animales_pendientes = Integer.parseInt(origen.getValorString(i, 7));
                peso_origen = Double.parseDouble(origen.getValorString(i, 8));
                limite_merma = Double.parseDouble(origen.getValorString(i, 9));
                merma = Double.parseDouble(origen.getValorString(i, 10));
                porcentaje_merma = Double.parseDouble(origen.getValorString(i, 11));
                peso_recepcion = Double.parseDouble(origen.getValorString(i, 12));
                numero_lote = origen.getValorString(i, 13);
                costo_flete = Double.parseDouble(origen.getValorString(i, 14));
                devoluciones = Integer.parseInt(origen.getValorString(i, 15));
                causa_devolucion = origen.getValorString(i, 16);
                total_alimento = Double.parseDouble(origen.getValorString(i, 17));

            } catch (ParseException ex) {
                log.log(ex.getMessage(), true);
                Logger.getLogger(Recepcion.class.getName()).log(Level.SEVERE, null, ex);
            }

            destino.parametrosSP = new ParametrosSP();

            destino.parametrosSP.agregarParametro(id_recepcion, "varIdRecepcion", "STRING", "IN");
            destino.parametrosSP.agregarParametro(id_proveedor, "varIdProveedor", "STRING", "IN");
            destino.parametrosSP.agregarParametro(id_origen, "varIdOrigen", "STRING", "IN");
            destino.parametrosSP.agregarParametro(folio, "varFolio", "STRING", "IN");
            destino.parametrosSP.agregarParametro(formatoDateTime.format(fecha_compra), "varFechaCompra", "STRING", "IN");
            destino.parametrosSP.agregarParametro(formatoDateTime.format(fecha_recepcion), "varFechaRecepcion", "STRING", "IN");
            destino.parametrosSP.agregarParametro(animales.toString(), "varAnimales", "INT", "IN");
            destino.parametrosSP.agregarParametro(animales_pendientes.toString(), "varAnimalesPendientes", "INT", "IN");
            destino.parametrosSP.agregarParametro(peso_origen.toString(), "varPesoOrigen", "DOUBLE", "IN");
            destino.parametrosSP.agregarParametro(limite_merma.toString(), "varLimiteMerma", "DOUBLE", "IN");
            destino.parametrosSP.agregarParametro(merma.toString(), "varMerma", "DOUBLE", "IN");
            destino.parametrosSP.agregarParametro(porcentaje_merma.toString(), "varPorcentajeMerma", "DOUBLE", "IN");
            destino.parametrosSP.agregarParametro(peso_recepcion.toString(), "varPesoRecepcion", "DOUBLE", "IN");
            destino.parametrosSP.agregarParametro(numero_lote, "varNumeroLote", "STRING", "IN");
            destino.parametrosSP.agregarParametro(costo_flete.toString(), "varCostoFlete", "DOUBLE", "IN");
            destino.parametrosSP.agregarParametro(devoluciones.toString(), "varDevoluciones", "INT", "IN");
            destino.parametrosSP.agregarParametro(causa_devolucion, "varCausaDevolucion", "STRING", "IN");
            destino.parametrosSP.agregarParametro(total_alimento.toString(), "varTotalAlimento", "DOUBLE", "IN");

            destino.ejecutarSP("{ call actualizarRecepcionRepl(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }");
        }
    }

    public void cargarDatos() {

        manejadorBD.consulta(""
                + "SELECT     c.id_recepcion,    c.id_proveedor,    c.id_origen,\n"
                + "    c.folio,    c.fecha_compra,    c.fecha_recepcion,\n"
                + "    c.animales,    c.animales_pendientes,    c.peso_origen,\n"
                + "    c.limite_merma,    c.merma,    c.porcentaje_merma,\n"
                + "    c.peso_recepcion,    c.numero_lote,    c.costo_flete,\n"
                + "    c.devoluciones,    c.causa_devolucion,    c.total_alimento\n"
                + "FROM    recepcion c,    repl_recepcion r\n"
                + "WHERE    c.id_recepcion = r.id_recepcion        \n"
                + "AND c.id_proveedor = r.id_proveedor        AND c.id_origen = r.id_origen\n"
                + "AND r.status = 'PR';");

    }

    public void cargarDatos_1(ManejadorBD bd, Date fecha) {

        bd.consulta(""
                + "SELECT     c.id_recepcion,    c.id_proveedor,    c.id_origen,\n"
                + "    c.folio,    c.fecha_compra,    c.fecha_recepcion,\n"
                + "    c.animales,    c.animales_pendientes,    c.peso_origen,\n"
                + "    c.limite_merma,    c.merma,    c.porcentaje_merma,\n"
                + "    c.peso_recepcion,    c.numero_lote,    c.costo_flete,\n"
                + "    c.devoluciones,    c.causa_devolucion,    c.total_alimento\n"
                + "FROM    recepcion c,    repl_recepcion r\n"
                + "WHERE    c.id_recepcion = r.id_recepcion        \n"
                + "AND c.id_proveedor = r.id_proveedor        AND c.id_origen = r.id_origen\n"
                + "AND      r.fecha >   '" + formatoDateTime.format(fecha) + "';");

    }

}
