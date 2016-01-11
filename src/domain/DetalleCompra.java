/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import static domain.Principal.log;
import static domain.Principal.manejadorBD;
import static domain.Principal.ventana;
import java.util.Date;
import java.util.StringTokenizer;

/**
 *
 * @author Home
 */
public class DetalleCompra extends ExportTable {

    String id_rancho;
    String id_compra;
    String id_medicina;
    Integer id_detalle;
    Integer cantidad;
    Double presentacion;
    Double precio_unitario;
    Double importe;

    public DetalleCompra() {
        super("[detalle_compra]");
    }

    public void cargarDatos() {

        manejadorBD.consulta(""
                + "SELECT     d.id_rancho,    d.id_compra,    d.id_medicina,    \n"
                + "d.id_detalle,    d.cantidad,    d.presentacion,    \n"
                + "d.precio_unitario,    d.importe\n"
                + "FROM    detalle_compra d,    repl_detalle_compra r\n"
                + "WHERE    d.id_rancho = r.id_rancho        \n"
                + "AND d.id_compra = r.id_compra        \n"
                + "AND d.id_medicina = r.id_medicina        \n"
                + "AND d.id_detalle = r.id_detalle        \n"
                + "AND r.status = 'PR';");
    }

    public void actualizar(String cadena) {

        StringTokenizer st;

        String delete;

        log.log(cadena, true);

        st = new StringTokenizer(cadena, "|");
        System.out.println("cadena " + cadena);
        id_rancho = st.nextToken();
        id_compra = st.nextToken();
        id_medicina = st.nextToken();
        id_detalle = Integer.parseInt(st.nextToken());
        cantidad = Integer.parseInt(st.nextToken());
        presentacion = Double.parseDouble(st.nextToken());
        precio_unitario = Double.parseDouble(st.nextToken());
        importe = Double.parseDouble(st.nextToken());

        manejadorBD.parametrosSP = new ParametrosSP();

        manejadorBD.parametrosSP.agregarParametro(id_rancho, "varIdRancho", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(id_compra, "varIdCompra", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(id_medicina, "varIdMedicina", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(id_detalle.toString(), "varIdDetalle", "INT", "IN");
        manejadorBD.parametrosSP.agregarParametro(cantidad.toString(), "varCantidad", "INT", "IN");
        manejadorBD.parametrosSP.agregarParametro(presentacion.toString(), "varPresentacion", "DOUBLE", "IN");
        manejadorBD.parametrosSP.agregarParametro(precio_unitario.toString(), "varPrecioUnitario", "DOUBLE", "IN");
        manejadorBD.parametrosSP.agregarParametro(importe.toString(), "varImporte", "DOUBLE", "IN");

        manejadorBD.ejecutarSP("{ call actualizarDetalleCompraRepl(?,?,?,?,?,?,?,?) }");
    }

    public void cargarDatos_1(ManejadorBD bd, Date fecha) {

        bd.consulta(""
                + "SELECT     d.id_rancho,    d.id_compra,    d.id_medicina,    \n"
                + "d.id_detalle,    d.cantidad,    d.presentacion,    \n"
                + "d.precio_unitario,    d.importe\n"
                + "FROM    detalle_compra d,    repl_detalle_compra r\n"
                + "WHERE    d.id_rancho = r.id_rancho        \n"
                + "AND d.id_compra = r.id_compra        \n"
                + "AND d.id_medicina = r.id_medicina        \n"
                + "AND d.id_detalle = r.id_detalle        \n"
                + "AND      r.fecha >   '" + formatoDateTime.format(fecha) + "';");
    }

    public void actualizar_1(ManejadorBD origen, ManejadorBD destino) {
        for (int i = 0; i < origen.getRowCount(); i++) {

            id_rancho = origen.getValorString(i, 0);
            id_compra = origen.getValorString(i, 1);
            id_medicina = origen.getValorString(i, 2);
            id_detalle = Integer.parseInt(origen.getValorString(i, 3));
            cantidad = Integer.parseInt(origen.getValorString(i, 4));
            presentacion = Double.parseDouble(origen.getValorString(i, 5));
            precio_unitario = Double.parseDouble(origen.getValorString(i, 6));
            importe = Double.parseDouble(origen.getValorString(i, 7));

            destino.parametrosSP = new ParametrosSP();

            destino.parametrosSP.agregarParametro(id_rancho, "varIdRancho", "STRING", "IN");
            destino.parametrosSP.agregarParametro(id_compra, "varIdCompra", "STRING", "IN");
            destino.parametrosSP.agregarParametro(id_medicina, "varIdMedicina", "STRING", "IN");
            destino.parametrosSP.agregarParametro(id_detalle.toString(), "varIdDetalle", "INT", "IN");
            destino.parametrosSP.agregarParametro(cantidad.toString(), "varCantidad", "INT", "IN");
            destino.parametrosSP.agregarParametro(presentacion.toString(), "varPresentacion", "DOUBLE", "IN");
            destino.parametrosSP.agregarParametro(precio_unitario.toString(), "varPrecioUnitario", "DOUBLE", "IN");
            destino.parametrosSP.agregarParametro(importe.toString(), "varImporte", "DOUBLE", "IN");

            log.log("agregando " + this.toString(), false);
            
            destino.ejecutarSP("{ call actualizarDetalleCompraRepl(?,?,?,?,?,?,?,?) }");
            
            ventana.avanzar();
        }
    }

    public String toString() {
        return id_compra + " " + cantidad + " " + importe;
    }
}
