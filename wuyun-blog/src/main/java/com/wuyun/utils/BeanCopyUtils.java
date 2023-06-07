package com.wuyun.utils;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * 拷贝工具
 * 复制对象或集合属性
 * @author DarkClouds
 * @date 2023/05/13
 */
public class BeanCopyUtils {

    /**
     * 复制对象
     *
     * @param source 源
     * @param target 目标
     * @return {@link T}
     */
    public static <T> T copyBean(Object source, Class<T> target) {
        // 创建目标对象
        T result = null;
        try {
            result = target.getDeclaredConstructor().newInstance();
            if (source != null) {
                // 实现属性copy
                BeanUtils.copyProperties(source, result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 返回结果
        return result;
    }

    /**
     * 拷贝集合
     *
     * @param source 源
     * @param target 目标
     * @return {@link List}<{@link T}>
     */
    public static <T, S> List<T> copyBeanList(List<S> source, Class<T> target) {
        List<T> list = new ArrayList<>();
        if (null != source && source.size() > 0) {
            for (Object obj : source) {
                list.add(copyBean(obj, target));
            }
        }
        return list;
    }

}
