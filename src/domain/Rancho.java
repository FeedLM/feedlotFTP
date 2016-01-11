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
 * @author Developer GAGS
 */
public class Rancho extends ExportTable {

    public String id_rancho;
    public String descripcion;
    public String con_traspaso_entrada;
    public String con_traspaso_salida;
    public String con_salida;
    public String con_muerte;
    public String con_pesaje;
    public String id_corral_hospital;
    public String actividad;
    public String id_estado;
    public String id_ciudad;
    public String status;

    public Rancho() {

        super("[rancho]");
    }

    public void cargarDatos() {

        manejadorBD.consulta(""
                + "SELECT   ra.id_rancho,              ra.descripcion,            \n"
                + "         ra.con_traspaso_entrada,   ra.con_traspaso_salida,    \n    "
                + "         ra.con_salida,             ra.con_muerte,             \n"
                + "         ra.con_pesaje,             ra.id_corral_hospital,  \n"
                + "         ra.actividad,              ra.id_estado,           \n"
                + "         ra.id_ciudad                                       \n"
                + "FROM     rancho ra, repl_rancho re                          \n"
                + "WHERE    ra.id_rancho = re.id_rancho                        \n"
                + "AND      re.status    =   'PR';");
    }

    public void cargarDatos_1(ManejadorBD bd, Date fecha) {

        bd.consulta(""
                + "SELECT   ra.id_rancho,              ra.descripcion,            \n"
                + "         ra.con_traspaso_entrada,   ra.con_traspaso_salida,    \n    "
                + "         ra.con_salida,             ra.con_muerte,             \n"
                + "         ra.con_pesaje,             ra.id_corral_hospital,  \n"
                + "         ra.actividad,              ra.id_estado,           \n"
                + "         ra.id_ciudad                                       \n"
                + "FROM     rancho ra, repl_rancho re                          \n"
                + "WHERE    ra.id_rancho = re.id_rancho                        \n"
                + "AND      re.fecha >   '" + formatoDateTime.format(fecha) + "';");
    }

    public void actualizar_1(ManejadorBD origen, ManejadorBD destino) {

        for (int i = 0; i < origen.getRowCount(); i++) {

            id_rancho = origen.getValorString(i, 0);
            descripcion = origen.getValorString(i, 1);
            con_traspaso_entrada = origen.getValorString(i, 2);
            con_traspaso_salida = origen.getValorString(i, 3);
            con_salida = origen.getValorString(i, 4);
            con_muerte = origen.getValorString(i, 5);
            con_pesaje = origen.getValorString(i, 6);
            id_corral_hospital = origen.getValorString(i, 7);
            actividad = origen.getValorString(i, 8);
            id_estado = origen.getValorString(i, 9);
            id_ciudad = origen.getValorString(i, 10);

            destino.parametrosSP = new ParametrosSP();

            destino.parametrosSP.agregarParametro(id_rancho, "varIdRancho", "STRING", "IN");
            destino.parametrosSP.agregarParametro(descripcion, "varDescripcion", "STRING", "IN");
            destino.parametrosSP.agregarParametro(con_traspaso_entrada, "varConTraspasoEntrada", "STRING", "IN");
            destino.parametrosSP.agregarParametro(con_traspaso_salida, "varConTraspasoSalida", "STRING", "IN");
            destino.parametrosSP.agregarParametro(con_salida, "varConSalida", "STRING", "IN");
            destino.parametrosSP.agregarParametro(con_muerte, "varConMuerte", "STRING", "IN");
            destino.parametrosSP.agregarParametro(con_pesaje, "varConPesaje", "STRING", "IN");
            destino.parametrosSP.agregarParametro(id_corral_hospital, "varIdCorralHospital", "STRING", "IN");
            destino.parametrosSP.agregarParametro(actividad, "varActividad", "STRING", "IN");
            destino.parametrosSP.agregarParametro(id_estado, "varIdEstado", "STRING", "IN");
            destino.parametrosSP.agregarParametro(id_ciudad, "varIdCiudad", "STRING", "IN");

            log.log("agregando " + this.toString(), false);
            
            destino.ejecutarSP("{ call actualizarRanchoRepl(?,?,?,?,?,?,?,?,?,?,?) }");
            
            ventana.avanzar();
        }
    }

    public void actualizar(String cadena) {

        StringTokenizer st;

        String delete;

        log.log(cadena, true);

        st = new StringTokenizer(cadena, "|");

        id_rancho = st.nextToken();
        descripcion = st.nextToken();
        con_traspaso_entrada = st.nextToken();
        con_traspaso_salida = st.nextToken();
        con_salida = st.nextToken();
        con_muerte = st.nextToken();
        con_pesaje = st.nextToken();
        id_corral_hospital = st.nextToken();
        actividad = st.nextToken();
        id_estado = st.nextToken();
        id_ciudad = st.nextToken();

        manejadorBD.parametrosSP = new ParametrosSP();

        manejadorBD.parametrosSP.agregarParametro(id_rancho, "varIdRancho", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(descripcion, "varDescripcion", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(con_traspaso_entrada, "varConTraspasoEntrada", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(con_traspaso_salida, "varConTraspasoSalida", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(con_salida, "varConSalida", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(con_muerte, "varConMuerte", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(con_pesaje, "varConPesaje", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(id_corral_hospital, "varIdCorralHospital", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(actividad, "varActividad", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(id_estado, "varIdEstado", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(id_ciudad, "varIdCiudad", "STRING", "IN");

        manejadorBD.ejecutarSP("{ call actualizarRanchoRepl(?,?,?,?,?,?,?,?,?,?,?) }");
    }

    public String toString() {
        return id_rancho + " " + descripcion + " " + actividad;
    }

}
