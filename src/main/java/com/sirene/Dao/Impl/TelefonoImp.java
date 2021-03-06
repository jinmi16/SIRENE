/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirene.Dao.Impl;

import com.sirene.Bean.Telefono;
import com.sirene.Coneccion.Conexion;
import com.sirene.Dao.TelefonoDao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;

/**
 *
 * @author JINMI
 */
public class TelefonoImp implements TelefonoDao{
    
    @Override
    public  ArrayList<Telefono> listaTelefono(DefaultListModel modLista) {
        ArrayList<Telefono> lst = new ArrayList<>();
        Telefono t;
        for (int k = 0; k < modLista.getSize(); k++) {
            t = new Telefono();
            
            t.setTelefono(modLista.getElementAt(k).toString());
            lst.add(t);
            
            
        }
        return lst;
    }
     @Override
    public  void registrarTel(Telefono t, int idcli) {
        String sql = "INSERT INTO telefono (telefono, idcliente) VALUES (?, ?)";
        Connection cn = Conexion.abrir();
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, t.getTelefono());
            ps.setInt(2, idcli);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Mensaje.panelSms("ERROR metodo registrarTel" + ex);
            Logger.getLogger(TelefonoImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
 @Override
    public  void registrarTel2(Telefono t, int idcli, Connection cn) throws SQLException {
        String sql = "INSERT INTO telefono (telefono, idcliente) VALUES (?, ?)";
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setString(1, t.getTelefono());
        ps.setInt(2, idcli);
        ps.executeUpdate();
    }
     @Override
    public  void registrarListTelefonos(ArrayList<Telefono> lst, int idcli) {
        for (Telefono t : lst) {
            registrarTel(t, idcli);
        }
    }
     @Override
    public  void registrarListTelefonos2(ArrayList<Telefono> lst, int idcli, Connection cn) throws SQLException {
        for (Telefono t : lst) {
            registrarTel2(t, idcli, cn);
        }
    }
 @Override
    public  ArrayList<Telefono> listarTelfCliente(int idcli) {
        ArrayList<Telefono> lst = new ArrayList<>();
        String sql = "SELECT telefono FROM telefono WHERE idcliente=?";
        Connection cn = Conexion.abrir();
        PreparedStatement ps;
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(1, idcli);
            ResultSet rs = ps.executeQuery();
            Telefono t;
            while(rs.next()){
            t=new Telefono();
            t.setTelefono(rs.getString(1));
            lst.add(t);
            }
        } catch (SQLException ex) {
            Mensaje.panelSms("error listar telefonos x cliente"+ex);
            //Logger.getLogger(DaoTelefono.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        return lst;
    }
     @Override
    public  void modificarTelf(int id,String t,String newT){
    String sql="UPDATE telefono SET `telefono` = '"+newT+"' WHERE CONVERT(telefono USING utf8) = '"+t+"' AND idcliente = "+id+" ";
     Connection cn = Conexion.abrir();
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.executeUpdate();
            
        } catch (SQLException ex) {
            Mensaje.panelSms("error modificar telefono"+ex);
            
        }
    }
     @Override
    public  void eliminarTelf(int id,String t){
    String sql="DELETE FROM telefono WHERE CONVERT(telefono USING utf8) = '"+t+"' AND idcliente = "+id+" ";
     Connection cn = Conexion.abrir();
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.executeUpdate();
            
        } catch (SQLException ex) {
            Mensaje.panelSms("error Eliminar telefono"+ex);
            
        }
    }
}
