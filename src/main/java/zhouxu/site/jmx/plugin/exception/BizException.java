package zhouxu.site.jmx.plugin.exception;

/**
 * 业务异常统一定义
 *
 * @author zhouxu
 * @date 2019/03/21 15:24
 **/
public class BizException  extends  RuntimeException{
    public BizException(String msg) {
        super(msg);
    }
} 
