package de.kontaktbuch;

import java.util.List;
import de.kontaktbuch.model.Kontakt;
import de.kontaktbuch.legacy.CsvReader;
import de.kontaktbuch.legacy.JsonReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;

/**
 * A simple contact book view application that reads contact information from a specified file (CSV or JSON)
 * and prints it to the console. The file path is provided as a command-line argument using the
 * --import option. The program supports two file formats:
 * CSV, where each line contains a contact's full name, call name, and email separated by a semicolon; and
 * JSON, where each contact is represented as a JSON object with "fullName", "callName", and "email"
 *      fields. The program includes error handling for missing or invalid arguments,
 *      unsupported file types, and issues during file reading.
 */
public class App
{

    //#region main

    // --------------------------

    /**
     * This program reads contact information from a specified file (CSV or JSON)
     * and prints it to the console. The file path is provided as a command-line
     * argument using the --import option. The program supports two file formats:
     * CSV, where each line contains a contact's full name, call name, and email
     *    separated by a semicolon; and
     * JSON, where each contact is represented as a JSON object with "fullName",
     *    "callName", and "email" fields. The program includes error handling for
     *    missing or invalid arguments, unsupported file types, and issues during
     *    file reading.
     */
    public static void
    main(String[] args)
    {
        String        filePath = parseArgs(args);
        if (filePath == null) return;

        String        fileType = getFileType(filePath);
        if (fileType == null) {printError("Unknown file type"); return;}

        List<Kontakt> kontakte = new java.util.ArrayList<>();
        if (fileType.equals("csv"))
        {
            List<String[]> lines = CsvReader.read(filePath);
            if (lines == null) {printError("Failed to read CSV file"); return;}

            for (String[] line : lines)
            {
                if (line.length < 4) continue;

                String fullName = line[0];
                String callName = line[1];
                String email    = line[2];
                String birthday = line[3];
                Kontakt kontakt = new Kontakt(fullName, callName, email, birthday);
                kontakte.add(kontakt);
            }
        }
        if (fileType.equals("json"))
        {
            try (InputStream              inputStream = new java.io.FileInputStream(filePath))
            {
                JsonReader                jsonReader  = new JsonReader(inputStream);
                List<Map<String, String>> contacts    = jsonReader.read();
                if (contacts == null) {printError("Failed to read JSON file"); return;}

                for (Map<String, String> contact : contacts)
                {
                    String fullName  = contact.get("fullName");
                    String callName  = contact.get("callName");
                    String email     = contact.get("email");
                    String birthday  = contact.get("birthday");
                    Kontakt kontakt  = new Kontakt(fullName, callName, email, birthday);
                    kontakte.add(kontakt);
                }
            }
            catch (Exception e)
            {
                printError("Error reading JSON file: " + e.getMessage());
                return;
            }
        }

        printKontakte(kontakte);

        return;
    }

    // --------------------------

    //#endregion main

    //#region private static methods

    // --------------------------

    /**
     * Prints the list of contacts to the console. Each contact is printed on a new line
     * using its toString() method. If the list of contacts is null, this method does nothing.
     * @param kontakte The list of contacts to print. May be null.
     */
    private static void
    printKontakte(List<Kontakt> kontakte)
    {
        if (kontakte == null) {printError("No contacts to display"); return;}

        System.out.println("Kontakte:");
        for (Kontakt kontakt : kontakte)
        {
            System.out.println(kontakt);
            if (kontakt.getBirthday() != null && !kontakt.getBirthday().isEmpty())
            {
                java.time.LocalDate today    = java.time.LocalDate.now();
                java.time.LocalDate birthday = java.time.LocalDate.parse(kontakt.getBirthday());
                if (today.getMonth() == birthday.getMonth() && today.getDayOfMonth() == birthday.getDayOfMonth())
                {
                    System.out.println("  -> Heute hat " + kontakt.getCallName() + " Geburtstag!");
                }
            }
        }
    }

    // --------------------------

    /**
     * Prints an error message to the console and returns an error code.
     * @param message The error message to print.
     * @return The error code (always 1).
     */
    private static int
    printError(String message)
    {
        System.out.println("Error: " + message);
        return 1;
    }

    // --------------------------

    /**
     * Extracts the file type (extension) from the given file path. The file type is
     * determined by the substring after the last dot ('.') in the file path.
     * The returned file type is converted to lowercase. If the file path is null, empty,
     * or does not contain a dot, this method returns null.
     * @param filePath The file path from which to extract the file type.
     * @return The file type (extension) in lowercase, or null if the file path is invalid.
     */
    private static String
    getFileType(String filePath)
    {
        if (filePath == null) return null;
        if (filePath.isEmpty()) return null;

        int dotIndex  = filePath.lastIndexOf('.');
        if (dotIndex == -1) return null;

        String result = filePath.substring(dotIndex + 1);
        result        = result.toLowerCase();
        return result;
    }

    // --------------------------

    /**
     * Parses the command-line arguments to extract the file path for importing contacts.
     * The method looks for the --import option followed by a file path. It also handles
     * --help and --version options. If the arguments are valid and contain the --import
     * option, it returns the specified file path. If the arguments are invalid, missing,
     * or if an error occurs during parsing, this method returns null.
     * @param args The command-line arguments to parse.
     * @return The file path specified after the --import option, or null if the arguments are invalid.
     */
    private static String
    parseArgs(String[] args)
    {
        if (args == null) return null;

        int index = -1;
        for (String arg : args)
        {
            index++;
            if (arg == null) continue;
            if (arg.isEmpty()) continue;

            switch (arg)
            {
                case "--help":
                case "-h":
                    printHelp();
                    return null;
                case "--version":
                case "-v":
                    System.out.println("Kontaktbuch version 1.0.0");
                    return null;
                case "--import":
                    String filePath  = null;
                    if (args.length <= index + 1)
                    {
                        System.out.println("Error: Missing file path for import");
                        printHelp();
                        return null;
                    }
                    filePath = args[index + 1];
                    return filePath;
                default:
                    System.out.println("Unknown argument: " + arg);
                    printHelp();
                    return null;
            }
        }

        System.out.println("Error: No arguments provided");
        printHelp();
        return null;
    }

    // --------------------------

    /**
     * Prints the help message to the console.
     * @return Always returns true.
     */
    private static boolean
    printHelp()
    {
        System.out.println("Usage: java -jar kontaktbuch.jar [options]");
        System.out.println("Options:");
        System.out.println("  --help, -h          Show this help message and exit");
        System.out.println("  --version, -v       Show version information and exit");
        System.out.println("  --import <file> Import contacts from a file");
        return true;
    }

    // --------------------------

    //#endregion private static methods

}

// --[ EOF ]--