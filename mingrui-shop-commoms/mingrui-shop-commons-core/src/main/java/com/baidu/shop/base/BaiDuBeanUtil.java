package com.baidu.shop.base;

import org.springframework.beans.BeanUtils;

/**
 * @ClassName BaiDuBeanUtil
 * @Description: TODO
 * @Author wangyue
 * @Date 2021/1/22
 * @Version V1.0
 **/
public class BaiDuBeanUtil {
    public static <T> T copyProperties(Object source,Class<T> clazz){

        try {
            T t = clazz.newInstance();//创建当前类型的实例
            BeanUtils.copyProperties(source,t);
            return t;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }
}
