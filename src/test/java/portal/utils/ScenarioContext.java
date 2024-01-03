package portal.utils;


import java.util.HashMap;
import java.util.Map;

public class ScenarioContext {

    public static Map<String, Object> scenarioContext = new HashMap<>();

    public static void setContext(String key, Object value) {
        scenarioContext.put(key, value);
    }

    public static Object getContext(String key) {
        return scenarioContext.get(key);
    }

    public static Boolean Contains(String key) {
        return scenarioContext.containsKey(key);
    }
}
