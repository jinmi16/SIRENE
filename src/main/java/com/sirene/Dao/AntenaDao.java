package com.sirene.Dao;

import com.sirene.Bean.Antena;
import com.sirene.Bean.tipoAntena;
import java.util.ArrayList;

public interface AntenaDao {
    
    void registrar(Antena a);
    ArrayList<Antena> listar();
    ArrayList<Object[]> listar2();
    ArrayList<Object[]> listarBuquedadNomTipo(String nom, String consulta);
    ArrayList<String> antenaEE(String torre);
    ArrayList<Antena> buscarNombre(String nom, String tipo);
    ArrayList<Antena> listarAntEnlEmisora();
    ArrayList<tipoAntena> listarTipoAntena();
    void modificar(Antena a);
    void eliminar(int id);
    ArrayList<Object[]> listarBusAntena();
    ArrayList<Object[]> BusAntXnom_torr_serv(String nom, String torr, String serv);
    ArrayList<Object[]> BusAntXnom_torr_serv2(String nom, String consulta);
    boolean existeAntena(String nom);
    
}
