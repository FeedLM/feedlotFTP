/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import static domain.Principal.log;
import static domain.Principal.manejadorBD;
import java.text.ParseException;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Developer GAGS
 */
public class Animal extends ExportTable {

    public String id_animal;
    public String id_proveedor;
    public Date fecha_compra;
    public String compra;
    public String numero_lote;
    public Double peso_compra;
    public String id_sexo;
    public Date fecha_ingreso;
    public String arete_visual;
    public String arete_electronico;
    public String arete_siniiga;
    public String arete_campaña;
    public Double peso_actual;
    public Double temperatura;
    public String es_semental;
    public String id_semental;
    public String id_raza;
    public String es_vientre;
    public Date fecha_recepcion;
    public Double peso_recepcion;
    public Double porcentaje_merma;
    public Double costo_flete;
    public Double total_alimento;
    public Double costo_alimento;
    public Double promedio_alimento;
    public Double promedio_costo_alimento;
    public Date fecha_ultima_comida;
    public Double ganancia_promedio;
    public String status;

    public Animal() {

        super("[animal]");
    }

    public void cargarDatos() {

        manejadorBD.consulta(""
                + "SELECT a.id_animal,          id_proveedor,                     \n"
                + "       fecha_compra,         compra,    \n"
                + "       numero_lote,          peso_compra,             \n"
                + "       id_sexo,              fecha_ingreso,     \n"
                + "       arete_visual,         arete_electronico,               \n"
                + "       arete_siniiga,        arete_campaña,               \n"
                + "       peso_actual,          temperatura,                    \n"
                + "       COALESCE(es_semental,'N'), COALESCE(id_semental,0), \n"
                + "       COALESCE(id_raza, ''), a.status                    \n"
                + "FROM   animal a, repl_animal r                             \n"
                + "WHERE  a.id_animal =   r.id_animal                         \n"
                + "AND    r.status    =   'PR';");
    }

    public void cargarDatos_1(ManejadorBD bd, Date fecha) {

        bd.consulta(""
                + "SELECT a.id_animal,                      id_proveedor,                   \n"
                + "       fecha_compra,                     compra,                         \n"
                + "       numero_lote,                      peso_compra,                    \n"
                + "       id_sexo,                          fecha_ingreso,                  \n"
                + "       arete_visual,                     arete_electronico,              \n"
                + "       arete_siniiga,                    arete_campaña,                  \n"
                + "       peso_actual,                      temperatura,                    \n"
                + "       COALESCE(es_semental,'N'),        COALESCE(id_semental,0),        \n"
                + "       COALESCE(id_raza, ''),            es_vientre,                     \n"
                + "       fecha_recepcion,                  COALESCE(peso_recepcion,0.00),  \n"
                + "       COALESCE(porcentaje_merma,0.00),  COALESCE(costo_flete,0.00),     \n"
                + "       COALESCE(total_alimento,0.00),    COALESCE(costo_alimento,0.00),  \n"
                + "       COALESCE(promedio_alimento,0.00), COALESCE(promedio_costo_alimento,0.00),       \n"
                + "       fecha_ultima_comida,              COALESCE(ganancia_promedio,0.00), \n"
                + "       a.status \n"
                + "FROM   animal a, repl_animal r \n"
                + "WHERE  a.id_animal =   r.id_animal \n"
                + "AND      r.fecha >   '" + formatoDateTime.format(fecha) + "';");
    }

    public void actualizar_1(ManejadorBD origen, ManejadorBD destino) {

        for (int i = 0; i < origen.getRowCount(); i++) {

            try {

                id_animal = origen.getValorString(i, 0);
                id_proveedor = origen.getValorString(i, 1);
                fecha_compra = formatoDateTime.parse(origen.getValorString(i, 2));
                compra = origen.getValorString(i, 3);
                numero_lote = origen.getValorString(i, 4);
                peso_compra = Double.parseDouble(origen.getValorString(i, 5));
                id_sexo = origen.getValorString(i, 6);
                fecha_ingreso = formatoDateTime.parse(origen.getValorString(i, 7));
                arete_visual = origen.getValorString(i, 8);
                arete_electronico = origen.getValorString(i, 9);
                arete_siniiga = origen.getValorString(i, 10);
                arete_campaña = origen.getValorString(i, 11);
                peso_actual = Double.parseDouble(origen.getValorString(i, 12));
                temperatura = Double.parseDouble(origen.getValorString(i, 13));
                es_semental = origen.getValorString(i, 14);
                id_semental = origen.getValorString(i, 15);
                id_raza = origen.getValorString(i, 16);
                es_vientre = origen.getValorString(i, 17);
                fecha_recepcion = formatoDateTime.parse(origen.getValorString(i, 18));
                peso_recepcion = Double.parseDouble(origen.getValorString(i, 19));
                porcentaje_merma = Double.parseDouble(origen.getValorString(i, 20));
                costo_flete = Double.parseDouble(origen.getValorString(i, 21));
                total_alimento = Double.parseDouble(origen.getValorString(i, 22));
                costo_alimento = Double.parseDouble(origen.getValorString(i, 23));
                promedio_alimento = Double.parseDouble(origen.getValorString(i, 24));
                promedio_costo_alimento = Double.parseDouble(origen.getValorString(i, 25));
                fecha_ultima_comida = formatoDateTime.parse(origen.getValorString(i, 26));

                ganancia_promedio = Double.parseDouble(origen.getValorString(i, 27));
                status = origen.getValorString(i, 28);

                destino.parametrosSP = new ParametrosSP();

                destino.parametrosSP.agregarParametro(id_animal.toString(), "varIdAnimal", "STRING", "IN");
                destino.parametrosSP.agregarParametro(id_proveedor,
                        "varIdProveedor", "STRING", "IN");
                destino.parametrosSP.agregarParametro(formatoDateTime.format(fecha_compra), "varFechaCompra", "STRING", "IN");
                destino.parametrosSP.agregarParametro(compra,
                        "varCompra", "STRING", "IN");
                destino.parametrosSP.agregarParametro(numero_lote,
                        "varNumeroLote", "STRING", "IN");
                destino.parametrosSP.agregarParametro(peso_compra.toString(), "varPesoCompra", "DOUBLE", "IN");
                destino.parametrosSP.agregarParametro(id_sexo,
                        "varIdSexo", "STRING", "IN");
                destino.parametrosSP.agregarParametro(formatoDateTime.format(fecha_ingreso), "varFechaIngreso", "STRING", "IN");

                destino.parametrosSP.agregarParametro(arete_visual,
                        "varAreteVisual", "STRING", "IN");
                destino.parametrosSP.agregarParametro(arete_electronico,
                        "varAreteElectronico", "STRING", "IN");
                destino.parametrosSP.agregarParametro(arete_siniiga,
                        "varAreteSiniiga", "STRING", "IN");
                destino.parametrosSP.agregarParametro(arete_campaña,
                        "varAreteCampaña", "STRING", "IN");

                destino.parametrosSP.agregarParametro(peso_actual.toString(), "varPesoActual", "DOUBLE", "IN");
                destino.parametrosSP.agregarParametro(temperatura.toString(), "varTemperatura", "DOUBLE", "IN");
                destino.parametrosSP.agregarParametro(es_semental, "varEsSemental", "STRING", "IN");

                if (es_semental
                        != null) {
                    destino.parametrosSP.agregarParametro(id_semental, "varIdSemental", "STRING", "IN");
                } else {
                    
                    destino.parametrosSP.agregarParametro("0", "varIdSemental", "STRING", "IN");
                }
                
                destino.parametrosSP.agregarParametro(id_raza, "varIdRaza", "STRING", "IN");
                
                destino.parametrosSP.agregarParametro(es_vientre, "varEsVientre", "STRING", "IN");
                destino.parametrosSP.agregarParametro(formatoDateTime.format(fecha_recepcion), "varFechaRecepcion", "STRING", "IN");
                destino.parametrosSP.agregarParametro(peso_recepcion.toString(), "varPesoRecepcion", "DOUBLE", "IN");
                destino.parametrosSP.agregarParametro(porcentaje_merma.toString(), "varPorcentajeMerma", "DOUBLE", "IN");
                destino.parametrosSP.agregarParametro(costo_flete.toString(), "varCostoFlete", "DOUBLE", "IN");
                destino.parametrosSP.agregarParametro(total_alimento.toString(), "varTotalAlimento", "DOUBLE", "IN");
                destino.parametrosSP.agregarParametro(costo_alimento.toString(), "varCostoAlimento", "DOUBLE", "IN");
                destino.parametrosSP.agregarParametro(promedio_alimento.toString(), "varPromedioAlimento", "DOUBLE", "IN");
                destino.parametrosSP.agregarParametro(promedio_costo_alimento.toString(), "varPromedioCostoAlimento", "DOUBLE", "IN");
                destino.parametrosSP.agregarParametro(formatoDateTime.format(fecha_ultima_comida), "varFechaUltimaComida", "STRING", "IN");
                destino.parametrosSP.agregarParametro(ganancia_promedio.toString(), "varGananciaPromedio", "DOUBLE", "IN");
                destino.parametrosSP.agregarParametro(status, "varStatus", "STRING", "IN");
                destino.ejecutarSP("{ call actualizarAnimalRepl(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }");

            } catch (ParseException ex) {
                Logger.getLogger(Animal.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void actualizar(String cadena) {

        log.log(cadena, false);

        try {
            StringTokenizer st;

            st = new StringTokenizer(cadena, "|");

            System.out.println(cadena);
            id_animal = st.nextToken();
            id_proveedor = st.nextToken();
            fecha_compra = formatoDateTime.parse(st.nextToken());
            compra = st.nextToken();
            numero_lote = st.nextToken();
            peso_compra = Double.parseDouble(st.nextToken());
            id_sexo = st.nextToken();
            fecha_ingreso = formatoDateTime.parse(st.nextToken());

            arete_visual = st.nextToken();
            arete_electronico = st.nextToken();
            arete_siniiga = st.nextToken();
            arete_campaña = st.nextToken();
            peso_actual = Double.parseDouble(st.nextToken());
            temperatura = Double.parseDouble(st.nextToken());
            es_semental = st.nextToken();
            id_semental = st.nextToken();
            id_raza = st.nextToken();
            status = st.nextToken();

            manejadorBD.parametrosSP = new ParametrosSP();

            manejadorBD.parametrosSP.agregarParametro(id_animal.toString(), "varIdAnimal", "STRING", "IN");
            manejadorBD.parametrosSP.agregarParametro(id_proveedor, "varIdProveedor", "STRING", "IN");
            manejadorBD.parametrosSP.agregarParametro(formatoDateTime.format(fecha_compra), "varFechaCompra", "STRING", "IN");
            manejadorBD.parametrosSP.agregarParametro(compra, "varCompra", "STRING", "IN");
            manejadorBD.parametrosSP.agregarParametro(numero_lote, "varNumeroLote", "STRING", "IN");
            manejadorBD.parametrosSP.agregarParametro(peso_compra.toString(), "varPesoCompra", "DOUBLE", "IN");
            manejadorBD.parametrosSP.agregarParametro(id_sexo, "varIdSexo", "STRING", "IN");
            manejadorBD.parametrosSP.agregarParametro(formatoDateTime.format(fecha_ingreso), "varFechaIngreso", "STRING", "IN");

            manejadorBD.parametrosSP.agregarParametro(arete_visual, "varAreteVisual", "STRING", "IN");
            manejadorBD.parametrosSP.agregarParametro(arete_electronico, "varAreteElectronico", "STRING", "IN");
            manejadorBD.parametrosSP.agregarParametro(arete_siniiga, "varAreteSiniiga", "STRING", "IN");
            manejadorBD.parametrosSP.agregarParametro(arete_campaña, "varAreteCampaña", "STRING", "IN");

            manejadorBD.parametrosSP.agregarParametro(peso_actual.toString(), "varPesoActual", "DOUBLE", "IN");
            manejadorBD.parametrosSP.agregarParametro(temperatura.toString(), "varTemperatura", "DOUBLE", "IN");

            manejadorBD.parametrosSP.agregarParametro(es_semental, "varEsSemental", "STRING", "IN");

            if (es_semental != null) {
                manejadorBD.parametrosSP.agregarParametro(es_semental, "varIdSemental", "STRING", "IN");
            } else {
                manejadorBD.parametrosSP.agregarParametro("0", "varIdSemental", "STRING", "IN");
            }

            manejadorBD.parametrosSP.agregarParametro(id_raza, "varIdRaza", "STRING", "IN");

            manejadorBD.parametrosSP.agregarParametro(status, "varStatus", "STRING", "IN");

            manejadorBD.ejecutarSP("{ call actualizarAnimalRepl(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }");

        } catch (ParseException ex) {
            log.log(ex.getMessage(), true);
            Logger
                    .getLogger(Animal.class
                            .getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String toString(){
        return id_animal + " " + arete_visual + " " + arete_electronico + " " + peso_actual.toString();
    }
}
