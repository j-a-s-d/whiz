/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.platform;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import whiz.Whiz;

/**
 * Utility class for working with the JDK.
 */
public class JDK extends Whiz {

	/**
	 * Determines if the JDK is available in the system.
	 * 
	 * @return <tt>true</tt> if the JDK is available in the system, <tt>false</tt> otherwise
	 */
	public static final boolean isAvailable() {
		return assigned(getJavaCompiler(), getJavaClassLoader());
	}

	/**
	 * Gets the java compiler system instance.
	 * 
	 * @return the java compiler system instance
	 */
	public static final JavaCompiler getJavaCompiler() {
		return ToolProvider.getSystemJavaCompiler();
	}

	/**
	 * Gets the java class loader system instance.
	 * 
	 * @return the java class loader system instance
	 */
	public static final ClassLoader getJavaClassLoader() {
		return ToolProvider.getSystemToolClassLoader();
	}

}
