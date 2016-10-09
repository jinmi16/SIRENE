/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirene.Dao;

import com.sirene.Bean.Telefono;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.DefaultListModel;

/**
 *
 * @author Jinmi
 */
public interface TelefonoDao {
    
     ArrayList<Telefono> listaTelefono(DefaultListModel modLista);
    void registrarTel(Telefono t, int idcli);
    void registrarTel2(Telefono t, int idcli, Connection cn) throws SQLException ;
     void registrarListTelefonos(ArrayList<Telefono> lst, int idcli);
     void registrarListTelefonos2(ArrayList<Telefono> lst, int idcli, Connection cn) throws SQLException;
     ArrayList<Telefono> listarTelfCliente(int idcli);
    void modificarTelf(int id,String t,String newT);
    void eliminarTelf(int id,String t);
    
}
