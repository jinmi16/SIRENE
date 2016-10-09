package com.sirene.GUI;

import com.sirene.Bean.Servidor;
import com.sirene.Bean.Torre;
import com.sirene.Dao.AntenaDao;
import com.sirene.Dao.Impl.DaoFactory;
//import com.sirene.Dao.Impl.DaoAntena;
//import com.sirene.Dao.Impl.DaoServidor;
//import com.sirene.Dao.Impl.DaoTorre;
import com.sirene.Dao.Impl.Mensaje;
import com.sirene.Dao.ServidorDao;
import com.sirene.Dao.TorreDao;
import com.sirene.Utilitarios.Cadena;
import static com.sirene.Utilitarios.Constantes.ANTENA;
import static com.sirene.Utilitarios.Constantes.SERVIDOR;
import static com.sirene.Utilitarios.Constantes.TORRE;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class BuscarAntenaBC extends javax.swing.JDialog {

    DefaultTableModel modTabla;
    DefaultComboBoxModel modCboS = new DefaultComboBoxModel();
    DefaultComboBoxModel modCboT = new DefaultComboBoxModel();
    ArrayList<Servidor> lstServ;
    ArrayList<Torre> lstTorre;
    JTextField ant;
    boolean llave = false;

    String antena = "";
    public final AntenaDao antenaDao;
    public final ServidorDao servidorDao;
    public final TorreDao torreDao;

    public BuscarAntenaBC(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        DaoFactory factory = DaoFactory.getInstance();
        antenaDao = factory.getAntenaDao(ANTENA);
        servidorDao = factory.getServidorDao(SERVIDOR);
        torreDao=factory.getTorreDao(TORRE);


        setLocationRelativeTo(null);
        transparenciaTabla();
        listarAntena();
        listaTorre();
        listaServidores();
        txtNomBus.requestFocus();
        controles();

    }

    private void transparenciaTabla() {
        //La mesa será transparente si ni ella ni las células son opacas:
        tblVista.setOpaque(false);
        ((DefaultTableCellRenderer) tblVista.getDefaultRenderer(Object.class)).setOpaque(false);
        //Si la tabla está en un ScrollPane , que es hacer transparente así
        jScrollPane1.setOpaque(false);
        jScrollPane1.getViewport().setOpaque(false);
        //Por lo menos, usted puede quitar las líneas de la cuadrícula:
        // tblVista.setShowGrid(false);

    }

    public void limpiar() {
//        txtId.setText(null);
        txtServidor.setText(null);
        txtAntena.setText(null);
        txtTorre.setText(null);

    }

    private void controles() {
        // txtId.setEditable(false);
        txtAntena.setEditable(false);
        txtServidor.setEditable(false);
        txtTorre.setEditable(false);

    }

    private void listarAntena() {
        ArrayList<Object[]> listAntena = antenaDao.listarBusAntena();
        Object[] colum = {"Id", "Nombre", "Torre", "Servidor"};
        modTabla = new DefaultTableModel(null, colum) {

            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        int k = listAntena.size();
        if (k > 0) {
            for (Object[] r : listAntena) {
                Object[] fila = {r[0], r[1], r[2], r[3]};
                modTabla.addRow(fila);

            }
            tblVista.setModel(modTabla);
            anchoColum();
            // txtId.setText(tblVista.getValueAt(0, 0).toString());
            txtAntena.setText(tblVista.getValueAt(0, 1).toString());
            txtTorre.setText(tblVista.getValueAt(0, 2).toString());
            txtServidor.setText(tblVista.getValueAt(0, 3).toString());

        } else {
            tblVista.setModel(modTabla);

        }

    }

    public String consulta() {
        String consulta = "";
        String t = "";
        String s = "";
        if (modCboT.getSelectedItem().toString().equals("todos")) {
            t = " AND t.nombreTorre LIKE '%'";
        } else {

            t = " AND t.nombreTorre='" + modCboT.getSelectedItem().toString() + "'";
        }

        if (modCboS.getSelectedItem().toString().equals("todos")) {
            s = " AND s.nombreServidor like '%'";
        } else {
            s = " AND s.nombreServidor='" + modCboS.getSelectedItem().toString() + "'";

        }
        consulta = t + s;
        return consulta;
    }

    private void listarAntenaBusqueda() {

        // ArrayList<Object[]> listAntena = DaoAntena.BusAntXnom_torr_serv(txtNomBus.getText().trim(), modCboT.getSelectedItem().toString(), modCboS.getSelectedItem().toString());
        int k = antenaDao.BusAntXnom_torr_serv2(txtNomBus.getText().trim(), consulta()).size();
        if (k > 0) {
            llave = false;
            ArrayList<Object[]> listAntena = antenaDao.BusAntXnom_torr_serv2(txtNomBus.getText().trim(), consulta());
            Object[] colum = {"Id", "Nombre", "Torre", "Servidor"};
            modTabla = new DefaultTableModel(null, colum) {

                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            for (Object[] r : listAntena) {
                Object[] fila = {r[0], r[1], r[2], r[3]};
                modTabla.addRow(fila);

            }
            tblVista.setModel(modTabla);
            anchoColum();
            //txtId.setText(tblVista.getValueAt(0, 0).toString());
            txtAntena.setText(tblVista.getValueAt(0, 1).toString());
            txtTorre.setText(tblVista.getValueAt(0, 2).toString());
            txtServidor.setText(tblVista.getValueAt(0, 3).toString());
            lblTotal.setText(" " + k);

        } else {
            llave = true;
            while (modTabla.getRowCount() > 0) {
                modTabla.removeRow(0);
            }
            lblTotal.setText(" " + k);
            Mensaje.panelSms("NO SE ENCONTRARON REGISTROS");
        }

    }

    public void anchoColum() {
        tblVista.getColumnModel().getColumn(0).setMinWidth(30);
        tblVista.getColumnModel().getColumn(0).setMaxWidth(40);
        tblVista.getColumnModel().getColumn(1).setMinWidth(120);
        tblVista.getColumnModel().getColumn(1).setMaxWidth(140);
        tblVista.getColumnModel().getColumn(2).setMinWidth(120);
        tblVista.getColumnModel().getColumn(2).setMaxWidth(140);
        tblVista.getColumnModel().getColumn(3).setMinWidth(120);
        tblVista.getColumnModel().getColumn(3).setMaxWidth(140);

    }

    private void listaServidores() {
        modCboS.addElement("todos");
        lstServ = servidorDao.listarNombre();
        for (Servidor se : lstServ) {
            modCboS.addElement(se.getNomServ());

        }
        cboServidor.setModel(modCboS);
    }

    private void listaTorre() {
        modCboT.addElement("todos");
        lstTorre = torreDao.listarNobre();
        for (Torre to : lstTorre) {
            modCboT.addElement(to.getNombre());
        }
        cboTorre.setModel(modCboT);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtAntena = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtTorre = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtServidor = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblVista = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtNomBus = new javax.swing.JTextField();
        cboTorre = new javax.swing.JComboBox();
        cboServidor = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setOpaque(false);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Antena");

        txtAntena.setEditable(false);
        txtAntena.setBackground(new java.awt.Color(255, 255, 204));
        txtAntena.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txtAntena.setForeground(new java.awt.Color(255, 204, 0));
        txtAntena.setOpaque(false);
        txtAntena.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAntenaActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Servidor");

        txtTorre.setEditable(false);
        txtTorre.setBackground(new java.awt.Color(255, 255, 204));
        txtTorre.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txtTorre.setForeground(new java.awt.Color(255, 204, 0));
        txtTorre.setOpaque(false);
        txtTorre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTorreActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Torre");

        txtServidor.setEditable(false);
        txtServidor.setBackground(new java.awt.Color(255, 255, 204));
        txtServidor.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txtServidor.setForeground(new java.awt.Color(255, 204, 0));
        txtServidor.setOpaque(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtAntena, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 68, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtServidor, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
                    .addComponent(txtTorre))
                .addGap(23, 23, 23))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtAntena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTorre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtServidor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 111, 398, -1));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/cancel.png"))); // NOI18N
        jButton1.setText("Cancelar");
        jButton1.setOpaque(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(172, 421, -1, -1));

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/accept.png"))); // NOI18N
        jButton2.setText("Aceprar");
        jButton2.setOpaque(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(301, 421, -1, -1));

        jScrollPane1.setMinimumSize(new java.awt.Dimension(650, 700));
        jScrollPane1.setOpaque(false);

        tblVista.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
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
        jScrollPane1.setViewportView(tblVista);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 195, 400, 220));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(153, 153, 153)), "Buscar por:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(204, 204, 204))); // NOI18N
        jPanel2.setOpaque(false);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Antena");

        txtNomBus.setBackground(new java.awt.Color(204, 204, 204));
        txtNomBus.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtNomBus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomBusActionPerformed(evt);
            }
        });
        txtNomBus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNomBusKeyReleased(evt);
            }
        });

        cboTorre.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        cboTorre.setFocusable(false);
        cboTorre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTorreActionPerformed(evt);
            }
        });

        cboServidor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboServidorActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Torre");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Servidor");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(89, 89, 89)
                        .addComponent(jLabel6)
                        .addGap(98, 98, 98)
                        .addComponent(jLabel7))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtNomBus, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(cboTorre, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14)
                        .addComponent(cboServidor, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addGap(6, 6, 6)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNomBus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboTorre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboServidor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 12, 398, 90));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Total items");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 421, -1, -1));

        lblTotal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTotal.setForeground(new java.awt.Color(255, 255, 255));
        getContentPane().add(lblTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 420, 50, 20));

        jLabel9.setBackground(new java.awt.Color(0, 51, 51));
        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/Encabezados/ftn1.png"))); // NOI18N
        jLabel9.setText("jLabel9");
        jLabel9.setOpaque(true);
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -6, 420, 490));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtAntenaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAntenaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAntenaActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        dispose();

        //System.out.println(consulta());
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        RegCliente.txtAntena.setText(txtAntena.getText());
        limpiar();
        dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void tblVistaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblVistaMouseClicked
        if (evt.getClickCount() == 2) {
            int i = tblVista.getSelectedRow();
            // txtId.setText(tblVista.getValueAt(i, 0).toString());
            txtAntena.setText(tblVista.getValueAt(i, 1).toString());
            txtServidor.setText(tblVista.getValueAt(i, 3).toString());
            txtTorre.setText(tblVista.getValueAt(i, 2).toString());
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_tblVistaMouseClicked

    private void txtNomBusKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomBusKeyReleased

        listarAntenaBusqueda();
        if (llave == true) {
            txtNomBus.setText(Cadena.eliminarUltCaracter(txtNomBus.getText().trim()));

        }
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomBusKeyReleased

    private void cboTorreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTorreActionPerformed
        listarAntenaBusqueda();
        /*    if (f1 == true) {
         listarAntenaBusqueda();
         } else {
         f1 = true;
         }
         */
        // TODO add your handling code here:
    }//GEN-LAST:event_cboTorreActionPerformed

    private void cboServidorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboServidorActionPerformed
        listarAntenaBusqueda();
        /*    if (f2 == true) {
         listarAntenaBusqueda();
         } else {
         f2 = true;
         }
         */
        // TODO add your handling code here:
    }//GEN-LAST:event_cboServidorActionPerformed

    private void txtNomBusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomBusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomBusActionPerformed

    private void txtTorreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTorreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTorreActionPerformed

    private void tblVistaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblVistaKeyPressed
        int k = tblVista.getSelectedRow();
        if (evt.getKeyCode() == 38) {
            k = k - 1;
            if (k >= 0) {

                txtAntena.setText(tblVista.getValueAt(k, 1).toString());
                txtTorre.setText(tblVista.getValueAt(k, 2).toString());
                txtServidor.setText(tblVista.getValueAt(k, 3).toString());

            }

        } else if (evt.getKeyCode() == 40) {
            k = k + 1;
            if (k < tblVista.getRowCount()) {
                txtAntena.setText(tblVista.getValueAt(k, 1).toString());
                txtTorre.setText(tblVista.getValueAt(k, 2).toString());
                txtServidor.setText(tblVista.getValueAt(k, 3).toString());

            }

        }

// TODO add your handling code here:
    }//GEN-LAST:event_tblVistaKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BuscarAntenaBC.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BuscarAntenaBC.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BuscarAntenaBC.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BuscarAntenaBC.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                BuscarAntenaBC dialog = new BuscarAntenaBC(new javax.swing.JFrame(), true);
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
    private javax.swing.JComboBox cboServidor;
    private javax.swing.JComboBox cboTorre;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JTable tblVista;
    private javax.swing.JTextField txtAntena;
    private javax.swing.JTextField txtNomBus;
    private javax.swing.JTextField txtServidor;
    private javax.swing.JTextField txtTorre;
    // End of variables declaration//GEN-END:variables
}
