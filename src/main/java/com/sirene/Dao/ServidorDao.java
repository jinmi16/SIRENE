/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirene.Dao;

import com.sirene.Bean.Servidor;
import java.util.ArrayList;

/**
 *
 * @author Jinmi
 */
public interface ServidorDao {

    void registrarServ(String nom, String ipe, String ips);

    ArrayList<Servidor> listar();

    ArrayList<Servidor> listarNombre();

    void modificarServer(Servidor serv);

    void eliminarRegistro(int id);
}
