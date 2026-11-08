package CITIM21_09.view;

import CITIM21_09.view.vistaComandos.Command;

import javax.swing.*;
import java.util.List;

public class MenuVista {

    public void printMenu() {
        System.out.println(
                "██████╗  █████╗ ████████╗████████╗██╗     ███████╗███████╗██╗  ██╗██╗██████╗\n"+
                        "██╔══██╗██╔══██╗╚══██╔══╝╚══██╔══╝██║     ██╔════╝██╔════╝██║  ██║██║██╔══██╗\n"+
                        " ██████╔╝███████║   ██║      ██║   ██║     █████╗  ███████╗███████║██║██████╔╝\n"+
                        " ██╔══██╗██╔══██║   ██║      ██║   ██║     ██╔══╝  ╚════██║██╔══██║██║██╔═══╝\n"+
                        " ██████╔╝██║  ██║   ██║      ██║   ███████╗███████╗███████║██║  ██║██║██║\n"+
                        " ╚═════╝ ╚═╝  ╚═╝   ╚═╝      ╚═╝   ╚══════╝╚══════╝╚══════╝╚═╝  ╚═╝╚═╝╚═╝\n"
                        +"                  BATTLESHIP\n"+"======================");
    }

    public void printOpcion(List<Command> commandLis) {
        int i = 1;
        for (Command command : commandLis) {
            System.out.println(i + "-" + command.getName());
            i++;
        }
        System.out.println("======================");
    }

}
