package edu.upc.etsetb.arqsoft.multispreadsheet.functional;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import edu.upc.etsetb.arqsoft.multispreadsheet.functional.exceptions.NoWriteAccessException;

/**
 * Utility class for file system operations.
 * 
 */
public class FileSystemUtils {

    /**
     * Method that ensures that a file exists so we can write into it
     * 
     * @param path
     * @return File
     * @throws NoWriteAccessException
     * @throws IOException
     */
    public static File ensurePath(String path) throws NoWriteAccessException, IOException {
        File file = new File(path);
        if (!file.exists()) {
            file.createNewFile();
        }
        if (!file.canWrite()) {
            throw new NoWriteAccessException(path);
        }
        return file;
    }

    /**
     * Method that writes a string into a file
     * 
     * @param file
     * @param fileContent
     * @throws IOException
     */
    public static void writeInto(File file, String fileContent) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(fileContent);
        }
    }
}
