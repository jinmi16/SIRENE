package com.sirene.Coneccion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Jinmi
 */
public class Conexionn {
    private static Connection cn;
    private static String driver = null;
    private static String url = null;
    private static String usuario = null;
    private static String contrasena = null;

    public Conexionn() {
        driver = "com.mysql.jdbc.Driver";
        url = "jdbc:mysql://127.0.0.1/bd_arapa";
        usuario = "root";
        contrasena = "admin";

    }

    public static Connection createConnection() throws SQLException {
        try {
            Class.forName(driver);
            cn = DriverManager.getConnection(url, usuario, contrasena);
            System.out.println("EXITO__  SE CONECTO CON ARAPA");
        } catch (ClassNotFoundException | SQLException objException) {
            System.out.println("ERROR DE CONEXION  : " + objException.getMessage());
        }
        return cn;
    }

    public static Connection getConnection() {
        try {
            if (cn == null || cn.isClosed()) {
                createConnection();
            }
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return cn;
    }
}
