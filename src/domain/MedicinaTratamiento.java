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
public class MedicinaTratamiento extends ExportTable {

    public String id_tratamiento;
    public String id_medicina;
    public Double dosis;

    public MedicinaTratamiento() {

        super("[medicina_tratamiento]");
    }

    public void cargarDatos() {

        manejadorBD.consulta(""
                + "SELECT   mt.id_tratamiento, mt.id_medicina,                      \n"
                + "         mt.dosis                                               \n"
                + "FROM     medicina_tratamiento mt,   repl_medicina_tratamiento r  \n"
                + "WHERE    mt.id_tratamiento    =   r.id_tratamiento               \n"
                + "AND      mt.id_medicina       =   r.id_medicina                  \n"
                + "AND      r.status    =   'PR';");
    }

    void cargarDatos_1(ManejadorBD bd, Date fecha) {
        bd.consulta(""
                + "SELECT   mt.id_tratamiento, mt.id_medicina,                      \n"
                + "         mt.dosis                                               \n"
                + "FROM     medicina_tratamiento mt,   repl_medicina_tratamiento r  \n"
                + "WHERE    mt.id_tratamiento    =   r.id_tratamiento               \n"
                + "AND      mt.id_medicina       =   r.id_medicina                  \n"
                + "AND      r.fecha >   '" + formatoDateTime.format(fecha) + "';");
    }

    void actualizar_1(ManejadorBD origen, ManejadorBD destino) {

        for (int i = 0; i < origen.getRowCount(); i++) {
            id_tratamiento = origen.getValorString(i, 0);
            id_medicina = origen.getValorString(i, 1);
            dosis = Double.parseDouble(origen.getValorString(i, 2));
            
            destino.parametrosSP = new ParametrosSP();

            destino.parametrosSP.agregarParametro(id_tratamiento, "varIdTratamiento", "STRING", "IN");
            destino.parametrosSP.agregarParametro(id_medicina, "varIdMedicina", "STRING", "IN");
            destino.parametrosSP.agregarParametro(dosis.toString(), "varDosis", "STRING", "IN");
            
            log.log("agregando " + this.toString(), false);
            
            destino.ejecutarSP("{ call actualizarMedicinaTratamientoRepl(?,?,?) }");
            
            ventana.avanzar();
        }

    }

    public void actualizar(String cadena) {

        log.log(cadena, false);

        StringTokenizer st;
        st = new StringTokenizer(cadena, "|");
        System.out.println(cadena);

        id_tratamiento = st.nextToken();
        id_medicina = st.nextToken();
        dosis = Double.parseDouble(st.nextToken());

        manejadorBD.parametrosSP = new ParametrosSP();

        manejadorBD.parametrosSP.agregarParametro(id_tratamiento, "varIdTratamiento", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(id_medicina, "vaIdMedicina", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(dosis.toString(), "varDosis", "STRING", "IN");
        manejadorBD.ejecutarSP("{ call actualizarMedicinaTratamientoRepl(?,?,?) }");
    }

    public String toString() {
        return id_tratamiento + " " + id_medicina + " " + dosis;
    }
}
