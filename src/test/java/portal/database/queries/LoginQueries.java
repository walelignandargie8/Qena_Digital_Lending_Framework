package portal.database.queries;

public class LoginQueries {

    public static final String UPDATE_LINK_EXPIRATION_DATE =
            "UPDATE [CxsIdentity].[dbo].[Tokens]\n" +
                    "SET ExpirationDate = (\n" +
                    "    SELECT\n" +
                    "        CONVERT(\n" +
                    "            DATETIME,\n" +
                    "            SWITCHOFFSET(\n" +
                    "                CONVERT(DATETIMEOFFSET, DATEADD(MINUTE, -10, GETDATE())),\n" +
                    "                DATENAME(TzOffset, SYSDATETIMEOFFSET())\n" +
                    "            )\n" +
                    "        )\n" +
                    ")" +
                    "WHERE [Key] = '%s'";
}