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

public class ConsultaEstadoC_SE extends javax.swing.JInternalFrame {

    DefaultTableModel modTabla;
    String cliente;

    int id = -1;
    boolean llave = false;
    public final ClienteDao clienteDao;

    public ConsultaEstadoC_SE() {
        initComponents();

        DaoFactory factory = DaoFactory.getInstance();
        clienteDao = factory.getClienteDao(CLIENTE);

        localisar(624, 10);
        setTitle("Consulta_Estado/Cliente");
        transparenciaTabla();
        //listar(0);
        listarBusqueda(0, "", 0, ordenar());

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

    public String ascDesc() {
        String cadena = "desc";
        if (rbtDesc.isSelected()) {
            cadena = "desc";
        } else if (rbtAsc.isSelected()) {
            cadena = "asc";
        }

        return cadena;

    }

    public String ordenar() {

        String cadena = "";
        String order = ascDesc();
        if (cboOrdenar.getSelectedIndex() == 0) {
            cadena = "ORDER BY c.ultimoCorteEjecutado " + order + "";
        } else if (cboOrdenar.getSelectedIndex() == 1) {
            cadena = "ORDER BY c.nombre " + order + "";
        } else if (cboOrdenar.getSelectedIndex() == 2) {
            cadena = "ORDER BY c.apellido " + order + "";
        } else if (cboOrdenar.getSelectedIndex() == 3) {
            cadena = "ORDER BY c.f_Vencimiento " + order + "";
        } else if (cboOrdenar.getSelectedIndex() == 4) {
            cadena = "ORDER BY c.f_Corte " + order + "";
        }

        return cadena;

    }

    public int estadoSelect() {
        int es = 0;
        if (CK1.isSelected()) {
            es = 1;
        } else if (CK2.isSelected()) {
            es = 2;
        } else if (CK3.isSelected()) {
            es = 3;
        } else if (CK4.isSelected()) {
            es = 4;
        } else if (CK5.isSelected()) {
            es = 5;
        } else if (CK6.isSelected()) {
            es = 6;
        } else if (CK7.isSelected()) {
            es = 7;
        } else if (Chk8.isSelected()) {
            es = 8;
        } else {
            es = 0;

        }

        return es;
    }

    private void listar(int es) {
        int longitud = clienteDao.listarClienteEstado(es).size();
        if (longitud > 0) {
            //llave = false;

            Object[] colum = {"Id", "Nombre", "Apellido", "estado", "F_vencimiento", "F_corte", "Tarifa"};
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

    private void listarBusqueda(int es, String cadena, int x, String orden) {
////c.idCliente,c.nombre,c.apellido,ec.descripcion,c.f_Vencimiento,c.f_Corte,c.tarifa,c.ultimoCorteEjecutado,c.saldo

        Object[] colum = {"Id", "Nombre", "Apellido", "estado", "F_vencimiento", "F_corte", "Tarifa", "Ejecucion_Corte", "Deuda"};
        modTabla = new DefaultTableModel(null, colum) {

            public boolean isCellEditable(int row, int colum) {
                return false;
            }
        };
        int longitud = clienteDao.buscarCliente2(es, cadena, x, orden).size();
        float deuda = 0;
        int i = 0;
        if (longitud > 0) {
            llave = false;

            for (Object[] fila : clienteDao.buscarCliente2(es, cadena, x, orden)) {

                modTabla.addRow(fila);
                deuda = deuda + Float.parseFloat(modTabla.getValueAt(i, 8).toString());
                i++;

            }
            tblVista.setModel(modTabla);

            anchoColum();
            cliente = tblVista.getValueAt(0, 1).toString() + " " + tblVista.getValueAt(0, 2).toString();
            txtCliente.setText(cliente);
            id = Integer.parseInt(tblVista.getValueAt(0, 0).toString());

            lblTotal.setText(" " + longitud);
            lblTotalDeuda.setText(" " + deuda);
        } else {
            llave = true;
            txtCliente.setText(null);
            tblVista.setModel(modTabla);
            lblTotal.setText(" " + longitud);
            lblTotalDeuda.setText(" " + deuda);

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
        tblVista.getColumnModel().getColumn(7).setMinWidth(50);
        //tblVista.getColumnModel().getColumn(7).setMaxWidth(70);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        cboBusCampo = new javax.swing.JComboBox();
        txtBuscar = new javax.swing.JTextField();
        CK1 = new javax.swing.JCheckBox();
        CK2 = new javax.swing.JCheckBox();
        CK3 = new javax.swing.JCheckBox();
        CK4 = new javax.swing.JCheckBox();
        CK0 = new javax.swing.JCheckBox();
        CK5 = new javax.swing.JCheckBox();
        CK6 = new javax.swing.JCheckBox();
        jLabel2 = new javax.swing.JLabel();
        CK7 = new javax.swing.JCheckBox();
        Chk8 = new javax.swing.JCheckBox();
        cboOrdenar = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        rbtAsc = new javax.swing.JRadioButton();
        rbtDesc = new javax.swing.JRadioButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtCliente = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblVista = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lblTotalDeuda = new javax.swing.JLabel();
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

        buttonGroup1.add(CK1);
        CK1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        CK1.setForeground(new java.awt.Color(51, 255, 0));
        CK1.setText("Activos");
        CK1.setOpaque(false);
        CK1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CK1ActionPerformed(evt);
            }
        });

        buttonGroup1.add(CK2);
        CK2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        CK2.setForeground(new java.awt.Color(51, 255, 0));
        CK2.setText("Deuda Vencida");
        CK2.setOpaque(false);
        CK2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CK2ActionPerformed(evt);
            }
        });

        buttonGroup1.add(CK3);
        CK3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        CK3.setForeground(new java.awt.Color(51, 255, 0));
        CK3.setText("Corte sin Ejecutar");
        CK3.setOpaque(false);
        CK3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CK3ActionPerformed(evt);
            }
        });

        buttonGroup1.add(CK4);
        CK4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        CK4.setForeground(new java.awt.Color(51, 255, 0));
        CK4.setText("Corte Ejecutado");
        CK4.setOpaque(false);
        CK4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CK4ActionPerformed(evt);
            }
        });

        buttonGroup1.add(CK0);
        CK0.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        CK0.setForeground(new java.awt.Color(51, 255, 0));
        CK0.setSelected(true);
        CK0.setText("Todos los Estados");
        CK0.setOpaque(false);
        CK0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CK0ActionPerformed(evt);
            }
        });

        buttonGroup1.add(CK5);
        CK5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        CK5.setForeground(new java.awt.Color(51, 255, 0));
        CK5.setText("Retiro de Antena");
        CK5.setOpaque(false);
        CK5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CK5ActionPerformed(evt);
            }
        });

        buttonGroup1.add(CK6);
        CK6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        CK6.setForeground(new java.awt.Color(51, 255, 0));
        CK6.setText("Retirado");
        CK6.setOpaque(false);
        CK6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CK6ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Elejir Estado del Cliente :");

        buttonGroup1.add(CK7);
        CK7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        CK7.setForeground(new java.awt.Color(51, 255, 0));
        CK7.setText("Congelado");
        CK7.setOpaque(false);
        CK7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CK7ActionPerformed(evt);
            }
        });

        buttonGroup1.add(Chk8);
        Chk8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        Chk8.setForeground(new java.awt.Color(51, 255, 0));
        Chk8.setText("Clientes con Deuda");
        Chk8.setOpaque(false);
        Chk8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Chk8ActionPerformed(evt);
            }
        });

        cboOrdenar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "EJecucion de Corte", "Nombre", "Apellido", "Fecha de Vencimiento", "Fecha de Corte" }));
        cboOrdenar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboOrdenarActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Ordenar Por:");

        buttonGroup2.add(rbtAsc);
        rbtAsc.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        rbtAsc.setForeground(new java.awt.Color(240, 240, 240));
        rbtAsc.setText("Ascendente");
        rbtAsc.setOpaque(false);
        rbtAsc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtAscActionPerformed(evt);
            }
        });

        buttonGroup2.add(rbtDesc);
        rbtDesc.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        rbtDesc.setForeground(new java.awt.Color(240, 240, 240));
        rbtDesc.setSelected(true);
        rbtDesc.setText("Descendente");
        rbtDesc.setOpaque(false);
        rbtDesc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtDescActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(Chk8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cboOrdenar, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(rbtAsc)
                        .addGap(18, 18, 18)
                        .addComponent(rbtDesc)))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(CK4)
                            .addComponent(CK1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(CK2)
                            .addComponent(CK5)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(txtBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cboBusCampo, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(CK0)
                    .addComponent(CK7)
                    .addComponent(CK6)
                    .addComponent(CK3))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboBusCampo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(CK0)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(CK7)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(CK1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(CK4))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addComponent(CK3)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(CK6)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(CK2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(CK5)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(Chk8)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cboOrdenar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rbtAsc)
                            .addComponent(rbtDesc))))
                .addContainerGap())
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 49, 481, 180));

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

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 481, 40));

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

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 280, 583, 390));

        jPanel4.setOpaque(false);
        jPanel4.setLayout(new java.awt.GridLayout(2, 1, 0, 2));

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

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(509, 71, -1, 110));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 255, 0));
        jLabel4.setText("Total Deuda");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 680, -1, -1));

        lblTotal.setBackground(new java.awt.Color(255, 255, 255));
        lblTotal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTotal.setOpaque(true);
        getContentPane().add(lblTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 680, 82, 17));
        getContentPane().add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 670, 583, 10));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/Encabezados/CCSE.png"))); // NOI18N
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 0, 620, 50));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 255, 0));
        jLabel6.setText("Total Registros");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 680, -1, -1));

        lblTotalDeuda.setBackground(new java.awt.Color(255, 255, 255));
        lblTotalDeuda.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTotalDeuda.setOpaque(true);
        getContentPane().add(lblTotalDeuda, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 680, 82, 17));

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
        // listar();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSalirActionPerformed

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
        int x = cboBusCampo.getSelectedIndex();
        listarBusqueda(estadoSelect(), txtBuscar.getText().trim(), x, ordenar());
        if (llave == true) {
            txtBuscar.setText(Cadena.eliminarUltCaracter(txtBuscar.getText().trim()));
            listarBusqueda(estadoSelect(), txtBuscar.getText().trim(), x, ordenar());
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarKeyReleased

    private void cboBusCampoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboBusCampoActionPerformed
        txtBuscar.setText(null);
        int x = cboBusCampo.getSelectedIndex();

        listarBusqueda(estadoSelect(), txtBuscar.getText().trim(), x, ordenar());
        txtBuscar.requestFocus();

        // TODO add your handling code here:
    }//GEN-LAST:event_cboBusCampoActionPerformed

    private void CK2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CK2ActionPerformed
        txtBuscar.setText(null);
        listarBusqueda(estadoSelect(), txtBuscar.getText().trim(), cboBusCampo.getSelectedIndex(), ordenar());
        txtBuscar.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_CK2ActionPerformed

    private void CK0ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CK0ActionPerformed
        txtBuscar.setText(null);
        listarBusqueda(estadoSelect(), txtBuscar.getText().trim(), cboBusCampo.getSelectedIndex(), ordenar());
        txtBuscar.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_CK0ActionPerformed

    private void CK1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CK1ActionPerformed
        txtBuscar.setText(null);
        listarBusqueda(estadoSelect(), txtBuscar.getText().trim(), cboBusCampo.getSelectedIndex(), ordenar());
        txtBuscar.requestFocus();

// TODO add your handling code here:
    }//GEN-LAST:event_CK1ActionPerformed

    private void CK3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CK3ActionPerformed
        txtBuscar.setText(null);
        listarBusqueda(estadoSelect(), txtBuscar.getText().trim(), cboBusCampo.getSelectedIndex(), ordenar());
        txtBuscar.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_CK3ActionPerformed

    private void CK4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CK4ActionPerformed
        txtBuscar.setText(null);
        listarBusqueda(estadoSelect(), txtBuscar.getText().trim(), cboBusCampo.getSelectedIndex(), ordenar());
        txtBuscar.requestFocus();

// TODO add your handling code here:
    }//GEN-LAST:event_CK4ActionPerformed

    private void CK5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CK5ActionPerformed
        txtBuscar.setText(null);
        listarBusqueda(estadoSelect(), txtBuscar.getText().trim(), cboBusCampo.getSelectedIndex(), ordenar());
        txtBuscar.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_CK5ActionPerformed

    private void CK6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CK6ActionPerformed
        txtBuscar.setText(null);
        listarBusqueda(estadoSelect(), txtBuscar.getText().trim(), cboBusCampo.getSelectedIndex(), ordenar());
        txtBuscar.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_CK6ActionPerformed

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

    private void CK7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CK7ActionPerformed
        txtBuscar.setText(null);
        listarBusqueda(estadoSelect(), txtBuscar.getText().trim(), cboBusCampo.getSelectedIndex(), ordenar());
        txtBuscar.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_CK7ActionPerformed

    private void Chk8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Chk8ActionPerformed
        txtBuscar.setText(null);
        listarBusqueda(estadoSelect(), txtBuscar.getText().trim(), cboBusCampo.getSelectedIndex(), ordenar());
        txtBuscar.requestFocus();

// TODO add your handling code here:
    }//GEN-LAST:event_Chk8ActionPerformed

    private void cboOrdenarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboOrdenarActionPerformed
        listarBusqueda(estadoSelect(), txtBuscar.getText().trim(), cboBusCampo.getSelectedIndex(), ordenar());

        // TODO add your handling code here:
    }//GEN-LAST:event_cboOrdenarActionPerformed

    private void rbtAscActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtAscActionPerformed
        listarBusqueda(estadoSelect(), txtBuscar.getText().trim(), cboBusCampo.getSelectedIndex(), ordenar());
        // TODO add your handling code here:
    }//GEN-LAST:event_rbtAscActionPerformed

    private void rbtDescActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtDescActionPerformed
        listarBusqueda(estadoSelect(), txtBuscar.getText().trim(), cboBusCampo.getSelectedIndex(), ordenar());
        // TODO add your handling code here:
    }//GEN-LAST:event_rbtDescActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox CK0;
    private javax.swing.JCheckBox CK1;
    private javax.swing.JCheckBox CK2;
    private javax.swing.JCheckBox CK3;
    private javax.swing.JCheckBox CK4;
    private javax.swing.JCheckBox CK5;
    private javax.swing.JCheckBox CK6;
    private javax.swing.JCheckBox CK7;
    private javax.swing.JCheckBox Chk8;
    private javax.swing.JButton btnSalir;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox cboBusCampo;
    private javax.swing.JComboBox cboOrdenar;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JLabel lblTotalDeuda;
    private javax.swing.JRadioButton rbtAsc;
    private javax.swing.JRadioButton rbtDesc;
    private javax.swing.JTable tblVista;
    public transient javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtCliente;
    // End of variables declaration//GEN-END:variables
}
