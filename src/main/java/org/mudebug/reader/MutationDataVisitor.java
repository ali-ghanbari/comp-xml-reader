package org.mudebug.reader;

public interface MutationDataVisitor {
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
                              String[] coveringTests);
}
