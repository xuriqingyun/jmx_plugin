package zhouxu.site.jmx.plugin.constant;

/**
 * 全局变量配置
 *
 * @author zhouxu
 * @date 2019/03/21 11:29
 **/
public final class GlobalConstants {

    /**
     * map初始化大小
     */
    public static final int MAP_CAPACITY = 16;
    /**
     * list初始化大小
     */
    public static final int LIST_CAPACITY = 16;

    private GlobalConstants () {
        throw new AssertionError("不允许实例化该类");
    }
} 
