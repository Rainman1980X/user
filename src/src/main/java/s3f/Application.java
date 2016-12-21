package s3f;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Level;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import s3f.framework.interfaces.ApplicationConstants;
import s3f.framework.lifecycle.LifeCycle;
import s3f.framework.lifecycle.LifecycleUrlDictionary;
import s3f.framework.logger.LoggerHelper;

@SpringBootApplication
public class Application implements ApplicationConstants {
    private static String[] args;
    public static final String version = "v1";
    public final static String configServerAddress = "http://localhost:30000";
    public static final String configServiceAddress = "/api/v1/s3f-configuration/completion/{serviceName}/{version}/{lifecycle}/";
    public static String lifecycle;
    public final static String serviceName = "ka-user-store";

    /**
     * lifecycle (i.a. -develop, -test, -stage, -production)
     *
     * @see https://s3f.sintec.de/_layouts/15/start.aspx#/SitePages/Application-Lifecycle.aspx
     */
    public static void main(String[] args) {
        Application.args = args;
        new LifecycleUrlDictionary().check(args);
        Application.lifecycle = new LifecycleUrlDictionary().getKey(args);
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public LifeCycle build() {
        String result = "";
        for (String arg : args) {
            if (arg.startsWith("--lifecycle")) {
                result = arg.replaceFirst("--lifecycle", "");
                result = result.substring(1, result.length());
            }
        }
        return new LifeCycle(result);
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public String getLifecycle() {
        return lifecycle;
    }

    @Override
    public URL getConfigServerAddress() {
        try {
            return new URL(configServerAddress);
        } catch (MalformedURLException e) {
            LoggerHelper.logData(Level.ERROR, e.getMessage(), "token", "token", Application.class.getName());
        }
        return null;
    }

    @Override
    public String getConfigServiceAddress() {
        return configServiceAddress;
    }

    @Override
    public Map<String, String> getConfigServiceArguments() {
        HashMap<String,String> map = new HashMap<>();
        map.put("serviceName",serviceName);
        map.put("version",version);
        map.put("lifecycle",lifecycle);
        return map;
    }

    @Override
    public String getServiceName() {
        return serviceName;
    }
}
