/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirene.GUI;

import com.sirene.Bean.Usuario;
import com.sirene.Dao.ClienteDao;
import com.sirene.Dao.Impl.DaoFactory;
//import com.sirene.Dao.Impl.DaoCliente;
import com.sirene.Dao.Impl.Mensaje;
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

public class CongelarServicioCliente extends javax.swing.JInternalFrame {

    Usuario usuario;
    public static int idcliente = -1, idEstado = -1;

    boolean flag = true;
    public static Object[] a;
    public final ClienteDao clienteDao;

    public CongelarServicioCliente() {
        initComponents();

        DaoFactory factory = DaoFactory.getInstance();
        clienteDao = factory.getClienteDao(CLIENTE);

        localisar(538, 10);
        setTitle("Congelar Servicio"
                + "");
        // fecha();
        controles();
        eventoFechaCong();

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

    private void eventoFechaCong() {
        DCfecha.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {

                if (DCfecha.getDate() != null) {
                    // DcProximoCorte.setDate(Fecha.SumarDias(DcProxPago.getDate(), 3));
                    llenarCampos();

                }

            }

        });

    }

    public void limpiar() {
        txtCorte.setText(null);
        txtEstado.setText(null);

        txtPago.setText(null);
        txtTarifa.setText(null);
        txt_Pago.setText(null);

        DCfecha.setDate(null);

        flag = true;

        idcliente = -1;
        idEstado = -1;

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

    public static void fecha() {
        //obtiene la fecha actual del sistema en el formato indicado
        Date ahora = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");
        String hoy = formateador.format(ahora);
        DCfecha.setDate(Fecha.deStrigaDate(hoy));
        // lblFechaActual.setText("HOY ES : " + formateador.format(ahora));
//lblFechaActual.setText(Fecha.hoy());
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

    }

    public static void llenarCampos() {
        int diasCorte = 0, diasRetraso = 0;
        if (idcliente != -1) {

           // a = clienteDao.buscarCliXid(idcliente);
            a = DaoFactory.getClienteDaoStatic(CLIENTE).buscarCliXid(idcliente);
            Date f = (Date) a[0];
            txt_Pago.setText(Fecha.convertToString(f));
            txtCorte.setText(a[1].toString());
            txtTarifa.setText(a[2].toString());
            txtEstado.setText(a[3].toString());

            idEstado = (int) a[5];

            colorEstado(idEstado);

            Date fechaInicial = DCfecha.getDate();
            Date fechaFinal = f;
            diasRetraso = Fecha.diferenciasDeFechas(fechaInicial, fechaFinal);
            txtSaldoDias.setText("  " + diasRetraso);

            //---total a pagar
            // int k = (diasRetraso / 30) + 1;
            //float tp = ((float) a[2]) * k;
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
        DCfecha = new com.toedter.calendar.JDateChooser();
        txtSaldoDias = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        btnPagar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(0, 0, 51));
        setClosable(true);
        setIconifiable(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(153, 153, 153)), "Datos de Cliente", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial Narrow", 1, 14), new java.awt.Color(204, 204, 204))); // NOI18N
        jPanel1.setOpaque(false);
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Cliente");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 33, -1, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Estado");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 92, -1, -1));

        txtEstado.setBackground(new java.awt.Color(204, 204, 204));
        txtEstado.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jPanel1.add(txtEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(78, 89, 121, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("F_Pago");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 65, -1, -1));

        txtPago.setBackground(new java.awt.Color(204, 204, 204));
        txtPago.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jPanel1.add(txtPago, new org.netbeans.lib.awtextra.AbsoluteConstraints(78, 30, 353, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("F_ Corte");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(209, 65, -1, -1));

        txtCorte.setBackground(new java.awt.Color(204, 204, 204));
        txtCorte.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jPanel1.add(txtCorte, new org.netbeans.lib.awtextra.AbsoluteConstraints(289, 62, 139, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Mensualidad");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(209, 92, -1, -1));

        txtTarifa.setBackground(new java.awt.Color(204, 204, 204));
        txtTarifa.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        txtTarifa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTarifaActionPerformed(evt);
            }
        });
        jPanel1.add(txtTarifa, new org.netbeans.lib.awtextra.AbsoluteConstraints(289, 89, 139, -1));

        btnBuscCli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/cli32x32png.png"))); // NOI18N
        btnBuscCli.setToolTipText("BUSCAR CLIENTE ACTIVO");
        btnBuscCli.setOpaque(false);
        btnBuscCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscCliActionPerformed(evt);
            }
        });
        jPanel1.add(btnBuscCli, new org.netbeans.lib.awtextra.AbsoluteConstraints(441, 30, 44, 40));

        txt_Pago.setBackground(new java.awt.Color(204, 204, 204));
        txt_Pago.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jPanel1.add(txt_Pago, new org.netbeans.lib.awtextra.AbsoluteConstraints(78, 62, 121, -1));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Fecha de Congelacion :");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 160, -1, -1));

        DCfecha.setDateFormatString("dd-MM-yyyy");
        jPanel1.add(DCfecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 160, 139, -1));

        txtSaldoDias.setBackground(new java.awt.Color(204, 204, 204));
        txtSaldoDias.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        txtSaldoDias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSaldoDiasActionPerformed(evt);
            }
        });
        jPanel1.add(txtSaldoDias, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 120, 140, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Saldo en Dias");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 120, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 500, 210));

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
        btnPagar.setToolTipText("CONGELAR SERVICIO DE CLIENTE");
        btnPagar.setOpaque(false);
        btnPagar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPagarActionPerformed(evt);
            }
        });
        jPanel4.add(btnPagar);

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 370, 240, 50));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/Encabezados/congServicio.png"))); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, -10, 540, 90));

        jLabel15.setBackground(new java.awt.Color(0, 0, 0));
        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/Encabezados/ftn1.png"))); // NOI18N
        jLabel15.setOpaque(true);
        getContentPane().add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 14, 530, 520));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscCliActionPerformed
        limpiar();
        try {
            BuscaClienteActivo BusCli = new BuscaClienteActivo(null, true);
            BusCli.setVisible(true);

        } catch (Exception e) {
            System.out.println("error :" + e);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBuscCliActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        idcliente = -1;
        dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void btnPagarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPagarActionPerformed
        if (idcliente != -1) {
            int i = JOptionPane.showConfirmDialog(this, "¿ESTA SEGURO DE CONGELAR SERVICIO DE CLIENTE?", "MENSAJE DE CONFIRMACION", JOptionPane.OK_OPTION, JOptionPane.NO_OPTION);
            if (i == 0) {
                if (DCfecha.getDate() != null) {
                    clienteDao.congelarCliente(idcliente, Fecha.convertToString2(DCfecha.getDate()));
                    limpiar();
                }

            } else {
                limpiar();
            }

        } else {
            Mensaje.panelSms("error al congelar cliente");
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

    private void txtSaldoDiasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSaldoDiasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSaldoDiasActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static com.toedter.calendar.JDateChooser DCfecha;
    private javax.swing.JButton btnBuscCli;
    private javax.swing.JButton btnPagar;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    public static javax.swing.JTextField txtCorte;
    public static javax.swing.JTextField txtEstado;
    public static javax.swing.JTextField txtPago;
    public static javax.swing.JTextField txtSaldoDias;
    public static javax.swing.JTextField txtTarifa;
    public static javax.swing.JTextField txt_Pago;
    // End of variables declaration//GEN-END:variables
}
