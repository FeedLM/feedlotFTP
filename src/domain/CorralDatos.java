/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import static domain.Principal.log;
import static domain.Principal.manejadorBD;
import static domain.Principal.ventana;
import java.text.ParseException;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Developer GAGS
 */
public class CorralDatos extends ExportTable {

    public String id_rancho;
    public String id_corral;
    public String categoria;
    public String ganado_amedias;
    public String color_arete;
    public Date fecha_nacimiento;
    public String numero_lote;
    public String compra;
    public Double porcentaje;
    public String id_proveedor;

    public CorralDatos() {

        super("[corralDatos]");
    }

    public void cargarDatos() {

        manejadorBD.consulta(""
                + "SELECT   c.id_rancho,	c.id_corral,        \n"
                + "         c.categoria, 	c.ganado_amedias,   \n"
                + "         c.color_arete,	c.fecha_nacimiento, \n"
                + "         c.numero_lote,	c.compra,           \n"
                + "         c.porcentaje,       c.id_proveedor      \n"
                + "FROM	    corral_datos c,	repl_corral_datos r \n"
                + "WHERE    c.id_rancho	=   r.id_rancho             \n"
                + "AND      c.id_corral	=   r.id_corral             \n"
                + "AND      r.status	=   'PR';");
    }

    public void actualizar(String cadena) {

        StringTokenizer st;

        String delete;

        log.log(cadena, false);

        st = new StringTokenizer(cadena, "|");

        id_rancho = st.nextToken();
        id_corral = st.nextToken();
        categoria = st.nextToken();
        ganado_amedias = st.nextToken();
        color_arete = st.nextToken();

        try {
            fecha_nacimiento = formatoDateTime.parse(st.nextToken());
        } catch (ParseException ex) {
            log.log(ex.getMessage(), true);
            Logger.getLogger(Movimiento.class.getName()).log(Level.SEVERE, null, ex);
        }

        numero_lote = st.nextToken();
        compra = st.nextToken();
        porcentaje = Double.parseDouble(st.nextToken());
        id_proveedor = st.nextToken();

        manejadorBD.parametrosSP = new ParametrosSP();

        manejadorBD.parametrosSP.agregarParametro(id_rancho, "varIdRancho", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(id_corral, "varIdCorral", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(categoria, "varCategoria", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(ganado_amedias, "varGanadoAmedias", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(color_arete, "varColorArete", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(formatoDateTime.format(fecha_nacimiento), "varFechaNacimiento", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(numero_lote, "varNumeroLote", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(compra, "varCompra", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(porcentaje.toString(), "varPorcentaje", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(id_proveedor, "varIdProveedor", "STRING", "IN");

        manejadorBD.ejecutarSP("{ call actualizarCorralDatosRepl(?,?,?,?,?,?,?,?,?,?) }");
    }

    public void cargarDatos_1(ManejadorBD bd, Date fecha) {

        manejadorBD.consulta(""
                + "SELECT   c.id_rancho,	c.id_corral,        \n"
                + "         c.categoria, 	c.ganado_amedias,   \n"
                + "         c.color_arete,	c.fecha_nacimiento, \n"
                + "         c.numero_lote,	c.compra,           \n"
                + "         c.porcentaje,       c.id_proveedor      \n"
                + "FROM	    corral_datos c,	repl_corral_datos r \n"
                + "WHERE    c.id_rancho	=   r.id_rancho             \n"
                + "AND      c.id_corral	=   r.id_corral             \n"
                + "AND      r.fecha >   '" + formatoDateTime.format(fecha) + "';");
    }

    public void actualizar_1(ManejadorBD origen, ManejadorBD destino) {
        try {
            for (int i = 0; i < origen.getRowCount(); i++) {
                id_rancho = origen.getValorString(i, 0);
                id_corral = origen.getValorString(i, 1);
                categoria = origen.getValorString(i, 2);
                ganado_amedias = origen.getValorString(i, 3);
                color_arete = origen.getValorString(i, 4);
                fecha_nacimiento = formatoDateTime.parse(origen.getValorString(i, 5));
                numero_lote = origen.getValorString(i, 6);
                compra = origen.getValorString(i, 7);
                porcentaje = Double.parseDouble(origen.getValorString(i, 8));
                id_proveedor = origen.getValorString(i, 9);

                manejadorBD.parametrosSP = new ParametrosSP();

                manejadorBD.parametrosSP.agregarParametro(id_rancho, "varIdRancho", "STRING", "IN");
                manejadorBD.parametrosSP.agregarParametro(id_corral, "varIdCorral", "STRING", "IN");
                manejadorBD.parametrosSP.agregarParametro(categoria, "varCategoria", "STRING", "IN");
                manejadorBD.parametrosSP.agregarParametro(ganado_amedias, "varGanadoAmedias", "STRING", "IN");
                manejadorBD.parametrosSP.agregarParametro(color_arete, "varColorArete", "STRING", "IN");
                manejadorBD.parametrosSP.agregarParametro(formatoDateTime.format(fecha_nacimiento), "varFechaNacimiento", "STRING", "IN");
                manejadorBD.parametrosSP.agregarParametro(numero_lote, "varNumeroLote", "STRING", "IN");
                manejadorBD.parametrosSP.agregarParametro(compra, "varCompra", "STRING", "IN");
                manejadorBD.parametrosSP.agregarParametro(porcentaje.toString(), "varPorcentaje", "STRING", "IN");
                manejadorBD.parametrosSP.agregarParametro(id_proveedor, "varIdProveedor", "STRING", "IN");

                log.log("agregando " + this.toString(), false);
                
                manejadorBD.ejecutarSP("{ call actualizarCorralDatosRepl(?,?,?,?,?,?,?,?,?,?) }");
                
                ventana.avanzar();
            }
        } catch (ParseException ex) {
            Logger.getLogger(CorralDatos.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }
}
