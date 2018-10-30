package fi.archon.codesmellsdetector;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

/**
 *
 * Displays code smells from a Java project using CodeSmellsDetector class.
 * Smell name, where it was found and row number are printed
 *
 * @author jojares
 */
public class Main {

    /**
     *
     * @param args Full path to the project root directory where *.java source
     * files are found
     */
    public static void main(String[] args) {

        if (args.length > 0) {
            String projectRootDirectory = args[0];

            try {

                CodeSmellsDetector detector = new CodeSmellsDetector(Paths.get(projectRootDirectory));
                List<String> detectedSmells = detector.detectSmells();

                for (String smell : detectedSmells) {
                    System.out.println(smell);
                }
            } catch (FileNotFoundException e) {
                System.out.println("File not found: " + projectRootDirectory);
                System.out.println(e.getCause().toString() + ": " + e.getMessage());
            } catch (IOException ie) {
                System.out.println("I/O Error: " + projectRootDirectory);
                System.out.println(ie.getCause().toString() + ": " + ie.getMessage());
            }
        }
        else {
            System.out.println("Argument missing. You need to give project root directory as argument");
        }
    }
}
