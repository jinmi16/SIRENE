/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sirene.Utilitarios;

import javax.swing.JOptionPane;

/**
 *
 * @author JINMI
 */
public class pruevas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
     /*   String A="HOLA";
        String B="1522";
        String C="1522.0.2";
        boolean f = false;
        try {
            Float.parseFloat(C);
            f = true;
        } catch (NumberFormatException exc) {
            f = false;
            System.out.println("no es numero valido : " + exc);
            //(NumberFormatException exc -Exception e   
        }
       if(f){
           JOptionPane.showMessageDialog(null,"es numero");
       }else{
       JOptionPane.showMessageDialog(null," NO es numero");
       }
      */
        System.out.println(Fecha.hoy());
    }
        
      public   boolean esNumero(String cadena) {
        boolean f = false;
        try {
            Float.parseFloat(cadena);
            f = true;
        } catch (NumberFormatException exc) {
            f = false;
            System.out.println("no es numero valido : " + exc);
            //(NumberFormatException exc -Exception e   
        }

        return f;   
        
        
        
        // TODO code application logic here
    }
    
}
