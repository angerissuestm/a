package de.kontaktbuch.legacy;

import java.util.List;
import java.nio.file.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class provides functionality to read CSV files and
 * return their contents as a list of string arrays.
 * Each array represents a line in the CSV file, split
 * by the defined delimiter.
 */
public class CsvReader
{

    //#region statics

    // --------------------------

    /**
     * Defines the delimiter used in the CSV file. In this case, it's a semicolon (';').
     */
    public static String CSV_DELIMITER = ";";

    // --------------------------

    /**
     * Reads a CSV file and returns a list of string arrays,
     * where each array represents a line split by the
     * defined delimiter.
     * @param path_string The path to the CSV file as a string.
     * @return A list of string arrays, or null if the path is invalid or an error occurs during reading.
     */
    public static List<String[]>
    read(String path_string)
    {
        if (path_string == null) return null;
        if (path_string.isEmpty()) return null;

        List<String[]> result      = new ArrayList<>();
        List<String>   lines       = null;
        Path           csvFilePath = Path.of(path_string);

        if (!Files.exists(csvFilePath)) return null;
        if (!Files.isRegularFile(csvFilePath)) return null;

        try
        {
            lines = Files.readAllLines(csvFilePath);
        }
        catch (Exception e)
        {
            return null;
        }

        for (String line : lines)
        {
            if (line == null) continue;

            String[] parts = line.split(CSV_DELIMITER);
            result.add(parts);
        }

        return result;
    }

    // --------------------------

    //#endregion statics

}

// --[ EOF ]--