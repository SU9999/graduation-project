package com.su.util;

import com.su.enums.IStatusEnum;

/**
 * 处理枚举类的工具类
 */
public class EnumUtil {

    /**
     * 根据给定的Code和枚举类Class，返回该code值对象的枚举对象
     * @param code
     * @param statusEnumClass
     * @param <T>
     * @return
     */
    public static <T extends IStatusEnum> T getStatusEnum(Integer code, Class<T> statusEnumClass){
        // 遍历该枚举类
        for (T statusEnum : statusEnumClass.getEnumConstants()){
            if (statusEnum.getCode().equals(code)){
                return statusEnum;
            }
        }
        return null;
    }
}
