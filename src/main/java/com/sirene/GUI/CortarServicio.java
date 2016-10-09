package com.sirene.GUI;

import com.sirene.Dao.ClienteDao;
//import com.sirene.Dao.Impl.DaoCliente;
import com.sirene.Dao.Impl.DaoFactory;
import com.sirene.Dao.Impl.Mensaje;
import com.sirene.Utilitarios.Cadena;
import static com.sirene.Utilitarios.Constantes.CLIENTE;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class CortarServicio extends javax.swing.JInternalFrame {

    DefaultTableModel modTabla;
    String cliente;

    int id = -1;
    boolean llave = false;
    public final ClienteDao clienteDao;

    public CortarServicio() {
        initComponents();

        DaoFactory factory = DaoFactory.getInstance();
        clienteDao = factory.getClienteDao(CLIENTE);

        localisar(498, 10);
        setTitle("Cortar_servicio");
        transparenciaTabla();
        listar();
    }

    private void localisar(int x, int y) {
        int w_screen = getToolkit().getScreenSize().width;
        int k = (w_screen / 2) - (x / 2);
        setLocation(k, y);

    }

    private void transparenciaTabla() {
        //La mesa será transparente si ni ella ni las células son opacas:
        tblVista.setOpaque(false);
        ((DefaultTableCellRenderer) tblVista.getDefaultRenderer(Object.class)).setOpaque(false);
        //Si la tabla está en un ScrollPane , que es hacer transparente así
        jScrollPane2.setOpaque(false);
        jScrollPane2.getViewport().setOpaque(false);
        //Por lo menos, usted puede quitar las líneas de la cuadrícula:
        // tblVista.setShowGrid(false);

    }

    private void listar() {
        int k = clienteDao.listarCliCortSE().size();
        if (k > 0) {
            // llave = true;

            Object[] colum = {"Id", "Nombre", "Apellido", "F_Vencimiento", "F_Corte", "Servidor", "estado"};
            modTabla = new DefaultTableModel(null, colum) {

                public boolean isCellEditable(int row, int colum) {
                    return false;
                }
            };
            for (Object[] fila : clienteDao.listarCliCortSE()) {

                modTabla.addRow(fila);

            }
            tblVista.setModel(modTabla);

            anchoColum();
            cliente = tblVista.getValueAt(0, 1).toString() + " " + tblVista.getValueAt(0, 2).toString();
            txtCliente.setText(cliente);
            txtServidor.setText(tblVista.getValueAt(0, 5).toString());
            id = Integer.parseInt(tblVista.getValueAt(0, 0).toString());
            lblTotal.setText("  " + k);
        } else {
            // llave = false;

            modTabla = new DefaultTableModel();
            tblVista.setModel(modTabla);
            //Mensaje.panelSms("No hay clientes con estado CORTE SIN EJECUTAR");
            id = -1;
            txtCliente.setText(null);
            lblTotal.setText("  " + k);
        }

    }

    public void listarBusqueda(int x, String cadena) {
        int k = clienteDao.BuscarCliCortSE(x, cadena).size();
        if (k > 0) {
            llave = false;
            Object[] colum = {"Id", "Nombre", "Apellido", "F_Vencimiento", "F_Corte", "Servidor", "estado"};
            modTabla = new DefaultTableModel(null, colum) {

                public boolean isCellEditable(int row, int colum) {
                    return false;
                }
            };
            for (Object[] fila : clienteDao.BuscarCliCortSE(x, cadena)) {
                //Object[] fila = {c.getIdCliente(), c.getNombre(), c.getApellido(), c.getDni()};
                modTabla.addRow(fila);

            }
            tblVista.setModel(modTabla);

            anchoColum();
            cliente = tblVista.getValueAt(0, 1).toString() + " " + tblVista.getValueAt(0, 2).toString();
            txtCliente.setText(cliente);
            txtServidor.setText(tblVista.getValueAt(0, 5).toString());
            id = Integer.parseInt(tblVista.getValueAt(0, 0).toString());
            lblTotal.setText("  " + k);
        } else {
            llave = true;
            lblTotal.setText("  " + k);
            Mensaje.panelSms("No se encontraron Registros");

        }

    }

    public void anchoColum() {
        tblVista.getColumnModel().getColumn(0).setMinWidth(25);
        tblVista.getColumnModel().getColumn(0).setMaxWidth(30);
        tblVista.getColumnModel().getColumn(1).setMinWidth(110);
        tblVista.getColumnModel().getColumn(1).setMaxWidth(150);
        tblVista.getColumnModel().getColumn(2).setMinWidth(130);
        tblVista.getColumnModel().getColumn(2).setMaxWidth(170);
        tblVista.getColumnModel().getColumn(3).setMinWidth(90);
        tblVista.getColumnModel().getColumn(3).setMaxWidth(100);
        tblVista.getColumnModel().getColumn(4).setMinWidth(90);
        tblVista.getColumnModel().getColumn(4).setMaxWidth(100);
        tblVista.getColumnModel().getColumn(5).setMinWidth(110);
        //tblVista.getColumnModel().getColumn(5).setMaxWidth(150);
        tblVista.getColumnModel().getColumn(6).setMinWidth(140);
        tblVista.getColumnModel().getColumn(6).setMaxWidth(180);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtCliente = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtServidor = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        btnCortarSrvicio = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        cboBusCampo = new javax.swing.JComboBox();
        txtBuscar = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblVista = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(51, 0, 0));
        setClosable(true);
        setIconifiable(true);
        setMinimumSize(new java.awt.Dimension(709, 599));
        setPreferredSize(new java.awt.Dimension(709, 599));
        setRequestFocusEnabled(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setOpaque(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Cliente");

        txtCliente.setEditable(false);
        txtCliente.setBackground(new java.awt.Color(204, 255, 0));
        txtCliente.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txtCliente.setForeground(new java.awt.Color(204, 255, 0));
        txtCliente.setOpaque(false);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Servidor");

        txtServidor.setEditable(false);
        txtServidor.setBackground(new java.awt.Color(204, 255, 0));
        txtServidor.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txtServidor.setForeground(new java.awt.Color(204, 255, 0));
        txtServidor.setOpaque(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtServidor, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtServidor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 570, -1));

        jPanel4.setOpaque(false);
        jPanel4.setLayout(new java.awt.GridLayout(3, 1, 0, 2));

        btnCortarSrvicio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/sinSeñal44.png"))); // NOI18N
        btnCortarSrvicio.setToolTipText("Cortar Servicio");
        btnCortarSrvicio.setOpaque(false);
        btnCortarSrvicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCortarSrvicioActionPerformed(evt);
            }
        });
        jPanel4.add(btnCortarSrvicio);

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/arrow_refresh.png"))); // NOI18N
        jButton4.setToolTipText("Refresh");
        jButton4.setOpaque(false);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton4);

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/cancel.png"))); // NOI18N
        btnSalir.setToolTipText("Salir");
        btnSalir.setOpaque(false);
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        jPanel4.add(btnSalir);

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 50, -1, 170));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(153, 153, 153)), "Buscar por:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.setOpaque(false);

        cboBusCampo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Nombre", "Apellido" }));
        cboBusCampo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboBusCampoActionPerformed(evt);
            }
        });

        txtBuscar.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txtBuscar.setForeground(new java.awt.Color(255, 255, 255));
        txtBuscar.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtBuscar.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(153, 153, 153)));
        txtBuscar.setCaretColor(new java.awt.Color(102, 255, 0));
        txtBuscar.setOpaque(false);
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cboBusCampo, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(228, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboBusCampo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 560, 70));

        jScrollPane2.setMinimumSize(new java.awt.Dimension(650, 700));
        jScrollPane2.setOpaque(false);

        tblVista.setBackground(new java.awt.Color(51, 51, 51));
        tblVista.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        tblVista.setForeground(new java.awt.Color(255, 255, 255));
        tblVista.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblVista.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblVista.setOpaque(false);
        tblVista.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblVistaMouseClicked(evt);
            }
        });
        tblVista.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblVistaKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(tblVista);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 660, 290));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/Encabezados/corte2.png"))); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 0, 700, 40));

        lblTotal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTotal.setForeground(new java.awt.Color(255, 255, 0));
        getContentPane().add(lblTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 530, 110, 20));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Total Registros : ");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 530, -1, -1));

        jLabel3.setBackground(new java.awt.Color(153, 0, 0));
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/Encabezados/ftn1.png"))); // NOI18N
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 730, 550));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCortarSrvicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCortarSrvicioActionPerformed
        if (id != -1) {
            int i = JOptionPane.showConfirmDialog(this, "confirmar Accion", "MENSAJE DE CONFIRMACION", JOptionPane.OK_OPTION, JOptionPane.NO_OPTION);

            if (i == 0) {
                boolean f = clienteDao.cortarServicio(id);
                if (f == true) {

                    txtBuscar.setText(null);
                } else {
                    Mensaje.panelSms("No se ejecuto el Corte de servicio/n seleccione un cliente");
                }
            } else {
                txtBuscar.setText(null);
            }

            listar();
        } else {
            Mensaje.panelSms("No se ejecuto el Corte de servicio/n seleccione un cliente");
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCortarSrvicioActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        txtBuscar.setText(null);
        cboBusCampo.setSelectedIndex(0);
        listar();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSalirActionPerformed

    private void cboBusCampoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboBusCampoActionPerformed
        txtBuscar.setText(null);
        int x = cboBusCampo.getSelectedIndex();
        if (x == 2) {
            txtBuscar.setText(null);
            listarBusqueda(x, txtBuscar.getText().trim());
            txtBuscar.requestFocus();
        } else {
            listarBusqueda(x, txtBuscar.getText().trim());
            txtBuscar.requestFocus();
        }

        //txtPrueva.setText("index = "+x);
        // TODO add your handling code here:
    }//GEN-LAST:event_cboBusCampoActionPerformed

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
        int k = clienteDao.listarCliCortSE().size();
        if (k > 0) {
            int x = cboBusCampo.getSelectedIndex();
            listarBusqueda(x, txtBuscar.getText().trim());
            if (llave == true) {
                txtBuscar.setText(Cadena.eliminarUltCaracter(txtBuscar.getText().trim()));
            }
        } else {
            txtBuscar.setText(null);

        }

        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarKeyReleased

    private void tblVistaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblVistaMouseClicked

        if (evt.getClickCount() == 2) {
            int i = tblVista.getSelectedRow();
            id = (int) tblVista.getValueAt(i, 0);
            cliente = tblVista.getValueAt(i, 1).toString() + " " + tblVista.getValueAt(i, 2).toString();
            txtCliente.setText(cliente);
            txtServidor.setText(tblVista.getValueAt(i, 5).toString());

        }

        // TODO add your handling code here:
    }//GEN-LAST:event_tblVistaMouseClicked

    private void tblVistaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblVistaKeyPressed

        int k = tblVista.getSelectedRow();
        if (evt.getKeyCode() == 38) {
            k = k - 1;
            if (k >= 0) {

                id = (int) tblVista.getValueAt(k, 0);
                cliente = tblVista.getValueAt(k, 1).toString() + " " + tblVista.getValueAt(k, 2).toString();
                txtCliente.setText(cliente);
                txtServidor.setText(tblVista.getValueAt(k, 5).toString());
            }

        } else if (evt.getKeyCode() == 40) {
            k = k + 1;
            if (k < tblVista.getRowCount()) {
                id = (int) tblVista.getValueAt(k, 0);
                cliente = tblVista.getValueAt(k, 1).toString() + " " + tblVista.getValueAt(k, 2).toString();
                txtCliente.setText(cliente);
                txtServidor.setText(tblVista.getValueAt(k, 5).toString());

            }

        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tblVistaKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCortarSrvicio;
    private javax.swing.JButton btnSalir;
    private javax.swing.JComboBox cboBusCampo;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JTable tblVista;
    public transient javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtCliente;
    private javax.swing.JTextField txtServidor;
    // End of variables declaration//GEN-END:variables
}
