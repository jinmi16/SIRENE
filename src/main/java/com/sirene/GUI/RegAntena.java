/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirene.GUI;

import com.sirene.Bean.*;
import com.sirene.Dao.AntenaDao;
import com.sirene.Dao.Impl.DaoFactory;
//import com.sirene.Dao.Impl.DaoAntena;
//import com.sirene.Dao.Impl.DaoServidor;
//import com.sirene.Dao.Impl.DaoTorre;
import com.sirene.Dao.Impl.Mensaje;
import com.sirene.Dao.ServidorDao;
import com.sirene.Dao.TorreDao;
import com.sirene.Utilitarios.Cadena;
import static com.sirene.Utilitarios.Constantes.ANTENA;
import static com.sirene.Utilitarios.Constantes.SERVIDOR;
import static com.sirene.Utilitarios.Constantes.TORRE;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class RegAntena extends javax.swing.JInternalFrame {

    Usuario usuario;
    DefaultTableModel modTabla;
    DefaultComboBoxModel modCboTAB, modCboserverBusc, modCboTorreBusc;
    int id = -1;
    ArrayList<Antena> listAntena, lstAntEE;
    ArrayList<Servidor> lstServ;
    ArrayList<tipoAntena> listipoAntena;
    ArrayList<Torre> lstTorre;
    int ta = 0, ae = 0, s = 0, t = 0;
    boolean llave = false, llave2 = false;
    public final AntenaDao antenaDao;
    public final ServidorDao servidorDao;
    public final TorreDao torreDao;

    public RegAntena() {

        initComponents();
        DaoFactory factory = DaoFactory.getInstance();
        antenaDao = factory.getAntenaDao(ANTENA);
        servidorDao = factory.getServidorDao(SERVIDOR);
        torreDao=factory.getTorreDao(TORRE);


        setTitle("Registro_Antena");
        transparenciaTabla();
        localisar(772, 14);
        modCboTAB = new DefaultComboBoxModel();
        modCboserverBusc = new DefaultComboBoxModel();
        modCboTorreBusc = new DefaultComboBoxModel();
        listarTipoAntenaBusqueda();
        listarAntena2();
        listarTipoAntena();
        listaServidores();
        listaTorre();
        lstAntEE = antenaDao.listarAntEnlEmisora();

        Controller(false);
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

    public String sql() {
        String sql;
        String tab, server, torre;
        if (!modCboTAB.getSelectedItem().equals("todos")) {
            tab = " AND ta.tipoAntena='" + modCboTAB.getSelectedItem().toString() + "' ";

        } else {
            tab = " AND ta.tipoAntena LIKE '%'";

        }

        if (!modCboserverBusc.getSelectedItem().equals("todos")) {
            server = " AND s.nombreServidor='" + modCboserverBusc.getSelectedItem().toString() + "' ";

        } else {
            server = " AND s.nombreServidor LIKE '%'";

        }

        if (!modCboTorreBusc.getSelectedItem().equals("todos")) {
            torre = " AND t.nombreTorre='" + modCboTorreBusc.getSelectedItem().toString() + "' ";

        } else {
            torre = " AND t.nombreTorre LIKE '%'";

        }
        sql = tab + server + torre;
        return sql;
    }

    public void buscarIdTipoAntena() {
        for (tipoAntena tipoA : listipoAntena) {
            if (tipoA.getTipoAntena().equals(cboTipoAntena.getSelectedItem())) {
                ta = tipoA.getIdTipo();
                System.out.println("idTipoAntena" + ta);
            }

        }

    }

    public void listarAE() {
        DefaultComboBoxModel modCboA = new DefaultComboBoxModel();
        cboAE.setModel(modCboA);
        if (cboTipoAntena.getSelectedIndex() == 3 && cboTorre2.getSelectedIndex() != 0) {

            if (antenaDao.antenaEE(cboTorre2.getSelectedItem().toString()).size() > 0) {
                for (String a : antenaDao.antenaEE(cboTorre2.getSelectedItem().toString())) {
                    modCboA.addElement(a);
                }

            }

        } else {
            // cboAE.removeAllItems();

        }

    }

    public String nomTipoAntena2(int i) {
        String nomtipAnt = null;
        for (tipoAntena tipoA : listipoAntena) {
            if (tipoA.getIdTipo() == i) {
                ta = tipoA.getIdTipo();
                nomtipAnt = tipoA.getTipoAntena();
                System.out.println("nombre tipo antena " + nomtipAnt);
            }

        }

        return nomtipAnt;
    }

    public void validarLongTexto(JTextField txt, int l, java.awt.event.KeyEvent evt) {

        if (txt.getText().trim().length() >= l) {
            getToolkit().beep();
            evt.consume();
        }

    }

    public String nomServidor(int i) {
        String nom = null;
        for (Servidor se : lstServ) {
            if (se.getIdServ() == i) {
                s = se.getIdServ();
                nom = se.getNomServ();
                System.out.println("nombre serv " + nom);
            }

        }

        return nom;
    }

    public String nomTorre(int i) {
        String nom = null;
        for (Torre tor : lstTorre) {
            if (tor.getIdTorre() == i) {
                t = tor.getIdTorre();
                nom = tor.getNombre();
                System.out.println("nombre torre " + nom);
            }

        }

        return nom;
    }

    public String nomAntEnlace(int i) {
        //para doble click
        String nom = null;
        for (Antena at : lstAntEE) {
            if (at.getIdAntena() == i) {
                ae = at.getIdAntena();
                nom = at.getNombre();
                System.out.println("nombre AEE " + nom);
            }

        }

        return nom;
    }

    public void buscarIdAntenaE() {
        //para registrar y actualizar
        if (cboAE.getItemCount() > 0) {
            for (Antena A : lstAntEE) {
                if (A.getNombre().equals(cboAE.getSelectedItem())) {
                    ae = A.getIdAntena();
                    System.out.println("idAntenaEnlace" + ae);
                }

            }
        } else {
            ae = 0;
            System.out.println("idAntenaEnlace" + ae);
        }

    }

    public void buscarIdServ() {
        for (Servidor ser : lstServ) {
            if (ser.getNomServ().equals(cboServidor.getSelectedItem())) {
                s = ser.getIdServ();
                System.out.println("idServidor" + s);
            }

        }

    }

    public void buscarIdTorre() {
        for (Torre tor : lstTorre) {
            if (tor.getNombre().equals(cboTorre.getSelectedItem())) {
                t = tor.getIdTorre();
                System.out.println("idTorre" + t);
            }

        }

    }

    public void setUser(Usuario us) {
        usuario = us;

        System.out.println("tipo de usuario  : " + usuario.getIdTipoUser());

    }

    private void cboInicial() {
        cboTipoAntena.addItem("selecione");

    }

    private void activarBusqueda() {
        if (chkBusqueda.isSelected()) {
            pnlBusqueda.setVisible(true);
        } else {
            limpiar();
            pnlBusqueda.setVisible(false);
        }

    }

    private void Controller(boolean f) {

        btnModif.setEnabled(f);
        btnRegistrar.setEnabled(f);
        btnEliminar.setEnabled(f);
        txtNombre.setEditable(f);
        txtIp.setEditable(f);
        txtMac1.setEditable(f);
        txtPasswConexion.setEditable(f);
        txtPasswConfig.setEditable(f);

        cboTipoAntena.setEnabled(f);
        cboServidor.setEnabled(f);
        cboTorre.setEnabled(f);

    }

    public void habilitarController() {
        if (usuario.getIdTipoUser() == 1) {
            btnModif.setEnabled(true);
            btnRegistrar.setEnabled(true);
            btnEliminar.setEnabled(true);
            Controller(true);

        } else {
            Controller(false);
            Mensaje.panelSms("NO TIENE EL PRIVILEGIO ADMINISTRATIVO");
            chkB.setSelected(true);
        }

    }

    public void limpiar() {

        txtNombre.setText(null);
        txtIp.setText(null);
        txtCanal.setText(null);
        txtMac1.setText(null);
        txtPasswConexion.setText(null);
        txtPasswConfig.setText(null);
        rbt1.setSelected(true);
        cboServidor.setSelectedIndex(0);
        cboTipoAntena.setSelectedIndex(0);
        DefaultComboBoxModel modcboA = new DefaultComboBoxModel();
        cboAE.setModel(modcboA);
        cboTorre.setSelectedIndex(0);
        cboTorre2.setSelectedIndex(0);
        txtBuscar.setText(null);

        // cboTipoAntenaBusqueda.setSelectedIndex(0);
        // modCboTAB.setSelectedItem("todos");
        id = -1;

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
    //--

    private void anchoColum() {

        tblVista.getColumnModel().getColumn(0).setMinWidth(30);
        tblVista.getColumnModel().getColumn(0).setMaxWidth(40);
        tblVista.getColumnModel().getColumn(1).setMinWidth(120);
        tblVista.getColumnModel().getColumn(1).setMaxWidth(140);
        tblVista.getColumnModel().getColumn(2).setMinWidth(120);
        tblVista.getColumnModel().getColumn(2).setMaxWidth(130);
        tblVista.getColumnModel().getColumn(3).setMinWidth(150);
        tblVista.getColumnModel().getColumn(3).setMaxWidth(160);
        tblVista.getColumnModel().getColumn(4).setMinWidth(68);
        tblVista.getColumnModel().getColumn(4).setMaxWidth(70);
        tblVista.getColumnModel().getColumn(5).setMinWidth(50);
        tblVista.getColumnModel().getColumn(5).setMaxWidth(60);
        tblVista.getColumnModel().getColumn(6).setMinWidth(90);
        tblVista.getColumnModel().getColumn(6).setMaxWidth(110);
        tblVista.getColumnModel().getColumn(7).setMinWidth(90);
        tblVista.getColumnModel().getColumn(7).setMaxWidth(110);
        tblVista.getColumnModel().getColumn(8).setMinWidth(60);
        tblVista.getColumnModel().getColumn(8).setMaxWidth(70);
        tblVista.getColumnModel().getColumn(9).setMinWidth(100);
        tblVista.getColumnModel().getColumn(9).setMaxWidth(110);
        tblVista.getColumnModel().getColumn(10).setMinWidth(100);
        tblVista.getColumnModel().getColumn(10).setMaxWidth(120);
        tblVista.getColumnModel().getColumn(11).setMinWidth(100);
        tblVista.getColumnModel().getColumn(11).setMaxWidth(120);

    }

    private void listarTipoAntena() {

        listipoAntena = antenaDao.listarTipoAntena();
        cboTipoAntena.addItem("seleccione item");
        for (tipoAntena tip : listipoAntena) {

            cboTipoAntena.addItem(tip.getTipoAntena());

        }

    }

    private void listarTipoAntenaBusqueda() {

        listipoAntena = antenaDao.listarTipoAntena();
        modCboTAB.addElement("todos");
        for (tipoAntena tip : listipoAntena) {

            modCboTAB.addElement(tip.getTipoAntena());

        }
        cboTipoAntenaBusqueda.setModel(modCboTAB);
    }

    private void listaServidores() {
        cboServidor.addItem("seleccione item");
        modCboserverBusc.addElement("todos");
        lstServ = servidorDao.listarNombre();
        for (Servidor se : lstServ) {
            cboServidor.addItem(se.getNomServ());
            modCboserverBusc.addElement(se.getNomServ());

        }
        cboServidorBusqueda.setModel(modCboserverBusc);
    }

    private void listaTorre() {
        modCboTorreBusc.addElement("todos");
        cboTorre.addItem("seleccione item");
        cboTorre2.addItem("seleccione item");
        lstTorre = torreDao.listarNobre();
        for (Torre to : lstTorre) {
            cboTorre.addItem(to.getNombre());
            cboTorre2.addItem(to.getNombre());
            modCboTorreBusc.addElement(to.getNombre());
        }
        cboTorreBusqueda.setModel(modCboTorreBusc);
    }

    private void estadoCboAntEm() {
        if (cboTipoAntena.getSelectedItem().equals("Enlace_Receptor")) {
            cboAE.setEnabled(true);
            cboTorre2.setEnabled(true);
            pnlAEE.setVisible(true);

        } else {
            pnlAEE.setVisible(false);
            cboAE.setEnabled(false);
            cboTorre2.setEnabled(false);
        }

    }

    /*
     private void listarAntena() {
     listAntena = DaoAntena.listar();
     Object[] colum = {"Id", "Nombre", "IP", "Mac", "Frecuencia", "Canal", "Passw_Config", "Passw_conexion", "id_Ant_Enlace", "id_Serv", "id_Torre", "id_Tipo"};
     modTabla = new DefaultTableModel(null, colum) {

     public boolean isCellEditable(int row, int column) {
     return false;
     }
     };
     for (Antena a : listAntena) {
     Object[] fila = {a.getIdAntena(), a.getNombre(), a.getIp(), a.getMac(), a.getFrecuencia(), a.getCanal(), a.getPasswConfig(), a.getPasswConexion(), a.getIdAntenaEnlace(), a.getIdServidor(), a.getIdTorre(), a.getIdTipo()};
     modTabla.addRow(fila);

     }
     tblVista.setModel(modTabla);
     anchoColum();
     }
     */

    private void listarAntena2() {
        System.out.println("  ----inicio listar antena 2");
        listAntena = antenaDao.listar();
        int k = antenaDao.listar2().size();
        Object[] colum = {"Id", "Nombre", "IP", "Mac", "Frecuencia", "Canal", "Passw_Config", "Passw_conexion", "Antena/E_E", "Servidor", "Torre", "Tipo_Antena"};
        modTabla = new DefaultTableModel(null, colum) {

            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        if (k > 0) {
            for (Object[] fila : antenaDao.listar2()) {

                modTabla.addRow(fila);

            }
            tblVista.setModel(modTabla);
            anchoColum();
            lblTotal.setText("" + k);
        } else {
            tblVista.setModel(modTabla);
            lblTotal.setText("" + k);
        }

        System.out.println("  ----inicio listar antena 2");
    }

    private void listarAntenaBusqueda() {

        ArrayList<Object[]> lst = antenaDao.listarBuquedadNomTipo(txtBuscar.getText().trim(), sql());

        int k = lst.size();

        Object[] colum = {"Id", "Nombre", "IP", "Mac", "Frecuencia", "Canal", "Passw_Config", "Passw_conexion", "Antena/E_E", "Servidor", "Torre", "Tipo_Antena"};
        modTabla = new DefaultTableModel(null, colum) {

            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        if (k > 0) {
            llave = false;

            for (Object[] f : lst) {

                modTabla.addRow(f);

            }

            tblVista.setModel(modTabla);

            anchoColum();
            lblTotal.setText("" + k);
            //---
            int i = 0;
            id = (int) tblVista.getValueAt(i, 0);
            txtNombre.setText(tblVista.getValueAt(i, 1).toString());
            txtIp.setText(tblVista.getValueAt(i, 2).toString());
            txtMac1.setText(tblVista.getValueAt(i, 3).toString());
            if (tblVista.getValueAt(i, 4).toString().equals(rbt1.getText())) {
                rbt1.setSelected(true);
            }
            txtCanal.setText(tblVista.getValueAt(i, 5).toString());
            txtPasswConfig.setText(tblVista.getValueAt(i, 6).toString());
            txtPasswConexion.setText(tblVista.getValueAt(i, 7).toString());
            //----------

            cboTipoAntena.setSelectedItem(tblVista.getValueAt(i, 11).toString());
            cboTorre.setSelectedItem(tblVista.getValueAt(i, 10).toString());
            cboServidor.setSelectedItem(tblVista.getValueAt(i, 9).toString());
            DefaultComboBoxModel modCbo = new DefaultComboBoxModel();

            if (nomAntEnlace(Integer.parseInt(tblVista.getValueAt(i, 8).toString())) != null) {

                modCbo.addElement(nomAntEnlace(Integer.parseInt(tblVista.getValueAt(i, 8).toString())));
                cboAE.setModel(modCbo);

            } else {

                cboAE.setModel(modCbo);
            }

            //---
        } else {
            llave = true;

            tblVista.setModel(modTabla);
            lblTotal.setText("" + k);

            txtNombre.setText(null);
            txtIp.setText(null);
            txtCanal.setText(null);
            txtMac1.setText(null);
            txtPasswConexion.setText(null);
            txtPasswConfig.setText(null);
            rbt1.setSelected(true);
            cboServidor.setSelectedIndex(0);
            cboTipoAntena.setSelectedIndex(0);
            DefaultComboBoxModel modcboA = new DefaultComboBoxModel();
            cboAE.setModel(modcboA);
            cboTorre.setSelectedIndex(0);

            // cboTipoAntenaBusqueda.setSelectedIndex(0);
            // modCboTAB.setSelectedItem("todos");
            id = -1;

        }

    }

    public void validaCampoNullo() {
    }

    private void registro() {
        if (txtNombre.getText().trim().length() == 0 || txtIp.getText().trim().length() == 0 || cboTipoAntena.getSelectedIndex() == 0 || cboServidor.getSelectedIndex() == 0 || cboTorre.getSelectedIndex() == 0) {
            Mensaje.panelSms("LLENE LOS CAMPOS NECESARIOS");
        } else {
            buscarIdTipoAntena();
            buscarIdAntenaE();
            buscarIdServ();
            buscarIdTorre();
            Antena a = new Antena();
            a.setNombre(txtNombre.getText());
            a.setIp(txtIp.getText());
            a.setMac(txtMac1.getText());
            if (rbt1.isSelected()) {
                a.setFrecuencia(rbt1.getText());
                System.out.println("frecuencia :" + a.getFrecuencia());

            } else {
                a.setFrecuencia(rbt2.getText());
                System.out.println("frecuencia :" + a.getFrecuencia());
            }
            a.setCanal(txtCanal.getText());

            if (ta != 0) {
                a.setIdTipo(ta);
            }
            if (ae != 0) {
                a.setIdAntenaEnlace(ae);
            }
            if (s != 0) {
                a.setIdServidor(s);
            }

            if (t != 0) {
                a.setIdTorre(t);
            }

            a.setPasswConfig(txtPasswConfig.getText());
            a.setPasswConexion(txtPasswConexion.getText());

            antenaDao.registrar(a);
            limpiar();

        }
    }

    private void actualisar() {
        if (txtNombre.getText().trim().length() == 0 || txtIp.getText().trim().length() == 0 || txtMac1.getText().trim().length() == 0 || txtCanal.getText().trim().length() == 0 || cboTipoAntena.getSelectedIndex() == 0 || cboServidor.getSelectedIndex() == 0 || cboTorre.getSelectedIndex() == 0) {
            Mensaje.panelSms("LLENE LOS CAMPOS NECESARIOS");
        } else {
            buscarIdTipoAntena();
            buscarIdAntenaE();
            buscarIdServ();
            buscarIdTorre();
            Antena a = new Antena();
            a.setIdAntena(id);
            a.setNombre(txtNombre.getText());
            a.setIp(txtIp.getText());
            a.setMac(txtMac1.getText());
            if (rbt1.isSelected()) {
                a.setFrecuencia(rbt1.getText());
                System.out.println("frecuencia :" + a.getFrecuencia());

            } else {
                a.setFrecuencia(rbt2.getText());
                System.out.println("frecuencia :" + a.getFrecuencia());
            }
            a.setCanal(txtCanal.getText());

            if (ta != 0) {
                a.setIdTipo(ta);
            }
            if (ae != 0) {
                a.setIdAntenaEnlace(ae);
            }
            if (s != 0) {
                a.setIdServidor(s);
            }

            if (t != 0) {
                a.setIdTorre(t);
            }

            a.setPasswConfig(txtPasswConfig.getText());
            a.setPasswConexion(txtPasswConexion.getText());

            antenaDao.modificar(a);

        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        cboTorre1 = new javax.swing.JComboBox();
        panel = new javax.swing.JPanel();
        txtPasswConexion = new javax.swing.JTextField();
        cboServidor = new javax.swing.JComboBox();
        cboTorre = new javax.swing.JComboBox();
        txtCanal = new javax.swing.JTextField();
        txtPasswConfig = new javax.swing.JTextField();
        rbt1 = new javax.swing.JRadioButton();
        jLabel9 = new javax.swing.JLabel();
        rbt2 = new javax.swing.JRadioButton();
        jLabel10 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtIp = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cboTipoAntena = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtMac1 = new javax.swing.JFormattedTextField();
        pnlAEE = new javax.swing.JPanel();
        cboTorre2 = new javax.swing.JComboBox();
        cboAE = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
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
        pnlBusqueda = new javax.swing.JPanel();
        txtBuscar = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        cboTipoAntenaBusqueda = new javax.swing.JComboBox();
        jLabel14 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        cboServidorBusqueda = new javax.swing.JComboBox();
        jLabel19 = new javax.swing.JLabel();
        cboTorreBusqueda = new javax.swing.JComboBox();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblVista = new javax.swing.JTable();
        jLabel15 = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();

        cboTorre1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cboTorre1.setMinimumSize(new java.awt.Dimension(55, 20));
        cboTorre1.setPreferredSize(new java.awt.Dimension(55, 20));
        cboTorre1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTorre1ActionPerformed(evt);
            }
        });

        setClosable(true);
        setIconifiable(true);
        setMinimumSize(new java.awt.Dimension(772, 743));
        setPreferredSize(new java.awt.Dimension(772, 743));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panel.setBackground(new java.awt.Color(255, 0, 0));
        panel.setOpaque(false);
        panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtPasswConexion.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtPasswConexion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPasswConexionKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPasswConexionKeyTyped(evt);
            }
        });
        panel.add(txtPasswConexion, new org.netbeans.lib.awtextra.AbsoluteConstraints(405, 39, 130, -1));

        cboServidor.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cboServidor.setMinimumSize(new java.awt.Dimension(55, 20));
        cboServidor.setPreferredSize(new java.awt.Dimension(55, 20));
        panel.add(cboServidor, new org.netbeans.lib.awtextra.AbsoluteConstraints(405, 71, 130, -1));

        cboTorre.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cboTorre.setMinimumSize(new java.awt.Dimension(55, 20));
        cboTorre.setPreferredSize(new java.awt.Dimension(55, 20));
        cboTorre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTorreActionPerformed(evt);
            }
        });
        panel.add(cboTorre, new org.netbeans.lib.awtextra.AbsoluteConstraints(405, 97, 130, -1));

        txtCanal.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtCanal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCanalActionPerformed(evt);
            }
        });
        txtCanal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCanalKeyTyped(evt);
            }
        });
        panel.add(txtCanal, new org.netbeans.lib.awtextra.AbsoluteConstraints(91, 159, 150, -1));

        txtPasswConfig.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtPasswConfig.setMinimumSize(new java.awt.Dimension(55, 20));
        txtPasswConfig.setPreferredSize(new java.awt.Dimension(55, 20));
        txtPasswConfig.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPasswConfigKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPasswConfigKeyTyped(evt);
            }
        });
        panel.add(txtPasswConfig, new org.netbeans.lib.awtextra.AbsoluteConstraints(405, 7, 130, -1));

        buttonGroup2.add(rbt1);
        rbt1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        rbt1.setForeground(new java.awt.Color(255, 255, 255));
        rbt1.setSelected(true);
        rbt1.setText("2.4 GHZ");
        rbt1.setOpaque(false);
        panel.add(rbt1, new org.netbeans.lib.awtextra.AbsoluteConstraints(91, 126, -1, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Passw_Conexion");
        panel.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(264, 42, -1, -1));

        buttonGroup2.add(rbt2);
        rbt2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        rbt2.setForeground(new java.awt.Color(255, 255, 255));
        rbt2.setText("5.8 GHZ");
        rbt2.setOpaque(false);
        rbt2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbt2ActionPerformed(evt);
            }
        });
        panel.add(rbt2, new org.netbeans.lib.awtextra.AbsoluteConstraints(172, 126, -1, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Servidor (*)");
        panel.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(264, 70, 80, -1));

        txtNombre.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNombreKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreKeyTyped(evt);
            }
        });
        panel.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(89, 39, 154, -1));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Torre (*)");
        panel.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(264, 99, -1, 20));

        txtIp.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtIp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtIpKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIpKeyTyped(evt);
            }
        });
        panel.add(txtIp, new org.netbeans.lib.awtextra.AbsoluteConstraints(89, 67, 154, -1));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Nombre (*)");
        panel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 42, -1, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("IP (*)");
        panel.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("MAC ");
        panel.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 102, -1, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Frecuencia");
        panel.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, -1, -1));

        cboTipoAntena.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cboTipoAntena.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTipoAntenaActionPerformed(evt);
            }
        });
        panel.add(cboTipoAntena, new org.netbeans.lib.awtextra.AbsoluteConstraints(89, 7, 154, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Tipo Antena");
        panel.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Passw_Config.");
        panel.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(261, 12, -1, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("canal");
        panel.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 162, -1, -1));

        try {
            txtMac1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("AA:AA:AA:AA:AA:AA")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtMac1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtMac1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtMac1KeyPressed(evt);
            }
        });
        panel.add(txtMac1, new org.netbeans.lib.awtextra.AbsoluteConstraints(89, 99, 154, -1));

        pnlAEE.setBackground(new java.awt.Color(255, 255, 255));
        pnlAEE.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(153, 153, 153)));
        pnlAEE.setOpaque(false);

        cboTorre2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cboTorre2.setMinimumSize(new java.awt.Dimension(55, 20));
        cboTorre2.setPreferredSize(new java.awt.Dimension(55, 20));
        cboTorre2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTorre2ActionPerformed(evt);
            }
        });

        cboAE.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Antena_Enlace_Emisor");

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Torre/A-E-Emisora");

        javax.swing.GroupLayout pnlAEELayout = new javax.swing.GroupLayout(pnlAEE);
        pnlAEE.setLayout(pnlAEELayout);
        pnlAEELayout.setHorizontalGroup(
            pnlAEELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAEELayout.createSequentialGroup()
                .addGroup(pnlAEELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 5, Short.MAX_VALUE)
                .addGroup(pnlAEELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cboTorre2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboAE, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        pnlAEELayout.setVerticalGroup(
            pnlAEELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAEELayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlAEELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboTorre2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addGap(10, 10, 10)
                .addGroup(pnlAEELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboAE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        panel.add(pnlAEE, new org.netbeans.lib.awtextra.AbsoluteConstraints(264, 126, -1, -1));

        getContentPane().add(panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 550, 220));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setOpaque(false);
        jPanel4.setLayout(new java.awt.GridLayout(3, 2, 0, 4));

        chkBusqueda.setBackground(new java.awt.Color(255, 255, 255));
        chkBusqueda.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        chkBusqueda.setForeground(new java.awt.Color(0, 255, 0));
        chkBusqueda.setText("Buscar");
        chkBusqueda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/buscar32.png"))); // NOI18N
        chkBusqueda.setOpaque(false);
        chkBusqueda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkBusquedaActionPerformed(evt);
            }
        });
        jPanel4.add(chkBusqueda);

        btnRegistrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/table_add.png"))); // NOI18N
        btnRegistrar.setToolTipText("REGISTRAR");
        btnRegistrar.setOpaque(false);
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });
        jPanel4.add(btnRegistrar);

        btnRefrshe.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        btnRefrshe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/table_refresh.png"))); // NOI18N
        btnRefrshe.setToolTipText("REFRESH");
        btnRefrshe.setOpaque(false);
        btnRefrshe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefrsheActionPerformed(evt);
            }
        });
        jPanel4.add(btnRefrshe);

        btnModif.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/table_edit.png"))); // NOI18N
        btnModif.setToolTipText("MODIFICAR");
        btnModif.setOpaque(false);
        btnModif.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModifActionPerformed(evt);
            }
        });
        jPanel4.add(btnModif);

        btnCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/cancel.png"))); // NOI18N
        btnCerrar.setToolTipText("CERRAR");
        btnCerrar.setOpaque(false);
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });
        jPanel4.add(btnCerrar);

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/table_delete.png"))); // NOI18N
        btnEliminar.setToolTipText("ELIMINAR REGISTRO");
        btnEliminar.setOpaque(false);
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        jPanel4.add(btnEliminar);

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 150, 170, 160));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
        jPanel5.setOpaque(false);
        jPanel5.setLayout(null);

        chkB.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(chkB);
        chkB.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        chkB.setForeground(new java.awt.Color(255, 204, 0));
        chkB.setSelected(true);
        chkB.setText("Bloqueado");
        chkB.setOpaque(false);
        chkB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkBActionPerformed(evt);
            }
        });
        jPanel5.add(chkB);
        chkB.setBounds(10, 10, 91, 23);

        chkD.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(chkD);
        chkD.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        chkD.setForeground(new java.awt.Color(255, 204, 0));
        chkD.setText("Desbloqueado");
        chkD.setOpaque(false);
        chkD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkDActionPerformed(evt);
            }
        });
        jPanel5.add(chkD);
        chkD.setBounds(10, 40, 120, 23);

        getContentPane().add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 70, 170, 70));

        pnlBusqueda.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)), "Busqueda por :", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial Unicode MS", 3, 14), new java.awt.Color(255, 204, 0))); // NOI18N
        pnlBusqueda.setOpaque(false);

        txtBuscar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtBuscar.setForeground(new java.awt.Color(255, 204, 0));
        txtBuscar.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtBuscar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        txtBuscar.setCaretColor(new java.awt.Color(0, 255, 0));
        txtBuscar.setOpaque(false);
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 255, 0));
        jLabel12.setText("Nombre");

        cboTipoAntenaBusqueda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTipoAntenaBusquedaActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 255, 0));
        jLabel14.setText("Tipo antena");

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 255, 0));
        jLabel18.setText("Servidor");

        cboServidorBusqueda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboServidorBusquedaActionPerformed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 255, 0));
        jLabel19.setText("Torre");

        cboTorreBusqueda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTorreBusquedaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlBusquedaLayout = new javax.swing.GroupLayout(pnlBusqueda);
        pnlBusqueda.setLayout(pnlBusquedaLayout);
        pnlBusquedaLayout.setHorizontalGroup(
            pnlBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBusquedaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlBusquedaLayout.createSequentialGroup()
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(66, 66, 66)
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlBusquedaLayout.createSequentialGroup()
                        .addComponent(cboTipoAntenaBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cboServidorBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(pnlBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cboTorreBusqueda, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlBusquedaLayout.createSequentialGroup()
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 71, Short.MAX_VALUE))))
        );
        pnlBusquedaLayout.setVerticalGroup(
            pnlBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBusquedaLayout.createSequentialGroup()
                .addGroup(pnlBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel14)
                    .addComponent(jLabel18)
                    .addComponent(jLabel19))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addGroup(pnlBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboTipoAntenaBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboServidorBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboTorreBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        getContentPane().add(pnlBusqueda, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 550, 80));
        getContentPane().add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 390, 720, 10));

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
        jScrollPane1.setViewportView(tblVista);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 400, 730, 250));

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/Encabezados/antR2.png"))); // NOI18N
        getContentPane().add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, -10, 770, 80));

        lblTotal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTotal.setForeground(new java.awt.Color(0, 255, 0));
        getContentPane().add(lblTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 670, 100, 20));

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 255, 0));
        jLabel17.setText("Total registros : ");
        getContentPane().add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 670, 130, -1));

        jLabel13.setBackground(new java.awt.Color(51, 51, 51));
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/Encabezados/ftn1.png"))); // NOI18N
        jLabel13.setOpaque(true);
        getContentPane().add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 760, 690));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cboTipoAntenaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTipoAntenaActionPerformed
        estadoCboAntEm();
        listarAE();
        // TODO add your handling code here:
    }//GEN-LAST:event_cboTipoAntenaActionPerformed

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        registro();
        //limpiar();
        listarAntena2();

        // TODO add your handling code here:
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void btnRefrsheActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefrsheActionPerformed
        System.out.println("inicio refresh----------");
        limpiar();
        System.out.println("despues de limpiar---------");
        listarAntena2();
        System.out.println("fin refresh----------");
        // TODO add your handling code here:
    }//GEN-LAST:event_btnRefrsheActionPerformed

    private void btnModifActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModifActionPerformed
        actualisar();
        limpiar();
        listarAntena2();

        // TODO add your handling code here:
    }//GEN-LAST:event_btnModifActionPerformed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed

        if (id != -1) {
            int i = JOptionPane.showConfirmDialog(this, "Esta seguro de eliminar Registro?", "MENSAJE DE CONFIRMACION", JOptionPane.OK_OPTION, JOptionPane.NO_OPTION);
            if (i == 0) {
                antenaDao.eliminar(id);
                id = -1;
                limpiar();
                listarAntena2();
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

    private void chkBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkBActionPerformed

        Controller(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_chkBActionPerformed

    private void chkDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkDActionPerformed
        habilitarController();
        // TODO add your handling code here:
    }//GEN-LAST:event_chkDActionPerformed

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased

        listarAntenaBusqueda();
        if (llave == true) {
            txtBuscar.setText(Cadena.eliminarUltCaracter(txtBuscar.getText().trim()));

        }
        listarAntenaBusqueda();

        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarKeyReleased

    private void tblVistaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblVistaMouseClicked
        if (evt.getClickCount() == 2) {
            int i = tblVista.getSelectedRow();
            id = (int) tblVista.getValueAt(i, 0);
            txtNombre.setText(tblVista.getValueAt(i, 1).toString());
            txtIp.setText(tblVista.getValueAt(i, 2).toString());
            txtMac1.setText(tblVista.getValueAt(i, 3).toString());
            if (tblVista.getValueAt(i, 4).toString().equals(rbt1.getText())) {
                rbt1.setSelected(true);
            }
            txtCanal.setText(tblVista.getValueAt(i, 5).toString());
            txtPasswConfig.setText(tblVista.getValueAt(i, 6).toString());
            txtPasswConexion.setText(tblVista.getValueAt(i, 7).toString());
            //----------

            cboTipoAntena.setSelectedItem(tblVista.getValueAt(i, 11).toString());
            cboTorre.setSelectedItem(tblVista.getValueAt(i, 10).toString());
            cboServidor.setSelectedItem(tblVista.getValueAt(i, 9).toString());
            DefaultComboBoxModel modCbo = new DefaultComboBoxModel();

            if (nomAntEnlace(Integer.parseInt(tblVista.getValueAt(i, 8).toString())) != null) {

                modCbo.addElement(nomAntEnlace(Integer.parseInt(tblVista.getValueAt(i, 8).toString())));
                cboAE.setModel(modCbo);

            } else {

                cboAE.setModel(modCbo);
            }

        }

        // TODO add your handling code here:
    }//GEN-LAST:event_tblVistaMouseClicked

    private void txtCanalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCanalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCanalActionPerformed

    private void rbt2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbt2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbt2ActionPerformed

    private void chkBusquedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkBusquedaActionPerformed
        activarBusqueda();
        modCboTAB.setSelectedItem("todos");
        txtBuscar.requestFocus();

        // TODO add your handling code here:
    }//GEN-LAST:event_chkBusquedaActionPerformed

    private void cboTipoAntenaBusquedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTipoAntenaBusquedaActionPerformed

        System.out.println("---inicio cboTB----");

        listarAntenaBusqueda();
        txtBuscar.requestFocus();

        System.out.println("---fin cboTB----");

        // TODO add your handling code here:
    }//GEN-LAST:event_cboTipoAntenaBusquedaActionPerformed

    private void txtNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyTyped
        validarLongTexto(txtNombre, 20, evt);

// TODO add your handling code here:
    }//GEN-LAST:event_txtNombreKeyTyped

    private void txtIpKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIpKeyTyped
        if (txtIp.getText().trim().length() < 15) {
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
    }//GEN-LAST:event_txtIpKeyTyped

    private void txtCanalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCanalKeyTyped
        validarLongTexto(txtCanal, 30, evt);
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCanalKeyTyped

    private void txtPasswConfigKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPasswConfigKeyTyped
        validarLongTexto(txtPasswConfig, 20, evt);

// TODO add your handling code here:
    }//GEN-LAST:event_txtPasswConfigKeyTyped

    private void txtPasswConexionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPasswConexionKeyTyped
        validarLongTexto(txtPasswConexion, 20, evt);
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPasswConexionKeyTyped

    private void txtNombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyPressed
        txtNombre.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, java.util.Collections.EMPTY_SET);
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            txtIp.requestFocus();

        }

// TODO add your handling code here:
    }//GEN-LAST:event_txtNombreKeyPressed

    private void txtIpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIpKeyPressed
        txtIp.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, java.util.Collections.EMPTY_SET);
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            txtMac1.requestFocus();

        }

// TODO add your handling code here:
    }//GEN-LAST:event_txtIpKeyPressed

    private void txtMac1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMac1KeyPressed
        txtMac1.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, java.util.Collections.EMPTY_SET);
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            txtCanal.requestFocus();

        }
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMac1KeyPressed

    private void txtPasswConfigKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPasswConfigKeyPressed
        txtPasswConfig.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, java.util.Collections.EMPTY_SET);
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            txtPasswConexion.requestFocus();

        }
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPasswConfigKeyPressed

    private void txtPasswConexionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPasswConexionKeyPressed
        txtPasswConexion.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, java.util.Collections.EMPTY_SET);
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            cboServidor.requestFocus();

        }

// TODO add your handling code here:
    }//GEN-LAST:event_txtPasswConexionKeyPressed

    private void cboTorreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTorreActionPerformed
        // listarAE();

// TODO add your handling code here:
    }//GEN-LAST:event_cboTorreActionPerformed

    private void tblVistaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblVistaKeyPressed
        int i = tblVista.getSelectedRow();
        if (evt.getKeyCode() == 38) {
            i = i - 1;
            if (i >= 0) {

                id = (int) tblVista.getValueAt(i, 0);
                txtNombre.setText(tblVista.getValueAt(i, 1).toString());
                txtIp.setText(tblVista.getValueAt(i, 2).toString());
                txtMac1.setText(tblVista.getValueAt(i, 3).toString());
                if (tblVista.getValueAt(i, 4).toString().equals(rbt1.getText())) {
                    rbt1.setSelected(true);
                }
                txtCanal.setText(tblVista.getValueAt(i, 5).toString());
                txtPasswConfig.setText(tblVista.getValueAt(i, 6).toString());
                txtPasswConexion.setText(tblVista.getValueAt(i, 7).toString());
                //----------

                cboTipoAntena.setSelectedItem(tblVista.getValueAt(i, 11).toString());
                cboTorre.setSelectedItem(tblVista.getValueAt(i, 10).toString());
                cboServidor.setSelectedItem(tblVista.getValueAt(i, 9).toString());
                DefaultComboBoxModel modCbo = new DefaultComboBoxModel();

                if (nomAntEnlace(Integer.parseInt(tblVista.getValueAt(i, 8).toString())) != null) {

                    modCbo.addElement(nomAntEnlace(Integer.parseInt(tblVista.getValueAt(i, 8).toString())));
                    cboAE.setModel(modCbo);

                } else {

                    cboAE.setModel(modCbo);
                }

            }

        } else if (evt.getKeyCode() == 40) {
            i = i + 1;
            if (i < tblVista.getRowCount()) {
                id = (int) tblVista.getValueAt(i, 0);
                txtNombre.setText(tblVista.getValueAt(i, 1).toString());
                txtIp.setText(tblVista.getValueAt(i, 2).toString());
                txtMac1.setText(tblVista.getValueAt(i, 3).toString());
                if (tblVista.getValueAt(i, 4).toString().equals(rbt1.getText())) {
                    rbt1.setSelected(true);
                }
                txtCanal.setText(tblVista.getValueAt(i, 5).toString());
                txtPasswConfig.setText(tblVista.getValueAt(i, 6).toString());
                txtPasswConexion.setText(tblVista.getValueAt(i, 7).toString());
                //----------

                cboTipoAntena.setSelectedItem(tblVista.getValueAt(i, 11).toString());
                cboTorre.setSelectedItem(tblVista.getValueAt(i, 10).toString());
                cboServidor.setSelectedItem(tblVista.getValueAt(i, 9).toString());
                DefaultComboBoxModel modCbo = new DefaultComboBoxModel();

                if (nomAntEnlace(Integer.parseInt(tblVista.getValueAt(i, 8).toString())) != null) {

                    modCbo.addElement(nomAntEnlace(Integer.parseInt(tblVista.getValueAt(i, 8).toString())));
                    cboAE.setModel(modCbo);

                } else {

                    cboAE.setModel(modCbo);
                }

            }

        }

// TODO add your handling code here:
    }//GEN-LAST:event_tblVistaKeyPressed

    private void cboTorre1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTorre1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboTorre1ActionPerformed

    private void cboTorre2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTorre2ActionPerformed
        listarAE();

// TODO add your handling code here:
    }//GEN-LAST:event_cboTorre2ActionPerformed

    private void cboServidorBusquedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboServidorBusquedaActionPerformed
        System.out.println("---inicio cboserverBusac----");

        listarAntenaBusqueda();
        txtBuscar.requestFocus();

        System.out.println("---fin cboServerBusc----");

// TODO add your handling code here:
    }//GEN-LAST:event_cboServidorBusquedaActionPerformed

    private void cboTorreBusquedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTorreBusquedaActionPerformed

        System.out.println("---inicio cboTorreBusac----");

        listarAntenaBusqueda();
        txtBuscar.requestFocus();

        System.out.println("---fin cboTorreBusc----");
// TODO add your handling code here:
    }//GEN-LAST:event_cboTorreBusquedaActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnModif;
    private javax.swing.JButton btnRefrshe;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox cboAE;
    private javax.swing.JComboBox cboServidor;
    private javax.swing.JComboBox cboServidorBusqueda;
    private javax.swing.JComboBox cboTipoAntena;
    private javax.swing.JComboBox cboTipoAntenaBusqueda;
    private javax.swing.JComboBox cboTorre;
    private javax.swing.JComboBox cboTorre1;
    private javax.swing.JComboBox cboTorre2;
    private javax.swing.JComboBox cboTorreBusqueda;
    private javax.swing.JCheckBox chkB;
    private javax.swing.JCheckBox chkBusqueda;
    private javax.swing.JCheckBox chkD;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JPanel panel;
    private javax.swing.JPanel pnlAEE;
    private javax.swing.JPanel pnlBusqueda;
    private javax.swing.JRadioButton rbt1;
    private javax.swing.JRadioButton rbt2;
    private javax.swing.JTable tblVista;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtCanal;
    private javax.swing.JTextField txtIp;
    private javax.swing.JFormattedTextField txtMac1;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPasswConexion;
    private javax.swing.JTextField txtPasswConfig;
    // End of variables declaration//GEN-END:variables
}
