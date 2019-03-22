package zhouxu.site.jmx.plugin.service;

import zhouxu.site.jmx.plugin.pojo.Metric;

import javax.management.MBeanServerConnection;
import java.util.List;

/**
 * jmx获取指标接口定义
 *
 * @author zhouxu
 * @date 2019/03/21 14:55
 **/
public interface JmxService {

    /**
     * 通过objectName获取指标
     * @param ip
	 * @param port
	 * @param objectNameStr
     * @return java.util.List<zhouxu.site.jmx.plugin.pojo.Metric>
     * @throws:
     * @author zhouxu
     * @date 2019/3/22 14:00
     */
    List<Metric> listByObjectName(String ip, String port,String objectNameStr);

    /**
     * 通过ip端口获取全部指标
     * @param ip
	 * @param port
     * @return java.util.List<zhouxu.site.jmx.plugin.pojo.Metric>
     * @throws:
     * @author zhouxu
     * @date 2019/3/22 13:59
     */
    List<Metric> listAll(String ip, String port);

    /**
     * 获取MBeanServerConnection
     * @param ip
	 * @param port
     * @return javax.management.MBeanServerConnection
     * @throws:
     * @author zhouxu
     * @date 2019/3/22 13:59
     */
    MBeanServerConnection getCon(String ip, String port);
}
