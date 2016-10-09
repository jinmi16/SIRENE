/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirene.Bean;

/**
 *
 * @author JINMI
 */
public class Servidor {
    int idServ;
    String nomServ,ipImput,ipOutput;

    public int getIdServ() {
        return idServ;
    }

    public void setIdServ(int idServ) {
        this.idServ = idServ;
    }

    public String getIpImput() {
        return ipImput;
    }

    public void setIpImput(String ipImput) {
        this.ipImput = ipImput;
    }

    public String getIpOutput() {
        return ipOutput;
    }

    public void setIpOutput(String ipOutput) {
        this.ipOutput = ipOutput;
    }

    public String getNomServ() {
        return nomServ;
    }

    public void setNomServ(String nomServ) {
        this.nomServ = nomServ;
    }
    
    
}
