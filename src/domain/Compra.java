package domain;

import static domain.Principal.log;
import static domain.Principal.manejadorBD;
import static domain.Principal.ventana;
import java.text.ParseException;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Compra extends ExportTable {

    public String id_rancho;
    public String id_compra;
    public String id_proveedor;
    public Date fecha;
    public String factura;
    public String orden;
    public Double subtotal;
    public Double iva;
    public Double total;

    public Compra() {
        super("[compra]");
    }

    public void actualizar(String cadena) {

        log.log(cadena, false);

        try {
            StringTokenizer st;

            st = new StringTokenizer(cadena, "|");

            System.out.println(cadena);

            id_rancho = st.nextToken();
            id_compra = st.nextToken();
            id_proveedor = st.nextToken();
            fecha = formatoDateTime.parse(st.nextToken());
            factura = st.nextToken();
            orden = st.nextToken();
            subtotal = Double.parseDouble(st.nextToken());
            iva = Double.parseDouble(st.nextToken());
            total = Double.parseDouble(st.nextToken());

            manejadorBD.parametrosSP = new ParametrosSP();

            manejadorBD.parametrosSP.agregarParametro(id_rancho, "varIdRancho", "STRING", "IN");
            manejadorBD.parametrosSP.agregarParametro(id_compra, "varIdCompra", "STRING", "IN");
            manejadorBD.parametrosSP.agregarParametro(id_proveedor, "varIdProveedor", "STRING", "IN");
            manejadorBD.parametrosSP.agregarParametro(formatoDateTime.format(fecha), "varFecha", "STRING", "IN");
            manejadorBD.parametrosSP.agregarParametro(factura, "varFactura", "STRING", "IN");
            manejadorBD.parametrosSP.agregarParametro(orden, "varOrden", "STRING", "IN");
            manejadorBD.parametrosSP.agregarParametro(subtotal.toString(), "varSubtotal", "DOUBLE", "IN");
            manejadorBD.parametrosSP.agregarParametro(iva.toString(), "varIva", "DOUBLE", "IN");
            manejadorBD.parametrosSP.agregarParametro(total.toString(), "varTotal", "DOUBLE", "IN");

            manejadorBD.ejecutarSP("{ call actualizarCompraRepl(?,?,?,?,?,?,?,?,?) }");

        } catch (ParseException ex) {
            log.log(ex.getMessage(), true);
            Logger
                    .getLogger(Compra.class
                            .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void actualizar_1(ManejadorBD origen, ManejadorBD destino) {
        for (int i = 0; i < origen.getRowCount(); i++) {

            try {

                id_rancho = origen.getValorString(i, 0);
                id_compra = origen.getValorString(i, 1);
                id_proveedor = origen.getValorString(i, 2);
                fecha = formatoDateTime.parse(origen.getValorString(i, 3));
                factura = origen.getValorString(i, 4);;
                orden = origen.getValorString(i, 5);
                subtotal = Double.parseDouble(origen.getValorString(i, 6));
                iva = Double.parseDouble(origen.getValorString(i, 7));
                total = Double.parseDouble(origen.getValorString(i, 8));

                destino.parametrosSP = new ParametrosSP();

                destino.parametrosSP.agregarParametro(id_rancho, "varIdRancho", "STRING", "IN");
                destino.parametrosSP.agregarParametro(id_compra, "varIdCompra", "STRING", "IN");
                destino.parametrosSP.agregarParametro(id_proveedor, "varIdProveedor", "STRING", "IN");
                destino.parametrosSP.agregarParametro(formatoDateTime.format(fecha), "varFecha", "STRING", "IN");
                destino.parametrosSP.agregarParametro(factura, "varFactura", "STRING", "IN");
                destino.parametrosSP.agregarParametro(orden, "varOrden", "STRING", "IN");
                destino.parametrosSP.agregarParametro(subtotal.toString(), "varSubtotal", "DOUBLE", "IN");
                destino.parametrosSP.agregarParametro(iva.toString(), "varIva", "DOUBLE", "IN");
                destino.parametrosSP.agregarParametro(total.toString(), "varTotal", "DOUBLE", "IN");

                log.log("agregando " + this.toString(), false);
                
                destino.ejecutarSP("{ call actualizarCompraRepl(?,?,?,?,?,?,?,?,?) }");
                                                                
                ventana.avanzar();
                
            } catch (ParseException ex) {
                log.log(ex.getMessage(), true);
                Logger
                        .getLogger(Compra.class
                                .getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void cargarDatos() {
        manejadorBD.consulta(""
                + "SELECT c.id_rancho,  c.id_compra,    c.id_proveedor, "
                + "c.fecha,       factura,    orden, "
                + "subtotal,    iva,    total "
                + "FROM     compra c, repl_compra r "
                + "WHERE        c.id_rancho     =       r.id_rancho "
                + "AND          c.id_compra     =       r.id_compra "
                + "AND          c.id_proveedor  =       r.id_proveedor"
                + "AND          r.status        =       'PR';"
                + "");
    }

    public void cargarDatos_1(ManejadorBD bd, Date fecha) {
        bd.consulta(""
                + "SELECT c.id_rancho, c.id_compra, c.id_proveedor,\n"
                + "       COALESCE(c.fecha,'1900-01-01 00:00:00'),     COALESCE(factura,''),     COALESCE(orden,''),\n"
                + "       COALESCE(subtotal,0),    COALESCE(iva,0),         COALESCE(total,0)\n"
                + "FROM   compra c, repl_compra r\n"
                + "WHERE  c.id_rancho    = r.id_rancho\n"
                + "AND    c.id_compra    = r.id_compra\n"
                + "AND    c.id_proveedor = r.id_proveedor\n"
                + "AND    r.fecha >   '" + formatoDateTime.format(fecha) + "';");
    }

    public String toString() {
        return id_compra + " " + formatoDateTime.format(fecha) + " " + total.toString();
    }
}
