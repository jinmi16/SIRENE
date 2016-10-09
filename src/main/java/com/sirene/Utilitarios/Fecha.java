package com.sirene.Utilitarios;

import com.sirene.Coneccion.Conexion;
import com.sirene.Dao.Impl.Mensaje;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Fecha {

    public static java.sql.Date deStrigaDate(String fecha) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        java.util.Date d1 = new java.util.Date();// con esto obtengo la fecha del sistemas

        try {
            d1 = sdf.parse(fecha);// d1 ya tiene la fecha del string

        } catch (ParseException ex) {
            Logger.getLogger(Fecha.class.getName()).log(Level.SEVERE, null, ex);
        }
        java.sql.Date d2 = new java.sql.Date(d1.getTime());
        return d2;

    }

    public static java.sql.Date deStrigaDate2(String fecha) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        java.util.Date d1 = new java.util.Date();// con esto obtengo la fecha del sistemas

        try {
            d1 = sdf.parse(fecha);// d1 ya tiene la fecha del string

        } catch (ParseException ex) {
            Logger.getLogger(Fecha.class.getName()).log(Level.SEVERE, null, ex);
        }
        java.sql.Date d2 = new java.sql.Date(d1.getTime());
        return d2;

    }

    public static String cabioFormato(String fe) {

        String f = convertToString2(deStrigaDate2(fe));

        return f;
    }

    public static String hoy() {
        String fecha;
        Date ahora = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
        fecha = formateador.format(ahora);
        return fecha;
    }

    public static Date ahora() {
        Date hoy = new Date();
        String fecha;
        Date ahora = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
        fecha = formateador.format(ahora);
        hoy = deStrigaDate2(fecha);
        return hoy;
    }
    public static Date ahora2() {
        Date hoy = new Date();
        String fecha;
        Date ahora = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");
        fecha = formateador.format(ahora);
        hoy = deStrigaDate2(fecha);
        return hoy;
    }

    public static boolean confirmarActualizacion(String fa, String ufa) {
        Boolean f = false;
        if (fa.equals(ufa)) {
            f = true;
        }
        return f;
    }

    public static String convertToString(Date date) {

        String datestr = null;
        if (date != null) {
            DateFormat df;
            df = new SimpleDateFormat("dd-MM-yyyy");
            datestr = df.format(date);

        }

        return datestr;
    }

    public static String convertToString2(Date date) {
        String datestr = null;
        if (date != null) {
            DateFormat df;

            df = new SimpleDateFormat("yyyy-MM-dd");
            datestr = df.format(date);

        }

        return datestr;
    }

    public static Date SumarDias(Date fecha, int n) {
        Calendar calen = Calendar.getInstance();
        calen.setTime(fecha);
        calen.add(Calendar.DAY_OF_YEAR, n);
        return calen.getTime();

    }

    public static int diasXsumar(Date fech) {
        int s = 0;
        int m = fech.getMonth();
        switch (m) {
            case (3): {
                s = 30;
                break;
            }

            case (5): {
                s = 30;
                break;
            }

            case (8): {
                s = 30;
                break;
            }

            case (10): {
                s = 30;
                break;
            }

            default:
                s = 31;
        }

        return s;
    }

    public static void actualizarEstadoClienteFp(String hoy) {

        String sql1 = "UPDATE cliente SET idEstado =2 WHERE f_Vencimiento <='" + hoy + "' AND idEstado=1";

        Connection cn = Conexion.abrir();
        try {
            PreparedStatement ps = cn.prepareStatement(sql1);
            ps.executeUpdate();

        } catch (SQLException ex) {
            Mensaje.panelSms("ERROR en actualizacion de estado del cliente :" + ex);
        }

    }

    public static void actualizarEstadoClienteFc(String hoy) {

        String sql = "UPDATE cliente SET idEstado =3 WHERE f_Corte <='" + hoy + "' AND idEstado=2";
        Connection cn = Conexion.abrir();
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.executeUpdate();

        } catch (SQLException ex) {
            Mensaje.panelSms("ERROR en actualizacion de estado del cliente :" + ex);
        }

    }

    public static void actualizarEstadoClienteRa() {
        Date d = SumarDias(ahora(), -14);
        String hoy = convertToString2(d);

        String sql = "UPDATE cliente SET idEstado =5 WHERE f_Corte <='" + hoy + "' AND  idEstado=4   AND condicionAntena='Alquilada' ";
        Connection cn = Conexion.abrir();
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.executeUpdate();

        } catch (SQLException ex) {
            Mensaje.panelSms("ERROR en actualizacion de estado del cliente :" + ex);
        }

    }

    public static long diferenciaDias(Date hoy, Date pasado) {
        long d = 0;
        d = ((hoy.getTime() - pasado.getTime()) / (24 * 60 * 60 * 1000)) + 1;

        return d;
    }

    public static synchronized int diferenciasDeFechas(Date fechaInicial, Date fechaFinal) {

        DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
        String fechaInicioString = df.format(fechaInicial);
        try {
            fechaInicial = df.parse(fechaInicioString);
        } catch (ParseException ex) {
            System.out.println("error"+ex);
        }

        String fechaFinalString = df.format(fechaFinal);
        try {
            fechaFinal = df.parse(fechaFinalString);
        } catch (ParseException ex) {
             System.out.println("error 2 "+ex);
        }

        long fechaInicialMs = fechaInicial.getTime();
        long fechaFinalMs = fechaFinal.getTime();
        long diferencia = fechaFinalMs - fechaInicialMs;
        double dias = Math.floor(diferencia / (1000 * 60 * 60 * 24));
        return ((int) dias);
    }

}
