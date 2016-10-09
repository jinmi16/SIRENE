/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirene.GUI;

import com.sirene.Bean.Pago;
import com.sirene.Bean.Usuario;
import com.sirene.Dao.ClienteDao;
import com.sirene.Dao.Impl.DaoFactory;
//import com.sirene.Dao.Impl.DaoCliente;
//import com.sirene.Dao.Impl.DaoPago;
import com.sirene.Dao.Impl.Mensaje;
import com.sirene.Dao.PagoDao;
import com.sirene.Utilitarios.Cadena;
import static com.sirene.Utilitarios.Constantes.CLIENTE;
import static com.sirene.Utilitarios.Constantes.PAGO;
import com.sirene.Utilitarios.Fecha;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author JINMI
 */
public class ReportePagos extends javax.swing.JInternalFrame {

    DefaultTableModel modTabla;
    DefaultComboBoxModel modCboA = new DefaultComboBoxModel();
    DefaultComboBoxModel modCboB = new DefaultComboBoxModel();
    int k = 0;
    Usuario usuario;
    Pago _objPago = new Pago();
    //int codigoSelect = 0;
    public final ClienteDao clienteDao;
    public final PagoDao pagoDao;

    public ReportePagos() {
        initComponents();

        DaoFactory factory = DaoFactory.getInstance();
        clienteDao = factory.getClienteDao(CLIENTE);
        pagoDao = factory.getPagoDao(PAGO);

        localisar(762, 10);
        setTitle("Consulta_Pagos");
        transparenciaTabla();
        listarCboFecha();
        listarCboOpciones();
        eventoDcFecha1();
        eventoDcFecha2();

    }

    public void setUser(Usuario us) {
        usuario = us;

        System.out.println("tipo de usuario  : " + usuario.getIdTipoUser());
        System.out.println("IdPersonal  : " + usuario.getIdPersonal());
        System.out.println("Tipo User  : " + usuario.getTipoUser());
    }

    private void localisar(int x, int y) {
        int w_screen = getToolkit().getScreenSize().width;
        int ka = (w_screen / 2) - (x / 2);
        setLocation(ka, y);

    }

    private void transparenciaTabla() {
        //La mesa será transparente si ni ella ni las células son opacas:
        tblVista.setOpaque(false);
        ((DefaultTableCellRenderer) tblVista.getDefaultRenderer(Object.class)).setOpaque(false);
        //Si la tabla está en un ScrollPane , que es hacer transparente así
        //ScrollPane.setOpaque(false);
        ScrollPane.getViewport().setOpaque(false);
        //Por lo menos, usted puede quitar las líneas de la cuadrícula:
        tblVista.setShowGrid(false);

    }

    private void eventoDcFecha1() {

        DcFecha1.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (DcFecha1.getDate() != null && cboFecha.getSelectedIndex() == 0) {
                    listarConsula();

                }

            }
        });

    }

    private void eventoDcFecha2() {

        DcFecha2.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (DcFecha2.getDate() != null && DcFecha1.getDate() != null && cboFecha.getSelectedIndex() == 1) {
                    listarConsula();

                }

            }
        });

    }

    private void listarCboFecha() {
        modCboA.addElement("Fecha");
        modCboA.addElement("Rango de Fechas");
        cboFecha.setModel(modCboA);

    }

    private void listarCboOpciones() {
        modCboB.addElement("Nombre_Cliente");
        modCboB.addElement("Apellido_Cliente");
        modCboB.addElement("Dni_Cliente");
        modCboB.addElement("Nombre_Personal");
        modCboB.addElement("Anulados");
        cboOpciones.setModel(modCboB);
    }

    private void estadoFecha() {
        DcFecha1.setDate(null);
        DcFecha2.setDate(null);
        if (tblVista.getRowCount() > 0) {
            while (modTabla.getRowCount() > 0) {
                modTabla.removeRow(0);
            }
        }
        if (cboFecha.getSelectedIndex() == 1) {
            DcFecha2.setEnabled(true);
        } else {
            DcFecha2.setEnabled(false);

        }
        lblTotalItems.setText(" 0");
        lblTotalMonto.setText(" 0");

    }

    public String sql() {
        String sql = "";
        if (cboFecha.getSelectedIndex() == 0) {
            if (DcFecha1.getDate() != null) {
                sql = " AND p.fecha='" + Fecha.convertToString2(DcFecha1.getDate()) + "' ";
            } else {
                sql = " AND p.fecha LIKE '%' ";

            }

        } else if (DcFecha2.getDate() != null && DcFecha1.getDate() != null) {
            sql = " AND ( p.fecha BETWEEN '" + Fecha.convertToString2(DcFecha1.getDate()) + "' AND '" + Fecha.convertToString2(DcFecha2.getDate()) + "')";
        } else {
            sql = " AND p.fecha LIKE '%' ";
        }

        return sql;
    }

    public String sql2() {
        String sql = "", txt = txtBuscar.getText().trim();

        if (cboOpciones.getSelectedIndex() == 0) {
            sql = " AND c.nombre LIKE '" + txt + "%' ";
        } else if (cboOpciones.getSelectedIndex() == 1) {
            sql = " AND c.apellido LIKE '" + txt + "%' ";
        } else if (cboOpciones.getSelectedIndex() == 2) {
            sql = " AND c.DNI LIKE '" + txt + "%' ";
        } else if (cboOpciones.getSelectedIndex() == 3) {
            sql = " AND pe.nombre LIKE '" + txt + "%' ";

        } else if (cboOpciones.getSelectedIndex() == 4) {
            sql = " AND p.Anulado LIKE '" + txt + "%' ";

        }

        return sql;
    }

    public void listarConsula() {

        k = pagoDao.ConsultarPago(sql(), sql2()).size();

        if (k > 0) {
            Object[] colum = {"Codigo", "Fecha_Pagada", "Nombre", "Apellido", "Dni", "Periodo", "Monto", "Personal", "Anulado"};
            // con este codigo adicional al modelo de yabla consigo que las celdas y columnas no sean 
            // editables

            modTabla = new DefaultTableModel(null, colum) {

                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            double total = 0;
            for (Object[] p : pagoDao.ConsultarPago(sql(), sql2())) {
                total = total + Double.parseDouble(p[6].toString());
                modTabla.addRow(p);
            }

            tblVista.setModel(modTabla);
            anchoColum();

            lblTotalItems.setText(" " + k);
            lblTotalMonto.setText(" " + total);

        } else {
            if (tblVista.getRowCount() > 0) {
                while (modTabla.getRowCount() > 0) {
                    modTabla.removeRow(0);
                }
            }

            lblTotalItems.setText("" + k);
            lblTotalMonto.setText(" 0");
        }
        limpiarSeleccion();

    }

    private void anchoColum() {

        tblVista.getColumnModel().getColumn(0).setMinWidth(50);
        //tblVista.getColumnModel().getColumn(0).setMaxWidth(100);
        tblVista.getColumnModel().getColumn(1).setMinWidth(80);
        //tblVista.getColumnModel().getColumn(1).setMaxWidth(110);
        tblVista.getColumnModel().getColumn(2).setMinWidth(100);
//        tblVista.getColumnModel().getColumn(2).setMaxWidth(110);
        tblVista.getColumnModel().getColumn(3).setMinWidth(100);
        //tblVista.getColumnModel().getColumn(3).setMaxWidth(65);
        tblVista.getColumnModel().getColumn(4).setMinWidth(80);
        // tblVista.getColumnModel().getColumn(4).setMaxWidth(190);
        tblVista.getColumnModel().getColumn(5).setMinWidth(150);
        // tblVista.getColumnModel().getColumn(5).setMaxWidth(60);
        tblVista.getColumnModel().getColumn(6).setMinWidth(100);
        //tblVista.getColumnModel().getColumn(6).setMaxWidth(110);
        tblVista.getColumnModel().getColumn(7).setMinWidth(120);
        // tblVista.getColumnModel().getColumn(7).setMaxWidth(110);

    }

    public void AnularPago(Pago p) {
        //boolean anulado = pagoDao.AnularPago(p);
        if (p.getIdPago() != 0 && txtTransaccionSelecionada.getText().length() > 0 && p.getAnulado().equals("NO")) {

            int i = JOptionPane.showConfirmDialog(this, "Esta seguro de anular Transaccion?", "MENSAJE DE CONFIRMACION", JOptionPane.OK_OPTION, JOptionPane.NO_OPTION);
            if (i == 0) {
                if (pagoDao.AnularPago(p)) {
                    Mensaje.panelSms("Transaccion [ " + p.getIdPago() + " ]Anulada Exitosamente");
                } else {
                    Mensaje.panelSms("NO se pudo anular Transaccion");
                }

            }

        } else {
            Mensaje.panelSms("- seleccione un Registro \n - Asegurese que el registro NO este anulado ");

        }

    }

    public void limpiar() {
        cboFecha.setSelectedIndex(0);
        cboOpciones.setSelectedIndex(0);
        txtBuscar.setText(null);
        DcFecha2.setEnabled(false);
        if (tblVista.getRowCount() > 0) {
            while (modTabla.getRowCount() > 0) {
                modTabla.removeRow(0);
            }
        }
        lblTotalItems.setText(" 0");
        lblTotalMonto.setText(" 0");

    }

    public void limpiarSeleccion() {
        _objPago.setIdPago(0);
        _objPago.setMonto(0);
        _objPago.setAnulado("NO");
        txtTransaccionSelecionada.setText(null);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        ScrollPane = new javax.swing.JScrollPane();
        tblVista = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblTotalItems = new javax.swing.JLabel();
        lblTotalMonto = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        DcFecha1 = new com.toedter.calendar.JDateChooser();
        DcFecha2 = new com.toedter.calendar.JDateChooser();
        cboFecha = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        cboOpciones = new javax.swing.JComboBox();
        txtBuscar = new javax.swing.JTextField();
        txtTransaccionSelecionada = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(0, 0, 51));
        setClosable(true);
        setIconifiable(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setOpaque(false);

        ScrollPane.setBackground(new java.awt.Color(255, 0, 0));
        ScrollPane.setMinimumSize(new java.awt.Dimension(650, 700));
        ScrollPane.setOpaque(false);

        tblVista.setBackground(new java.awt.Color(0, 0, 51));
        tblVista.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tblVista.setForeground(new java.awt.Color(255, 255, 255));
        tblVista.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblVista.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblVista.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblVistaMouseClicked(evt);
            }
        });
        ScrollPane.setViewportView(tblVista);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 204, 0));
        jLabel2.setText("Total Items");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 204, 0));
        jLabel3.setText("Total Monto");

        lblTotalItems.setBackground(new java.awt.Color(255, 255, 255));
        lblTotalItems.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTotalItems.setOpaque(true);

        lblTotalMonto.setBackground(new java.awt.Color(255, 255, 255));
        lblTotalMonto.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTotalMonto.setOpaque(true);

        jPanel3.setOpaque(false);
        jPanel3.setLayout(new java.awt.GridLayout(1, 2, 5, 5));

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/arrow_refresh.png"))); // NOI18N
        jButton2.setBorderPainted(false);
        jButton2.setOpaque(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton2);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/cancel.png"))); // NOI18N
        jButton1.setOpaque(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(ScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 683, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 3, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblTotalItems, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTotalMonto, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(ScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblTotalItems, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblTotalMonto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(468, 468, 468)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 710, 520));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Busqueda de pagos por:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel1.setOpaque(false);
        jPanel1.setLayout(null);

        DcFecha1.setDateFormatString("dd-MM-yyyy");
        DcFecha1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jPanel1.add(DcFecha1);
        DcFecha1.setBounds(203, 33, 101, 20);

        DcFecha2.setDateFormatString("dd-MM-yyyy");
        DcFecha2.setEnabled(false);
        DcFecha2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jPanel1.add(DcFecha2);
        DcFecha2.setBounds(352, 33, 105, 20);

        cboFecha.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cboFecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboFechaActionPerformed(evt);
            }
        });
        jPanel1.add(cboFecha);
        cboFecha.setBounds(16, 33, 177, 21);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 204, 0));
        jLabel1.setText("Hasta");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(308, 36, 40, 17);

        cboOpciones.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cboOpciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboOpcionesActionPerformed(evt);
            }
        });
        jPanel1.add(cboOpciones);
        cboOpciones.setBounds(463, 33, 136, 21);

        txtBuscar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });
        jPanel1.add(txtBuscar);
        txtBuscar.setBounds(605, 33, 111, 21);
        jPanel1.add(txtTransaccionSelecionada);
        txtTransaccionSelecionada.setBounds(20, 80, 610, 20);

        jButton3.setText("Anular");
        jButton3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton3.setOpaque(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3);
        jButton3.setBounds(640, 80, 70, 20);

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 740, 650));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/Encabezados/C1.png"))); // NOI18N
        jLabel4.setPreferredSize(new java.awt.Dimension(764, 50));
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 0, 760, 50));

        jLabel5.setBackground(new java.awt.Color(0, 0, 51));
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/Encabezados/ftn1.png"))); // NOI18N
        jLabel5.setOpaque(true);
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 750, 670));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cboFechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboFechaActionPerformed
        estadoFecha();
        // TODO add your handling code here:
    }//GEN-LAST:event_cboFechaActionPerformed

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased

        listarConsula();
        if (k == 0) {
            Mensaje.panelSms("NO SE ENCONTRARON REGISTROS");
            txtBuscar.setText(Cadena.eliminarUltCaracter(txtBuscar.getText().trim()));
            listarConsula();
        }

// TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        limpiar();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void cboOpcionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboOpcionesActionPerformed
        txtBuscar.setText(null);
        txtBuscar.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_cboOpcionesActionPerformed

    private void tblVistaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblVistaMouseClicked
//
        if (evt.getClickCount() == 2) {
            int i = tblVista.getSelectedRow();
            String transaccion = "";
            transaccion = "[ " + tblVista.getValueAt(i, 1).toString() + " ]  [ " + tblVista.getValueAt(i, 2).toString() + " " + tblVista.getValueAt(i, 3).toString() + " ]  [ " + tblVista.getValueAt(i, 5).toString() + " ]  [ " + tblVista.getValueAt(i, 6).toString() + " S/ ]";
            txtTransaccionSelecionada.setText(transaccion);
            System.out.println("---OBJpAGO");

            _objPago.setIdPago(Integer.parseInt(tblVista.getValueAt(i, 0).toString()));
            System.out.println("id: " + tblVista.getValueAt(i, 0).toString());

            _objPago.setMonto(Float.parseFloat(tblVista.getValueAt(i, 6).toString()));
            System.out.println("id: " + tblVista.getValueAt(i, 6).toString());

            _objPago.setAnulado(tblVista.getValueAt(i, 8).toString());
            System.out.println("anulado: " + tblVista.getValueAt(i, 8).toString());
            System.out.println("FIN ---OBJpAGO");
        }

// TODO add your handling code here:
    }//GEN-LAST:event_tblVistaMouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        AnularPago(_objPago);
        listarConsula();

// TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser DcFecha1;
    private com.toedter.calendar.JDateChooser DcFecha2;
    private javax.swing.JScrollPane ScrollPane;
    private javax.swing.JComboBox cboFecha;
    private javax.swing.JComboBox cboOpciones;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblTotalItems;
    private javax.swing.JLabel lblTotalMonto;
    private javax.swing.JTable tblVista;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtTransaccionSelecionada;
    // End of variables declaration//GEN-END:variables
}
