package com.wei.authserver.dao;


import com.wei.authmvc.mybatis.PageInfo;
import org.apache.ibatis.annotations.Mapper;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


@Mapper
public interface CommonDao {
    /**
     *
     * 增加一个实体
     * @param pojo
     * @return 影响的行数 0失败，1成功
     */
    public <T extends Serializable> int save(T pojo);

    /**
     *
     * 通过id删除实体
     *
     * @param clazz
     * @param id
     * @return
     */
    public <T extends Serializable> int deleteById(Class<T> clazz,
                                                   Serializable id);

    /**
     *
     * 通过主键获取实体
     *
     * @param clazz
     * @param id
     * @return
     */
    public <T extends Serializable> T getById(Class<T> clazz, Serializable id);

    /**
     *
     * 查询所有实体
     *
     * @param clazz
     * @return
     */
    public <T extends Serializable> List<T> listAll(Class<T> clazz);

    /**
     *
     * 分页查询
     *
     * @param clazz
     * @param p
     * @return
     */
    public <T extends Serializable> PageInfo<T> pageSelect(Class<T> clazz, PageInfo<T> p, String[]attrs, Object[]values);


    /**
     *
     * 分页查询时，用来统计总条数
     *
     * @param clazz
     * @param attrs
     * @param values
     * @return
     */
    public <T extends Serializable> int pageCount(Class<T> clazz,String[]attrs,Object[]values);

    /**
     *
     * 统计总条数
     *
     * @param clazz
     * @return
     */
    public <T extends Serializable> int countAll(Class<T> clazz);

    /**
     *
     * 指定查询使用的命名sql，查询结果封装成map
     *
     * @param paraMap
     * @return
     */
    List<Map<String,Object>>  selectMap( Map<String, Object> paraMap);

}