package net.dev.alts.utils;

import java.util.*;

public class Utils {
    public int getRandomInt(int Min, int Max) {
        Random random = new Random();
        int i = random.nextInt(Max) % (Max - Min + 1) + Min;
        return i;
    }

    public String getStringRandom(int length) {
        StringBuilder val = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            if ("char".equalsIgnoreCase(charOrNum)) {
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val.append((char) (random.nextInt(26) + temp));
            } else {
                val.append(random.nextInt(10));
            }
        }
        return val.toString();
    }
}
