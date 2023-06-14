package cn.edu.guet.dao;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

/**
 * T是泛型：没有指定类型，例如save方法，将来既能保存Users对象，也能保存Student对象
 * @Author liwei
 * @Date 2023/1/4 09:32
 * @Version 1.0
 */
public interface BaseDao<T> {

    /**
     * 保存
     * @param t
     * @return
     * @throws SQLException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    int save(T t) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;

    /**
     * 查找
     * @param id
     * @return
     */
    T getObjectById(Long id);
    List<T> getObjects();
}
