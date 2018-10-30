package fi.archon.codesmellsdetector;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.GenericVisitorAdapter;

/**
 * Detects Long Parameter code smell from a given method declaration. If a method declaration has "too many" parameters it's considered harmful.
 * @author jojares
 */
public class LongParameterListSmell extends GenericVisitorAdapter<String, Void> {

    private int treshold;
    private final int TRESHOLD_DEFAULT_VALUE = 5;

    public LongParameterListSmell() {
        
        this.treshold = TRESHOLD_DEFAULT_VALUE;
    }
    /**
     * 
     * @param treshold Determines how many is "too many" parameters.
     */
    public LongParameterListSmell(int treshold) {

        this.treshold = treshold;
    }
    @Override
    public String visit(MethodDeclaration n, Void arg) {

        super.visit(n, arg);

        int numberOfParameters = n.getParameters().size();

        // Long Parameter List Smell is found when the number of parameters in a method exceeds the treshold value
        if (numberOfParameters > treshold) {
            return createResultString(n);
        } else {
            return null;
        }
    }
    private String getClassNameOfMethod(MethodDeclaration n) {

        String className = n.getParentNode().get().accept(new GenericVisitorAdapter<String, Void>() {

            @Override
            public String visit(ClassOrInterfaceDeclaration c, Void arg) {

                super.visit(n, arg);
                return c.getNameAsString();

            }
        }, null);

        return className;
    }
    private String createResultString(MethodDeclaration n) {

        String name = "Long Parameter List";
        Integer lineWhereFound = n.getBegin().get().line;
        String className = null;

        if (n.getParentNode().isPresent()) {

            className = getClassNameOfMethod(n);
        }

        return (name + " (" + className + "): row# " + lineWhereFound);
    }
}
