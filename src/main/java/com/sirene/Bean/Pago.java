

package com.sirene.Bean;

import java.util.Date;


public class Pago {
    
    String Anulado;

    
    int idPago,idCliente,idPersonal,idEstado;
    Date fecha;
    float monto;
public String getAnulado() {
        return Anulado;
    }

    public void setAnulado(String Anulado) {
        this.Anulado = Anulado;
    }
    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }
    String Periodo,fech;

    public int getIdPago() {
        return idPago;
    }

    public String getFech() {
        return fech;
    }

    public void setFech(String fech) {
        this.fech = fech;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdPersonal() {
        return idPersonal;
    }

    public void setIdPersonal(int idPersonal) {
        this.idPersonal = idPersonal;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public float getMonto() {
        return monto;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }

    public String getPeriodo() {
        return Periodo;
    }

    public void setPeriodo(String Periodo) {
        this.Periodo = Periodo;
    }
    
}
