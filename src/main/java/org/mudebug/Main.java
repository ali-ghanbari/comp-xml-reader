package org.mudebug;

import org.mudebug.reader.MutationDataReader;
import org.mudebug.reader.MutationDataVisitor;

import java.io.File;

public class Main implements MutationDataVisitor {
    public static void main(String[] args) {
        final String xmlFileName = args[0]; // TODO: XML FILE NAME
        final Main visitor = new Main();
        final MutationDataReader mdr = new MutationDataReader(new File(xmlFileName), visitor);
        mdr.start();
    }

    @Override
    public void visitMutation(String status,
                              String sourceFile,
                              String mutatedClass,
                              String mutatedMethod,
                              String methodDescription,
                              int lineNumber,
                              int index,
                              double susp,
                              String mutator,
                              String mutationDescription,
                              String[] killingTests,
                              String[] coveringTests) {
        // TODO: FOR EACH XML TAG, THIS FUNCTION WILL BE CALLED
        // AS YOU CAN SEE IT CONTAINS ALL THE INFORMATION YOU NEED
    }
}
