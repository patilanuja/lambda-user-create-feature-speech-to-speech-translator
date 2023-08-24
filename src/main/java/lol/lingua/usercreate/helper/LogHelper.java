package lol.lingua.usercreate.helper;

import com.amazonaws.services.lambda.runtime.LambdaLogger;

public class LogHelper {

    private static volatile LambdaLogger logger;

    public static void init(LambdaLogger log) {
        logger = log;
    }

    public static LambdaLogger getLogger() {
        return logger;
    }

}
