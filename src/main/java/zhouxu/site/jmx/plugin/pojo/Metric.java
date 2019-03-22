package zhouxu.site.jmx.plugin.pojo;

import zhouxu.site.jmx.plugin.util.DateUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 指标定义
 *
 * @author zhouxu
 * @date 2019/03/21 14:49
 **/
public class Metric {
    /**
     * 指标名称
     */
    private String name;
    /**
     * 指标的值
     */
    private Object value;
    /**
     * 对应域
     */
    private String domain;

    /**
     * 对应时间
     */
    private Date date;

    /**
     * 指标其余域解析使用tag
     */
    private Map<String,String> tags = new HashMap<String, String>();

    public Metric() {

    }


    public Metric(String name, Object value, String domain, Date date) {
        this.name = name;
        this.value = value;
        this.domain = domain;
        this.date = date;
    }

    public Metric(String name, Object value, String domain, Date date, Map<String, String > tags) {
        this.name = name;
        this.value = value;
        this.domain = domain;
        this.date = date;
        this.tags = tags;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Map<String, String> getTags() {
        return tags;
    }

    public void setTags(Map<String, String> tags) {
        this.tags = tags;
    }

    private String mapView () {
        StringBuilder stringBuilder = new StringBuilder();
        for(Map.Entry<String, String> entry : this.tags.entrySet()) {
            stringBuilder.append(", ").append(entry.getKey()).append("='").append(entry.getValue()).append('\'');
        }
        return stringBuilder.toString();
    }
    @Override
    public String toString() {
        return "Metric{" +
                "name='" + name + '\'' +
                ", value=" + value +
                ", domain='" + domain + '\'' +
                mapView() +
                ", date=" + DateUtils.format(date) +
                '}';
    }
}
