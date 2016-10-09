/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirene.Bean;


public class Torre {
    int idTorre;
    String nombre,ubicacion,dueñoLocal,telefono;
    float pago;

    public String getDueñoLocal() {
        return dueñoLocal;
    }

    public void setDueñoLocal(String dueñoLocal) {
        this.dueñoLocal = dueñoLocal;
    }

    public int getIdTorre() {
        return idTorre;
    }

    public void setIdTorre(int idTorre) {
        this.idTorre = idTorre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getPago() {
        return pago;
    }

    public void setPago(float pago) {
        this.pago = pago;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
    
}
