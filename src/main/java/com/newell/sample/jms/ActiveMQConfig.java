package com.newell.sample.jms;

import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.usage.SystemUsage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Configuration
public class ActiveMQConfig {

    @Value("${jms.broker.http.address:0.0.0.0}")
    private String httpBindAddress;
    @Value("${jms.broker.http.port:9080}")
    private String httpBindPort;
    @Value("${jms.broker.http.enabled:true}")
    private boolean httpEnabled;
    @Value("${jms.broker.tcp.port:61616}")
    private String tcpBindPort;
    @Value("${jms.broker.tcp.address:0.0.0.0}")
    private String tcpBindAddress;
    @Value("${jms.broker.tcp.enabled:false}")
    private boolean tcpEnabled;
    @Value("${jms.broker.vm.enabled:false}")
    private boolean vmEnabled;
    @Value("${jms.broker.vm.address:localhost}")
    private String vmBindAddress;
    @Value("${jms.broker.name:localhost}")
    private String brokerName;
    @Value("${jms.broker.memory.limit.mb:50}")
    private long memoryLimit;
    @Value("${jms.broker.use.jmx:false}")
    private boolean useJMX;
    @Value("${jms.broker.shutdown.hook.use:false}")
    private boolean useShutdown;
    @Value("${jms.broker.shutdown.hook.class:}")
    private String shutdownHookClass;

    @Bean(initMethod = "start", destroyMethod = "stop")
    public BrokerService broker() throws Exception {

        final BrokerService broker = new BrokerService();
        broker.setBrokerName(brokerName);
        broker.setPersistent(false);
        broker.setUseJmx(useJMX);

        //Shutdown Hook stuff...
        broker.setUseShutdownHook(useShutdown);
        if(useShutdown && !StringUtils.isEmpty(shutdownHookClass)) {
            try {
                System.out.println(String.format("Adding Active MQ Shutdown Hook [%s]", shutdownHookClass));
                broker.addShutdownHook((Runnable) Class.forName(shutdownHookClass).newInstance());
            }
            catch(Throwable t)
            {
                System.out.println(String.format("Could not add Shutdown Hook [$s]. Ensure class exists on classpath and" +
                                "implements Runnable! Details: %s",
                        shutdownHookClass,
                        t.getMessage()));
            }

        }
        //Setup memory manager
        final SystemUsage memoryManager = new SystemUsage();
        memoryManager.getMemoryUsage().setLimit(1024 * 1024 * memoryLimit); //MB
        broker.setSystemUsage(memoryManager);

        //Setup Connectors
        if (httpEnabled) {
            broker.addConnector("http://" + httpBindAddress + ":" + httpBindPort);
        }
        if (tcpEnabled) {
            broker.addConnector("tcp://" + tcpBindAddress + ":" + tcpBindPort);
        }
        if (vmEnabled) {
            broker.addConnector("vm://" + vmBindAddress);
        }

        return broker;
    }
}
