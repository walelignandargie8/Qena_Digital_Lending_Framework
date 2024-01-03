package portal.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class TestDataReader {

    public static final String PATH_TEST_DATA = "testdata/";

    /**
     * Method to read test data from YAML file
     *
     * @param fileName file name
     * @param objectClass type of Object to deserialize from YAML to Object
     * @param <T> return type of Object to deserialize from YAML to Object
     * @return the Objects
     */
    public static <T> T readData(String fileName, Class<T> objectClass) {
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            File file = new File(Objects.requireNonNull(classLoader.getResource(PATH_TEST_DATA + fileName)).getFile());
            ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
            return objectMapper.readValue(file, objectClass);
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
        return null;
    }
}
