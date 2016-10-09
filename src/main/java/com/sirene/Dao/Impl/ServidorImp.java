/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirene.Dao.Impl;

import com.sirene.Bean.Servidor;
import com.sirene.Coneccion.Conexion;
import com.sirene.Dao.ServidorDao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author JINMI
 */
public class ServidorImp implements ServidorDao {

    //@SuppressWarnings("ConvertToTryWithResources")
    @Override
    public void registrarServ(String nom, String ipe, String ips) {

        String sql = "INSERT INTO servidor (nombreServidor, ipEntrada, ipSalida) "
                + "VALUES (?, ?, ?)";
        Connection cn = Conexion.abrir();
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, nom);
            ps.setString(2, ipe);
            ps.setString(3, ips);
            ps.executeUpdate();
            ps.close();
            cn.close();

        } catch (Exception e) {
            Mensaje.panelSms("error al registar Servidor :" + e);
        }

    }

    @Override
    public ArrayList<Servidor> listar() {
        ArrayList<Servidor> listServ = new ArrayList<>();
        String sql = "SELECT * FROM servidor";
        Connection cn = Conexion.abrir();
        try {
            try (PreparedStatement ps = cn.prepareStatement(sql);
                    ResultSet rs = ps.executeQuery()) {
                Servidor sv;
                while (rs.next()) {
                    sv = new Servidor();
                    sv.setIdServ(rs.getInt(1));
                    sv.setNomServ(rs.getString(2));
                    sv.setIpImput(rs.getString(3));
                    sv.setIpOutput(rs.getString(4));
                    listServ.add(sv);
                }
            }
            cn.close();

        } catch (Exception e) {
            Mensaje.panelSms("error al listar Servidor :" + e);
        }

        return listServ;

    }

    @Override
    public ArrayList<Servidor> listarNombre() {
        ArrayList<Servidor> listServ = new ArrayList<>();
        String sql = "SELECT idServidor,nombreServidor FROM servidor";
        Connection cn = Conexion.abrir();
        try {
            try (PreparedStatement ps = cn.prepareStatement(sql);
                    ResultSet rs = ps.executeQuery()) {
                Servidor sv;
                while (rs.next()) {
                    sv = new Servidor();
                    sv.setIdServ(rs.getInt(1));
                    sv.setNomServ(rs.getString(2));

                    listServ.add(sv);
                }
                rs.close();
                ps.close();
            }

            cn.close();

        } catch (Exception e) {
            Mensaje.panelSms("error al listar nombres de  Servidor  :" + e);
        }

        return listServ;

    }

    @Override
    public void modificarServer(Servidor serv) {

        String sql = "UPDATE servidor SET nombreServidor =?, `ipEntrada` = ?, ipSalida= ? "
                + "WHERE idServidor = ? ";

        Connection cn = Conexion.abrir();
        try {
            try (PreparedStatement ps = cn.prepareStatement(sql)) {
                ps.setString(1, serv.getNomServ());
                ps.setString(2, serv.getIpImput());
                ps.setString(3, serv.getIpOutput());
                ps.setInt(4, serv.getIdServ());
                ps.executeUpdate();
                ps.close();
            }
            cn.close();

        } catch (Exception e) {
            Mensaje.panelSms("ERROR EN ACTUALIZACION DE SERVIDOR :" + e);
        }

    }

    @Override
    public void eliminarRegistro(int id) {
        String sqlD = "DELETE FROM servidor WHERE idServidor=?";
        Connection cn = Conexion.abrir();
        try {
            try (PreparedStatement ps = cn.prepareStatement(sqlD)) {
                ps.setInt(1, id);
                ps.execute();
            }
            cn.close();

        } catch (Exception e) {
            Mensaje.panelSms("ERROR AL ELIMINAR REGISTRO :" + e);
            System.out.println("error : " + e);
        }

    }
}
