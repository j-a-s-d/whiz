/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net.servers;

import ace.files.BinaryFiles;
import ace.files.FilenameUtils;
import ace.files.FilenameValidator;
import ace.interfaces.Treater;
import whiz.net.HttpMethod;

public abstract class HttpFilePostHandler extends HttpBinaryHandler implements HttpFileOperator {

	private final FilenameValidator _filter;
	private String _folder;

	public HttpFilePostHandler(final Class<?> clazz, final String route, final String folder) {
		this(clazz, route, null, null, folder, FilenameValidator.makeAllFilesValidator());
	}

	public HttpFilePostHandler(final Class<?> clazz, final String route, final Treater<byte[]> readingAdapter, final Treater<byte[]> writingAdapter, final String folder) {
		this(clazz, route, readingAdapter, writingAdapter, folder, FilenameValidator.makeAllFilesValidator());
	}

	public HttpFilePostHandler(final Class<?> clazz, final String route, final String folder, final String filter) {
		this(clazz, route, null, null, folder, new FilenameValidator(filter));
	}

	public HttpFilePostHandler(final Class<?> clazz, final String route, final Treater<byte[]> readingAdapter, final Treater<byte[]> writingAdapter, final String folder, final String filter) {
		this(clazz, route, readingAdapter, writingAdapter, folder, new FilenameValidator(filter));
	}

	public HttpFilePostHandler(final Class<?> clazz, final String route, final String folder, final FilenameValidator filter) {
		this(clazz, route, null, null, folder, filter);
	}

	public HttpFilePostHandler(final Class<?> clazz, final String route, final Treater<byte[]> readingAdapter, final Treater<byte[]> writingAdapter, final String folder, final FilenameValidator filter) {
		super(clazz, new HttpMethod[] { HttpMethod.POST }, route, readingAdapter, writingAdapter);
		_folder = FilenameUtils.ensureLastDirectorySeparator(folder);
		_filter = filter;
	}

	public synchronized void setFolder(final String value) {
		_folder = value;
	}

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

	protected abstract void onMissingFilename(final HttpRequest request);

	protected abstract void onForbiddenFilename(final HttpRequest request, final String fileName);

}
