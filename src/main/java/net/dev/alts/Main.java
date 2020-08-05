package net.dev.alts;

import net.dev.alts.database.*;
import net.dev.alts.encrypt.*;
import net.dev.alts.gui.*;
import net.dev.alts.hwid.*;
import net.dev.alts.user.*;
import net.dev.alts.utils.*;

import java.io.*;
import java.net.*;
import java.nio.channels.*;
import java.security.*;
import java.util.*;

public final class Main{
    public static String hwid = MD5Utils.get16MD5Codes(Base64Utils.EnCode(HWID.bytesToHex(HWID.generateHWID())));
    public static double version = 1.00;
    public static void main(String[] args) throws Exception {
        LogUtils.log();
        LogUtils.writeLog("程序启动...");
        makeSingle("single.run");
        LogUtils.writeLog("本机HWID："+hwid);
        LogUtils.writeLog("程序正在初始化中...");
        LogUtils.writeLog("正在连接数据库中...");
        DatabaseUtils.loadDatabase();
        LogUtils.writeLog("连接数据库成功！");
        try {
            if(HttpsUtils.get("https://gitee.com/azurepvp/wyalts/raw/master/Staff").contains(hwid)){
                new MainGui().MainGui();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            LogUtils.writeLog(e.getMessage());
            JOptionPaneManager.sendERROR("STAFF名单检测错误","请保持网络通顺，然后再次启动");
            System.exit(0);
        }
    }
    public static void makeSingle(String singleId) {
        RandomAccessFile raf = null;
        FileChannel channel = null;
        FileLock lock = null;
        try{
            File sf = new File(System.getProperty("java.io.tmpdir") + singleId + ".single");
            sf.deleteOnExit();
            sf.createNewFile();
            raf = new RandomAccessFile(sf, "rw");
            channel = raf.getChannel();
            lock = channel.tryLock();
            if (lock == null) {
                JOptionPaneManager.sendERROR("检测到程序已运行...","尝试关闭已运行的程序然后再次尝试...");
                LogUtils.writeLog("检测到程序已运行...");
                System.exit(0);
            }
        }catch (Throwable e){
            e.printStackTrace();
            LogUtils.writeLog(e.getMessage());
            JOptionPaneManager.sendERROR("检测到程序已运行...","尝试关闭已运行的程序然后再次尝试...");
            LogUtils.writeLog("检测到程序已运行...");
            System.exit(0);
        }
    }
}
