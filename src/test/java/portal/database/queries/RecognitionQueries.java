package portal.database.queries;

public class RecognitionQueries {

    public static final String GET_RECOGNITION_CATEGORY_BY_NAME =
            "SELECT TOP(1) Id FROM [CxsRecognition].[dbo].[RecognitionAssetCategories] WHERE DefaultName = '%s'";

    public static final String GET_RECOGNITION_CATALOG_BY_NAME =
            "SELECT TOP(1) Id FROM [CxsRecognition].[dbo].[RecognitionAssetCatalogs] WHERE Name = '%s' and RecognitionAssetTypeId = %s";

    public static final String DELETE_RECOGNITION_CATEGORY =
            "DELETE FROM [CxsRecognition].[dbo].[RecognitionAssetCategories] WHERE Id = %s";

    public static final String DELETE_RECOGNITION_CATALOG =
            "DELETE FROM [CxsRecognition].[dbo].[RecognitionAssetCatalogs] WHERE Id = %s";

    public static final String GET_PROGRAM_BY_NAME =
            "SELECT TOP(1) Id FROM [CxsRecognition].[dbo].[RecognitionAssetPrograms] WHERE Name = '%s'";

    public static final String DELETE_PROGRAM_BY_Id =
            "DELETE FROM [CxsRecognition].[dbo].[RecognitionAssetPrograms] WHERE Id = %s";

    public static final String ASSIGN_RANDOM_CATEGORY_ECARDS =
            "UPDATE [CxsRecognition].[dbo].[RecognitionAssets]\n" +
                    "SET RecognitionAssetCategoryId = (SELECT TOP 1 Id FROM [CxsRecognition].[dbo].[RecognitionAssetCategories] " +
                    "ORDER BY NEWID())\n" +
                    "WHERE RecognitionAssetCategoryId = (" +
                    "SELECT Id FROM [CxsRecognition].[dbo].[RecognitionAssetCategories] WHERE DefaultName = '%s')";

    public static final String DELETE_RECOGNITION_ASSET_BY_CATALOG =
            "DELETE FROM [CxsRecognition].[dbo].[RecognitionAssets] WHERE RecognitionAssetCatalogId = %s";

    public static final String REMOVE_LINK_BETWEEN_CATEGORY_AND_CATALOG =
            "DELETE FROM [CxsRecognition].[dbo].[FeaturedCategories] WHERE RecognitionAssetCatalogId = %s";
}