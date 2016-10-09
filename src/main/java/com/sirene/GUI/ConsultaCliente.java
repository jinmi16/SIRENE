package com.sirene.GUI;

import com.sirene.Bean.EstadoCliente;
import com.sirene.Bean.Servidor;
import com.sirene.Bean.Telefono;
import com.sirene.Bean.Torre;
import com.sirene.Dao.ClienteDao;
import com.sirene.Dao.Impl.DaoFactory;
//import com.sirene.Dao.Impl.DaoCliente;
//import com.sirene.Dao.Impl.DaoServidor;
//import com.sirene.Dao.Impl.DaoTelefono;
//import com.sirene.Dao.Impl.DaoTorre;
import com.sirene.Dao.Impl.Mensaje;
import com.sirene.Dao.ServidorDao;
import com.sirene.Dao.TelefonoDao;
import com.sirene.Dao.TorreDao;
import com.sirene.Utilitarios.Cadena;
import static com.sirene.Utilitarios.Constantes.CLIENTE;
import static com.sirene.Utilitarios.Constantes.SERVIDOR;
import static com.sirene.Utilitarios.Constantes.TELEFONO;
import static com.sirene.Utilitarios.Constantes.TORRE;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class ConsultaCliente extends javax.swing.JInternalFrame {

    DefaultTableModel modTabla;
    DefaultComboBoxModel modCboA = new DefaultComboBoxModel();
    DefaultComboBoxModel modCboT = new DefaultComboBoxModel();
    DefaultComboBoxModel modCboE = new DefaultComboBoxModel();
    DefaultComboBoxModel modCboM = new DefaultComboBoxModel();
    DefaultComboBoxModel modCboS = new DefaultComboBoxModel();
    DefaultComboBoxModel modCboNA = new DefaultComboBoxModel();
    String cliente;
    DefaultListModel modLista;
    int id = -1;
    boolean b1 = false, b2 = false, b3 = false, b4 = false, b5 = false;
    public final ClienteDao clienteDao;
    public final ServidorDao servidorDao;
    public final TelefonoDao telefonoDao;
    public final TorreDao torreDao;

    public ConsultaCliente() {
        initComponents();
        DaoFactory factory = DaoFactory.getInstance();
        clienteDao = factory.getClienteDao(CLIENTE);
        servidorDao = factory.getServidorDao(SERVIDOR);
        telefonoDao = factory.getTelefonoDao(TELEFONO);
        torreDao=factory.getTorreDao(TORRE);

        localisar(1142, 10);
        setTitle("Consulta_Personalizada/clientes");
        transparenciaTabla();
        modLista = new DefaultListModel();
        listaCboNA();
        listarEtado();
        listaServidores();
        listarmarcas();
        listaTorre();

        listarAntenas();

        listarBusqueda(0, txtCadBus.getText().trim(), " ");
        txtCadBus.requestFocus();

    }

    private void localisar(int x, int y) {
        int w_screen = getToolkit().getScreenSize().width;
        int k = (w_screen / 2) - (x / 2);
        setLocation(k, y);

    }

    private void transparenciaTabla() {
        //La mesa será transparente si ni ella ni las células son opacas:
        //tblVista.setOpaque(false); 
        // ((DefaultTableCellRenderer)tblVista.getDefaultRenderer(Object.class)).setOpaque(false);
        //Si la tabla está en un ScrollPane , que es hacer transparente así
        ScrollPane.setOpaque(false);
        ScrollPane.getViewport().setOpaque(false);
        //Por lo menos, usted puede quitar las líneas de la cuadrícula:
        // tblVista.setShowGrid(false);

    }

    public void limpiarBusc() {
        while (modTabla.getRowCount() > 0) {
            modTabla.removeRow(0);

        }
        int t = modTabla.getRowCount();
        txtCliente.setText(null);
        id = -1;
        lblTotal.setText(" " + t);

    }

    public void refreshBusc() {

        id = -1;
        txtCadBus.setText(null);
        //modCboA.setSelectedItem("");
        cboAntenaBusc.setSelectedIndex(0);
        cboEstadoBusc.setSelectedIndex(0);
        cboMarcaBusc.setSelectedIndex(0);
        cboNomApeBusc.setSelectedIndex(0);
        cboServidorBusc.setSelectedIndex(0);
        cboTorreBusc.setSelectedIndex(0);
    }

    public String ConsultaSql() {
        String sql = "", e, m, t, a, s;

        if (modCboE.getSelectedItem().equals("todos")) {
            e = " AND e.descripcion LIKE '%'";
        } else {
            e = " AND e.descripcion='" + modCboE.getSelectedItem().toString().trim() + "'";
        }
        if (modCboM.getSelectedItem().equals("todas")) {
            m = " AND c.marca LIKE '%'";
        } else {
            m = " AND c.marca='" + modCboM.getSelectedItem().toString().trim() + "'";
        }
        if (modCboT.getSelectedItem().toString().trim().equals("todas")) {
            t = " AND t.nombreTorre LIKE '%'";
        } else {
            t = " AND t.nombreTorre='" + modCboT.getSelectedItem().toString().trim() + "'";
        }
        if (modCboA.getSelectedItem().toString().trim().equals("todas")) {
            a = " AND a.nombreAntena LIKE '%'";
        } else {
            a = " AND a.nombreAntena='" + modCboA.getSelectedItem().toString().trim() + "'";
        }
        if (modCboS.getSelectedItem().toString().trim().equals("todos")) {
            s = " AND s.nombreServidor LIKE '%'";
        } else {
            s = " AND s.nombreServidor='" + modCboS.getSelectedItem().toString().trim() + "'";
        }

        sql = e + m + t + a + s;
        return sql;
    }

    private void listarBusqueda(int x, String nomApe, String consulta) {

        int longitud = clienteDao.buscarCliente(x, nomApe, consulta).size();
        if (longitud > 0) {
            b1 = false;
            Object[] colum = {"Id", "Nombre", "Apellido", "Dni", "Estado", "t/m antena", "Antena B/C", "Servidor", "Torre"};
            modTabla = new DefaultTableModel(null, colum) {

                public boolean isCellEditable(int row, int colum) {
                    return false;
                }
            };
            int k = 0;
            for (Object[] fila : clienteDao.buscarCliente(x, nomApe, consulta)) {
                modTabla.addRow(fila);
                k++;

            }
            tblVista.setModel(modTabla);
            anchoColum();
            cliente = tblVista.getValueAt(0, 1).toString() + " " + tblVista.getValueAt(0, 2).toString();
            txtCliente.setText(cliente);
            id = Integer.parseInt(tblVista.getValueAt(0, 0).toString());
            int t = modTabla.getRowCount();
            lblTotal.setText(" " + t);
            if (chkDetalle.isSelected()) {
                llenarCamposCliente(id);
            }
        } else {
            b1 = true;
            limpiarBusc();

            if (chkDetalle.isSelected()) {
                limpiar();
            }
            Mensaje.panelSms("No se encontraron Registros");
        }
    }

    public void anchoColum() {
        tblVista.getColumnModel().getColumn(0).setMinWidth(25);
        tblVista.getColumnModel().getColumn(0).setMaxWidth(35);
        tblVista.getColumnModel().getColumn(1).setMinWidth(100);
        tblVista.getColumnModel().getColumn(1).setMaxWidth(120);
        tblVista.getColumnModel().getColumn(2).setMinWidth(100);
        tblVista.getColumnModel().getColumn(2).setMaxWidth(120);
        tblVista.getColumnModel().getColumn(3).setMinWidth(55);
        tblVista.getColumnModel().getColumn(3).setMaxWidth(55);
        tblVista.getColumnModel().getColumn(4).setMinWidth(90);
        tblVista.getColumnModel().getColumn(4).setMaxWidth(110);
        tblVista.getColumnModel().getColumn(5).setMinWidth(90);
        tblVista.getColumnModel().getColumn(5).setMaxWidth(110);
        tblVista.getColumnModel().getColumn(6).setMinWidth(50);
        tblVista.getColumnModel().getColumn(6).setMaxWidth(80);

    }

    private void listarmarcas() {

        modCboM.addElement("todas");
        if (clienteDao.listarMarcas().size() > 0) {
            for (String m : clienteDao.listarMarcas()) {
                modCboM.addElement(m);

            }
        }

        cboMarcaBusc.setModel(modCboM);
    }

    private void listarAntenas() {

        modCboA.addElement("todas");
        if (clienteDao.listarAntenaBaseCli().size() > 0) {
            for (String m : clienteDao.listarAntenaBaseCli()) {
                modCboA.addElement(m);

            }
        }

        cboAntenaBusc.setModel(modCboA);
    }

    private void listarAntenasXTorre(String torre) {
        DefaultComboBoxModel modCbo = new DefaultComboBoxModel();
        modCbo.addElement("todas");
        if (clienteDao.listarAntenaBaseCliXtorre(torre).size() > 0) {
            // modCboA.removeAllElements();

            for (String m : clienteDao.listarAntenaBaseCliXtorre(torre)) {
                modCbo.addElement(m);

            }
            cboAntenaBusc.setModel(modCbo);
        } else {
            cboAntenaBusc.setModel(modCbo);

        }
        modCboA = modCbo;
        //cboAntenaBusc.setModel(modCbo);
    }

    private void listarEtado() {
        modCboE.addElement("todos");
        if (clienteDao.listarEstadoCli().size() > 0) {

            for (EstadoCliente e : clienteDao.listarEstadoCli()) {
                modCboE.addElement(e.getDescripcion());
            }

        }
        cboEstadoBusc.setModel(modCboE);
    }

    private void listaTorre() {
        modCboT.addElement("todas");
        if (torreDao.listarNobre().size() > 0) {

            for (Torre to : torreDao.listarNobre()) {
                modCboT.addElement(to.getNombre());
            }

        }
        cboTorreBusc.setModel(modCboT);
    }

    private void listaServidores() {
        modCboS.addElement("todos");
        if (servidorDao.listarNombre().size() > 0) {

            for (Servidor se : servidorDao.listarNombre()) {
                modCboS.addElement(se.getNomServ());

            }

        }
        cboServidorBusc.setModel(modCboS);
    }

    private void listaCboNA() {

        modCboNA.addElement("Nombre");
        modCboNA.addElement("Apellido");
        modCboNA.addElement("Dni");

        cboNomApeBusc.setModel(modCboNA);
    }

    public void limpiar() {
        txtApeCli.setText(null);
        txtNomCli.setText(null);
        txtDniCli.setText(null);
        txtCorreoCli.setText(null);
        txtFinicioCli.setText(null);
        txtTarifaCli.setText(null);
        txtDireccionCli.setText(null);
        txtFPagoCli.setText(null);
        txtFCorteCli.setText(null);
        txtCondAntenaCli.setText(null);
        txtMacCli.setText(null);
        txtIpCli.setText(null);
        txtFrecuenciaCli.setText(null);
        txtMarcaCli.setText(null);
        txtEstadoCli.setText(null);
        txtAnchoBandaCli.setText(null);
        txtObservacionCli.setText(null);

        txtNombreAnt.setText(null);
        txtMacAnt.setText(null);
        txtIpAnt.setText(null);
        txtFrecuenciaAnt.setText(null);
        txtPasswConfigAnt.setText(null);
        txtPasswConexionAnt.setText(null);
        txtCanalAnt.setText(null);

        txtNombreServ.setText(null);
        txtIpEntradaServ.setText(null);
        txtIpSalidaServ.setText(null);

        txtNombreTorr.setText(null);
        txtUbicacionTorr.setText(null);

        modLista.removeAllElements();

    }

    public void llenarCamposCliente(int i) {
        Object[] a;
        a = clienteDao.llenarTodosLosCampos(i);
        if (a.length > 0) {
            txtApeCli.setText(a[0].toString());
            txtNomCli.setText(a[1].toString());
            txtDniCli.setText(a[2].toString());
            txtCorreoCli.setText(a[3].toString());
            if (a[4] != null) {
                txtFinicioCli.setText(a[4].toString());
            } else {
                txtFinicioCli.setText(null);
            }

            txtTarifaCli.setText(a[5].toString());
            txtDireccionCli.setText(a[6].toString());
            txtFPagoCli.setText(a[7].toString());
            txtFCorteCli.setText(a[8].toString());
            txtCondAntenaCli.setText(a[9].toString());
            txtMacCli.setText(a[10].toString());
            txtIpCli.setText(a[11].toString());
            txtFrecuenciaCli.setText(a[12].toString());
            txtMarcaCli.setText(a[13].toString());
            txtEstadoCli.setText(a[14].toString());
            txtAnchoBandaCli.setText(a[15].toString());
            txtObservacionCli.setText(a[16].toString());
            txtNombreAnt.setText(a[17].toString());
            txtMacAnt.setText(a[18].toString());
            txtIpAnt.setText(a[19].toString());
            txtFrecuenciaAnt.setText(a[20].toString());
            txtPasswConfigAnt.setText(a[21].toString());
            txtPasswConexionAnt.setText(a[22].toString());
            txtCanalAnt.setText(a[23].toString());
            txtNombreServ.setText(a[24].toString());
            txtIpEntradaServ.setText(a[25].toString());
            txtIpSalidaServ.setText(a[26].toString());
            txtNombreTorr.setText(a[27].toString());
            txtUbicacionTorr.setText(a[28].toString());
            if (a[29] != null) {
                txtEjecCorte.setText(a[29].toString());
            } else {
                txtEjecCorte.setText(null);

            }
            if (a[30] != null) {
                txtCogelado.setText(a[30].toString());
            } else {
                txtCogelado.setText(null);

            }

            modLista.removeAllElements();
            for (Telefono te : telefonoDao.listarTelfCliente(id)) {
                modLista.addElement(te.getTelefono());
                lstTelefonos.setModel(modLista);
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        cboTorreBusc = new javax.swing.JComboBox();
        jLabel27 = new javax.swing.JLabel();
        cboAntenaBusc = new javax.swing.JComboBox();
        cboNomApeBusc = new javax.swing.JComboBox();
        txtCadBus = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        cboMarcaBusc = new javax.swing.JComboBox();
        jLabel25 = new javax.swing.JLabel();
        cboEstadoBusc = new javax.swing.JComboBox();
        jLabel37 = new javax.swing.JLabel();
        cboServidorBusc = new javax.swing.JComboBox();
        jButton4 = new javax.swing.JButton();
        ScrollPane = new javax.swing.JScrollPane();
        tblVista = new javax.swing.JTable();
        jLabel36 = new javax.swing.JLabel();
        txtCliente = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel38 = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        chkDetalle = new javax.swing.JCheckBox();
        jPanel7 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txtNombreTorr = new javax.swing.JTextField();
        txtUbicacionTorr = new javax.swing.JTextField();
        panel = new javax.swing.JPanel();
        txtPasswConexionAnt = new javax.swing.JTextField();
        txtCanalAnt = new javax.swing.JTextField();
        txtPasswConfigAnt = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtIpAnt = new javax.swing.JTextField();
        txtNombreAnt = new javax.swing.JTextField();
        txtMacAnt = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        txtFrecuenciaAnt = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        txtNombreServ = new javax.swing.JTextField();
        txtIpEntradaServ = new javax.swing.JTextField();
        txtIpSalidaServ = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtApeCli = new javax.swing.JTextField();
        txtNomCli = new javax.swing.JTextField();
        txtDniCli = new javax.swing.JTextField();
        txtDireccionCli = new javax.swing.JTextField();
        txtCorreoCli = new javax.swing.JTextField();
        txtFinicioCli = new javax.swing.JTextField();
        txtTarifaCli = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtFPagoCli = new javax.swing.JTextField();
        txtFCorteCli = new javax.swing.JTextField();
        txtCondAntenaCli = new javax.swing.JTextField();
        txtMacCli = new javax.swing.JTextField();
        txtIpCli = new javax.swing.JTextField();
        txtFrecuenciaCli = new javax.swing.JTextField();
        txtMarcaCli = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txtEstadoCli = new javax.swing.JTextField();
        txtAnchoBandaCli = new javax.swing.JTextField();
        txtObservacionCli = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstTelefonos = new javax.swing.JList();
        txtEjecCorte = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        txtCogelado = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel40 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(0, 0, 51));
        setClosable(true);
        setIconifiable(true);
        setMinimumSize(new java.awt.Dimension(1140, 760));
        setPreferredSize(new java.awt.Dimension(1140, 760));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setOpaque(false);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(204, 204, 204)), "Busqueda Por:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.setOpaque(false);
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(204, 255, 204));
        jLabel26.setText("Torre");
        jPanel2.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 115, 126, -1));

        cboTorreBusc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTorreBuscActionPerformed(evt);
            }
        });
        jPanel2.add(cboTorreBusc, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 115, 147, -1));

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(204, 255, 204));
        jLabel27.setText("Antena Base/Cliente");
        jPanel2.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 147, 126, -1));

        cboAntenaBusc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboAntenaBuscActionPerformed(evt);
            }
        });
        jPanel2.add(cboAntenaBusc, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 144, 147, -1));

        cboNomApeBusc.setFocusable(false);
        cboNomApeBusc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboNomApeBuscActionPerformed(evt);
            }
        });
        jPanel2.add(cboNomApeBusc, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 32, 102, -1));

        txtCadBus.setBackground(new java.awt.Color(255, 255, 204));
        txtCadBus.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtCadBus.setForeground(new java.awt.Color(255, 0, 0));
        txtCadBus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCadBusKeyReleased(evt);
            }
        });
        jPanel2.add(txtCadBus, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 32, 130, -1));

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(204, 255, 204));
        jLabel24.setText("Tipo/marca Antena");
        jPanel2.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 92, 126, -1));

        cboMarcaBusc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboMarcaBuscActionPerformed(evt);
            }
        });
        jPanel2.add(cboMarcaBusc, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 89, 147, -1));

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(204, 255, 204));
        jLabel25.setText("Estado");
        jPanel2.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 66, 126, -1));

        cboEstadoBusc.setBackground(new java.awt.Color(255, 51, 51));
        cboEstadoBusc.setOpaque(false);
        cboEstadoBusc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboEstadoBuscActionPerformed(evt);
            }
        });
        jPanel2.add(cboEstadoBusc, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 63, 147, -1));

        jLabel37.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(204, 255, 204));
        jLabel37.setText("Servidor");
        jPanel2.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 170, 111, 26));

        cboServidorBusc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboServidorBuscActionPerformed(evt);
            }
        });
        jPanel2.add(cboServidorBusc, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 173, 147, -1));

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/arrow_refresh16.png"))); // NOI18N
        jButton4.setToolTipText("REFRESH campos de busqueda");
        jButton4.setOpaque(false);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(293, 16, 40, 40));

        ScrollPane.setMinimumSize(new java.awt.Dimension(650, 700));
        ScrollPane.setOpaque(false);

        tblVista.setBackground(new java.awt.Color(51, 51, 51));
        tblVista.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
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
        ScrollPane.setViewportView(tblVista);

        jLabel36.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(255, 255, 255));
        jLabel36.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/cliente35.png"))); // NOI18N
        jLabel36.setText("Cliente");

        txtCliente.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtCliente.setForeground(new java.awt.Color(204, 255, 0));
        txtCliente.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 255, 255)));
        txtCliente.setOpaque(false);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/accept16.png"))); // NOI18N
        jButton1.setToolTipText("VER DETALLE");
        jButton1.setOpaque(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel38.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(204, 255, 204));
        jLabel38.setText("Total Registros : ");

        lblTotal.setBackground(new java.awt.Color(255, 255, 255));
        lblTotal.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        lblTotal.setForeground(new java.awt.Color(204, 255, 0));
        lblTotal.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));

        chkDetalle.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        chkDetalle.setForeground(new java.awt.Color(204, 255, 204));
        chkDetalle.setSelected(true);
        chkDetalle.setText("Ver detalle Automatico");
        chkDetalle.setOpaque(false);
        chkDetalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkDetalleActionPerformed(evt);
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
                        .addComponent(jLabel38)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chkDetalle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(16, 16, 16))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(ScrollPane, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel36)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel36)
                        .addComponent(txtCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 338, Short.MAX_VALUE)
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblTotal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel38, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(11, 11, 11))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(chkDetalle)
                        .addContainerGap())))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 69, 360, 650));

        jPanel7.setOpaque(false);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(204, 204, 204)), "TORRE", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel5.setOpaque(false);

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(204, 255, 204));
        jLabel18.setText("Nombre");

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(204, 255, 204));
        jLabel19.setText("Ubicacion");

        txtNombreTorr.setEditable(false);
        txtNombreTorr.setBackground(new java.awt.Color(0, 0, 0));
        txtNombreTorr.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        txtNombreTorr.setForeground(new java.awt.Color(255, 255, 255));

        txtUbicacionTorr.setEditable(false);
        txtUbicacionTorr.setBackground(new java.awt.Color(0, 0, 0));
        txtUbicacionTorr.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        txtUbicacionTorr.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtNombreTorr, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addGap(34, 34, 34)
                        .addComponent(txtUbicacionTorr, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txtNombreTorr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUbicacionTorr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        panel.setBackground(new java.awt.Color(102, 102, 102));
        panel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(204, 204, 204)), "ANTENA (Base Cliente) ", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(255, 255, 255))); // NOI18N
        panel.setOpaque(false);

        txtPasswConexionAnt.setEditable(false);
        txtPasswConexionAnt.setBackground(new java.awt.Color(0, 0, 0));
        txtPasswConexionAnt.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtPasswConexionAnt.setForeground(new java.awt.Color(255, 255, 255));

        txtCanalAnt.setEditable(false);
        txtCanalAnt.setBackground(new java.awt.Color(0, 0, 0));
        txtCanalAnt.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtCanalAnt.setForeground(new java.awt.Color(255, 255, 255));

        txtPasswConfigAnt.setEditable(false);
        txtPasswConfigAnt.setBackground(new java.awt.Color(0, 0, 0));
        txtPasswConfigAnt.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtPasswConfigAnt.setForeground(new java.awt.Color(255, 255, 255));
        txtPasswConfigAnt.setMinimumSize(new java.awt.Dimension(55, 20));
        txtPasswConfigAnt.setPreferredSize(new java.awt.Dimension(55, 20));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(204, 255, 204));
        jLabel11.setText("Passw_Conexion");

        txtIpAnt.setEditable(false);
        txtIpAnt.setBackground(new java.awt.Color(0, 0, 0));
        txtIpAnt.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtIpAnt.setForeground(new java.awt.Color(255, 255, 255));

        txtNombreAnt.setEditable(false);
        txtNombreAnt.setBackground(new java.awt.Color(0, 0, 0));
        txtNombreAnt.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtNombreAnt.setForeground(new java.awt.Color(255, 255, 255));

        txtMacAnt.setEditable(false);
        txtMacAnt.setBackground(new java.awt.Color(0, 0, 0));
        txtMacAnt.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtMacAnt.setForeground(new java.awt.Color(255, 255, 255));

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(204, 255, 204));
        jLabel22.setText("Nombre");

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(204, 255, 204));
        jLabel23.setText("IP");

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(204, 255, 204));
        jLabel29.setText("MAC");

        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(204, 255, 204));
        jLabel30.setText("Frecuencia");

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(204, 255, 204));
        jLabel32.setText("Passw_Config.");

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(204, 255, 204));
        jLabel33.setText("canal");

        txtFrecuenciaAnt.setEditable(false);
        txtFrecuenciaAnt.setBackground(new java.awt.Color(0, 0, 0));
        txtFrecuenciaAnt.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtFrecuenciaAnt.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel22)
                                    .addComponent(jLabel29)
                                    .addComponent(jLabel23))
                                .addGap(18, 18, 18))
                            .addGroup(panelLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel30)
                                .addGap(1, 1, 1)))
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtFrecuenciaAnt, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtIpAnt, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(4, 4, 4)
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel33)
                            .addComponent(jLabel11)
                            .addComponent(jLabel32))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPasswConfigAnt, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPasswConexionAnt, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCanalAnt, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelLayout.createSequentialGroup()
                                .addGap(73, 73, 73)
                                .addComponent(txtNombreAnt, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelLayout.createSequentialGroup()
                                .addGap(72, 72, 72)
                                .addComponent(txtMacAnt, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22)
                    .addComponent(txtNombreAnt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32)
                    .addComponent(txtPasswConfigAnt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel29)
                    .addComponent(txtMacAnt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(txtPasswConexionAnt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel23)
                    .addComponent(txtIpAnt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33)
                    .addComponent(txtCanalAnt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel30)
                    .addComponent(txtFrecuenciaAnt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(204, 204, 204)), "SERVIDOR ", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel4.setOpaque(false);

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(204, 255, 204));
        jLabel21.setText("Nombre_servidor");

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(204, 255, 204));
        jLabel31.setText("IP_Entrada");

        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(204, 255, 204));
        jLabel34.setText("IP_Salida");

        txtNombreServ.setEditable(false);
        txtNombreServ.setBackground(new java.awt.Color(0, 0, 0));
        txtNombreServ.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtNombreServ.setForeground(new java.awt.Color(255, 255, 255));

        txtIpEntradaServ.setEditable(false);
        txtIpEntradaServ.setBackground(new java.awt.Color(0, 0, 0));
        txtIpEntradaServ.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtIpEntradaServ.setForeground(new java.awt.Color(255, 255, 255));

        txtIpSalidaServ.setEditable(false);
        txtIpSalidaServ.setBackground(new java.awt.Color(0, 0, 0));
        txtIpSalidaServ.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtIpSalidaServ.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel21)
                    .addComponent(jLabel31)
                    .addComponent(jLabel34))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtNombreServ, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
                    .addComponent(txtIpEntradaServ)
                    .addComponent(txtIpSalidaServ))
                .addGap(110, 110, 110))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(txtNombreServ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(txtIpEntradaServ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(txtIpSalidaServ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(204, 204, 204)), "CLIENTE", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(204, 255, 204));
        jLabel16.setText("Direccion");
        jPanel3.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 202, -1, -1));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 255, 204));
        jLabel1.setText("Nombre");
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 27, -1, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(204, 255, 204));
        jLabel2.setText("Apellido");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 52, -1, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(204, 255, 204));
        jLabel3.setText("DNI");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 80, -1, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(204, 255, 204));
        jLabel4.setText("Correo");
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 109, -1, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(204, 255, 204));
        jLabel5.setText("Fecha_Inicio");
        jPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 136, -1, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(204, 255, 204));
        jLabel6.setText("Tarifa");
        jPanel3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 159, -1, -1));

        txtApeCli.setEditable(false);
        txtApeCli.setBackground(new java.awt.Color(0, 0, 0));
        txtApeCli.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtApeCli.setForeground(new java.awt.Color(255, 255, 255));
        txtApeCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtApeCliActionPerformed(evt);
            }
        });
        jPanel3.add(txtApeCli, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 49, 175, -1));

        txtNomCli.setEditable(false);
        txtNomCli.setBackground(new java.awt.Color(0, 0, 0));
        txtNomCli.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtNomCli.setForeground(new java.awt.Color(255, 255, 255));
        jPanel3.add(txtNomCli, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 23, 175, -1));

        txtDniCli.setEditable(false);
        txtDniCli.setBackground(new java.awt.Color(0, 0, 0));
        txtDniCli.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtDniCli.setForeground(new java.awt.Color(255, 255, 255));
        jPanel3.add(txtDniCli, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 77, 175, -1));

        txtDireccionCli.setEditable(false);
        txtDireccionCli.setBackground(new java.awt.Color(0, 0, 0));
        txtDireccionCli.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtDireccionCli.setForeground(new java.awt.Color(255, 255, 255));
        jPanel3.add(txtDireccionCli, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 198, 410, -1));

        txtCorreoCli.setEditable(false);
        txtCorreoCli.setBackground(new java.awt.Color(0, 0, 0));
        txtCorreoCli.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtCorreoCli.setForeground(new java.awt.Color(255, 255, 255));
        jPanel3.add(txtCorreoCli, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 105, 175, -1));

        txtFinicioCli.setEditable(false);
        txtFinicioCli.setBackground(new java.awt.Color(0, 0, 0));
        txtFinicioCli.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtFinicioCli.setForeground(new java.awt.Color(255, 255, 255));
        jPanel3.add(txtFinicioCli, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 132, 175, -1));

        txtTarifaCli.setEditable(false);
        txtTarifaCli.setBackground(new java.awt.Color(0, 0, 0));
        txtTarifaCli.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtTarifaCli.setForeground(new java.awt.Color(255, 255, 255));
        jPanel3.add(txtTarifaCli, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 159, 175, -1));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(204, 255, 204));
        jLabel12.setText("Frecuencia");
        jPanel3.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(312, 159, -1, -1));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(204, 255, 204));
        jLabel14.setText("Condicion_Antena");
        jPanel3.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(269, 74, -1, -1));

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(204, 255, 204));
        jLabel28.setText("Marca/Antena");
        jPanel3.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(518, 25, -1, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(204, 255, 204));
        jLabel7.setText("Fecha_Pago");
        jPanel3.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(269, 24, -1, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(204, 255, 204));
        jLabel8.setText("Fecha_Corte");
        jPanel3.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(269, 53, -1, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(204, 255, 204));
        jLabel9.setText("Mac");
        jPanel3.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 105, -1, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(204, 255, 204));
        jLabel10.setText("IP");
        jPanel3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(361, 132, -1, -1));

        txtFPagoCli.setEditable(false);
        txtFPagoCli.setBackground(new java.awt.Color(0, 0, 0));
        txtFPagoCli.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtFPagoCli.setForeground(new java.awt.Color(255, 255, 255));
        jPanel3.add(txtFPagoCli, new org.netbeans.lib.awtextra.AbsoluteConstraints(383, 21, 122, -1));

        txtFCorteCli.setEditable(false);
        txtFCorteCli.setBackground(new java.awt.Color(0, 0, 0));
        txtFCorteCli.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtFCorteCli.setForeground(new java.awt.Color(255, 255, 255));
        jPanel3.add(txtFCorteCli, new org.netbeans.lib.awtextra.AbsoluteConstraints(383, 46, 122, -1));

        txtCondAntenaCli.setEditable(false);
        txtCondAntenaCli.setBackground(new java.awt.Color(0, 0, 0));
        txtCondAntenaCli.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtCondAntenaCli.setForeground(new java.awt.Color(255, 255, 255));
        jPanel3.add(txtCondAntenaCli, new org.netbeans.lib.awtextra.AbsoluteConstraints(383, 74, 121, -1));

        txtMacCli.setEditable(false);
        txtMacCli.setBackground(new java.awt.Color(0, 0, 0));
        txtMacCli.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtMacCli.setForeground(new java.awt.Color(255, 255, 255));
        jPanel3.add(txtMacCli, new org.netbeans.lib.awtextra.AbsoluteConstraints(383, 101, 121, -1));

        txtIpCli.setEditable(false);
        txtIpCli.setBackground(new java.awt.Color(0, 0, 0));
        txtIpCli.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtIpCli.setForeground(new java.awt.Color(255, 255, 255));
        jPanel3.add(txtIpCli, new org.netbeans.lib.awtextra.AbsoluteConstraints(383, 128, 121, -1));

        txtFrecuenciaCli.setEditable(false);
        txtFrecuenciaCli.setBackground(new java.awt.Color(0, 0, 0));
        txtFrecuenciaCli.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtFrecuenciaCli.setForeground(new java.awt.Color(255, 255, 255));
        jPanel3.add(txtFrecuenciaCli, new org.netbeans.lib.awtextra.AbsoluteConstraints(383, 155, 121, -1));

        txtMarcaCli.setEditable(false);
        txtMarcaCli.setBackground(new java.awt.Color(0, 0, 0));
        txtMarcaCli.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtMarcaCli.setForeground(new java.awt.Color(255, 255, 255));
        jPanel3.add(txtMarcaCli, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 21, 109, -1));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(204, 255, 204));
        jLabel13.setText("A_ Banda");
        jPanel3.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(518, 78, -1, -1));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(204, 255, 204));
        jLabel15.setText("Observacion");
        jPanel3.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 234, -1, -1));

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(204, 255, 204));
        jLabel20.setText("Telefonos");
        jPanel3.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(518, 128, -1, -1));

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(204, 255, 204));
        jLabel17.setText("Estado");
        jPanel3.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(518, 50, -1, -1));

        txtEstadoCli.setEditable(false);
        txtEstadoCli.setBackground(new java.awt.Color(0, 0, 0));
        txtEstadoCli.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtEstadoCli.setForeground(new java.awt.Color(255, 255, 255));
        jPanel3.add(txtEstadoCli, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 46, 109, -1));

        txtAnchoBandaCli.setEditable(false);
        txtAnchoBandaCli.setBackground(new java.awt.Color(0, 0, 0));
        txtAnchoBandaCli.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtAnchoBandaCli.setForeground(new java.awt.Color(255, 255, 255));
        jPanel3.add(txtAnchoBandaCli, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 74, 109, -1));

        txtObservacionCli.setEditable(false);
        txtObservacionCli.setBackground(new java.awt.Color(0, 0, 0));
        txtObservacionCli.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtObservacionCli.setForeground(new java.awt.Color(255, 255, 255));
        jPanel3.add(txtObservacionCli, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 230, 410, -1));

        lstTelefonos.setBackground(new java.awt.Color(0, 0, 0));
        lstTelefonos.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lstTelefonos.setForeground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setViewportView(lstTelefonos);

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 106, 109, 54));

        txtEjecCorte.setEditable(false);
        txtEjecCorte.setBackground(new java.awt.Color(0, 0, 0));
        txtEjecCorte.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtEjecCorte.setForeground(new java.awt.Color(255, 255, 255));
        jPanel3.add(txtEjecCorte, new org.netbeans.lib.awtextra.AbsoluteConstraints(609, 166, 110, -1));

        jLabel41.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(204, 255, 204));
        jLabel41.setText("F/Ejec- Corte");
        jPanel3.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(525, 170, -1, -1));

        jLabel42.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(204, 255, 204));
        jLabel42.setText("F/Congelacion ");
        jPanel3.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 200, -1, -1));

        txtCogelado.setEditable(false);
        txtCogelado.setBackground(new java.awt.Color(0, 0, 0));
        txtCogelado.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtCogelado.setForeground(new java.awt.Color(255, 255, 255));
        jPanel3.add(txtCogelado, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 200, 110, -1));

        jPanel8.setOpaque(false);
        jPanel8.setLayout(new java.awt.GridLayout(1, 2, 5, 0));

        jButton2.setBackground(new java.awt.Color(51, 51, 255));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/arrow_refresh.png"))); // NOI18N
        jButton2.setMnemonic('L');
        jButton2.setText("Limpiar");
        jButton2.setBorderPainted(false);
        jButton2.setOpaque(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel8.add(jButton2);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/cancel.png"))); // NOI18N
        jButton3.setText("Salir");
        jButton3.setOpaque(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel8.add(jButton3);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                            .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel7Layout.createSequentialGroup()
                            .addGap(28, 28, 28)
                            .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(39, 39, 39))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getContentPane().add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(363, 69, 760, 650));

        jLabel40.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/Encabezados/CPC.png"))); // NOI18N
        getContentPane().add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(-4, 0, -1, 50));

        jLabel35.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/Encabezados/ftn1.png"))); // NOI18N
        getContentPane().add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 50, 780, 680));

        jLabel39.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/Encabezados/ft2.png"))); // NOI18N
        getContentPane().add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(-600, 50, 970, 680));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblVistaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblVistaMouseClicked

        if (evt.getClickCount() == 2) {
            int i = tblVista.getSelectedRow();
            id = (int) tblVista.getValueAt(i, 0);
            cliente = tblVista.getValueAt(i, 1).toString() + " " + tblVista.getValueAt(i, 2).toString();
            txtCliente.setText(cliente);
            if (chkDetalle.isSelected()) {
                llenarCamposCliente(id);
            }

        }

        // TODO add your handling code here:
    }//GEN-LAST:event_tblVistaMouseClicked

    private void cboMarcaBuscActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboMarcaBuscActionPerformed
        listarBusqueda(cboNomApeBusc.getSelectedIndex(), txtCadBus.getText().trim(), ConsultaSql());

        // TODO add your handling code here:
    }//GEN-LAST:event_cboMarcaBuscActionPerformed

    private void txtCadBusKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCadBusKeyReleased

        listarBusqueda(cboNomApeBusc.getSelectedIndex(), txtCadBus.getText().trim(), ConsultaSql());
        if (b1 == true) {
            txtCadBus.setText(Cadena.eliminarUltCaracter(txtCadBus.getText().trim()));
        }

// TODO add your handling code here:
    }//GEN-LAST:event_txtCadBusKeyReleased

    private void cboNomApeBuscActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboNomApeBuscActionPerformed
        txtCadBus.setText(null);
        listarBusqueda(cboNomApeBusc.getSelectedIndex(), txtCadBus.getText().trim(), ConsultaSql());
        // TODO add your handling code here:
    }//GEN-LAST:event_cboNomApeBuscActionPerformed

    private void cboEstadoBuscActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboEstadoBuscActionPerformed
        listarBusqueda(cboNomApeBusc.getSelectedIndex(), txtCadBus.getText().trim(), ConsultaSql());
// TODO add your handling code here:
    }//GEN-LAST:event_cboEstadoBuscActionPerformed

    private void cboTorreBuscActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTorreBuscActionPerformed
        listarAntenasXTorre(modCboT.getSelectedItem().toString());
        listarBusqueda(cboNomApeBusc.getSelectedIndex(), txtCadBus.getText().trim(), ConsultaSql());

        // listarBusqueda(cboNomApeBusc.getSelectedIndex(), txtCadBus.getText().trim(), ConsultaSql());
        // TODO add your handling code here:
    }//GEN-LAST:event_cboTorreBuscActionPerformed

    private void cboAntenaBuscActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboAntenaBuscActionPerformed

        listarBusqueda(cboNomApeBusc.getSelectedIndex(), txtCadBus.getText().trim(), ConsultaSql());
        // TODO add your handling code here:
    }//GEN-LAST:event_cboAntenaBuscActionPerformed

    private void cboServidorBuscActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboServidorBuscActionPerformed
        listarBusqueda(cboNomApeBusc.getSelectedIndex(), txtCadBus.getText().trim(), ConsultaSql());

        // TODO add your handling code here:
    }//GEN-LAST:event_cboServidorBuscActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        llenarCamposCliente(id);
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        refreshBusc();

// TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        limpiar();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void chkDetalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkDetalleActionPerformed
        if (!chkDetalle.isSelected()) {
            limpiar();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_chkDetalleActionPerformed

    private void tblVistaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblVistaKeyPressed
        int k = tblVista.getSelectedRow();
        if (evt.getKeyCode() == 38) {
            k = k - 1;
            if (k >= 0) {
                id = (int) tblVista.getValueAt(k, 0);
                cliente = tblVista.getValueAt(k, 1).toString() + " " + tblVista.getValueAt(k, 2).toString();
                txtCliente.setText(cliente);
                if (chkDetalle.isSelected()) {
                    llenarCamposCliente(id);
                }
            }

        } else if (evt.getKeyCode() == 40) {
            k = k + 1;
            if (k < tblVista.getRowCount()) {
                id = (int) tblVista.getValueAt(k, 0);
                cliente = tblVista.getValueAt(k, 1).toString() + " " + tblVista.getValueAt(k, 2).toString();
                txtCliente.setText(cliente);
                if (chkDetalle.isSelected()) {
                    llenarCamposCliente(id);
                }
            }

        }

// TODO add your handling code here:
    }//GEN-LAST:event_tblVistaKeyPressed

    private void txtApeCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtApeCliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtApeCliActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane ScrollPane;
    private javax.swing.JComboBox cboAntenaBusc;
    private javax.swing.JComboBox cboEstadoBusc;
    private javax.swing.JComboBox cboMarcaBusc;
    private javax.swing.JComboBox cboNomApeBusc;
    private javax.swing.JComboBox cboServidorBusc;
    private javax.swing.JComboBox cboTorreBusc;
    private javax.swing.JCheckBox chkDetalle;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JList lstTelefonos;
    private javax.swing.JPanel panel;
    private javax.swing.JTable tblVista;
    private javax.swing.JTextField txtAnchoBandaCli;
    private javax.swing.JTextField txtApeCli;
    private javax.swing.JTextField txtCadBus;
    private javax.swing.JTextField txtCanalAnt;
    private javax.swing.JTextField txtCliente;
    private javax.swing.JTextField txtCogelado;
    private javax.swing.JTextField txtCondAntenaCli;
    private javax.swing.JTextField txtCorreoCli;
    private javax.swing.JTextField txtDireccionCli;
    private javax.swing.JTextField txtDniCli;
    private javax.swing.JTextField txtEjecCorte;
    private javax.swing.JTextField txtEstadoCli;
    private javax.swing.JTextField txtFCorteCli;
    private javax.swing.JTextField txtFPagoCli;
    private javax.swing.JTextField txtFinicioCli;
    private javax.swing.JTextField txtFrecuenciaAnt;
    private javax.swing.JTextField txtFrecuenciaCli;
    private javax.swing.JTextField txtIpAnt;
    private javax.swing.JTextField txtIpCli;
    private javax.swing.JTextField txtIpEntradaServ;
    private javax.swing.JTextField txtIpSalidaServ;
    private javax.swing.JTextField txtMacAnt;
    private javax.swing.JTextField txtMacCli;
    private javax.swing.JTextField txtMarcaCli;
    private javax.swing.JTextField txtNomCli;
    private javax.swing.JTextField txtNombreAnt;
    private javax.swing.JTextField txtNombreServ;
    private javax.swing.JTextField txtNombreTorr;
    private javax.swing.JTextField txtObservacionCli;
    private javax.swing.JTextField txtPasswConexionAnt;
    private javax.swing.JTextField txtPasswConfigAnt;
    private javax.swing.JTextField txtTarifaCli;
    private javax.swing.JTextField txtUbicacionTorr;
    // End of variables declaration//GEN-END:variables
}
