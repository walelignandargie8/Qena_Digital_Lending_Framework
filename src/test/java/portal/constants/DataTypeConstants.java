package portal.constants;

import java.util.HashMap;
import java.util.Map;

public class DataTypeConstants {

    public static Map<String, Integer> VALUE;

    static {
        VALUE = new HashMap<>();
        VALUE.put("String", 1);
        VALUE.put("Boolean", 2);
        VALUE.put("Integer", 3);
        VALUE.put("Decimal", 4);
        VALUE.put("Date", 5);
        VALUE.put("Json", 6);
        VALUE.put("StringEnum", 7);
    }
}
