package portal.utils;

import java.util.HashMap;

public class TestContext {

    public HashMap<String, Object> testContext;

    public TestContext() {
        testContext = new HashMap<>();
    }

    /**
     * Method to set a value in the context
     *
     * @param key   key reference
     * @param value value
     */
    public void setContext(String key, Object value) {
        testContext.put(key, value);
    }

    /**
     * Method to get the context given the key reference
     *
     * @param key key reference
     * @return value
     */
    public Object getContext(String key) {
        Object objectOnDictionary = null;
        if(this.isContains(key)) {
            objectOnDictionary = testContext.get(key);
        }
        return objectOnDictionary;
    }

    /**
     * Method to verify that test context contains the key reference
     *
     * @param key key reference
     * @return true if the text context contains the key reference otherwise it will return false
     */
    public Boolean isContains(String key) {
        return testContext.containsKey(key);
    }
}