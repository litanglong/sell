package cn.ltl.sell.util;

import cn.ltl.sell.enums.CodeEnum;

public class EnumUtil {

    public static <T extends CodeEnum> T getMessage(Integer code, Class<T> enumClass) {
        for (T e : enumClass.getEnumConstants()) {
            if(e.getCode().equals(code)) {
                return e;
            }
        }
        return null;
    }
}
