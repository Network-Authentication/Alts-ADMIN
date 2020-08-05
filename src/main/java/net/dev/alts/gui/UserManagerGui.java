package net.dev.alts.gui;

import net.dev.alts.*;
import net.dev.alts.gui.actions.*;
import net.dev.alts.utils.*;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class UserManagerGui extends JFrame {
    public void UserManagerGui(){
        this.setTitle("Azure 163-Alts 管理系统 - 账号管理");
        this.setSize(480, 630);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setFont(new Font(null,Font.PLAIN,14));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        FlowLayout fl = new FlowLayout(FlowLayout.CENTER,10,10);
        Font jb = new Font(null,Font.PLAIN,14);
        this.setLayout(fl);
        Dimension dim1 = new Dimension(250,30);
        Dimension dim2 = new Dimension(100,30);
        JLabel refresh = new JLabel();
        refresh.setFont(jb);
        this.add(refresh);
        Vector row = new Vector();
        Vector data = new Vector();
        Vector names = new Vector();
        names.add("用户名");
        names.add("邮箱");
        names.add("HWID");
        names.add("是否锁定");
        for(String username:DatabaseUtils.getUsersList()){
            row.add(username);
            row.add(DatabaseUtils.getUserEmail(username));
            row.add(DatabaseUtils.getUserHwid(username));
            row.add(DatabaseUtils.hasBeenLocked(username));
            data.add(row.clone());
            row.clear();
        }
        DefaultTableModel tableModel = new DefaultTableModel(data,names){
            @Override
            public boolean isCellEditable(int row,int column){
                return false;
            }
        };
        JTable table = new JTable(tableModel);
        RowSorter<TableModel> rowSorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(rowSorter);
        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane);
        JLabel delUser = new JLabel("删除用户：");
        delUser.setFont(jb);
        this.add(delUser);
        JTextField text_delUser = new JTextField();
        text_delUser.setPreferredSize(dim1);
        this.add(text_delUser);
        JButton del = new JButton();
        del.setText("删除");
        del.setFont(jb);
        del.setSize(dim2);
        this.add(del);
        JLabel lockUser = new JLabel("锁定用户：");
        lockUser.setFont(jb);
        this.add(lockUser);
        JTextField text_lockUser = new JTextField();
        text_lockUser.setPreferredSize(dim1);
        this.add(text_lockUser);
        JButton lock = new JButton();
        lock.setText("锁定");
        lock.setFont(jb);
        lock.setSize(dim2);
        this.add(lock);
        JButton back = new JButton();
        back.setText("点击 即可 立即返回 163 Alts 管理系统 --- 主界面");
        back.setFont(jb);
        back.setSize(dim2);
        this.add(back);
        this.setVisible(true);
        back.addActionListener(new GoToMainActions(this));
        del.addActionListener(new deleteUsersActions(this,text_delUser));
        lock.addActionListener(new lockUsersActions(this,text_lockUser));
        new refreshUser(this,row,data,refresh,tableModel,scrollPane).run();
    }
    private static class lockUsersActions implements ActionListener {
        private JFrame gui;
        private JTextField text_lockUser;
        public lockUsersActions(JFrame gui,JTextField text_lockUser){
            this.gui = gui;
            this.text_lockUser = text_lockUser;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            if(text_lockUser.getText().isEmpty()){
                JOptionPaneManager.sendERROR("锁定失败","指定锁定的用户不能为空！");
                return;
            }
            if(DatabaseUtils.hasUser(text_lockUser.getText())){
                boolean lock = DatabaseUtils.hasBeenLocked(text_lockUser.getText());
                if(lock){
                    //已经被锁定
                    DatabaseUtils.changeUserLocked(text_lockUser.getText(), false);
                    JOptionPaneManager.sendDEFAULT("解锁用户成功！");
                }else{
                    //没有被锁定
                    DatabaseUtils.changeUserLocked(text_lockUser.getText(), true);
                    JOptionPaneManager.sendDEFAULT("锁定用户成功！");
                }
            }else{
                JOptionPaneManager.sendERROR("锁定失败","指定锁定的用户不存在！");
                return;
            }
        }
    }
    private static class deleteUsersActions implements ActionListener {
        private JFrame gui;
        private JTextField text_delUser;
        public deleteUsersActions(JFrame gui,JTextField text_delUser){
            this.gui = gui;
            this.text_delUser = text_delUser;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            if(text_delUser.getText().isEmpty()){
                JOptionPaneManager.sendERROR("删除失败","指定删除的用户不能为空！");
                return;
            }
            if(DatabaseUtils.hasUser(text_delUser.getText())){
                if(DatabaseUtils.removeUsers(text_delUser.getText())){
                    JOptionPaneManager.sendDEFAULT("删除成功！");
                }else{
                    JOptionPaneManager.sendERROR("删除失败","数据库操作错误，请联系开发人员。");
                }
            }else{
                JOptionPaneManager.sendERROR("删除失败","指定删除的用户不存在！");
                return;
            }
        }
    }
    private class refreshUser {
        private JFrame gui;
        private Vector row,data;
        private JLabel refresh;
        private DefaultTableModel tableModel;
        private JScrollPane scrollPane;
        refreshUser(JFrame gui, Vector row, Vector data, JLabel refresh,DefaultTableModel tableModel,JScrollPane scrollPane){
            this.gui = gui;
            this.row = row;
            this.data = data;
            this.refresh = refresh;
            this.tableModel = tableModel;
            this.scrollPane = scrollPane;
        }
        public void run() {
            new Thread(()->{
                while(true){
                    new Thread(()->{
                        try{
                            tableModel.getDataVector().clear();
                            for(String username:DatabaseUtils.getUsersList()){
                                row.add(username);
                                row.add(DatabaseUtils.getUserEmail(username));
                                row.add(DatabaseUtils.getUserHwid(username));
                                row.add(DatabaseUtils.hasBeenLocked(username));
                                data.add(row.clone());
                                row.clear();
                            }
                            scrollPane.updateUI();
                        }catch (Throwable e){ }
                    }).start();
                    int time = 15;
                    try {
                        while(time!=0){
                            refresh.setText("已注册的用户(每"+time+"秒刷新一次) 下次刷新时间："+time+"秒");
                            Thread.sleep(1000);
                            time--;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
