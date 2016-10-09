/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirene.Dao;

import com.sirene.Bean.Usuario;
import java.util.ArrayList;

/**
 *
 * @author Jinmi
 */
public interface UsuarioDao {
    
    Object[] logUser(String usuario, String passw);
    ArrayList<Usuario> listarUsuario();
    void eliminarUser(int id);
    ArrayList<Usuario> listarUsuario_idPers(int idPer);
     boolean registrar(Usuario p);
     boolean getPasw(int idU,String pas);
     void cambiarContraseña(Usuario p);
     boolean eliminar(int p);
     
     
}
