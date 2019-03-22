package zhouxu.site.jmx.plugin.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zhouxu.site.jmx.plugin.exception.BizException;
import zhouxu.site.jmx.plugin.pojo.Metric;
import zhouxu.site.jmx.plugin.service.AbstractJmxService;
import zhouxu.site.jmx.plugin.service.JmxService;

import javax.management.MBeanInfo;
import javax.management.MBeanServerConnection;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * jmx服务实现
 *
 * @author zhouxu
 * @date 2019/03/21 15:18
 **/
public class JmxServiceImpl extends AbstractJmxService implements JmxService {
    private static final Logger logger = LoggerFactory.getLogger(JmxServiceImpl.class);
    @Override
    public List<Metric> listByObjectName(String ip, String port, String objectNameStr) {
        MBeanServerConnection mbsc = getCon(ip, port);
        ObjectName objectName = null;
        MBeanInfo mBeanInfo=null;
        try {
            objectName = new ObjectName(objectNameStr);
            mBeanInfo= mbsc.getMBeanInfo(objectName);
        } catch (Exception e) {
            logger.error("", e);
            throw new BizException("获取mbean对象失败");
        }
        return listByObjectName(mbsc, objectName, mBeanInfo);
    }

    @Override
    public List<Metric> listAll(String ip, String port)  {
        List<Metric> metrics = new LinkedList<Metric>();
        MBeanServerConnection mbsc = getCon(ip, port);

        String[] domains = new String[0];
        try {
            domains = mbsc.getDomains();
        } catch (IOException e) {
           logger.error("", e);
           throw new BizException("获取属性列表失败");
        }
        for(String domain : domains) {
            try {
                ObjectName objectName = new ObjectName(domain + ":*");
                Set<ObjectInstance> queryMBeans = mbsc.queryMBeans(objectName, null);
                for(ObjectInstance objectInstance : queryMBeans) {
                    ObjectName echoObjectName = objectInstance.getObjectName();
                    try{
                        List<Metric> mbMetrics = listByObjectName(mbsc, echoObjectName, mbsc.getMBeanInfo(echoObjectName));
                        metrics.addAll(mbMetrics);
                    } catch (Exception e) {
                        logger.error("", e);
                    }
                }
            } catch (Exception e) {
                logger.error("", e);
            }
        }
        return metrics;
    }


}
