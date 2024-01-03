package portal.api.services;

import portal.database.queries_executor.ApplicationsQueriesExecutor;
import portal.database.queries_executor.ContentQueriesExecutor;
import portal.models.users.User;
import portal.models.users.Users;
import portal.utils.UserUtils;


public class ContentService extends GenericService<ContentService> {

    public static final String ROOT_APP_NAME = "E2E Group";
    public static final String QA_AUTOMATION_GROUP = "QA Automation Group";
    public User user;
    public ApplicationsQueriesExecutor applicationsQueriesExecutor;
    public ContentQueriesExecutor contentQueriesExecutor;

    public static ContentService getContentService() {
        return new ContentService();
    }

    public ContentService() {
        Users users = UserUtils.getUsers();
        user = users.getUserGivenRole("Admin");
        applicationsQueriesExecutor = ApplicationsQueriesExecutor.getApplicationsScriptExecutor();
        contentQueriesExecutor = ContentQueriesExecutor.getContentQueriesExecutor();
    }
}