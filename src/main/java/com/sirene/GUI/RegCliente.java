/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirene.GUI;

import com.sirene.Bean.Cliente;
import com.sirene.Bean.EstadoCliente;
import com.sirene.Bean.Telefono;
import com.sirene.Bean.Usuario;
import com.sirene.Controller.Registro;
import com.sirene.Dao.*;

//import com.sirene.Dao.Impl.DaoAntena;
//import com.sirene.Dao.Impl.DaoCliente;
import com.sirene.Dao.Impl.DaoFactory;
//import com.sirene.Dao.Impl.DaoTelefono;
import com.sirene.Dao.Impl.Mensaje;
import static com.sirene.Utilitarios.Constantes.*;
import com.sirene.Utilitarios.Fecha;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author JINMI
 */
public class RegCliente extends javax.swing.JInternalFrame {

    ArrayList<Telefono> listTelf;
    Usuario usuario;
    DefaultListModel modLista;
    DefaultTableModel modTabla;
    int id = -1;
    String t = null;
    boolean llave = false;
    public final AntenaDao antenaDao;
    public final ClienteDao clienteDao;
public final TelefonoDao telefonoDao;

    public RegCliente() {
        initComponents();
        DaoFactory factory = DaoFactory.getInstance();
        antenaDao = factory.getAntenaDao(ANTENA);
        clienteDao = factory.getClienteDao(CLIENTE);
        telefonoDao=factory.getTelefonoDao(TELEFONO);

        localisar(1040, 10);
        setTitle("Registro_Clientes");
        transparenciaTabla();
        modLista = new DefaultListModel();
        controles();
        activarBusqueda();
        listarEtado();
        listarmarcas();
        listar();
        eventoDcFechaPago();

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

    private void eventoDcFechaPago() {

        DcFechaPago.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (DcFechaPago.getDate() != null) {
                    DcFechaCorte.setDate(Fecha.SumarDias(DcFechaPago.getDate(), 3));
                }

                //throw new UnsupportedOperationException("Not supported yet.");
            }
        });

    }

    private void totalItem() {
        int items = modTabla.getRowCount();
        lblTotal.setText("TOTAL ITEMS :  " + items);

    }

    public void setUser(Usuario us) {
        usuario = us;

        System.out.println("tipo de usuario  : " + usuario.getIdTipoUser());

    }

    public void validarLongTexto(JTextField txt, int l, java.awt.event.KeyEvent evt) {

        if (txt.getText().trim().length() >= l) {
            getToolkit().beep();
            evt.consume();
        }

    }

    private void controles() {

        btnModif.setEnabled(false);
        btnRegistrar.setEnabled(false);
        btnEliminar.setEnabled(false);
        //---
        txtApellido.setEditable(false);
        txtNombre.setEditable(false);
        txtDni.setEditable(false);
        txtDireccion.setEditable(false);
        txtCorreo.setEditable(false);
        DcFechaInicio.setEnabled(false);
        txtTarifa.setEditable(false);
        DcFechaPago.setEnabled(false);
        DcFechaCorte.setEnabled(false);
        txtMac.setEditable(false);
        txtIp.setEditable(false);
        txtAntena.setEditable(false);
        txtObservacion.setEditable(false);
        txtTelefno.setEditable(false);
        cboEstadoCliente.setEnabled(false);
        cboMarca.setEnabled(false);
        cboCondicionAntena.setEnabled(false);
        rbt1.setEnabled(false);
        rbt2.setEnabled(false);
        lstTelefonos.setEnabled(false);
        cboAnchoBanda.setEnabled(false);

        //-----
        id = -1;

    }

    public void habilitarController() {
        if (usuario.getIdTipoUser() == 1) {
            boolean l = true;
            btnModif.setEnabled(l);
            btnRegistrar.setEnabled(l);
            btnEliminar.setEnabled(l);

            txtApellido.setEditable(l);
            txtNombre.setEditable(l);
            txtDni.setEditable(l);
            txtDireccion.setEditable(l);
            txtCorreo.setEditable(l);
            DcFechaInicio.setEnabled(l);
            txtTarifa.setEditable(l);
            DcFechaPago.setEnabled(l);
            DcFechaCorte.setEnabled(l);
            txtMac.setEditable(l);
            txtIp.setEditable(l);
            txtAntena.setEditable(l);
            txtObservacion.setEditable(l);
            txtTelefno.setEditable(l);
            cboEstadoCliente.setEnabled(l);
            cboMarca.setEnabled(l);
            cboCondicionAntena.setEnabled(l);
            rbt1.setEnabled(l);
            rbt2.setEnabled(l);
            lstTelefonos.setEnabled(l);
            cboAnchoBanda.setEnabled(l);

        } else {
            Mensaje.panelSms("NO TIENE EL PRIVILEGIO ADMINISTRATIVO");
            chkB.setSelected(true);
        }

    }

    private void activarBusqueda() {
        if (chkBusqueda.isSelected()) {
            pnlBusqueda.setVisible(true);
        } else {
            pnlBusqueda.setVisible(false);
        }

    }

    private void listarEtado() {
        for (EstadoCliente e : clienteDao.listarEstadoCli()) {
            cboEstadoCliente.addItem(e.getDescripcion());
        }
        int k = cboEstadoCliente.getItemCount();
        cboEstadoCliente.removeItemAt(k - 1);

    }

    private void listarmarcas() {
        cboMarcaBusqueda.addItem("todas");
        for (String m : clienteDao.listarMarcas()) {
            cboMarca.addItem(m);
            cboMarcaBusqueda.addItem(m);
        }

    }

    private void registrar() {
        System.out.println("-----INICIO REGISTRAR-----");
        if (txtApellido.getText().trim().length() == 0 || txtNombre.getText().trim().length() == 0 || txtDni.getText().trim().length() == 0
                || txtDireccion.getText().trim().length() == 0 || txtTarifa.getText().trim().length() == 0
                || txtMac.getText().trim().length() == 0 || txtIp.getText().trim().length() == 0
                || txtAntena.getText().trim().length() == 0 || DcFechaPago.getDate() == null || DcFechaCorte.getDate() == null) {
            Mensaje.panelSms("Llene los campos necesarios y correctos");

        } else {
            Cliente c = new Cliente();
            c.setApellido(txtApellido.getText());
            c.setNombre(txtNombre.getText());
            c.setDni(txtDni.getText());
            c.setDireccion(txtDireccion.getText());
            c.setCorreo(txtCorreo.getText());
            c.setFechInicio(Fecha.convertToString2(DcFechaInicio.getDate()));
            if (txtTarifa.getText().trim().length() > 0) {
                c.setTarifa(Float.parseFloat(txtTarifa.getText()));
            }

            c.setFechVencimiento(Fecha.convertToString2(DcFechaPago.getDate()));
            c.setFechCorte(Fecha.convertToString2(DcFechaCorte.getDate()));
            c.setCondicioAntena(cboCondicionAntena.getSelectedItem().toString());
            c.setMarca(cboMarca.getSelectedItem().toString());
            c.setMac(txtMac.getText());
            c.setIp(txtIp.getText());

            if (rbt1.isSelected()) {
                c.setFrecuenci(rbt1.getText());
            } else {
                c.setFrecuenci(rbt2.getText());

            }
            c.setAnchoBanda(cboAnchoBanda.getSelectedItem().toString());
            c.setIdEstado(clienteDao.getIdEstado(cboEstadoCliente.getSelectedItem().toString()));

            c.setIdAntena(clienteDao.getIdAntena(txtAntena.getText().trim()));
            c.setObservacion(txtObservacion.getText());
//-------------------------------------
            listTelf = telefonoDao.listaTelefono(modLista);
            //clienteDao.registrarCliente(c);
            Registro.RegistrarCliente_Telefonos(c, listTelf);
            limpiar();

        }

        totalItem();
        System.out.println("-----FIN REGISTRAR-----");
    }

    public void modificar() {
        if (id != -1) {
            Cliente c = new Cliente();
            c.setIdCliente(id);
            c.setApellido(txtApellido.getText());
            c.setNombre(txtNombre.getText());
            c.setDni(txtDni.getText());
            c.setDireccion(txtDireccion.getText());
            c.setCorreo(txtCorreo.getText());
            c.setFechInicio(Fecha.convertToString2(DcFechaInicio.getDate()));
            c.setTarifa(Float.parseFloat(txtTarifa.getText()));
            c.setFechVencimiento(Fecha.convertToString2(DcFechaPago.getDate()));
            c.setFechCorte(Fecha.convertToString2(DcFechaCorte.getDate()));
            c.setCondicioAntena(cboCondicionAntena.getSelectedItem().toString());
            c.setMarca(cboMarca.getSelectedItem().toString());
            c.setMac(txtMac.getText());
            c.setIp(txtIp.getText());

            if (rbt1.isSelected()) {
                c.setFrecuenci(rbt1.getText());
            } else {
                c.setFrecuenci(rbt2.getText());

            }
            c.setAnchoBanda(cboAnchoBanda.getSelectedItem().toString());
            c.setIdEstado(clienteDao.getIdEstado(cboEstadoCliente.getSelectedItem().toString()));
            if (clienteDao.getIdEstado(cboEstadoCliente.getSelectedItem().toString()) == 4) {
                c.setUltCorteEjecutado(Fecha.hoy());
            } else {
                c.setUltCorteEjecutado(null);
            }
            c.setIdAntena(clienteDao.getIdAntena(txtAntena.getText()));
            c.setObservacion(txtObservacion.getText());

            clienteDao.modificarCliente(c);
        } else {
            Mensaje.panelSms("Seleccione un Cliente");
        }

    }

    private void listar() {
        int k = clienteDao.listarCliente().size();
        if (k > 0) {
            Object[] colum = {"Id", "Apellido", "Nombre", "DNI", "Direccion", " Correo", "F_Inicio", "Tarifa", "F_Pago", "F_Corte", "Cond_Antena", "Mac", "IP", "Frecuencia", "A_Banda", "Estado", "Antena B/C", "Observacion", "Tipo/Marca", "Fecha cortada"};
            modTabla = new DefaultTableModel(null, colum) {

                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            for (Object[] f : clienteDao.listarCliente()) {
                modTabla.addRow(f);

            }
            tblVista.setModel(modTabla);
            anchoColum();

            //totalItem();
            lblTotal.setText("Totat registros :  " + k);

        } else {
            lblTotal.setText("Total registros :  " + k);
        }

    }

    public void anchoColum() {
        tblVista.getColumnModel().getColumn(0).setMinWidth(30);
        tblVista.getColumnModel().getColumn(0).setMaxWidth(40);
        tblVista.getColumnModel().getColumn(1).setMinWidth(110);
        tblVista.getColumnModel().getColumn(1).setMaxWidth(120);
        tblVista.getColumnModel().getColumn(2).setMinWidth(110);
        tblVista.getColumnModel().getColumn(2).setMaxWidth(120);
        tblVista.getColumnModel().getColumn(3).setMinWidth(70);
        tblVista.getColumnModel().getColumn(3).setMaxWidth(70);
        tblVista.getColumnModel().getColumn(4).setMinWidth(160);
        tblVista.getColumnModel().getColumn(4).setMaxWidth(190);
        tblVista.getColumnModel().getColumn(5).setMinWidth(110);
        tblVista.getColumnModel().getColumn(5).setMaxWidth(130);
        tblVista.getColumnModel().getColumn(6).setMinWidth(80);
        tblVista.getColumnModel().getColumn(6).setMaxWidth(80);
        tblVista.getColumnModel().getColumn(7).setMinWidth(40);
        tblVista.getColumnModel().getColumn(7).setMaxWidth(60);
        tblVista.getColumnModel().getColumn(8).setMinWidth(80);
        tblVista.getColumnModel().getColumn(8).setMaxWidth(80);
        tblVista.getColumnModel().getColumn(9).setMinWidth(80);
        tblVista.getColumnModel().getColumn(9).setMaxWidth(80);
        tblVista.getColumnModel().getColumn(10).setMinWidth(85);
        tblVista.getColumnModel().getColumn(10).setMaxWidth(85);
        tblVista.getColumnModel().getColumn(11).setMinWidth(110);
        tblVista.getColumnModel().getColumn(11).setMaxWidth(110);
        tblVista.getColumnModel().getColumn(12).setMinWidth(100);
        tblVista.getColumnModel().getColumn(12).setMaxWidth(110);
        tblVista.getColumnModel().getColumn(13).setMinWidth(70);
        tblVista.getColumnModel().getColumn(13).setMaxWidth(70);
        tblVista.getColumnModel().getColumn(14).setMinWidth(60);
        tblVista.getColumnModel().getColumn(14).setMaxWidth(65);
        tblVista.getColumnModel().getColumn(15).setMinWidth(60);
        tblVista.getColumnModel().getColumn(15).setMaxWidth(100);

        tblVista.getColumnModel().getColumn(16).setMinWidth(90);
        tblVista.getColumnModel().getColumn(16).setMaxWidth(10);
        tblVista.getColumnModel().getColumn(17).setMinWidth(40);
        tblVista.getColumnModel().getColumn(17).setMaxWidth(100);
        tblVista.getColumnModel().getColumn(18).setMinWidth(70);
        tblVista.getColumnModel().getColumn(18).setMaxWidth(90);
        tblVista.getColumnModel().getColumn(19).setMinWidth(110);
        tblVista.getColumnModel().getColumn(19).setMaxWidth(110);

    }

    private void listarBusc() {
        int k = clienteDao.busClienteApell_Nom_Dni(txtBuscarApellido.getText(), txtBuscarNombre.getText(), txtBuscarDNI.getText(), cboMarcaBusqueda.getSelectedItem().toString()).size();
        if (k > 0) {
            Object[] colum = {"Id", "Apellido", "Nombre", "DNI", "Direccion", " Correo", "F_Inicio", "Tarifa", "F_Pago", "F_Corte", "Cond_Antena", "Mac", "IP", "Frecuencia", "A_Banda", "Estado", "Antena B/C", "Observacion", "Tipo/Marca", "Fecha cortada"};
            modTabla = new DefaultTableModel(null, colum) {

                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            for (Object[] f : clienteDao.busClienteApell_Nom_Dni(txtBuscarApellido.getText(), txtBuscarNombre.getText(), txtBuscarDNI.getText(), cboMarcaBusqueda.getSelectedItem().toString())) {
                modTabla.addRow(f);

            }
            tblVista.setModel(modTabla);
            anchoColum();
            lblTotal.setText("Total registros :  " + k);

        } else {
            limpiar();
            lblTotal.setText("Total registros :  " + k);
            Mensaje.panelSms("Registros no Encontrados");
            listar();

        }

    }

    /*
     public void mostrarCampos() {
     int i = 0;
     id = (int) tblVista.getValueAt(i, 0);
     txtApellido.setText(tblVista.getValueAt(i, 1).toString());
     txtNombre.setText(tblVista.getValueAt(i, 2).toString());
     txtDni.setText(tblVista.getValueAt(i, 3).toString());
     txtDireccion.setText(tblVista.getValueAt(i, 4).toString());
     txtCorreo.setText(tblVista.getValueAt(i, 5).toString());
     DcFechaInicio.setDate(Fecha.deStrigaDate(tblVista.getValueAt(i, 6).toString()));
     txtTarifa.setText(tblVista.getValueAt(i, 7).toString());
        
     DcFechaPago.setDate(Fecha.deStrigaDate(tblVista.getValueAt(i, 8).toString()));
     DcFechaCorte.setDate(Fecha.deStrigaDate(tblVista.getValueAt(i, 9).toString()));
     //"Condicion_Antena","Mac","IP","Frecuencia","Ancho_Banda","Estado","Antena","Observacion"};
     cboCondicionAntena.setSelectedItem(tblVista.getValueAt(i, 10).toString());

     txtMac.setText(tblVista.getValueAt(i, 11).toString());
     txtIp.setText(tblVista.getValueAt(i, 12).toString());
     if (tblVista.getValueAt(i, 13).toString().equals(rbt1.getText())) {
     rbt1.setSelected(true);
     }
     cboAnchoBanda.setSelectedItem(tblVista.getValueAt(i, 14).toString());
     cboEstadoCliente.setSelectedItem(tblVista.getValueAt(i, 15).toString());
     txtAntena.setText(tblVista.getValueAt(i, 16).toString());
     txtObservacion.setText(tblVista.getValueAt(i, 17).toString());
     if (tblVista.getValueAt(i, 18) == null) {
     cboMarca.setSelectedIndex(-1);

     } else {
     cboMarca.setSelectedItem(tblVista.getValueAt(i, 18).toString());
     }
     //txtTelefno.setText(null);
     modLista.removeAllElements();
     for (Telefono te : telefonoDao.listarTelfCliente(id)) {
     modLista.addElement(te.getTelefono());
     lstTelefonos.setModel(modLista);
     }

     }
     */

    public void limpiar() {

        txtApellido.setText(null);
        txtNombre.setText(null);
        txtDni.setText(null);
        txtDireccion.setText(null);
        txtCorreo.setText(null);
        DcFechaInicio.setDate(null);
        txtTarifa.setText(null);
        DcFechaPago.setDate(null);
        DcFechaCorte.setDate(null);
        txtMac.setText(null);
        txtIp.setText(null);
        txtAntena.setText(null);
        txtObservacion.setText(null);
        txtTelefno.setText(null);
        cboEstadoCliente.setSelectedIndex(0);
        cboMarca.setSelectedIndex(0);
        cboCondicionAntena.setSelectedIndex(0);
        rbt1.setSelected(true);

        id = -1;
        modLista.removeAllElements();
        txtBuscarApellido.setText(null);
        txtBuscarDNI.setText(null);
        txtBuscarNombre.setText(null);
        cboMarcaBusqueda.setSelectedIndex(0);

    }

    public void agregarTelefono_lista() {
        modLista.addElement(txtTelefno.getText());
        lstTelefonos.setModel(modLista);
        txtTelefno.setText(null);
        txtTelefno.requestFocus();

    }

    public void llanarCampos() {
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txtApellido = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        txtDireccion = new javax.swing.JTextField();
        txtCorreo = new javax.swing.JTextField();
        txtTarifa = new javax.swing.JTextField();
        cboCondicionAntena = new javax.swing.JComboBox();
        txtIp = new javax.swing.JTextField();
        rbt2 = new javax.swing.JRadioButton();
        rbt1 = new javax.swing.JRadioButton();
        cboAnchoBanda = new javax.swing.JComboBox();
        cboEstadoCliente = new javax.swing.JComboBox();
        txtAntena = new javax.swing.JTextField();
        DcFechaInicio = new com.toedter.calendar.JDateChooser();
        DcFechaPago = new com.toedter.calendar.JDateChooser();
        DcFechaCorte = new com.toedter.calendar.JDateChooser();
        btnBuscarAntena = new javax.swing.JButton();
        txtObservacion = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstTelefonos = new javax.swing.JList();
        txtTelefno = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        cboMarca = new javax.swing.JComboBox();
        txtDni = new javax.swing.JFormattedTextField();
        txtMac = new javax.swing.JFormattedTextField();
        jPanel3 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        btnEliminarTelf = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        chkBusqueda = new javax.swing.JCheckBox();
        btnRegistrar = new javax.swing.JButton();
        btnModif = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnRefrshe = new javax.swing.JButton();
        btnCerrar = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        chkB = new javax.swing.JCheckBox();
        chkD = new javax.swing.JCheckBox();
        pnlBusqueda = new javax.swing.JPanel();
        txtBuscarApellido = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        txtBuscarNombre = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        txtBuscarDNI = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        cboMarcaBusqueda = new javax.swing.JComboBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblVista = new javax.swing.JTable();
        jSeparator1 = new javax.swing.JSeparator();
        lblTotal = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setMinimumSize(new java.awt.Dimension(1040, 740));
        setOpaque(true);
        setPreferredSize(new java.awt.Dimension(1040, 740));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.setOpaque(false);
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Apellido (*)");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(8, 18, -1, -1));

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Nombre(*)");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(8, 51, -1, -1));

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("DNI(*)");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(8, 84, -1, -1));

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Correo");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(8, 147, -1, -1));

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Fecha_Inicio");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(8, 180, -1, -1));

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Tarifa(*)");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(8, 211, -1, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Fecha_Pago(*)");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(334, 14, -1, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Fecha_Corte(*)");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(334, 51, -1, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Mac (*)");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 117, -1, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("IP (*)");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 147, -1, -1));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Antena (*)");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(615, 78, -1, -1));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Frecuencia");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(333, 182, -1, -1));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("A_ Banda");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(615, 11, -1, -1));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Condicion_Antena(*)");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 82, -1, -1));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Observacion");
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(615, 111, -1, -1));

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Estado (*)");
        jPanel1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(615, 44, -1, -1));

        txtApellido.setBackground(new java.awt.Color(204, 204, 204));
        txtApellido.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtApellido.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtApellidoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtApellidoKeyTyped(evt);
            }
        });
        jPanel1.add(txtApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 18, 216, -1));

        txtNombre.setBackground(new java.awt.Color(204, 204, 204));
        txtNombre.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNombreKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreKeyTyped(evt);
            }
        });
        jPanel1.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 51, 216, -1));

        txtDireccion.setBackground(new java.awt.Color(204, 204, 204));
        txtDireccion.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtDireccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDireccionKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDireccionKeyTyped(evt);
            }
        });
        jPanel1.add(txtDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 113, 216, -1));

        txtCorreo.setBackground(new java.awt.Color(204, 204, 204));
        txtCorreo.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtCorreo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCorreoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCorreoKeyTyped(evt);
            }
        });
        jPanel1.add(txtCorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 147, 216, -1));

        txtTarifa.setBackground(new java.awt.Color(204, 204, 204));
        txtTarifa.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtTarifa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTarifaKeyTyped(evt);
            }
        });
        jPanel1.add(txtTarifa, new org.netbeans.lib.awtextra.AbsoluteConstraints(93, 210, 121, -1));

        cboCondicionAntena.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        cboCondicionAntena.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Alquilada", "Propia", "Pc", "Router" }));
        cboCondicionAntena.setOpaque(false);
        jPanel1.add(cboCondicionAntena, new org.netbeans.lib.awtextra.AbsoluteConstraints(461, 79, 136, -1));

        txtIp.setBackground(new java.awt.Color(204, 204, 204));
        txtIp.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtIp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIpKeyTyped(evt);
            }
        });
        jPanel1.add(txtIp, new org.netbeans.lib.awtextra.AbsoluteConstraints(461, 147, 136, -1));

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
        jPanel1.add(rbt2, new org.netbeans.lib.awtextra.AbsoluteConstraints(528, 178, -1, -1));

        buttonGroup2.add(rbt1);
        rbt1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        rbt1.setForeground(new java.awt.Color(255, 255, 255));
        rbt1.setSelected(true);
        rbt1.setText("2.4 GHZ");
        rbt1.setOpaque(false);
        jPanel1.add(rbt1, new org.netbeans.lib.awtextra.AbsoluteConstraints(453, 178, -1, -1));

        cboAnchoBanda.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        cboAnchoBanda.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "512 KBps", "1 MBps", "2 MBps", "3 MBps", "4 MBps", "6 MBps", "10 MBps" }));
        jPanel1.add(cboAnchoBanda, new org.netbeans.lib.awtextra.AbsoluteConstraints(699, 8, 136, -1));

        cboEstadoCliente.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jPanel1.add(cboEstadoCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(699, 41, 136, -1));

        txtAntena.setBackground(new java.awt.Color(204, 204, 204));
        txtAntena.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtAntena.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtAntenaKeyPressed(evt);
            }
        });
        jPanel1.add(txtAntena, new org.netbeans.lib.awtextra.AbsoluteConstraints(699, 74, 134, -1));

        DcFechaInicio.setBackground(new java.awt.Color(204, 204, 204));
        DcFechaInicio.setForeground(new java.awt.Color(204, 204, 204));
        DcFechaInicio.setDateFormatString("dd-MM-yyyy");
        DcFechaInicio.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        DcFechaInicio.setOpaque(false);
        DcFechaInicio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DcFechaInicioKeyPressed(evt);
            }
        });
        jPanel1.add(DcFechaInicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(93, 180, 133, -1));

        DcFechaPago.setDateFormatString("dd-MM-yyyy");
        jPanel1.add(DcFechaPago, new org.netbeans.lib.awtextra.AbsoluteConstraints(461, 11, 136, -1));

        DcFechaCorte.setDateFormatString("dd-MM-yyyy");
        jPanel1.add(DcFechaCorte, new org.netbeans.lib.awtextra.AbsoluteConstraints(461, 48, 136, -1));

        btnBuscarAntena.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/buscar16.png"))); // NOI18N
        btnBuscarAntena.setToolTipText("BUSCAR ANTENA B/C");
        btnBuscarAntena.setOpaque(false);
        btnBuscarAntena.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarAntenaActionPerformed(evt);
            }
        });
        jPanel1.add(btnBuscarAntena, new org.netbeans.lib.awtextra.AbsoluteConstraints(851, 74, -1, -1));

        txtObservacion.setBackground(new java.awt.Color(204, 204, 204));
        txtObservacion.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtObservacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtObservacionKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtObservacionKeyTyped(evt);
            }
        });
        jPanel1.add(txtObservacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(699, 107, 201, -1));

        lstTelefonos.setBackground(new java.awt.Color(204, 204, 204));
        lstTelefonos.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lstTelefonos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lstTelefonosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(lstTelefonos);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(702, 176, 141, 63));

        txtTelefno.setBackground(new java.awt.Color(204, 204, 204));
        txtTelefno.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel1.add(txtTelefno, new org.netbeans.lib.awtextra.AbsoluteConstraints(702, 147, 141, -1));

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Telefonos");
        jPanel1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(615, 151, -1, -1));

        jLabel16.setBackground(new java.awt.Color(255, 255, 255));
        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Direccion(*)");
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(8, 117, -1, -1));

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("Tipo/marca Antena");
        jPanel1.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(333, 211, -1, -1));

        cboMarca.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        cboMarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboMarcaActionPerformed(evt);
            }
        });
        jPanel1.add(cboMarca, new org.netbeans.lib.awtextra.AbsoluteConstraints(469, 208, 132, -1));

        txtDni.setBackground(new java.awt.Color(204, 204, 204));
        try {
            txtDni.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("########")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtDni.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtDni.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDniKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDniKeyTyped(evt);
            }
        });
        jPanel1.add(txtDni, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 84, 216, -1));

        txtMac.setBackground(new java.awt.Color(204, 204, 204));
        try {
            txtMac.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("AA:AA:AA:AA:AA:AA")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtMac.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtMac.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtMacKeyPressed(evt);
            }
        });
        jPanel1.add(txtMac, new org.netbeans.lib.awtextra.AbsoluteConstraints(461, 107, 136, -1));

        jPanel3.setOpaque(false);
        jPanel3.setLayout(new java.awt.GridLayout(3, 0, 0, 3));

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/telephone_add.png"))); // NOI18N
        jButton3.setToolTipText("AGREGAR TELEFONO");
        jButton3.setOpaque(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton3);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/telephone_edit.png"))); // NOI18N
        jButton1.setToolTipText("MODIFICAR TELEFONO");
        jButton1.setOpaque(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton1);

        btnEliminarTelf.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/telephone_delete.png"))); // NOI18N
        btnEliminarTelf.setToolTipText("ELIMINAR TELEFONO");
        btnEliminarTelf.setOpaque(false);
        btnEliminarTelf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarTelfActionPerformed(evt);
            }
        });
        jPanel3.add(btnEliminarTelf);

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(853, 147, 47, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 920, 250));

        jPanel4.setOpaque(false);
        jPanel4.setLayout(new java.awt.GridLayout(6, 1, 0, 4));

        chkBusqueda.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        chkBusqueda.setForeground(new java.awt.Color(0, 255, 0));
        chkBusqueda.setText("Buscar");
        chkBusqueda.setToolTipText("BUSCAR CLIENTE");
        chkBusqueda.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        chkBusqueda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/buscar32.png"))); // NOI18N
        chkBusqueda.setOpaque(false);
        chkBusqueda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkBusquedaActionPerformed(evt);
            }
        });
        jPanel4.add(chkBusqueda);

        btnRegistrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/table_add.png"))); // NOI18N
        btnRegistrar.setToolTipText("REGISTRAR NUEVO CLIENTE");
        btnRegistrar.setOpaque(false);
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });
        jPanel4.add(btnRegistrar);

        btnModif.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/table_edit.png"))); // NOI18N
        btnModif.setToolTipText("MODIFICAR CLIENTE SELECCIONADO");
        btnModif.setOpaque(false);
        btnModif.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModifActionPerformed(evt);
            }
        });
        jPanel4.add(btnModif);

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/table_delete.png"))); // NOI18N
        btnEliminar.setToolTipText("ELIMINAR REGISTRO DE CLIENTE");
        btnEliminar.setOpaque(false);
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        jPanel4.add(btnEliminar);

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

        btnCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/cancel.png"))); // NOI18N
        btnCerrar.setToolTipText("CERRAR");
        btnCerrar.setOpaque(false);
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });
        jPanel4.add(btnCerrar);

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 120, 90, 250));

        jPanel5.setOpaque(false);
        jPanel5.setLayout(null);

        buttonGroup1.add(chkB);
        chkB.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
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
        chkB.setBounds(20, 10, 85, 23);

        buttonGroup1.add(chkD);
        chkD.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        chkD.setForeground(new java.awt.Color(255, 204, 0));
        chkD.setText("Desbloqueado");
        chkD.setOpaque(false);
        chkD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkDActionPerformed(evt);
            }
        });
        jPanel5.add(chkD);
        chkD.setBounds(100, 10, 105, 23);

        getContentPane().add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 80, 220, 40));

        pnlBusqueda.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)), "Busqueda por :", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial Unicode MS", 3, 14), new java.awt.Color(255, 204, 51))); // NOI18N
        pnlBusqueda.setOpaque(false);

        txtBuscarApellido.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txtBuscarApellido.setForeground(new java.awt.Color(255, 204, 0));
        txtBuscarApellido.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtBuscarApellido.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        txtBuscarApellido.setCaretColor(new java.awt.Color(51, 255, 0));
        txtBuscarApellido.setOpaque(false);
        txtBuscarApellido.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBuscarApellidoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarApellidoKeyReleased(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 255, 0));
        jLabel18.setText("Apellido");

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(0, 255, 0));
        jLabel21.setText("Nombre");

        txtBuscarNombre.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txtBuscarNombre.setForeground(new java.awt.Color(255, 204, 0));
        txtBuscarNombre.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtBuscarNombre.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        txtBuscarNombre.setCaretColor(new java.awt.Color(51, 255, 0));
        txtBuscarNombre.setOpaque(false);
        txtBuscarNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBuscarNombreKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarNombreKeyReleased(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(0, 255, 0));
        jLabel22.setText("DNI");

        txtBuscarDNI.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txtBuscarDNI.setForeground(new java.awt.Color(255, 204, 0));
        txtBuscarDNI.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtBuscarDNI.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        txtBuscarDNI.setCaretColor(new java.awt.Color(51, 255, 0));
        txtBuscarDNI.setOpaque(false);
        txtBuscarDNI.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBuscarDNIKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarDNIKeyReleased(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(0, 255, 0));
        jLabel25.setText("T/M Antena");

        cboMarcaBusqueda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboMarcaBusquedaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlBusquedaLayout = new javax.swing.GroupLayout(pnlBusqueda);
        pnlBusqueda.setLayout(pnlBusquedaLayout);
        pnlBusquedaLayout.setHorizontalGroup(
            pnlBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlBusquedaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtBuscarApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtBuscarNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtBuscarDNI, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel25)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cboMarcaBusqueda, 0, 143, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlBusquedaLayout.setVerticalGroup(
            pnlBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBusquedaLayout.createSequentialGroup()
                .addGroup(pnlBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscarApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18)
                    .addComponent(jLabel21)
                    .addComponent(txtBuscarNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22)
                    .addComponent(txtBuscarDNI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25)
                    .addComponent(cboMarcaBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 14, Short.MAX_VALUE))
        );

        getContentPane().add(pnlBusqueda, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 800, 60));

        jScrollPane2.setMinimumSize(new java.awt.Dimension(650, 700));
        jScrollPane2.setOpaque(false);

        tblVista.setBackground(new java.awt.Color(30, 29, 29));
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

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 390, 1000, 280));
        getContentPane().add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 380, 1000, 10));

        lblTotal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTotal.setForeground(new java.awt.Color(0, 255, 0));
        lblTotal.setText("TOTAL ITEMS : ");
        getContentPane().add(lblTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 680, 230, -1));

        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/Encabezados/regConsCli02.png"))); // NOI18N
        getContentPane().add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(-40, 0, 1100, 60));

        jLabel19.setBackground(new java.awt.Color(51, 51, 51));
        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/Encabezados/ftn1.png"))); // NOI18N
        jLabel19.setOpaque(true);
        getContentPane().add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 40, 1040, 780));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void rbt2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbt2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbt2ActionPerformed

    private void chkBusquedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkBusquedaActionPerformed
        activarBusqueda();
        txtBuscarApellido.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_chkBusquedaActionPerformed

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        if (antenaDao.existeAntena(txtAntena.getText())) {
            registrar();
            //limpiar();
            listar();
        } else {
            Mensaje.panelSms("  ERROR ! \n Antena no existe \n Asegurece que el campo no este vacio ");
            txtAntena.setText(null);
            txtAntena.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void btnRefrsheActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefrsheActionPerformed
        limpiar();
        listar();

    }//GEN-LAST:event_btnRefrsheActionPerformed

    private void btnModifActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModifActionPerformed
        modificar();
        limpiar();
        listar();

    }//GEN-LAST:event_btnModifActionPerformed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed

        dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed

        if (id != -1) {
            int i = JOptionPane.showConfirmDialog(this, "Estaseguro de eliminar Registro?", "MENSAJE DE CONFIRMACION", JOptionPane.OK_OPTION, JOptionPane.NO_OPTION);
            if (i == 0) {

                clienteDao.eliminarCliente(id);
                id = -1;
                limpiar();
                listar();

            } else {
                limpiar();
            }
        } else {
            Mensaje.panelSms("Seleccione un registro existente");
            limpiar();
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void chkBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkBActionPerformed
        controles();
        // TODO add your handling code here:
    }//GEN-LAST:event_chkBActionPerformed

    private void chkDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkDActionPerformed
        habilitarController();
        txtApellido.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_chkDActionPerformed

    private void txtBuscarApellidoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarApellidoKeyReleased
        listarBusc();
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarApellidoKeyReleased

    private void tblVistaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblVistaMouseClicked

        if (evt.getClickCount() == 2) {
            int i = tblVista.getSelectedRow();
            id = (int) tblVista.getValueAt(i, 0);
            txtApellido.setText(tblVista.getValueAt(i, 1).toString());
            txtNombre.setText(tblVista.getValueAt(i, 2).toString());
            txtDni.setText(tblVista.getValueAt(i, 3).toString());
            txtDireccion.setText(tblVista.getValueAt(i, 4).toString());
            txtCorreo.setText(tblVista.getValueAt(i, 5).toString());
            DcFechaInicio.setDate(null);

            if (tblVista.getValueAt(i, 6) != null) {
                DcFechaInicio.setDate(Fecha.deStrigaDate(tblVista.getValueAt(i, 6).toString()));
            } else {
                DcFechaInicio.setDate(null);
            }

            //DcFechaInicio.setDate(Fecha.deStrigaDate(tblVista.getValueAt(i, 6).toString()));
            txtTarifa.setText(tblVista.getValueAt(i, 7).toString());
            DcFechaPago.setDate(Fecha.deStrigaDate(tblVista.getValueAt(i, 8).toString()));
            DcFechaCorte.setDate(Fecha.deStrigaDate(tblVista.getValueAt(i, 9).toString()));
            //"Condicion_Antena","Mac","IP","Frecuencia","Ancho_Banda","Estado","Antena","Observacion"};
            cboCondicionAntena.setSelectedItem(tblVista.getValueAt(i, 10).toString());
            txtMac.setText(tblVista.getValueAt(i, 11).toString());
            txtIp.setText(tblVista.getValueAt(i, 12).toString());
            System.out.println(tblVista.getValueAt(i, 13).toString() + " =  " + rbt1.getText());
            if (tblVista.getValueAt(i, 13).toString().equals(rbt1.getText())) {
                rbt1.setSelected(true);
            } else {
                rbt2.setSelected(true);

            }
            cboAnchoBanda.setSelectedItem(tblVista.getValueAt(i, 14).toString());
            cboEstadoCliente.setSelectedItem(tblVista.getValueAt(i, 15).toString());
            txtAntena.setText(tblVista.getValueAt(i, 16).toString());
            txtObservacion.setText(tblVista.getValueAt(i, 17).toString());
            if (tblVista.getValueAt(i, 18) == null) {
                cboMarca.setSelectedIndex(-1);

            } else {
                cboMarca.setSelectedItem(tblVista.getValueAt(i, 18).toString());
            }

            //txtTelefno.setText(null);
            modLista.removeAllElements();
            for (Telefono te : telefonoDao.listarTelfCliente(id)) {
                modLista.addElement(te.getTelefono());
                lstTelefonos.setModel(modLista);

            }


    }//GEN-LAST:event_tblVistaMouseClicked
    }
    private void btnBuscarAntenaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarAntenaActionPerformed
        try {
            BuscarAntenaBC vs = new BuscarAntenaBC(null, true);
            vs.setVisible(true);
            /*   
             MdiPrincipal.desktopPane.add(vs);
             // vs.setTarnferenciaDatos(txtAntena);
             vs.show();
             */
        } catch (Exception e) {
            System.out.println("error :" + e);
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_btnBuscarAntenaActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if (txtTelefno.getText().trim().length() != 0) {
            if (id == -1) {
                agregarTelefono_lista();
            } else {
                Telefono te = new Telefono();
                te.setTelefono(txtTelefno.getText());
                telefonoDao.registrarTel(te, id);

                modLista.removeAllElements();
                for (Telefono tel : telefonoDao.listarTelfCliente(id)) {
                    modLista.addElement(tel.getTelefono());
                    lstTelefonos.setModel(modLista);
                }
                txtTelefno.setText(null);

            }
        } else {
            Mensaje.panelSms("El campo telefono esta vacio");
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (txtTelefno.getText().trim().length() != 0) {
            telefonoDao.modificarTelf(id, t, txtTelefno.getText());
            modLista.removeAllElements();
            for (Telefono te : telefonoDao.listarTelfCliente(id)) {
                modLista.addElement(te.getTelefono());
                lstTelefonos.setModel(modLista);

            }
            txtTelefno.setText(null);
            t = null;

        } else {
            Mensaje.panelSms("El campo telefono esta vacio");
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnEliminarTelfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarTelfActionPerformed

//--------
        if (id != -1) {
            int i = JOptionPane.showConfirmDialog(this, "Estaseguro de eliminar Registro?", "MENSAJE DE CONFIRMACION", JOptionPane.OK_OPTION, JOptionPane.NO_OPTION);
            if (i == 0) {
                if (txtTelefno.getText().trim().length() != 0) {
                    telefonoDao.eliminarTelf(id, t);
                    modLista.removeAllElements();
                    for (Telefono te : telefonoDao.listarTelfCliente(id)) {
                        modLista.addElement(te.getTelefono());
                        lstTelefonos.setModel(modLista);

                    }
                    txtTelefno.setText(null);
                    t = null;

                } else {
                    Mensaje.panelSms("El campo telefono esta vacio");
                }

            } else {
                txtTelefno.setText(null);
            }

        } else {
            Mensaje.panelSms("Seleccione un registro existente");
            limpiar();
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_btnEliminarTelfActionPerformed

    private void lstTelefonosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lstTelefonosMouseClicked
        if (evt.getClickCount() == 2) {
            t = lstTelefonos.getSelectedValue().toString();
            txtTelefno.setText(t);

        }

        // TODO add your handling code here:
    }//GEN-LAST:event_lstTelefonosMouseClicked

    private void txtBuscarNombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarNombreKeyReleased
        listarBusc();
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarNombreKeyReleased

    private void txtBuscarDNIKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarDNIKeyReleased
        listarBusc();
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarDNIKeyReleased

    private void txtApellidoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidoKeyPressed
        txtApellido.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, java.util.Collections.EMPTY_SET);
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            txtNombre.requestFocus();

        }
        // TODO add your handling code here:
    }//GEN-LAST:event_txtApellidoKeyPressed

    private void txtNombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyPressed
        txtNombre.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, java.util.Collections.EMPTY_SET);
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            txtDni.requestFocus();

        }
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreKeyPressed

    private void txtDireccionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDireccionKeyPressed
        txtDireccion.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, java.util.Collections.EMPTY_SET);
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            txtCorreo.requestFocus();

        }
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDireccionKeyPressed

    private void txtCorreoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCorreoKeyPressed
        txtCorreo.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, java.util.Collections.EMPTY_SET);
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            DcFechaInicio.requestFocus();

        }
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCorreoKeyPressed

    private void DcFechaInicioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DcFechaInicioKeyPressed
        DcFechaInicio.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, java.util.Collections.EMPTY_SET);
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            txtTarifa.requestFocus();

        }
        // TODO add your handling code here:
    }//GEN-LAST:event_DcFechaInicioKeyPressed

    private void txtDniKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDniKeyPressed
        txtDni.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, java.util.Collections.EMPTY_SET);
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            txtDireccion.requestFocus();

        }

// TODO add your handling code here:
    }//GEN-LAST:event_txtDniKeyPressed

    private void cboMarcaBusquedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboMarcaBusquedaActionPerformed
        txtBuscarApellido.setText(null);
        txtBuscarNombre.setText(null);
        txtBuscarDNI.setText(null);
        if (llave == true) {
            listarBusc();

            llave = true;
        } else {
            llave = true;
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_cboMarcaBusquedaActionPerformed

    private void txtTarifaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTarifaKeyTyped
        //char c = evt.getKeyChar();
        String s = String.valueOf(evt.getKeyChar());

        if (!s.matches("[0-9.]")) {
            getToolkit().beep();
            evt.consume();
        }
// 
// TODO add your handling code here:
    }//GEN-LAST:event_txtTarifaKeyTyped

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

    private void cboMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboMarcaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboMarcaActionPerformed

    private void txtMacKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMacKeyPressed
        txtMac.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, java.util.Collections.EMPTY_SET);
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            txtIp.requestFocus();

        }

// TODO add your handling code here:
    }//GEN-LAST:event_txtMacKeyPressed

    private void txtAntenaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAntenaKeyPressed
        txtAntena.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, java.util.Collections.EMPTY_SET);
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            txtObservacion.requestFocus();

        }
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAntenaKeyPressed

    private void txtObservacionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtObservacionKeyPressed
        txtObservacion.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, java.util.Collections.EMPTY_SET);
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            txtTelefno.requestFocus();

        }
        // TODO add your handling code here:
    }//GEN-LAST:event_txtObservacionKeyPressed

    private void txtBuscarApellidoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarApellidoKeyPressed
        txtBuscarApellido.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, java.util.Collections.EMPTY_SET);
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            txtBuscarNombre.requestFocus();

        }
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarApellidoKeyPressed

    private void txtBuscarNombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarNombreKeyPressed
        txtBuscarNombre.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, java.util.Collections.EMPTY_SET);
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            txtBuscarDNI.requestFocus();

        }

// TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarNombreKeyPressed

    private void txtBuscarDNIKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarDNIKeyPressed
        txtBuscarDNI.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, java.util.Collections.EMPTY_SET);
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            cboMarcaBusqueda.requestFocus();

        }

// TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarDNIKeyPressed

    private void tblVistaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblVistaKeyPressed
        int i = tblVista.getSelectedRow();
        if (evt.getKeyCode() == 38) {
            i = i - 1;
            if (i >= 0) {

                id = (int) tblVista.getValueAt(i, 0);
                txtApellido.setText(tblVista.getValueAt(i, 1).toString());
                txtNombre.setText(tblVista.getValueAt(i, 2).toString());
                txtDni.setText(tblVista.getValueAt(i, 3).toString());
                txtDireccion.setText(tblVista.getValueAt(i, 4).toString());
                txtCorreo.setText(tblVista.getValueAt(i, 5).toString());
                if (tblVista.getValueAt(i, 6) != null) {
                    DcFechaInicio.setDate(Fecha.deStrigaDate(tblVista.getValueAt(i, 6).toString()));
                } else {
                    DcFechaInicio.setDate(null);
                }

                txtTarifa.setText(tblVista.getValueAt(i, 7).toString());
                DcFechaPago.setDate(Fecha.deStrigaDate(tblVista.getValueAt(i, 8).toString()));
                DcFechaCorte.setDate(Fecha.deStrigaDate(tblVista.getValueAt(i, 9).toString()));
                //"Condicion_Antena","Mac","IP","Frecuencia","Ancho_Banda","Estado","Antena","Observacion"};
                cboCondicionAntena.setSelectedItem(tblVista.getValueAt(i, 10).toString());
                txtMac.setText(tblVista.getValueAt(i, 11).toString());
                txtIp.setText(tblVista.getValueAt(i, 12).toString());
                //System.out.println(tblVista.getValueAt(i, 13).toString()+" =  " + rbt1.getText());
                if (tblVista.getValueAt(i, 13).toString().equals(rbt1.getText())) {

                    rbt1.setSelected(true);
                } else {

                    rbt2.setSelected(true);
                }
                cboAnchoBanda.setSelectedItem(tblVista.getValueAt(i, 14).toString());
                cboEstadoCliente.setSelectedItem(tblVista.getValueAt(i, 15).toString());
                txtAntena.setText(tblVista.getValueAt(i, 16).toString());
                txtObservacion.setText(tblVista.getValueAt(i, 17).toString());
                if (tblVista.getValueAt(i, 18) == null) {
                    cboMarca.setSelectedIndex(-1);

                } else {
                    cboMarca.setSelectedItem(tblVista.getValueAt(i, 18).toString());
                }

                //txtTelefno.setText(null);
                modLista.removeAllElements();
                for (Telefono te : telefonoDao.listarTelfCliente(id)) {
                    modLista.addElement(te.getTelefono());
                    lstTelefonos.setModel(modLista);

                }

            }

        } else if (evt.getKeyCode() == 40) {
            i = i + 1;
            if (i < tblVista.getRowCount()) {
                id = (int) tblVista.getValueAt(i, 0);
                txtApellido.setText(tblVista.getValueAt(i, 1).toString());
                txtNombre.setText(tblVista.getValueAt(i, 2).toString());
                txtDni.setText(tblVista.getValueAt(i, 3).toString());
                txtDireccion.setText(tblVista.getValueAt(i, 4).toString());
                txtCorreo.setText(tblVista.getValueAt(i, 5).toString());
                if (tblVista.getValueAt(i, 6) != null) {
                    DcFechaInicio.setDate(Fecha.deStrigaDate(tblVista.getValueAt(i, 6).toString()));
                } else {
                    DcFechaInicio.setDate(null);
                }

                txtTarifa.setText(tblVista.getValueAt(i, 7).toString());
                DcFechaPago.setDate(Fecha.deStrigaDate(tblVista.getValueAt(i, 8).toString()));
                DcFechaCorte.setDate(Fecha.deStrigaDate(tblVista.getValueAt(i, 9).toString()));
                //"Condicion_Antena","Mac","IP","Frecuencia","Ancho_Banda","Estado","Antena","Observacion"};
                cboCondicionAntena.setSelectedItem(tblVista.getValueAt(i, 10).toString());
                txtMac.setText(tblVista.getValueAt(i, 11).toString());
                txtIp.setText(tblVista.getValueAt(i, 12).toString());
                if (tblVista.getValueAt(i, 13).toString().equals(rbt1.getText())) {
                    rbt1.setSelected(true);
                }
                cboAnchoBanda.setSelectedItem(tblVista.getValueAt(i, 14).toString());
                cboEstadoCliente.setSelectedItem(tblVista.getValueAt(i, 15).toString());
                txtAntena.setText(tblVista.getValueAt(i, 16).toString());
                txtObservacion.setText(tblVista.getValueAt(i, 17).toString());
                if (tblVista.getValueAt(i, 18) == null) {
                    cboMarca.setSelectedIndex(-1);

                } else {
                    cboMarca.setSelectedItem(tblVista.getValueAt(i, 18).toString());
                }

                //txtTelefno.setText(null);
                modLista.removeAllElements();
                for (Telefono te : telefonoDao.listarTelfCliente(id)) {
                    modLista.addElement(te.getTelefono());
                    lstTelefonos.setModel(modLista);

                }

            }

        }

// TODO add your handling code here:
    }//GEN-LAST:event_tblVistaKeyPressed

    private void txtApellidoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidoKeyTyped
        validarLongTexto(txtApellido, 30, evt);

// TODO add your handling code here:
    }//GEN-LAST:event_txtApellidoKeyTyped

    private void txtNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyTyped

        validarLongTexto(txtNombre, 30, evt);

// TODO add your handling code here:
    }//GEN-LAST:event_txtNombreKeyTyped

    private void txtDniKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDniKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDniKeyTyped

    private void txtDireccionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDireccionKeyTyped
        validarLongTexto(txtDireccion, 40, evt);

// TODO add your handling code here:
    }//GEN-LAST:event_txtDireccionKeyTyped

    private void txtObservacionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtObservacionKeyTyped
        validarLongTexto(txtObservacion, 60, evt);
        // TODO add your handling code here:
    }//GEN-LAST:event_txtObservacionKeyTyped

    private void txtCorreoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCorreoKeyTyped
        validarLongTexto(txtCorreo, 30, evt);
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCorreoKeyTyped

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser DcFechaCorte;
    private com.toedter.calendar.JDateChooser DcFechaInicio;
    private com.toedter.calendar.JDateChooser DcFechaPago;
    private javax.swing.JButton btnBuscarAntena;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnEliminarTelf;
    private javax.swing.JButton btnModif;
    private javax.swing.JButton btnRefrshe;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox cboAnchoBanda;
    private javax.swing.JComboBox cboCondicionAntena;
    private javax.swing.JComboBox cboEstadoCliente;
    private javax.swing.JComboBox cboMarca;
    private javax.swing.JComboBox cboMarcaBusqueda;
    private javax.swing.JCheckBox chkB;
    private javax.swing.JCheckBox chkBusqueda;
    private javax.swing.JCheckBox chkD;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
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
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JList lstTelefonos;
    private javax.swing.JPanel pnlBusqueda;
    private javax.swing.JRadioButton rbt1;
    private javax.swing.JRadioButton rbt2;
    private javax.swing.JTable tblVista;
    public static javax.swing.JTextField txtAntena;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtBuscarApellido;
    private javax.swing.JTextField txtBuscarDNI;
    private javax.swing.JTextField txtBuscarNombre;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JFormattedTextField txtDni;
    private javax.swing.JTextField txtIp;
    private javax.swing.JFormattedTextField txtMac;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtObservacion;
    private javax.swing.JTextField txtTarifa;
    private javax.swing.JTextField txtTelefno;
    // End of variables declaration//GEN-END:variables

    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
