package portal.utils;

public class MonacoUtils {

    public static String replaceHTMLTags(String contentValue){
        String htmlTagsToRemove = "\\<.*?\\>";
        return contentValue.replaceAll(htmlTagsToRemove, "");
    }
}