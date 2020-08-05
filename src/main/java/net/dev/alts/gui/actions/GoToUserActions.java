package net.dev.alts.gui.actions;

import net.dev.alts.gui.*;

import javax.swing.*;
import java.awt.event.*;

public class GoToUserActions implements ActionListener {
    private final JFrame gui;
    public GoToUserActions(JFrame gui){
        this.gui = gui;
    }
    public void actionPerformed(ActionEvent e) {
        gui.dispose();
        new UserManagerGui().UserManagerGui();
    }
}
