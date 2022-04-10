package com.yangdonglin.mcto.utils;

import com.alibaba.fastjson.JSON;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class BeanCopyHelper extends BeanUtils {
    /**
     * 集合数据的拷贝
     * @param sources: 数据源类
     * @param target: 目标类::new(eg: UserVO::new)
     * @return 目标类集合
     */
    public static <S, T> List<T> copyListProperties(List<S> sources, Supplier<T> target) {
        if (null == sources || sources.isEmpty()) {
            return new ArrayList<>(0);
        }
        return copyListProperties(sources, target, null);
    }

    /**
     * 带回调函数的集合数据的拷贝（可自定义字段拷贝规则）
     * @param sources: 数据源类
     * @param target: 目标类::new(eg: UserVO::new)
     * @param callBack: 回调函数
     * @return 目标类集合
     */
    public static <T, S> List<T> copyListProperties(List<S> sources, Supplier<T> target, BeanCopyUtilCallBack<S, T> callBack) {
        List<T> list = new ArrayList<>(sources.size());
        for (S source : sources) {
            T t = target.get();
            copyProperties(source, t);
            list.add(t);
            if (callBack != null) {
                // 回调
                callBack.callBack(source, t);
            }
        }
        return list;
    }

    public static <T> T mapTo(Object obj, Class<T> target) {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        return mapperFactory.getMapperFacade().map(obj, target);
    }

    public static void setValueByName(String name,Object obj,Object value) {
        Class cls = obj.getClass();
        try {
            Field field = cls.getDeclaredField(name);
            field.setAccessible(true);
            field.set(obj,value);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     *  特殊用于,对象属性不一致 而导致数据转换失败,此接口不报异常
     * @param obj
     * @param target
     * @param <T>
     * @return
     */
    public static <T> T mapProperties(Object obj,Supplier<T> target){
        T t = target.get();
        copyProperties(obj,t);
        return t;
    }

    /**
     *  带Managers 属性字段的copy
     *  managers 小写
     * @param obj
     * @param target
     * @param <T>
     * @return
     */
    public static <T> T mapToWithManagers(Object obj,Class cls, Supplier<T> target) {
        T t = target.get();
        copyProperties(obj,t);
        try {
            Field field = obj.getClass().getDeclaredField("managers");
            if(field!=null){
                field.setAccessible(true);
                if(field.getType() == String.class) {
                    Object d = field.get(obj);
                    if(d!=null){
                        List m = JSON.parseArray(d.toString(),cls);
                        setValueByName("managers",t,m);
                    }
                }else if(field.getType() == List.class){
                    Object d = field.get(obj);
                    if(d!=null){
                        String m = JSON.toJSONString(d);
                        setValueByName("managers",t,m);
                    }
                }
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     *  copy 属性带有Manaygers 属性
     * @param res
     * @param dest
     * @param cls
     */
    public static void copyPropertiesWithManagers(Object res,Object dest,Class cls) {
        copyManyWithManagers(res,dest,cls,"managers");
    }
    /**
     *  copy 属性带有其他多选Manaygers 属性
     * @param res
     * @param dest
     * @param cls
     */
    public static void copyManyWithManagers(Object res,Object dest,Class cls,String attributeName) {
        try {
            copyProperties(res,dest);
            Field field = res.getClass().getDeclaredField(attributeName);
            if(field!=null){
                field.setAccessible(true);
                if(field.getType() == String.class) {
                    Object d = field.get(res);
                    if(d!=null){
                        List m = JSON.parseArray(d.toString(),cls);
                        setValueByName(attributeName,dest,m);
                    }
                }else if(field.getType() == List.class){
                    Object d = field.get(res);
                    if(d!=null){
                        String m = JSON.toJSONString(d);
                        setValueByName(attributeName,dest,m);
                    }
                }
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }







    public static void copyPropertieList(Object res,Object dest,Class cls,String fieldName) {
        try {
            Field field = res.getClass().getDeclaredField(fieldName);
            if(field!=null){
                field.setAccessible(true);
                if(field.getType() == String.class) {
                    Object d = field.get(res);
                    if(d!=null){
                        List m = JSON.parseArray(d.toString(),cls);
                        setValueByName(fieldName,dest,m);
                    } else {
                        setValueByName(fieldName,dest,new ArrayList<>());
                    }
                }else if(field.getType() == List.class) {
                    Object d = field.get(res);
                    if(d!=null){
                        String m = JSON.toJSONString(d);
                        setValueByName(fieldName,dest,m);
                    }else {
                        setValueByName(fieldName,dest,"[]");
                    }
                }
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
