/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirene.Bean;

/**
 *
 * @author JINMI
 */
public class Cliente {
    private int idCliente,idEstado,idAntena;
    private String ultCorteEjecutado,apellido,nombre,direccion,correo,condicioAntena,ip,frecuenci,anchoBanda,mac,observacion,fechInicio,marca,fechVencimiento,fechCorte,dni;
  private String F_PagoSaldo;

    public String getF_PagoSaldo() {
        return F_PagoSaldo;
    }

    public void setF_PagoSaldo(String F_PagoSaldo) {
        this.F_PagoSaldo = F_PagoSaldo;
    }

    public float getSaldo() {
        return saldo;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }
    private float tarifa,saldo=0;
    public String getUltCorteEjecutado() {
        return ultCorteEjecutado;
    }

    public void setUltCorteEjecutado(String ultCorteEjecutado) {
        this.ultCorteEjecutado = ultCorteEjecutado;
    }
    
  

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getAnchoBanda() {
        return anchoBanda;
    }

    public void setAnchoBanda(String anchoBanda) {
        this.anchoBanda = anchoBanda;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCondicioAntena() {
        return condicioAntena;
    }

    public void setCondicioAntena(String condicioAntena) {
        this.condicioAntena = condicioAntena;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getFechCorte() {
        return fechCorte;
    }

    public void setFechCorte(String fechCorte) {
        this.fechCorte = fechCorte;
    }

    public String getFechInicio() {
        return fechInicio;
    }

    public void setFechInicio(String fechInicio) {
        this.fechInicio = fechInicio;
    }

    public String getFechVencimiento() {
        return fechVencimiento;
    }

    public void setFechVencimiento(String fechVencimiento) {
        this.fechVencimiento = fechVencimiento;
    }

    public String getFrecuenci() {
        return frecuenci;
    }

    public void setFrecuenci(String frecuenci) {
        this.frecuenci = frecuenci;
    }

    public int getIdAntena() {
        return idAntena;
    }

    public void setIdAntena(int idAntena) {
        this.idAntena = idAntena;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
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

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public float getTarifa() {
        return tarifa;
    }

    public void setTarifa(float tarifa) {
        this.tarifa = tarifa;
    }
    
    
}
