package com.yangdonglin.mcto.utils;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yangdonglin.mcto.module.PageQueryParams;
import com.yangdonglin.mcto.module.PageResult;
import org.apache.commons.lang3.StringUtils;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 公共工具类
 * @since 2021/12/22
 */
public class PageQueryWrapperHelper {

    public static interface IFilter<E,T> {
        public void filterAction(E res,T dest);
    }
    /**
     * 创建 LambdaQueryWrapper 并根据 ${PageQueryPagination} 增加配排序列表
     * @param queryParams
     * @param <T>
     * @return
     */
    public static  <T> LambdaQueryWrapper<T> buildQueryWrapper(PageQueryParams queryParams) {
        LambdaQueryWrapper<T> queryWrapper = new LambdaQueryWrapper<>();
        buildQueryWrapper(queryParams.getPagination(),queryWrapper);
        return queryWrapper;
    }

    /**
     *  封装增加排序,要放到最后
     * @param pagination
     * @param wrapper
     */
    public static void buildQueryWrapper(PageQueryParams.PageQueryPagination pagination, AbstractWrapper wrapper) {
        if(pagination==null){ return;}
        if(StringUtils.isBlank(pagination.getSord()) || StringUtils.isBlank(pagination.getSidx())){
            return;
        }
        boolean isAsc = "asc".equalsIgnoreCase(pagination.getSord());
        wrapper.last(String.format(" ORDER BY %s %s",pagination.getSidx(),isAsc ? "ASC" : "DESC"));
    }


    /**
     * 公共数据库查询方法
     * @param page
     * @param pageSize
     * @param wrapper
     * @param <T>
     * @return
     */
    public static  <T> IPage<T> pageList(Integer page, Integer pageSize, Wrapper<T> wrapper, IService<T> baseService) {
        int pageX = 1;
        int pageSizeX = 10;
        if (page!=null){
            pageX = page;
        }
        if (pageSize!=null){
            pageSizeX = pageSize;
            if(pageSizeX==0){
                pageSizeX = Integer.MAX_VALUE;
            }
        }
        IPage<T> ipage = new Page<>(pageX,pageSizeX);
        IPage<T> results = baseService.page(ipage,wrapper);
        return results;
    }

    /**
     * 直接生成结果集
     * @param page
     * @param pageSize
     * @param wrapper
     * @param baseService
     * @param <T>
     * @return
     */
    public static <T> PageResult<T> pageResult(Integer page, Integer pageSize, Wrapper<T> wrapper, IService<T> baseService){
        IPage<T> t = PageQueryWrapperHelper.pageList(page,pageSize,wrapper,baseService);
        return new PageResult<>(t.getRecords(),t.getTotal());
    }

    /**
     *
     * @param params
     * @param wrapper
     * @param baseService
     * @param <T>
     * @return
     */
    public static <T> PageResult<T> pageResult(PageQueryParams params, Wrapper<T> wrapper, IService<T> baseService){
        IPage<T> t = PageQueryWrapperHelper.pageList(params.getPagination().getPage(),params.getPagination().getPageSize(),wrapper,baseService);
        return new PageResult<>(t.getRecords(),t.getTotal());
    }
    /**
     * @param wrapper
     * @param cls
     * @param baseService
     * @return
     */
    public static <D,E> List<D> list(Wrapper<E> wrapper, Class<D> cls, IService<E> baseService) {
        List<E> t = baseService.list(wrapper);
        List<D> results = new ArrayList<>(t.size());
        for (E e : t){
            D d =BeanCopyHelper.mapTo(e,cls);
            results.add(d);
        }
        return results;
    }
    /**
     * 根据查出结果直接转结果到DTO对象
     * @param page
     * @param pageSize
     * @param wrapper
     * @param cls
     * @param baseService
     * @param <D>
     * @param <E>
     * @return
     */
    public static <D,E> PageResult<D> pageResultEtoD(Integer page, Integer pageSize, Wrapper<E> wrapper, Class<D> cls, IService<E> baseService){
        IPage<E> t = PageQueryWrapperHelper.pageList(page,pageSize,wrapper,baseService);
        List<D> results = new ArrayList<>(t.getRecords().size());
        for (E e : t.getRecords()){
            D d =BeanCopyHelper.mapTo(e,cls);
            results.add(d);
        }
        return new PageResult<>(results,t.getTotal());
    }

    /**
     * @param page
     * @param pageSize
     * @param wrapper
     * @param cls
     * @param baseService
     * @param <D>
     * @param <E>
     * @return
     */
    public static <D,E> PageResult<D> pageResultWithManagersEtoD(Integer page, Integer pageSize, Wrapper<E> wrapper, Class<D> cls,IFilter manager, IService<E> baseService){
        IPage<E> t = PageQueryWrapperHelper.pageList(page,pageSize==0?Integer.MAX_VALUE:pageSize,wrapper,baseService);
        List<D> results = new ArrayList<>(t.getRecords().size());
        for (E e : t.getRecords()){
            D d = paramsToEntity(e,cls);
            if(manager!=null){
                manager.filterAction(e,d);
            }
            results.add(d);
        }
        return new PageResult<>(results,t.getTotal());
    }

    public static <D,E> List<D> listWithFilterEtoD(Wrapper<E> wrapper, Class<D> cls,IFilter manager, IService<E> baseService){
        List<E> t = baseService.list(wrapper);
        List<D> results = new ArrayList<>(t.size());
        for (E e : t){
            D d = paramsToEntity(e,cls);
            if(manager!=null){
                manager.filterAction(e,d);
            }
            results.add(d);
        }
        return results;
    }

    /**
     * Dto 转 Entity
     * @param obj
     * @param cls
     * @param <D>
     * @param <E>
     * @return
     */
    public static <E,D> E paramsToEntity(Object obj,Class<E> cls)  {
        Class clsD = obj.getClass();
        Field[] fields = clsD.getDeclaredFields();
        try {
            E ex = cls.getDeclaredConstructor().newInstance();
            for (Field field:fields){
                try {
                    field.setAccessible(true);
                    Field  f = cls.getDeclaredField(field.getName());
                    if (null != f){
                        f.setAccessible(true);
                        Object o = field.get(obj);
                        f.set(ex,o);
                    }
                } catch (Exception e){
                }
            }
            return ex;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
