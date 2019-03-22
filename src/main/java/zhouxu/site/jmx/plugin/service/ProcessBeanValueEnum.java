package zhouxu.site.jmx.plugin.service;

import zhouxu.site.jmx.plugin.constant.GlobalConstants;
import zhouxu.site.jmx.plugin.exception.MbeanValueTypeNotSupportException;
import zhouxu.site.jmx.plugin.pojo.Metric;

import javax.management.MBeanAttributeInfo;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.CompositeType;
import javax.management.openmbean.TabularData;
import java.util.*;

/**
 * 用于MBean对象值不同类型解析
 *
 * @author zhouxu
 * @date 2019/03/21 15:42
 **/
enum ProcessBeanValueEnum implements ProcessAble{
    /**
     * 数值类型
     */
    NUMBER(Number.class),
    /**
     * 字符类型
     */
    STRING(String.class),
    /**
     * boolean类型
     */
    BOOLEAN(Boolean.class),
    /**
     * ObjectName类型
     */
    OBJECTNAME(ObjectName.class),
    /**
     * CompositeData类型
     */
    COMPOSITEDATA(CompositeData.class){
        @Override
        public List<Metric> processBeanValue(ObjectName objectInstance, MBeanAttributeInfo mBeanAttributeInfo,Object attributeValue) {
            List<Metric> metrics = new ArrayList<Metric>();
            CompositeData composite = (CompositeData) attributeValue;
            CompositeType type = composite.getCompositeType();
            for(String key : type.keySet()) {
                Object value = composite.get(key);
                Map<String,String> objectNameMap = getObjectNameMap(objectInstance.getCanonicalName());
                Metric metric = new Metric(getMetricPrefix(objectNameMap) + mBeanAttributeInfo.getName() + "_" + key,value, objectInstance.getDomain(), new Date(),getTag(objectNameMap));
                metrics.add(metric);
            }
            return metrics;
        }
    },
    /**
     * TabularData 类型
     */
    TABULARDATA(TabularData.class){
        @Override
        public List<Metric> processBeanValue(ObjectName objectInstance, MBeanAttributeInfo mBeanAttributeInfo,Object attributeValue) {
            // TODO
            throw new MbeanValueTypeNotSupportException(objectInstance.getCanonicalName() , mBeanAttributeInfo.getName(),attributeValue.getClass());
        }
    },
    /**
     * 不支持数据类型
     */
    NOTSUPORT(MbeanValueTypeNotSupportException.class){
        @Override
        public List<Metric> processBeanValue(ObjectName objectInstance, MBeanAttributeInfo mBeanAttributeInfo,Object attributeValue) {
            throw new MbeanValueTypeNotSupportException(objectInstance.getCanonicalName() , mBeanAttributeInfo.getName(),attributeValue.getClass());
        }
    };
    /**
     * 定义objectname中包含的字符串name
     */
    private static final String OBJECT_NAME_INCLUDE_NAME = "name";
    /**
     * 定义objectname中包含的字符串type
     */
    private static final String OBJECT_NAME_INCLUDE_TYPE = "type";
    /**
     * 对应的字节码
     */
    Class<?> aClass;
    ProcessBeanValueEnum(Class<?> aClass) {
        this.aClass = aClass;
    }
    public Class<?> getaClass() {
        return aClass;
    }

    /**
    * 通过字节码获取ProcessBeanValueEnum对象
    *
    * @param tmpClass
    * @return zhouxu.site.jmx.plugin.service.ProcessBeanValueEnum
    * @throws
    * @author zhouxu
    * @date 15:55 2019/3/21
    **/
    public static ProcessBeanValueEnum getEnum (Class<?> tmpClass) {
        for(ProcessBeanValueEnum processBeanValueEnum : ProcessBeanValueEnum.values()) {
            if(processBeanValueEnum.getaClass().isAssignableFrom(tmpClass)) {
                return processBeanValueEnum;
            }
        }
        return NOTSUPORT;
    }

    @Override
    public List<Metric> processBeanValue(ObjectName objectInstance, MBeanAttributeInfo mBeanAttributeInfo,Object attributeValue) {
        List<Metric> list = new ArrayList<Metric>();
        Map<String,String> objectNameMap = getObjectNameMap(objectInstance.getCanonicalName());
        Metric metric = new Metric(getMetricPrefix(objectNameMap) + mBeanAttributeInfo.getName(),attributeValue ,objectInstance.getDomain(), new Date(),getTag(objectNameMap));
        list.add(metric);
        return list;
    }

    /**
    * 获取指标前缀
    *
    * @param map
    * @return java.lang.String
    * @throws
    * @author zhouxu
    * @date 17:52 2019/3/21
    **/
    protected String getMetricPrefix (Map<String,String> map) {
        String prefix = "";
        if(map.containsKey(OBJECT_NAME_INCLUDE_TYPE)) {
            prefix += map.get(OBJECT_NAME_INCLUDE_TYPE).replace("/","_").replace(" ","") + "_";
        }
        if(map.containsKey(OBJECT_NAME_INCLUDE_NAME)) {
            prefix += map.get(OBJECT_NAME_INCLUDE_NAME).replace("/","_").replace(" ","") + "_";
        }
        return prefix;
    }

    protected Map<String,String> getTag(Map<String,String> map) {
        Map<String,String> tagMap = new HashMap<String, String>(GlobalConstants.MAP_CAPACITY);
        for(Map.Entry<String,String> entry : map.entrySet()) {
            if(!OBJECT_NAME_INCLUDE_TYPE.equals(entry.getKey()) && !OBJECT_NAME_INCLUDE_NAME.equals(entry.getKey())) {
                tagMap.put(entry.getKey(),entry.getValue());
            }
        }
        return tagMap;
    }

    /**
    * 获取objectName的map
    *
    * @param canonicalName
    * @return java.util.Map<java.lang.String,java.lang.Object>
    * @throws
    * @author zhouxu
    * @date 17:57 2019/3/21
    **/
    protected Map<String,String> getObjectNameMap(String canonicalName){
        Map<String,String> map = new HashMap<String, String>(GlobalConstants.MAP_CAPACITY);
        String[] strs =  canonicalName.split(",");
        if(strs != null && strs.length > 0) {
            for(String str : strs) {
               String[] keyValuePair =  str.split("=");
               if(keyValuePair != null && keyValuePair.length ==2) {
                   map.put(keyValuePair[0].substring(keyValuePair[0].lastIndexOf(':') + 1 ),keyValuePair[1]);
               }
            }
        }
        return map;
    }
}

/**
 * 定义解析方式
 *
 * @author zhouxu
 * @date 2019/03/21 15:42
 **/
interface ProcessAble {

    /**
     * 指标解析
     * @param objectInstance
	 * @param mBeanAttributeInfo
	 * @param attributeValue
     * @return java.util.List<zhouxu.site.jmx.plugin.pojo.Metric>
     * @throws:
     * @author zhouxu
     * @date 2019/3/22 14:07
     */
    List<Metric> processBeanValue(ObjectName objectInstance, MBeanAttributeInfo mBeanAttributeInfo,Object attributeValue);
}
