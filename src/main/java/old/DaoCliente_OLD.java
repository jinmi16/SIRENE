/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package old;

import com.sirene.Dao.Impl.*;
import com.sirene.Bean.Cliente;
import com.sirene.Bean.EstadoCliente;
import com.sirene.Coneccion.Conexion;
import com.sirene.Utilitarios.Fecha;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoCliente_OLD {

    public static ArrayList<EstadoCliente> listarEstadoCli() {
        ArrayList<EstadoCliente> lst = new ArrayList<>();
        String sql = "SELECT * FROM estadoCliente";
        Connection cn = Conexion.abrir();
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            EstadoCliente e;
            while (rs.next()) {
                e = new EstadoCliente();
                e.setIdEstado(rs.getInt(1));
                e.setDescripcion(rs.getString(2));
                lst.add(e);

            }

        } catch (SQLException ex) {
            Mensaje.panelSms("Error en listar estado Cliente : " + ex);
            Logger.getLogger(DaoCliente_OLD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lst;
    }

    public static void regresarAcorteSE(int id) {
        String sql = "UPDATE cliente SET idEstado=3,ultimoCorteEjecutado=NULL WHERE idCliente=" + id + "";
        Connection cn = Conexion.abrir();
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DaoCliente_OLD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static int getIdAntena(String nom) {
        int id = 0;
        String sql = "SELECT `idAntena` FROM antena WHERE nombreAntena='" + nom + "'";
        Connection cn = Conexion.abrir();

        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DaoCliente_OLD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    public static void congelarCliente(int idCli, String fecha) {
        String sql = "UPDATE cliente SET idEstado = 7,F_Congelada='" + fecha + "' WHERE idCliente = " + idCli + " ";
        Connection cn = Conexion.abrir();
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.executeUpdate();
            ps.close();
            cn.close();
        } catch (SQLException ex) {

            System.out.println("ERRO EN congelarCliente : " + ex);
        }
    }

    public static int getIdEstado(String nom) {
        int id = 0;
        String sql = "SELECT idEstado FROM estadocliente where descripcion='" + nom + "'";
        Connection cn = Conexion.abrir();

        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DaoCliente_OLD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    public static void registrarCliente(Cliente c) {
        String sql = "INSERT INTO cliente(apellido,nombre,DNI,direccion,correo,f_Inicio,tarifa,f_Vencimiento,f_corte,condicionAntena,mac,ip,frecuencia,anchoBanda,idEstado,idAntena,observacion,marca)"
                + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        Connection cn = Conexion.abrir();
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, c.getApellido());
            ps.setString(2, c.getNombre());
            ps.setString(3, c.getDni());
            ps.setString(4, c.getDireccion());
            ps.setString(5, c.getCorreo());
            ps.setString(6, c.getFechInicio());
            ps.setFloat(7, c.getTarifa());
            ps.setString(8, c.getFechVencimiento());
            ps.setString(9, c.getFechCorte());
            ps.setString(10, c.getCondicioAntena());
            ps.setString(11, c.getMac());
            ps.setString(12, c.getIp());
            ps.setString(13, c.getFrecuenci());
            ps.setString(14, c.getAnchoBanda());
            ps.setInt(15, c.getIdEstado());
            ps.setInt(16, c.getIdAntena());
            ps.setString(17, c.getObservacion());
            ps.setString(18, c.getMarca());
            ps.executeUpdate();

        } catch (SQLException ex) {
            Mensaje.panelSms("ERROR AL REGISTRAR CLIENTE :" + ex);
            Logger.getLogger(DaoCliente_OLD.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void registrarCliente2(Cliente c, Connection cn) throws SQLException {
        String sql = "INSERT INTO cliente(apellido,nombre,DNI,direccion,correo,f_Inicio,tarifa,f_Vencimiento,f_corte,condicionAntena,mac,ip,frecuencia,anchoBanda,idEstado,idAntena,observacion,marca)"
                + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = cn.prepareStatement(sql);

        ps.setString(1, c.getApellido());
        ps.setString(2, c.getNombre());
        ps.setString(3, c.getDni());
        ps.setString(4, c.getDireccion());
        ps.setString(5, c.getCorreo());
        ps.setString(6, c.getFechInicio());
        ps.setFloat(7, c.getTarifa());
        ps.setString(8, c.getFechVencimiento());
        ps.setString(9, c.getFechCorte());
        ps.setString(10, c.getCondicioAntena());
        ps.setString(11, c.getMac());
        ps.setString(12, c.getIp());
        ps.setString(13, c.getFrecuenci());
        ps.setString(14, c.getAnchoBanda());
        ps.setInt(15, c.getIdEstado());
        ps.setInt(16, c.getIdAntena());
        ps.setString(17, c.getObservacion());
        ps.setString(18, c.getMarca());
        ps.executeUpdate();

    }

    public static ArrayList<Object[]> listarCliente() {
        ArrayList<Object[]> lst = new ArrayList<>();
        String sql = "SELECT c.idCliente,c.apellido,c.nombre,c.DNI,c.direccion,c.correo,c.f_Inicio,c.tarifa,"
                + "c.f_Vencimiento,c.f_Corte,c.condicionAntena,c.mac,c.ip,c.frecuencia,c.anchoBanda,"
                + "ec.descripcion,a.nombreAntena,c.observacion,c.marca,c.ultimoCorteEjecutado "
                + "FROM cliente c,estadocliente ec , antena a "
                + "WHERE c.idEstado=ec.idEstado AND c.idAntena=a.idAntena ORDER BY idCliente DESC";

        Connection cn = Conexion.abrir();

        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            Object[] f;
            while (rs.next()) {
                f = new Object[20];
                f[0] = rs.getInt(1);
                f[1] = rs.getString(2);
                f[2] = rs.getString(3);
                f[3] = rs.getString(4);
                f[4] = rs.getString(5);
                f[5] = rs.getString(6);
                f[6] = Fecha.convertToString(rs.getDate(7));
                f[7] = rs.getFloat(8);
                f[8] = Fecha.convertToString(rs.getDate(9));
                f[9] = Fecha.convertToString(rs.getDate(10));
                f[10] = rs.getString(11);
                f[11] = rs.getString(12);
                f[12] = rs.getString(13);
                f[13] = rs.getString(14);
                f[14] = rs.getString(15);
                f[15] = rs.getString(16);
                f[16] = rs.getString(17);
                f[17] = rs.getString(18);
                f[18] = rs.getString(19);
                f[19] = Fecha.convertToString(rs.getDate(20));
                lst.add(f);

            }

        } catch (SQLException ex) {
            Mensaje.panelSms("Error_metodo listar Cliente" + ex);
            Logger.getLogger(DaoCliente_OLD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lst;
    }

    public static ArrayList<Object[]> busClienteApell_Nom_Dni(String a, String n, String d, String marca) {
        ArrayList<Object[]> lst = new ArrayList<>();
        String sql;
        if (marca != "todas") {
            sql = "SELECT c.idCliente,c.apellido,c.nombre,c.DNI,c.direccion,c.correo,c.f_Inicio,c.tarifa,"
                    + "c.f_Vencimiento,c.f_Corte,c.condicionAntena,c.mac,c.ip,c.frecuencia,c.anchoBanda,"
                    + "ec.descripcion,a.nombreAntena,c.observacion,c.marca,c.ultimoCorteEjecutado "
                    + "FROM cliente c,estadocliente ec , antena a "
                    + "WHERE c.idEstado=ec.idEstado AND c.idAntena=a.idAntena "
                    + "AND c.apellido LIKE '" + a + "%' AND c.nombre LIKE '" + n + "%' AND c.DNI LIKE '" + d + "%' AND c.marca='" + marca + "' "
                    + "ORDER BY idCliente DESC";
        } else {
            sql = "SELECT c.idCliente,c.apellido,c.nombre,c.DNI,c.direccion,c.correo,c.f_Inicio,c.tarifa,"
                    + "c.f_Vencimiento,c.f_Corte,c.condicionAntena,c.mac,c.ip,c.frecuencia,c.anchoBanda,"
                    + "ec.descripcion,a.nombreAntena,c.observacion,c.marca,c.ultimoCorteEjecutado "
                    + "FROM cliente c,estadocliente ec , antena a "
                    + "WHERE c.idEstado=ec.idEstado AND c.idAntena=a.idAntena "
                    + "AND c.apellido LIKE '" + a + "%' AND c.nombre LIKE '" + n + "%' AND c.DNI LIKE '" + d + "%'  "
                    + "ORDER BY idCliente DESC";

        }

        Connection cn = Conexion.abrir();

        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            Object[] f;
            while (rs.next()) {
                f = new Object[20];
                f[0] = rs.getInt(1);
                f[1] = rs.getString(2);
                f[2] = rs.getString(3);
                f[3] = rs.getString(4);
                f[4] = rs.getString(5);
                f[5] = rs.getString(6);
                f[6] = Fecha.convertToString(rs.getDate(7));
                f[7] = rs.getFloat(8);
                f[8] = Fecha.convertToString(rs.getDate(9));
                f[9] = Fecha.convertToString(rs.getDate(10));
                f[10] = rs.getString(11);
                f[11] = rs.getString(12);
                f[12] = rs.getString(13);
                f[13] = rs.getString(14);
                f[14] = rs.getString(15);
                f[15] = rs.getString(16);
                f[16] = rs.getString(17);
                f[17] = rs.getString(18);
                f[18] = rs.getString(19);
                f[19] = rs.getString(20);
                lst.add(f);

            }

        } catch (SQLException ex) {
            Mensaje.panelSms("Error_metodo listar Cliente" + ex);
            Logger.getLogger(DaoCliente_OLD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lst;
    }

    public static ArrayList<Cliente> BuscarX(int x, String cadena, boolean f) {
        //x: 0=nombre, 1=apellido, 2=Dni
        ArrayList<Cliente> lst = new ArrayList<>();
        String hoy = Fecha.hoy();

        String sql;
        if (f == false) {
            if (x == 0) {
                sql = "SELECT idCliente,nombre,apellido,DNI FROM cliente WHERE nombre LIKE '" + cadena + "%' AND idEstado !=6 AND idEstado !=1 AND idEstado !=7 OR F_pago_Saldo <='" + hoy + "'";
            } else if (x == 1) {
                sql = "SELECT idCliente,nombre,apellido,DNI FROM cliente WHERE apellido LIKE '" + cadena + "%' AND idEstado !=6 AND idEstado !=1 AND idEstado !=7 OR F_pago_Saldo <='" + hoy + "'";
            } else {
                sql = "SELECT idCliente,nombre,apellido,DNI FROM cliente WHERE DNI LIKE '" + cadena + "%' AND idEstado !=6 AND idEstado !=1 AND idEstado !=7 OR F_pago_Saldo <='" + hoy + "'";
            }

        } else {
            if (x == 0) {
                sql = "SELECT idCliente,nombre,apellido,DNI FROM cliente WHERE nombre LIKE '" + cadena + "%' AND idEstado !=6 AND idEstado !=7 OR F_pago_Saldo <='" + hoy + "'";
            } else if (x == 1) {
                sql = "SELECT idCliente,nombre,apellido,DNI FROM cliente WHERE apellido LIKE '" + cadena + "%' AND idEstado !=6 AND idEstado !=7 OR F_pago_Saldo <='" + hoy + "'";
            } else {
                sql = "SELECT idCliente,nombre,apellido,DNI FROM cliente WHERE DNI LIKE '" + cadena + "%' AND idEstado !=6 AND idEstado !=7 OR F_pago_Saldo <='" + hoy + "'";
            }

        }

        Connection cn = Conexion.abrir();
        PreparedStatement ps;
        try {
            ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            Cliente c;
            while (rs.next()) {
                c = new Cliente();
                c.setIdCliente(rs.getInt(1));
                c.setNombre(rs.getString(2));
                c.setApellido(rs.getString(3));
                c.setDni(rs.getString(4));
                lst.add(c);

            }
            rs.close();
            ps.close();
            cn.close();
        } catch (SQLException ex) {
            Mensaje.panelSms("Error de Busqueda " + ex);

        }

        return lst;
    }

    public static ArrayList<Cliente> BuscarClientesActivos(int x, String cadena, int estado) {
        //x: 0=nombre, 1=apellido, 2=Dni
        ArrayList<Cliente> lst = new ArrayList<>();

        String sql;

        if (x == 0) {
            sql = "SELECT idCliente,nombre,apellido,DNI FROM cliente WHERE nombre LIKE '" + cadena + "%' AND  idEstado =" + estado + "";
        } else if (x == 1) {
            sql = "SELECT idCliente,nombre,apellido,DNI FROM cliente WHERE apellido LIKE '" + cadena + "%' AND idEstado =" + estado + "";
        } else {
            sql = "SELECT idCliente,nombre,apellido,DNI FROM cliente WHERE DNI LIKE '" + cadena + "%' AND idEstado =" + estado + "";
        }

        Connection cn = Conexion.abrir();
        PreparedStatement ps;
        try {
            ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            Cliente c;
            while (rs.next()) {
                c = new Cliente();
                c.setIdCliente(rs.getInt(1));
                c.setNombre(rs.getString(2));
                c.setApellido(rs.getString(3));
                c.setDni(rs.getString(4));
                lst.add(c);

            }
            rs.close();
            ps.close();
            cn.close();
        } catch (SQLException ex) {
            Mensaje.panelSms("Error de Busqueda " + ex);

        }

        return lst;
    }

    public static ArrayList<Object[]> BuscarCliCortSE(int x, String cadena) {
        //x: 0=nombre, 1=apellido, 2=Dni
        /*
         sql = "SELECT c.idCliente,c.nombre,c.apellido,c.f_Vencimiento,c.f_Corte,s.nombreServidor,ec.descripcion "
         + "FROM cliente c,estadoCliente ec,antena a, servidor s"
         + " WHERE  c.idEstado=ec.idEstado AND c.idEstado=3 "
         + " AND c.idAntena=a.idAntena AND a.idServidor=s.idServidor";
        
        
         */

        ArrayList<Object[]> list = new ArrayList<>();
        String sql;
        if (x == 0) {
            sql = "SELECT c.idCliente,c.nombre,c.apellido,c.f_Vencimiento,c.f_Corte,s.nombreServidor,ec.descripcion "
                    + "FROM cliente c,estadoCliente ec,antena a, servidor s "
                    + "WHERE nombre LIKE '" + cadena + "%' "
                    + "AND c.idEstado=ec.idEstado AND c.idEstado=3 "
                    + "AND c.idAntena=a.idAntena AND a.idServidor=s.idServidor";
        } else {
            sql = "SELECT c.idCliente,c.nombre,c.apellido,c.f_Vencimiento,c.f_Corte,s.nombreServidor,ec.descripcion "
                    + "FROM cliente c,estadoCliente ec,antena a, servidor s "
                    + "WHERE apellido LIKE '" + cadena + "%' "
                    + "AND c.idEstado=ec.idEstado AND c.idEstado=3 "
                    + " AND c.idAntena=a.idAntena AND a.idServidor=s.idServidor";
        }

        Connection cn = Conexion.abrir();
        PreparedStatement ps;
        try {
            ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            Cliente c;
            Object[] o;
            while (rs.next()) {
                o = new Object[7];
                c = new Cliente();
                o[0] = rs.getInt(1);
                o[1] = rs.getString(2);
                o[2] = rs.getString(3);
                o[3] = rs.getString(4);
                o[4] = rs.getString(5);
                o[5] = rs.getString(6);
                o[6] = rs.getString(7);

                list.add(o);

            }
        } catch (SQLException ex) {
            Mensaje.panelSms("Error de Busqueda " + ex);

        }

        return list;
    }

    public static ArrayList<Object[]> BuscarCliCeRa(int x, String cadena) {
        //x: 0=nombre, 1=apellido, 2=Dni

        ArrayList<Object[]> list = new ArrayList<>();
        String sql;
        if (x == 0) {
            sql = "SELECT c.idCliente,c.nombre,c.apellido,c.DNI,ec.descripcion FROM cliente c,estadoCliente ec WHERE nombre LIKE '" + cadena + "%' AND c.idEstado=ec.idEstado AND (c.idEstado=4 OR c.idEstado=5)";
        } else if (x == 1) {
            sql = "SELECT c.idCliente,c.nombre,c.apellido,c.DNI,ec.descripcion FROM cliente c,estadoCliente ec WHERE apellido LIKE '" + cadena + "%' AND c.idEstado=ec.idEstado AND (c.idEstado=4 OR c.idEstado=5)";
        } else {
            sql = "SELECT c.idCliente,c.nombre,c.apellido,c.DNI,ec.descripcion FROM cliente c,estadoCliente ec WHERE DNI LIKE '" + cadena + "%' AND c.idEstado=ec.idEstado AND (c.idEstado=4 OR c.idEstado=5)";
        }

        Connection cn = Conexion.abrir();
        PreparedStatement ps;
        try {
            ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            Cliente c;
            Object[] o;
            while (rs.next()) {
                o = new Object[5];
                c = new Cliente();
                o[0] = rs.getInt(1);
                o[1] = rs.getString(2);
                o[2] = rs.getString(3);
                o[3] = rs.getString(4);
                o[4] = rs.getString(5);

                list.add(o);

            }
        } catch (SQLException ex) {
            Mensaje.panelSms("Error de Busqueda " + ex);

        }

        return list;
    }

    public static ArrayList<Object[]> BuscarCliRe(int x, String cadena) {
        //x: 0=nombre, 1=apellido, 2=Dni

        ArrayList<Object[]> list = new ArrayList<>();
        String sql;
        if (x == 0) {
            sql = "SELECT c.idCliente,c.nombre,c.apellido,c.DNI,ec.descripcion FROM cliente c,estadoCliente ec WHERE nombre LIKE '" + cadena + "%' AND c.idEstado=ec.idEstado AND (c.idEstado=6)";
        } else if (x == 1) {
            sql = "SELECT c.idCliente,c.nombre,c.apellido,c.DNI,ec.descripcion FROM cliente c,estadoCliente ec WHERE apellido LIKE '" + cadena + "%' AND c.idEstado=ec.idEstado AND (c.idEstado=6)";
        } else {
            sql = "SELECT c.idCliente,c.nombre,c.apellido,c.DNI,ec.descripcion FROM cliente c,estadoCliente ec WHERE DNI LIKE '" + cadena + "%' AND c.idEstado=ec.idEstado AND (c.idEstado=6)";
        }

        Connection cn = Conexion.abrir();
        PreparedStatement ps;
        try {
            ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            Cliente c;
            Object[] o;
            while (rs.next()) {
                o = new Object[5];
                c = new Cliente();
                o[0] = rs.getInt(1);
                o[1] = rs.getString(2);
                o[2] = rs.getString(3);
                o[3] = rs.getString(4);
                o[4] = rs.getString(5);

                list.add(o);

            }
        } catch (SQLException ex) {
            Mensaje.panelSms("Error de Busqueda " + ex);

        }

        return list;
    }

    public static ArrayList<Object[]> listarCliCortSE() {
        //x: 0=nombre, 1=apellido, 2=Dni

        ArrayList<Object[]> list = new ArrayList<>();
        String sql;
        sql = "SELECT c.idCliente,c.nombre,c.apellido,c.f_Vencimiento,c.f_Corte,s.nombreServidor,ec.descripcion "
                + "FROM cliente c,estadoCliente ec,antena a, servidor s"
                + " WHERE  c.idEstado=ec.idEstado AND c.idEstado=3 "
                + " AND c.idAntena=a.idAntena AND a.idServidor=s.idServidor";

        Connection cn = Conexion.abrir();
        PreparedStatement ps;
        try {
            ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            Cliente c;
            Object[] o;
            while (rs.next()) {
                o = new Object[7];
                //c = new Cliente();
                o[0] = rs.getInt(1);
                o[1] = rs.getString(2);
                o[2] = rs.getString(3);
                o[3] = rs.getString(4);
                o[4] = rs.getString(5);
                o[5] = rs.getString(6);
                o[6] = rs.getString(7);

                list.add(o);

            }
        } catch (SQLException ex) {
            Mensaje.panelSms("Error de Busqueda " + ex);

        }

        return list;
    }

    public static ArrayList<Object[]> listarCeRa() {
        //x: 0=nombre, 1=apellido, 2=Dni

        ArrayList<Object[]> list = new ArrayList<>();
        String sql;
        sql = "SELECT c.idCliente,c.nombre,c.apellido,c.DNI,ec.descripcion FROM cliente c,estadoCliente ec WHERE  c.idEstado=ec.idEstado AND (c.idEstado=4 OR c.idEstado=5  )";

        Connection cn = Conexion.abrir();
        PreparedStatement ps;
        try {
            ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            Cliente c;
            Object[] o;
            while (rs.next()) {
                o = new Object[5];
                c = new Cliente();
                o[0] = rs.getInt(1);
                o[1] = rs.getString(2);
                o[2] = rs.getString(3);
                o[3] = rs.getString(4);
                o[4] = rs.getString(5);

                list.add(o);

            }
        } catch (SQLException ex) {
            Mensaje.panelSms("Error de Busqueda " + ex);

        }

        return list;
    }

    public static ArrayList<Object[]> listarClienteRetirado() {
        //x: 0=nombre, 1=apellido, 2=Dni

        ArrayList<Object[]> list = new ArrayList<>();
        String sql;
        sql = "SELECT c.idCliente,c.nombre,c.apellido,c.DNI,ec.descripcion FROM cliente c,estadoCliente ec WHERE  c.idEstado=ec.idEstado AND (c.idEstado=6 )";

        Connection cn = Conexion.abrir();
        PreparedStatement ps;
        try {
            ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            Cliente c;
            Object[] o;
            while (rs.next()) {
                o = new Object[5];
                c = new Cliente();
                o[0] = rs.getInt(1);
                o[1] = rs.getString(2);
                o[2] = rs.getString(3);
                o[3] = rs.getString(4);
                o[4] = rs.getString(5);

                list.add(o);

            }
        } catch (SQLException ex) {
            Mensaje.panelSms("Error de Busqueda " + ex);

        }

        return list;
    }

    public static ArrayList<Object[]> listarClienteEstado(int es) {

        ArrayList<Object[]> list = new ArrayList<>();
        String sql;
        if (es == 0) {
            sql = "SELECT c.idCliente,c.nombre,c.apellido,ec.descripcion,c.f_Vencimiento,c.f_Corte,c.tarifa,c.ultimoCorteEjecutado	 "
                    + "FROM cliente c,estadoCliente ec "
                    + "WHERE  c.idEstado=ec.idEstado  ";
        } else {
            sql = "SELECT c.idCliente,c.nombre,c.apellido,ec.descripcion,c.f_Vencimiento,c.f_Corte,c.tarifa,c.ultimoCorteEjecutado "
                    + " FROM cliente c,estadoCliente ec "
                    + "WHERE  c.idEstado=ec.idEstado AND c.idEstado=" + es + " ";
        }

        Connection cn = Conexion.abrir();
        PreparedStatement ps;
        try {
            ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            Object[] o;
            while (rs.next()) {
                o = new Object[8];

                o[0] = rs.getInt(1);
                o[1] = rs.getString(2);
                o[2] = rs.getString(3);
                o[3] = rs.getString(4);
                o[4] = Fecha.convertToString(rs.getDate(5));
                o[5] = Fecha.convertToString(rs.getDate(6));
                o[6] = rs.getFloat(7);
                o[7] = Fecha.convertToString(rs.getDate(8));

                list.add(o);

            }
            rs.close();
            ps.close();
            cn.close();
        } catch (SQLException ex) {
            Mensaje.panelSms("Error de Busqueda " + ex);

        }

        return list;
    }

    public static ArrayList<Object[]> buscarCliente2(int es, String cadena, int x, String orden) {

        ArrayList<Object[]> list = new ArrayList<>();
        String sql;

        if (es == 8) {
            if (x == 0) {
                sql = "SELECT c.idCliente,c.nombre,c.apellido,ec.descripcion,c.f_Vencimiento,c.f_Corte,c.tarifa,c.ultimoCorteEjecutado,c.saldo "
                        + "FROM cliente c,estadoCliente ec "
                        + "WHERE  c.idEstado=ec.idEstado AND c.nombre LIKE '" + cadena + "%' AND c.saldo >0  " + orden + "";
            } else {
                sql = "SELECT c.idCliente,c.nombre,c.apellido,ec.descripcion,c.f_Vencimiento,c.f_Corte,c.tarifa,c.ultimoCorteEjecutado,c.saldo "
                        + "FROM cliente c,estadoCliente ec "
                        + "WHERE  c.idEstado=ec.idEstado AND c.apellido LIKE '" + cadena + "%' AND c.saldo >0 " + orden + " ";

            }
        } else {
            if (es == 0) {
                if (x == 0) {
                    sql = "SELECT c.idCliente,c.nombre,c.apellido,ec.descripcion,c.f_Vencimiento,c.f_Corte,c.tarifa,c.ultimoCorteEjecutado,c.saldo "
                            + "FROM cliente c,estadoCliente ec "
                            + "WHERE  c.idEstado=ec.idEstado AND c.nombre LIKE '" + cadena + "%' " + orden + "";
                } else {
                    sql = "SELECT c.idCliente,c.nombre,c.apellido,ec.descripcion,c.f_Vencimiento,c.f_Corte,c.tarifa,c.ultimoCorteEjecutado,c.saldo "
                            + "FROM cliente c,estadoCliente ec "
                            + "WHERE  c.idEstado=ec.idEstado AND c.apellido LIKE '" + cadena + "%' " + orden + "";

                }

            } else {
                if (x == 0) {
                    sql = "SELECT c.idCliente,c.nombre,c.apellido,ec.descripcion,c.f_Vencimiento,c.f_Corte,c.tarifa,c.ultimoCorteEjecutado,c.saldo "
                            + "FROM cliente c,estadoCliente ec "
                            + "WHERE  c.idEstado=ec.idEstado AND c.nombre LIKE '" + cadena + "%' AND c.idEstado=" + es + " " + orden + "";
                } else {
                    sql = "SELECT c.idCliente,c.nombre,c.apellido,ec.descripcion,c.f_Vencimiento,c.f_Corte,c.tarifa,c.ultimoCorteEjecutado,c.saldo "
                            + "FROM cliente c,estadoCliente ec "
                            + "WHERE  c.idEstado=ec.idEstado AND c.apellido LIKE '" + cadena + "%' AND c.idEstado=" + es + " " + orden + "";

                }

            }
        }
        //

        Connection cn = Conexion.abrir();
        PreparedStatement ps;
        try {
            ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            Object[] o;
            while (rs.next()) {
                o = new Object[9];

                o[0] = rs.getInt(1);
                o[1] = rs.getString(2);
                o[2] = rs.getString(3);
                o[3] = rs.getString(4);
                o[4] = Fecha.convertToString(rs.getDate(5));
                o[5] = Fecha.convertToString(rs.getDate(6));
                o[6] = rs.getFloat(7);
                o[7] = Fecha.convertToString(rs.getDate(8));
                o[8] = rs.getFloat(9);

                list.add(o);

            }
            rs.close();
            ps.close();
            cn.close();
        } catch (SQLException ex) {
            Mensaje.panelSms("Error de Busqueda " + ex);

        }

        return list;
    }

    public static ArrayList<Object[]> buscarClienteEstado(int es, String cadena, int x) {

        ArrayList<Object[]> list = new ArrayList<>();
        String sql;
        if (es == 0) {
            if (x == 0) {
                sql = "SELECT c.idCliente,c.nombre,c.apellido,ec.descripcion,c.f_Vencimiento,c.f_Corte,c.tarifa,c.ultimoCorteEjecutado "
                        + "FROM cliente c,estadoCliente ec "
                        + "WHERE  c.idEstado=ec.idEstado AND c.nombre LIKE '" + cadena + "%' ";
            } else {
                sql = "SELECT c.idCliente,c.nombre,c.apellido,ec.descripcion,c.f_Vencimiento,c.f_Corte,c.tarifa,c.ultimoCorteEjecutado "
                        + "FROM cliente c,estadoCliente ec "
                        + "WHERE  c.idEstado=ec.idEstado AND c.apellido LIKE '" + cadena + "%' ";

            }

        } else {
            if (x == 0) {
                sql = "SELECT c.idCliente,c.nombre,c.apellido,ec.descripcion,c.f_Vencimiento,c.f_Corte,c.tarifa,c.ultimoCorteEjecutado "
                        + "FROM cliente c,estadoCliente ec "
                        + "WHERE  c.idEstado=ec.idEstado AND c.nombre LIKE '" + cadena + "%' AND c.idEstado=" + es + "  ";
            } else {
                sql = "SELECT c.idCliente,c.nombre,c.apellido,ec.descripcion,c.f_Vencimiento,c.f_Corte,c.tarifa,c.ultimoCorteEjecutado "
                        + "FROM cliente c,estadoCliente ec "
                        + "WHERE  c.idEstado=ec.idEstado AND c.apellido LIKE '" + cadena + "%' AND c.idEstado=" + es + " ";

            }

        }

        Connection cn = Conexion.abrir();
        PreparedStatement ps;
        try {
            ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            Object[] o;
            while (rs.next()) {
                o = new Object[8];

                o[0] = rs.getInt(1);
                o[1] = rs.getString(2);
                o[2] = rs.getString(3);
                o[3] = rs.getString(4);
                o[4] = Fecha.convertToString(rs.getDate(5));
                o[5] = Fecha.convertToString(rs.getDate(6));
                o[6] = rs.getFloat(7);
                o[7] = Fecha.convertToString(rs.getDate(8));

                list.add(o);

            }
            rs.close();
            ps.close();
            cn.close();
        } catch (SQLException ex) {
            Mensaje.panelSms("Error de Busqueda " + ex);

        }

        return list;
    }

    public static Object[] llenarTodosLosCampos(int id) {
        // ArrayList<Object[]> list = new ArrayList<>();
        Object[] b = new Object[0];
        Object[] o;
        String sql = "SELECT c.apellido,c.nombre,c.DNI,c.correo, c.f_Inicio,c.tarifa,c.direccion,c.f_Vencimiento,c.f_Corte,c.condicionAntena,c.mac,c.ip,c.frecuencia, "
                + "c.marca,e.descripcion,c.anchoBanda,c.observacion,a.nombreAntena,a.mac,a.ip,a.frecuencia,a.passwConfig,a.passwConeccion,a.canal,s.nombreServidor,s.ipEntrada,s.ipSalida,t.nombreTorre,t.direccion,c.ultimoCorteEjecutado,c.F_Congelada"
                + " FROM cliente  c "
                + " INNER JOIN estadocliente e ON c.idEstado=e.idEstado "
                + " INNER JOIN antena a ON c.idAntena=a.idAntena "
                + " INNER JOIN servidor s ON a.idServidor=s.idServidor "
                + " INNER JOIN torre t ON a.idTorre=t.idTorre "
                + " AND c.idCliente=" + id + "";

        Connection cn = Conexion.abrir();
        PreparedStatement ps;
        try {
            ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                o = new Object[31];

                o[0] = rs.getString(1);
                o[1] = rs.getString(2);
                o[2] = rs.getString(3);
                o[3] = rs.getString(4);
                o[4] = rs.getString(5);//f_inicio
                o[5] = rs.getString(6);
                o[6] = rs.getString(7);
                o[7] = Fecha.convertToString(rs.getDate(8));//f_pago
                o[8] = Fecha.convertToString(rs.getDate(9));//f_corte
                o[9] = rs.getString(10);
                o[10] = rs.getString(11);
                o[11] = rs.getString(12);
                o[12] = rs.getString(13);
                o[13] = rs.getString(14);
                o[14] = rs.getString(15);
                o[15] = rs.getString(16);
                o[16] = rs.getString(17);
                o[17] = rs.getString(18);
                o[18] = rs.getString(19);
                o[19] = rs.getString(20);
                o[20] = rs.getString(21);
                o[21] = rs.getString(22);
                o[22] = rs.getString(23);
                o[23] = rs.getString(24);
                o[24] = rs.getString(25);
                o[25] = rs.getString(26);
                o[26] = rs.getString(27);
                o[27] = rs.getString(28);
                o[28] = rs.getString(29);
                o[29] = Fecha.convertToString(rs.getDate(30));//f_ejec/corte
                o[30] = Fecha.convertToString(rs.getDate(31));//f_congelada

                b = o;

            }

            rs.close();
            ps.close();
            cn.close();
        } catch (SQLException ex) {
            Mensaje.panelSms("Error en LlenarTodosLosCampos " + ex);

        }

        return b;
    }

    public static ArrayList<Object[]> buscarCliente(int x, String nomApe, String consulta) {

        ArrayList<Object[]> list = new ArrayList<>();
        String sql;

        if (x == 0) {
            sql = "SELECT c.idCliente,c.nombre,c.apellido,c.DNI,e.descripcion,c.marca,a.nombreAntena,s.nombreServidor,t.nombreTorre"
                    + " FROM cliente  c INNER JOIN estadocliente e ON c.idEstado=e.idEstado "
                    + "INNER JOIN antena a ON c.idAntena=a.idAntena INNER JOIN servidor s ON a.idServidor=s.idServidor "
                    + "INNER JOIN torre t ON a.idTorre=t.idTorre AND c.nombre LIKE '" + nomApe + "%' " + consulta + " ORDER BY c.nombre ASC";

        } else if (x == 1) {
            sql = "SELECT c.idCliente,c.nombre,c.apellido,c.DNI,e.descripcion,c.marca,a.nombreAntena,s.nombreServidor,t.nombreTorre"
                    + " FROM `cliente`  c INNER JOIN estadocliente e ON c.idEstado=e.idEstado "
                    + "INNER JOIN antena a ON c.idAntena=a.idAntena INNER JOIN servidor s ON a.idServidor=s.idServidor "
                    + "INNER JOIN torre t ON a.idTorre=t.idTorre AND c.apellido LIKE '" + nomApe + "%' " + consulta + " ORDER BY c.nombre ASC";

        } else {
            sql = "SELECT c.idCliente,c.nombre,c.apellido,c.DNI,e.descripcion,c.marca,a.nombreAntena,s.nombreServidor,t.nombreTorre"
                    + " FROM `cliente`  c INNER JOIN estadocliente e ON c.idEstado=e.idEstado "
                    + "INNER JOIN antena a ON c.idAntena=a.idAntena INNER JOIN servidor s ON a.idServidor=s.idServidor "
                    + "INNER JOIN torre t ON a.idTorre=t.idTorre AND c.DNI LIKE '" + nomApe + "%' " + consulta + " ORDER BY c.nombre ASC";

        }

        Connection cn = Conexion.abrir();
        PreparedStatement ps;
        try {
            ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            Object[] o;
            while (rs.next()) {
                o = new Object[9];

                o[0] = rs.getInt(1);
                o[1] = rs.getString(2);
                o[2] = rs.getString(3);
                o[3] = rs.getString(4);
                o[4] = rs.getString(5);
                o[5] = rs.getString(6);
                o[6] = rs.getString(7);
                o[7] = rs.getString(8);
                o[8] = rs.getString(9);

                list.add(o);

            }
            rs.close();
            ps.close();
            cn.close();
        } catch (SQLException ex) {
            Mensaje.panelSms("Error de Busqueda metodo buscarCliente de DaoCliente" + ex);

        }

        return list;
    }

    public static boolean cortarServicio(int id) {
        boolean f = false;
        String sql = "UPDATE cliente SET idEstado=4,ultimoCorteEjecutado='" + Fecha.hoy() + "' WHERE idCliente=" + id + " ";
        Connection cn = Conexion.abrir();
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.executeUpdate();
            f = true;
        } catch (SQLException ex) {
            Mensaje.panelSms("Error: " + ex);
        }
        return f;
    }

    public static boolean desabilitarCliente(int id) {
        boolean f = false;
        String sql = "UPDATE cliente SET idEstado=6 WHERE idCliente=" + id + " ";
        Connection cn = Conexion.abrir();
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.executeUpdate();
            f = true;
        } catch (SQLException ex) {
            Mensaje.panelSms("Error: " + ex);
        }
        return f;
    }

    public static boolean habilitarCliente(int id, int e) {
        boolean f = false;
        String sql = "UPDATE cliente SET idEstado=" + e + " WHERE idCliente=" + id + " ";
        Connection cn = Conexion.abrir();
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.executeUpdate();
            f = true;
        } catch (SQLException ex) {
            Mensaje.panelSms("Error: " + ex);
        }
        return f;
    }

    public static void modificarCliente(Cliente c) {
        String sql = "UPDATE cliente SET apellido=?,nombre=?,DNI=?,direccion=?,correo=?,f_Inicio=?,tarifa=?,"
                + "f_Vencimiento=?,f_Corte=?,condicionAntena=?,mac=?,ip=?,frecuencia=?,anchoBanda=?,"
                + "idEstado=?,idAntena=?,observacion=?,marca=?,ultimoCorteEjecutado=? "
                + "WHERE idCliente=?";

        Connection cn = Conexion.abrir();
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, c.getApellido());
            ps.setString(2, c.getNombre());
            ps.setString(3, c.getDni());
            ps.setString(4, c.getDireccion());
            ps.setString(5, c.getCorreo());
            ps.setString(6, c.getFechInicio());
            ps.setFloat(7, c.getTarifa());
            ps.setString(8, c.getFechVencimiento());
            ps.setString(9, c.getFechCorte());
            ps.setString(10, c.getCondicioAntena());
            ps.setString(11, c.getMac());
            ps.setString(12, c.getIp());
            ps.setString(13, c.getFrecuenci());
            ps.setString(14, c.getAnchoBanda());
            ps.setInt(15, c.getIdEstado());
            ps.setInt(16, c.getIdAntena());
            ps.setString(17, c.getObservacion());
            ps.setString(18, c.getMarca());
            ps.setString(19, c.getUltCorteEjecutado());
            ps.setInt(20, c.getIdCliente());
            ps.executeUpdate();

        } catch (SQLException ex) {
            Mensaje.panelSms("Error metodo Actualizar" + ex);
            Logger.getLogger(DaoCliente_OLD.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
   

    public static int getUltimoIdCliente() {
        int id = 0;
        String sql = "SELECT max(idCliente) FROM cliente";
        Connection cn = Conexion.abrir();
        try {

            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DaoCliente_OLD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return id;
    }

    public static void eliminarCliente(int id) {
        String sql = "DELETE FROM cliente WHERE idCliente=" + id + "";
        Connection cn = Conexion.abrir();
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Mensaje.panelSms("Error al Eliminar Cliente : " + ex);
        }

    }

    public static Object[] buscarCliXid(int id) {
        Object[] cli = new Object[8];

        String sql = "SELECT c.f_Vencimiento,c.f_Corte,c.tarifa,e.descripcion,c.ultimoCorteEjecutado,c.idEstado,c.saldo,c.F_pago_Saldo "
                + "FROM cliente c,estadocliente e WHERE c.idEstado=e.idEstado AND c.idCliente=" + id + "";
        Connection cn = Conexion.abrir();
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {

                cli[0] = rs.getDate(1);
                cli[1] = Fecha.convertToString(rs.getDate(2));
                cli[2] = rs.getFloat(3);
                cli[3] = rs.getString(4);
                cli[4] = rs.getDate(5);
                cli[5] = rs.getInt(6);
                cli[6] = rs.getFloat(7);
                cli[7] = rs.getDate(8);
            }
            rs.close();
            ps.close();
            cn.close();

        } catch (SQLException ex) {
            Mensaje.panelSms("Error / Cliente no encontrado " + ex);

        }

        return cli;
    }

    public static Object[] buscarCliCongeladoXid(int id) {
        Object[] cli = new Object[6];

        String sql = "SELECT c.f_Vencimiento,c.f_Corte,c.tarifa,e.descripcion,c.F_Congelada,c.idEstado "
                + "FROM cliente c,estadocliente e WHERE c.idEstado=e.idEstado AND c.idCliente=" + id + "";
        Connection cn = Conexion.abrir();
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {

                cli[0] = rs.getDate(1);
                cli[1] = Fecha.convertToString(rs.getDate(2));
                cli[2] = rs.getFloat(3);
                cli[3] = rs.getString(4);
                cli[4] = rs.getDate(5);
                cli[5] = rs.getInt(6);
            }
            rs.close();
            ps.close();
            cn.close();

        } catch (SQLException ex) {
            Mensaje.panelSms("Error / Cliente no encontrado " + ex);

        }

        return cli;
    }

    public static void DescongelarCliente(Cliente c) {
        //cambia de estado congelado a estado activo, actualiza las nuevas fechas de pago, corte y pone en null f_congelada
        String sql = "UPDATE cliente SET idEstado = 1,f_Vencimiento=?,f_Corte=?,F_Congelada=NULL WHERE idCliente=? ";
        Connection cn = Conexion.abrir();
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, c.getFechVencimiento());
            ps.setString(2, c.getFechCorte());
            ps.setInt(3, c.getIdCliente());

            ps.executeUpdate();
            ps.close();
            cn.close();

        } catch (SQLException ex) {
            System.out.println("ERROR EN METODO DescongelarCliente");
        }

    }

    public static void activarEstado(Cliente c, Connection cn) throws SQLException {
        String sql;
        if (c.getSaldo() > 0) {
            //caso de pago a cuenta
            System.out.println(" caso pago a cuenta");
            sql = "UPDATE cliente SET f_Vencimiento=?, f_Corte = ?, idEstado = '1',ultimoCorteEjecutado=NULL,saldo=?,F_pago_Saldo=?  WHERE  idCliente = ? ";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, c.getFechVencimiento());
            ps.setString(2, c.getFechCorte());
            ps.setFloat(3, c.getSaldo());
            ps.setString(4, c.getF_PagoSaldo());
            ps.setInt(5, c.getIdCliente());
            ps.executeUpdate();

        } else if (c.getFechVencimiento() == null && c.getFechCorte() == null) {
            // caso en el que el cliente cancela solo el salo
            System.out.println("caso en el que el cliente cancela solo el salo");
            sql = "UPDATE cliente SET  saldo=0,F_pago_Saldo=NULL  WHERE  idCliente = ? ";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setInt(1, c.getIdCliente());
            ps.executeUpdate();
        } else {
            // caso de pado completo normal
            System.out.println("caso de pado completo normal Y TOTAL ");
            sql = "UPDATE cliente SET f_Vencimiento=?, f_Corte = ?, idEstado = '1',ultimoCorteEjecutado=NULL,saldo=0,F_pago_Saldo=NULL WHERE  idCliente = ? ";

            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, c.getFechVencimiento());
            ps.setString(2, c.getFechCorte());
            ps.setInt(3, c.getIdCliente());
            ps.executeUpdate();

        }

    }

    public static ArrayList<String> listarMarcas() {
        ArrayList<String> lst = new ArrayList<>();
        String sql = "SELECT marca FROM marcaAntena";
        Connection cn = Conexion.abrir();

        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String marca = rs.getString(1);
                lst.add(marca);
            }
            rs.close();
            ps.close();
            cn.close();
        } catch (SQLException ex) {
            Mensaje.panelSms("ERROR en metodo listar marcas" + ex);
        }

        return lst;
    }

    public static ArrayList<String> listarAntenaBaseCli() {
        ArrayList<String> lst = new ArrayList<>();
        String sql = "SELECT nombreAntena FROM antena WHERE idTipo=1";
        Connection cn = Conexion.abrir();

        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String marca = rs.getString(1);
                lst.add(marca);
            }
            rs.close();
            ps.close();
            cn.close();

        } catch (SQLException ex) {
            Mensaje.panelSms("ERROR en metodo listar antena" + ex);
        }

        return lst;
    }

    public static ArrayList<String> listarAntenaBaseCliXtorre(String torre) {
        ArrayList<String> lst = new ArrayList<>();
        String sql;
        if (torre == "todas") {
            sql = "SELECT nombreAntena FROM antena WHERE idTipo=1";
        } else {
            sql = "SELECT a.nombreAntena FROM antena a INNER JOIN torre t ON a.idTorre=t.idTorre  "
                    + "AND t.nombreTorre='" + torre + "' AND a.idTipo=1 ";

        }

        Connection cn = Conexion.abrir();

        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String marca = rs.getString(1);
                lst.add(marca);
            }
            rs.close();
            ps.close();
            cn.close();

        } catch (SQLException ex) {
            Mensaje.panelSms("ERROR en metodo listar antena2" + ex);
        }

        return lst;
    }

}
