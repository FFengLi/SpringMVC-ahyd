package cn.edu.guet.util;

import cn.edu.guet.common.ResponseData;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

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
        SqlSession sqlSession = null;
        try{

            sqlSession = DBUtil.getSqlSession();
            logger.info("代理类中获取的{}",sqlSession);
            logger.info("方法名称：{}", method.getName());
            Object retValue = null;
            if (method.getName().startsWith("create") || method.getName().startsWith("save")
                    || method.getName().startsWith("new")
                    || method.getName().startsWith("delete")
                    || method.getName().startsWith("update")) {
                retValue = method.invoke(targetObject, args);
                logger.info("代理类中commit的{}",sqlSession);
                sqlSession.commit();
            } else {
                retValue = method.invoke(targetObject, args);
            }
            return retValue;
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            sqlSession.rollback();
            return ResponseData.fail("事务操作失败");
        }finally {
            logger.info("代理类中close的{}",sqlSession);
            sqlSession.close();
        }
        return null;
    }
}
