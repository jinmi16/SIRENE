/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirene.GUI;

import com.sirene.Bean.Cliente;
import com.sirene.Bean.Pago;
import com.sirene.Bean.Usuario;
import com.sirene.Controller.Registro;
import com.sirene.Dao.ClienteDao;
import com.sirene.Dao.Impl.DaoFactory;
//import com.sirene.Dao.Impl.DaoCliente;
import com.sirene.Dao.Impl.Mensaje;
import com.sirene.Utilitarios.Cadena;
import static com.sirene.Utilitarios.Constantes.CLIENTE;
import com.sirene.Utilitarios.Fecha;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Ventas extends javax.swing.JInternalFrame {

    Usuario usuario;
    public static int idcliente = -1, idEstado = -1;

    boolean flag = true;
    public static Object[] a;
    public final ClienteDao clienteDao;

    public Ventas() {
        initComponents();
        DaoFactory factory = DaoFactory.getInstance();
        clienteDao = factory.getClienteDao(CLIENTE);

        localisar(538, 10);
        setTitle("Registro_Pagos");
        fecha();
        controles();
        eventoDcFechaPago();
        SNumeros(txtTotalPagar);
        SNumeros(txtAcuenta);
        pagoAcuenta();
    }

    public boolean esNumero(String cadena) {
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
    }

    private void localisar(int x, int y) {
        int w_screen = getToolkit().getScreenSize().width;
        int ka = (w_screen / 2) - (x / 2);
        setLocation(ka, y);

    }

    private void soloLetras(JTextField a) {
        a.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();

                if (Character.isDigit(c)) {
                    getToolkit().beep();
                    e.consume();

                }

            }

        });

    }

    private void SNumeros(final JTextField a) {
        a.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                String s = String.valueOf(e.getKeyChar());
                if (s.matches("[0-9.]")) {

                } else {
                    getToolkit().beep();
                    e.consume();
                }

            }

        });

    }

    public void limpiar() {
        txtCorte.setText(null);
        txtEstado.setText(null);
        txtPer.setText(null);
        txtPago.setText(null);
        txtTarifa.setText(null);
        txt_Pago.setText(null);
        DcProxPago.setDate(null);
        DcProximoCorte.setDate(null);
        flag = true;
        DcProxPago.setEnabled(false);
        DcProximoCorte.setEnabled(false);
        idcliente = -1;
        idEstado = -1;
        txtDiasRetraso.setText(null);
        txtDiasCorte.setText(null);
        txtEjecucionCorte.setText(null);
        txtTotalPagar.setText(null);
        txtTotalPagar.setBackground(new java.awt.Color(204, 204, 204));
        txtEstado.setBackground(new java.awt.Color(204, 204, 204));
        ChkAcuenta.setSelected(false);
        txtDeuda.setText(null);
        txtFpagoSaldo.setText(null);
        pagoAcuenta();
        controles();

    }

    public static void colorEstado(int e) {
        switch (e) {
            case (1): {
                txtEstado.setBackground(new java.awt.Color(153, 255, 0));
                break;
            }
            case (2): {
                txtEstado.setBackground(new java.awt.Color(255, 255, 0));
                break;
            }
            case (3): {
                txtEstado.setBackground(new java.awt.Color(255, 153, 51));
                break;
            }
            case (4): {
                txtEstado.setBackground(new java.awt.Color(255, 102, 102));
                break;
            }
            case (5): {
                txtEstado.setBackground(new java.awt.Color(255, 102, 102));
                break;
            }

        }

    }

    private void fecha() {
        //obtiene la fecha actual del sistema en el formato indicado
        Date ahora = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");
        lblFechaActual.setText("HOY ES : " + formateador.format(ahora));
//lblFechaActual.setText(Fecha.hoy());
    }

    private void eventoDcFechaPago() {

        DcProxPago.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (DcProxPago.getDate() != null) {
                    // DcProximoCorte.setDate(Fecha.SumarDias(DcProxPago.getDate(), 3));
                    llenarCampos2();
                }

            }
        });

    }

    public void dia() {
        int dia = DcProxPago.getDate().getDay();
        Mensaje.panelSms("dia : " + dia);

    }

    public void setUser(Usuario us) {
        usuario = us;

        System.out.println("tipo de usuario  : " + usuario.getIdTipoUser());
        System.out.println("IdPersonal  : " + usuario.getIdPersonal());

    }

    private void controles() {

        txtPago.setEditable(false);
        txt_Pago.setEditable(false);
        txtTarifa.setEditable(false);
        txtEstado.setEditable(false);
        txtCorte.setEditable(false);
        txtPer.setEditable(false);
        txtEjecucionCorte.setEditable(false);
        txtDiasCorte.setEditable(false);
        txtDiasRetraso.setEditable(false);
        txtTotalPagar.setEditable(false);
        DcProxPago.setEnabled(false);
        DcProximoCorte.setEnabled(false);
    }

    public static void llenarCampos() {
        System.out.println("inicio llenar campos ------");
        int diasCorte = 0, diasRetraso = 0;
        if (idcliente != -1) {
            System.out.println("1");

            //a = DaoCliente.buscarCliXid(idcliente);
            a = DaoFactory.getClienteDaoStatic(CLIENTE).buscarCliXid(idcliente);
            System.out.println("01");
            Date f = (Date) a[0];
            System.out.println("02");
            txt_Pago.setText(Fecha.convertToString(f));
            System.out.println("03");
            txtCorte.setText(a[1].toString());
            System.out.println("04");
            txtTarifa.setText(a[2].toString());
            System.out.println("05");
            txtEstado.setText(a[3].toString());
            System.out.println("2");
            if (a[4] != null) {
                System.out.println("3");
                txtEjecucionCorte.setText(Fecha.convertToString((Date) a[4]));
            } else {
                txtEjecucionCorte.setText(null);
                System.out.println("4");
            }

            if ((float) a[6] > 0) {
                System.out.println("5");
                txtDeuda.setText("" + a[6]);
            } else {
                txtDeuda.setText(null);
                System.out.println("6");
            }

            if (a[7] != null) {
                System.out.println("7");
                txtFpagoSaldo.setText(Fecha.convertToString((Date) a[7]));
            } else {
                System.out.println("8");
                txtFpagoSaldo.setText(null);

            }

            //panel de servicio---------
            idEstado = (int) a[5];
            //para los dias de retraso
            System.out.println("9");
            if (idEstado == 3 || idEstado == 2) {
                System.out.println("10");
                // txtEstado.setBackground(new java.awt.Color(255, 153, 51));
                Date fechaInicial = Fecha.deStrigaDate2(a[0].toString());
                Date fechaFinal = Fecha.ahora();
                diasRetraso = Fecha.diferenciasDeFechas(fechaInicial, fechaFinal);
                System.out.println("11");
                txtDiasRetraso.setText("  " + diasRetraso);

                txtDiasCorte.setText(" " + diasCorte);
                System.out.println("12");
            }

            if (idEstado == 4 || idEstado == 5) {
                if (a[4] != null) {
                    System.out.println("13");
                    //para los dias sin servicio 
                    Date fechaInicial = Fecha.deStrigaDate2(a[4].toString());
                    Date fechaFinal = Fecha.ahora();
                    diasCorte = Fecha.diferenciasDeFechas(fechaInicial, fechaFinal);
                    txtDiasCorte.setText("  " + diasCorte);
                    System.out.println("14");
                    //-----dias de retraso---------  
                    Date fechaInicial2 = Fecha.deStrigaDate2(a[0].toString());
                    Date fechaFinal2 = (Date) a[4];
                    diasRetraso = Fecha.diferenciasDeFechas(fechaInicial2, fechaFinal2);
                    txtDiasRetraso.setText("  " + diasRetraso);
                    System.out.println("15");
                }

            }

            Date ff = Fecha.deStrigaDate2(a[0].toString());
            Date fHoy = Fecha.ahora();

            if (Fecha.diferenciasDeFechas(fHoy, ff) > 0 && (float) a[6] > 0) {
                System.out.println("*****  CUNDO SOLO DEVE EL SALDO");
                DcProxPago.setDate(null);
                DcProximoCorte.setDate(null);
                txtPer.setText(" Pago de saldo Pendiente");
                txtTotalPagar.setText("" + a[6]);

            } else {
                int k = (diasRetraso / 30) + 1;
                float tp = ((float) a[2]) * k;
                Date proxPago = null, fechPago;
                fechPago = Fecha.deStrigaDate(txt_Pago.getText().trim());

                for (int i = 1; i <= k; i++) {
                    System.out.println("i=" + i + "    k= " + k);
                    proxPago = (Fecha.SumarDias(fechPago, Fecha.diasXsumar(fechPago)));
                    fechPago = proxPago;

                }
                if (diasCorte > 0) {
                    proxPago = Fecha.SumarDias(proxPago, diasCorte);
                    DcProxPago.setDate(proxPago);
                    DcProximoCorte.setDate(Fecha.SumarDias(DcProxPago.getDate(), 3));
                } else {
                    DcProxPago.setDate(proxPago);
                    DcProximoCorte.setDate(Fecha.SumarDias(DcProxPago.getDate(), 3));
                }

                String periodo = "  " + Fecha.convertToString(Fecha.SumarDias(f, diasCorte)) + "   al   " + Fecha.convertToString(DcProxPago.getDate());
                txtPer.setText(periodo);

                System.out.println("***** dentro de ELSE , CUANDO ya paso su fecha de vencimiento y tieno o no deuda");
                float tp2 = tp + (float) a[6];
                txtTotalPagar.setText("" + tp2);

            }

//---
            colorEstado(idEstado);

            //---total a pagar
        }
        System.out.println("fin llenar campos ------");
    }

    public static void llenarCampos2() {
        System.out.println("inicio llenar campos 2------");
        int diasCorte = 0, diasRetraso = 0;
        if (idcliente != -1) {

            a = DaoFactory.getClienteDaoStatic(CLIENTE).buscarCliXid(idcliente);
            Date f = (Date) a[0];
            txt_Pago.setText(Fecha.convertToString(f));
            txtCorte.setText(a[1].toString());
            txtTarifa.setText(a[2].toString());
            txtEstado.setText(a[3].toString());
            if (a[4] != null) {

                txtEjecucionCorte.setText(Fecha.convertToString((Date) a[4]));
            } else {
                txtEjecucionCorte.setText(null);
            }
            idEstado = (int) a[5];
            //para los dias de retraso
            if (idEstado == 3) {
                Date fechaInicial = Fecha.deStrigaDate2(a[0].toString());
                Date fechaFinal = Fecha.ahora();
                diasRetraso = Fecha.diferenciasDeFechas(fechaInicial, fechaFinal);
                txtDiasRetraso.setText("  " + diasRetraso);
                txtDiasCorte.setText(" " + diasCorte);
            }

            if (idEstado == 4) {
                //para los dias sin servicio 
                Date fechaInicial = Fecha.deStrigaDate2(a[4].toString());
                Date fechaFinal = Fecha.ahora();
                diasCorte = Fecha.diferenciasDeFechas(fechaInicial, fechaFinal);
                txtDiasCorte.setText("  " + diasCorte);
                //-----dias de retraso---------  
                Date fechaInicial2 = Fecha.deStrigaDate2(a[0].toString());
                Date fechaFinal2 = (Date) a[4];
                diasRetraso = Fecha.diferenciasDeFechas(fechaInicial2, fechaFinal2);
                txtDiasRetraso.setText("  " + diasRetraso);

            }

            //DcProxPago.setDate(Fecha.SumarDias(Fecha.deStrigaDate(txt_Pago.getText().trim()), Fecha.diasXsumar(Fecha.deStrigaDate(txt_Pago.getText().trim())) + diasCorte));
            DcProximoCorte.setDate(Fecha.SumarDias(DcProxPago.getDate(), 3));
            String periodo = "  " + Fecha.convertToString(Fecha.SumarDias(f, diasCorte)) + "   al   " + Fecha.convertToString(DcProxPago.getDate());
            txtPer.setText(periodo);

            int k = (diasRetraso / 30) + 1;
            float tp = ((float) a[2]) * k + ((float) a[6]);
            txtTotalPagar.setText("" + tp);

        }
        System.out.println("fin llenar campos 2------");

    }

    private void pagoAcuenta() {
        if (ChkAcuenta.isSelected()) {
            pnlAcuenta.setVisible(true);
            txtAcuenta.requestFocus();

        } else {
            pnlAcuenta.setVisible(false);
            txtAcuenta.setText(null);
            txtSaldo.setText(null);
            DC_FechPagoSaldo.setDate(null);

        }

    }

    public void saldo() {
        if (txtTotalPagar.getText().trim().length() > 0 && txtAcuenta.getText().trim().length() > 0) {
            if (esNumero(txtAcuenta.getText()) == true) {
                float saldo = Float.parseFloat(txtTotalPagar.getText()) - Float.parseFloat(txtAcuenta.getText());
                txtSaldo.setText("" + saldo);
            } else {
                txtAcuenta.setText(Cadena.eliminarUltCaracter(txtAcuenta.getText().trim()));
            }

        } else {
            txtSaldo.setText(null);
        }

    }

    public void registrarPago() {
        if (idcliente != -1) {

            try {
                Pago p = new Pago();
                // p.setFecha(Fecha.deStrigaDate(Fecha.hoy()));
                p.setFech(Fecha.hoy());
                if (txtAcuenta.getText().trim().length() > 0) {
                    p.setMonto(Float.parseFloat(txtAcuenta.getText()));
                } else {

                    p.setMonto(Float.parseFloat(txtTotalPagar.getText()));

                }
                // p.setMonto(Float.parseFloat(txtTotalPagar.getText()));
                p.setPeriodo(txtPer.getText());
                p.setIdCliente(idcliente);
                p.setIdPersonal(usuario.getIdPersonal());
                p.setIdEstado(idEstado);
                System.out.println("--------------------------------");
                System.out.println("personal=" + p.getIdPersonal());
                System.out.println("fecha=" + p.getFech());
                System.out.println("idCli=" + p.getIdCliente());
                System.out.println("monto=" + p.getMonto());
                System.out.println("periodo=" + p.getPeriodo());
                System.out.println("-----------------------------------------");
                Cliente c = new Cliente();
                c.setFechVencimiento(Fecha.convertToString2(DcProxPago.getDate()));
                c.setFechCorte(Fecha.convertToString2(DcProximoCorte.getDate()));
                c.setIdCliente(idcliente);
                if (txtSaldo.getText().trim().length() > 0) {
                    c.setSaldo(Float.parseFloat(txtSaldo.getText()));

                }
                if (DC_FechPagoSaldo.getDate() != null) {
                    c.setF_PagoSaldo(Fecha.convertToString2(DC_FechPagoSaldo.getDate()));
                }

                System.out.println("f_vencimiento=" + c.getFechVencimiento());
                System.out.println("f_corte=" + c.getFechCorte());
                System.out.println("idcli=" + c.getIdCliente());
                System.out.println("saldo=" + c.getSaldo());

                Registro.RegistrarPago(p, c);
            } catch (NumberFormatException e) {
                System.out.println("ERROR: " + e);
            }

        } else {
            Mensaje.panelSms("idCliente no Registrado");
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtEstado = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtPago = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtCorte = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtTarifa = new javax.swing.JTextField();
        btnBuscCli = new javax.swing.JButton();
        txt_Pago = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtEjecucionCorte = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txtDeuda = new javax.swing.JTextField();
        txtFpagoSaldo = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        DcProxPago = new com.toedter.calendar.JDateChooser();
        DcProximoCorte = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        btnEditarCampos = new javax.swing.JButton();
        txtPer = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtDiasRetraso = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtDiasCorte = new javax.swing.JTextField();
        lblFechaActual = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        btnPagar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        pnlAcuenta = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        txtAcuenta = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtSaldo = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        DC_FechPagoSaldo = new com.toedter.calendar.JDateChooser();
        ChkAcuenta = new javax.swing.JCheckBox();
        jPanel5 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        txtTotalPagar = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(0, 0, 51));
        setClosable(true);
        setIconifiable(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(153, 153, 153)), "Datos de Cliente", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial Narrow", 1, 14), new java.awt.Color(204, 204, 204))); // NOI18N
        jPanel1.setOpaque(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Cliente");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Estado");

        txtEstado.setBackground(new java.awt.Color(204, 204, 204));
        txtEstado.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("F_Pago");

        txtPago.setBackground(new java.awt.Color(204, 204, 204));
        txtPago.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("F_ Corte");

        txtCorte.setBackground(new java.awt.Color(204, 204, 204));
        txtCorte.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Mensualidad");

        txtTarifa.setBackground(new java.awt.Color(204, 204, 204));
        txtTarifa.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        txtTarifa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTarifaActionPerformed(evt);
            }
        });

        btnBuscCli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/buscarCiente.png"))); // NOI18N
        btnBuscCli.setOpaque(false);
        btnBuscCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscCliActionPerformed(evt);
            }
        });

        txt_Pago.setBackground(new java.awt.Color(204, 204, 204));
        txt_Pago.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        txt_Pago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_PagoActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("F_ E_Corte");

        txtEjecucionCorte.setBackground(new java.awt.Color(204, 204, 204));
        txtEjecucionCorte.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtEjecucionCorte.setForeground(new java.awt.Color(255, 0, 0));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Deuda");

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("F_Pago Deuda");

        txtDeuda.setBackground(new java.awt.Color(204, 204, 204));
        txtDeuda.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtDeuda.setForeground(new java.awt.Color(255, 0, 0));

        txtFpagoSaldo.setBackground(new java.awt.Color(204, 204, 204));
        txtFpagoSaldo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtFpagoSaldo.setForeground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5)
                            .addComponent(jLabel1)
                            .addComponent(jLabel7))
                        .addGap(28, 28, 28))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_Pago)
                            .addComponent(txtCorte, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
                            .addComponent(txtEstado)
                            .addComponent(txtTarifa))
                        .addGap(32, 32, 32)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addGap(39, 39, 39)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtEjecucionCorte)
                                    .addComponent(txtDeuda, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel19)
                                .addGap(18, 18, 18)
                                .addComponent(txtFpagoSaldo))))
                    .addComponent(txtPago))
                .addGap(18, 18, 18)
                .addComponent(btnBuscCli, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_Pago)
                            .addComponent(jLabel12)
                            .addComponent(txtEjecucionCorte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCorte)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDeuda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtEstado)
                                .addComponent(jLabel18))
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTarifa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(txtFpagoSaldo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel19)))
                        .addGap(36, 36, 36))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnBuscCli, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 580, 210));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(102, 102, 102)), "Datos del servicio", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial Narrow", 1, 14), new java.awt.Color(204, 204, 204))); // NOI18N
        jPanel2.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        jPanel2.setOpaque(false);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Proximo Pago");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Proximo Corte");

        DcProxPago.setBackground(new java.awt.Color(255, 255, 204));
        DcProxPago.setDateFormatString("dd-MM-yyyy");
        DcProxPago.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        DcProximoCorte.setBackground(new java.awt.Color(255, 255, 204));
        DcProximoCorte.setDateFormatString("dd-MM-yyyy");
        DcProximoCorte.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Periodo de Pago");

        btnEditarCampos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/textfield_key.png"))); // NOI18N
        btnEditarCampos.setToolTipText("DESBLOQUEAR CAMPOS");
        btnEditarCampos.setOpaque(false);
        btnEditarCampos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarCamposActionPerformed(evt);
            }
        });

        txtPer.setBackground(new java.awt.Color(204, 204, 204));
        txtPer.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("N° Dias/Retraso");

        txtDiasRetraso.setBackground(new java.awt.Color(204, 204, 204));
        txtDiasRetraso.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("N° Dias en corte");

        txtDiasCorte.setBackground(new java.awt.Color(204, 204, 204));
        txtDiasCorte.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtPer))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(DcProxPago, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
                            .addComponent(txtDiasRetraso))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(jLabel9))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(DcProximoCorte, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
                            .addComponent(txtDiasCorte))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEditarCampos, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(txtDiasRetraso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13)
                            .addComponent(txtDiasCorte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(DcProximoCorte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(DcProxPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)
                            .addComponent(jLabel4)))
                    .addComponent(btnEditarCampos))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtPer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 330, 580, 140));

        lblFechaActual.setBackground(new java.awt.Color(51, 51, 51));
        lblFechaActual.setFont(new java.awt.Font("Segoe Print", 1, 18)); // NOI18N
        lblFechaActual.setForeground(new java.awt.Color(51, 204, 255));
        getContentPane().add(lblFechaActual, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 60, 250, 30));

        jPanel4.setOpaque(false);
        jPanel4.setLayout(new java.awt.GridLayout(1, 3, 6, 0));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/arrow_refresh.png"))); // NOI18N
        jButton1.setToolTipText("Refresh");
        jButton1.setOpaque(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton1);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/cancelarPago44x44.png"))); // NOI18N
        jButton3.setToolTipText("Cancelar");
        jButton3.setOpaque(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton3);

        btnPagar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnPagar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/pago44x44.png"))); // NOI18N
        btnPagar.setToolTipText("Registrar Pago");
        btnPagar.setOpaque(false);
        btnPagar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPagarActionPerformed(evt);
            }
        });
        jPanel4.add(btnPagar);

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 590, 240, 50));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/Encabezados/pagoServiciosInter.png"))); // NOI18N
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 0, 630, 50));

        pnlAcuenta.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(153, 153, 153)));
        pnlAcuenta.setOpaque(false);

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("A CUENTA");

        txtAcuenta.setBackground(new java.awt.Color(204, 204, 204));
        txtAcuenta.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        txtAcuenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAcuentaKeyReleased(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("SALDO");

        txtSaldo.setEditable(false);
        txtSaldo.setBackground(new java.awt.Color(204, 204, 204));
        txtSaldo.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("F_Pago_Saldo");

        DC_FechPagoSaldo.setDateFormatString("dd-MM-yyyy");

        javax.swing.GroupLayout pnlAcuentaLayout = new javax.swing.GroupLayout(pnlAcuenta);
        pnlAcuenta.setLayout(pnlAcuentaLayout);
        pnlAcuentaLayout.setHorizontalGroup(
            pnlAcuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAcuentaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlAcuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(pnlAcuentaLayout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addGap(23, 23, 23)
                        .addComponent(txtAcuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSaldo, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlAcuentaLayout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(DC_FechPagoSaldo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        pnlAcuentaLayout.setVerticalGroup(
            pnlAcuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAcuentaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlAcuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAcuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)
                    .addComponent(jLabel16)
                    .addComponent(txtSaldo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlAcuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(DC_FechPagoSaldo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(pnlAcuenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 500, 350, 80));

        ChkAcuenta.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        ChkAcuenta.setForeground(new java.awt.Color(0, 204, 0));
        ChkAcuenta.setText("Pago a Cuenta");
        ChkAcuenta.setOpaque(false);
        ChkAcuenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkAcuentaActionPerformed(evt);
            }
        });
        getContentPane().add(ChkAcuenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 600, -1, -1));

        jPanel5.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(204, 204, 204)));
        jPanel5.setOpaque(false);

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("TOTAL ");

        txtTotalPagar.setBackground(new java.awt.Color(204, 204, 204));
        txtTotalPagar.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        txtTotalPagar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtTotalPagarMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTotalPagar, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtTotalPagar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39))
        );

        getContentPane().add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 500, 200, 80));

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/Encabezados/ftn1.png"))); // NOI18N
        getContentPane().add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 0, 630, 670));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscCliActionPerformed
        limpiar();
        try {
            BuscaCliente BusCli = new BuscaCliente(null, true);
            BusCli.setVisible(true);

        } catch (Exception e) {
            System.out.println("error :" + e);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBuscCliActionPerformed

    private void btnEditarCamposActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarCamposActionPerformed

        if (flag == true) {
            DcProxPago.setEnabled(true);
            DcProximoCorte.setEnabled(true);
            //txtTotalPagar.setEditable(false);
            // txtTotalPagar.setBackground(new java.awt.Color(255, 255, 255));
            txtPer.setEditable(true);
            flag = false;
        } else {
            DcProxPago.setEnabled(false);
            DcProximoCorte.setEnabled(false);
            txtTotalPagar.setEditable(false);
            //txtTotalPagar.setBackground(new java.awt.Color(226, 253, 198));
            txtPer.setEditable(false);
            flag = true;

        }

        // TODO add your handling code here:
    }//GEN-LAST:event_btnEditarCamposActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        idcliente = -1;
        dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void btnPagarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPagarActionPerformed
        if (idcliente != -1) {
            int i = JOptionPane.showConfirmDialog(this, "Confirme Transaccion", "MENSAJE DE CONFIRMACION", JOptionPane.OK_OPTION, JOptionPane.NO_OPTION);
            if (i == 0) {

                registrarPago();
                if (idEstado == 4 || idEstado == 5) {
                    ClientesXactivarPagos act = new ClientesXactivarPagos(null, true);
                    act.setVisible(true);
                }

                limpiar();
            } else {
                limpiar();
            }

        } else {
            Mensaje.panelSms("error al regPago");
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_btnPagarActionPerformed

    private void txtTarifaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTarifaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTarifaActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        limpiar();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txt_PagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_PagoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_PagoActionPerformed

    private void ChkAcuentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkAcuentaActionPerformed
        pagoAcuenta();

// TODO add your handling code here:
    }//GEN-LAST:event_ChkAcuentaActionPerformed

    private void txtAcuentaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAcuentaKeyReleased
        saldo();

// TODO add your handling code here:
    }//GEN-LAST:event_txtAcuentaKeyReleased

    private void txtTotalPagarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtTotalPagarMouseClicked
        if (evt.getClickCount() == 2) {
            if (usuario.getIdTipoUser() == 1) {
                Mensaje.panelSms("Campo de texto TOTAL habilitado");
                txtTotalPagar.setEditable(true);

            } else {
                Mensaje.panelSms("No tiene Permiso Administratio Para Editar este  campo");
                txtTotalPagar.setEditable(false);
            }

        }

// TODO add your handling code here:
    }//GEN-LAST:event_txtTotalPagarMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox ChkAcuenta;
    private com.toedter.calendar.JDateChooser DC_FechPagoSaldo;
    public static com.toedter.calendar.JDateChooser DcProxPago;
    public static com.toedter.calendar.JDateChooser DcProximoCorte;
    private javax.swing.JButton btnBuscCli;
    private javax.swing.JButton btnEditarCampos;
    private javax.swing.JButton btnPagar;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel lblFechaActual;
    private javax.swing.JPanel pnlAcuenta;
    public static javax.swing.JTextField txtAcuenta;
    public static javax.swing.JTextField txtCorte;
    public static javax.swing.JTextField txtDeuda;
    public static javax.swing.JTextField txtDiasCorte;
    public static javax.swing.JTextField txtDiasRetraso;
    public static javax.swing.JTextField txtEjecucionCorte;
    public static javax.swing.JTextField txtEstado;
    public static javax.swing.JTextField txtFpagoSaldo;
    public static javax.swing.JTextField txtPago;
    public static javax.swing.JTextField txtPer;
    public static javax.swing.JTextField txtSaldo;
    public static javax.swing.JTextField txtTarifa;
    public static javax.swing.JTextField txtTotalPagar;
    public static javax.swing.JTextField txt_Pago;
    // End of variables declaration//GEN-END:variables
}
