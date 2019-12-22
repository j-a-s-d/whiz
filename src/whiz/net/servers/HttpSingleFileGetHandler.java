/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net.servers;

import ace.files.BinaryFiles;
import ace.files.FilenameValidator;
import ace.interfaces.Treater;
import java.io.File;
import whiz.net.HttpMethod;

/**
 * Useful file get handler class that serves single files (no root directory involved).
 */
public abstract class HttpSingleFileGetHandler extends HttpBinaryHandler implements HttpFileOperator {

	protected String _template;
	private final FilenameValidator _filter;

	/**
	 * Constructor accepting a class instance and a route.
	 * 
	 * @param clazz
	 * @param route 
	 */
	public HttpSingleFileGetHandler(final Class<?> clazz, final String route) {
		this(clazz, route, null, null, FilenameValidator.makeAllFilesValidator());
	}

	/**
	 * Constructor accepting a class instance, a route and adapters for reading and writing.
	 * 
	 * @param clazz
	 * @param route 
	 * @param readingAdapter 
	 * @param writingAdapter 
	 */
	public HttpSingleFileGetHandler(final Class<?> clazz, final String route, final Treater<byte[]> readingAdapter, final Treater<byte[]> writingAdapter) {
		this(clazz, route, readingAdapter, writingAdapter, FilenameValidator.makeAllFilesValidator());
	}

	/**
	 * Constructor accepting a class instance, a route and a file name filter.
	 * 
	 * @param clazz
	 * @param route 
	 * @param filter 
	 */
	public HttpSingleFileGetHandler(final Class<?> clazz, final String route, final String filter) {
		this(clazz, route, null, null, filter);
	}

	/**
	 * Constructor accepting a class instance, a route, adapters for reading and writing and a file name filter.
	 * 
	 * @param clazz
	 * @param route 
	 * @param readingAdapter 
	 * @param writingAdapter 
	 * @param filter 
	 */
	public HttpSingleFileGetHandler(final Class<?> clazz, final String route, final Treater<byte[]> readingAdapter, final Treater<byte[]> writingAdapter, final String filter) {
		this(clazz, route, readingAdapter, writingAdapter, new FilenameValidator(filter));
	}

	/**
	 * Constructor accepting a class instance, a route and a file name validator.
	 * 
	 * @param clazz
	 * @param route 
	 * @param validator 
	 */
	public HttpSingleFileGetHandler(final Class<?> clazz, final String route, final FilenameValidator validator) {
		this(clazz, route, null, null, validator);
	}

	/**
	 * Constructor accepting a class instance, a route, adapters for reading and writing and a file name validator.
	 * 
	 * @param clazz
	 * @param route 
	 * @param readingAdapter 
	 * @param writingAdapter 
	 * @param validator 
	 */
	public HttpSingleFileGetHandler(final Class<?> clazz, final String route, final Treater<byte[]> readingAdapter, final Treater<byte[]> writingAdapter, final FilenameValidator validator) {
		super(clazz, new HttpMethod[] { HttpMethod.GET }, route, readingAdapter, writingAdapter);
		_filter = validator;
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

	/**
	 * Gets the file name validator used to filter.
	 * 
	 * @return the file name validator used to filter
	 */
	public FilenameValidator getFilter() {
		return _filter;
	}

	/**
	 * On get file event handler accepting a HTTP request and a file name.
	 * 
	 * @param request
	 * @param fileName
	 * @return the file
	 */
	protected abstract File onGetFile(final HttpRequest request, final String fileName);

	/**
	 * On determine real file name event accepting the HTTP request and the file name.
	 * 
	 * NOTE: override this method if you need to change the name of the file you want to get
	 * 
	 * @param request
	 * @param fileName
	 * @return the real file name
	 */
	protected abstract String onDetermineContentTypeForFilename(final HttpRequest request, final String fileName);

}
