/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirene.Dao.Impl;

import com.sirene.Bean.Usuario;
import com.sirene.Coneccion.Conexion;
import com.sirene.Dao.UsuarioDao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author JINMI
 */
public class UsuarioImp implements UsuarioDao {

    @Override
    public Object[] logUser(String usuario, String passw) {
        Object[] user = new Object[2];

        String sql = "select u.idUsuario,u.user,u.password,u.idPersonal,u.idTipoUser,p.nombre "
                + "FROM usuario u,personal p "
                + "WHERE u.user =? and u.password=? and u.idPersonal=p.idPersonal";
        Connection cn = Conexion.abrir();

        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, usuario);
            ps.setString(2, passw);
            ResultSet rs = ps.executeQuery();
            Usuario us;
            if (rs.next()) {
                us = new Usuario();
                us.setIdUser(rs.getInt(1));
                us.setUser(rs.getString(2));
                us.setPassword(rs.getString(3));
                us.setIdPersonal(rs.getInt(4));
                us.setIdTipoUser(rs.getInt(5));
                user[0] = rs.getString(6);
                user[1] = us;

            } else {
                JOptionPane.showMessageDialog(null, "User y/o password incorrectos \n    Vuelva a intentarlo");
            }
            cn.close();
            ps.close();
            rs.close();

        } catch (Exception e) {
            System.out.println("error en metodo logUser: " + e);
        }

        return user;
    }

    @Override
    public ArrayList<Usuario> listarUsuario() {
        ArrayList<Usuario> lst = new ArrayList<>();
        String sql = "SELECT user,t.tipoUser FROM usuario u , tipoUsuario t WHERE u.idTipoUser=t.idTipoUser";
        Connection cn = Conexion.abrir();
        PreparedStatement ps;
        try {
            ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            Usuario u;
            while (rs.next()) {
                u = new Usuario();
                u.setUser(rs.getString(1));
                u.setTipoUser(rs.getString(2));
                lst.add(u);

            }
        } catch (SQLException ex) {
            Mensaje.panelSms("error al listar Usuario : " + ex);

        }

        return lst;
    }

    @Override
    public void eliminarUser(int id) {
        String sql = "DELETE FROM usuario WHERE idUsuario=" + id + "";
        Connection cn = Conexion.abrir();
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.executeUpdate();
            ps.close();
            cn.close();
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioImp.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public ArrayList<Usuario> listarUsuario_idPers(int idPer) {
        ArrayList<Usuario> lst = new ArrayList<>();
        String sql = "SELECT u.idUsuario,u.user,t.tipoUser FROM usuario u , tipoUsuario t "
                + "WHERE u.idTipoUser=t.idTipoUser AND u.idPersonal=" + idPer + "";
        Connection cn = Conexion.abrir();
        PreparedStatement ps;
        try {
            ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            Usuario u;
            while (rs.next()) {
                u = new Usuario();
                u.setIdUser(rs.getInt(1));
                u.setUser(rs.getString(2));
                u.setTipoUser(rs.getString(3));
                lst.add(u);

            }
        } catch (SQLException ex) {
            Mensaje.panelSms("error al listar Usuario : " + ex);

        }

        return lst;
    }

    @Override
    public boolean registrar(Usuario p) {
        boolean f = false;
        String sql = "INSERT INTO usuario (user,password,idPersonal,idTipoUser) VALUES (?,?,?,?)";
        Connection cn = Conexion.abrir();

        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, p.getUser());
            ps.setString(2, p.getPassword());
            ps.setInt(3, p.getIdPersonal());
            ps.setInt(4, p.getIdTipoUser());
            ps.executeUpdate();
            ps.close();
            cn.close();
            f = true;
        } catch (SQLException ex) {
            Mensaje.panelSms("error al Registrar usuario : " + ex);

        }
        return f;
    }

    @Override
    public boolean getPasw(int idU, String pas) {
        boolean pasw = false;
        String sql = "SELECT password FROM usuario WHERE idUsuario=" + idU + " AND password='" + pas + "'";
        Connection cn = Conexion.abrir();
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                pasw = true;
            }

        } catch (SQLException ex) {
            Mensaje.panelSms("Error :" + ex);
        }
        return pasw;
    }

    @Override
    public void cambiarContraseña(Usuario p) {

        String sql = "UPDATE usuario SET password = ? "
                + "WHERE idUsuario = ? ";
        Connection cn = Conexion.abrir();

        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, p.getPassword());
            ps.setInt(2, p.getIdUser());
            ps.executeUpdate();
            ps.close();
            cn.close();

        } catch (SQLException ex) {
            Mensaje.panelSms("error al cambiar contraseña : " + ex);

        }

    }

    @Override
    public boolean eliminar(int p) {
        boolean f = false;
        String sql = "DELETE FROM usuario WHERE idUsuario=?";
        Connection cn = Conexion.abrir();

        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setInt(1, p);
            ps.executeUpdate();
            ps.close();
            cn.close();
            f = true;
        } catch (SQLException ex) {
            Mensaje.panelSms("error al eliminar usuario: " + ex);

        }
        return f;
    }

}
