/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package old;

import com.sirene.Dao.Impl.*;
import com.sirene.Bean.BeanFecha;
import com.sirene.Coneccion.Conexion;
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
public class DaoFecha_OLD {
    
    public static ArrayList<BeanFecha> listarFecha(){
    ArrayList<BeanFecha> lst=new ArrayList<>();
    String sql="SELECT * FROM fecha";
    Connection cn=Conexion.abrir();
        try {
            PreparedStatement ps= cn.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            BeanFecha f;
            while(rs.next()){
            f=new BeanFecha();
           f.setIdFecha(rs.getInt(1));
           f.setFecha(rs.getDate(2));
           lst.add(f);
            
            
            }
        } catch (SQLException ex) {
            Mensaje.panelSms("error en fechas :"+ex);
            Logger.getLogger(DaoFecha_OLD.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
    return lst;
    }
    
    public static void registrar(String fecha){
        String sql="INSERT INTO fecha ( fecha) VALUES ( ?)";
     Connection cn=Conexion.abrir();
        try {
            PreparedStatement ps= cn.prepareStatement(sql);
            ps.setString(1, fecha);
            
            ps.executeUpdate();
        } catch (SQLException ex) {
            Mensaje.panelSms("error en registrar :"+ex);
            Logger.getLogger(DaoFecha_OLD.class.getName()).log(Level.SEVERE, null, ex);
        }
     
     
     
    }
    
}
