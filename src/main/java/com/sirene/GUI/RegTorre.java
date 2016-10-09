/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirene.GUI;

import com.sirene.Bean.Torre;
import com.sirene.Bean.Usuario;
import com.sirene.Dao.Impl.DaoFactory;
//import com.sirene.Dao.Impl.DaoTorre;
import com.sirene.Dao.Impl.Mensaje;
import com.sirene.Dao.TorreDao;
import com.sirene.Utilitarios.Cadena;
import static com.sirene.Utilitarios.Constantes.TORRE;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class RegTorre extends javax.swing.JInternalFrame {

    Usuario usuario;
    DefaultTableModel modTabla;
    int id = -1;
    ArrayList<Torre> listatorre;
    boolean llave = false, limpiar = false;
    public final TorreDao torreDao;

    public RegTorre() {
        initComponents();

        DaoFactory factory = DaoFactory.getInstance();
        torreDao = factory.getTorreDao(TORRE);

        localisar(630, 12);
        setTitle("Registro_Torre");
        transparenciaTabla();
        listarTorre();

        Controller();
        activarBusqueda();

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
        jScrollPane1.setOpaque(false);
        jScrollPane1.getViewport().setOpaque(false);
        //Por lo menos, usted puede quitar las líneas de la cuadrícula:
        // tblVista.setShowGrid(false);
        // int k=cboAntenaEnlace.getItemCount();

    }

    public void setUser(Usuario us) {
        usuario = us;


    }

    public void validarLongTexto(JTextField txt, int l, java.awt.event.KeyEvent evt) {

        if (txt.getText().trim().length() >= l) {
            getToolkit().beep();
            evt.consume();
        }

    }

    private void activarBusqueda() {
        if (chkBusqueda.isSelected()) {
            pnlBusqueda.setVisible(true);
        } else {
            limpiar(true);
            pnlBusqueda.setVisible(false);
        }

    }

    private void Controller() {

        btnModif.setEnabled(false);
        btnRegistrar.setEnabled(false);
        btnEliminar.setEnabled(false);
        txtNom.setEditable(false);
        txtUbicacion.setEditable(false);
        txtDueñoL.setEditable(false);
        txtTelefono.setEditable(false);
        txtPago.setEditable(false);
        id = -1;

    }

    public void habilitarController() {
        if (usuario.getIdTipoUser() == 1) {
            btnModif.setEnabled(true);
            btnRegistrar.setEnabled(true);
            btnEliminar.setEnabled(true);
            txtNom.setEditable(true);
            txtUbicacion.setEditable(true);
            txtDueñoL.setEditable(true);
            txtTelefono.setEditable(true);
            txtPago.setEditable(true);

        } else {
            Mensaje.panelSms("NO TIENE EL PRIVILEGIO ADMINISTRATIVO");
            chkB.setSelected(true);
        }

    }

    public void limpiar(boolean f) {
        if (f == true) {
            txtNom.setText(null);
            txtUbicacion.setText(null);
            txtDueñoL.setText(null);
            txtTelefono.setText(null);
            txtPago.setText(null);
            txtBuscar.setText(null);
            id = -1;
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
    //---------------------------------------------------

    private void anchoColum() {

        tblVista.getColumnModel().getColumn(0).setMinWidth(30);
        tblVista.getColumnModel().getColumn(0).setMaxWidth(40);

    }

    private void listarTorre() {
        Object[] colum = {"ID", "Nombre", "Ubicacion", "Dueño_Local", "Telefono", "Pago"};
        //codigo para que la tabla no sea editable
        modTabla = new DefaultTableModel(null, colum) {

            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        listatorre = torreDao.listar();
        if (listatorre.size() > 0) {
            for (Torre t : listatorre) {
                Object[] fila = {t.getIdTorre(), t.getNombre(), t.getUbicacion(), t.getDueñoLocal(), t.getTelefono(), t.getPago()};
                modTabla.addRow(fila);
            }
            tblVista.setModel(modTabla);

            anchoColum();
            lblTotal.setText("" + listatorre.size());
        } else {
            lblTotal.setText("" + listatorre.size());
            tblVista.setModel(modTabla);

        }

    }

    private void buscarTorre() {
        Object[] colum = {"ID", "Nombre", "Ubicacion", "Dueño_Local", "Telefono", "Pago"};
        //codigo para que la tabla no sea editable
        modTabla = new DefaultTableModel(null, colum) {

            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        listatorre = torreDao.buscar(txtBuscar.getText());

        if (listatorre.size() > 0) {
            llave = false;
            for (Torre t : listatorre) {
                Object[] fila = {t.getIdTorre(), t.getNombre(), t.getUbicacion(), t.getDueñoLocal(), t.getTelefono(), t.getPago()};
                modTabla.addRow(fila);
            }
            tblVista.setModel(modTabla);

            anchoColum();

            //**********
            id = (int) tblVista.getValueAt(0, 0);
            txtNom.setText(tblVista.getValueAt(0, 1).toString());
            txtUbicacion.setText(tblVista.getValueAt(0, 2).toString());
            txtDueñoL.setText(tblVista.getValueAt(0, 3).toString());
            txtTelefono.setText(tblVista.getValueAt(0, 4).toString());
            txtPago.setText(tblVista.getValueAt(0, 5).toString());
            lblTotal.setText("" + listatorre.size());
        } else {
            llave = true;
            // Mensaje.panelSms("Registro no encontrado");
            //txtBuscar.setText(null);
            // tblVista.setModel(modTabla);
            lblTotal.setText("" + listatorre.size());
            txtNom.setText(null);
            txtUbicacion.setText(null);
            txtDueñoL.setText(null);
            txtTelefono.setText(null);
            txtPago.setText(null);

            id = -1;
        }

    }

    private void registro() {
        if (txtNom.getText().trim().length() == 0 || txtUbicacion.getText().trim().length() == 0) {
            Mensaje.panelSms("LLENE LOS CAMPOS");
            limpiar(false);
        } else {
            try {
                Torre t = new Torre();
                t.setNombre(txtNom.getText());
                t.setUbicacion(txtUbicacion.getText());
                t.setDueñoLocal(txtDueñoL.getText());
                t.setTelefono(txtTelefono.getText());
                if (txtPago.getText().trim().length() > 0) {
                    t.setPago(Float.parseFloat(txtPago.getText()));
                }

                torreDao.registrar(t);
                listarTorre();
                limpiar(true);
            } catch (Exception e) {
                System.out.println("ERROR : " + e);
            }

        }

    }

    private void actualisar() {
        if (txtNom.getText().trim().length() == 0 || txtUbicacion.getText().trim().length() == 0) {
            Mensaje.panelSms("LLENE LOS CAMPOS");

        } else if (id != -1) {
            Torre t = new Torre();
            t.setIdTorre(id);
            t.setNombre(txtNom.getText());
            t.setUbicacion(txtUbicacion.getText());
            t.setDueñoLocal(txtDueñoL.getText());
            t.setTelefono(txtTelefono.getText());
            if (txtPago.getText().trim().length() > 0) {
                t.setPago(Float.parseFloat(txtPago.getText()));
            }

            torreDao.modificar(t);
            id = -1;
            listarTorre();
            limpiar(true);
        } else {
            limpiar(true);
            Mensaje.panelSms("SELECCIONE UN ITEN EXIXTENTE PARA MODIFICAR");
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtNom = new javax.swing.JTextField();
        txtUbicacion = new javax.swing.JTextField();
        txtDueñoL = new javax.swing.JTextField();
        txtTelefono = new javax.swing.JTextField();
        txtPago = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        chkBusqueda = new javax.swing.JCheckBox();
        btnRegistrar = new javax.swing.JButton();
        btnRefrshe = new javax.swing.JButton();
        btnModif = new javax.swing.JButton();
        btnCerrar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        chkB = new javax.swing.JCheckBox();
        chkD = new javax.swing.JCheckBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblVista = new javax.swing.JTable();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel7 = new javax.swing.JLabel();
        pnlBusqueda = new javax.swing.JPanel();
        txtBuscar = new javax.swing.JTextField();
        lblTotal = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setMinimumSize(new java.awt.Dimension(630, 645));
        setPreferredSize(new java.awt.Dimension(630, 645));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setOpaque(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Nombre(*) ");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Ubicacion(*)");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Dueño Local");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Telefono");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Pago");

        txtNom.setBackground(new java.awt.Color(204, 204, 204));
        txtNom.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtNom.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNomKeyTyped(evt);
            }
        });

        txtUbicacion.setBackground(new java.awt.Color(204, 204, 204));
        txtUbicacion.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtUbicacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtUbicacionKeyTyped(evt);
            }
        });

        txtDueñoL.setBackground(new java.awt.Color(204, 204, 204));
        txtDueñoL.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtDueñoL.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDueñoLKeyTyped(evt);
            }
        });

        txtTelefono.setBackground(new java.awt.Color(204, 204, 204));
        txtTelefono.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtTelefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefonoKeyTyped(evt);
            }
        });

        txtPago.setBackground(new java.awt.Color(204, 204, 204));
        txtPago.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtPago.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPagoKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(17, 17, 17)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtUbicacion)
                            .addComponent(txtNom)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtTelefono, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtPago, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtDueñoL))))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtNom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtUbicacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtDueñoL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 380, 150));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setOpaque(false);
        jPanel4.setLayout(new java.awt.GridLayout(3, 2, 0, 4));

        chkBusqueda.setBackground(new java.awt.Color(255, 255, 255));
        chkBusqueda.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        chkBusqueda.setForeground(new java.awt.Color(0, 204, 0));
        chkBusqueda.setText("Busqueda");
        chkBusqueda.setToolTipText("BUSCAR TORRES");
        chkBusqueda.setOpaque(false);
        chkBusqueda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkBusquedaActionPerformed(evt);
            }
        });
        jPanel4.add(chkBusqueda);

        btnRegistrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/table_add.png"))); // NOI18N
        btnRegistrar.setToolTipText("REGISTRAR TORRE");
        btnRegistrar.setOpaque(false);
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });
        jPanel4.add(btnRegistrar);

        btnRefrshe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/arrow_refresh.png"))); // NOI18N
        btnRefrshe.setToolTipText("REFRESH");
        btnRefrshe.setOpaque(false);
        btnRefrshe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefrsheActionPerformed(evt);
            }
        });
        jPanel4.add(btnRefrshe);

        btnModif.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/table_edit.png"))); // NOI18N
        btnModif.setToolTipText("MODIFICAR TORRE");
        btnModif.setOpaque(false);
        btnModif.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModifActionPerformed(evt);
            }
        });
        jPanel4.add(btnModif);

        btnCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/cancel.png"))); // NOI18N
        btnCerrar.setToolTipText(" CERRAR");
        btnCerrar.setOpaque(false);
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });
        jPanel4.add(btnCerrar);

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/table_delete.png"))); // NOI18N
        btnEliminar.setToolTipText("ELIMINAR");
        btnEliminar.setOpaque(false);
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        jPanel4.add(btnEliminar);

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 140, 178, 130));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setOpaque(false);
        jPanel5.setLayout(null);

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
        jPanel5.add(chkB);
        chkB.setBounds(7, 8, 100, 23);

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
        jPanel5.add(chkD);
        chkD.setBounds(100, 10, 120, 23);

        getContentPane().add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 80, 220, 40));

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

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, 590, 250));
        getContentPane().add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, 590, 10));

        jLabel7.setFont(new java.awt.Font("Arial Black", 1, 36)); // NOI18N
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/Encabezados/regtorres.png"))); // NOI18N
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, -10, 630, 90));

        pnlBusqueda.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(153, 153, 153)), "Buscar por Nombre", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 14), new java.awt.Color(255, 204, 0))); // NOI18N
        pnlBusqueda.setOpaque(false);

        txtBuscar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtBuscar.setForeground(new java.awt.Color(255, 204, 0));
        txtBuscar.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtBuscar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        txtBuscar.setCaretColor(new java.awt.Color(255, 204, 0));
        txtBuscar.setOpaque(false);
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout pnlBusquedaLayout = new javax.swing.GroupLayout(pnlBusqueda);
        pnlBusqueda.setLayout(pnlBusquedaLayout);
        pnlBusquedaLayout.setHorizontalGroup(
            pnlBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlBusquedaLayout.createSequentialGroup()
                .addContainerGap(158, Short.MAX_VALUE)
                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlBusquedaLayout.setVerticalGroup(
            pnlBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBusquedaLayout.createSequentialGroup()
                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 19, Short.MAX_VALUE))
        );

        getContentPane().add(pnlBusqueda, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 370, 60));

        lblTotal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTotal.setForeground(new java.awt.Color(0, 204, 0));
        getContentPane().add(lblTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 570, 100, 20));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 204, 0));
        jLabel9.setText("Total registros :");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 570, 120, -1));

        jLabel8.setBackground(new java.awt.Color(51, 51, 51));
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/Encabezados/ftn101.png"))); // NOI18N
        jLabel8.setOpaque(true);
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 24, 640, 600));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRefrsheActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefrsheActionPerformed
        limpiar(true);
        listarTorre();

        chkB.setSelected(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_btnRefrsheActionPerformed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        registro();

        // TODO add your handling code here:
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void btnModifActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModifActionPerformed
        actualisar();

        // TODO add your handling code here:
    }//GEN-LAST:event_btnModifActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed

        if (id != -1) {
            int i = JOptionPane.showConfirmDialog(this, "Esta seguro de eliminar Registro?", "MENSAJE DE CONFIRMACION", JOptionPane.OK_OPTION, JOptionPane.NO_OPTION);
            if (i == 0) {
                torreDao.eliminar(id);
                id = -1;
                limpiar(true);
                listarTorre();
            } else {
                limpiar(true);
            }
        } else {
            Mensaje.panelSms("Seleccione un registro existente");
            limpiar(true);
        }
// DaoServidor.eliminarRegistro(id);

        // TODO add your handling code here:
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void chkBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkBActionPerformed

        Controller();
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
            txtUbicacion.setText(tblVista.getValueAt(i, 2).toString());
            txtDueñoL.setText(tblVista.getValueAt(i, 3).toString());
            txtTelefono.setText(tblVista.getValueAt(i, 4).toString());
            txtPago.setText(tblVista.getValueAt(i, 5).toString());

            //System.out.println("id serv: "+id);
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_tblVistaMouseClicked

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
        buscarTorre();
        if (llave == true) {
            txtBuscar.setText(Cadena.eliminarUltCaracter(txtBuscar.getText().trim()));

        }
        buscarTorre();
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarKeyReleased

    private void chkBusquedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkBusquedaActionPerformed
        activarBusqueda();
        txtBuscar.requestFocus();

        // TODO add your handling code here:
    }//GEN-LAST:event_chkBusquedaActionPerformed

    private void tblVistaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblVistaKeyPressed
        int i = tblVista.getSelectedRow();
        if (evt.getKeyCode() == 38) {
            i = i - 1;
            if (i >= 0) {

                id = (int) tblVista.getValueAt(i, 0);
                txtNom.setText(tblVista.getValueAt(i, 1).toString());
                txtUbicacion.setText(tblVista.getValueAt(i, 2).toString());
                txtDueñoL.setText(tblVista.getValueAt(i, 3).toString());
                txtTelefono.setText(tblVista.getValueAt(i, 4).toString());
                txtPago.setText(tblVista.getValueAt(i, 5).toString());
            }

        } else if (evt.getKeyCode() == 40) {
            i = i + 1;
            if (i < tblVista.getRowCount()) {
                id = (int) tblVista.getValueAt(i, 0);
                txtNom.setText(tblVista.getValueAt(i, 1).toString());
                txtUbicacion.setText(tblVista.getValueAt(i, 2).toString());
                txtDueñoL.setText(tblVista.getValueAt(i, 3).toString());
                txtTelefono.setText(tblVista.getValueAt(i, 4).toString());
                txtPago.setText(tblVista.getValueAt(i, 5).toString());

            }

        }

// TODO add your handling code here:
    }//GEN-LAST:event_tblVistaKeyPressed

    private void txtNomKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomKeyTyped
        validarLongTexto(txtNom, 20, evt);

// TODO add your handling code here:
    }//GEN-LAST:event_txtNomKeyTyped

    private void txtUbicacionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUbicacionKeyTyped
        validarLongTexto(txtUbicacion, 30, evt);

// TODO add your handling code here:
    }//GEN-LAST:event_txtUbicacionKeyTyped

    private void txtDueñoLKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDueñoLKeyTyped

        validarLongTexto(txtDueñoL, 40, evt);

// TODO add your handling code here:
    }//GEN-LAST:event_txtDueñoLKeyTyped

    private void txtPagoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPagoKeyTyped
        String s = String.valueOf(evt.getKeyChar());

        if (!s.matches("[0-9.]")) {
            getToolkit().beep();
            evt.consume();
        }

// TODO add your handling code here:
    }//GEN-LAST:event_txtPagoKeyTyped

    private void txtTelefonoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoKeyTyped
        if (txtTelefono.getText().trim().length() < 9) {
            String s = String.valueOf(evt.getKeyChar());

            if (!s.matches("[0-9]")) {
                //getToolkit().beep();
                evt.consume();
            }

        } else {
            getToolkit().beep();
            evt.consume();

        }

// TODO add your handling code here:
    }//GEN-LAST:event_txtTelefonoKeyTyped

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnModif;
    private javax.swing.JButton btnRefrshe;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox chkB;
    private javax.swing.JCheckBox chkBusqueda;
    private javax.swing.JCheckBox chkD;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JPanel pnlBusqueda;
    private javax.swing.JTable tblVista;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtDueñoL;
    private javax.swing.JTextField txtNom;
    private javax.swing.JTextField txtPago;
    private javax.swing.JTextField txtTelefono;
    private javax.swing.JTextField txtUbicacion;
    // End of variables declaration//GEN-END:variables
}
