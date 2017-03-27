/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.platform;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import whiz.Whiz;

/**
 * Utility class for working with the JDK.
 */
public class JDK extends Whiz {

	public static final boolean isAvailable() {
		return assigned(getJavaCompiler(), getJavaClassLoader());
	}

	public static final JavaCompiler getJavaCompiler() {
		return ToolProvider.getSystemJavaCompiler();
	}

	public static final ClassLoader getJavaClassLoader() {
		return ToolProvider.getSystemToolClassLoader();
	}

}
