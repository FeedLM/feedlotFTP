/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import absttract.Table;
import static domain.Principal.log;
import static domain.Proceso.export;
import static domain.Principal.manejadorBD;
import static domain.Principal.ventana;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

/**
 *
 * @author Developer GAGS
 */
public class CorralAnimal extends ExportTable {

    public String id_rancho;
    public String id_corral;
    public String id_animal;

    public CorralAnimal() {

        super("[corral_animal]");
    }

    public void cargarDatos() {

        manejadorBD.consulta(""
                + "SELECT c.id_rancho,    c.id_corral,              \n"
                + "       c.id_animal                             \n"
                + "FROM	  corral_animal c, repl_corral_animal r \n"
                + "WHERE  c.id_rancho = r.id_rancho             \n"
                + "AND	  c.id_corral = r.id_corral             \n"
                + "AND    c.id_animal = r.id_animal             \n"
                + "AND    r.status    = 'PR';");
    }

    public void cargarDatos_1(ManejadorBD bd, Date fecha) {

        bd.consulta(""
                + "SELECT c.id_rancho,    c.id_corral,              \n"
                + "       COALESCE(c.id_animal,'')                             \n"
                + "FROM	  corral_animal c, repl_corral_animal r \n"
                + "WHERE  c.id_rancho = r.id_rancho             \n"
                + "AND	  c.id_corral = r.id_corral             \n"
                + "AND    c.id_animal = r.id_animal             \n"
                + "AND      r.fecha >   '" + formatoDateTime.format(fecha) + "';");
    }

    public void actualizar_1(ManejadorBD origen, ManejadorBD destino) {

        for (int i = 0; i < origen.getRowCount(); i++) {

            id_rancho = origen.getValorString(i, 0);
            id_corral = origen.getValorString(i, 1);
            id_animal = origen.getValorString(i, 2);

            destino.parametrosSP = new ParametrosSP();

            destino.parametrosSP.agregarParametro(id_rancho, "varIdRancho", "STRING", "IN");
            destino.parametrosSP.agregarParametro(id_corral, "varIdCorral", "STRING", "IN");
            destino.parametrosSP.agregarParametro(id_animal, "varIdAnimal", "STRING", "IN");

            log.log("agregando " + this.toString(), false);
            
            destino.ejecutarSP("{ call actualizarCorralAnimalRepl(?,?,?) }");
            
            ventana.avanzar();
        }
    }

    public void actualizar(String cadena) {

        StringTokenizer st;

        String delete;

        log.log(cadena, false);

        st = new StringTokenizer(cadena, "|");

        id_rancho = st.nextToken();
        id_corral = st.nextToken();
        id_animal = st.nextToken();

        manejadorBD.parametrosSP = new ParametrosSP();

        manejadorBD.parametrosSP.agregarParametro(id_rancho, "varIdRancho", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(id_corral, "varIdCorral", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(id_animal, "varIdAnimal", "STRING", "IN");

        manejadorBD.ejecutarSP("{ call actualizarCorralAnimalRepl(?,?,?) }");
    }

    public String toString() {
        return id_corral + " " + id_animal;
    }
}
