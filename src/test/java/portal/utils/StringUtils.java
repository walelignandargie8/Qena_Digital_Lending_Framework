package portal.utils;

import net.ricecode.similarity.JaroWinklerStrategy;
import net.ricecode.similarity.SimilarityStrategy;
import net.ricecode.similarity.StringSimilarityService;
import net.ricecode.similarity.StringSimilarityServiceImpl;
import org.apache.commons.io.FilenameUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Selenide.sleep;

public class StringUtils {

    public static final String WHITE_SPACE_ENCODED_URL = "%20";
    public static final String URL_START_PATH = "file:\\";

    /**
     * Method to verify if a String is Null or Empty
     *
     * @param value String value
     * @return true if the value is null or empty otherwise will return false
     */
    public static boolean IsNullOrEmpty(String value) {
        return value == null || value.isEmpty();
    }

    /**
     * This method calculates a normalized distance or similarity score between two strings.
     * Please see https://github.com/rrice/java-string-similarity for mor information.
     *
     * @param source source
     * @param target target
     * @return A score of 0.0 means that the two strings are absolutely dissimilar,
     * and 1.0 means that absolutely similar (or equal). Anything in between indicates how similar each the two strings are.
     */
    public static double getSimilarityScore(String source, String target) {
        SimilarityStrategy strategy = new JaroWinklerStrategy();
        StringSimilarityService service = new StringSimilarityServiceImpl(strategy);
        return service.score(target, source);
    }

    /**
     * Method to get String between a range
     *
     * @param value String value
     * @param init  Initial Range of String
     * @param end   End Range of String
     * @return the StringString between a range
     */
    public static String getStringBetweenRange(String value, int init, String end) {
        return value.substring(init, value.indexOf(end));
    }

    /**
     * Method to generate the UUID
     *
     * @return UUID
     */
    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * Method to get an Image as array of Bytes
     *
     * @param path Path of the Image
     * @return the array of bytes of the Image
     */
    public static byte[] getImageAsArrayOfBytes(String path) {
        try {
            BufferedImage bImage = ImageIO.read(new File(path));
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(bImage, "png", bos);
            return bos.toByteArray();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    /**
     * Method to get the HTML source of the Page
     *
     * @param path path
     * @return the array of bytes of the HTML Page
     */
    public static byte[] getDocumentAsArrayOfBytes(String path) {
        try {
            return Files.readAllBytes(Paths.get(path).toAbsolutePath());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    /**
     * Method to verify if the image is present in the file system
     *
     * @param path Path of the file
     * @return true if the file exist otherwise return false
     */
    public static boolean verifyIfImageExist(String path) {
        int attempts = 10;
        int counter = 1;
        if (!new File(path).exists()) {
            do {
                sleep(1000);
                System.out.println("Waiting for screenshot..." + counter);
                attempts--;
                counter++;
            }
            while (!new File(path).exists() && attempts > 0);
            if (!new File(path).exists()) {
                System.out.println("Screenshot not available");
                return false;
            } else {
                System.out.println("Screenshot created!!!...");
                return true;
            }
        } else {
            System.out.println("Screenshot created!!!...");
            return true;
        }
    }

    /**
     * Method to validate if the filename is correct
     *
     * @param fileName Filename
     * @return the filename without special characters
     */
    public static String validateFileName(String fileName) {
        return fileName.replaceAll("[\\\\/:*?\"<>|]", "");
    }

    /**
     * Method to convert the path to windows path
     *
     * @param path windows path
     * @return path
     */
    public static String convertToWindowsPath(String path) {
        if (path.contains(WHITE_SPACE_ENCODED_URL)) {
            path = FilenameUtils.separatorsToSystem(path);
            path = path.replaceAll(WHITE_SPACE_ENCODED_URL, " ");
            path = path.replace(URL_START_PATH, "");
        }
        return path;
    }

    /**
     * Method to replace non break space (   ) by spaces
     *
     * @param textWithNonBreakSpace the text that has non break space special characters
     * @return the text with spaces
     */
    public static String replaceNonBreakSpace(String textWithNonBreakSpace) {
        return textWithNonBreakSpace.replaceAll("\\u00a0", " ");
    }

    /**
     * Method to convert from list of HashMap  to List of String
     */
    public static ArrayList<String> getListOfStringFromListOfHashMap(List<HashMap<String, Object>> hashMapList, String filteredColumnName) {
        ArrayList<String> list = new ArrayList<>();

        for (HashMap<String, Object> aHashMap : hashMapList) {
            String value = aHashMap.entrySet().stream()
                    .filter(x -> filteredColumnName.equals(x.getKey()))
                    .map(x -> (String) x.getValue())
                    .collect(Collectors.joining());
            list.add(value);

        }
        return list;
    }

    public static String decodeURL(String connectEndpoint) {
        try {
            String result = java.net.URLDecoder.decode(connectEndpoint, StandardCharsets.UTF_8.name());
            return result;
        } catch (UnsupportedEncodingException e) {
            // not going to happen - value came from JDK's own StandardCharsets
        }
        return null;
    }

    public static String encodeURL(String connectEndpoint) {
        try {
            String result = java.net.URLEncoder.encode(connectEndpoint, StandardCharsets.UTF_8.name());
            return result;
        } catch (UnsupportedEncodingException e) {
            // not going to happen - value came from JDK's own StandardCharsets
        }
        return null;
    }

    /**
     * Method to generate random string given length
     *
     * @param stringLength String length
     * @return Random String
     */
    public static String generateAlphabeticString(int stringLength) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .limit(stringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    /**
     * Method to generate a random string which contains alphanumeric and special character
     *
     * @param stringLength         String length
     * @param specialCharacterList List of special characters
     * @return Random string
     */
    public static String generateStringWithSpecialCharacter(String specialCharacterList, int stringLength) {
        String characters = "abcdefghijklmnopqrstuvwxyz" + specialCharacterList;
        Random random = new Random();
        StringBuffer randomString = new StringBuffer();
        for (int i = 0; i < stringLength; i++) {
            randomString.append(characters.charAt(random.nextInt(characters.length())));
        }
        return randomString.toString();
    }

    /**
     * Method to compare two string and get the different value
     *
     * @param fistString   first string
     * @param secondString second string
     * @return difference
     */
    public static String compareStringAndGetDifference(String fistString, String secondString) {
        String difference = "";
        for (int i = 0; i < fistString.length(); i++) {
            if (!Objects.equals(fistString.charAt(i), secondString.charAt(i))) {
                difference += fistString.charAt(i);
            }
        }
        return difference;
    }
    /**
     * Method to do sorting dates ascending or descending order
     *
     * @param listValues collection of dates
     * @param order      order type e.g. ascending or descending
     * @return collection of dates sorted
     */
    public static List<String> sortValues(List<String> listValues, String order) {
        if (listValues.size() > 0) {
            Collections.sort(listValues, new Comparator<String>() {
                final DateFormat formatData = new SimpleDateFormat("MMM dd, yyyy");

                @Override
                public int compare(String object1, String object2) {
                    try {
                        if (order.contains("descending")) {
                            return formatData.parse(object2).compareTo(formatData.parse(object1));
                        } else {
                            return formatData.parse(object1).compareTo(formatData.parse(object2));
                        }
                    } catch (ParseException e) {
                        throw new IllegalArgumentException(e);
                    }
                }
            });
        }
        return listValues;
    }

    /**
     * Method to change a string to lower cased
     *
     * @param name string to replace
     * @return lowered case string
     */
    public static String toLowerCase(String name) {
        return name.toLowerCase().replaceAll(" ", "_");
    }

    /**
     * Method to do sorting a collection of strings by adding special characters at the beginning
     * (The app is doing this kind of sorting, so this method is useful for testing grids that are using this sorting)
     *
     * @param collectionOfStrings collection of strings
     * @return collection of strings sorted
     */
    public static List<String> customSortList(List<String> collectionOfStrings) {
        String temp;
        for (int j = 0; j < collectionOfStrings.size() - 1; j++) {
            for (int i = j + 1; i < collectionOfStrings.size(); i++) {
                if (compareStrings(collectionOfStrings.get(j), collectionOfStrings.get(i)) > 0) {
                    temp = collectionOfStrings.get(j);
                    collectionOfStrings.set(j, collectionOfStrings.get(i));
                    collectionOfStrings.set(i, temp);
                }
            }
        }
        return collectionOfStrings;
    }

    /**
     * Method to compare two strings character by character in order to see which one becomes first
     *
     * @param str1 first string
     * @param str2 second string
     * @return if the comparison is greater than Zero (0) then the second string becomes first
     */
    public static int compareStrings(String str1, String str2) {
        for (int i = 0; i < str1.length() && i < str2.length(); i++) {
            if ((int) str1.charAt(i) == (int) str2.charAt(i)) {
                continue;
            } else {
                return (int) str1.charAt(i) - (int) str2.charAt(i);
            }
        }

        if (str1.length() < str2.length()) {
            return (str1.length() - str2.length());
        } else if (str1.length() > str2.length()) {
            return (str1.length() - str2.length());
        } else {
            return 0;
        }
    }

    /**
     * Method to remove the \n, \t and spaces between the strings
     *
     * @param list list of strings
     * @return list of strings without spaces
     */
    public static List<String> removeSpacesBetweenStrings(List<String> list) {
        return list.stream()
                .map(attribute -> attribute.replaceAll("\\s+", ""))
                .collect(Collectors.toList());
    }

    /**
     * Method to replace special characters in the provided template
     *
     * @param template template
     * @return template after replaced
     */
    public static String replaceHTMLCharactersOfEmailTemplates(String template) {
        return template.replaceAll("\n", "").replaceAll("\t", "")
                .replaceAll("<p>", "").replaceAll("</p>", "").replaceAll(";", "")
                .replaceAll("&amp", "&").replaceAll("&nbsp", " ")
                .replaceAll("%3a", ":").replaceAll("%2f", "/");
    }

    /**
     * Capitalizes the first letter of a given word and converts the remaining letters to lowercase.
     *
     * @param word The input word to be capitalized.
     * @return A new String with the first letter in uppercase and the rest in lowercase.
     *
     *
     * @example
     * {@code
     * String result = capitalizeWord("hello"); // Returns "Hello"
     * String result2 = capitalizeWord("WORLD"); // Returns "World"
     * }
     */
    public static String capitalizeWord(String word) {
        return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
    }
}