package net.dev.alts;

import javax.swing.*;

public class JOptionPaneManager {
    public static void sendDEFAULT(String mess){
        JOptionPane.showMessageDialog(null, mess);
    }
    public static void sendWORING(String title,String mess){
        JOptionPane.showMessageDialog(null, mess, title,JOptionPane.WARNING_MESSAGE);
    }
    public static void sendERROR(String title,String mess){
        JOptionPane.showMessageDialog(null, mess, title,JOptionPane.ERROR_MESSAGE);
    }
    public static void sendPLAIN(String title,String mess){
        JOptionPane.showMessageDialog(null, mess, title,JOptionPane.PLAIN_MESSAGE);
    }
    public static boolean sendYESORNO(String title,String mess){
        int i = JOptionPane.showConfirmDialog(null, mess, title,JOptionPane.YES_NO_OPTION);
        return i == 0;
    }
    public static String sendCHOOSE(String title,String mess,Object[] choose){
        Object[] obj2 ={ choose };
        String s = (String) JOptionPane.showInputDialog(null,mess+"\n", title, JOptionPane.PLAIN_MESSAGE, new ImageIcon("icon.png"), obj2,"");
        return s;
    }
}
