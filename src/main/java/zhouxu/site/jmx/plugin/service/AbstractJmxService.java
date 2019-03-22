package zhouxu.site.jmx.plugin.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zhouxu.site.jmx.plugin.constant.GlobalConstants;
import zhouxu.site.jmx.plugin.exception.BizException;
import zhouxu.site.jmx.plugin.exception.MbeanValueTypeNotSupportException;
import zhouxu.site.jmx.plugin.pojo.Metric;
import javax.management.*;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * JmxService共有方法实现
 *
 * @author zhouxu
 * @date 2019/03/21 15:32
 **/
public abstract class AbstractJmxService implements JmxService,ProcessAble{

    private static final Logger logger = LoggerFactory.getLogger(AbstractJmxService.class);
    /**
     * JMX连接URL
     */
    protected static final String JMX_SERVICE_URL = "service:jmx:rmi:///jndi/rmi://%s:%s/jmxrmi";

    @Override
    public MBeanServerConnection getCon(String ip, String port) {
        try {
            JMXServiceURL url = new JMXServiceURL(String.format(JMX_SERVICE_URL,ip,port));
            JMXConnector conn = JMXConnectorFactory.connect(url);
            return conn.getMBeanServerConnection();
        }catch (Exception e) {
            logger.error("", e);
            throw new BizException("不能获取连接");
        }
    }

    @Override
    public List<Metric> processBeanValue(ObjectName objectInstance, MBeanAttributeInfo mBeanAttributeInfo, Object attributeValue) {
        if(attributeValue!=null) {
            try{
                return ProcessBeanValueEnum.getEnum(attributeValue.getClass()).processBeanValue(objectInstance, mBeanAttributeInfo, attributeValue);
            }catch (MbeanValueTypeNotSupportException e){
//                logger.error("",e);
            }
        }
        return new ArrayList<Metric>();
    }

   /**
    * 通过连接获取metric
    * @param mbsc
   	* @param objectNameIns
   	* @param mBeanInfo
    * @return java.util.List<zhouxu.site.jmx.plugin.pojo.Metric>
    * @throws
    * @author zhouxu
    * @date 12:06 2019/3/22
    */
    protected List<Metric> listByObjectName(MBeanServerConnection mbsc,ObjectName objectNameIns,MBeanInfo mBeanInfo) {
        List<Metric> metrics = new ArrayList<Metric>(GlobalConstants.LIST_CAPACITY);
        MBeanAttributeInfo[] attributInfos = mBeanInfo.getAttributes();
        Map<String,MBeanAttributeInfo> name2AttrInfo = new LinkedHashMap<String, MBeanAttributeInfo>();
        for(MBeanAttributeInfo mBeanAttributeInfo : attributInfos) {
            if(mBeanAttributeInfo.isReadable()){
                name2AttrInfo.put(mBeanAttributeInfo.getName(), mBeanAttributeInfo);
            }
        }
        AttributeList attributes = null;
        try {
            attributes = mbsc.getAttributes(objectNameIns, name2AttrInfo.keySet().toArray(new String[0]));
        } catch (Exception e) {
            logger.error("", e);
            throw new BizException(objectNameIns.getCanonicalName() + "获取属性列表失败");
        }
        for (Attribute attribute : attributes.asList()) {
            MBeanAttributeInfo attr = name2AttrInfo.get(attribute.getName());
            if(attr != null) {
                List<Metric> metricList = processBeanValue(objectNameIns,attr, attribute.getValue());
                metrics.addAll(metricList);
            }
        }
        return metrics;
    }
} 
