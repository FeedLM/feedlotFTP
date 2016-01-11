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
 * @author Marco
 */
class Usuario extends ExportTable {

    String id_usuario, log_1, password, nombre, apellido, id_estado, id_ciudad,
            correo, fecha, telefono;

    public Usuario() {
        super("[usuario]");
    }

    public void cargarDatos() {

        manejadorBD.consulta(""
                + "SELECT     u.id_usuario,    u.log,    u.password,    u.nombre,    \n"
                + "			u.apellido,    u.id_estado,    u.id_ciudad,    u.correo,    \n"
                + "			u.fecha_nacimiento,    u.telefono\n"
                + "FROM    usuario u,    repl_usuario r\n"
                + "WHERE    u.id_usuario = r.id_usuario        AND u.log = r.log\n"
                + "AND    r.status    =   'PR';");
    }

    public void cargarDatos_1(ManejadorBD bd, Date fecha) {

        bd.consulta(""
                + "SELECT     u.id_usuario,    u.log,    u.password,    u.nombre,    \n"
                + "			u.apellido,    u.id_estado,    u.id_ciudad,    u.correo,    \n"
                + "			u.fecha_nacimiento,    u.telefono\n"
                + "FROM    usuario u,    repl_usuario r\n"
                + "WHERE    u.id_usuario = r.id_usuario        AND u.log = r.log\n"
                + "AND      r.fecha >   '" + formatoDateTime.format(fecha) + "';");
    }

    public void actualizar_1(ManejadorBD origen, ManejadorBD destino) {

        for (int i = 0; i < origen.getRowCount(); i++) {

            id_usuario = origen.getValorString(i, 0);
            log_1 = origen.getValorString(i, 1);
            password = origen.getValorString(i, 2);
            nombre = origen.getValorString(i, 3);
            apellido = origen.getValorString(i, 4);
            id_estado = origen.getValorString(i, 5);
            id_ciudad = origen.getValorString(i, 6);
            correo = origen.getValorString(i, 7);
            fecha = origen.getValorString(i, 8);
            telefono = origen.getValorString(i, 9);

            destino.parametrosSP = new ParametrosSP();

            destino.parametrosSP.agregarParametro(id_usuario, "varIdUsuario", "STRING", "IN");
            destino.parametrosSP.agregarParametro(log_1, "varLog", "STRING", "IN");
            destino.parametrosSP.agregarParametro(password, "varPassword", "STRING", "IN");
            destino.parametrosSP.agregarParametro(nombre, "varNombre", "STRING", "IN");
            destino.parametrosSP.agregarParametro(apellido, "varApellido", "STRING", "IN");
            destino.parametrosSP.agregarParametro(id_estado, "varIdEstado", "STRING", "IN");
            destino.parametrosSP.agregarParametro(id_ciudad, "varIdCiudad", "STRING", "IN");
            destino.parametrosSP.agregarParametro(correo, "varCorreo", "STRING", "IN");
            destino.parametrosSP.agregarParametro(fecha, "varFecha", "STRING", "IN");
            destino.parametrosSP.agregarParametro(telefono, "varTelefono", "STRING", "IN");

            log.log("agregando " + this.toString(), false);
            
            destino.ejecutarSP("{ call actualizarUsuarioRepl(?,?,?,?,?,?,?,?,?,?) }");
            
            ventana.avanzar();
        }
    }

    public void actualizar(String cadena) {

        domain.Principal.log.log(cadena, false);

        StringTokenizer st;

        st = new StringTokenizer(cadena, "|");

        System.out.println(cadena);
        id_usuario = st.nextToken();
        log_1 = st.nextToken();
        password = st.nextToken();
        nombre = st.nextToken();
        apellido = st.nextToken();
        id_estado = st.nextToken();
        id_ciudad = st.nextToken();
        correo = st.nextToken();
        fecha = st.nextToken();
        telefono = st.nextToken();

        manejadorBD.parametrosSP = new ParametrosSP();

        manejadorBD.parametrosSP.agregarParametro(id_usuario, "varIdUsuario", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(log_1, "varLog", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(password, "varPassword", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(nombre, "varNombre", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(apellido, "varApellido", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(id_estado, "varIdEstado", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(id_ciudad, "varIdCiudad", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(correo, "varCorreo", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(fecha, "varFecha", "STRING", "IN");
        manejadorBD.parametrosSP.agregarParametro(telefono, "varTelefono", "STRING", "IN");

        manejadorBD.ejecutarSP("{ call actualizarUsuarioRepl(?,?,?,?,?,?,?,?,?,?) }");
    }

    public String toString() {
        return id_usuario + " " + log_1 + " " + nombre + " " + apellido + " " + correo;
    }
}
