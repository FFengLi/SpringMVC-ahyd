package cn.edu.guet.mvc;

import cn.edu.guet.ioc.BeanFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author liwei
 * @Date 2020/9/5 15:20
 * @Version 1.0
 */
@WebServlet(value = "*.do")
public class DispatcherServlet extends HttpServlet {

    private static Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    Map<String, ControllerMapping> controllerMapping;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        controllerMapping = (Map<String, ControllerMapping>) config.getServletContext().getAttribute("cn.guet.web.controller");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ControllerMapping mapping = null;
        String requestURI = request.getRequestURI();
        requestURI = StringUtils.substringBetween(requestURI, request.getContextPath(), ".do");
        if (controllerMapping.containsKey(requestURI)) {
            mapping = controllerMapping.get(requestURI);
        }
        Class controllerClass = mapping.getControllerClass();
        Method handleMethod = mapping.getHandleMethod();
        Class[] parameterType = handleMethod.getParameterTypes();

        /*
        反射获取参数的名称
         */
        List<String> paramterList = new ArrayList<String>();
        Parameter[] params = handleMethod.getParameters();
        for (Parameter parameter : params) {
            paramterList.add(parameter.getName());
        }

        Object[] parameterValues = new Object[parameterType.length];
        Object obj = null;
        try {
            String controllerClassName = controllerClass.getSimpleName();
            controllerClassName = StringUtils.replaceChars(controllerClassName, controllerClassName.substring(0, 1), controllerClassName.substring(0, 1).toLowerCase());
            obj = BeanFactory.getInstance().getBean(controllerClassName);
            //obj = controllerClass.newInstance();
            logger.info("接口参数的长度："+ parameterType.length);
            for (int i = 0; i < parameterType.length; i++) {

                if (ClassUtils.isAssignable(parameterType[i], HttpServletRequest.class)) {
                    parameterValues[i] = request;
                } else if (ClassUtils.isAssignable(parameterType[i], HttpServletResponse.class)) {
                    parameterValues[i] = response;
                } else if (ClassUtils.isAssignable(parameterType[i], HttpSession.class)) {
                    parameterValues[i] = request.getSession();
                } else if (parameterType[i].isPrimitive()) {

                    if (parameterType[i].getTypeName().equals("int")) {
                        parameterValues[i] = Integer.parseInt(request.getParameter(paramterList.get(i)));
                    } else if (parameterType[i].getTypeName().equals("float")) {
                        parameterValues[i] = Float.parseFloat(request.getParameter(paramterList.get(i)));
                    } else if (parameterType[i].getTypeName().equals("long")) {
                        parameterValues[i] = Long.parseLong(request.getParameter(paramterList.get(i)));
                    }


                } else if (ClassUtils.isAssignable(parameterType[i], String.class)) {
                    parameterValues[i] = request.getParameter(paramterList.get(i));
                } else {
                    request.setCharacterEncoding("UTF-8");
                    // 输入流取数据
                    InputStreamReader isr=new InputStreamReader(request.getInputStream(),"UTF-8");
                    // BufferedReader取前段数据
                    BufferedReader br =new BufferedReader(isr);
                    StringBuffer sb = new StringBuffer();
                    String line="";
                    while((line= br.readLine())!=null){
                        sb.append(line);
                    }

                    // json数据映射到对象
                    Gson gson= new Gson();
                    Object pojo=gson.fromJson(sb.toString(), parameterType[i]);

                    // 给接口中的参数赋值
                    parameterValues[i] = pojo;
                }
            }
            /*
            通过反射调用方法，并且把参数的值赋值给
             */
            Object returnValue = handleMethod.invoke(obj, parameterValues);
            if (returnValue != null && returnValue instanceof String) { //方法返回的是一个字符串类
                String path = returnValue.toString();
                if (StringUtils.startsWith(path, "redirect:")) {
                    //重定向
                    response.sendRedirect(request.getContextPath() + StringUtils.substringAfter(path, "redirect:"));
                } else {
                    //转发
                    request.getRequestDispatcher(path).forward(request, response);
                }
            } else if (returnValue != null && !(returnValue instanceof String)) {
                response.setContentType("application/json; charset=UTF-8");
                //返回的是一个bean，即客户端发送的是ajax请求，并将该bean转换成json
                String json = new GsonBuilder()
                        .setDateFormat("yyyy-MM-dd HH:mm:ss")
                        .setPrettyPrinting()
                        .create()
                        .toJson(returnValue);
                PrintWriter out = response.getWriter();
                out.write(json);
                out.flush();
                out.close();
            }
        } /*catch (InstantiationException e) {
            e.printStackTrace();
        }*/ catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
