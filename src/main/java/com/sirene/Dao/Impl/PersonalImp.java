

package com.sirene.Dao.Impl;

import com.sirene.Bean.Personal;
import com.sirene.Coneccion.Conexion;
import com.sirene.Dao.PersonalDao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class PersonalImp implements PersonalDao{
    @Override
    public  ArrayList<Personal> listarPer(){
    ArrayList<Personal> lst=new ArrayList<>();
    String sql="SELECT * FROM personal";
    Connection cn = Conexion.abrir();
    PreparedStatement ps;
        try {
            ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery();
             
             Personal p;
             while(rs.next()){
             p=new Personal();
             p.setIdPersonal(rs.getInt(1));
              p.setApellido(rs.getString(2));
              p.setNombre(rs.getString(3));
              p.setDni(rs.getString(4));
              lst.add(p);
             
             }
        } catch (SQLException ex) {
            Mensaje.panelSms("error al listar personal : "+ex);
            
        }
           
    
    return lst;
    }
    @Override
    public  boolean registrar(Personal p){
    boolean f=false;
    String sql="INSERT INTO personal (nombre,apellido,dni) VALUES (?,?,?)";
    Connection cn = Conexion.abrir();
 
        try {
          PreparedStatement  ps = cn.prepareStatement(sql);
          ps.setString(1,p.getNombre());
          ps.setString(2,p.getApellido());
          ps.setString(3,p.getDni());
          ps.executeUpdate();
          ps.close();
          cn.close();
            f=true;
        } catch (SQLException ex) {
            Mensaje.panelSms("error al Registrar personal : "+ex);
            
        }
    return f;
    }
    @Override
    public  boolean editar(Personal p){
    boolean f=false;
    String sql="UPDATE personal SET apellido = ?, nombre = ?,dni=? "
            + "WHERE idPersonal = ? ";
    Connection cn = Conexion.abrir();
 
        try {
          PreparedStatement  ps = cn.prepareStatement(sql);
          ps.setString(1,p.getApellido());
          ps.setString(2,p.getNombre());
          ps.setString(3,p.getDni());
          ps.setInt(4,p.getIdPersonal());
          ps.executeUpdate();
          ps.close();
          cn.close();
            f=true;
        } catch (SQLException ex) {
            Mensaje.panelSms("error al Registrar personal : "+ex);
            
        }
    return f;
    }
    @Override
    public  boolean eliminar(int p){
    boolean f=false;
    String sql="DELETE FROM personal WHERE idPersonal=?";
    Connection cn = Conexion.abrir();
 
        try {
          PreparedStatement  ps = cn.prepareStatement(sql);
         ps.setInt(1, p);
          ps.executeUpdate();
          ps.close();
          cn.close();
            f=true;
        } catch (SQLException ex) {
            Mensaje.panelSms("error al Registrar personal : "+ex);
            
        }
    return f;
    }
    
}
