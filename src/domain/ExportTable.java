/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import static domain.Principal.log;
import static domain.Proceso.export;
import static domain.Principal.manejadorBD;
import java.text.SimpleDateFormat;

/**
 *
 * @author Developer GAGS
 */
public class ExportTable {

    /**
     *
     */
    String encabezado;
    public SimpleDateFormat formatoDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public ExportTable(String Aencabezado) {

        encabezado = Aencabezado;
    }

    public void insertar() {

        cargarDatos();
        String registro;
        String dato = "";
        export.setEncabezado(encabezado);

        System.out.println("Exportando "+encabezado);
              
        log.log("Exportando "+encabezado, false);
        
        for (int i = 0; i < manejadorBD.getRowCount(); i++) {

            
            
            registro = manejadorBD.getValueAt(i, 0).toString();

            for (int j = 1; j < manejadorBD.getColumnCount(); j++) {

                dato = manejadorBD.getValueAt(i, j).toString();

                if (dato.equals("")) {

                    dato = "null";
                }

                registro += "|" + dato;
            }

            export.reg(registro);
        }
    }

    public void cargarDatos() {

    }

    public void actualizar(String cadena) {

    }
    
    public Integer IntStringTokenizer(String StNumero){
     
        Integer InNumero = null;
        
        if(!StNumero.equals("null")){
            
            InNumero = Integer.parseInt(StNumero);
        }
        
        return InNumero;        
    }
    
    public String StrStringTokenizer(String StCadena){
        
        if(!StCadena.equals("null")){
            
            return StCadena;
        }
        
        return null;        
    }
}
