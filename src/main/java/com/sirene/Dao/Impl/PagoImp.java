package com.sirene.Dao.Impl;

import com.sirene.Bean.Pago;
import com.sirene.Coneccion.Conexion;
import com.sirene.Dao.PagoDao;
import com.sirene.Utilitarios.Fecha;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PagoImp implements PagoDao{

    @Override
    public  void RegistrarPago(Pago p, Connection cn) throws SQLException {
        int k = p.getIdEstado();
        String sql;
        if (k == 4 || k == 5) {
            sql = "INSERT INTO pago(fecha,monto,periodo,idCliente,idPersonal)VALUES(?,?,?,?,?)";
        } else {
            System.out.println("registar pago k!= 4,5");
            sql = "INSERT INTO pago(fecha,monto,periodo,idCliente,idPersonal,Activado)VALUES(?,?,?,?,?,'SI')";
        }

        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setString(1, p.getFech());
        ps.setFloat(2, p.getMonto());
        ps.setString(3, p.getPeriodo());
        ps.setInt(4, p.getIdCliente());
        ps.setInt(5, p.getIdPersonal());
        ps.executeUpdate();

    }

    @Override
    public  void RegistrarPagoAcuenta(Pago p, Connection cn) throws SQLException {
        int k = p.getIdEstado();
        String sql;
        if (k == 4 || k == 5) {
            sql = "INSERT INTO pago(fecha,monto,periodo,idCliente,idPersonal)VALUES(?,?,?,?,?)";
        } else {
            sql = "INSERT INTO pago(fecha,monto,periodo,idCliente,idPersonal,Activado)VALUES(?,?,?,?,?,'SI')";
        }

        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setString(1, p.getFech());
        ps.setFloat(2, p.getMonto());
        ps.setString(3, p.getPeriodo());
        ps.setInt(4, p.getIdCliente());
        ps.setInt(5, p.getIdPersonal());
        ps.executeUpdate();

    }

    @Override
    public  ArrayList<Object[]> ConsultarPago(String consulta, String consulta2) {
        ArrayList<Object[]> lst = new ArrayList<>();
        String sql = "SELECT p.IdPago,p.fecha, c.nombre,c.apellido,c.DNI, p.periodo,p.monto,pe.nombre,p.Anulado "
                + "FROM pago p INNER JOIN cliente c ON p.idCliente=c.idCliente INNER JOIN personal pe ON p.idPersonal=pe.idPersonal"
                + consulta + consulta2 + " ORDER BY  p.fecha ASC";
        System.out.println("ingrso, consulta sql :" + sql);
        Connection cn = Conexion.abrir();
        System.out.println("001 ");
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            System.out.println(" 002");
            ResultSet rs = ps.executeQuery();
            System.out.println("003");
            Object[] f;
            while (rs.next()) {
                f = new Object[9];

                f[0] = rs.getInt(1);
                f[1] = Fecha.convertToString(rs.getDate(2));
                f[2] = rs.getString(3);
                f[3] = rs.getString(4);
                f[4] = rs.getString(5);;
                f[5] = rs.getString(6);;
                f[6] = rs.getDouble(7);
                f[7] = rs.getString(8);
                f[8] = rs.getString(9);
                lst.add(f);

            }

            rs.close();
            ps.close();
            cn.close();

        } catch (SQLException ex) {
            System.out.println("error en consulatr pago :" + ex);
        }
        return lst;
    }

    @Override
    public  boolean AnularPago(Pago p) {
        boolean flag = true;
        float montoAnulado = p.getMonto() * (-1);
        int id = p.getIdPago();
        String sql = "UPDATE pago SET Anulado='SI',monto=" + montoAnulado + " WHERE idPago=" + id + "";
        Connection cn = Conexion.abrir();
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.executeUpdate();
            ps.close();
            cn.close();
            flag = true;
        } catch (SQLException ex) {
            System.out.println("Error -Anular  pago : " + ex);
            flag = false;
        }
        return flag;
    }

    @Override
    public  ArrayList<Object[]> clientesXactivar() {
        ArrayList<Object[]> lst = new ArrayList<>();
        String sql = "SELECT p.fecha,CONCAT(c.nombre,',',c.apellido) AS Cliente,s.nombreServidor,p.idPago "
                + "FROM pago p INNER JOIN cliente c ON p.idCliente=c.idCliente "
                + "INNER JOIN antena a ON c.idAntena=a.idAntena "
                + "INNER JOIN servidor s ON a.idServidor=s.idServidor "
                + " AND p.Activado='NO'"
                + " ORDER BY p.idPago DESC";
        Connection cn = Conexion.abrir();
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            Object[] f;
            while (rs.next()) {
                f = new Object[7];
                f[0] = Fecha.convertToString(rs.getDate(1));
                f[1] = rs.getString(2);
                f[2] = rs.getString(3);
                f[3] = rs.getInt(4);
                lst.add(f);

            }
            rs.close();
            ps.close();
            cn.close();

        } catch (SQLException ex) {
            System.out.println("error en consulatar cliente por activar :" + ex);
        }
        return lst;
    }

    @Override
    public  void activarCliente(int id) {
        String sql = "UPDATE pago SET Activado='SI' WHERE idPago=" + id + "";
        Connection cn = Conexion.abrir();
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.executeUpdate();
            ps.close();
            cn.close();
        } catch (SQLException ex) {
            System.out.println("Error -Activar Cliente : " + ex);
        }
    }

    @Override
    public  float pagoTotalFecha(String fecha) {
        String sql = "SELECT sum(`monto`) FROM `pago` WHERE fecha='" + fecha + "'";
        Connection cn = Conexion.abrir();
        float total = 0;
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                total = rs.getFloat(1);
            }

        } catch (SQLException ex) {
            System.out.println("ERROR :" + ex);

        }

        return total;
    }

    @Override
    public  float pagoTotalFechaRango(String fechaI, String fechaF) {
        String sql = "SELECT sum(`monto`) FROM `pago` WHERE fecha BETWEEN '" + fechaI + "' AND '" + fechaF + "' ";
        Connection cn = Conexion.abrir();
        float total = 0;
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                total = rs.getFloat(1);
            }

        } catch (SQLException ex) {
            System.out.println("ERROR :" + ex);

        }

        return total;
    }

    @Override
    public  float pagoTotalFechaMes(int mes, int año) {
        String sql = "SELECT sum(`monto`) FROM `pago` WHERE month(fecha)=" + mes + " AND year(fecha)=" + año + " ";
        Connection cn = Conexion.abrir();
        float total = 0;
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                total = rs.getFloat(1);
            }

        } catch (SQLException ex) {
            System.out.println("ERROR :" + ex);

        }

        return total;
    }

}
