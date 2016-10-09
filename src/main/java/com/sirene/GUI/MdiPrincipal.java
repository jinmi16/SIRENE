/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirene.GUI;

import com.sirene.Bean.Usuario;
import com.sirene.Dao.Impl.Mensaje;

import com.sirene.Utilitarios.Fecha;

import java.awt.Dimension;
import java.awt.Rectangle;


/**
 *
 * @author JINMI
 */
public class MdiPrincipal extends javax.swing.JFrame {
private int contador=1;
private int posicion=0;
    String nomPer;
    Usuario user;
    static int w_screen, h_screen;
    //DefaultDesktopManager modDesktop = new DefaultDesktopManager();

    public MdiPrincipal() {
         w_screen = getToolkit().getScreenSize().width;
        h_screen = getToolkit().getScreenSize().height;
        initComponents();
       // desktopPane.setBorder(new ImagenFondo());
        this.setExtendedState(MdiPrincipal.MAXIMIZED_BOTH);
setSize(w_screen,h_screen-20);
     desktopPane.setPreferredSize(new Dimension(w_screen-100,h_screen+20));
     
        //desktopPane.setPreferredSize(new Dimension(400+(posicion*50), 400+(posicion*50)));
        //this.desktopPane.setBounds(new Rectangle(0, 0, w_screen, h_screen - 100));
        this.lblFondo.setBounds(new Rectangle(0, 0, w_screen, h_screen+20));
        desktopPane.setOpaque(false);
        //desktopPane.setDesktopManager(this.modDesktop);

        actualizarEstadoCliente();
     

    }

    public void setUser(Object[] array) {

        nomPer = array[0].toString();
        user = (Usuario) array[1];

        setTitle("ING_JINMI" + "                                                           Usuario  : " + nomPer);
        desktopPane.setOpaque(false);

    }
public void privilegios(){
if(user.getIdTipoUser()==2){
mnRegistrar.setEnabled(false);
mitDesHab.setEnabled(false);
mitCongelarServicio.setEnabled(false);
mitDescongelarServicio.setEnabled(false);
miConsultaPersonalizada.setEnabled(false);
miConsultaPagos.setEnabled(false);
miPersonal.setEnabled(false);
}else{
mnRegistrar.setEnabled(true);
mitDesHab.setEnabled(true);
mitCongelarServicio.setEnabled(true);
mitDescongelarServicio.setEnabled(true);
miConsultaPersonalizada.setEnabled(true);
miConsultaPagos.setEnabled(true);
miPersonal.setEnabled(true);




}


}
    public static void bloquearDesb(boolean f) {
        mnConculta.setEnabled(f);
        mnPagos.setEnabled(f);
        mnRegistrar.setEnabled(f);
        mnServicio.setEnabled(f);
        mnConfigurar.setEnabled(f);
        if (f == false) {
            MiBloq.setEnabled(false);
            MiDesbl1.setEnabled(true);

        }else{
        MiBloq.setEnabled(true);
            MiDesbl1.setEnabled(false);
        
        }

    }

    private void actualizarEstadoCliente() {

        Fecha.actualizarEstadoClienteFp(Fecha.hoy());
        Fecha.actualizarEstadoClienteFc(Fecha.hoy());
        Fecha.actualizarEstadoClienteRa();

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        desktopPane = new javax.swing.JDesktopPane();
        lblFondo = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        MiBloq = new javax.swing.JMenuItem();
        MiDesbl1 = new javax.swing.JMenuItem();
        mnPagos = new javax.swing.JMenu();
        miPagoServicio = new javax.swing.JMenuItem();
        mnConculta = new javax.swing.JMenu();
        miEstadoClientes = new javax.swing.JMenuItem();
        miConsultaPersonalizada = new javax.swing.JMenuItem();
        miConsultaPagos = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        mnServicio = new javax.swing.JMenu();
        mitCortarServicio = new javax.swing.JMenuItem();
        miClientesXactivar = new javax.swing.JMenuItem();
        mitDesHab = new javax.swing.JMenuItem();
        mitCongelarServicio = new javax.swing.JMenuItem();
        mitDescongelarServicio = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        mnRegistrar = new javax.swing.JMenu();
        openMenuItem = new javax.swing.JMenuItem();
        saveMenuItem = new javax.swing.JMenuItem();
        saveAsMenuItem = new javax.swing.JMenuItem();
        exitMenuItem = new javax.swing.JMenuItem();
        mnConfigurar = new javax.swing.JMenu();
        miPersonal = new javax.swing.JMenuItem();
        miCambiarContraseña = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1150, 800));

        jScrollPane1.setEnabled(false);
        jScrollPane1.setOpaque(false);
        jScrollPane1.setPreferredSize(new java.awt.Dimension(800, 400));

        desktopPane.setBackground(new java.awt.Color(204, 204, 204));
        desktopPane.setForeground(new java.awt.Color(255, 255, 255));
        desktopPane.setMinimumSize(new java.awt.Dimension(1150, 600));
        desktopPane.setPreferredSize(new java.awt.Dimension(800, 600));

        lblFondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/tesla1400x900.png"))); // NOI18N
        desktopPane.add(lblFondo);
        lblFondo.setBounds(0, 10, 1100, 680);

        jScrollPane1.setViewportView(desktopPane);

        menuBar.setBorder(null);
        menuBar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        menuBar.setOpaque(false);

        jMenu2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/Encabezados/Sistema.png"))); // NOI18N
        jMenu2.setText("Sistema");

        jMenuItem4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/salirSistema.png"))); // NOI18N
        jMenuItem4.setText("Salir del Sistema");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem4);
        jMenu2.add(jSeparator1);

        MiBloq.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/bloquear.png"))); // NOI18N
        MiBloq.setText("Bloquear Sistema");
        MiBloq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MiBloqActionPerformed(evt);
            }
        });
        jMenu2.add(MiBloq);

        MiDesbl1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/desbloquear.png"))); // NOI18N
        MiDesbl1.setText("Desbloquear Sistema");
        MiDesbl1.setEnabled(false);
        MiDesbl1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MiDesbl1ActionPerformed(evt);
            }
        });
        jMenu2.add(MiDesbl1);

        menuBar.add(jMenu2);

        mnPagos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/pago30.png"))); // NOI18N
        mnPagos.setText("Pagos");

        miPagoServicio.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.ALT_MASK));
        miPagoServicio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/inter19.png"))); // NOI18N
        miPagoServicio.setText("Pago de Servicio");
        miPagoServicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miPagoServicioActionPerformed(evt);
            }
        });
        mnPagos.add(miPagoServicio);

        menuBar.add(mnPagos);

        mnConculta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/consulta.png"))); // NOI18N
        mnConculta.setMnemonic('h');
        mnConculta.setText("Consultas");

        miEstadoClientes.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.ALT_MASK));
        miEstadoClientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/estado19.png"))); // NOI18N
        miEstadoClientes.setMnemonic('c');
        miEstadoClientes.setText("Estado de Clientes");
        miEstadoClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miEstadoClientesActionPerformed(evt);
            }
        });
        mnConculta.add(miEstadoClientes);

        miConsultaPersonalizada.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/cper19.png"))); // NOI18N
        miConsultaPersonalizada.setMnemonic('a');
        miConsultaPersonalizada.setText("Consulta Personalizada de Clientes");
        miConsultaPersonalizada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miConsultaPersonalizadaActionPerformed(evt);
            }
        });
        mnConculta.add(miConsultaPersonalizada);

        miConsultaPagos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/consultaPagos.png"))); // NOI18N
        miConsultaPagos.setText("Consulta de Pagos/Anular Pagos");
        miConsultaPagos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miConsultaPagosActionPerformed(evt);
            }
        });
        mnConculta.add(miConsultaPagos);

        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/buscar16.png"))); // NOI18N
        jMenuItem2.setText("Consulta de Pagos Totales");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        mnConculta.add(jMenuItem2);

        menuBar.add(mnConculta);

        mnServicio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/servicio.png"))); // NOI18N
        mnServicio.setText("Servicio");

        mitCortarServicio.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.ALT_MASK));
        mitCortarServicio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/cortar.png"))); // NOI18N
        mitCortarServicio.setMnemonic('C');
        mitCortarServicio.setText("Cortar Servicio");
        mitCortarServicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mitCortarServicioActionPerformed(evt);
            }
        });
        mnServicio.add(mitCortarServicio);

        miClientesXactivar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.ALT_MASK));
        miClientesXactivar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/activar2.png"))); // NOI18N
        miClientesXactivar.setText("Clientes por Activar");
        miClientesXactivar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miClientesXactivarActionPerformed(evt);
            }
        });
        mnServicio.add(miClientesXactivar);

        mitDesHab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/hd19.png"))); // NOI18N
        mitDesHab.setMnemonic('y');
        mitDesHab.setText("Desabilitar/Habilitar Cliente");
        mitDesHab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mitDesHabActionPerformed(evt);
            }
        });
        mnServicio.add(mitDesHab);

        mitCongelarServicio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/H23.png"))); // NOI18N
        mitCongelarServicio.setText("Congelar Sevicio");
        mitCongelarServicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mitCongelarServicioActionPerformed(evt);
            }
        });
        mnServicio.add(mitCongelarServicio);

        mitDescongelarServicio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/Encabezados/sol23.png"))); // NOI18N
        mitDescongelarServicio.setText("Descongelar Servicio");
        mitDescongelarServicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mitDescongelarServicioActionPerformed(evt);
            }
        });
        mnServicio.add(mitDescongelarServicio);

        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/arrow_left16.png"))); // NOI18N
        jMenuItem1.setText("Regresar a Estado_ Corte Sin Ejecutar");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        mnServicio.add(jMenuItem1);

        menuBar.add(mnServicio);

        mnRegistrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/registrar.png"))); // NOI18N
        mnRegistrar.setMnemonic('f');
        mnRegistrar.setText("Registrar");

        openMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/server18.png"))); // NOI18N
        openMenuItem.setMnemonic('o');
        openMenuItem.setText("Servidor");
        openMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openMenuItemActionPerformed(evt);
            }
        });
        mnRegistrar.add(openMenuItem);

        saveMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/torre18.png"))); // NOI18N
        saveMenuItem.setMnemonic('s');
        saveMenuItem.setText("Torre");
        saveMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveMenuItemActionPerformed(evt);
            }
        });
        mnRegistrar.add(saveMenuItem);

        saveAsMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/antena19.png"))); // NOI18N
        saveAsMenuItem.setMnemonic('a');
        saveAsMenuItem.setText("Antena");
        saveAsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveAsMenuItemActionPerformed(evt);
            }
        });
        mnRegistrar.add(saveAsMenuItem);

        exitMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/cliente19.png"))); // NOI18N
        exitMenuItem.setMnemonic('x');
        exitMenuItem.setText("Cliente");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        mnRegistrar.add(exitMenuItem);

        menuBar.add(mnRegistrar);

        mnConfigurar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/configuracion.png"))); // NOI18N
        mnConfigurar.setText("Configuracion");

        miPersonal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/personal19.png"))); // NOI18N
        miPersonal.setText("Personal");
        miPersonal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miPersonalActionPerformed(evt);
            }
        });
        mnConfigurar.add(miPersonal);

        miCambiarContraseña.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sirene/Imagenes/password19.png"))); // NOI18N
        miCambiarContraseña.setText("Cambiar contraseña");
        miCambiarContraseña.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miCambiarContraseñaActionPerformed(evt);
            }
        });
        mnConfigurar.add(miCambiarContraseña);

        menuBar.add(mnConfigurar);

        jMenu1.setText("Reportes");

        jMenuItem3.setText("Reporte de Pagos");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuItem5.setText("Grafico de Ingresos Mensuales");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem5);

        jMenuItem6.setText("Grafico Comparatido Mensual Agrupados por años");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem6);

        menuBar.add(jMenu1);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1217, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 692, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        try {
            RegCliente vs = new RegCliente();
            vs.setUser(user);
            desktopPane.add(vs);

            vs.show();

        } catch (Exception e) {
            System.out.println("error :  " + e);
        }


    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void saveMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveMenuItemActionPerformed
        try {
            RegTorre vt = new RegTorre();
            vt.setUser(user);
            desktopPane.add(vt);
            vt.show();
        } catch (Exception e) {
            System.out.println("error :  " + e);
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_saveMenuItemActionPerformed

    private void saveAsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveAsMenuItemActionPerformed
        try {
            RegAntena vs = new RegAntena();
            vs.setUser(user);
            desktopPane.add(vs);

            vs.show();

        } catch (Exception e) {
            System.out.println("error :  " + e);
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_saveAsMenuItemActionPerformed

    private void miPagoServicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miPagoServicioActionPerformed
        try {
            Ventas vn = new Ventas();
            vn.setUser(user);
            desktopPane.add(vn);
            vn.show();

        } catch (Exception e) {
            System.out.println("error llamando a Ventas :  " + e);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_miPagoServicioActionPerformed

    private void mitCortarServicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mitCortarServicioActionPerformed
        try {
            
            CortarServicio cs = new CortarServicio();
            // vn.setUser(user);
            desktopPane.add(cs);
            cs.show();

        } catch (Exception e) {
            System.out.println("error llamando a Cortar servicio :  " + e);
        }
// TODO add your handling code here:
    }//GEN-LAST:event_mitCortarServicioActionPerformed

    private void mitDesHabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mitDesHabActionPerformed
        try {
            HabitarDesabilitarCliente hd = new HabitarDesabilitarCliente();
            // vn.setUser(user);
            desktopPane.add(hd);
            hd.show();

        } catch (Exception e) {
            System.out.println("error llamando a Habilitar/desabilitar cliente :  " + e);
        }

// TODO add your handling code here:
    }//GEN-LAST:event_mitDesHabActionPerformed

    private void miEstadoClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miEstadoClientesActionPerformed
        try {
            ConsultaEstadoC_SE ce = new ConsultaEstadoC_SE();
            // vn.setUser(user);
            desktopPane.add(ce);
            ce.show();

        } catch (Exception e) {
            System.out.println("error llamando a consulta de estados :  " + e);
        }

// TODO add your handling code here:
    }//GEN-LAST:event_miEstadoClientesActionPerformed

    private void miConsultaPersonalizadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miConsultaPersonalizadaActionPerformed
        try {
            ConsultaCliente cp = new ConsultaCliente();
            desktopPane.add(cp);
            cp.show();
        } catch (Exception e) {
            System.out.println("error llamando a consulta de estados :  " + e);
        }

// TODO add your handling code here:
    }//GEN-LAST:event_miConsultaPersonalizadaActionPerformed

    private void miPersonalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miPersonalActionPerformed
        int u = user.getIdTipoUser();
        if (u == 1) {
            try {
                RegPersonal p = new RegPersonal();
                desktopPane.add(p);
                p.show();
            } catch (Exception e) {
                System.out.println("error llamando a Ventana Personal :  " + e);
            }

        } else {

            Mensaje.panelSms("NO TIENE LOS PERMISOS ADMINISTRATIVOS");
        }

// TODO add your handling code here:
    }//GEN-LAST:event_miPersonalActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        System.exit(0);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void miCambiarContraseñaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miCambiarContraseñaActionPerformed
        EditarUsuario eu = new EditarUsuario(this, true);
        eu.txtUsuario.setText(user.getUser());
        eu.isUs = user.getIdUser();
        eu.setVisible(true);

// TODO add your handling code here:
    }//GEN-LAST:event_miCambiarContraseñaActionPerformed

    private void miConsultaPagosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miConsultaPagosActionPerformed
        try {
            ReportePagos rp = new ReportePagos();
            desktopPane.add(rp);
            rp.show();
        } catch (Exception e) {
            System.out.println("error llamando a Ventana Personal :  " + e);
        }

// TODO add your handling code here:
    }//GEN-LAST:event_miConsultaPagosActionPerformed

    private void MiBloqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MiBloqActionPerformed
        bloquearDesb(false);
        

// TODO add your handling code here:
    }//GEN-LAST:event_MiBloqActionPerformed

    private void MiDesbl1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MiDesbl1ActionPerformed
     
        Desbloqueo d = new Desbloqueo(this, true);
        
        d.setUser(user);
      
        d.txtUser.setText(user.getUser());
         
        d.setVisible(true);
        
        privilegios();
         
        // TODO add your handling code here:
    }//GEN-LAST:event_MiDesbl1ActionPerformed

    private void miClientesXactivarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miClientesXactivarActionPerformed
try {
            ClientesXactivar rp = new ClientesXactivar();
            desktopPane.add(rp);
            rp.show();
        } catch (Exception e) {
            System.out.println("error llamando a Clientes x activar :  " + e);
        }
        
// TODO add your handling code here:
    }//GEN-LAST:event_miClientesXactivarActionPerformed

    private void mitCongelarServicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mitCongelarServicioActionPerformed
try {
            CongelarServicioCliente rp = new CongelarServicioCliente();
            desktopPane.add(rp);
            rp.show();
        } catch (Exception e) {
            System.out.println("error llamando a CongelarServicioCliente :  " + e);
        }
        

// TODO add your handling code here:
    }//GEN-LAST:event_mitCongelarServicioActionPerformed

    private void mitDescongelarServicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mitDescongelarServicioActionPerformed
try {
            DescongelarServicioCliente rp = new DescongelarServicioCliente();
            desktopPane.add(rp);
            rp.show();
        } catch (Exception e) {
            System.out.println("error llamando a DescongelarServicioCliente :  " + e);
        }
        

// TODO add your handling code here:
    }//GEN-LAST:event_mitDescongelarServicioActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed

        try {
            CambiarA_CorteSE rp = new CambiarA_CorteSE();
            desktopPane.add(rp);
            rp.show();
        } catch (Exception e) {
            System.out.println("error llamando a CambiarA_CorteSE :  " + e);
        }

// TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed

        try {
            ReportePagosTotales rp = new ReportePagosTotales();
            desktopPane.add(rp);
            rp.show();
        } catch (Exception e) {
            System.out.println("error llamando a Reporte de pagos totales :  " + e);
        }
        
// TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
int u = user.getIdTipoUser();
        if (u == 1) {
            try {
                Reporte_PagosIR p = new Reporte_PagosIR();
                desktopPane.add(p);
                p.show();
            } catch (Exception e) {
                System.out.println("error llamando a Ventana  :  " + e);
            }

        } else {

            Mensaje.panelSms("NO TIENE LOS PERMISOS ADMINISTRATIVOS");
        }
        


// TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed

    int u = user.getIdTipoUser();
        if (u == 1) {
            try {
                GraficaPagosMensual p = new GraficaPagosMensual();
                desktopPane.add(p);
                p.show();
            } catch (Exception e) {
                System.out.println("error llamando a Ventana  :  " + e);
            }

        } else {

            Mensaje.panelSms("NO TIENE LOS PERMISOS ADMINISTRATIVOS");
        }
            


// TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
 int u = user.getIdTipoUser();
        if (u == 1) {
            try {
                GraficaPagosMensualAgrpadaAnual p = new GraficaPagosMensualAgrpadaAnual();
                desktopPane.add(p);
                p.show();
            } catch (Exception e) {
                System.out.println("error llamando a Ventana  :  " + e);
            }

        } else {

            Mensaje.panelSms("NO TIENE LOS PERMISOS ADMINISTRATIVOS");
        }
            
        
// TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void openMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openMenuItemActionPerformed
       String msg="Hola";
        try {
            RegistroServidores rs=new RegistroServidores();
            
           // RegServidor vs = new RegServidor();
          rs.setUser(user);
            desktopPane.add(rs);
            rs.show();
        } catch (Exception e) {
             System.out.println("error :  " + e);
        }
        

        // TODO add your handling code here:
    }//GEN-LAST:event_openMenuItemActionPerformed

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
                    // UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MdiPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MdiPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MdiPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MdiPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {

                new MdiPrincipal().setVisible(true);

            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JMenuItem MiBloq;
    public static javax.swing.JMenuItem MiDesbl1;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    public static javax.swing.JDesktopPane desktopPane;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JLabel lblFondo;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem miCambiarContraseña;
    private javax.swing.JMenuItem miClientesXactivar;
    private javax.swing.JMenuItem miConsultaPagos;
    private javax.swing.JMenuItem miConsultaPersonalizada;
    private javax.swing.JMenuItem miEstadoClientes;
    private javax.swing.JMenuItem miPagoServicio;
    private javax.swing.JMenuItem miPersonal;
    private javax.swing.JMenuItem mitCongelarServicio;
    private javax.swing.JMenuItem mitCortarServicio;
    private javax.swing.JMenuItem mitDesHab;
    private javax.swing.JMenuItem mitDescongelarServicio;
    public static javax.swing.JMenu mnConculta;
    public static javax.swing.JMenu mnConfigurar;
    public static javax.swing.JMenu mnPagos;
    public static javax.swing.JMenu mnRegistrar;
    public static javax.swing.JMenu mnServicio;
    private javax.swing.JMenuItem openMenuItem;
    private javax.swing.JMenuItem saveAsMenuItem;
    private javax.swing.JMenuItem saveMenuItem;
    // End of variables declaration//GEN-END:variables
}
