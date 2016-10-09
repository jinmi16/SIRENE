/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirene.Dao;

import com.sirene.Bean.Cliente;
import com.sirene.Bean.EstadoCliente;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Jinmi
 */
public interface ClienteDao {

    ArrayList<EstadoCliente> listarEstadoCli();

    void regresarAcorteSE(int id);

    int getIdAntena(String nom);

    void congelarCliente(int idCli, String fecha);

    int getIdEstado(String nom);

    void registrarCliente(Cliente c);

    void registrarCliente2(Cliente c, Connection cn);

    ArrayList<Object[]> listarCliente();

    ArrayList<Object[]> busClienteApell_Nom_Dni(String a, String n, String d, String marca);

    ArrayList<Cliente> BuscarX(int x, String cadena, boolean f);

    ArrayList<Cliente> BuscarClientesActivos(int x, String cadena, int estado);

    ArrayList<Object[]> BuscarCliCortSE(int x, String cadena);

    ArrayList<Object[]> BuscarCliCeRa(int x, String cadena);

    ArrayList<Object[]> BuscarCliRe(int x, String cadena);

    ArrayList<Object[]> listarCliCortSE();

    ArrayList<Object[]> listarCeRa();

    ArrayList<Object[]> listarClienteRetirado();

    ArrayList<Object[]> listarClienteEstado(int es);

    ArrayList<Object[]> buscarCliente2(int es, String cadena, int x, String orden);

    ArrayList<Object[]> buscarClienteEstado(int es, String cadena, int x);

    Object[] llenarTodosLosCampos(int id);

    ArrayList<Object[]> buscarCliente(int x, String nomApe, String consulta);

    boolean cortarServicio(int id);

    boolean desabilitarCliente(int id);

    boolean habilitarCliente(int id, int e);

    void modificarCliente(Cliente c);

    int getUltimoIdCliente();

    void eliminarCliente(int id);

    Object[] buscarCliXid(int id);

    Object[] buscarCliCongeladoXid(int id);

    void DescongelarCliente(Cliente c);

    void activarEstado(Cliente c, Connection cn) throws SQLException;

    ArrayList<String> listarMarcas();

    ArrayList<String> listarAntenaBaseCli();

    ArrayList<String> listarAntenaBaseCliXtorre(String torre);

}
