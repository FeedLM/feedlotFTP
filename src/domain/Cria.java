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
public class Cria extends ExportTable {

    public String id_rancho;
    public String id_madre;
    public String id_cria;
    public String arete;
    public String id_sexo;
    public Date fecha_nacimiento;
    public String id_raza;
    public String status;

    public Cria() {

        super("[cria]");
    }

    public void cargarDatos() {

        manejadorBD.consulta(""
                + "SELECT	c.id_rancho,	c.id_madre,\n"
                + "		c.id_cria,			c.arete,\n"
                + "		c.id_sexo,		c.fecha_nacimiento,\n"
                + "		c.id_raza,		c.status\n"
                + "FROM 	cria c, repl_cria r\n"
                + "WHERE	c.id_rancho	=	r.id_rancho\n"
                + "AND		c.id_madre	=	r.id_madre\n"
                + "AND		c.id_cria	=	r.id_cria                               \n"
                + "AND      r.status	=	'PR';");

    }

    public void actualizar(String cadena) {

        StringTokenizer st;

        log.log(cadena, false);

        st = new StringTokenizer(cadena, "|");

        id_rancho = st.nextToken();
        id_madre = st.nextToken();
        id_cria = st.nextToken();

        arete = st.nextToken();
        id_sexo = st.nextToken();

        try {
            fecha_nacimiento = formatoDateTime.parse(st.nextToken());
        } catch (ParseException ex) {
            log.log(ex.getMessage(), true);
            Logger.getLogger(Movimiento.class.getName()).log(Level.SEVERE, null, ex);
        }

        id_raza = st.nextToken();
        status = st.nextToken();

        manejadorBD.parametrosSP = new ParametrosSP();

        manejadorBD.parametrosSP.agregarParametro(id_rancho, "varIdRancho", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(id_madre, "varIdMadre", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(id_cria, "varIdCria", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(arete, "varArete", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(id_sexo, "varIdSexo", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(formatoDateTime.format(fecha_nacimiento), "varFechaNacimiento", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(id_raza, "varIdRaza", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(status, "varStatus", "STRING", "IN");

        manejadorBD.ejecutarSP("{ call actualizarCriaRepl(?,?,?,?,?,?,?,?) }");
    }

    public void cargarDatos_1(ManejadorBD bd, Date fecha) {

        bd.consulta(""
                + "SELECT	c.id_rancho,	c.id_madre,\n"
                + "		c.id_cria,			c.arete,\n"
                + "		c.id_sexo,		c.fecha_nacimiento,\n"
                + "		c.id_raza,		c.status\n"
                + "FROM 	cria c, repl_cria r\n"
                + "WHERE	c.id_rancho	=	r.id_rancho\n"
                + "AND		c.id_madre	=	r.id_madre\n"
                + "AND		c.id_cria	=	r.id_cria                               \n"
                + "AND      r.fecha >   '" + formatoDateTime.format(fecha) + "';");

    }

    public void actualizar_1(ManejadorBD origen, ManejadorBD destino) {
        for (int i = 0; i < origen.getRowCount(); i++) {
            try {
                id_rancho = origen.getValorString(i, 0);
                id_madre = origen.getValorString(i, 1);
                id_cria = origen.getValorString(i, 2);
                arete = origen.getValorString(i, 3);
                id_sexo = origen.getValorString(i, 4);
                fecha_nacimiento = formatoDateTime.parse(origen.getValorString(i, 5));
                id_raza = origen.getValorString(i, 6);
                status = origen.getValorString(i, 7);
            } catch (ParseException ex) {
                log.log(ex.getMessage(), true);
                Logger.getLogger(Cria.class.getName()).log(Level.SEVERE, null, ex);
            }

            destino.parametrosSP = new ParametrosSP();

            destino.parametrosSP.agregarParametro(id_rancho, "varIdRancho", "STRING", "IN");
            destino.parametrosSP.agregarParametro(id_madre, "varIdMadre", "STRING", "IN");
            destino.parametrosSP.agregarParametro(id_cria, "varIdCria", "STRING", "IN");
            destino.parametrosSP.agregarParametro(arete, "varArete", "STRING", "IN");
            destino.parametrosSP.agregarParametro(id_sexo, "varIdSexo", "STRING", "IN");
            destino.parametrosSP.agregarParametro(formatoDateTime.format(fecha_nacimiento), "varFechaNacimiento", "STRING", "IN");
            destino.parametrosSP.agregarParametro(id_raza, "varIdRaza", "STRING", "IN");
            destino.parametrosSP.agregarParametro(status, "varStatus", "STRING", "IN");

            log.log("agregando " + this.toString(), false);
            
            destino.ejecutarSP("{ call actualizarCriaRepl(?,?,?,?,?,?,?,?) }");
            
            ventana.avanzar();
        }
    }

    public String toString() {
        return id_cria + " " + arete + " " + formatoDateTime.format(fecha_nacimiento);
    }
}
