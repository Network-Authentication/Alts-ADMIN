package net.dev.alts.gui;

import net.dev.alts.gui.actions.*;

import javax.swing.*;
import java.awt.*;

public class MainGui extends JFrame {
    public void MainGui(){
        this.setTitle("Azure 163-Alts 管理系统 - 主界面");
        this.setSize(480, 80);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setFont(new Font(null,Font.PLAIN,14));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        FlowLayout fl = new FlowLayout(FlowLayout.CENTER,10,10);
        Font jb = new Font(null,Font.PLAIN,14);
        this.setLayout(fl);
        Dimension dim2 = new Dimension(100,30);
        JButton button1 = new JButton();
        button1.setText("激活码管理");
        button1.setFont(jb);
        button1.setSize(dim2);
        this.add(button1);
        JButton button2 = new JButton();
        button2.setText("用户管理");
        button2.setFont(jb);
        button2.setSize(dim2);
        this.add(button2);
        JButton button3 = new JButton();
        button3.setText("账号管理");
        button3.setFont(jb);
        button3.setSize(dim2);
        this.add(button3);
        this.setVisible(true);
        button1.addActionListener(new GoToGetCodesActions(this));
        button3.addActionListener(new GoToAccountsActions(this));
        button2.addActionListener(new GoToUserActions(this));
    }
}
