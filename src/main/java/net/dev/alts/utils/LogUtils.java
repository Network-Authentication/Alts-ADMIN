package net.dev.alts.utils;

import java.io.*;
import java.nio.charset.*;
import java.util.*;

public class LogUtils {
    public static FileOutputStream log;
    public static void writeLog(String s)
    {
        System.out.println(s);
        try {
            log.write((new Date().toString()+" : "+ s+"\n").getBytes(StandardCharsets.UTF_8));
        }catch(Throwable e){if(e instanceof RuntimeException)  throw (RuntimeException)e; throw new RuntimeException(e);}
    }
    public static void log(){
        try {
            Date d = new Date();
            File l = new File(System.getProperty("user.dir")+File.separator + "ADMIN-logs" + File.separator + (d.getYear()+1900) +"-"+ (d.getMonth()+1) + "-" + d.getDate() + File.separator + ((d.getYear()+1900)+"-"+(d.getMonth()+1)+"-"+d.getDate()+"-"+d.getHours()+"-"+d.getMinutes()+"-"+d.getSeconds()+"-"+System.nanoTime()) + ".log");
            l.getParentFile().mkdirs();
            l.createNewFile();
            log=new FileOutputStream(l);
        }catch(Throwable e){if(e instanceof RuntimeException)  throw (RuntimeException)e; throw new RuntimeException(e);}
    }
}
