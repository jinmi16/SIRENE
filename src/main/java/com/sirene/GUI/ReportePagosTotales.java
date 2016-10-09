/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirene.GUI;

//import com.sirene.Dao.Impl.DaoPago;
import com.sirene.Dao.Impl.DaoFactory;
import com.sirene.Dao.Impl.Mensaje;
import com.sirene.Dao.PagoDao;
import com.sirene.Utilitarios.Cadena;
import static com.sirene.Utilitarios.Constantes.PAGO;
import com.sirene.Utilitarios.Fecha;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author JINMI
 */
public class ReportePagosTotales extends javax.swing.JInternalFrame {

    DefaultComboBoxModel modCboA = new DefaultComboBoxModel();
    DefaultComboBoxModel modCboB = new DefaultComboBoxModel();
    public final PagoDao pagoDao;

    public ReportePagosTotales() {
        initComponents();

        DaoFactory factory = DaoFactory.getInstance();
        pagoDao = factory.getPagoDao(PAGO);

        localisar(762, 10);
        setTitle("Consulta_Pagos");

        listarCboFecha();
        estadoFecha();
        eventoDcFecha1();
        eventoDcFecha2();

    }

    private void localisar(int x, int y) {
        int w_screen = getToolkit().getScreenSize().width;
        int ka = (w_screen / 2) - (x / 2);
        setLocation(ka, y);

    }

    private void eventoDcFecha1() {

        DcFecha1.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (DcFecha1.getDate() != null && cboFecha.getSelectedIndex() == 0) {
                    float total = pagoDao.pagoTotalFecha(Fecha.convertToString2(DcFecha1.getDate()));
                    txtTotal.setText("" + total);

                }

            }
        });

    }

    private void McMesEvent() {

    }

    private void eventoDcFecha2() {

        DcFecha2.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (DcFecha2.getDate() != null && DcFecha1.getDate() != null && cboFecha.getSelectedIndex() == 1) {
                    float total = pagoDao.pagoTotalFechaRango(Fecha.convertToString2(DcFecha1.getDate()), Fecha.convertToString2(DcFecha2.getDate()));
                    txtTotal.setText(" " + total);

                }

            }
        });

    }

    private void listarCboFecha() {
        modCboA.addElement("Fecha");
        modCboA.addElement("Rango de Fechas");
        cboFecha.setModel(modCboA);

    }

    private void estadoFecha() {
        DcFecha1.setDate(null);
        DcFecha2.setDate(null);

        if (cboFecha.getSelectedIndex() == 1) {
            DcFecha2.setEnabled(true);
        } else {
            DcFecha2.setEnabled(false);

        }

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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        DcFecha1 = new com.toedter.calendar.JDateChooser();
        DcFecha2 = new com.toedter.calendar.JDateChooser();
        cboFecha = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        mcMes = new com.toedter.calendar.JMonthChooser();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        ycAño = new com.toedter.calendar.JYearChooser();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(0, 0, 51));
        setClosable(true);
        setIconifiable(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Busqueda de pagos  Totales Por:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel1.setOpaque(false);

        DcFecha1.setDateFormatString("yyyy-MM-dd");
        DcFecha1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        DcFecha2.setDateFormatString("yyyy-MM-dd");
        DcFecha2.setEnabled(false);
        DcFecha2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        cboFecha.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cboFecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboFechaActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 204, 0));
        jLabel1.setText("Consulta por Mes :");

        mcMes.setOpaque(false);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 204, 0));
        jLabel2.setText("Año");

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/buscar16.png"))); // NOI18N
        jButton1.setOpaque(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 204, 0));
        jLabel4.setText("Hasta");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cboFecha, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(DcFecha1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mcMes, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(ycAño, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1))
                    .addComponent(DcFecha2, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(213, 213, 213))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cboFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(DcFecha2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(DcFecha1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(1, 1, 1)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(mcMes, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(ycAño, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 510, 120));

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setOpaque(false);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 204, 0));
        jLabel3.setText("Total Ingreso");

        txtTotal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtTotal.setForeground(new java.awt.Color(255, 255, 0));
        txtTotal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTotal.setOpaque(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 210, 340, 60));

        jLabel6.setBackground(new java.awt.Color(102, 102, 102));
        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("CONSULTA DE PAGOS TOTALES");
        jLabel6.setOpaque(true);
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 540, 50));

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/cancel.png"))); // NOI18N
        jButton2.setOpaque(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 220, -1, -1));

        jLabel5.setBackground(new java.awt.Color(0, 0, 51));
        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/Encabezados/ftn1.png"))); // NOI18N
        jLabel5.setOpaque(true);
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 550, 380));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cboFechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboFechaActionPerformed
        txtTotal.setText(null);
        estadoFecha();

        // TODO add your handling code here:
    }//GEN-LAST:event_cboFechaActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int mes = mcMes.getMonth() + 1;
        int año = ycAño.getYear();
        float total = pagoDao.pagoTotalFechaMes(mes, año);
        txtTotal.setText(" " + total);

// TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser DcFecha1;
    private com.toedter.calendar.JDateChooser DcFecha2;
    private javax.swing.JComboBox cboFecha;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private com.toedter.calendar.JMonthChooser mcMes;
    private javax.swing.JTextField txtTotal;
    private com.toedter.calendar.JYearChooser ycAño;
    // End of variables declaration//GEN-END:variables
}
