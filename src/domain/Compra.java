package domain;

import static domain.Principal.log;
import static domain.Principal.manejadorBD;
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
            manejadorBD.parametrosSP.agregarParametro(id_compra, "varId", "STRING", "IN");
            manejadorBD.parametrosSP.agregarParametro(id_proveedor, "varIdProveedor", "STRING", "IN");
            manejadorBD.parametrosSP.agregarParametro(formatoDateTime.format(fecha), "varFecha", "STRING", "IN");
            manejadorBD.parametrosSP.agregarParametro(factura, "varFactura", "STRING", "IN");
            manejadorBD.parametrosSP.agregarParametro(orden, "varOrden", "STRING", "IN");
            manejadorBD.parametrosSP.agregarParametro(subtotal.toString(), "varSubtotal", "DOUBLE", "IN");
            manejadorBD.parametrosSP.agregarParametro(iva.toString(), "varIva", "DOUBLE", "IN");
            manejadorBD.parametrosSP.agregarParametro(total.toString(), "varTotal", "DOUBLE", "IN");

            manejadorBD.ejecutarSP("{ call actualizarCompraRepl(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }");

        } catch (ParseException ex) {
            log.log(ex.getMessage(), true);
            Logger
                    .getLogger(Compra.class
                            .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void actualizar_1() {

    }

    public void cargarDatos() {

    }

    public void cargarDatos_1() {

    }

}
