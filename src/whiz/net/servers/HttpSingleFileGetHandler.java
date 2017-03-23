/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net.servers;

import ace.files.BinaryFiles;
import ace.files.FilenameValidator;
import ace.interfaces.Treater;
import java.io.File;
import whiz.net.HttpMethod;

public abstract class HttpSingleFileGetHandler extends HttpBinaryHandler implements HttpFileOperator {
	
	protected String _template;
	private final FilenameValidator _filter;
	
	public HttpSingleFileGetHandler(final String route) {
		this(route, null, null, FilenameValidator.makeAllFilesValidator());
	}
	
	public HttpSingleFileGetHandler(final String route, final Treater<byte[]> readingAdapter, final Treater<byte[]> writingAdapter) {
		this(route, readingAdapter, writingAdapter, FilenameValidator.makeAllFilesValidator());
	}
	
	public HttpSingleFileGetHandler(final String route, final String filter) {
		this(route, null, null, filter);
	}
	
	public HttpSingleFileGetHandler(final String route, final Treater<byte[]> readingAdapter, final Treater<byte[]> writingAdapter, final String filter) {
		this(route, readingAdapter, writingAdapter, new FilenameValidator(filter));
	}
	
	public HttpSingleFileGetHandler(final String route, final FilenameValidator filter) {
		this(route, null, null, filter);
	}
	
	public HttpSingleFileGetHandler(final String route, final Treater<byte[]> readingAdapter, final Treater<byte[]> writingAdapter, final FilenameValidator filter) {
		super(new HttpMethod[] { HttpMethod.GET }, route, readingAdapter, writingAdapter);
		_filter = filter;
	}
	
	@Override public byte[] transact(final HttpRequest request, final byte[] body) {
		final String fileName = request.getRequestPath().substring(getRoute().length());
		if (!validate(request, fileName)) {
			return null;
		}
		final String mimeType = onDetermineContentTypeForFilename(request, fileName);
		if (assigned(mimeType)) {
			request.setResponseHeader("Content-Type", mimeType);
		}
		final File file = onGetFile(request, fileName);
		if (!assigned(file) || !file.exists()) {
			return null;
		}
		return BinaryFiles.read(file);
	}
	
	public FilenameValidator getFilter() {
		return _filter;
	}
	
    protected abstract File onGetFile(final HttpRequest request, final String fileName);
    
	protected abstract String onDetermineContentTypeForFilename(final HttpRequest request, final String fileName);
	
}
