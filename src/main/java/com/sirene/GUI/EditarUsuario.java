package com.sirene.GUI;

import com.sirene.Bean.Usuario;
import com.sirene.Dao.Impl.DaoFactory;
//import com.sirene.Dao.Impl.DaoUser;
import com.sirene.Dao.Impl.Mensaje;
import com.sirene.Dao.UsuarioDao;
import static com.sirene.Utilitarios.Constantes.USUARIO;

public class EditarUsuario extends javax.swing.JDialog {

    public int isUs = -1;
    Usuario user = new Usuario();
    public final UsuarioDao usuarioDao;

    public EditarUsuario(java.awt.Frame parent, boolean modal) {
        super(parent, modal);

        initComponents();

        DaoFactory factory = DaoFactory.getInstance();
        usuarioDao = factory.getUsuarioDao(USUARIO);

        setLocationRelativeTo(null);
    }

    public void CambiarPasw() {

        user.setPassword(pswConNew.getText());
        user.setIdUser(isUs);
        if (usuarioDao.getPasw(isUs, pswContActual.getText())) {
            usuarioDao.cambiarContraseña(user);
            pswContActual.setText(null);
            pswConNew.setText(null);
            dispose();

        } else {
            Mensaje.panelSms("Contraseña Actual, no es valida");
            pswContActual.setText(null);
            pswConNew.setText(null);
            pswContActual.requestFocus();
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel5 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        pswContActual = new javax.swing.JPasswordField();
        pswConNew = new javax.swing.JPasswordField();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        LBLllave = new javax.swing.JLabel();
        lblFondo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(438, 250));
        setPreferredSize(new java.awt.Dimension(428, 250));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/Encabezados/CONTRASEÑA2.png"))); // NOI18N
        jLabel5.setMaximumSize(new java.awt.Dimension(344, 50));
        jLabel5.setMinimumSize(new java.awt.Dimension(340, 50));
        jLabel5.setPreferredSize(new java.awt.Dimension(340, 50));
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 0, 440, 50));

        jPanel3.setBackground(new java.awt.Color(0, 0, 51));
        jPanel3.setForeground(new java.awt.Color(0, 0, 51));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setOpaque(false);

        jLabel1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 204, 0));
        jLabel1.setText("Usuario");

        jLabel2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 204, 0));
        jLabel2.setText("Contraseña Actual ");

        jLabel3.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 204, 0));
        jLabel3.setText("Nueva Contraseña");

        txtUsuario.setEditable(false);
        txtUsuario.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtUsuario.setForeground(new java.awt.Color(255, 255, 255));
        txtUsuario.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtUsuario.setOpaque(false);

        pswContActual.setForeground(new java.awt.Color(255, 255, 255));
        pswContActual.setCaretColor(new java.awt.Color(0, 255, 0));
        pswContActual.setOpaque(false);

        pswConNew.setForeground(new java.awt.Color(255, 255, 255));
        pswConNew.setCaretColor(new java.awt.Color(0, 255, 0));
        pswConNew.setOpaque(false);
        pswConNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pswConNewActionPerformed(evt);
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pswConNew, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                    .addComponent(txtUsuario)
                    .addComponent(pswContActual))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(pswContActual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(pswConNew, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 290, -1));

        jPanel2.setOpaque(false);
        jPanel2.setLayout(new java.awt.GridLayout(2, 0, 0, 4));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/accept.png"))); // NOI18N
        jButton1.setToolTipText("ACEPTAR");
        jButton1.setOpaque(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/cancel.png"))); // NOI18N
        jButton2.setToolTipText("CANCELAR");
        jButton2.setOpaque(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton2);

        jPanel3.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 20, -1, 90));

        LBLllave.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        LBLllave.setForeground(new java.awt.Color(0, 153, 0));
        LBLllave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/LLAVE80X80.png"))); // NOI18N
        jPanel3.add(LBLllave, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 100, -1, 70));

        lblFondo.setForeground(new java.awt.Color(0, 0, 51));
        lblFondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/Encabezados/ftn1.png"))); // NOI18N
        jPanel3.add(lblFondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -20, 430, 190));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 430, 170));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        CambiarPasw();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void pswConNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pswConNewActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pswConNewActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        dispose();

// TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(EditarUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditarUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditarUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditarUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                EditarUsuario dialog = new EditarUsuario(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel LBLllave;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblFondo;
    private javax.swing.JPasswordField pswConNew;
    private javax.swing.JPasswordField pswContActual;
    public javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
