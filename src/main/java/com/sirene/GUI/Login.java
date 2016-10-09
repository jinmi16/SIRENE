/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirene.GUI;

import com.sirene.Bean.Usuario;
import com.sirene.Dao.Impl.DaoFactory;
import com.sirene.Dao.UsuarioDao;
import static com.sirene.Utilitarios.Constantes.USUARIO;
//import com.sirene.Dao.Impl.DaoUser;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class Login extends javax.swing.JFrame {

    Usuario user;
    public final UsuarioDao usuarioDao;

    public Login() {
        initComponents();

        DaoFactory factory = DaoFactory.getInstance();
        usuarioDao = factory.getUsuarioDao(USUARIO);

        setLocationRelativeTo(null);
        setTitle("Sistema de Autenticacion");
    }

    public void limpiar() {
        txtUser.setText(null);
        txtPassw.setText(null);
    }

    public void logear() {

        String usuario, passw;
        usuario = txtUser.getText();
        passw = txtPassw.getText();

        if (usuario.trim().length() == 0 || passw.trim().length() == 0) {
            JOptionPane.showMessageDialog(null, "Ingrese tosos los campos");
            limpiar();
        } else {

            Object[] array = usuarioDao.logUser(usuario, passw);

            user = (Usuario) array[1];

            if (array[0] != null) {

                MdiPrincipal ventana = new MdiPrincipal();

                ventana.setUser(array);

                ventana.privilegios();
                ventana.setVisible(true);
                dispose();

            } else {
                limpiar();
            }

        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnCancelar = new javax.swing.JButton();
        btnAceptar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtUser = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtPassw = new javax.swing.JPasswordField();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 0));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.GridLayout(1, 2, 5, 5));

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/cancel.png"))); // NOI18N
        btnCancelar.setToolTipText("CANCELAR");
        btnCancelar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        btnCancelar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnCancelarKeyPressed(evt);
            }
        });
        jPanel1.add(btnCancelar);

        btnAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/lock_open.png"))); // NOI18N
        btnAceptar.setToolTipText("ACEPTAR");
        btnAceptar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });
        btnAceptar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnAceptarKeyPressed(evt);
            }
        });
        jPanel1.add(btnAceptar);

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 150, 140, 40));

        jPanel2.setOpaque(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("User");

        txtUser.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        txtUser.setText("ruben");
        txtUser.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));
        txtUser.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtUserKeyPressed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("Password");
        jLabel2.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentMoved(java.awt.event.ComponentEvent evt) {
                jLabel2ComponentMoved(evt);
            }
        });

        txtPassw.setText("123456");
        txtPassw.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));
        txtPassw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPasswKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPassw, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel1))
                    .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtPassw, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 60, -1, -1));

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/Encabezados/log2.png"))); // NOI18N
        jLabel4.setOpaque(true);
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 470, 210));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        logear();

        // TODO add your handling code here:
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        System.exit(0);

        // TODO add your handling code here:
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void jLabel2ComponentMoved(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jLabel2ComponentMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel2ComponentMoved

    private void txtUserKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUserKeyPressed
        txtUser.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, java.util.Collections.EMPTY_SET);
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            txtPassw.requestFocus();

        }
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUserKeyPressed

    private void btnAceptarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnAceptarKeyPressed
        btnAceptar.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, java.util.Collections.EMPTY_SET);
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            btnCancelar.requestFocus();

        }
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAceptarKeyPressed

    private void btnCancelarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnCancelarKeyPressed
        btnCancelar.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, java.util.Collections.EMPTY_SET);
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            btnAceptar.requestFocus();

        }
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCancelarKeyPressed

    private void txtPasswKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPasswKeyPressed
        txtPassw.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, java.util.Collections.EMPTY_SET);
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            btnAceptar.requestFocus();

        }

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {

            logear();
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_txtPasswKeyPressed

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
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    //javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new Login().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPasswordField txtPassw;
    private javax.swing.JTextField txtUser;
    // End of variables declaration//GEN-END:variables
}
