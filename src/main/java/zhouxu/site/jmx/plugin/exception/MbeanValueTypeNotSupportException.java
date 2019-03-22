package zhouxu.site.jmx.plugin.exception;

/**
 * mbean对象类型不支持解析异常
 *
 * @author zhouxu
 * @date 2019/03/22 10:28
 **/
public class MbeanValueTypeNotSupportException extends RuntimeException {
    public MbeanValueTypeNotSupportException(String objectName, String attribute, Class<?> tClass) {
        super("不支持" + objectName +" " + attribute + " " + tClass);
    }
} 
