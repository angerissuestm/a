This is a simple contact book application that reads contact information from a specified file (CSV or JSON) and prints it to the console. The file path is provided as a command-line argument using the --import option. The program supports two file formats: CSV, where each line contains a contact's full name, call name, and email separated by a semicolon; and JSON, where each contact is represented as a JSON object with "fullName", "callName", and "email" fields. The program includes error handling for missing or invalid arguments, unsupported file types, and issues during file reading. The contact information is stored in a list of Kontakt objects, which are then printed to the console. The application uses a legacy JsonReader class to parse JSON files and a simple CSV reader for CSV files.

## Usage:
1. Compile the Java application. With
```bash
./gradlew build
```
2. Run the application with the --import option followed by the path to the contact file:
```bash
./gradlew run --args="--import path/to/contacts.csv"
```
or
```bash
./gradlew run --args="--import path/to/contacts.json"
```
## Structure:
You can find the source code in the `app/src/main/java/de/kontaktbuch` directory. The main files are:
- `App.java`: The main application class that handles command-line arguments, reads contact information from files, and prints the contacts.
- `Kontakt.java`: A simple class representing a contact with full name, call name, and email.
- `JsonReader.java`: A legacy class for reading and parsing JSON files into a list of maps.
- `CSVReader.java`: A simple class for reading CSV files and returning a list of string arrays.

Some example contact files are included in the project root:
- `kontakte.csv`: An example CSV file containing contact information.
- `kontakte.json`: An example JSON file containing contact information.

## Practice:
### Structural Patterns:
 - Download the kontaktbuch from Stud.IP
 - Install Java and IDE (VSCode recommended)
 - User Story 1:
   - AK1: Introduce a unified way to load contacts regardless of the file format. The main program should not need to know how the data is read.
   - AK2: Refactor the code so that adding a new file type requires minimal changes
   - AK3: Avoid modifying existing logic when introducing new formats
### Creational Patterns:
 - User Story 2:
   - AK1: The application currently creates Kontakt objects directly in multiple places. Move the creation of Kontakt objects into dedicated classes. Avoid direct instantiation (new Kontakt(...)) in business logic.
   - AK2: Ensure that all objects needed for a specific format are created in a consistent way
   - AK3: Ensure that the instantiation logic can be easily replaced or extended in the future without modifying the adapter
### Behavioral Patterns:
 - User Story 3:
   - AK1: The application should react to certain events during contact creation (logging created contacts, triggering on specific conditions)
   - AK2: Print a warning on pepole they have their birthday today