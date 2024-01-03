package portal.api.endpoints;

public class CommunicationFileEndpoints {

    public static final String UPLOAD_CSV_FILE = "application/{applicationKey}/file";
    public static final String GET_CSV_FILES = "application/{applicationKey}/file";
    public static final String UPDATE_CSV_FILE = "application/{applicationKey}/file/{fileName}";
    public static final String GET_CSV_FILE_CONTENT = "/communication-management/application/{applicationKey}/file/{fileName}/content";

}
