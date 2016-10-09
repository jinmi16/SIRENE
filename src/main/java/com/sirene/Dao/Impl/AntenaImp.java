package com.sirene.Dao.Impl;

import com.sirene.Bean.Antena;
import com.sirene.Bean.tipoAntena;
import com.sirene.Coneccion.Conexion;
import com.sirene.Coneccion.Conexionn;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.sirene.Dao.AntenaDao;

public class AntenaImp implements AntenaDao{

    
    @Override
    public void registrar(Antena a) {
        String sql = "INSERT INTO antena(`nombreAntena`, `ip`, `mac`, `frecuencia`, `canal`, "
                + "`passwConfig`, `passwConeccion`, `idAntenaEnlace`, `idServidor`, `idTorre`, `idTipo`) "
                + "VALUES (?, ?,?, ?, ?, ?, ?, ?, ?,?, ?)";
        Connection cn = Conexion.abrir();
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, a.getNombre());
            ps.setString(2, a.getIp());
            ps.setString(3, a.getMac());
            ps.setString(4, a.getFrecuencia());
            ps.setString(5, a.getCanal());
            ps.setString(6, a.getPasswConfig());
            ps.setString(7, a.getPasswConexion());
            ps.setInt(8, a.getIdAntenaEnlace());
            ps.setInt(9, a.getIdServidor());
            ps.setInt(10, a.getIdTorre());
            ps.setInt(11, a.getIdTipo());
            ps.executeUpdate();
            ps.close();
            cn.close();

        } catch (SQLException ex) {
            Mensaje.panelSms("error : " + ex);
            //Logger.getLogger(DaoAntena.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public ArrayList<Antena> listar() {
        ArrayList<Antena> lista = new ArrayList<>();
        String sql = "SELECT * FROM antena";
        Connection cn = Conexion.abrir();
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            Antena a;

            while (rs.next()) {
                a = new Antena();
                a.setIdAntena(rs.getInt(1));
                a.setNombre(rs.getString(2));
                a.setIp(rs.getString(3));
                a.setMac(rs.getString(4));
                a.setFrecuencia(rs.getString(5));
                a.setCanal(rs.getString(6));
                a.setPasswConfig(rs.getString(7));
                a.setPasswConexion(rs.getString(8));
                a.setIdAntenaEnlace(rs.getInt(9));
                a.setIdServidor(rs.getInt(10));
                a.setIdTorre(rs.getInt(11));
                a.setIdTipo(rs.getInt(12));
                lista.add(a);
                //SELECT idAntena,nombreAntena FROM antena where idTipo=2

            }
        } catch (SQLException ex) {
            Mensaje.panelSms("Error en listar Antena : " + ex);
            Logger.getLogger(AntenaImp.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lista;
    }

    @Override
    public ArrayList<Object[]> listar2() {
        ArrayList<Object[]> lista = new ArrayList<>();
        //{"Id", "Nombre", "IP", "Mac", "Frecuencia", "Canal", "Passw_Config", "Passw_conexion", "id_Ant_Enlace", "id_Serv", "id_Torre", "id_Tipo"};
        String sql = "SELECT a.idAntena,a.nombreAntena,a.ip,a.mac,a.frecuencia,a.canal,a.passwConfig,a.passwConeccion,a.idAntenaEnlace,s.nombreServidor,t.nombreTorre,ta.tipoAntena"
                + " FROM antena a INNER JOIN servidor s ON a.idServidor=s.idServidor INNER JOIN torre t ON a.idTorre=t.idTorre INNER JOIN tipoantena ta ON a.idTipo=ta.idtipo "
                + " ORDER BY a.idAntena DESC";
        Connection cn = Conexion.abrir();
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            Object[] a;
            while (rs.next()) {
                a = new Object[12];
                a[0] = rs.getInt(1);
                a[1] = rs.getString(2);
                a[2] = rs.getString(3);
                a[3] = rs.getString(4);
                a[4] = rs.getString(5);
                a[5] = rs.getString(6);
                a[6] = rs.getString(7);
                a[7] = rs.getString(8);
                a[8] = rs.getInt(9);
                a[9] = rs.getString(10);
                a[10] = rs.getString(11);
                a[11] = rs.getString(12);
                lista.add(a);
                //SELECT idAntena,nombreAntena FROM antena where idTipo=2

            }
        } catch (SQLException ex) {
            Mensaje.panelSms("Error en listar Antena : " + ex);
            Logger.getLogger(AntenaImp.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lista;
    }

    @Override
    public ArrayList<Object[]> listarBuquedadNomTipo(String nom, String consulta) {
        ArrayList<Object[]> lista = new ArrayList<>();
        //{"Id", "Nombre", "IP", "Mac", "Frecuencia", "Canal", "Passw_Config", "Passw_conexion", "id_Ant_Enlace", "id_Serv", "id_Torre", "id_Tipo"};
        String sql = "SELECT a.idAntena,a.nombreAntena,a.ip,a.mac,a.frecuencia,a.canal,a.passwConfig,a.passwConeccion,a.idAntenaEnlace,s.nombreServidor,t.nombreTorre,ta.tipoAntena"
                + " FROM antena a INNER JOIN servidor s ON a.idServidor=s.idServidor INNER JOIN torre t ON a.idTorre=t.idTorre INNER JOIN tipoantena ta ON a.idTipo=ta.idtipo "
                + " AND a.nombreAntena LIKE '" + nom + "%'" + consulta
                + " ORDER BY a.idAntena DESC";
        Connection cn = Conexion.abrir();
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            Object[] a;
            while (rs.next()) {
                a = new Object[12];
                a[0] = rs.getInt(1);
                a[1] = rs.getString(2);
                a[2] = rs.getString(3);
                a[3] = rs.getString(4);
                a[4] = rs.getString(5);
                a[5] = rs.getString(6);
                a[6] = rs.getString(7);
                a[7] = rs.getString(8);
                a[8] = rs.getInt(9);
                a[9] = rs.getString(10);
                a[10] = rs.getString(11);
                a[11] = rs.getString(12);
                lista.add(a);
                //SELECT idAntena,nombreAntena FROM antena where idTipo=2

            }
        } catch (SQLException ex) {
            Mensaje.panelSms("Error en listar Antena : " + ex);
            Logger.getLogger(AntenaImp.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lista;
    }

    @Override
    public ArrayList<String> antenaEE(String torre) {
        ArrayList<String> lst = new ArrayList<>();
        String sql = "SELECT a.nombreAntena FROM antena a INNER JOIN torre t ON a.idTorre=t.idTorre "
                + "AND a.idTipo=2 AND t.nombreTorre='" + torre + "'";
        Connection cn = Conexion.abrir();
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            String a;
            while (rs.next()) {
                a = rs.getString(1);
                lst.add(a);

            }

        } catch (SQLException ex) {
            System.out.println("error listando antena EE :" + ex);
        }
        return lst;
    }

    @Override
    public ArrayList<Antena> buscarNombre(String nom, String tipo) {
        ArrayList<Antena> lista = new ArrayList<>();
        String sql;
        if (tipo.equals("todos")) {
            sql = "SELECT * FROM antena WHERE nombreAntena like '" + nom + "%'";
        } else {
            sql = "SELECT a.idAntena,a.nombreAntena,a.ip,a.mac,a.frecuencia,a.canal,a.passwConfig,a.passwConeccion,a.idAntenaEnlace ,a.idServidor,a.idTorre,a.idTipo "
                    + "FROM  antena a, tipoAntena t WHERE a.nombreAntena like '" + nom + "%'  AND a.idTipo=t.idtipo AND t.tipoAntena='" + tipo + "'";
        }
        //sql = "SELECT * FROM antena WHERE nombreAntena like ?";
        Connection cn = Conexion.abrir();
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            //ps.setString(1, nom + '%');
            ResultSet rs = ps.executeQuery();
            Antena a;

            while (rs.next()) {
                a = new Antena();
                a.setIdAntena(rs.getInt(1));
                a.setNombre(rs.getString(2));
                a.setIp(rs.getString(3));
                a.setMac(rs.getString(4));
                a.setFrecuencia(rs.getString(5));
                a.setCanal(rs.getString(6));
                a.setPasswConfig(rs.getString(7));
                a.setPasswConexion(rs.getString(8));
                a.setIdAntenaEnlace(rs.getInt(9));
                a.setIdServidor(rs.getInt(10));
                a.setIdTorre(rs.getInt(11));
                a.setIdTipo(rs.getInt(12));
                lista.add(a);
                //SELECT idAntena,nombreAntena FROM antena where idTipo=2

            }
        } catch (SQLException ex) {
            Mensaje.panelSms("Error en listar Antena en busqueda : " + ex);
            Logger.getLogger(AntenaImp.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lista;
    }

    @Override
    public ArrayList<Antena> listarAntEnlEmisora() {
        ArrayList<Antena> lista = new ArrayList<>();
        String sql = "SELECT idAntena,nombreAntena FROM antena where idTipo=2";
        Connection cn = Conexion.abrir();
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            Antena a;

            while (rs.next()) {
                a = new Antena();
                a.setIdAntena(rs.getInt(1));
                a.setNombre(rs.getString(2));

                lista.add(a);

            }
        } catch (SQLException ex) {
            Mensaje.panelSms("Error en listar Antena de enlace Emisora : " + ex);
            Logger.getLogger(AntenaImp.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lista;
    }

    @Override
    public ArrayList<tipoAntena> listarTipoAntena() {
        ArrayList<tipoAntena> listAnt = new ArrayList<>();
        String sql = "SELECT * FROM tipoantena";
        Connection cn = Conexion.abrir();
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            tipoAntena ta;
            while (rs.next()) {
                ta = new tipoAntena();
                ta.setIdTipo(rs.getInt(1));
                ta.setTipoAntena(rs.getString(2));
                listAnt.add(ta);

            }

        } catch (SQLException ex) {
            Mensaje.panelSms("error en listar TIPO ANTENA" + ex);
            Logger.getLogger(AntenaImp.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listAnt;
    }

    @Override
    public void modificar(Antena a) {
        String sql = "UPDATE antena SET nombreAntena=?,ip=?,mac=?,frecuencia=?,canal=?,passwConfig=?,passwConeccion=?,"
                + "idAntenaEnlace=?, idServidor=?,idTorre=?,idTipo=? WHERE idAntena=? ";
        Connection cn = Conexion.abrir();
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, a.getNombre());
            ps.setString(2, a.getIp());
            ps.setString(3, a.getMac());
            ps.setString(4, a.getFrecuencia());
            ps.setString(5, a.getCanal());
            ps.setString(6, a.getPasswConfig());
            ps.setString(7, a.getPasswConexion());
            ps.setInt(8, a.getIdAntenaEnlace());
            ps.setInt(9, a.getIdServidor());
            ps.setInt(10, a.getIdTorre());
            ps.setInt(11, a.getIdTipo());
            ps.setInt(12, a.getIdAntena());
            ps.executeUpdate();
            ps.close();
            cn.close();

        } catch (SQLException ex) {
            Mensaje.panelSms("error al MODICAR ANTENA :" + ex);
            Logger.getLogger(AntenaImp.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM antena WHERE idAntena=?";
        Connection cn = Conexion.abrir();
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
            cn.close();
        } catch (SQLException ex) {
            Mensaje.panelSms("ERRO EN ELIMINAR ANTENA" + ex);
            Logger.getLogger(AntenaImp.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public ArrayList<Object[]> listarBusAntena() {

        ArrayList<Object[]> lst = new ArrayList<>();

        Connection cn = Conexion.abrir();
        String sql = "SELECT a.idAntena,a.nombreAntena,t.nombreTorre,s.nombreServidor "
                + "FROM antena a,torre t,servidor s WHERE a.idTorre=t.idTorre "
                + "AND a.idServidor=s.idServidor AND a.idTipo=1";
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            Object[] r;
            while (rs.next()) {
                r = new Object[4];
                r[0] = rs.getString(1);
                r[1] = rs.getString(2);
                r[2] = rs.getString(3);
                r[3] = rs.getString(4);
                lst.add(r);

            }

        } catch (SQLException ex) {
            Mensaje.panelSms("Error en listar Antena Busqueda : " + ex);
            Logger.getLogger(AntenaImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lst;
    }

    @Override
    public ArrayList<Object[]> BusAntXnom_torr_serv(String nom, String torr, String serv) {

        ArrayList<Object[]> lst = new ArrayList<>();

        Connection cn = Conexion.abrir();

        String sql;
        if (torr.equals("todos") && serv.equals("todos")) {
            sql = "SELECT a.idAntena,a.nombreAntena,t.nombreTorre,s.nombreServidor "
                    + "FROM antena a,torre t,servidor s WHERE a.idTorre=t.idTorre "
                    + "AND a.idServidor=s.idServidor AND a.nombreAntena LIKE '" + nom + "%' AND a.idTipo=1";
        } else if (torr.equals("todos")) {
            sql = "SELECT a.idAntena,a.nombreAntena,t.nombreTorre,s.nombreServidor "
                    + "FROM antena a,torre t,servidor s WHERE a.idTorre=t.idTorre "
                    + "AND a.idServidor=s.idServidor AND a.nombreAntena LIKE '" + nom + "%'"
                    + " AND s.nombreServidor='" + serv + "' AND a.idTipo=1";
        } else if (serv.equals("todos")) {
            sql = "SELECT a.idAntena,a.nombreAntena,t.nombreTorre,s.nombreServidor "
                    + "FROM antena a,torre t,servidor s WHERE a.idTorre=t.idTorre "
                    + "AND a.idServidor=s.idServidor AND a.nombreAntena LIKE '" + nom + "%'"
                    + " AND t.nombreTorre='" + torr + "' AND a.idTipo=1";

        } else {
            sql = "SELECT a.idAntena,a.nombreAntena,t.nombreTorre,s.nombreServidor "
                    + "FROM antena a,torre t,servidor s WHERE a.idTorre=t.idTorre "
                    + "AND a.idServidor=s.idServidor AND a.nombreAntena LIKE '" + nom + "%'"
                    + " AND t.nombreTorre='" + torr + "' AND s.nombreServidor='" + serv + "' AND a.idTipo=1";

        }

        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            Object[] r;
            while (rs.next()) {
                r = new Object[4];
                r[0] = rs.getString(1);
                r[1] = rs.getString(2);
                r[2] = rs.getString(3);
                r[3] = rs.getString(4);
                lst.add(r);

            }

        } catch (SQLException ex) {
            Mensaje.panelSms("Error en listar Antena Busqueda : " + ex);
            Logger.getLogger(AntenaImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lst;
    }

    @Override
    public ArrayList<Object[]> BusAntXnom_torr_serv2(String nom, String consulta) {

        ArrayList<Object[]> lst = new ArrayList<>();

        Connection cn = Conexion.abrir();

        String sql = "SELECT a.idAntena,a.nombreAntena,t.nombreTorre,s.nombreServidor FROM antena a "
                + "INNER JOIN torre t ON a.idTorre=t.idTorre INNER JOIN servidor s ON a.idServidor=s.idServidor "
                + "AND a.nombreAntena LIKE '" + nom + "%' AND a.idTipo=1 " + consulta;

        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            Object[] r;
            while (rs.next()) {
                r = new Object[4];
                r[0] = rs.getString(1);
                r[1] = rs.getString(2);
                r[2] = rs.getString(3);
                r[3] = rs.getString(4);
                lst.add(r);

            }

        } catch (SQLException ex) {
            Mensaje.panelSms("Error en listar Antena Busqueda : " + ex);
            Logger.getLogger(AntenaImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lst;
    }

    @Override
    public boolean existeAntena(String nom) {
        boolean f = false;
        String sql = "SELECT nombreAntena FROM antena WHERE nombreAntena='" + nom + "'";
        Connection cn = Conexion.abrir();
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                f = true;

            }
        } catch (SQLException ex) {
            Logger.getLogger(AntenaImp.class.getName()).log(Level.SEVERE, null, ex);
        }

        return f;
    }

}
