package portal.database.queries;

public class ContentQueries {

    public static final String QUERY_REMOVE_CONTENT_GROUP_BY_NAME =
            "DELETE FROM CxsContent..ContentGroups " +
            "WHERE Name = '%s'";

    public static final String QUERY_GET_CONTENT_PROPERTIES =
            "SELECT TOP 1 * " +
            "FROM CxsContent..Content " +
            "WHERE Name = '%s'";

    public static final String QUERY_DELETE_APPLICATION_CONTENT =
            "DELETE FROM CxsContent..ApplicationContent " +
            "WHERE ContentId = %s";

    public static final String QUERY_DELETE_APPLICATION_CONTENT_DESCRIPTION =
            "DELETE FROM CxsContent..ApplicationContentDescriptions " +
            "WHERE ContentId = %s";

    public static final String QUERY_DELETE_CONTENT =
            "DELETE FROM CxsContent..Content " +
            "WHERE Id  = %s";

    public static final String QUERY_GET_CONTENT_GROUP_WITH_NAME =
            "SELECT TOP 1 *\n" +
            "FROM [CxsContent].[dbo].[ContentGroups]\n" +
            "WHERE Name = '%s'";

    public static final String QUERY_DELETE_CONTENT_REFERENCES =
            "DELETE FROM CxsContent..ApplicationContentReference\n" +
            "WHERE ReferencedContentId = %s";

    public static final String REMOVE_APPLICATION_CONTENT_REFERENCES =
            "delete from CxsContent.dbo.ApplicationContentReference\n" +
            "where ReferencedContentId in \n" +
            "(\n" +
            "\tselect Id\n" +
            "\tfrom CxsContent.dbo.Content\n" +
            "\twhere ContentGroupId in \n" +
            "\t(\n" +
            "\t\tselect  id\n" +
            "\t\tfrom CxsContent.dbo.ContentGroups\n" +
            "\t\twhere name ='%s'\n" +
            "\t)\n" +
            ")";

    public static final String REMOVE_APPLICATION_CONTENTS =
            "delete from CxsContent.dbo.ApplicationContent\n" +
            "where ContentId in \n" +
            "(\n" +
            "\tselect Id\n" +
            "\tfrom CxsContent.dbo.Content\n" +
            "\twhere ContentGroupId in \n" +
            "\t(\n" +
            "\t\tselect  id\n" +
            "\t\tfrom CxsContent.dbo.ContentGroups\n" +
            "\t\twhere name = '%s'\n" +
            "\t)\n" +
            ") ";

    public static final String REMOVE_APPLICATION_CONTENT_DESCRIPTIONS =
            "delete from CxsContent.dbo.ApplicationContentDescriptions\n" +
            "where ContentId in \n" +
            "(\n" +
            "\tselect Id\n" +
            "\tfrom CxsContent.dbo.Content\n" +
            "\twhere ContentGroupId in \n" +
            "\t(\n" +
            "\t\tselect  id\n" +
            "\t\tfrom CxsContent.dbo.ContentGroups\n" +
            "\t\twhere name = '%s'\n" +
            "\t)\n" +
            ")";


    public static final String REMOVE_CONTENTS =
            "delete from CxsContent.dbo.Content\n" +
            "where ContentGroupId in \n" +
            "(\n" +
            "\tselect  id\n" +
            "\tfrom CxsContent.dbo.ContentGroups\n" +
            "\twhere name = '%s'\n" +
            ") ";

}