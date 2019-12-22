/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net.servers;

import ace.files.BinaryFiles;
import ace.files.FilenameUtils;
import ace.files.FilenameValidator;
import ace.interfaces.Treater;
import java.io.File;
import whiz.net.HttpMethod;

/**
 * Useful file get handler class that serves the content of a root directory.
 */
public abstract class HttpFileGetHandler extends HttpBinaryHandler implements HttpFileOperator {

	private static String DEFAULT_FILENAME = "index.html";

	private final FilenameValidator _filter;
	private String _folder;
	private String _defaultFilename;

	/**
	 * Constructor accepting a class instance, a route and a folder path.
	 * 
	 * @param clazz
	 * @param route
	 * @param folder 
	 */
	public HttpFileGetHandler(final Class<?> clazz, final String route, final String folder) {
		this(clazz, route, null, null, folder, FilenameValidator.makeAllFilesValidator());
	}

	/**
	 * Constructor accepting a class instance, a route and adapters for reading and writing.
	 * 
	 * @param clazz
	 * @param route
	 * @param readingAdapter
	 * @param writingAdapter
	 */
	public HttpFileGetHandler(final Class<?> clazz, final String route, final Treater<byte[]> readingAdapter, final Treater<byte[]> writingAdapter, final String folder) {
		this(clazz, route, readingAdapter, writingAdapter, folder, FilenameValidator.makeAllFilesValidator());
	}

	/**
	 * Constructor accepting a class instance, a route, a folder path and a file name filter.
	 * 
	 * @param clazz
	 * @param route
	 * @param folder 
	 * @param filter 
	 */
	public HttpFileGetHandler(final Class<?> clazz, final String route, final String folder, final String filter) {
		this(clazz, route, null, null, folder, filter);
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
	public HttpFileGetHandler(final Class<?> clazz, final String route, final Treater<byte[]> readingAdapter, final Treater<byte[]> writingAdapter, final String folder, final String filter) {
		this(clazz, route, readingAdapter, writingAdapter, folder, new FilenameValidator(filter));
	}

	/**
	 * Constructor accepting a class instance, a route, a folder path and a file name validator.
	 * 
	 * @param clazz
	 * @param route
	 * @param folder 
	 * @param validator 
	 */
	public HttpFileGetHandler(final Class<?> clazz, final String route, final String folder, final FilenameValidator validator) {
		this(clazz, route, null, null, folder, validator);
	}

	/**
	 * Constructor accepting a class instance, a route, adapters for reading and writing, a folder path and a file name validator.
	 * 
	 * @param clazz
	 * @param route
	 * @param readingAdapter
	 * @param writingAdapter
	 * @param folder 
	 * @param validator 
	 */
	public HttpFileGetHandler(final Class<?> clazz, final String route, final Treater<byte[]> readingAdapter, final Treater<byte[]> writingAdapter, final String folder, final FilenameValidator validator) {
		super(clazz, new HttpMethod[] { HttpMethod.GET }, route, readingAdapter, writingAdapter);
		_folder = FilenameUtils.ensureLastDirectorySeparator(folder);
		_filter = validator;
		_defaultFilename = DEFAULT_FILENAME;
	}

	/**
	 * Sets the root folder to serve.
	 * 
	 * @param value 
	 */
	public synchronized void setFolder(final String value) {
		_folder = FilenameUtils.ensureLastDirectorySeparator(value);
	}

	/**
	 * Gets the root folder being served.
	 * 
	 * @return the root folder path
	 */
	public synchronized String getFolder() {
		return _folder;
	}

	/**
	 * Sets the default file name to the specified value.
	 * 
	 * @param value 
	 */
	public synchronized void setDefaultFilename(final String value) {
		_defaultFilename = value;
	}

	/**
	 * Gets the default file name (usually 'index.html').
	 * 
	 * @return the default file name
	 */
	public synchronized String getDefaultFilename() {
		return _defaultFilename;
	}

	@Override public byte[] transact(final HttpRequest request, final byte[] body) {
		String fileName = request.getRequestPath().substring(getRoute().length());
		if (fileName == null) {
			onMissingFilename(request);
			return null;
		}
		if ((fileName = onDetermineRealFilename(request, fileName)) == null) {
			onInexistentFile(request, null);
			return null;
		}
		File file = new File(getFolder(), fileName);
		if (file.isDirectory()) {
			if ((fileName = getDefaultFilename()) == null) {
				onInexistentFile(request, null);
				return null;
			}
			file = new File(file, fileName);
		}
		if (!file.exists()) {
			onInexistentFile(request, fileName);
			return null;
		}
		if (!_filter.validate(fileName)) {
			onForbiddenFilename(request, fileName);
			return null;
		}
		final String mimeType = onDetermineContentTypeForFilename(request, fileName);
		if (mimeType != null) {
			request.setResponseHeader("Content-Type", mimeType);
		}
		if (!validate(request, fileName)) {
			return null;
		}
		return BinaryFiles.read(file);
	}

	/**
	 * On missing file name event handler accepting a HTTP request.
	 * 
	 * @param request 
	 */
	protected abstract void onMissingFilename(final HttpRequest request);

	/**
	 * On forbidden file name event handler accepting a HTTP request and the forbidden file name.
	 * 
	 * @param request 
	 * @param fileName 
	 */
	protected abstract void onForbiddenFilename(final HttpRequest request, final String fileName);

	/**
	 * On inexistent file name event handler accepting a HTTP request and the inexistent file name.
	 * 
	 * @param request 
	 * @param fileName 
	 */
	protected abstract void onInexistentFile(final HttpRequest request, final String fileName);

	/**
	 * On determine content type for file name event handler accepting a HTTP request and the file name.
	 * 
	 * @param request
	 * @param fileName
	 * @return the content type for the file name
	 */
	protected abstract String onDetermineContentTypeForFilename(final HttpRequest request, final String fileName);

	/**
	 * On determine real file name event accepting a HTTP request and a file name.
	 * 
	 * NOTE: override this method if you need to change the name of the file you want to get
	 * 
	 * @param request
	 * @param fileName
	 * @return the real file name
	 */
	protected String onDetermineRealFilename(final HttpRequest request, final String fileName) {
		return fileName;
	}

}
