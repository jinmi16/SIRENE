
package com.sirene.Coneccion;
import java.sql.*;

public  class Conexion {
   
    
   public static Connection abrir(){
     Connection con=null;
     
    String url="jdbc:mysql://127.0.0.1/bd_arapa";
    String usuario="root";
    String Password="admin";
    
        try {
            Class.forName("com.mysql.jdbc.Driver");
            
            con=DriverManager.getConnection(url, usuario, Password);
        System.out.println("CONEXION EXITOSA");
        } catch (Exception ex) {
            System.out.println("Error en la conexion a la Bd:"+ex);
        }
        return  con;
    
    }  
   
   
   
   
}
