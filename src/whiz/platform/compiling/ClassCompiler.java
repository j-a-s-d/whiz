/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.platform.compiling;

import ace.containers.Lists;
import ace.platform.Jars;
import java.io.StringWriter;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import whiz.WhizObject;
import whiz.platform.JDK;

/**
 * Useful class compiler class. Inspired on Java Source Code Compiler (https://github.com/verhas/jscc/).
 */
public class ClassCompiler extends WhizObject {

	public static final Class<?> compileClass(final String sourceCode, final String canonicalClassName) {
		return new ClassCompiler().compile(sourceCode, canonicalClassName);
	}

	private ClassLoader _classLoader;
	private String _compilerErrorOutput;

	/**
	 * Default constructor.
	 */
	public ClassCompiler() {
		super(ClassCompiler.class);
		_classLoader = ClassCompiler.class.getClassLoader();
	}

	/**
	 * Gets the internal class loader instance.
	 * 
	 * @return the internal class loader instance
	 */
	public ClassLoader getClassLoader() {
		return _classLoader;
	}

	/**
	 * Sets the internal class loader instance.
	 * 
	 * @param classLoader
	 */
	public void setClassLoader(final ClassLoader classLoader) {
		_classLoader = classLoader;
	}

	private String calculateSimpleClassName(final String canonicalClassName) {
		return canonicalClassName.substring(canonicalClassName.lastIndexOf('.') + 1);
	}

	private SimpleJavaFileObject makeJavaSourceFromString(final String name, final String code) {
		return new SimpleJavaFileObject(
			URI.create("string:///" + Jars.classNameToClassPath(name) + JavaFileObject.Kind.SOURCE.extension),
			JavaFileObject.Kind.SOURCE
		) {
			@Override public CharSequence getCharContent(final boolean ignoreEncodingErrors) {
				return code;
			}
		};
	}

	private URLClassLoader makeLocalByteClassLoader(final URL[] urls, final ClassLoader parent, final Map<String, byte[]> classFilesMap) {
		return new URLClassLoader(urls, parent) {
			@Override protected Class<?> findClass(final String name) throws ClassNotFoundException {
				if (classFilesMap.containsKey(name)) {
					final byte[] classFile = classFilesMap.get(name);
					final Class<?> klass = defineClass(name, classFile, 0, classFile.length);
					classFilesMap.remove(name);
					return klass;
				}
				return super.findClass(name);
			}
		};
	}

	/**
	 * Compiles the specified java source code with the specified canonical class name.
	 * 
	 * NOTE: the compiled class is loaded via the internal class loader.
	 * 
	 * @param sourceCode
	 * @param canonicalClassName
	 * @return the compiled class instance
	 */
	public Class<?> compile(final String sourceCode, final String canonicalClassName) {
		Class<?> result = null;
		if (JDK.isAvailable()) {
			final JavaCompiler compiler = JDK.getJavaCompiler();
			final List<SimpleJavaFileObject> sources = Lists.makeLinked(makeJavaSourceFromString(
				calculateSimpleClassName(canonicalClassName), sourceCode
			));
			final MemoryJavaFileManager fm = new MemoryJavaFileManager(
				compiler.getStandardFileManager(null, null, null)
			);
			final StringWriter sw = new StringWriter();
			if (Boolean.TRUE.equals(compiler.getTask(sw, fm, null, null, null, sources).call())) {
				final URLClassLoader localByteClassLoader = makeLocalByteClassLoader(
					new URL[0], _classLoader, getClassesMap(fm)
				);
				try {
					result = localByteClassLoader.loadClass(canonicalClassName);
					// NOTE: URLClassLoader.close() does not exists in Java 1.6,
					// but since it should be called, the call is performed via reflection.
					URLClassLoader.class.getMethod("close").invoke(localByteClassLoader);
				} catch (final Exception e) {
					setLastException(e);
				}
				_compilerErrorOutput = null;
			} else {
				_compilerErrorOutput = sw.toString();
			}
		}
		return result;
	}

	private Map<String, byte[]> getClassesMap(final MemoryJavaFileManager fileManager) {
		return new HashMap() {{
			for (final String name : fileManager.getClassFileObjectsMap().keySet()) {
				put(name, fileManager.getClassFileObjectsMap().get(name).getByteArray());
			}
		}};
	}

	/**
	 * Determines if there was a compiler error.
	 * 
	 * @return <tt>true</tt> if there was a compiler error, <tt>false</tt> otherwise
	 */
	public boolean hasCompilerErrorOutput() {
		return assigned(_compilerErrorOutput);
	}

	/**
	 * The compiler error output.
	 * 
	 * @return the compiler error output
	 */
	public String getCompilerErrorOutput() {
		return _compilerErrorOutput;
	}

}
