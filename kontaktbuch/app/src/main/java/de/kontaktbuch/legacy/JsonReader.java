package de.kontaktbuch.legacy;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class provides functionality to read JSON data from an input stream and
 * parse it into a list of maps, where each map represents a JSON object with
 * string keys and values. It uses the Jackson library for JSON parsing.
 * The input stream is expected to contain JSON objects, one per line. Each line
 * is parsed independently, and any parsing errors are handled gracefully by
 * skipping the problematic line and continuing with the next one. The class
 * ensures that resources are properly managed by closing the reader after use.
 */
public class JsonReader
{

    // #region vars

    // --------------------------

    /**
     * The input stream from which to read JSON data. This stream
     * is expected to contain JSON objects, one per line. It must not be null.
     */
    private final InputStream  inputStream;

    /**
     * The ObjectMapper instance used for parsing JSON data.
     * This is a thread-safe class provided by the Jackson library, which
     * allows for converting JSON strings into Java objects and vice versa.
     * It is initialized once and reused for all parsing
     * operations in this class.
     */
    private final ObjectMapper mapper      = new ObjectMapper();

    // --------------------------

    // #endregion vars

    // #region ctor

    // --------------------------

    /**
     * Initializes a new instance of the JsonReader
     * class with the specified input stream.
     * @param inputStream The input stream from which to
     *                    read JSON data. Must not be null.
     */
    public
    JsonReader(InputStream inputStream)
    {
        this.inputStream = inputStream;
    }

    // --------------------------

    // #endregion ctor

    // #region private methods

    // --------------------------

    /**
     * Parses a single line of JSON data and returns it as
     * a map of string keys and values.
     * @param line The JSON string to parse.
     * @return A map representing the JSON object, or null
     *         if the input line is null, empty, or if an
     *         error occurs during parsing.
     */
    private Map<String, String>
    readCurrentLine(String line)
    {
        if (line == null) return null;
        if (line.isEmpty()) return null;

        TypeReference<Map<String, String>>
                            typeRef = new TypeReference<Map<String, String>>(){};
        Map<String, String> result  = null;
        try
        {
            result = mapper.readValue(line, typeRef);
            return result;
        }
        catch (IOException e)
        {
            return null;
        }
    }

    // --------------------------

    // #endregion private methods

    // #region public methods

    // --------------------------

    /**
     * Reads the JSON data from the input stream and returns a list of maps,
     * where each map represents a JSON object with string keys and values.
     * @return A list of maps representing the JSON data,
     *         or null if the input stream is null or an error
     *         occurs during reading.
     */
    public List<Map<String, String>>
    read()
    {
        if (inputStream == null) return null;

        List<Map<String, String>> result = new ArrayList<>();
        BufferedReader            reader = null;
        String                    line   = null;

        try
        {
            reader = new BufferedReader(new InputStreamReader(inputStream));

            while ((line = reader.readLine()) != null)
            {
                Map<String, String> map = readCurrentLine(line);
                if (map == null) continue;

                result.add(map);
            }
        }
        catch (Exception e) { }

        try
        {
            if (reader != null) reader.close();
        }
        catch (Exception ignored) { }

        return result;
    }

    // --------------------------

    // #endregion public methods

}

// --[ EOF ]--