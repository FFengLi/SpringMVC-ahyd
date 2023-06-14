package cn.edu.guet.mvc;

import cn.edu.guet.mvc.annotation.Controller;
import cn.edu.guet.mvc.annotation.RequestMapping;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @Author liwei
 * @Date 2020/9/5 10:33
 * @Version 1.0
 */
public class Configuration {

    public Map<String, ControllerMapping> config() throws URISyntaxException {

        Map<String, ControllerMapping> controllerMapping = new HashMap<String, ControllerMapping>();

        ResourceBundle bundle = ResourceBundle.getBundle("config");
        String controllerPackageName = bundle.getString("controller.package");
        /**
         * 把报名转换为路径
         */
        URI uri = Configuration.class.getResource("/" + controllerPackageName.replace(".", "/")).toURI();

        File file = new File(uri);

        String controllerClassNames[] = file.list();
        for (String className : controllerClassNames) {
            if (className.endsWith(".class")) {
                String fullClassName = controllerPackageName + "." + StringUtils.substringBefore(className, ".class");
                try {
                    Class controllerClass = Class.forName(fullClassName);
                /*
                如果clazz中有Controller注解，才进一步处理
                 */
                    if (controllerClass.isAnnotationPresent(Controller.class)) {
                        Method methods[] = MethodUtils.getMethodsWithAnnotation(controllerClass, RequestMapping.class);
                    /*
                    把注解的信息暂时存储到一个map中，以便在Servlet中收到相应的请求后，找到相应的控制器的方法去处理请求
                     */
                        for (Method method : methods) {
                            RequestMapping annotation = method.getAnnotation(RequestMapping.class);
                            ControllerMapping mapping = new ControllerMapping(controllerClass, method);
                            controllerMapping.put(annotation.value(), mapping);
                        }
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return controllerMapping;
    }
}
