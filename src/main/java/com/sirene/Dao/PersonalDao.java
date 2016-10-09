/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirene.Dao;

import com.sirene.Bean.Personal;
import java.util.ArrayList;

/**
 *
 * @author Jinmi
 */
public interface PersonalDao {

    ArrayList<Personal> listarPer();

    boolean registrar(Personal p);

    boolean editar(Personal p);

    boolean eliminar(int p);
}
