package lol.lingua.usercreate;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import lol.lingua.usercreate.helper.LogHelper;
import lol.lingua.usercreate.model.RequestIn;
import lol.lingua.usercreate.repository.RDSRepository;

import java.util.Map;

public class Handler implements RequestHandler<Map<String, Object>, String> {

    @Override
    public String handleRequest(Map<String, Object> event, Context context) {
        LogHelper.init(context.getLogger());
        LogHelper.getLogger().log("RequestIn: " + event.toString());
        try {
            if (event.get("body") != null) {
                RequestIn requestIn = new ObjectMapper().readValue((String) event.get("body"), RequestIn.class);
                LogHelper.getLogger().log(requestIn.toString());
                return new ObjectMapper().writeValueAsString(
                        new RDSRepository()
                                .createUser(requestIn.getUid(), requestIn.getName(), requestIn.getLanguage())
                );
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            LogHelper.getLogger().log(e.getMessage());
            return null;
        }
    }

}
