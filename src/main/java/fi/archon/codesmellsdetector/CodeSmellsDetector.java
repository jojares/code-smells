package fi.archon.codesmellsdetector;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * Detects Code Smells using JavaParser's AST to navigate the source code. Only
 * Long Parameter List smell is currently supported (1st implementation)
 *
 * @author jojares
 */
public class CodeSmellsDetector {

    private List<CompilationUnit> compilationUnits = new ArrayList<CompilationUnit>();
    final static Logger log = LogManager.getLogger(CodeSmellsDetector.class);

    /**
     * Constructor prepares the AST for detection. DetectSmells() method needs
     * to be invoked to get the list of detected smells.
     *
     * @param projectRootDirectory
     * @throws FileNotFoundException
     * @throws IOException
     */
    public CodeSmellsDetector(Path projectRootDirectory) throws FileNotFoundException, IOException {

        log.info("Parsing source files from " + projectRootDirectory.toUri());

        List<Path> sourceFiles = listSourceFiles(projectRootDirectory);

        for (Path sourceFile : sourceFiles) {
            FileInputStream in = new FileInputStream(sourceFile.toFile());
            log.debug("Parsing source file: " + sourceFile.toUri());
            CompilationUnit cu = JavaParser.parse(in);
            this.compilationUnits.add(cu);
        }
    }

    /**
     * Detects Long Parameter List smells from source code.
     *
     * @return Results are appended as a list of strings that can be displayed
     * for the user.
     */
    public List<String> detectSmells() {

        LongParameterListSmell longParameterListSmell = new LongParameterListSmell();
        List<String> detectedSmells = new ArrayList<>();

        for (CompilationUnit cu : this.compilationUnits) {

            String result = cu.accept(longParameterListSmell, null);

            if (result != null) {
                detectedSmells.add(result);
            }
        }
        return detectedSmells;
    }

    private List<Path> listSourceFiles(Path projectRootDir) throws IOException {

        List<Path> sourceFiles = new ArrayList<>();

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(projectRootDir)) {
            for (Path entry : stream) {

                if (Files.isDirectory(entry)) {
                    sourceFiles.addAll(listSourceFiles(entry));
                } else if (entry.toString().endsWith(".java")) {
                    sourceFiles.add(entry);
                }
            }

        } catch (DirectoryIteratorException ex) {

            throw ex.getCause();
        }
        return sourceFiles;
    }
}
