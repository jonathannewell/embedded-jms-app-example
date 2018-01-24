## JMS Provider App (using embedded ActiveMQ)

The Project is an example of using an embedded activeMQ server as a JMS Provider.

### Configuration

This application supports a number of configuration properties that can be overriden using standard spring-boot methods (Environment variables, application.yml etc.)

| Property Name | Description | Default |
| ------- | --- | ---- |
| jms.broker.name | The name given to the JMS Broker | localhost |
| jms.broker.memory.limit.mb | The maximum amount of memory that the JMS Broker will utlize (in Megabytes) | 50 |
| jms.broker.tcp.enabled | A boolean to determine if the TCP connector for the JMS broker will be available | false |  
| jms.broker.tcp.address | The address that the tcp connection for the JMS broker will listen on | 0.0.0.0 |
| jms.broker.tcp.port | The port that the tcp connection for the JMS broker will listen on | 61616 |
| jms.broker.http.address | The address that the http connection for the JMS broker will listen on | 0.0.0.0 |
| jms.broker.http.port | The port that the http connection for the JMS broker will listen on | 9080 |
| jms.broker.http.enabled | A boolean to determine if the http connector for the JMS broker will be available | true |
| jms.broker.vm.address | The address that the vm connection for the JMS broker will listen on | localhost |
| jms.broker.vm.enabled | A boolean to determine if the vm connector for the JMS broker will be available | false |
| jms.broker.use.jmx | Enable the broker JMX port. Note: do not enable this is the app is deployed to Cloud Foundry unless you also enable TCP Routing and build the appropriate route! | false |
| jms.broker.shutdown.hook.use | When the app/broker is shutdown are are shutdown hooks executed. | false |
| jms.broker.shutdown.hook.class | The fully qualified name of the class you would like executed at shutdown. Note: This class must be available on the classpath and implement the java.lang.Runnable interface! |  |
    

See the [Active MQ documentation](http://activemq.apache.org/using-activemq.html) for further details on the hows/whats of these configurations. **Note**:*property names above do not directly correlate to active mq documentation. Look at that code to understand what to look at...* 


### Development

#### Building the app

##### Using `mvn`

    $ mvn clean package
    
#### Using `docker build` via `dockerize`:

    $ ./dockerize <versionNumber>

>Note: run `dockerize` from the project root directory. This will build a docker image called coam-acl-jms and tag it with 'latest' and the version number you specified. If you do not specify a version number it will be tagged as version 1.0.
   

#### Running the app

##### Using `mvn`:

    $ mvn spring-boot:run

##### Using `java -jar` command: 

    $ java -jar target/embedded-jms-sample-*.jar

>Note: Use must build the jar first using `mvn clean package`

##### Using `docker run`:

    $ docker run -d --name jms-provider -p 9080:9080 embedded-jms

##### Verify it's running (assuming default configuration):

    $ curl http://localhost:9080
       
>Note: This will start the container and name it "jms-provider". You can verify it is running using `docker ps -a`


