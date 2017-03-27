/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.platform.compiling;

import ace.containers.Maps;
import java.io.IOException;
import java.util.Map;
import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;
import javax.tools.StandardJavaFileManager;

final class MemoryJavaFileManager extends ForwardingJavaFileManager<StandardJavaFileManager> {
	
	private final Map<String, MemoryFileObject> _classFilesMap;
	
	protected MemoryJavaFileManager(final StandardJavaFileManager fileManager) {
		super(fileManager);
		_classFilesMap = Maps.make();
	}
	
	public Map<String, MemoryFileObject> getClassFileObjectsMap() {
		return _classFilesMap;
	}
	
	@Override public JavaFileObject getJavaFileForOutput(final Location location, final String className, final Kind kind, final FileObject sibling) throws IOException {
		final MemoryFileObject classFile = new MemoryFileObject(className);
		_classFilesMap.put(className, classFile);
		return classFile;
	}
	
}