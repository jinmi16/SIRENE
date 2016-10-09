/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirene.Dao;

import com.sirene.Bean.Torre;
import java.util.ArrayList;

/**
 *
 * @author Jinmi
 */
public interface TorreDao {
  
    void registrar(Torre t);
    ArrayList<Torre> listar();
    ArrayList<Torre> listarNobre();
    void modificar(Torre t);
    void eliminar(int id);
    ArrayList<Torre> buscar(String nom);
    
}
