package com.yangdonglin.mcto.utils;

/**
 * Bean复制工具回调方法
 *
 */
@FunctionalInterface
public interface BeanCopyUtilCallBack <S, T>{
    /**
     * 定义默认回调方法
     * @param t target
     * @param s source
     */
    void callBack(S t, T s);
}
