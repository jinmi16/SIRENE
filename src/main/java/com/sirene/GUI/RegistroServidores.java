/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirene.GUI;

import com.sirene.Bean.Servidor;
import com.sirene.Bean.Usuario;
import com.sirene.Dao.Impl.DaoFactory;
//import com.sirene.Dao.Impl.DaoServidor;
import com.sirene.Dao.Impl.Mensaje;
import com.sirene.Dao.ServidorDao;
import static com.sirene.Utilitarios.Constantes.SERVIDOR;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Jinmi
 */
public class RegistroServidores extends javax.swing.JInternalFrame {

    Usuario usuario;
    DefaultTableModel modTabla;
    ArrayList<Servidor> listServ;
    int id = -1;
    public final ServidorDao servidorDao;

    public RegistroServidores() {
        initComponents();

        DaoFactory factory = DaoFactory.getInstance();
        servidorDao = factory.getServidorDao(SERVIDOR);

        localisar(630, 10);
        setTitle("Registro_Servidor");
        transparenciaTabla();
        listarServ();
        Controller(false);
    }

    private void localisar(int x, int y) {
        int w_screen = getToolkit().getScreenSize().width;
        int k = (w_screen / 2) - (x / 2);
        setLocation(k, y);

    }

    private void Controller(boolean f) {

        btnModif.setEnabled(f);
        btnRegistrar.setEnabled(f);
        btnEliminar.setEnabled(f);

        txtNom.setEditable(f);
        txtIpE.setEditable(f);
        txtIpS.setEditable(f);

    }

    public void habilitarController() {
        if (usuario.getIdTipoUser() == 1) {
            Controller(true);

        } else {
            Controller(false);
            Mensaje.panelSms("NO TIENE EL PRIVILEGIO ADMINISTRATIVO");
            chkB.setSelected(true);
        }

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
        // int k=cboAntenaEnlace.getItemCount();

    }

    public void limpiar() {
        txtNom.setText(null);
        txtIpE.setText(null);
        txtIpS.setText(null);
        id = -1;
    }

    private void listarServ() {
        Object[] colum = {"id", "nombre", "IP_Entrada", "IP_Salida"};
        // con este codigo adicional al modelo de yabla consigo que las celdas y columnas no sean 
        // editables
        modTabla = new DefaultTableModel(null, colum) {

            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        listServ = servidorDao.listar();
        if (listServ.size() > 0) {
            for (Servidor s : listServ) {
                Object[] fila = {s.getIdServ(), s.getNomServ(), s.getIpImput(), s.getIpOutput()};
                modTabla.addRow(fila);
            }

            tblVista.setModel(modTabla);
            anchoColum();
            lblTotal.setText(" " + listServ.size());
        } else {
            tblVista.setModel(modTabla);
            lblTotal.setText(" " + listServ.size());
        }

    }

    private void anchoColum() {

        tblVista.getColumnModel().getColumn(0).setMinWidth(30);
        tblVista.getColumnModel().getColumn(0).setMaxWidth(40);

    }

    public void setUser(Usuario us) {
        usuario = us;

        System.out.println("tipo de usuario  : " + usuario.getIdTipoUser());

    }

    public void registro() {
        if (txtNom.getText().trim().length() == 0) {
            Mensaje.panelSms("LLENE LOS CAMPOS");

        } else {
            servidorDao.registrarServ(txtNom.getText(), txtIpE.getText(), txtIpS.getText());
            limpiar();
        }

    }

    public void actualisar() {
        if (txtNom.getText().trim().length() == 0 || txtIpE.getText().trim().length() == 0 || txtIpS.getText().trim().length() == 0) {
            Mensaje.panelSms("LLENE TODOS LOS CAMPOS");

        } else if (id != -1) {
            Servidor s = new Servidor();
            s.setIdServ(id);
            s.setNomServ(txtNom.getText());
            s.setIpImput(txtIpE.getText());
            s.setIpOutput(txtIpS.getText());
            servidorDao.modificarServer(s);
            id = -1;
        } else {
            Mensaje.panelSms("SELECCIONE UN ITEN EXIXTENTE PARA MODIFICAR");
        }

    }
    //------metodo para que la tabla escuche el evento del click

    private void setEventoMouseClicked(JTable tbl) {
        tbl.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                tblVistaMouseClicked(e);
            }
        });
    }
    //------------------------------------------------

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel4 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtNom = new javax.swing.JTextField();
        txtIpE = new javax.swing.JTextField();
        txtIpS = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        btnRegistrar = new javax.swing.JButton();
        btnModif = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        btnRefrshe = new javax.swing.JButton();
        btnCerrar = new javax.swing.JButton();
        chkB = new javax.swing.JCheckBox();
        chkD = new javax.swing.JCheckBox();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblVista = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        setClosable(true);
        setMinimumSize(new java.awt.Dimension(630, 645));
        setPreferredSize(new java.awt.Dimension(630, 645));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Arial Black", 1, 36)); // NOI18N
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/Encabezados/regServidores.png"))); // NOI18N
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(-20, -10, 690, 90));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(102, 102, 102)), "Registros", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(255, 204, 0))); // NOI18N
        jPanel1.setOpaque(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 204, 0));
        jLabel1.setText("Nombre_servidor(*)");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 204, 0));
        jLabel2.setText("IP_Entrada");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 204, 0));
        jLabel3.setText("IP_Salida");

        txtNom.setBackground(new java.awt.Color(204, 204, 204));
        txtNom.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtNom.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNomKeyTyped(evt);
            }
        });

        txtIpE.setBackground(new java.awt.Color(204, 204, 204));
        txtIpE.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtIpE.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIpEKeyTyped(evt);
            }
        });

        txtIpS.setBackground(new java.awt.Color(204, 204, 204));
        txtIpS.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtIpS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIpSKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtNom, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                    .addComponent(txtIpE)
                    .addComponent(txtIpS))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtNom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtIpE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtIpS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, -1, -1));

        jPanel2.setOpaque(false);
        jPanel2.setLayout(new java.awt.GridLayout(3, 1));

        btnRegistrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/table_add.png"))); // NOI18N
        btnRegistrar.setToolTipText("REGISTRAR");
        btnRegistrar.setOpaque(false);
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });
        jPanel2.add(btnRegistrar);

        btnModif.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/table_edit.png"))); // NOI18N
        btnModif.setToolTipText("MODIFICAR");
        btnModif.setOpaque(false);
        btnModif.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModifActionPerformed(evt);
            }
        });
        jPanel2.add(btnModif);

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/table_delete.png"))); // NOI18N
        btnEliminar.setToolTipText("ELIMINAR REGISTRO");
        btnEliminar.setOpaque(false);
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        jPanel2.add(btnEliminar);

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 108, 60, 120));

        jPanel4.setOpaque(false);
        jPanel4.setLayout(new java.awt.GridLayout(2, 0, 0, 7));

        btnRefrshe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/arrow_refresh.png"))); // NOI18N
        btnRefrshe.setToolTipText("REFRESH");
        btnRefrshe.setOpaque(false);
        btnRefrshe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefrsheActionPerformed(evt);
            }
        });
        jPanel4.add(btnRefrshe);

        btnCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/cancel.png"))); // NOI18N
        btnCerrar.setToolTipText("CERRAR");
        btnCerrar.setOpaque(false);
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });
        jPanel4.add(btnCerrar);

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 130, 60, 80));

        chkB.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(chkB);
        chkB.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        chkB.setForeground(new java.awt.Color(0, 204, 0));
        chkB.setSelected(true);
        chkB.setText("Bloqueado");
        chkB.setOpaque(false);
        chkB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkBActionPerformed(evt);
            }
        });
        getContentPane().add(chkB, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 70, -1, -1));

        chkD.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(chkD);
        chkD.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        chkD.setForeground(new java.awt.Color(0, 204, 0));
        chkD.setText("Desbloqueado");
        chkD.setOpaque(false);
        chkD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkDActionPerformed(evt);
            }
        });
        getContentPane().add(chkD, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 70, -1, -1));
        getContentPane().add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 248, 560, 10));

        tblVista.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tblVista.setForeground(new java.awt.Color(255, 255, 255));
        tblVista.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
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

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, 560, 270));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 204, 0));
        jLabel7.setText("Total registros : ");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 560, -1, -1));

        lblTotal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTotal.setForeground(new java.awt.Color(0, 204, 0));
        getContentPane().add(lblTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 560, 90, 20));

        jLabel6.setBackground(new java.awt.Color(51, 51, 51));
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/Encabezados/ftn1.png"))); // NOI18N
        jLabel6.setOpaque(true);
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, -6, 730, 620));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNomKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomKeyTyped
        if (txtNom.getText().trim().length() >= 20) {
            getToolkit().beep();
            evt.consume();
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomKeyTyped

    private void txtIpEKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIpEKeyTyped
        if (txtIpE.getText().trim().length() < 15) {
            String s = String.valueOf(evt.getKeyChar());

            if (!s.matches("[0-9.]")) {
                //getToolkit().beep();
                evt.consume();
            }
        } else {
            getToolkit().beep();
            evt.consume();

        }

        // TODO add your handling code here:
    }//GEN-LAST:event_txtIpEKeyTyped

    private void txtIpSKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIpSKeyTyped
        if (txtIpS.getText().trim().length() < 15) {
            String s = String.valueOf(evt.getKeyChar());

            if (!s.matches("[0-9.]")) {
                //getToolkit().beep();
                evt.consume();
            }
        } else {
            getToolkit().beep();
            evt.consume();

        }
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIpSKeyTyped

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        registro();

        listarServ();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void btnModifActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModifActionPerformed
        actualisar();
        limpiar();
        listarServ();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnModifActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed

        if (id != -1) {
            int i = JOptionPane.showConfirmDialog(this, "Esta seguro de eliminar Registro?", "MENSAJE DE CONFIRMACION", JOptionPane.OK_OPTION, JOptionPane.NO_OPTION);
            if (i == 0) {
                servidorDao.eliminarRegistro(id);
                id = -1;
                limpiar();
                listarServ();
            } else {
                limpiar();
            }
        } else {
            Mensaje.panelSms("Seleccione un registro existente");
            limpiar();
        }

    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnRefrsheActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefrsheActionPerformed
        limpiar();
        listarServ();

        chkB.setSelected(true);
//        // TODO add your handling code here:
    }//GEN-LAST:event_btnRefrsheActionPerformed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void chkBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkBActionPerformed

        Controller(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_chkBActionPerformed

    private void chkDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkDActionPerformed
        habilitarController();
        // TODO add your handling code here:
    }//GEN-LAST:event_chkDActionPerformed

    private void tblVistaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblVistaMouseClicked
        if (evt.getClickCount() == 2) {
            int i = tblVista.getSelectedRow();
            id = (int) tblVista.getValueAt(i, 0);
            txtNom.setText(tblVista.getValueAt(i, 1).toString());
            txtIpE.setText(tblVista.getValueAt(i, 2).toString());
            txtIpS.setText(tblVista.getValueAt(i, 3).toString());
            //System.out.println("id serv: "+id);
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_tblVistaMouseClicked

    private void tblVistaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblVistaKeyPressed
        int i = tblVista.getSelectedRow();
        if (evt.getKeyCode() == 38) {
            i = i - 1;
            if (i >= 0) {

                id = (int) tblVista.getValueAt(i, 0);
                txtNom.setText(tblVista.getValueAt(i, 1).toString());
                txtIpE.setText(tblVista.getValueAt(i, 2).toString());
                txtIpS.setText(tblVista.getValueAt(i, 3).toString());

            }

        } else if (evt.getKeyCode() == 40) {
            i = i + 1;
            if (i < tblVista.getRowCount()) {
                id = (int) tblVista.getValueAt(i, 0);
                txtNom.setText(tblVista.getValueAt(i, 1).toString());
                txtIpE.setText(tblVista.getValueAt(i, 2).toString());
                txtIpS.setText(tblVista.getValueAt(i, 3).toString());

            }

        }

        // TODO add your handling code here:
    }//GEN-LAST:event_tblVistaKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnModif;
    private javax.swing.JButton btnRefrshe;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox chkB;
    private javax.swing.JCheckBox chkD;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JTable tblVista;
    private javax.swing.JTextField txtIpE;
    private javax.swing.JTextField txtIpS;
    private javax.swing.JTextField txtNom;
    // End of variables declaration//GEN-END:variables
}
