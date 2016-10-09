/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirene.Bean;

/**
 *
 * @author JINMI
 */
public class Antena {
    private int idAntena,idAntenaEnlace,idServidor,idTorre,idTipo;
    private String nombre,ip,mac,canal,frecuencia,passwConfig,passwConexion;

    public String getCanal() {
        return canal;
    }

    public void setCanal(String canal) {
        this.canal = canal;
    }

    public String getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(String frecuencia) {
        this.frecuencia = frecuencia;
    }

    public int getIdAntena() {
        return idAntena;
    }

    public void setIdAntena(int idAntena) {
        this.idAntena = idAntena;
    }

    public int getIdAntenaEnlace() {
        return idAntenaEnlace;
    }

    public void setIdAntenaEnlace(int idAntenaEnlace) {
        this.idAntenaEnlace = idAntenaEnlace;
    }

    public int getIdServidor() {
        return idServidor;
    }

    public void setIdServidor(int idServidor) {
        this.idServidor = idServidor;
    }

    public int getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(int idTipo) {
        this.idTipo = idTipo;
    }

    public int getIdTorre() {
        return idTorre;
    }

    public void setIdTorre(int idTorre) {
        this.idTorre = idTorre;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPasswConexion() {
        return passwConexion;
    }

    public void setPasswConexion(String passwConexion) {
        this.passwConexion = passwConexion;
    }

    public String getPasswConfig() {
        return passwConfig;
    }

    public void setPasswConfig(String passwConfig) {
        this.passwConfig = passwConfig;
    }
    
    
}
