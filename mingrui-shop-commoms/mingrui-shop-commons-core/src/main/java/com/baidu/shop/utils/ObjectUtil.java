package com.baidu.shop.utils;

/**
 * @ClassName ObjectUtil
 * @Description: TODO
 * @Author wangyue
 * @Date 2021/1/20
 * @Version V1.0
 **/
public class ObjectUtil {
    public static Boolean isNull(Object obj){
        return null == obj;
    }
    public static Boolean isNotNull(Object obj){
        return null != obj;
    }
}
