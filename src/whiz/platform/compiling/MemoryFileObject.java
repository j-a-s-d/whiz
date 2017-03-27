/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.platform.compiling;

import ace.platform.Jars;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.URI;
import java.util.Date;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.NestingKind;
import javax.tools.JavaFileObject;

final class MemoryFileObject implements JavaFileObject {
	
	private final String _name;
	private final ByteArrayOutputStream _fileBytesContent = new ByteArrayOutputStream();
	
	public MemoryFileObject(final String name) {
		_name = name;
	}
	
	@Override public URI toUri() {
		return URI.create("string:///" + Jars.classNameToClassPath(_name) + Kind.SOURCE.extension);
	}
	
	@Override public String getName() {
		return _name;
	}
	
	@Override public InputStream openInputStream() throws IOException {
		return new ByteArrayInputStream(_fileBytesContent.toByteArray());
	}
	
	public byte[] getByteArray() {
		return _fileBytesContent.toByteArray();
	}
	
	@Override public OutputStream openOutputStream() throws IOException {
		return _fileBytesContent;
	}
	
	@Override public Reader openReader(final boolean ignoreEncodingErrors) throws IOException {
		return null;
	}
	
	@Override public CharSequence getCharContent(final boolean ignoreEncodingErrors) throws IOException {
		return null;
	}
	
	@Override public Writer openWriter() throws IOException {
		return null;
	}
	
	@Override public long getLastModified() {
		return new Date().getTime();
	}
	
	@Override public boolean delete() {
		return true;
	}
	
	@Override public Kind getKind() {
		return Kind.CLASS;
	}
	
	@Override public boolean isNameCompatible(final String simpleName, final Kind kind) {
		return true;
	}
	
	@Override public NestingKind getNestingKind() {
		return null;
	}
	
	@Override public Modifier getAccessLevel() {
		return Modifier.PUBLIC;
	}
	
}