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
public class Movimiento extends ExportTable {

    public String id_rancho;
    public String id_movimiento;
    public String id_concepto;
    public Date fecha;
    public String id_rancho_origen;
    public String id_corral_origen;
    public String id_rancho_destino;
    public String id_corral_destino;
    public Integer id_clase_movimiento;
    public String numero_pedido;
    public Integer id_cliente;
    public String necropcia;
    public String dx_muerte;
    public String etapa_reproductiva;
    public String causa_entrada;
    public String observacion;
    public Double peso;
    public Date fecha_reg;

    public Movimiento() {

        super("[movimiento]");
    }

    public void cargarDatos() {

        manejadorBD.consulta(""
                + "SELECT   m.id_rancho,			m.id_movimiento,                    \n"
                + "         m.id_concepto,			m.fecha,                            \n"
                + "         COALESCE(m.id_rancho_origen,0),     COALESCE(m.id_corral_origen,0),     \n"
                + "         COALESCE(m.id_rancho_destino,0),    COALESCE(m.id_corral_destino,0),    \n"
                + "         COALESCE(m.id_clase_movimiento,0),	COALESCE(m.numero_pedido,''),       \n"
                + "         COALESCE(m.id_cliente,0),		COALESCE(m.necropcia,''),           \n"
                + "         COALESCE(m.dx_muerte,''),		COALESCE(m.etapa_reproductiva,''),  \n"
                + "         COALESCE(m.causa_entrada,''),	COALESCE(m.observacion,''),         \n"
                + "         COALESCE(m.peso,0.0),               m.fecha_reg                         \n"
                + "FROM     movimiento m,   repl_movimiento r                                       \n"
                + "WHERE    m.id_rancho     =	r.id_rancho                                         \n"
                + "AND      m.id_movimiento =	r.id_movimiento                                     \n"
                + "AND      m.id_concepto   =	r.id_concepto                                       \n"
                + "AND      r.status        =	'PR';");
    }

    public void cargarDatos_1(ManejadorBD bd, Date fecha) {

        bd.consulta(""
                + "SELECT   m.id_rancho,			m.id_movimiento,                    \n"
                + "         m.id_concepto,			DATE_FORMAT(m.fecha, '%Y-%m-%d %T'),  \n"
                + "         COALESCE(m.id_rancho_origen,0),     COALESCE(m.id_corral_origen,0),     \n"
                + "         COALESCE(m.id_rancho_destino,0),    COALESCE(m.id_corral_destino,0),    \n"
                + "         COALESCE(m.id_clase_movimiento,0),	COALESCE(m.numero_pedido,''),       \n"
                + "         COALESCE(case id_cliente when '' then '0' end ,0),	                    \n"
                + "         COALESCE(m.necropcia,''),           \n"
                + "         COALESCE(m.dx_muerte,''),		COALESCE(m.etapa_reproductiva,''),  \n"
                + "         COALESCE(m.causa_entrada,''),	COALESCE(m.observacion,''),         \n"
                + "         COALESCE(m.peso,0.0),               m.fecha_reg                         \n"
                + "FROM     movimiento m,   repl_movimiento r                                       \n"
                + "WHERE    m.id_rancho     =	r.id_rancho                                         \n"
                + "AND      m.id_movimiento =	r.id_movimiento                                     \n"
                + "AND      m.id_concepto   =	r.id_concepto                                       \n"
                + "AND      r.fecha >   '" + formatoDateTime.format(fecha) + "'\n"
                + "order by m.fecha;");
    }

    public void actualizar(String cadena) {

        StringTokenizer st;

        String delete;

        log.log(cadena, false);

        st = new StringTokenizer(cadena, "|");

        id_rancho = st.nextToken();
        id_movimiento = st.nextToken();
        id_concepto = st.nextToken();

        try {
            fecha = formatoDateTime.parse(st.nextToken());
        } catch (ParseException ex) {

            log.log(ex.getMessage(), true);
            Logger.getLogger(Movimiento.class.getName()).log(Level.SEVERE, null, ex);
        }

        id_rancho_origen = st.nextToken();
        id_corral_origen = st.nextToken();
        id_rancho_destino = st.nextToken();
        id_corral_destino = st.nextToken();
        id_clase_movimiento = Integer.parseInt(st.nextToken());
        numero_pedido = st.nextToken();
        id_cliente = IntStringTokenizer(st.nextToken());
        necropcia = st.nextToken();
        dx_muerte = st.nextToken();
        etapa_reproductiva = st.nextToken();
        causa_entrada = st.nextToken();
        observacion = st.nextToken();
        peso = Double.parseDouble(st.nextToken());

        try {

            fecha_reg = formatoDateTime.parse(st.nextToken());
        } catch (ParseException ex) {

            log.log(ex.getMessage(), true);
            Logger.getLogger(Movimiento.class.getName()).log(Level.SEVERE, null, ex);
        }

        manejadorBD.parametrosSP = new ParametrosSP();

        manejadorBD.parametrosSP.agregarParametro(id_rancho, "varIdRancho", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(id_movimiento, "varIdMovimiento", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(id_concepto, "varIdConcepto", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(formatoDateTime.format(fecha), "varFecha", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(id_rancho_origen, "varIdRanchoOrigen", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(id_corral_origen, "varIdCorralOrigen", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(id_rancho_destino, "varIdRanchoDestino", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(id_corral_destino, "varIdCorralDestino", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(id_clase_movimiento.toString(), "varIdClaseMovimiento", "INT", "IN");
        manejadorBD.parametrosSP.agregarParametro(numero_pedido, "varNumeroPedido", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(id_cliente.toString(), "varIdCliente", "INT", "IN");
        manejadorBD.parametrosSP.agregarParametro(necropcia, "varNecropcia", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(dx_muerte, "varDxMuerte", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(etapa_reproductiva, "varEtapaReproductiva", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(causa_entrada, "varCausaEntrada", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(observacion, "varObservacion", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(peso.toString(), "varPeso", "DOUBLE", "IN");
        manejadorBD.parametrosSP.agregarParametro(formatoDateTime.format(fecha_reg), "varFechaReg", "STRING", "IN");

        manejadorBD.ejecutarSP("{ call actualizarMovimientoRepl(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }");
    }

    public void actualizar_1(ManejadorBD origen, ManejadorBD destino) {
        for (int i = 0; i < origen.getRowCount(); i++) {
            try {
                id_rancho = origen.getValorString(i, 0);
                id_movimiento = origen.getValorString(i, 1);
                id_concepto = origen.getValorString(i, 2);
                
                System.out.println("'"+id_rancho+"','"+id_movimiento+"','"+id_concepto+"'");
                
             //   System.out.println(origen.getValorString(i, 3));
                fecha = formatoDateTime.parse(origen.getValorString(i, 3));
                id_rancho_origen = origen.getValorString(i, 4);
                id_corral_origen = origen.getValorString(i, 5);
                id_rancho_destino = origen.getValorString(i, 6);
                id_corral_destino = origen.getValorString(i, 7);
                id_clase_movimiento = Integer.parseInt(origen.getValorString(i, 8));
                numero_pedido = origen.getValorString(i, 9);
                id_cliente = Integer.parseInt(origen.getValorString(i, 10));
                necropcia = origen.getValorString(i, 11);
                dx_muerte = origen.getValorString(i, 12);
                etapa_reproductiva = origen.getValorString(i, 13);
                causa_entrada = origen.getValorString(i, 14);
                observacion = origen.getValorString(i, 15);
                peso = Double.parseDouble(origen.getValorString(i, 16));
                fecha_reg = formatoDateTime.parse(origen.getValorString(i, 17));

                destino.parametrosSP = new ParametrosSP();

                destino.parametrosSP.agregarParametro(id_rancho, "varIdRancho", "STRING", "IN");
                destino.parametrosSP.agregarParametro(id_movimiento, "varIdMovimiento", "STRING", "IN");
                destino.parametrosSP.agregarParametro(id_concepto, "varIdConcepto", "STRING", "IN");
                destino.parametrosSP.agregarParametro(formatoDateTime.format(fecha), "varFecha", "STRING", "IN");
                destino.parametrosSP.agregarParametro(id_rancho_origen, "varIdRanchoOrigen", "STRING", "IN");
                destino.parametrosSP.agregarParametro(id_corral_origen, "varIdCorralOrigen", "STRING", "IN");
                destino.parametrosSP.agregarParametro(id_rancho_destino, "varIdRanchoDestino", "STRING", "IN");
                destino.parametrosSP.agregarParametro(id_corral_destino, "varIdCorralDestino", "STRING", "IN");
                destino.parametrosSP.agregarParametro(id_clase_movimiento.toString(), "varIdClaseMovimiento", "STRING", "IN");
                destino.parametrosSP.agregarParametro(numero_pedido, "varNumeroPedido", "STRING", "IN");
                destino.parametrosSP.agregarParametro(id_cliente.toString(), "varIdCliente", "STRING", "IN");
                destino.parametrosSP.agregarParametro(necropcia, "varNecropcia", "STRING", "IN");
                destino.parametrosSP.agregarParametro(dx_muerte, "varDxMuerte", "STRING", "IN");
                destino.parametrosSP.agregarParametro(etapa_reproductiva, "varEtapaReproductiva", "STRING", "IN");
                destino.parametrosSP.agregarParametro(causa_entrada, "varCausaEntrada", "STRING", "IN");
                destino.parametrosSP.agregarParametro(observacion, "varObservacion", "STRING", "IN");
                destino.parametrosSP.agregarParametro(peso.toString(), "varPeso", "DOUBLE", "IN");
                destino.parametrosSP.agregarParametro(formatoDateTime.format(fecha_reg), "varFechaReg", "STRING", "IN");
                
                log.log("agregando " + this.toString(), false);
                
                destino.ejecutarSP("{ call actualizarMovimientoRepl(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }");
                
                ventana.avanzar();
                
            } catch (ParseException ex) {
                Logger.getLogger(Movimiento.class
                        .getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public String toString() {
        return id_movimiento + " " + id_concepto + " " + formatoDateTime.format(fecha_reg);
    }

}
