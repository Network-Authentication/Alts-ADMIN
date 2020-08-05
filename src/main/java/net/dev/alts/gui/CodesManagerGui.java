package net.dev.alts.gui;

import com.alibaba.fastjson.*;
import net.dev.alts.*;
import net.dev.alts.encrypt.*;
import net.dev.alts.gui.actions.*;
import net.dev.alts.user.*;
import net.dev.alts.utils.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CodesManagerGui extends JFrame {
    public static boolean geting = false;
    public void CodesManagerGui(){
        this.setTitle("Azure 163-Alts 管理系统 - 激活码管理");
        this.setSize(480, 680);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setFont(new Font(null,Font.PLAIN,14));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        FlowLayout fl = new FlowLayout(FlowLayout.CENTER,10,10);
        Font jb = new Font(null,Font.PLAIN,14);
        this.setLayout(fl);
        Dimension dim1 = new Dimension(250,30);
        Dimension dim2 = new Dimension(100,30);
        JLabel getamount = new JLabel("获取的数量：");
        getamount.setFont(jb);
        this.add(getamount);
        JTextField text_name = new JTextField();
        text_name.setPreferredSize(dim1);
        this.add(text_name);
        JButton get = new JButton();
        get.setText("获取");
        get.setFont(jb);
        get.setSize(dim2);
        this.add(get);
        JPanel jp=new JPanel();
        JTextArea jta=new JTextArea(10,40);
        jta.setLineWrap(true);
        jta.setEditable(false);
        JScrollPane jsp=new JScrollPane(jta);
        jp.add(jsp);
        this.add(jp);
        JLabel searchCode = new JLabel("查询激活码：");
        searchCode.setFont(jb);
        this.add(searchCode);
        JTextField text_code = new JTextField();
        text_code.setPreferredSize(dim1);
        this.add(text_code);
        JButton search = new JButton();
        search.setText("查询");
        search.setFont(jb);
        search.setSize(dim2);
        this.add(search);
        JLabel user = new JLabel("用户名：");
        user.setFont(jb);
        this.add(user);
        JLabel user_s = new JLabel("未查询");
        user_s.setForeground(Color.GRAY);
        user_s.setFont(jb);
        this.add(user_s);
        JLabel email = new JLabel("邮箱：");
        email.setFont(jb);
        this.add(email);
        JLabel email_s = new JLabel("未查询");
        email_s.setForeground(Color.GRAY);
        email_s.setFont(jb);
        this.add(email_s);
        JLabel hwid = new JLabel("Hwid：");
        hwid.setFont(jb);
        this.add(hwid);
        JLabel hwid_s = new JLabel("未查询");
        hwid_s.setForeground(Color.GRAY);
        hwid_s.setFont(jb);
        this.add(hwid_s);
        JLabel time = new JLabel("使用时间：");
        time.setFont(jb);
        this.add(time);
        JLabel time_s = new JLabel("未查询");
        time_s.setForeground(Color.GRAY);
        time_s.setFont(jb);
        this.add(time_s);
        JLabel a2a51 = new JLabel("未使用的激活码(每10秒刷新一次) 下次刷新时间：");
        a2a51.setFont(jb);
        this.add(a2a51);
        JPanel jpa=new JPanel();
        JTextArea jtb=new JTextArea(10,40);
        jtb.setLineWrap(true);
        jtb.setEditable(false);
        JScrollPane jsj=new JScrollPane(jtb);
        jpa.add(jsj);
        this.add(jpa);
        JLabel delCode = new JLabel("删除激活码：");
        delCode.setFont(jb);
        this.add(delCode);
        JTextField text_delcode = new JTextField();
        text_delcode.setPreferredSize(dim1);
        this.add(text_delcode);
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
        del.addActionListener(new deleteCodesActions(this,text_delcode));
        back.addActionListener(new GoToMainActions(this));
        search.addActionListener(new getSearchCodesInfoActions(this,user_s,email_s,hwid_s,time_s,text_code));
        get.addActionListener(new getCodesActions(this,text_name,jta));
        new refreshCodes(this,jtb,a2a51).run();
    }
    private static class deleteCodesActions implements ActionListener {
        private JFrame gui;
        private JTextField code;
        public deleteCodesActions(JFrame gui,JTextField code){
            this.gui = gui;
            this.code = code;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            if(code.getText().isEmpty()){
                JOptionPaneManager.sendERROR("删除失败","要求删除的激活码不能为空！");
                return;
            }
            if(DatabaseUtils.removeCodes(code.getText()))
                JOptionPaneManager.sendDEFAULT("删除成功！");
            else
                JOptionPaneManager.sendERROR("删除失败","删除这个激活码时出现错误！");
        }
    }
    private class refreshCodes{
        private JFrame gui;
        private JTextArea codes;
        private JLabel label;
        refreshCodes(JFrame gui,JTextArea codes,JLabel label){
            this.gui = gui;
            this.codes = codes;
            this.label = label;
        }
        public void run() {
            new Thread(()->{
                while(true){
                    new Thread(()->{
                        codes.setText("");
                        for(String code:DatabaseUtils.getCodesList())
                            if(codes.getText().isEmpty())
                                codes.setText(code);
                            else
                                codes.setText(codes.getText()+"\n"+code);
                    }).start();
                    int time = 10;
                    try {
                        while(time!=0){
                            label.setText("未使用的激活码(每10秒刷新一次) 下次刷新时间："+time+"秒");
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
    private static class getSearchCodesInfoActions implements ActionListener {
        private JFrame gui;
        private JLabel user,email,time,hwid;
        private JTextField code;
        public getSearchCodesInfoActions(JFrame gui,JLabel user,JLabel email,JLabel hwid,JLabel time,JTextField code){
            this.gui = gui;
            this.user = user;
            this.email = email;
            this.time = time;
            this.hwid = hwid;
            this.code = code;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            this.user.setText("null");
            this.email.setText("null");
            this.hwid.setText("null");
            this.time.setText("null");
            UserInfo user = JSON.parseObject(DatabaseUtils.getUserInfoCodeJson(code.getText()), UserInfo.class);
            try{
                this.user.setText(user.getUsername());
                this.email.setText(user.getEmail());
                this.hwid.setText(user.getHwid());
                this.time.setText(DateUtils.getDate(Long.parseLong(DatabaseUtils.getCodeTime(code.getText()))));
            }catch (Throwable ea){}
        }
    }
    private static class getCodesActions implements ActionListener {
        private JFrame gui;
        private JTextField amounts;
        private JTextArea codes;
        public getCodesActions(JFrame gui,JTextField amounts,JTextArea codes){
            this.gui = gui;
            this.amounts = amounts;
            this.codes = codes;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            codes.setText("");
            if(!geting){
                new Thread(()->{
                    try{
                        int amounts = Integer.parseInt(this.amounts.getText());
                        while(amounts!=0){
                            geting = true;
                            Utils u = new Utils();
                            String code = MD5Utils.get16MD5Codes(u.getStringRandom(u.getRandomInt(10,15)));
                            if(!DatabaseUtils.hasCode(code)){
                                if(codes.getText().isEmpty())
                                    codes.setText(code);
                                else
                                    codes.setText(codes.getText()+"\n"+code);
                                amounts = amounts-1;
                                DatabaseUtils.addCode(code);
                            }
                        }
                        geting = false;
                        JOptionPaneManager.sendDEFAULT("获取成功");
                    }catch (Throwable e1){
                        JOptionPaneManager.sendERROR("获取错误","请输入正确的获取数量！");
                    }
                }).start();
            }else{
                JOptionPaneManager.sendERROR("获取错误","已经有一个线程正在获取了！");
            }
        }
    }
}
