package zhouxu.site.jmx.plugin.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;

/**
 * json转换工具
 *
 * @author zhouxu
 * @date 2019/03/21 16:57
 **/
public final class GsonUtils {
    private static Gson gson = new Gson();
    private GsonUtils() {
        throw new AssertionError("不能实例化此对象");
    }

    /**
     * object转换成json
     * @param object
     * @return java.lang.String
     * @throws:
     * @author zhouxu
     * @date 2019/3/22 14:20
     */
    public static String toJson(Object object) {
        String gsonString = null;
        if (gson != null) {
            gsonString = gson.toJson(object);
        }
        return gsonString;
    }

    /**
     * string转object
     * @param str
	 * @param cls
     * @return T
     * @throws:
     * @author zhouxu
     * @date 2019/3/22 14:20
     */
    public static <T> T parese(String str, Class<T> cls) {
        T t = null;
        if (gson != null) {
            t = gson.fromJson(str, cls);
        }
        return t;
    }

    /**
     * string转list对象集合
     * @param str
     * @return java.util.List<T>
     * @throws:
     * @author zhouxu
     * @date 2019/3/22 14:21
     */
    public static <T> List<T> parseList(String str) {
        List<T> list = null;
        if (gson != null) {
            list = gson.fromJson(str, new TypeToken<List<T>>() {
            }.getType());
        }
        return list;
    }

    /**
     * string转List<Map<String, T>>
     * @param str
     * @return java.util.List<java.util.Map<java.lang.String,T>>
     * @throws:
     * @author zhouxu
     * @date 2019/3/22 14:21
     */
    public static <T> List<Map<String, T>> parseListMaps(String str) {
        List<Map<String, T>> list = null;
        if (gson != null) {
            list = gson.fromJson(str,
                    new TypeToken<List<Map<String, T>>>() {
                    }.getType());
        }
        return list;
    }

    /**
     * string 转map
     * @param str
     * @return java.util.Map<java.lang.String,T>
     * @throws:
     * @author zhouxu
     * @date 2019/3/22 14:21
     */
    public static <T> Map<String, T> parseMap(String str) {
        Map<String, T> map = null;
        if (gson != null) {
            map = gson.fromJson(str, new TypeToken<Map<String, T>>() {
            }.getType());
        }
        return map;
    }
}
