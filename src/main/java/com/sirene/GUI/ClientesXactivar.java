package com.sirene.GUI;

import com.sirene.Dao.Impl.DaoFactory;
//import com.sirene.Dao.Impl.DaoPago;
import com.sirene.Dao.Impl.Mensaje;
import com.sirene.Dao.PagoDao;
import static com.sirene.Utilitarios.Constantes.PAGO;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class ClientesXactivar extends javax.swing.JInternalFrame {

    DefaultTableModel modTabla;
    int idPago = -1;
 public final PagoDao pagoDao;
    public ClientesXactivar() {
        initComponents();
       
DaoFactory factory = DaoFactory.getInstance();
        pagoDao=factory.getPagoDao(PAGO);

        localisar(430,10);
        listarCliXctivar();
        transparenciaTabla();

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
    private void localisar(int x, int y) {
        int w_screen = getToolkit().getScreenSize().width;
        int k = (w_screen / 2) - (x / 2);
        setLocation(k, y);

    }

    private void listarCliXctivar() {
        Object[] colum = {"F-Pagada", "cliente", "Servidor", "ID/Pago"};
        //codigo para que la tabla no sea editable
        modTabla = new DefaultTableModel(null, colum) {

            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        int k = pagoDao.clientesXactivar().size();
        if (k > 0) {

            for (Object[] f : pagoDao.clientesXactivar()) {
                modTabla.addRow(f);

            }
            tblVista.setModel(modTabla);
            lblTotal.setText(" " + k);
            mostrarCliente(0);

            tblVista.getColumnModel().getColumn(0).setMinWidth(80);
            tblVista.getColumnModel().getColumn(0).setMaxWidth(90);
            tblVista.getColumnModel().getColumn(1).setMinWidth(200);
            tblVista.getColumnModel().getColumn(1).setMaxWidth(240);
            tblVista.getColumnModel().getColumn(2).setMinWidth(90);
            tblVista.getColumnModel().getColumn(2).setMaxWidth(100);
            tblVista.getColumnModel().getColumn(3).setMinWidth(40);
            tblVista.getColumnModel().getColumn(3).setMaxWidth(50);
        } else {
            tblVista.setModel(modTabla);
            lblTotal.setText(" " + k);
            idPago = -1;
            txtCliente.setText(null);
            txtServidor.setText(null);
        }

    }

    public void activar() {
        if (idPago != -1) {

            pagoDao.activarCliente(idPago);
            Mensaje.panelSms("CLIENTE ACTIVADO");
        } else {

            Mensaje.panelSms("NO HAY CLIENTES POR ACTIVAR SERVICIO");
        }

    }

    private void mostrarCliente(int f) {
        if (f != -1) {
            idPago = (int) modTabla.getValueAt(f, 3);
            txtCliente.setText(modTabla.getValueAt(f, 1).toString());
            txtServidor.setText(modTabla.getValueAt(f, 2).toString());
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        tblVista = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtCliente = new javax.swing.JTextField();
        txtServidor = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane2.setMinimumSize(new java.awt.Dimension(390, 700));
        jScrollPane2.setOpaque(false);
        jScrollPane2.setPreferredSize(new java.awt.Dimension(390, 402));

        tblVista.setBackground(new java.awt.Color(51, 51, 51));
        tblVista.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
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

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 200, 370, 240));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Total registros");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 450, 100, -1));

        lblTotal.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblTotal.setForeground(new java.awt.Color(255, 255, 255));
        getContentPane().add(lblTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 450, 70, 14));

        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(390, 102));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Servidor");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Cliente");

        txtCliente.setEditable(false);
        txtCliente.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtCliente.setForeground(new java.awt.Color(51, 255, 0));
        txtCliente.setOpaque(false);

        txtServidor.setEditable(false);
        txtServidor.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtServidor.setForeground(new java.awt.Color(51, 255, 51));
        txtServidor.setOpaque(false);
        txtServidor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtServidorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtServidor, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 81, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(txtCliente)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtServidor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(48, 48, 48))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, 290, 70));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/accept.png"))); // NOI18N
        jButton1.setToolTipText("CONFIRMAR ACTIVACION");
        jButton1.setOpaque(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 110, -1, -1));

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/cancel.png"))); // NOI18N
        jButton2.setToolTipText("CANCELAR");
        jButton2.setOpaque(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 150, -1, -1));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/Encabezados/cliActivar.png"))); // NOI18N
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(-20, -10, 440, 70));

        jLabel4.setBackground(new java.awt.Color(51, 51, 51));
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/Encabezados/ftn1.png"))); // NOI18N
        jLabel4.setOpaque(true);
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 10, 430, 500));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblVistaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblVistaMouseClicked

        if (evt.getClickCount() == 2) {
            int i = tblVista.getSelectedRow();
            mostrarCliente(i);

        }

        // TODO add your handling code here:
    }//GEN-LAST:event_tblVistaMouseClicked

    private void tblVistaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblVistaKeyPressed
        int k = tblVista.getSelectedRow();
        if (evt.getKeyCode() == 38) {
            k = k - 1;
            if (k >= 0) {

                mostrarCliente(k);
            }

        } else if (evt.getKeyCode() == 40) {
            k = k + 1;
            if (k < tblVista.getRowCount()) {
                mostrarCliente(k);
            }

        }

        // TODO add your handling code here:
    }//GEN-LAST:event_tblVistaKeyPressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        activar();
        listarCliXctivar();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtServidorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtServidorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtServidorActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JTable tblVista;
    private javax.swing.JTextField txtCliente;
    private javax.swing.JTextField txtServidor;
    // End of variables declaration//GEN-END:variables
}
