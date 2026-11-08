package CITIM21_09.view.vistaComandos.ComandosPublicos;

import CITIM21_09.controller.SesionControlador;
import CITIM21_09.view.vistaComandos.Command;
import servidor.ExternalLDAP;

public class LDAP implements Command {

    private final String name;


    public LDAP() {
        name = "LDAP";
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void execute() {
        SesionControlador.getInstance().login(ExternalLDAP.LoginLDAP());
    }
}
