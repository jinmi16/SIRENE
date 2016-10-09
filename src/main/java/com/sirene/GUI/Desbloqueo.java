package com.sirene.GUI;

import com.sirene.Bean.Usuario;
import com.sirene.Dao.Impl.Mensaje;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

public class Desbloqueo extends javax.swing.JDialog {

    Usuario user;

    public Desbloqueo(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        txtPassword.requestFocus();
    }

    public void setUser(Usuario us) {
        user = us;

    }

    public boolean validar() {
        boolean f = false;

        String a = txtPassword.getText();
        String b = user.getPassword();
        if (a.equals(b)) {
            f = true;

        } else {
            Mensaje.panelSms("Password incorrecto");
            txtPassword.setText(null);
            txtPassword.requestFocus();
            f = false;
        }

        return f;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtUser = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        cboAceptar = new javax.swing.JButton();
        cboCancelar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(467, 230));
        setMinimumSize(new java.awt.Dimension(467, 230));
        setPreferredSize(new java.awt.Dimension(467, 230));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setOpaque(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("User");

        txtUser.setEditable(false);
        txtUser.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtUser.setForeground(new java.awt.Color(102, 102, 102));
        txtUser.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("Password");

        txtPassword.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtPassword.setForeground(new java.awt.Color(102, 102, 102));
        txtPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPasswordKeyPressed(evt);
            }
        });

        cboAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/accept.png"))); // NOI18N
        cboAceptar.setOpaque(false);
        cboAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboAceptarActionPerformed(evt);
            }
        });
        cboAceptar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cboAceptarKeyPressed(evt);
            }
        });

        cboCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/cancel.png"))); // NOI18N
        cboCancelar.setOpaque(false);
        cboCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(cboCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cboAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtPassword)
                    .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 20, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cboAceptar, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cboCancelar))
                .addGap(0, 8, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 60, 220, 130));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/Encabezados/log2.png"))); // NOI18N
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 460, 210));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cboAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboAceptarActionPerformed
        if (txtPassword.getText().trim().length() > 0) {
            boolean v = validar();
            if (v == true) {
                MdiPrincipal.bloquearDesb(true);
                dispose();
            }
        }

// TODO add your handling code here:
    }//GEN-LAST:event_cboAceptarActionPerformed

    private void cboCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboCancelarActionPerformed

dispose();
// TODO add your handling code here:
    }//GEN-LAST:event_cboCancelarActionPerformed

    private void txtPasswordKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPasswordKeyPressed
txtPassword.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, java.util.Collections.EMPTY_SET);
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            cboAceptar.requestFocus();

        }
        
        
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txtPassword.getText().trim().length() > 0) {
            boolean v = validar();
            if (v == true) {
                MdiPrincipal.bloquearDesb(true);
                dispose();
            }
        }

            
        }
        

// TODO add your handling code here:
    }//GEN-LAST:event_txtPasswordKeyPressed

    private void cboAceptarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cboAceptarKeyPressed
cboAceptar.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, java.util.Collections.EMPTY_SET);
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            cboCancelar.requestFocus();

        }
        // TODO add your handling code here:
    }//GEN-LAST:event_cboAceptarKeyPressed

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
            java.util.logging.Logger.getLogger(Desbloqueo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Desbloqueo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Desbloqueo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Desbloqueo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Desbloqueo dialog = new Desbloqueo(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton cboAceptar;
    private javax.swing.JButton cboCancelar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField txtPassword;
    public static javax.swing.JTextField txtUser;
    // End of variables declaration//GEN-END:variables
}
