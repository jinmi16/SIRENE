package com.sirene.GUI;

import com.sirene.Bean.Personal;
import com.sirene.Bean.Usuario;
import com.sirene.Dao.Impl.DaoFactory;
//import com.sirene.Dao.Impl.DaoPersonal;
//import com.sirene.Dao.Impl.DaoUser;
import com.sirene.Dao.Impl.Mensaje;
import com.sirene.Dao.PersonalDao;
import com.sirene.Dao.UsuarioDao;
import static com.sirene.Utilitarios.Constantes.PERSONAL;
import static com.sirene.Utilitarios.Constantes.USUARIO;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class RegPersonal extends javax.swing.JInternalFrame {

    DefaultTableModel modTabla;
    public static DefaultTableModel modTabla2;
    public static int idPer = -1, idUser = -1;
    public final PersonalDao personalDao;
    public final UsuarioDao usuarioDao;

    public RegPersonal() {
        initComponents();

        DaoFactory factory = DaoFactory.getInstance();
        personalDao = factory.getPersonalDao(PERSONAL);
        usuarioDao=factory.getUsuarioDao(USUARIO);

        localisar(434, 10);

        setTitle("Registro_Personal");
        listarPersonal();
        botonUserEstado();
        transparenciaTabla();
        transparenciaTabla2();
        enabledCampos(false);
    }

    private void localisar(int x, int y) {
        int w_screen = getToolkit().getScreenSize().width;
        int ka = (w_screen / 2) - (x / 2);
        setLocation(ka, y);

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

    private void refresh() {

        txtApePer.setText(null);
        txtDniPer.setText(null);
        txtNOmPer.setText(null);
        txtUsuario.setText(null);
        idPer = -1;
        idUser = -1;
        enabledCampos(false);
        chkCampos.setSelected(false);
        botonUserEstado();
        while (modTabla2.getRowCount() > 0) {
            modTabla2.removeRow(0);
        }

    }

    public void eliminarCuentaUser() {
        if (idUser != -1) {
            usuarioDao.eliminar(idUser);

        } else {
            Mensaje.panelSms("SELECCIONAR UN USUARIO");
        }

    }

    private void listarPersonal() {
        Object[] colum = {"Id", "Nombre", "Apellido", "Dni"};
        // con este codigo adicional al modelo de yabla consigo que las celdas y columnas no sean 
        // editables
        modTabla = new DefaultTableModel(null, colum) {

            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (Personal p : personalDao.listarPer()) {
            Object[] fila = {p.getIdPersonal(), p.getNombre(), p.getApellido(), p.getDni()};
            modTabla.addRow(fila);
        }

        tblVista.setModel(modTabla);
        anchoColum();

    }

    public static void listarUsuario(int i) {
        Object[] colum = {"Id", "Usuario", "Tipo_Usuario"};
        // con este codigo adicional al modelo de yabla consigo que las celdas y columnas no sean 
        // editables
        modTabla2 = new DefaultTableModel(null, colum) {

            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        //int k = DaoUser.listarUsuario_idPers(i).size();
        int k = DaoFactory.getUsuarioDaoStatic(USUARIO).listarUsuario_idPers(i).size();
        if (k > 0) {
            for (Usuario p : DaoFactory.getUsuarioDaoStatic(USUARIO).listarUsuario_idPers(i)) {
                Object[] fila = {p.getIdUser(), p.getUser(), p.getTipoUser()};
                modTabla2.addRow(fila);
            }

            tblVista2.setModel(modTabla2);
            txtUsuario.setText(tblVista2.getValueAt(0, 1).toString());
            tblVista2.getColumnModel().getColumn(0).setMinWidth(25);
            tblVista2.getColumnModel().getColumn(0).setMaxWidth(35);
            tblVista2.getColumnModel().getColumn(1).setMinWidth(100);
            tblVista2.getColumnModel().getColumn(1).setMaxWidth(110);
            tblVista2.getColumnModel().getColumn(2).setMinWidth(100);
            tblVista2.getColumnModel().getColumn(2).setMaxWidth(110);

        } else {
            tblVista2.setModel(modTabla2);
            txtUsuario.setText(null);

        }

    }

    private void anchoColum() {

        tblVista.getColumnModel().getColumn(0).setMinWidth(25);
        tblVista.getColumnModel().getColumn(0).setMaxWidth(35);
        tblVista.getColumnModel().getColumn(1).setMinWidth(105);
        tblVista.getColumnModel().getColumn(1).setMaxWidth(120);
        tblVista.getColumnModel().getColumn(2).setMinWidth(105);
        tblVista.getColumnModel().getColumn(2).setMaxWidth(120);
        tblVista.getColumnModel().getColumn(3).setMinWidth(60);
        tblVista.getColumnModel().getColumn(3).setMaxWidth(70);

    }

    public void limpiar() {
        txtApePer.setText(null);
        txtDniPer.setText(null);
        txtNOmPer.setText(null);
        idPer = -1;
        botonUserEstado();
        //enabledCampos(false);

    }

    public void registrarPeronal() {
        Personal p = new Personal();
        p.setNombre(txtNOmPer.getText().trim());
        p.setApellido(txtApePer.getText().trim());
        p.setDni(txtDniPer.getText().trim());

        boolean f = personalDao.registrar(p);
        if (f == true) {
            Mensaje.panelSms("Personal Registrado");

        }

    }

    private void botonUserEstado() {
        if (idPer != -1) {
            btnCambiarPasw.setEnabled(true);
            btnNewUser.setEnabled(true);
            btnEliminarUser.setEnabled(true);
        } else {
            btnCambiarPasw.setEnabled(false);
            btnNewUser.setEnabled(false);
            btnEliminarUser.setEnabled(false);
        }

    }

    private void enabledCampos(boolean f) {
        txtApePer.setEditable(f);
        txtNOmPer.setEditable(f);
        txtDniPer.setEditable(f);

    }

    public void editarPeronal() {
        Personal p = new Personal();
        p.setNombre(txtNOmPer.getText().trim());
        p.setApellido(txtApePer.getText().trim());
        p.setDni(txtDniPer.getText().trim());
        p.setIdPersonal(idPer);
        if (idPer != -1) {
            boolean f = personalDao.editar(p);
            if (f == true) {
                Mensaje.panelSms("Personal Editado");

            }
        } else {
            Mensaje.panelSms("Selecionar un personal existente");
            limpiar();
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel6 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btnRegistrar = new javax.swing.JButton();
        btnModif = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtNOmPer = new javax.swing.JTextField();
        txtApePer = new javax.swing.JTextField();
        txtDniPer = new javax.swing.JFormattedTextField();
        chkCampos = new javax.swing.JCheckBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblVista = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblVista2 = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        btnNewUser = new javax.swing.JButton();
        btnCambiarPasw = new javax.swing.JButton();
        btnEliminarUser = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel7 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(0, 0, 51));
        setClosable(true);
        setIconifiable(true);
        setMinimumSize(new java.awt.Dimension(435, 693));
        setPreferredSize(new java.awt.Dimension(435, 693));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/Encabezados/RP2.png"))); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(440, 50));
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 0, 460, 50));

        jPanel3.setOpaque(false);
        jPanel3.setLayout(new java.awt.GridLayout(5, 1, 5, 5));

        btnRegistrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/group_add.png"))); // NOI18N
        btnRegistrar.setToolTipText("REGISTRAR NUEVO PERSONAL");
        btnRegistrar.setOpaque(false);
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });
        jPanel3.add(btnRegistrar);

        btnModif.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/group_edit.png"))); // NOI18N
        btnModif.setToolTipText("MODIFICAR PERSONAL");
        btnModif.setOpaque(false);
        btnModif.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModifActionPerformed(evt);
            }
        });
        jPanel3.add(btnModif);

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/group_delete.png"))); // NOI18N
        btnEliminar.setToolTipText("ELIMINAR PERSONAL");
        btnEliminar.setOpaque(false);
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        jPanel3.add(btnEliminar);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/arrow_refresh.png"))); // NOI18N
        jButton1.setToolTipText("REFRESH");
        jButton1.setOpaque(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton1);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/cancel.png"))); // NOI18N
        jButton2.setToolTipText("CERRAR VENTANA");
        jButton2.setOpaque(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton2);

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 90, 60, 210));

        jPanel2.setOpaque(false);

        jPanel1.setOpaque(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 204, 0));
        jLabel1.setText("Nombre");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 204, 0));
        jLabel2.setText("Apellido");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 204, 0));
        jLabel3.setText("Dni");

        txtNOmPer.setEditable(false);
        txtNOmPer.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txtNOmPer.setForeground(new java.awt.Color(255, 255, 255));
        txtNOmPer.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(102, 102, 102)));
        txtNOmPer.setCaretColor(new java.awt.Color(0, 255, 0));
        txtNOmPer.setOpaque(false);
        txtNOmPer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNOmPerActionPerformed(evt);
            }
        });

        txtApePer.setEditable(false);
        txtApePer.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txtApePer.setForeground(new java.awt.Color(255, 255, 255));
        txtApePer.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(102, 102, 102)));
        txtApePer.setCaretColor(new java.awt.Color(0, 255, 0));
        txtApePer.setOpaque(false);
        txtApePer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtApePerActionPerformed(evt);
            }
        });

        txtDniPer.setEditable(false);
        txtDniPer.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(102, 102, 102)));
        txtDniPer.setForeground(new java.awt.Color(255, 255, 255));
        try {
            txtDniPer.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("########")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtDniPer.setCaretColor(new java.awt.Color(0, 255, 0));
        txtDniPer.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txtDniPer.setOpaque(false);
        txtDniPer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDniPerActionPerformed(evt);
            }
        });

        chkCampos.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        chkCampos.setForeground(new java.awt.Color(0, 204, 0));
        chkCampos.setText("Desbloquear Campos");
        chkCampos.setOpaque(false);
        chkCampos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkCamposActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtDniPer, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 122, Short.MAX_VALUE))
                            .addComponent(txtApePer))
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(chkCampos)
                                .addGap(19, 91, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtNOmPer)
                                .addContainerGap())))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(chkCampos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1)
                            .addComponent(txtNOmPer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2))
                    .addComponent(txtApePer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(txtDniPer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(23, Short.MAX_VALUE))
        );

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

        jScrollPane3.setMinimumSize(new java.awt.Dimension(650, 700));
        jScrollPane3.setOpaque(false);

        tblVista2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
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

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 204, 0));
        jLabel4.setText("Usuario");

        txtUsuario.setEditable(false);
        txtUsuario.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtUsuario.setForeground(new java.awt.Color(255, 255, 255));
        txtUsuario.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtUsuario.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(102, 102, 102)));
        txtUsuario.setOpaque(false);

        jPanel4.setOpaque(false);
        jPanel4.setLayout(new java.awt.GridLayout(3, 0, 2, 2));

        btnNewUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/newUser33.png"))); // NOI18N
        btnNewUser.setToolTipText("CREAR  NUEVO USUARIO");
        btnNewUser.setOpaque(false);
        btnNewUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewUserActionPerformed(evt);
            }
        });
        jPanel4.add(btnNewUser);

        btnCambiarPasw.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/key.png"))); // NOI18N
        btnCambiarPasw.setToolTipText("CAMBIAR CONTRASEÑA");
        btnCambiarPasw.setOpaque(false);
        btnCambiarPasw.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCambiarPaswActionPerformed(evt);
            }
        });
        jPanel4.add(btnCambiarPasw);

        btnEliminarUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/status_busy.png"))); // NOI18N
        btnEliminarUser.setToolTipText("ELIMINAR USUARIO");
        btnEliminarUser.setOpaque(false);
        btnEliminarUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarUserActionPerformed(evt);
            }
        });
        jPanel4.add(btnEliminarUser);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 18, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 243, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 330, 600));
        getContentPane().add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 450, 400, 10));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/Encabezados/ft2.png"))); // NOI18N
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 430, 640));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtApePerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtApePerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtApePerActionPerformed

    private void tblVistaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblVistaMouseClicked

        if (evt.getClickCount() == 2) {
            int i = tblVista.getSelectedRow();
            idPer = (int) tblVista.getValueAt(i, 0);
            txtNOmPer.setText(tblVista.getValueAt(i, 1).toString());
            txtApePer.setText(tblVista.getValueAt(i, 2).toString());
            txtDniPer.setText(tblVista.getValueAt(i, 3).toString());

            listarUsuario(idPer);
            botonUserEstado();
            if (tblVista2.getRowCount() > 0) {

                idUser = (int) tblVista2.getValueAt(0, 0);
                txtUsuario.setText(tblVista2.getValueAt(0, 1).toString());
            }
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_tblVistaMouseClicked

    private void tblVistaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblVistaKeyPressed
        int k = tblVista.getSelectedRow();
        if (evt.getKeyCode() == 38) {
            k = k - 1;
            if (k >= 0) {
                idPer = (int) tblVista.getValueAt(k, 0);
                txtNOmPer.setText(tblVista.getValueAt(k, 1).toString());
                txtApePer.setText(tblVista.getValueAt(k, 2).toString());
                txtDniPer.setText(tblVista.getValueAt(k, 3).toString());

                listarUsuario(idPer);
                if (tblVista2.getRowCount() > 0) {

                    idUser = (int) tblVista2.getValueAt(0, 0);
                    txtUsuario.setText(tblVista2.getValueAt(0, 1).toString());
                }
                botonUserEstado();

            }

        } else if (evt.getKeyCode() == 40) {
            k = k + 1;
            if (k < tblVista.getRowCount()) {
                idPer = (int) tblVista.getValueAt(k, 0);
                txtNOmPer.setText(tblVista.getValueAt(k, 1).toString());
                txtApePer.setText(tblVista.getValueAt(k, 2).toString());
                txtDniPer.setText(tblVista.getValueAt(k, 3).toString());

                listarUsuario(idPer);
                botonUserEstado();
                if (tblVista2.getRowCount() > 0) {

                    idUser = (int) tblVista2.getValueAt(0, 0);
                    txtUsuario.setText(tblVista2.getValueAt(0, 1).toString());
                }
            }

        }

        // TODO add your handling code here:
    }//GEN-LAST:event_tblVistaKeyPressed

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        if (txtApePer.getText().trim().length() > 0 && txtNOmPer.getText().trim().length() > 0 && txtDniPer.getText().trim().length() > 0) {
            registrarPeronal();
            listarPersonal();
            limpiar();

        } else {

            Mensaje.panelSms("LLENE TODOS LOS CAPOS");
            limpiar();
            txtNOmPer.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void btnModifActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModifActionPerformed
        if (txtApePer.getText().trim().length() > 0 && txtNOmPer.getText().trim().length() > 0 && txtDniPer.getText().trim().length() > 0) {
            editarPeronal();
            listarPersonal();
            limpiar();

        } else {

            Mensaje.panelSms("LLENE TODOS LOS CAPOS");
            limpiar();
            txtNOmPer.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_btnModifActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed

        if (idPer != -1) {
            int i = JOptionPane.showConfirmDialog(this, "Esta seguro de eliminar Registro?", "MENSAJE DE CONFIRMACION", JOptionPane.OK_OPTION, JOptionPane.NO_OPTION);
            if (i == 0) {
                boolean f = personalDao.eliminar(idPer);

                limpiar();
                listarPersonal();
                while (modTabla2.getRowCount() > 0) {
                    modTabla2.removeRow(0);
                }
                txtUsuario.setText(null);
            } else {
                limpiar();
            }
        } else {
            Mensaje.panelSms("Seleccione un registro existente");
            limpiar();
        }
        // DaoServidor.eliminarRegistro(id);

        // TODO add your handling code here:
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void txtNOmPerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNOmPerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNOmPerActionPerformed

    private void tblVista2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblVista2MouseClicked

        if (evt.getClickCount() == 2) {
            int i = tblVista2.getSelectedRow();
            idUser = (int) tblVista2.getValueAt(i, 0);
            txtUsuario.setText(tblVista2.getValueAt(i, 1).toString());

        }

// TODO add your handling code here:
    }//GEN-LAST:event_tblVista2MouseClicked

    private void tblVista2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblVista2KeyPressed
        int k = tblVista2.getSelectedRow();
        if (evt.getKeyCode() == 38) {
            k = k - 1;
            if (k >= 0) {
                idUser = (int) tblVista2.getValueAt(k, 0);
                txtUsuario.setText(tblVista2.getValueAt(k, 1).toString());

            }

        } else if (evt.getKeyCode() == 40) {
            k = k + 1;
            if (k < tblVista2.getRowCount()) {
                idUser = (int) tblVista2.getValueAt(k, 0);
                txtUsuario.setText(tblVista2.getValueAt(k, 1).toString());
            }

        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tblVista2KeyPressed

    private void btnNewUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewUserActionPerformed
        RegUsuario vu = new RegUsuario(null, true);
        RegUsuario.idP = idPer;
        vu.setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_btnNewUserActionPerformed

    private void btnCambiarPaswActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCambiarPaswActionPerformed
        EditarUsuario e = new EditarUsuario(null, true);
        e.isUs = idUser;
        e.txtUsuario.setText(txtUsuario.getText());
        e.setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCambiarPaswActionPerformed

    private void chkCamposActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkCamposActionPerformed
        if (chkCampos.isSelected()) {
            enabledCampos(true);
            txtNOmPer.requestFocus();

        } else {
            enabledCampos(false);

        }

// TODO add your handling code here:
    }//GEN-LAST:event_chkCamposActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        refresh();

// TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtDniPerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDniPerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDniPerActionPerformed

    private void btnEliminarUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarUserActionPerformed
        int i = JOptionPane.showConfirmDialog(this, "Esta seguro de eliminar usuario?", "MENSAJE DE CONFIRMACION", JOptionPane.OK_OPTION, JOptionPane.NO_OPTION);
        if (i == 0) {
            eliminarCuentaUser();
            listarUsuario(idPer);

        } else {
            //limpiar();
            refresh();
        }

// TODO add your handling code here:
    }//GEN-LAST:event_btnEliminarUserActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCambiarPasw;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnEliminarUser;
    private javax.swing.JButton btnModif;
    private javax.swing.JButton btnNewUser;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JCheckBox chkCampos;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable tblVista;
    public static javax.swing.JTable tblVista2;
    private javax.swing.JTextField txtApePer;
    private javax.swing.JFormattedTextField txtDniPer;
    private javax.swing.JTextField txtNOmPer;
    public static javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
