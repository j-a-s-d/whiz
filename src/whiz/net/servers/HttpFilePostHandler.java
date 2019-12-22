/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net.servers;

import ace.files.BinaryFiles;
import ace.files.FilenameUtils;
import ace.files.FilenameValidator;
import ace.interfaces.Treater;
import whiz.net.HttpMethod;

/**
 * Useful file get handler class that stores content to a root directory.
 */
public abstract class HttpFilePostHandler extends HttpBinaryHandler implements HttpFileOperator {

	private final FilenameValidator _filter;
	private String _folder;

	/**
	 * Constructor accepting a class instance, a route and a folder path.
	 * 
	 * @param clazz
	 * @param route
	 * @param folder 
	 */
	public HttpFilePostHandler(final Class<?> clazz, final String route, final String folder) {
		this(clazz, route, null, null, folder, FilenameValidator.makeAllFilesValidator());
	}

	/**
	 * Constructor accepting a class instance, a route, adapters for reading and writing and a folder path.
	 * 
	 * @param clazz
	 * @param route
	 * @param readingAdapter
	 * @param writingAdapter
	 * @param folder 
	 */
	public HttpFilePostHandler(final Class<?> clazz, final String route, final Treater<byte[]> readingAdapter, final Treater<byte[]> writingAdapter, final String folder) {
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
	public HttpFilePostHandler(final Class<?> clazz, final String route, final String folder, final String filter) {
		this(clazz, route, null, null, folder, new FilenameValidator(filter));
	}

	/**
	 * Constructor accepting a class instance, a route, adapters for reading and writing, a folder path and a file name filter.
	 * 
	 * @param clazz
	 * @param route
	 * @param readingAdapter
	 * @param writingAdapter
	 * @param folder 
	 * @param filter 
	 */
	public HttpFilePostHandler(final Class<?> clazz, final String route, final Treater<byte[]> readingAdapter, final Treater<byte[]> writingAdapter, final String folder, final String filter) {
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
	public HttpFilePostHandler(final Class<?> clazz, final String route, final String folder, final FilenameValidator validator) {
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
	public HttpFilePostHandler(final Class<?> clazz, final String route, final Treater<byte[]> readingAdapter, final Treater<byte[]> writingAdapter, final String folder, final FilenameValidator validator) {
		super(clazz, new HttpMethod[] { HttpMethod.POST }, route, readingAdapter, writingAdapter);
		_folder = FilenameUtils.ensureLastDirectorySeparator(folder);
		_filter = validator;
	}

	/**
	 * Sets the root folder to store.
	 * 
	 * @param value 
	 */
	public synchronized void setFolder(final String value) {
		_folder = value;
	}

	/**
	 * Gets the root folder being wrote.
	 * 
	 * @return the root folder path
	 */
	public synchronized String getFolder() {
		return _folder;
	}

	@Override public byte[] transact(final HttpRequest request, final byte[] body) {
		final String fileName = request.getRequestPath().substring(getRoute().length());
		if (fileName == null || fileName.isEmpty()) {
			onMissingFilename(request);
			return null;
		}
		if (!_filter.validate(fileName)) {
			onForbiddenFilename(request, fileName);
			return null;
		}
		if (!validate(request, fileName)) {
			return null;
		}
		if (!BinaryFiles.write(getFolder() + fileName, body)) {
			return null;
		}
		return request.EMPTY_RESPONSE;
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

}
