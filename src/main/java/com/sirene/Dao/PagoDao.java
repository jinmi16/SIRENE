/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirene.Dao;

import com.sirene.Bean.Pago;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Jinmi
 */
public interface PagoDao {
    
    void RegistrarPago(Pago p, Connection cn) throws SQLException;
    void RegistrarPagoAcuenta(Pago p, Connection cn) throws SQLException;
     ArrayList<Object[]> ConsultarPago(String consulta, String consulta2) ;
    boolean AnularPago(Pago p);
    ArrayList<Object[]> clientesXactivar();
    void activarCliente(int id);
    float pagoTotalFecha(String fecha);
    float pagoTotalFechaRango(String fechaI, String fechaF);
    float pagoTotalFechaMes(int mes, int año);
    
    
    
    
    
    
    
    
    
    
    
    
}
