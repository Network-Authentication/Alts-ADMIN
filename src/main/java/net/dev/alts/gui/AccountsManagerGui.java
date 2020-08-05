package net.dev.alts.gui;

import net.dev.alts.*;
import net.dev.alts.gui.actions.*;
import net.dev.alts.utils.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class AccountsManagerGui extends JFrame{
    public void AccountsManagerGui(){
        this.setTitle("Azure 163-Alts 管理系统 - 账号管理");
        this.setSize(480, 300);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setFont(new Font(null,Font.PLAIN,14));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        FlowLayout fl = new FlowLayout(FlowLayout.CENTER,10,10);
        Font jb = new Font(null,Font.PLAIN,14);
        this.setLayout(fl);
        Dimension dim1 = new Dimension(250,30);
        Dimension dim2 = new Dimension(100,30);
        JLabel getamount = new JLabel("在这里一行一行的写入你要添加的账号");
        getamount.setFont(jb);
        this.add(getamount);
        JButton addA = new JButton();
        addA.setText("添加账号");
        addA.setFont(jb);
        addA.setSize(dim2);
        this.add(addA);
        JPanel addAccounts=new JPanel();
        JTextArea jta=new JTextArea(6,40);
        jta.setLineWrap(true);
        JScrollPane jsp=new JScrollPane(jta);
        addAccounts.add(jsp);
        this.add(addAccounts);
        JLabel delAccounts = new JLabel("删除账号：");
        delAccounts.setFont(jb);
        this.add(delAccounts);
        JTextField text_delaccounts = new JTextField();
        text_delaccounts.setPreferredSize(dim1);
        this.add(text_delaccounts);
        JButton del = new JButton();
        del.setText("删除");
        del.setFont(jb);
        del.setSize(dim2);
        this.add(del);
        JButton back = new JButton();
        back.setText("点击 即可 立即返回 163 Alts 管理系统 --- 主界面");
        back.setFont(jb);
        back.setSize(dim2);
        this.add(back);
        this.setVisible(true);
        back.addActionListener(new GoToMainActions(this));
        addA.addActionListener(new AddAccountsActions(this,jta));
        del.addActionListener(new deleteAccountsActions(this,text_delaccounts));
    }
    private static class deleteAccountsActions implements ActionListener {
        private JFrame gui;
        private JTextField code;
        public deleteAccountsActions(JFrame gui,JTextField code){
            this.gui = gui;
            this.code = code;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            if(code.getText().isEmpty()){
                JOptionPaneManager.sendERROR("删除失败","要求删除的账号不能为空！");
                return;
            }
            if(DatabaseUtils.removeAccounts(code.getText()))
                JOptionPaneManager.sendDEFAULT("删除成功！");
            else
                JOptionPaneManager.sendERROR("删除失败","删除这个激活码时出现错误！");
        }
    }
    private static class AddAccountsActions implements ActionListener {
        private JFrame gui;
        private JTextArea accounts;
        public AddAccountsActions(JFrame gui,JTextArea accounts){
            this.gui = gui;
            this.accounts = accounts;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            if(!accounts.getText().isEmpty()){
                try{
                    new Thread(()->{
                        int load = 0;
                        try{
                            BufferedReader br = new BufferedReader(new StringReader(accounts.getText()));
                            String line = "";
                            while((line = br.readLine())!=null)
                            {
                                System.out.println("正在添加："+line);
                                DatabaseUtils.addAccounts(line);
                                load = load +1;
                                gui.setTitle("Azure 163-Alts 管理系统 - 账号管理 添加成功："+load);
                            }
                            JOptionPaneManager.sendDEFAULT("添加成功！");
                            gui.setTitle("Azure 163-Alts 管理系统 - 账号管理");
                        }catch (Throwable ea){
                            LogUtils.writeLog(ea.getMessage());
                        }
                    }).start();
                }catch (Throwable ea){
                    LogUtils.writeLog(ea.getMessage());
                }
            }else{
                JOptionPaneManager.sendERROR("添加失败","账号信息不能为空");
            }
        }
    }
}
