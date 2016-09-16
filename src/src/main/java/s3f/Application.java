package s3f;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import s3f.framework.lifecycle.LifeCycle;
import s3f.framework.lifecycle.LifecycleUrlDictionary;


@SpringBootApplication
public class Application {
    public static Logger LOGGER_DEFAULT;
    public static Logger LOGGER_DEBUG;
    private static String[] args;
    public static final String version = "v1";
    public static String lifecycle;
    public final static String serviceName = "ka-user-store";
    public final static boolean useConfigService = true;

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
        LOGGER_DEFAULT = Logger.getLogger("default");
        LOGGER_DEBUG = Logger.getLogger("debug");
        LOGGER_DEFAULT.setLevel(Level.INFO);
        LOGGER_DEBUG.setLevel(Level.DEBUG);
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
}
