package kirstein.source;

import kirstein.uml.UMLClass;
import kirstein.uml.UMLMethod;
import kirstein.uml.UMLVariable;

public interface LanguageDescription {
	/**
	 * For Example:
	 *      "package kirstein.source;"
	 * @return the lines which begin a file
	 */
	public Iterable<String> getFileHeader(SourceFile file) throws LanguageFactoryException;
	/**
	 * For Example:
	 *      "public class Ball{"
	 * @return the lines which start a class or interface
	 */
	public Iterable<String> getClassHeader(UMLClass clazz) throws LanguageFactoryException;
	/**
	 * For Example:
	 *      "private String name;"
	 * @return the lines which declare a variable
	 */
	public Iterable<String> getVariableDeclaration(UMLVariable variable) throws LanguageFactoryException;
	/**
	 * For Example:
	 *      "@Override"
	 *      "public String toString(){"
	 *      "	//TODO"
	 *      "}"
	 * @return the lines which provide a template for a method
	 */	
	public Iterable<String> getMethodTemplate(UMLMethod method) throws LanguageFactoryException;
	/**
	 * For Example:
	 *      "}"
	 * @return the lines which complete a class declaration
	 */
	public Iterable<String> getClassTerminator(UMLClass clazz) throws LanguageFactoryException;
	/**
	 * For Example:
	 *      "&lt;/php&gt;"
	 * @return the lines which complete a file
	 */
	public Iterable<String> getFileTerminator(SourceFile file) throws LanguageFactoryException;
}
