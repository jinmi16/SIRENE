package com.sirene.GUI;

//import com.sirene.Dao.Impl.DaoCliente;
import com.sirene.Dao.ClienteDao;
import com.sirene.Dao.Impl.DaoFactory;
import com.sirene.Dao.Impl.Mensaje;
import com.sirene.Utilitarios.Cadena;
import static com.sirene.Utilitarios.Constantes.CLIENTE;
import java.awt.event.KeyEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class HabitarDesabilitarCliente extends javax.swing.JInternalFrame {

    DefaultTableModel modTabla, modTabla2;
    String cliente, cliente2;

    int id = -1, id2 = -1;
    boolean llave1 = false, llave2 = false;
    public final ClienteDao clienteDao;

    public HabitarDesabilitarCliente() {
        initComponents();

        DaoFactory factory = DaoFactory.getInstance();
        clienteDao = factory.getClienteDao(CLIENTE);

        localisar(570, 10);
        setTitle("Habilitar/Deshabilitar_Cliente");
        transparenciaTabla();
        transparenciaTabla2();
        listar();
        listar2();
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

    private void transparenciaTabla2() {
        //La mesa será transparente si ni ella ni las células son opacas:
        tblVista2.setOpaque(false);
        ((DefaultTableCellRenderer) tblVista2.getDefaultRenderer(Object.class)).setOpaque(false);
        //Si la tabla está en un ScrollPane , que es hacer transparente así
        jScrollPane3.setOpaque(false);
        jScrollPane3.getViewport().setOpaque(false);
        //Por lo menos, usted puede quitar las líneas de la cuadrícula:
        // tblVista.setShowGrid(false);

    }

    private void listar() {
        int longitud = clienteDao.listarCeRa().size();
        if (longitud > 0) {
            // llave1 = true;

            Object[] colum = {"Id", "Nombre", "Apellido", "Dni", "estado"};
            modTabla = new DefaultTableModel(null, colum) {

                public boolean isCellEditable(int row, int colum) {
                    return false;
                }
            };
            for (Object[] fila : clienteDao.listarCeRa()) {

                modTabla.addRow(fila);

            }
            tblVista.setModel(modTabla);

            anchoColum();
            cliente = tblVista.getValueAt(0, 1).toString() + " " + tblVista.getValueAt(0, 2).toString();
            txtCliente.setText(cliente);
            id = Integer.parseInt(tblVista.getValueAt(0, 0).toString());
            lblT1.setText(" " + longitud);
        } else {
            //llave1 = false;

            modTabla = new DefaultTableModel();
            tblVista.setModel(modTabla);
            txtCliente.setText(null);
            id = -1;
            lblT1.setText(" " + longitud);
        }

    }

    private void listar2() {
        int longitud = clienteDao.listarClienteRetirado().size();
        if (longitud > 0) {
            //llave2 = true;

            Object[] colum = {"Id", "Nombre", "Apellido", "Dni", "estado"};
            modTabla2 = new DefaultTableModel(null, colum) {

                public boolean isCellEditable(int row, int colum) {
                    return false;
                }
            };
            for (Object[] fila : clienteDao.listarClienteRetirado()) {

                modTabla2.addRow(fila);

            }
            tblVista2.setModel(modTabla2);

            anchoColum2();
            cliente2 = tblVista2.getValueAt(0, 1).toString() + " " + tblVista2.getValueAt(0, 2).toString();
            txtCliente2.setText(cliente2);
            id2 = Integer.parseInt(tblVista2.getValueAt(0, 0).toString());
            lblT2.setText(" " + longitud);
        } else {
            // llave2 = false;

            modTabla2 = new DefaultTableModel();
            tblVista2.setModel(modTabla2);
            txtCliente2.setText(null);
            id2 = -1;
            lblT2.setText(" " + longitud);
        }

    }

    public void listarBusqueda(int x, String cadena) {
        int k = clienteDao.BuscarCliCeRa(x, cadena).size();
        if (k > 0) {
            llave1 = false;
            Object[] colum = {"Id", "Nombre", "Apellido", "Dni", "estado"};
            modTabla = new DefaultTableModel(null, colum) {

                public boolean isCellEditable(int row, int colum) {
                    return false;
                }
            };

            for (Object[] fila : clienteDao.BuscarCliCeRa(x, cadena)) {
                //Object[] fila = {c.getIdCliente(), c.getNombre(), c.getApellido(), c.getDni()};
                modTabla.addRow(fila);

            }
            tblVista.setModel(modTabla);

            anchoColum();
            cliente = tblVista.getValueAt(0, 1).toString() + " " + tblVista.getValueAt(0, 2).toString();
            txtCliente.setText(cliente);
            id = Integer.parseInt(tblVista.getValueAt(0, 0).toString());
            lblT1.setText(" " + k);
        } else {
            llave1 = true;
            lblT1.setText(" " + k);
            Mensaje.panelSms("No se encontraron Registros");

            //listar();
        }

    }

    public void listarBusqueda2(int x, String cadena) {
        int k = clienteDao.BuscarCliRe(x, cadena).size();
        if (k > 0) {
            llave2 = false;

            Object[] colum = {"Id", "Nombre", "Apellido", "Dni", "estado"};
            modTabla2 = new DefaultTableModel(null, colum) {

                public boolean isCellEditable(int row, int colum) {
                    return false;
                }
            };
            for (Object[] fila : clienteDao.BuscarCliRe(x, cadena)) {
                //Object[] fila = {c.getIdCliente(), c.getNombre(), c.getApellido(), c.getDni()};
                modTabla2.addRow(fila);

            }
            tblVista2.setModel(modTabla2);

            anchoColum2();
            cliente2 = tblVista2.getValueAt(0, 1).toString() + " " + tblVista2.getValueAt(0, 2).toString();
            txtCliente2.setText(cliente2);
            id2 = Integer.parseInt(tblVista2.getValueAt(0, 0).toString());
            lblT2.setText(" " + k);
        } else {
            llave2 = true;
            lblT2.setText(" " + k);
            Mensaje.panelSms("No se encontraron Registros");

        }

    }

    public void anchoColum() {
        tblVista.getColumnModel().getColumn(0).setMinWidth(30);
        tblVista.getColumnModel().getColumn(0).setMaxWidth(40);
        tblVista.getColumnModel().getColumn(1).setMinWidth(110);
        tblVista.getColumnModel().getColumn(1).setMaxWidth(120);
        tblVista.getColumnModel().getColumn(2).setMinWidth(110);
        tblVista.getColumnModel().getColumn(2).setMaxWidth(120);
        tblVista.getColumnModel().getColumn(3).setMinWidth(90);
        tblVista.getColumnModel().getColumn(3).setMaxWidth(100);
        tblVista.getColumnModel().getColumn(4).setMinWidth(130);
        tblVista.getColumnModel().getColumn(4).setMaxWidth(140);

    }

    public void anchoColum2() {
        tblVista2.getColumnModel().getColumn(0).setMinWidth(30);
        tblVista2.getColumnModel().getColumn(0).setMaxWidth(40);
        tblVista2.getColumnModel().getColumn(1).setMinWidth(110);
        tblVista2.getColumnModel().getColumn(1).setMaxWidth(120);
        tblVista2.getColumnModel().getColumn(2).setMinWidth(110);
        tblVista2.getColumnModel().getColumn(2).setMaxWidth(120);
        tblVista2.getColumnModel().getColumn(3).setMinWidth(90);
        tblVista2.getColumnModel().getColumn(3).setMaxWidth(100);
        tblVista2.getColumnModel().getColumn(4).setMinWidth(130);
        tblVista2.getColumnModel().getColumn(4).setMaxWidth(140);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblVista = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtCliente = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        cboBusCampo = new javax.swing.JComboBox();
        txtBuscar = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        btnDesabilitar = new javax.swing.JButton();
        btnRefresh1 = new javax.swing.JButton();
        btnSalir1 = new javax.swing.JButton();
        lblT1 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        cboBusCampo2 = new javax.swing.JComboBox();
        txtBuscar2 = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        btnHabilitar = new javax.swing.JButton();
        btnRefresh2 = new javax.swing.JButton();
        btnSalir2 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblVista2 = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtCliente2 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        lblT2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(102, 102, 102));
        setClosable(true);
        setIconifiable(true);
        setMinimumSize(new java.awt.Dimension(565, 651));
        setPreferredSize(new java.awt.Dimension(565, 651));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTabbedPane1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane2.setMinimumSize(new java.awt.Dimension(650, 700));
        jScrollPane2.setOpaque(false);

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
        jScrollPane2.setViewportView(tblVista);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(23, 191, 480, 267));

        jPanel3.setOpaque(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Cliente");

        txtCliente.setEditable(false);
        txtCliente.setBackground(new java.awt.Color(204, 255, 0));
        txtCliente.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txtCliente.setForeground(new java.awt.Color(255, 51, 51));
        txtCliente.setCaretColor(new java.awt.Color(255, 255, 255));
        txtCliente.setOpaque(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 115, 410, -1));

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(102, 102, 102)), "Buscar por:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel4.setOpaque(false);

        cboBusCampo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Nombre", "Apellido", "Dni" }));
        cboBusCampo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboBusCampoActionPerformed(evt);
            }
        });

        txtBuscar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtBuscar.setForeground(new java.awt.Color(255, 255, 255));
        txtBuscar.setCaretColor(new java.awt.Color(0, 255, 0));
        txtBuscar.setOpaque(false);
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cboBusCampo, 0, 119, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboBusCampo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(104, 25, 320, 70));

        jPanel5.setOpaque(false);
        jPanel5.setLayout(new java.awt.GridLayout(3, 1, 0, 2));

        btnDesabilitar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/status_offline.png"))); // NOI18N
        btnDesabilitar.setToolTipText("DESHABILITAR CLIENTE");
        btnDesabilitar.setOpaque(false);
        btnDesabilitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDesabilitarActionPerformed(evt);
            }
        });
        jPanel5.add(btnDesabilitar);

        btnRefresh1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/arrow_refresh.png"))); // NOI18N
        btnRefresh1.setToolTipText("REFRESH");
        btnRefresh1.setOpaque(false);
        btnRefresh1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefresh1ActionPerformed(evt);
            }
        });
        jPanel5.add(btnRefresh1);

        btnSalir1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/cancel.png"))); // NOI18N
        btnSalir1.setToolTipText("CANCELAR");
        btnSalir1.setOpaque(false);
        btnSalir1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalir1ActionPerformed(evt);
            }
        });
        jPanel5.add(btnSalir1);

        jPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 30, -1, 139));

        lblT1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblT1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.add(lblT1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 480, 80, 30));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Total registros");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 480, -1, -1));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/Encabezados/ftn1.png"))); // NOI18N
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -10, 530, 530));

        jTabbedPane1.addTab("Deshabilitar Cliente", jPanel1);

        jPanel2.setBackground(new java.awt.Color(102, 102, 102));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(153, 153, 153)), "Buscar por:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel6.setOpaque(false);

        cboBusCampo2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Nombre", "Apellido", "Dni" }));
        cboBusCampo2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboBusCampo2ActionPerformed(evt);
            }
        });

        txtBuscar2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtBuscar2.setForeground(new java.awt.Color(255, 255, 255));
        txtBuscar2.setCaretColor(new java.awt.Color(0, 255, 0));
        txtBuscar2.setOpaque(false);
        txtBuscar2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscar2KeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtBuscar2, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cboBusCampo2, 0, 119, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboBusCampo2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel2.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 30, 320, -1));

        jPanel7.setOpaque(false);
        jPanel7.setLayout(new java.awt.GridLayout(3, 1, 0, 2));

        btnHabilitar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/status_online.png"))); // NOI18N
        btnHabilitar.setToolTipText("HABILITAR CLIENTE");
        btnHabilitar.setOpaque(false);
        btnHabilitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHabilitarActionPerformed(evt);
            }
        });
        jPanel7.add(btnHabilitar);

        btnRefresh2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/arrow_refresh.png"))); // NOI18N
        btnRefresh2.setToolTipText("REFRESH");
        btnRefresh2.setOpaque(false);
        btnRefresh2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefresh2ActionPerformed(evt);
            }
        });
        jPanel7.add(btnRefresh2);

        btnSalir2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/cancel.png"))); // NOI18N
        btnSalir2.setToolTipText("CANCELAR");
        btnSalir2.setOpaque(false);
        btnSalir2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalir2ActionPerformed(evt);
            }
        });
        jPanel7.add(btnSalir2);

        jPanel2.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(432, 31, -1, 140));

        jScrollPane3.setMinimumSize(new java.awt.Dimension(650, 700));
        jScrollPane3.setOpaque(false);

        tblVista2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tblVista2.setForeground(new java.awt.Color(255, 255, 255));
        tblVista2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblVista2.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblVista2.setOpaque(false);
        tblVista2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblVista2MouseClicked(evt);
            }
        });
        tblVista2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblVista2KeyPressed(evt);
            }
        });
        jScrollPane3.setViewportView(tblVista2);

        jPanel2.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 190, 475, 267));

        jPanel8.setOpaque(false);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Cliente");

        txtCliente2.setEditable(false);
        txtCliente2.setBackground(new java.awt.Color(204, 255, 0));
        txtCliente2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txtCliente2.setForeground(new java.awt.Color(153, 255, 0));
        txtCliente2.setOpaque(false);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtCliente2, javax.swing.GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtCliente2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, -1, 50));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Total registros");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 480, -1, -1));

        lblT2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblT2.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(lblT2, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 480, 80, 30));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/Encabezados/ftn1.png"))); // NOI18N
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -10, 560, 530));

        jTabbedPane1.addTab("Habilitar Cliente", jPanel2);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 530, 550));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/Encabezados/habDesh.png"))); // NOI18N
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(-16, 0, 590, 46));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/Encabezados/ftn1.png"))); // NOI18N
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 44, 570, 580));

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
        //txtBuscar.setText(null);
        int x = cboBusCampo.getSelectedIndex();
        if (x == 2) {
            txtBuscar.setText(null);
            listarBusqueda(x, txtBuscar.getText().trim());
            txtBuscar.requestFocus();
        } else {
            listarBusqueda(x, txtBuscar.getText().trim());
            txtBuscar.requestFocus();
        }

        //txtPrueva.setText("index = "+x);
        // TODO add your handling code here:
    }//GEN-LAST:event_cboBusCampoActionPerformed

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
        int l = clienteDao.listarCeRa().size();
        if (l > 0) {
            int x = cboBusCampo.getSelectedIndex();
            listarBusqueda(x, txtBuscar.getText().trim());
            if (llave1 == true) {
                txtBuscar.setText(Cadena.eliminarUltCaracter(txtBuscar.getText().trim()));
            }

        } else {
            txtBuscar.setText(null);

        }

        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarKeyReleased

    private void btnDesabilitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDesabilitarActionPerformed
        if (id != -1) {
            boolean f = clienteDao.desabilitarCliente(id);
            if (f == true) {
                Mensaje.panelSms("Cliente Retirado (Desabilitado)");
            } else {
                Mensaje.panelSms("No se Desabilito Cliente");
            }
            listar();
            listar2();
        } else {
            Mensaje.panelSms("No Hay Registros");
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDesabilitarActionPerformed

    private void btnRefresh1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefresh1ActionPerformed
        txtBuscar.setText(null);
        cboBusCampo.setSelectedIndex(0);
        listar();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnRefresh1ActionPerformed

    private void btnSalir1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalir1ActionPerformed
        dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSalir1ActionPerformed

    private void cboBusCampo2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboBusCampo2ActionPerformed
        //txtBuscar2.setText(null);
        int x = cboBusCampo2.getSelectedIndex();
        if (x == 2) {
            txtBuscar2.setText(null);
            listarBusqueda2(x, txtBuscar2.getText().trim());
            txtBuscar2.requestFocus();
        } else {
            listarBusqueda2(x, txtBuscar2.getText().trim());
            txtBuscar2.requestFocus();
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_cboBusCampo2ActionPerformed

    private void txtBuscar2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscar2KeyReleased
        int l = clienteDao.listarClienteRetirado().size();
        if (l > 0) {
            int x = cboBusCampo2.getSelectedIndex();
            listarBusqueda2(x, txtBuscar2.getText().trim());

            if (llave2 == true) {
                txtBuscar2.setText(Cadena.eliminarUltCaracter(txtBuscar2.getText().trim()));
            }

        } else {
            txtBuscar2.setText(null);
        }

// TODO add your handling code here:
    }//GEN-LAST:event_txtBuscar2KeyReleased

    private void btnHabilitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHabilitarActionPerformed
        if (id2 != -1) {
            boolean f = clienteDao.habilitarCliente(id2, 4);
            if (f == true) {
                Mensaje.panelSms("Cliente habilitado");
            } else {
                Mensaje.panelSms("No se hablito Cliente");
            }
            listar2();
            listar();
        } else {
            Mensaje.panelSms("No Hay Registros");
        }

// TODO add your handling code here:
    }//GEN-LAST:event_btnHabilitarActionPerformed

    private void btnRefresh2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefresh2ActionPerformed
        txtBuscar2.setText(null);
        cboBusCampo2.setSelectedIndex(0);
        listar2();

// TODO add your handling code here:
    }//GEN-LAST:event_btnRefresh2ActionPerformed

    private void btnSalir2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalir2ActionPerformed
        dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSalir2ActionPerformed

    private void tblVista2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblVista2MouseClicked
        if (evt.getClickCount() == 2) {
            int i = tblVista2.getSelectedRow();
            id2 = (int) tblVista2.getValueAt(i, 0);
            cliente2 = tblVista2.getValueAt(i, 1).toString() + " " + tblVista2.getValueAt(i, 2).toString();
            txtCliente2.setText(cliente2);

        }

// TODO add your handling code here:
    }//GEN-LAST:event_tblVista2MouseClicked

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

    private void tblVista2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblVista2KeyPressed
        int k = tblVista2.getSelectedRow();
        if (evt.getKeyCode() == 38) {
            k = k - 1;
            if (k >= 0) {

                id2 = (int) tblVista2.getValueAt(k, 0);
                cliente2 = tblVista2.getValueAt(k, 1).toString() + " " + tblVista2.getValueAt(k, 2).toString();
                txtCliente2.setText(cliente2);
            }

        } else if (evt.getKeyCode() == 40) {
            k = k + 1;
            if (k < tblVista2.getRowCount()) {
                id2 = (int) tblVista2.getValueAt(k, 0);
                cliente2 = tblVista2.getValueAt(k, 1).toString() + " " + tblVista2.getValueAt(k, 2).toString();
                txtCliente2.setText(cliente2);

            }

        }

// TODO add your handling code here:
    }//GEN-LAST:event_tblVista2KeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDesabilitar;
    private javax.swing.JButton btnHabilitar;
    private javax.swing.JButton btnRefresh1;
    private javax.swing.JButton btnRefresh2;
    private javax.swing.JButton btnSalir1;
    private javax.swing.JButton btnSalir2;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cboBusCampo;
    private javax.swing.JComboBox cboBusCampo2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblT1;
    private javax.swing.JLabel lblT2;
    private javax.swing.JTable tblVista;
    private javax.swing.JTable tblVista2;
    public transient javax.swing.JTextField txtBuscar;
    public transient javax.swing.JTextField txtBuscar2;
    private javax.swing.JTextField txtCliente;
    private javax.swing.JTextField txtCliente2;
    // End of variables declaration//GEN-END:variables
}
