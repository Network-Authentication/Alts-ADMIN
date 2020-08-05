package net.dev.alts.encrypt;

import java.nio.charset.*;
import java.util.*;

public class Base64Utils {
    public static String DeCode(String codes) {
        return new String(Base64.getDecoder().decode(codes), StandardCharsets.UTF_8);
    }
    public static String EnCode(String codes){
        return Base64.getEncoder().encodeToString(codes.getBytes(StandardCharsets.UTF_8));
    }
}
