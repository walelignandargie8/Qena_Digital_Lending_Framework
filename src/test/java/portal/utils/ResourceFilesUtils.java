package portal.utils;

import com.codeborne.selenide.Selenide;
import portal.constants.LogsConstant;
import portal.constants.PathConstants;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

public class ResourceFilesUtils {
    public static final String PATH = "csv/";
    public static final int RANDOM_NUMBER_RANGE = 5000;
    public static Logger logger = (Logger) ScenarioContext.getContext(LogsConstant.LOGGER);

    public static File getCSVFile(String fileName) {
        String path = PATH + fileName;
        ClassLoader classLoader = ResourceFilesUtils.class.getClassLoader();
        File file = new File(classLoader.getResource(path).getFile());
        logger.info("File found :" + file.exists());
        return file;
    }

    /**
     * Method to get a Unique CSV file
     *
     * @return new File
     */
    public static File getUniqueCSVFile() {
        String newFileName = "RecipientsAuto_" + IntUtils.getRandomNumber(RANDOM_NUMBER_RANGE) + ".csv";
        File source = getCSVFile("RecipientsAuto.csv");
        Path path = Paths.get(source.getPath());
        String directory = path.getParent().toString();
        File newFileCSV = new File(directory + "\\" + newFileName);
        try {
            Files.copy(source.toPath(), newFileCSV.toPath());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        logger.info("Created the CSV file : " + newFileName);
        return newFileCSV;
    }

    /**
     * Method to update the existing CSV file
     * @param csvFile CSV file
     * @param indexRow index of the Row
     * @param  indexColumn index of the Column
     * @param  value value to update in the CSV
     * @return updated CSV file
     */
    public static File updateCSVFile(File csvFile, int indexRow, int indexColumn, String value) {
        try {
            CSVReader reader = new CSVReader(new FileReader(csvFile));
            List<String[]> csvBody = reader.readAll();
            csvBody.get(indexRow)[indexColumn] = value;
            reader.close();
            CSVWriter writer = new CSVWriter(new FileWriter(csvFile));
            writer.writeAll(csvBody);
            writer.flush();
            writer.close();
        } catch (IOException exception) {
            logger.warning(exception.getMessage());
        } catch (CsvException exception) {
            logger.warning(exception.getMessage());
        }
        return csvFile;
    }
    /**
     * Method to search file in directory and sub-directories
     *
     * @param fileName filename
     * @return the File if found
     */
    public static File searchFile(String fileName) {
        Selenide.sleep(3000); // time to wait until the CSV file is downloaded
        String pathDownloads = PathConstants.PATH_DOWNLOADS.replace("/", "\\");
        String winDir = System.getProperty("user.dir");
        String fullPath = winDir + "\\" + pathDownloads;
        File root = new File(fullPath);
        File csv = null;
        try {
            boolean recursive = true;

            Collection files = FileUtils.listFiles(root, null, recursive);

            for (Iterator iterator = files.iterator(); iterator.hasNext(); ) {
                File file = (File) iterator.next();
                if (file.getName().equals(fileName)) {
                    System.out.println(file.getAbsolutePath());
                    csv = file;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.warning("There was an issue when trying to search the file: " + fileName);
            logger.warning("Error from the exception: " + e.getMessage());
        }
        if (csv != null) {
            logger.info("The file was found : " + fileName);
        } else {
            logger.info("The file was NOT found : " + fileName);
        }
        return csv;
    }
    /**
     * Method to get the file as string
     *
     * @param file file
     * @return string
     * @throws IOException
     */
    public static String getFileAsString(File file) throws IOException {
        return new String(Files.readAllBytes(file.getAbsoluteFile().toPath()));
    }
}
