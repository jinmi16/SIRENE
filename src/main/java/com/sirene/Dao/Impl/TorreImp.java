/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirene.Dao.Impl;

import com.sirene.Bean.Torre;
import com.sirene.Coneccion.Conexion;
import com.sirene.Dao.TorreDao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JINMI
 */
public class TorreImp implements TorreDao{

    @Override
    public  void registrar(Torre t) {
        System.out.println("R1");
        String sql = "INSERT INTO torre (nombreTorre, direccion, dueñoLocal, telefono, pago) "
                + "VALUES (?, ?, ?, ?,?)";
        System.out.println("R1");
        Connection cn = Conexion.abrir();
        System.out.println("R1");
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            System.out.println("R1");
            ps.setString(1, t.getNombre());
            ps.setString(2, t.getUbicacion());
            System.out.println("R1");
            ps.setString(3, t.getDueñoLocal());
            System.out.println("R1");
            ps.setString(4, t.getTelefono());
            System.out.println("R1");
            ps.setFloat(5, t.getPago());
            System.out.println("R1");
            ps.executeUpdate();
            System.out.println("R1");
            ps.close();
            System.out.println("R1");
            cn.close();
            System.out.println("R1");

        } catch (SQLException ex) {
            Mensaje.panelSms("error al REGISTRAR TORRE :" + ex);
            Logger.getLogger(TorreImp.class.getName()).log(Level.SEVERE, null, ex);
        }


    }
@Override
    public  ArrayList<Torre> listar() {
        ArrayList<Torre> lista = new ArrayList<>();
        String sql = "SELECT * FROM torre";
        Connection cn = Conexion.abrir();
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            Torre t;
            while (rs.next()) {
                t = new Torre();
                t.setIdTorre(rs.getInt(1));
                t.setNombre(rs.getString(2));
                t.setUbicacion(rs.getString(3));
                t.setDueñoLocal(rs.getString(4));
                t.setTelefono(rs.getString(5));
                t.setPago(rs.getFloat(6));
                lista.add(t);
            }

        } catch (SQLException ex) {
            Mensaje.panelSms("error al LISTAR TORRE :" + ex);
            Logger.getLogger(TorreImp.class.getName()).log(Level.SEVERE, null, ex);
        }


        return lista;
    }
@Override
    public  ArrayList<Torre> listarNobre() {
        ArrayList<Torre> lista = new ArrayList<>();
        String sql = "SELECT idTorre,nombreTorre FROM torre";
        Connection cn = Conexion.abrir();
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            Torre t;
            while (rs.next()) {
                t = new Torre();
                t.setIdTorre(rs.getInt(1));
                t.setNombre(rs.getString(2));

                lista.add(t);
            }
            rs.close();
            ps.close();
            cn.close();
        } catch (SQLException ex) {
            Mensaje.panelSms("error al LISTAR NOMBRE TORRE :" + ex);
            Logger.getLogger(TorreImp.class.getName()).log(Level.SEVERE, null, ex);
        }



        return lista;
    }
@Override
    public  void modificar(Torre t) {

        String sql = "UPDATE TORRE SET nombreTorre =?, direccion = ?, dueñoLocal = ?, telefono = ?, pago = ? "
                + "WHERE idTorre = ?";
        Connection cn = Conexion.abrir();
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, t.getNombre());
            ps.setString(2, t.getUbicacion());
            ps.setString(3, t.getDueñoLocal());
            ps.setString(4, t.getTelefono());
            ps.setFloat(5, t.getPago());
            ps.setInt(6, t.getIdTorre());
            ps.executeUpdate();
            ps.close();
            cn.close();

        } catch (SQLException ex) {
            Mensaje.panelSms("error al MODICAR TORRE :" + ex);
            Logger.getLogger(TorreImp.class.getName()).log(Level.SEVERE, null, ex);
        }


    }
@Override
    public  void eliminar(int id) {
        String sql = "DELETE FROM torre WHERE idTorre=? ";
        Connection cn = Conexion.abrir();
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
            cn.close();

        } catch (SQLException ex) {
            Mensaje.panelSms("error al ELIMINAR TORRE :" + ex);
            Logger.getLogger(TorreImp.class.getName()).log(Level.SEVERE, null, ex);
        }



    }
@Override
    public  ArrayList<Torre> buscar(String nom) {
        ArrayList<Torre> lista = new ArrayList<>();
        String sql = "SELECT * FROM `torre` WHERE nombreTorre like ?";
        Connection cn = Conexion.abrir();
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, nom + '%');
            ResultSet rs = ps.executeQuery();

            Torre t;
            while (rs.next()) {
                t = new Torre();
                t.setIdTorre(rs.getInt(1));
                t.setNombre(rs.getString(2));
                t.setUbicacion(rs.getString(3));
                t.setDueñoLocal(rs.getString(4));
                t.setTelefono(rs.getString(5));
                t.setPago(rs.getFloat(6));
                lista.add(t);

            }






        } catch (SQLException ex) {
            Mensaje.panelSms("error al BUSCAR TORRE :" + ex);
            Logger.getLogger(TorreImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }
}
