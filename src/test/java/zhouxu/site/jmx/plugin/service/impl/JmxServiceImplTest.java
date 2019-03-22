package zhouxu.site.jmx.plugin.service.impl;

import org.junit.Before;
import org.junit.Test;
import zhouxu.site.jmx.plugin.pojo.Metric;
import zhouxu.site.jmx.plugin.service.JmxService;

import java.util.List;

public class JmxServiceImplTest {
    private static final String IP = "127.0.0.1";
    private static final String PORT = "9999";
    private static final String OBJECT_NAME = "Catalina:type=NamingResources,context=/host-manager,host=localhost";

    private JmxService jmxService;

    @Before
    public void init() {
        jmxService = new JmxServiceImpl();
    }

    @Test
    public void listByObjectName() {
        List<Metric> metrics = jmxService.listByObjectName(IP, PORT, OBJECT_NAME);
        for (Metric metric : metrics) {
            System.out.println(metric);
        }
    }

    @Test
    public void listAll() {
        List<Metric> metrics = jmxService.listAll(IP, PORT);
        for(Metric metric : metrics) {
            System.out.println(metric);
        }
        System.out.println(metrics.size());
    }
}