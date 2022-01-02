package edu.upc.etsetb.arqsoft.multispreadsheet.functional;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import edu.upc.etsetb.arqsoft.multispreadsheet.functional.exceptions.NoReadAccessException;
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
    public static File ensurePathWrite(String path) throws NoWriteAccessException, IOException {
        File file = new File(path);
        if (!file.exists()) {
            file.createNewFile();
        }
        if (!file.canWrite()) {
            throw new NoWriteAccessException(path);
        }
        return file;
    }

    public static File ensurePathRead(String path) throws NoReadAccessException {
        File file = new File(path);
        if (!file.exists() || !file.canRead()) {
            throw new NoReadAccessException(path);
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

    /**
     * Method that writes a string into a file
     * 
     * @param file
     * @param fileContent
     * @throws FileNotFoundException
     */
    public static List<String> readFrom(File file) throws FileNotFoundException {
        List<String> rows = new LinkedList<String>();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                rows.add(scanner.nextLine());
            }
        }
        return rows;
    }
}
