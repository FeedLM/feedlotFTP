/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import static domain.Principal.log;
import static domain.Principal.manejadorBD;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Developer GAGS
 */
public class MedicinaAnimal extends ExportTable {

    public String id_rancho;
    public String id_medicina_animal;
    public String id_medicina;
    public String id_animal;
    public Double dosis;
    public Date fecha;

    // SimpleDateFormat formatoDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public MedicinaAnimal() {

        super("[medicina_animal]");
    }

    public void cargarDatos() {

        manejadorBD.consulta(""
                + "SELECT m.id_rancho,    m.id_medicina_animal,       \n"
                + "       m.id_medicina,  m.id_animal,                \n"
                + "       m.dosis,        m.fecha                     \n"
                + "FROM   medicina_animal m, repl_medicina_animal r   \n"
                + "WHERE  m.id_rancho          = r.id_rancho          \n"
                + "AND    m.id_medicina_animal = r.id_medicina_animal \n"
                + "AND    r.status             = 'PR';");
    }

    public void actualizar(String cadena) {

        StringTokenizer st;

        log.log(cadena, false);

        st = new StringTokenizer(cadena, "|");

        id_rancho = st.nextToken();
        id_medicina_animal = st.nextToken();
        id_medicina = st.nextToken();
        id_animal = st.nextToken();
        dosis = Double.parseDouble(st.nextToken());

        try {

            fecha = formatoDateTime.parse(st.nextToken());
        } catch (ParseException ex) {

            log.log(ex.getMessage(), true);
            Logger.getLogger(MedicinaAnimal.class.getName()).log(Level.SEVERE, null, ex);
        }

        manejadorBD.parametrosSP = new ParametrosSP();

        manejadorBD.parametrosSP.agregarParametro(id_rancho, "varIdRancho", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(id_medicina_animal, "varIdMedicinaAnimal", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(id_medicina, "varIdMedicina", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(id_animal, "varIdAnimal", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(dosis.toString(), "varDosis", "DOUBLE", "IN");
        manejadorBD.parametrosSP.agregarParametro(formatoDateTime.format(fecha), "varFecha", "STRING", "IN");

        manejadorBD.ejecutarSP("{ call actualizarMedicinaAnimalRepl(?,?,?,?,?,?) }");
    }

    public void cargarDatos_1(ManejadorBD bd, Date fecha) {

        manejadorBD.consulta(""
                + "SELECT m.id_rancho,    m.id_medicina_animal,       \n"
                + "       m.id_medicina,  m.id_animal,                \n"
                + "       m.dosis,        m.fecha                     \n"
                + "FROM   medicina_animal m, repl_medicina_animal r   \n"
                + "WHERE  m.id_rancho          = r.id_rancho          \n"
                + "AND    m.id_medicina_animal = r.id_medicina_animal \n"
                + "AND      r.fecha >   '" + formatoDateTime.format(fecha) + "';");
    }

    public void actualizar_1(ManejadorBD origen, ManejadorBD destino) {

        for (int i = 0; i < origen.getRowCount(); i++) {
            try {
                id_rancho = origen.getValorString(i, 0);
                id_medicina_animal = origen.getValorString(i, 1);
                id_medicina = origen.getValorString(i, 2);
                id_animal = origen.getValorString(i, 3);
                dosis = Double.parseDouble(origen.getValorString(i, 4));
                fecha = formatoDateTime.parse(origen.getValorString(i, 5));

                manejadorBD.parametrosSP = new ParametrosSP();

                manejadorBD.parametrosSP.agregarParametro(id_rancho, "varIdRancho", "STRING", "IN");
                manejadorBD.parametrosSP.agregarParametro(id_medicina_animal, "varIdMedicinaAnimal", "STRING", "IN");
                manejadorBD.parametrosSP.agregarParametro(id_medicina, "varIdMedicina", "STRING", "IN");
                manejadorBD.parametrosSP.agregarParametro(id_animal, "varIdAnimal", "STRING", "IN");
                manejadorBD.parametrosSP.agregarParametro(dosis.toString(), "varDosis", "DOUBLE", "IN");
                manejadorBD.parametrosSP.agregarParametro(formatoDateTime.format(fecha), "varFecha", "STRING", "IN");

                manejadorBD.ejecutarSP("{ call actualizarMedicinaAnimalRepl(?,?,?,?,?,?) }");
            } catch (ParseException ex) {
                Logger.getLogger(Movimiento.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
