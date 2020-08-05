package net.dev.alts.utils;

import java.text.*;
import java.util.*;

public class DateUtils {
    public static String getDate(long time){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date(time));
    }
}
