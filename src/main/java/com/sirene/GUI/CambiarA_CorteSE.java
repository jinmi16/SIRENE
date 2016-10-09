package com.sirene.GUI;

import com.sirene.Dao.ClienteDao;
//import com.sirene.Dao.Impl.DaoCliente;
import com.sirene.Dao.Impl.DaoFactory;
import com.sirene.Dao.Impl.Mensaje;
import com.sirene.Utilitarios.Cadena;
import static com.sirene.Utilitarios.Constantes.CLIENTE;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class CambiarA_CorteSE extends javax.swing.JInternalFrame {

    DefaultTableModel modTabla;
    String cliente;

    int id = -1;
    boolean llave = false;
public final ClienteDao clienteDao;
    public CambiarA_CorteSE() {
        initComponents();
        
DaoFactory factory = DaoFactory.getInstance();
        clienteDao=factory.getClienteDao(CLIENTE);

        localisar(624, 10);
        setTitle("DE CORTE EJECUTADO A CORTE SIN EJECUTAR");
        transparenciaTabla();
        listar(4);

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

   

    private void listar(int es) {
        int longitud = clienteDao.listarClienteEstado(es).size();
        if (longitud > 0) {
            //llave = false;

            Object[] colum = {"Id", "Nombre", "Apellido", "estado", "F_vencimiento", "F_corte", "Tarifa","F_Corte Ejecutado"};
            modTabla = new DefaultTableModel(null, colum) {

                public boolean isCellEditable(int row, int colum) {
                    return false;
                }
            };
            for (Object[] fila : clienteDao.listarClienteEstado(es)) {

                modTabla.addRow(fila);

            }
            tblVista.setModel(modTabla);

            anchoColum();
            cliente = tblVista.getValueAt(0, 1).toString() + " " + tblVista.getValueAt(0, 2).toString();
            txtCliente.setText(cliente);
            id = Integer.parseInt(tblVista.getValueAt(0, 0).toString());

        } else {
            //llave = true;

            modTabla = new DefaultTableModel();
            tblVista.setModel(modTabla);
            //Mensaje.panelSms("No hay clientes con estado CORTE SIN EJECUTAR");
            id = -1;
            txtCliente.setText(null);
        }

        lblTotal.setText(" " + longitud);
    }

    public void listarBusqueda(int es, String cadena, int x) {

        Object[] colum = {"Id", "Nombre", "Apellido", "estado", "F_vencimiento", "F_corte", "Tarifa","F_Corte Ejecutado"};
        modTabla = new DefaultTableModel(null, colum) {

            public boolean isCellEditable(int row, int colum) {
                return false;
            }
        };
        int longitud = clienteDao.buscarClienteEstado(es, cadena, x).size();
        if (longitud > 0) {
            llave = false;

            for (Object[] fila : clienteDao.buscarClienteEstado(es, cadena, x)) {

                modTabla.addRow(fila);

            }
            tblVista.setModel(modTabla);

            anchoColum();
            cliente = tblVista.getValueAt(0, 1).toString() + " " + tblVista.getValueAt(0, 2).toString();
            txtCliente.setText(cliente);
            id = Integer.parseInt(tblVista.getValueAt(0, 0).toString());

            lblTotal.setText(" " + longitud);
        } else {
            llave = true;
            txtCliente.setText(null);
              tblVista.setModel(modTabla);
             lblTotal.setText(" " + longitud);
             
           // Mensaje.panelSms("No se encontraron Registros");
            //txtBuscar.setText(null);
            //btnSalir.requestFocus();
            //CK0.setSelected(true);
            //listar(0);
        }

    }
    public void regresarAcorteSE(){
        if(id!=-1){
        clienteDao.regresarAcorteSE(id);
       
        
        }else{
        Mensaje.panelSms("Seleccione un cliente");
        
        }
    
    
    }

    public void anchoColum() {
        tblVista.getColumnModel().getColumn(0).setMinWidth(25);
        tblVista.getColumnModel().getColumn(0).setMaxWidth(30);
        tblVista.getColumnModel().getColumn(1).setMinWidth(100);
        tblVista.getColumnModel().getColumn(1).setMaxWidth(110);
        tblVista.getColumnModel().getColumn(2).setMinWidth(100);
        tblVista.getColumnModel().getColumn(2).setMaxWidth(110);
        tblVista.getColumnModel().getColumn(3).setMinWidth(110);
        tblVista.getColumnModel().getColumn(3).setMaxWidth(120);
        tblVista.getColumnModel().getColumn(4).setMinWidth(90);
        tblVista.getColumnModel().getColumn(4).setMaxWidth(100);
        tblVista.getColumnModel().getColumn(5).setMinWidth(90);
        tblVista.getColumnModel().getColumn(5).setMaxWidth(100);
        tblVista.getColumnModel().getColumn(6).setMinWidth(50);
        tblVista.getColumnModel().getColumn(6).setMaxWidth(70);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        cboBusCampo = new javax.swing.JComboBox();
        txtBuscar = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtCliente = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblVista = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(0, 0, 51));
        setClosable(true);
        setIconifiable(true);
        setMinimumSize(new java.awt.Dimension(619, 719));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Buscar por:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(204, 204, 204))); // NOI18N
        jPanel1.setOpaque(false);

        cboBusCampo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Nombre", "Apellido" }));
        cboBusCampo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboBusCampoActionPerformed(evt);
            }
        });

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
                .addGap(72, 72, 72)
                .addComponent(txtBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
                .addGap(34, 34, 34)
                .addComponent(cboBusCampo, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(95, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboBusCampo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 481, 80));

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setOpaque(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 255, 0));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/cliente35.png"))); // NOI18N
        jLabel1.setText("Cliente");

        txtCliente.setBackground(new java.awt.Color(204, 255, 0));
        txtCliente.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txtCliente.setForeground(new java.awt.Color(204, 255, 204));
        txtCliente.setOpaque(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel1)
                .addComponent(txtCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 188, 481, -1));

        jScrollPane2.setMinimumSize(new java.awt.Dimension(650, 700));
        jScrollPane2.setOpaque(false);

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

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 233, 583, 390));

        jPanel4.setOpaque(false);
        jPanel4.setLayout(new java.awt.GridLayout(3, 1, 0, 2));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/arrow_left.png"))); // NOI18N
        jButton1.setToolTipText("REGRESAR A ESTADO_CORTE SIN EJECUTAR");
        jButton1.setOpaque(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton1);

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

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(509, 71, -1, 150));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 255, 0));
        jLabel4.setText("Total Registros");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 641, -1, -1));

        lblTotal.setBackground(new java.awt.Color(255, 255, 255));
        lblTotal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTotal.setOpaque(true);
        getContentPane().add(lblTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(124, 641, 82, 17));
        getContentPane().add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 629, 583, 10));

        jLabel2.setBackground(new java.awt.Color(102, 102, 102));
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("REGRESAR DE CORTE EJECUTADO  A  CORTE SIN EJECUTAR");
        jLabel2.setOpaque(true);
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 610, 50));

        jLabel3.setBackground(new java.awt.Color(0, 0, 51));
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/Encabezados/ftn1.png"))); // NOI18N
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 34, 620, 690));

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

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        txtBuscar.setText(null);
        cboBusCampo.setSelectedIndex(0);
        listar(4);
        
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSalirActionPerformed

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
        int x = cboBusCampo.getSelectedIndex();
        listarBusqueda(4, txtBuscar.getText().trim(), x);
        if (llave == true) {
            txtBuscar.setText(Cadena.eliminarUltCaracter(txtBuscar.getText().trim()));
             listarBusqueda(4, txtBuscar.getText().trim(), x);
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarKeyReleased

    private void cboBusCampoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboBusCampoActionPerformed
        txtBuscar.setText(null);
        int x = cboBusCampo.getSelectedIndex();

        listarBusqueda(4, txtBuscar.getText().trim(), x);
        txtBuscar.requestFocus();

        // TODO add your handling code here:
    }//GEN-LAST:event_cboBusCampoActionPerformed

    private void tblVistaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblVistaKeyPressed
        int k = tblVista.getSelectedRow();
        if (evt.getKeyCode() == 38) {
            k = k - 1;
            if (k >= 0) {

                //int i = tblVista.getSelectedRow();
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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
regresarAcorteSE();
txtBuscar.setText(null);
        listar(4);
        
// TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSalir;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cboBusCampo;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JTable tblVista;
    public transient javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtCliente;
    // End of variables declaration//GEN-END:variables
}
