package com.sirene.Controller;

import com.sirene.Bean.Cliente;
import com.sirene.Bean.Pago;
import com.sirene.Bean.Telefono;
import com.sirene.Coneccion.Conexion;
import com.sirene.Dao.Impl.DaoFactory;
//import com.sirene.Dao.Impl.DaoCliente;
//import com.sirene.Dao.Impl.DaoPago;
//import com.sirene.Dao.Impl.DaoTelefono;
import com.sirene.Dao.Impl.Mensaje;
import static com.sirene.Utilitarios.Constantes.CLIENTE;
import static com.sirene.Utilitarios.Constantes.PAGO;
import static com.sirene.Utilitarios.Constantes.TELEFONO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JINMI
 */
public class Registro {

    public Registro() {
    }
    

    public static void RegistrarCliente_Telefonos(Cliente c, ArrayList<Telefono> lst) {
        Connection cn = Conexion.abrir();

        try {
            cn.setAutoCommit(false);
            //DaoCliente.registrarCliente2(c, cn);
            DaoFactory.getClienteDaoStatic(CLIENTE).registrarCliente2(c, cn);

            int idcli = DaoFactory.getClienteDaoStatic(CLIENTE).getUltimoIdCliente() + 1;
            //Mensaje.panelSms("ultimo idCliente = "+idcli);
            //DaoTelefono.registrarListTelefonos2(lst, idcli, cn);
            DaoFactory.getTelefonoDaoStatic(TELEFONO).registrarListTelefonos2(lst, idcli, cn);

            cn.commit();
            cn.close();

            //Mensaje.panelSms("ultimo idCliente = "+idcli);
        } catch (SQLException ex) {
            try {
                cn.rollback();

            } catch (SQLException ex1) {
                Mensaje.panelSms("Error al registar transaccion = " + ex1);
                Logger.getLogger(Registro.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Mensaje.panelSms("Error al registar  = " + ex);
            Logger.getLogger(Registro.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void RegistrarPago(Pago p,Cliente c) {
        Connection cn = Conexion.abrir();

        try {
            cn.setAutoCommit(false);

           DaoFactory.getPagoDaoStatic(PAGO).RegistrarPago(p, cn);

            DaoFactory.getClienteDaoStatic(CLIENTE).activarEstado(c, cn);
            cn.commit();
            cn.close();

        } catch (SQLException ex) {
            try {
                cn.rollback();

            } catch (SQLException ex1) {
                Mensaje.panelSms("Error al registar transaccion = " + ex1);
                Logger.getLogger(Registro.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Mensaje.panelSms("Error al registar  = " + ex);
            //Logger.getLogger(Registro.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
