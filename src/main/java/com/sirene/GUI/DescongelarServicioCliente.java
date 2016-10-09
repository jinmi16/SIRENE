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
import static com.sirene.GUI.CongelarServicioCliente.DCfecha;
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

public class DescongelarServicioCliente extends javax.swing.JInternalFrame {

    Usuario usuario;
    public static int idcliente = -1, idEstado = -1;

    boolean flag = true;
    public static boolean f = false;
    public static Object[] a;
    public final ClienteDao clienteDao;

    public DescongelarServicioCliente() {
        initComponents();

        DaoFactory factory = DaoFactory.getInstance();
        clienteDao = factory.getClienteDao(CLIENTE);

        localisar(538, 10);
        setTitle("Descongelar Servicio");
        fecha();
        controles();
        eventoDcFechaPago();
        eventoDcFechaInicioSaldo();

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
        txtF_Corte.setText(null);
        txtEstado.setText(null);
        txtPer.setText(null);
        txtPago.setText(null);
        txtTarifa.setText(null);
        txt_F_Pago.setText(null);
        DcProxPago.setDate(null);
        DcProximoCorte.setDate(null);
        flag = true;
        f = false;
        DcProxPago.setEnabled(false);
        DcProximoCorte.setEnabled(false);
        DCfechaIS.setDate(null);
        idcliente = -1;
        idEstado = -1;
        txtDiasSaldo.setText(null);

        txtFechaCongelada.setText(null);

        txtEstado.setBackground(new java.awt.Color(255, 255, 204));
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

                    if (f == true) {
                        llenarCampos2();

                        System.out.println("hola..........");
                    }

                }

            }
        });

    }

    private void eventoDcFechaInicioSaldo() {

        DCfechaIS.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (DCfechaIS.getDate() != null) {
                    // DcProximoCorte.setDate(Fecha.SumarDias(DcProxPago.getDate(), 3));

                    llenarCampos3();

                    System.out.println("hola....ll 3......");

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

    public static void fechaIS() {
        //obtiene la fecha actual del sistema en el formato indicado
        Date ahora = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");
        String hoy = formateador.format(ahora);
        DCfechaIS.setDate(Fecha.deStrigaDate(hoy));
        // lblFechaActual.setText("HOY ES : " + formateador.format(ahora));
//lblFechaActual.setText(Fecha.hoy());
    }

    private void controles() {

        txtPago.setEditable(false);
        txt_F_Pago.setEditable(false);
        txtTarifa.setEditable(false);
        txtEstado.setEditable(false);
        txtF_Corte.setEditable(false);
        txtPer.setEditable(false);
        txtFechaCongelada.setEditable(false);

        txtDiasSaldo.setEditable(false);

        DcProxPago.setEnabled(false);
        DcProximoCorte.setEnabled(false);
        DCfechaIS.setEnabled(false);
    }

    public static void llenarCampos() {
        System.out.println("-------------inicio llenar campos -----------");
        int diasCorte = 0, diasRetraso = 0;
        if (idcliente != -1) {

            //a = clienteDao.buscarCliCongeladoXid(idcliente);
            a = DaoFactory.getClienteDaoStatic(CLIENTE).buscarCliCongeladoXid(idcliente);
            Date fe = (Date) a[0];
            txt_F_Pago.setText(Fecha.convertToString(fe));
            txtF_Corte.setText(a[1].toString());
            txtTarifa.setText(a[2].toString());
            txtEstado.setText(a[3].toString());
            System.out.println("f_congelada : " + a[4]);
            //txtFechaCongelada.setText(" "+a[4]);
            if (a[4] != null) {

                txtFechaCongelada.setText(Fecha.convertToString((Date) a[4]));
            } else {
                txtFechaCongelada.setText("hola");
            }
            idEstado = (int) a[5];
            //para los dias de saldo
            if (idEstado == 7) {
                // saldo= fpagoAnterior - f_congelada   : f-i

                Date fechaInicial = Fecha.deStrigaDate2(a[4].toString());
                Date fechaFinal = fe;
                diasRetraso = Fecha.diferenciasDeFechas(fechaInicial, fechaFinal);
                txtDiasSaldo.setText("  " + diasRetraso);

            }

            System.out.println("********* 1 *****");
            //f=false;
            DcProxPago.setDate(Fecha.SumarDias(DCfechaIS.getDate(), diasRetraso));
            // f=true;
            System.out.println("********* 2 *****");
            DcProximoCorte.setDate(Fecha.SumarDias(DcProxPago.getDate(), 3));
            String periodo = "  " + Fecha.convertToString(DCfechaIS.getDate()) + "   al   " + Fecha.convertToString(DcProxPago.getDate());
            txtPer.setText(periodo);
//---
            colorEstado(idEstado);

        }
        System.out.println("----------FIN llenar campos -----------");
    }

    public static void llenarCampos2() {
        System.out.println("....llenar 2 ...");
        int diasCorte = 0, diasRetraso = 0;
        if (idcliente != -1) {

            a = DaoFactory.getClienteDaoStatic(CLIENTE).buscarCliCongeladoXid(idcliente);
            Date fe = (Date) a[0];
            txt_F_Pago.setText(Fecha.convertToString(fe));
            txtF_Corte.setText(a[1].toString());
            txtTarifa.setText(a[2].toString());
            txtEstado.setText(a[3].toString());
            System.out.println("f_congelada : " + a[4]);
            //txtFechaCongelada.setText(" "+a[4]);
            if (a[4] != null) {

                txtFechaCongelada.setText(Fecha.convertToString((Date) a[4]));
            } else {
                txtFechaCongelada.setText("hola");
            }
            idEstado = (int) a[5];
            //para los dias de saldo
            if (idEstado == 7) {
                // saldo= fpagoAnterior - f_congelada   : f-i

                Date fechaInicial = Fecha.deStrigaDate2(a[4].toString());
                Date fechaFinal = fe;
                diasRetraso = Fecha.diferenciasDeFechas(fechaInicial, fechaFinal);
                txtDiasSaldo.setText("  " + diasRetraso);

            }

            System.out.println("********* 1  ll2 *****");

            //DcProxPago.setDate(Fecha.SumarDias(DCfechaIS.getDate(),  diasRetraso));
            System.out.println("********* 2  ll2*****");
            f = false;
            DcProximoCorte.setDate(Fecha.SumarDias(DcProxPago.getDate(), 3));
            f = true;
            String periodo = "  " + Fecha.convertToString(DCfechaIS.getDate()) + "   al   " + Fecha.convertToString(DcProxPago.getDate());
            txtPer.setText(periodo);
//---
            colorEstado(idEstado);

        }
        System.out.println("....FIN DE llenar 2 ...");
    }

    public static void llenarCampos3() {
        System.out.println("....llenar 3 ...");
        int diasCorte = 0, diasRetraso = 0;
        if (idcliente != -1) {

            a = DaoFactory.getClienteDaoStatic(CLIENTE).buscarCliCongeladoXid(idcliente);
            Date fe = (Date) a[0];
            txt_F_Pago.setText(Fecha.convertToString(fe));
            txtF_Corte.setText(a[1].toString());
            txtTarifa.setText(a[2].toString());
            txtEstado.setText(a[3].toString());
            System.out.println("f_congelada : " + a[4]);
            //txtFechaCongelada.setText(" "+a[4]);

            /* if (a[4] != null) {

                txtFechaCongelada.setText(Fecha.convertToString((Date) a[4]));
            } else {
                txtFechaCongelada.setText("hola");
            }
             */
            idEstado = (int) a[5];
            //para los dias de saldo
            if (idEstado == 7) {
                // saldo= fpagoAnterior - f_congelada   : f-i

                Date fechaInicial = Fecha.deStrigaDate2(a[4].toString());
                Date fechaFinal = fe;
                diasRetraso = Fecha.diferenciasDeFechas(fechaInicial, fechaFinal);
                txtDiasSaldo.setText("  " + diasRetraso);

            }

            System.out.println("********* 1  ll_3 *****");

            DcProxPago.setDate(Fecha.SumarDias(DCfechaIS.getDate(), diasRetraso));

            System.out.println("********* 2  ll_3*****");
            f = false;
            DcProximoCorte.setDate(Fecha.SumarDias(DcProxPago.getDate(), 3));
            f = true;
            String periodo = "  " + Fecha.convertToString(DCfechaIS.getDate()) + "   al   " + Fecha.convertToString(DcProxPago.getDate());
            txtPer.setText(periodo);
//---
            colorEstado(idEstado);

        }
        System.out.println("....FIN DE llenar 3 ...");
    }

    public void descongelarCliente() {

        try {
            Cliente c = new Cliente();
            c.setIdCliente(idcliente);
            // c.setIdEstado(1);
            c.setFechVencimiento(Fecha.convertToString2(DcProxPago.getDate()));
            c.setFechCorte(Fecha.convertToString2(DcProximoCorte.getDate()));

            clienteDao.DescongelarCliente(c);

        } catch (NumberFormatException e) {
            System.out.println("ERROR: " + e);
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
        txtF_Corte = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtTarifa = new javax.swing.JTextField();
        btnBuscCli = new javax.swing.JButton();
        txt_F_Pago = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtFechaCongelada = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtDiasSaldo = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        DcProxPago = new com.toedter.calendar.JDateChooser();
        DcProximoCorte = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        btnEditarCampos = new javax.swing.JButton();
        txtPer = new javax.swing.JTextField();
        DCfechaIS = new com.toedter.calendar.JDateChooser();
        jLabel10 = new javax.swing.JLabel();
        lblFechaActual = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        btnPagar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(0, 0, 51));
        setClosable(true);
        setIconifiable(true);
        setMinimumSize(new java.awt.Dimension(538, 565));
        setPreferredSize(new java.awt.Dimension(538, 567));
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

        txtF_Corte.setBackground(new java.awt.Color(204, 204, 204));
        txtF_Corte.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N

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

        btnBuscCli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/cli32x32png.png"))); // NOI18N
        btnBuscCli.setToolTipText("BUSCAR CLIENTE CONGELADO");
        btnBuscCli.setOpaque(false);
        btnBuscCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscCliActionPerformed(evt);
            }
        });

        txt_F_Pago.setBackground(new java.awt.Color(204, 204, 204));
        txt_F_Pago.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Fecha de Congelacion de Servicio");

        txtFechaCongelada.setBackground(new java.awt.Color(204, 204, 204));
        txtFechaCongelada.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtFechaCongelada.setForeground(new java.awt.Color(0, 0, 153));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Saldo en dias ");

        txtDiasSaldo.setBackground(new java.awt.Color(204, 204, 204));
        txtDiasSaldo.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(81, Short.MAX_VALUE)
                .addComponent(jLabel12)
                .addGap(208, 208, 208))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(txtPago)
                .addGap(60, 60, 60))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel5)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel6)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_F_Pago, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel8))
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtFechaCongelada)
                            .addComponent(txtTarifa)
                            .addComponent(txtF_Corte)
                            .addComponent(txtDiasSaldo))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBuscCli, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnBuscCli, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(txt_F_Pago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel6))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtF_Corte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtTarifa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8)
                                    .addComponent(txtEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtFechaCongelada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel12))))))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDiasSaldo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addContainerGap())
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 500, 210));

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
        jLabel2.setText("Periodo_Saldo");

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

        DCfechaIS.setDateFormatString("dd-MM-yyyy");
        DCfechaIS.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Inicio de periodo de Saldo");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtPer, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(DcProxPago, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(DcProximoCorte, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(DCfechaIS, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(101, 101, 101)
                        .addComponent(btnEditarCampos, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(DCfechaIS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))
                        .addGap(23, 23, 23)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(DcProximoCorte, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(DcProxPago, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtPer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btnEditarCampos))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 320, 500, 150));

        lblFechaActual.setBackground(new java.awt.Color(51, 51, 51));
        lblFechaActual.setFont(new java.awt.Font("Geometr212 BkCn BT", 1, 18)); // NOI18N
        lblFechaActual.setForeground(new java.awt.Color(51, 204, 255));
        getContentPane().add(lblFechaActual, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 80, 250, 30));

        jPanel4.setOpaque(false);
        jPanel4.setLayout(new java.awt.GridLayout(1, 3, 6, 0));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/arrow_refresh.png"))); // NOI18N
        jButton1.setToolTipText("REFRESH");
        jButton1.setOpaque(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton1);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/cancel.png"))); // NOI18N
        jButton3.setToolTipText("CANCELAR");
        jButton3.setOpaque(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton3);

        btnPagar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnPagar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/accept.png"))); // NOI18N
        btnPagar.setToolTipText("DESCONGELAR SERVICIO");
        btnPagar.setOpaque(false);
        btnPagar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPagarActionPerformed(evt);
            }
        });
        jPanel4.add(btnPagar);

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 480, 240, 50));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/Encabezados/descServ.png"))); // NOI18N
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, -10, 550, 60));

        jLabel15.setBackground(new java.awt.Color(0, 0, 0));
        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/Encabezados/ftn1.png"))); // NOI18N
        jLabel15.setOpaque(true);
        getContentPane().add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -6, 540, 540));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscCliActionPerformed
        limpiar();
        try {
            BuscaClienteCongelado BusCli = new BuscaClienteCongelado(null, true);
            BusCli.setVisible(true);

        } catch (Exception e) {
            System.out.println("error :" + e);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBuscCliActionPerformed

    private void btnEditarCamposActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarCamposActionPerformed
        f = false;
        if (flag == true) {

            DcProxPago.setEnabled(true);
            DcProximoCorte.setEnabled(true);
            DCfechaIS.setEnabled(true);

            flag = false;
            // f=true;
        } else {
            // f=false;
            DcProxPago.setEnabled(false);
            DcProximoCorte.setEnabled(false);
            DCfechaIS.setEnabled(false);

            flag = true;

        }
        f = true;
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEditarCamposActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        idcliente = -1;
        idEstado = -1;
        dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void btnPagarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPagarActionPerformed
        if (idcliente != -1) {
            int i = JOptionPane.showConfirmDialog(this, "¿Seguro que quiere descongelar servicio de cliente ?", "MENSAJE DE CONFIRMACION", JOptionPane.OK_OPTION, JOptionPane.NO_OPTION);
            if (i == 0) {
                descongelarCliente();

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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static com.toedter.calendar.JDateChooser DCfechaIS;
    public static com.toedter.calendar.JDateChooser DcProxPago;
    public static com.toedter.calendar.JDateChooser DcProximoCorte;
    private javax.swing.JButton btnBuscCli;
    private javax.swing.JButton btnEditarCampos;
    private javax.swing.JButton btnPagar;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
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
    private javax.swing.JLabel lblFechaActual;
    public static javax.swing.JTextField txtDiasSaldo;
    public static javax.swing.JTextField txtEstado;
    public static javax.swing.JTextField txtF_Corte;
    public static javax.swing.JTextField txtFechaCongelada;
    public static javax.swing.JTextField txtPago;
    public static javax.swing.JTextField txtPer;
    public static javax.swing.JTextField txtTarifa;
    public static javax.swing.JTextField txt_F_Pago;
    // End of variables declaration//GEN-END:variables
}
