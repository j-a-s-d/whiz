/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net.servers;

import ace.files.BinaryFiles;
import ace.files.FilenameUtils;
import ace.files.FilenameValidator;
import ace.interfaces.Treater;
import java.io.File;
import whiz.net.HttpMethod;

public abstract class HttpFileGetHandler extends HttpBinaryHandler implements HttpFileOperator {

	private static String DEFAULT_FILENAME = "index.html";

	private final FilenameValidator _filter;
	private String _folder;
	private String _defaultFilename;

	public HttpFileGetHandler(final Class<?> clazz, final String route, final String folder) {
		this(clazz, route, null, null, folder, FilenameValidator.makeAllFilesValidator());
	}

	public HttpFileGetHandler(final Class<?> clazz, final String route, final Treater<byte[]> readingAdapter, final Treater<byte[]> writingAdapter, final String folder) {
		this(clazz, route, readingAdapter, writingAdapter, folder, FilenameValidator.makeAllFilesValidator());
	}

	public HttpFileGetHandler(final Class<?> clazz, final String route, final String folder, final String filter) {
		this(clazz, route, null, null, folder, filter);
	}

	public HttpFileGetHandler(final Class<?> clazz, final String route, final Treater<byte[]> readingAdapter, final Treater<byte[]> writingAdapter, final String folder, final String filter) {
		this(clazz, route, readingAdapter, writingAdapter, folder, new FilenameValidator(filter));
	}

	public HttpFileGetHandler(final Class<?> clazz, final String route, final String folder, final FilenameValidator filter) {
		this(clazz, route, null, null, folder, filter);
	}

	public HttpFileGetHandler(final Class<?> clazz, final String route, final Treater<byte[]> readingAdapter, final Treater<byte[]> writingAdapter, final String folder, final FilenameValidator filter) {
		super(clazz, new HttpMethod[] { HttpMethod.GET }, route, readingAdapter, writingAdapter);
		_folder = FilenameUtils.ensureLastDirectorySeparator(folder);
		_filter = filter;
		_defaultFilename = DEFAULT_FILENAME;
	}

	public synchronized void setFolder(final String value) {
		_folder = FilenameUtils.ensureLastDirectorySeparator(value);
	}

	public synchronized String getFolder() {
		return _folder;
	}

	public synchronized void setDefaultFilename(final String value) {
		_defaultFilename = value;
	}

	public synchronized String getDefaultFilename() {
		return _defaultFilename;
	}

	@Override public byte[] transact(final HttpRequest request, final byte[] body) {
		String fileName = request.getRequestPath().substring(getRoute().length());
		if (fileName == null) {
			onMissingFilename(request);
			return null;
		}
		fileName = onDetermineRealFilename(request, fileName);
		File file = new File(getFolder(), fileName);
		if (file.isDirectory()) {
			fileName = getDefaultFilename();
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
		if (assigned(mimeType)) {
			request.setResponseHeader("Content-Type", mimeType);
		}
		if (!validate(request, fileName)) {
			return null;
		}
		return BinaryFiles.read(file);
	}

	protected abstract void onMissingFilename(final HttpRequest request);

	protected abstract void onForbiddenFilename(final HttpRequest request, final String fileName);

	protected abstract void onInexistentFile(final HttpRequest request, final String fileName);

	protected abstract String onDetermineContentTypeForFilename(final HttpRequest request, final String fileName);

	// NOTE: override this method if you need to change the name of the file you want to get
	protected String onDetermineRealFilename(final HttpRequest request, final String fileName) {
		return fileName;
	}

}
