/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirene.Dao.Impl;

import com.sirene.Dao.*;

import static com.sirene.Utilitarios.Constantes.*;

/**
 *
 * @author Jinmi
 */
public class DaoFactory {

    public AntenaDao getAntenaDao(int tipo) {
        AntenaDao dao;
        switch (tipo) {
            case ANTENA:
                dao = new AntenaImp();
                break;
            default:
                dao = null;
        }
        return dao;
    }

    public static AntenaDao getAntenaDaoStatic(int tipo) {
        AntenaDao dao;
        switch (tipo) {
            case ANTENA:
                dao = new AntenaImp();
                break;
            default:
                dao = null;
        }
        return dao;
    }

    public ClienteDao getClienteDao(int tipo) {
        ClienteDao dao;
        switch (tipo) {
            case CLIENTE:
                dao = new ClienteImp();
                break;
            default:
                dao = null;
        }
        return dao;
    }

    public static ClienteDao getClienteDaoStatic(int tipo) {
        ClienteDao dao;
        switch (tipo) {
            case CLIENTE:
                dao = new ClienteImp();
                break;
            default:
                dao = null;
        }
        return dao;
    }

    public PersonalDao getPersonalDao(int tipo) {
        PersonalDao dao;
        switch (tipo) {
            case PERSONAL:
                dao = new PersonalImp();
                break;
            default:
                dao = null;
        }
        return dao;
    }

    public static PersonalDao getPersonalDaoStatic(int tipo) {
        PersonalDao dao;
        switch (tipo) {
            case PERSONAL:
                dao = new PersonalImp();
                break;
            default:
                dao = null;
        }
        return dao;
    }

    public PagoDao getPagoDao(int tipo) {
        PagoDao dao;
        switch (tipo) {
            case PAGO:
                dao = new PagoImp();
                break;
            default:
                dao = null;
        }
        return dao;
    }

    public static PagoDao getPagoDaoStatic(int tipo) {
        PagoDao dao;
        switch (tipo) {
            case PAGO:
                dao = new PagoImp();
                break;
            default:
                dao = null;
        }
        return dao;
    }

    public ServidorDao getServidorDao(int tipo) {
        ServidorDao dao;
        switch (tipo) {
            case SERVIDOR:
                dao = new ServidorImp();
                break;
            default:
                dao = null;
        }
        return dao;
    }

    public static ServidorDao getServidorDaoStatic(int tipo) {
        ServidorDao dao;
        switch (tipo) {
            case SERVIDOR:
                dao = new ServidorImp();
                break;
            default:
                dao = null;
        }
        return dao;
    }

    public TelefonoDao getTelefonoDao(int tipo) {
        TelefonoDao dao;
        switch (tipo) {
            case TELEFONO:
                dao = new TelefonoImp();
                break;
            default:
                dao = null;
        }
        return dao;
    }
     public static TelefonoDao getTelefonoDaoStatic(int tipo) {
        TelefonoDao dao;
        switch (tipo) {
            case TELEFONO:
                dao = new TelefonoImp();
                break;
            default:
                dao = null;
        }
        return dao;
    }


    public TorreDao getTorreDao(int tipo) {
        TorreDao dao;
        switch (tipo) {
            case TORRE:
                dao = new TorreImp();
                break;
            default:
                dao = null;
        }
        return dao;
    }

    public static TorreDao getTorreDaoStatic(int tipo) {
        TorreDao dao;
        switch (tipo) {
            case TORRE:
                dao = new TorreImp();
                break;
            default:
                dao = null;
        }
        return dao;
    }

    public UsuarioDao getUsuarioDao(int tipo) {
        UsuarioDao dao;
        switch (tipo) {
            case USUARIO:
                dao = new UsuarioImp();
                break;
            default:
                dao = null;
        }
        return dao;
    }

    public static UsuarioDao getUsuarioDaoStatic(int tipo) {
        UsuarioDao dao;
        switch (tipo) {
            case USUARIO:
                dao = new UsuarioImp();
                break;
            default:
                dao = null;
        }
        return dao;
    }
    
    public static DaoFactory getInstance() {
        return DaoFactoryHolder.INSTANCE;
    }

    private static class DaoFactoryHolder {

        private static final DaoFactory INSTANCE = new DaoFactory();
    }

}
