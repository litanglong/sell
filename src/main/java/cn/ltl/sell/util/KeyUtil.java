package cn.ltl.sell.util;

import java.util.Random;

public class KeyUtil {

    public static String getUniqueKey() {
        Random random = new Random();
        int key = random.nextInt(900000) + 100000;
        return System.currentTimeMillis() + String.valueOf(key);
    }
}
