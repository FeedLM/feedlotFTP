/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import static domain.Principal.log;
import static domain.Principal.manejadorBD;
import java.util.Date;
import java.util.StringTokenizer;

/**
 *
 * @author Home
 */
class Genealogia extends ExportTable {

    String id_animal;
    String id_madre;
    String id_padre;

    public Genealogia() {
        super("[genealogia]");
    }

    public void cargarDatos() {

        manejadorBD.consulta(""
                + "SELECT  g.id_animal, g.id_madre, g.id_padre\n"
                + "FROM genealogia g, repl_genealogia x\n"
                + "WHERE x.id_animal = g.id_animal AND x.id_madre = g.id_madre \n"
                + "AND x.id_padre = g.id_padre AND r.status = 'PR';");
    }

    public void cargarDatos_1(ManejadorBD bd, Date fecha) {

        bd.consulta(""
                + "SELECT  g.id_animal, g.id_madre, g.id_padre\n"
                + "FROM genealogia g, repl_genealogia x\n"
                + "WHERE x.id_animal = g.id_animal AND x.id_madre = g.id_madre \n"
                + "AND x.id_padre = g.id_padre \n"
                + "AND      x.fecha >   '" + formatoDateTime.format(fecha) + "';");
    }

    public void actualizar(String cadena) {

        StringTokenizer st;

        String delete;

        log.log(cadena, true);

        st = new StringTokenizer(cadena, "|");
        System.out.println("cadena " + cadena);
        id_animal = st.nextToken();
        id_madre = st.nextToken();
        id_padre = st.nextToken();

        manejadorBD.parametrosSP = new ParametrosSP();

        manejadorBD.parametrosSP.agregarParametro(id_animal, "varIdAnimal", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(id_madre, "varIdMadre", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(id_padre, "varIdPadre", "STRING", "IN");

        manejadorBD.ejecutarSP("{ call actualizarGenealogiaRepl(?,?,?) }");
    }

    public void actualizar_1(ManejadorBD origen, ManejadorBD destino) {
        for (int i = 0; i < origen.getRowCount(); i++) {

            id_animal = origen.getValorString(i, 0);
            id_madre = origen.getValorString(i, 1);
            id_padre = origen.getValorString(i, 2);

            manejadorBD.parametrosSP = new ParametrosSP();

            manejadorBD.parametrosSP.agregarParametro(id_animal, "varIdAnimal", "STRING", "IN");
            manejadorBD.parametrosSP.agregarParametro(id_madre, "varIdMadre", "STRING", "IN");
            manejadorBD.parametrosSP.agregarParametro(id_padre, "varIdPadre", "STRING", "IN");

            manejadorBD.ejecutarSP("{ call actualizarGenealogiaRepl(?,?,?) }");
        }
    }

    public String toString() {
        return id_animal + " " + id_madre + " " + id_padre;
    }
}
