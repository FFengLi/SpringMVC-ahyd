package cn.edu.guet.util;

import cn.edu.guet.common.ResponseData;
import cn.edu.guet.service.impl.PlanDesignServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: fanFengLi
 * @Date: 2023/06/28/13:21
 * @Description:
 */
public class TransactionHandler implements InvocationHandler {
    public static Logger logger = LoggerFactory.getLogger(TransactionHandler.class);

    private Object targetObject;

    /**
     * @Description: 根据目标对象创建代理对象
     * @Param: [targetObject]
     * @return:
     */
    public Object creatProxyObject(Object targetObject){
        this.targetObject = targetObject;
        return Proxy.newProxyInstance(targetObject.getClass().getClassLoader(), targetObject.getClass().getInterfaces()
                ,this);
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        Connection con = null;
        try{

            con = DBConnection.getConn();
            logger.info("方法名称：{}", method.getName());
            Object retValue = null;
            if (method.getName().startsWith("create") || method.getName().startsWith("save")
                    || method.getName().startsWith("new")
                    || method.getName().startsWith("delete")
                    || method.getName().startsWith("update")) {
                // 开启事务
                con.setAutoCommit(false);
                retValue = method.invoke(targetObject, args);
                con.commit();
            } else {
                retValue = method.invoke(targetObject, args);
            }
            return retValue;
        }
        catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            try {
                con.rollback();
                System.out.println("回滚事务");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return ResponseData.fail("保存失败");
        }
        return null;
    }
}
