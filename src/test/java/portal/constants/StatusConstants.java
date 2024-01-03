package portal.constants;

import java.util.HashMap;
import java.util.Map;

public class StatusConstants {

    public static Map<String, Integer> VALUE;
    static {
        VALUE = new HashMap<>();
        VALUE.put("Active", 1);
        VALUE.put("Inactive", 0);
    }
}
