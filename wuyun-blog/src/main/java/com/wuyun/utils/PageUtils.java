package com.wuyun.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Objects;


/**
 * 分页工具类
 * 类用来提供线程内部的局部变量，不同的线程之间不会相互干扰
 * @author DarkClouds
 * @date 2023/05/10
 */
public class PageUtils {

    private static final ThreadLocal<Page<?>> PAGE_HOLDER = new ThreadLocal<>();

    public static void setCurrentPage(Page<?> page) {
        PAGE_HOLDER.set(page);
    }

    public static Page<?> getPage() {
        Page<?> page = PAGE_HOLDER.get();
        if (Objects.isNull(page)) {
            setCurrentPage(new Page<>());
        }
        return PAGE_HOLDER.get();
    }

    public static Long getCurrent() {
        return getPage().getCurrent();
    }

    public static Long getSize() {
        return getPage().getSize();
    }

    public static Long getLimit() {
        return (getCurrent() - 1) * getSize();
    }

    //销毁
    public static void remove() {
        PAGE_HOLDER.remove();
    }

}
