package com.sirene.GUI;

import com.sirene.Bean.Cliente;
import com.sirene.Dao.ClienteDao;
//import com.sirene.Dao.Impl.DaoCliente;
import com.sirene.Dao.Impl.DaoFactory;
import com.sirene.Dao.Impl.Mensaje;
import com.sirene.Utilitarios.Cadena;
import static com.sirene.Utilitarios.Constantes.CLIENTE;
import java.awt.event.KeyEvent;
import javax.swing.table.DefaultTableModel;

public class BuscaClienteCongelado extends javax.swing.JDialog {

    DefaultTableModel modTabla;
    String cliente;
    boolean llave1 = false;
    int id = -1;
 public final ClienteDao clienteDao;
    public BuscaClienteCongelado(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
       
DaoFactory factory = DaoFactory.getInstance();
        clienteDao=factory.getClienteDao(CLIENTE);

        transparenciaTabla();
        setLocationRelativeTo(null);
        listarBusqueda(0, txtBuscar.getText().trim());
        txtBuscar.requestFocus();

    }

    private void transparenciaTabla() {
        //La mesa será transparente si ni ella ni las células son opacas:
        //tblVista.setOpaque(false); 
        //((DefaultTableCellRenderer)tblVista.getDefaultRenderer(Object.class)).setOpaque(false);
        //Si la tabla está en un ScrollPane , que es hacer transparente así
        jScrollPane2.setOpaque(false);
        jScrollPane2.getViewport().setOpaque(false);
    //Por lo menos, usted puede quitar las líneas de la cuadrícula:
        // tblVista.setShowGrid(false);

    }

    private void listarBusqueda(int x, String cadena) {
        int k =  clienteDao.BuscarClientesActivos(x, txtBuscar.getText().trim(),7).size();
        if (k > 0) {

            Object[] colum = {"Id", "Nombre", "Apellido", "Dni"};
            modTabla = new DefaultTableModel(null, colum) {

                public boolean isCellEditable(int row, int colum) {
                    return false;
                }
            };
            for (Cliente c :  clienteDao.BuscarClientesActivos(x, txtBuscar.getText().trim(),7)) {
                Object[] fila = {c.getIdCliente(), c.getNombre(), c.getApellido(), c.getDni()};
                modTabla.addRow(fila);

            }
            tblVista.setModel(modTabla);

            anchoColum();
            cliente = tblVista.getValueAt(0, 1).toString() + " " + tblVista.getValueAt(0, 2).toString();
            txtCliente.setText(cliente);
            id = Integer.parseInt(tblVista.getValueAt(0, 0).toString());
            lblTotal.setText("" + k);
        } else {

            lblTotal.setText("" + k);
            // txtBuscar.setText(null);
            txtCliente.setText(null);
            Mensaje.panelSms("NO SE ENCONTRARON REGISTROS");
            //txtBuscar.requestFocus();

        }

    }

    public void anchoColum() {
        tblVista.getColumnModel().getColumn(0).setMinWidth(25);
        tblVista.getColumnModel().getColumn(0).setMaxWidth(30);
        tblVista.getColumnModel().getColumn(1).setMinWidth(100);
        tblVista.getColumnModel().getColumn(1).setMaxWidth(110);
        tblVista.getColumnModel().getColumn(2).setMinWidth(100);
        tblVista.getColumnModel().getColumn(2).setMaxWidth(110);
        tblVista.getColumnModel().getColumn(2).setMinWidth(80);
        tblVista.getColumnModel().getColumn(2).setMaxWidth(100);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtCliente = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblVista = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        cboBusCampo = new javax.swing.JComboBox();
        txtBuscar = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        lblTotal = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setOpaque(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Cliente");

        txtCliente.setEditable(false);
        txtCliente.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txtCliente.setForeground(new java.awt.Color(255, 255, 255));
        txtCliente.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCliente.setOpaque(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, -1));

        jScrollPane2.setMinimumSize(new java.awt.Dimension(650, 700));
        jScrollPane2.setOpaque(false);

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

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 157, 301, 290));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(102, 102, 102)), "Buscar por:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(204, 204, 204))); // NOI18N
        jPanel1.setOpaque(false);

        cboBusCampo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Nombre", "Apellido", "Dni" }));
        cboBusCampo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboBusCampoActionPerformed(evt);
            }
        });

        txtBuscar.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txtBuscar.setForeground(new java.awt.Color(255, 255, 51));
        txtBuscar.setCaretColor(new java.awt.Color(255, 255, 255));
        txtBuscar.setOpaque(false);
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBuscarKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(cboBusCampo, 0, 102, Short.MAX_VALUE)
                .addGap(28, 28, 28)
                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboBusCampo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 23, -1, 70));

        jPanel3.setOpaque(false);
        jPanel3.setLayout(new java.awt.GridLayout(1, 2, 6, 0));

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/cancel.png"))); // NOI18N
        jButton2.setToolTipText("CANCELAR");
        jButton2.setOpaque(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton2);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/accept.png"))); // NOI18N
        jButton1.setToolTipText("ACEPTAR");
        jButton1.setOpaque(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton1);

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 490, 198, 50));

        lblTotal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTotal.setForeground(new java.awt.Color(255, 255, 255));
        getContentPane().add(lblTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 450, 70, 20));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(204, 204, 204));
        jLabel4.setText("Total registros :");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 450, -1, -1));

        jLabel2.setBackground(new java.awt.Color(0, 51, 51));
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/Encabezados/ftn1.png"))); // NOI18N
        jLabel2.setOpaque(true);
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(-1, -6, 330, 560));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(204, 204, 204));
        jLabel5.setText("Total registros :");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 420, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblVistaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblVistaMouseClicked

        if (evt.getClickCount() == 2) {
            int i = tblVista.getSelectedRow();
            id = (int) tblVista.getValueAt(i, 0);
            cliente = tblVista.getValueAt(i, 1).toString() + " " + tblVista.getValueAt(i, 2).toString();
            txtCliente.setText(cliente);

        }

        // TODO add your handling code here:
    }//GEN-LAST:event_tblVistaMouseClicked

    private void cboBusCampoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboBusCampoActionPerformed
        txtBuscar.setText(null);
        int x = cboBusCampo.getSelectedIndex();
       
        if (x == 2) {
            txtBuscar.setText(null);
            listarBusqueda(x,txtBuscar.getText().trim());
            txtBuscar.requestFocus();
        } else {
             listarBusqueda(x,txtBuscar.getText().trim());
            txtBuscar.requestFocus();
        }

//txtPrueva.setText("index = "+x);
        // TODO add your handling code here:
    }//GEN-LAST:event_cboBusCampoActionPerformed

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
        int x = cboBusCampo.getSelectedIndex();
       
        
        int k =  clienteDao.BuscarClientesActivos(x, txtBuscar.getText().trim(),7).size();
        if (k > 0) {
           listarBusqueda(x,txtBuscar.getText().trim());

        } else {
            txtBuscar.setText(Cadena.eliminarUltCaracter(txtBuscar.getText().trim()));
            lblTotal.setText("" + k);
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarKeyReleased

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        DescongelarServicioCliente.idcliente = id;
        //getTransferenciaDatos();
        DescongelarServicioCliente.txtPago.setText(txtCliente.getText());
         DescongelarServicioCliente.fechaIS();
        DescongelarServicioCliente.llenarCampos();
       
        DescongelarServicioCliente.f=true;
        //CongelarServicioCliente.fecha();

        dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            Ventas.idcliente = id;
            // getTransferenciaDatos();
            Ventas.txtPago.setText(txtCliente.getText());
            Ventas.llenarCampos();
            dispose();

        }
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarKeyPressed

    private void tblVistaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblVistaKeyPressed
        int k = tblVista.getSelectedRow();
        if (evt.getKeyCode() == 38) {
            k = k - 1;
            if (k >= 0) {
                
                id = (int) tblVista.getValueAt(k, 0);
                cliente = tblVista.getValueAt(k, 1).toString() + " " + tblVista.getValueAt(k, 2).toString();
                txtCliente.setText(cliente);

            }

        } else if (evt.getKeyCode() == 40) {
            k = k + 1;
            if (k < tblVista.getRowCount()) {
                 id = (int) tblVista.getValueAt(k, 0);
                cliente = tblVista.getValueAt(k, 1).toString() + " " + tblVista.getValueAt(k, 2).toString();
                txtCliente.setText(cliente);

            }

        }

// TODO add your handling code here:
    }//GEN-LAST:event_tblVistaKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BuscaClienteCongelado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BuscaClienteCongelado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BuscaClienteCongelado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BuscaClienteCongelado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /*
         * Create and display the dialog
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                BuscaClienteCongelado dialog = new BuscaClienteCongelado(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cboBusCampo;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JTable tblVista;
    public transient javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtCliente;
    // End of variables declaration//GEN-END:variables
}
