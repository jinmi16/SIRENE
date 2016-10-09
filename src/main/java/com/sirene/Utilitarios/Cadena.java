/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirene.Utilitarios;

/**
 *
 * @author JINMI
 */
public class Cadena {

    public static String eliminarUltCaracter(String cad) {
        String cadena= cad.substring(0, cad.length() - 1);;
        
        return cadena;
    }

}
