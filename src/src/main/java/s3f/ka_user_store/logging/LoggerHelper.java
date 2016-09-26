package s3f.ka_user_store.logging;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import s3f.Application;

public final class LoggerHelper {

	private static final Logger LOGGER_DEFAULT = Logger.getLogger("default");
	private static final Logger LOGGER_DEBUG = Logger.getLogger("debug");

	private final static String seperator = "|";
	
	private static String formatFormatedLogMessages(String messageText,String correlationToken,String authorization, String packageName){
		StringBuilder messageBuilder = new StringBuilder();
		messageBuilder.append(Application.lifecycle);
		messageBuilder.append(seperator);
		messageBuilder.append(messageText);
		messageBuilder.append(seperator);
		messageBuilder.append(correlationToken);
		messageBuilder.append(seperator);
		messageBuilder.append(Application.version);
		messageBuilder.append(seperator);
		messageBuilder.append(authorization);
		messageBuilder.append(seperator);
		messageBuilder.append(packageName);
		return messageBuilder.toString();
	}

    public static void logData (Level logLevel,String messageText,String correlationToken,String authorization, String packageName){
        String logmessage = formatFormatedLogMessages(messageText,correlationToken,authorization,packageName);
        LOGGER_DEBUG.log(logLevel, logmessage);
        LOGGER_DEFAULT.log(logLevel, logmessage);
    }
    public static void logData (Level logLevel, String messageText,String correlationToken,String authorization, String packageName, Exception exception){
        String logmessage = formatFormatedLogMessages(messageText,correlationToken,authorization,packageName);
        LOGGER_DEBUG.log(logLevel, logmessage,exception);
        LOGGER_DEFAULT.log(logLevel, logmessage);
    }
    public static void logData (Level logLevel,String messageText,String correlationToken,String authorization, String packageName, Throwable throwable){
        String logmessage = formatFormatedLogMessages(messageText,correlationToken,authorization,packageName);
        LOGGER_DEBUG.log(logLevel, logmessage,throwable);
        LOGGER_DEFAULT.log(logLevel, logmessage);
    }

}
