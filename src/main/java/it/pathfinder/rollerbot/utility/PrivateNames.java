package it.pathfinder.rollerbot.utility;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Data
@Configuration
public class PrivateNames {

    @Value("${private.var}")
    private String privateVariablesString;

    @Value("${private.command}")
    private String privateCommandString;

    private final Map<String, String> privateVariableMap = readProperties(privateVariablesString);
    private final Map<String, String> privateCommandMap  = readProperties(privateCommandString);

    private Map<String, String> readProperties(String uglyString)
    {
        Map<String, String> temp = new HashMap<>();
        Arrays.stream(uglyString.split(",")).forEach(key -> temp.put(key, key));
        return temp;
    }

}
