package zhouxu.site.jmx.plugin.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zhouxu.site.jmx.plugin.constant.GlobalConstants;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用于MXBean属性提取
 *
 * @author zhouxu
 * @date 2019/03/21 11:06
 **/
public final class ObjectToMapUtils {
    private static final Logger logger = LoggerFactory.getLogger(ObjectToMapUtils.class);
    private static final String CONSTRACOTR_ERROR="不允许实例化";
    private static final String GETTER_PREFIX="get";
    private ObjectToMapUtils(){
        throw new AssertionError(CONSTRACOTR_ERROR);
    }

    /**
     * 获取对象所有的属性值
     * @param instance
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @throws
     * @author zhouxu
     * @date 13:40 2019/3/22
     */
    public static <T> Map<String,Object> parese(T instance) {
        Map<String,Object> map = new ConcurrentHashMap<String, Object>(GlobalConstants.MAP_CAPACITY);
        Class<?> tempClass = instance.getClass();
        Method[] declaredMethods = tempClass.getDeclaredMethods();
        for (Method method : declaredMethods){
            String methodName = method.getName();
            if(methodName.startsWith(GETTER_PREFIX)){
                String filedName = methodName.substring(3,4).toLowerCase() + methodName.substring(4);
                try {
                    Object value = method.invoke(instance, null);
                    map.put(filedName,value);
                } catch (Exception e) {
                    logger.error("",e);
                }
            }
        }
        return map;
    }
} 
