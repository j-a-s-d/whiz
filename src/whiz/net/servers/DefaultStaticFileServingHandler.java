/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net.servers;

import ace.files.FilenameUtils;
import ace.interfaces.Treater;
import java.util.Map;
import whiz.net.MIMETypes;

/**
 * An static file serving handler class.
 */
public class DefaultStaticFileServingHandler extends HttpFileGetHandler {

	private final Map<String, String> _mimeTypes;

    /**
     * Constructor accepting a route and a root.
	 * 
     * @param route
     * @param root
     */
	public DefaultStaticFileServingHandler(final String route, final String root) {
		super(DefaultStaticFileServingHandler.class, route, root);
		_mimeTypes = MIMETypes.getAsHashMap();
	}

    /**
     * Constructor accepting a route, a root and mime types.
	 * 
     * @param route
     * @param root
     * @param mimeTypes 
     */
	public DefaultStaticFileServingHandler(final String route, final String root, final Map<String, String> mimeTypes) {
		super(DefaultStaticFileServingHandler.class, route, root);
		_mimeTypes = assigned(mimeTypes) ? mimeTypes : MIMETypes.getAsHashMap();
	}

    /**
     * Constructor accepting a route, a root, mime types and adapters for reading and writing.
     * 
     * @param route
     * @param readingAdapter
     * @param writingAdapter
     * @param root
     * @param mimeTypes 
     */
	public DefaultStaticFileServingHandler(final String route, final Treater<byte[]> readingAdapter, final Treater<byte[]> writingAdapter, final String root, final Map<String, String> mimeTypes) {
		super(DefaultStaticFileServingHandler.class, route, readingAdapter, writingAdapter, root);
		_mimeTypes = assigned(mimeTypes) ? mimeTypes : MIMETypes.getAsHashMap();
	}

	@Override public String onDetermineContentTypeForFilename(final HttpRequest request, final String fileName) {
		return _mimeTypes.get(FilenameUtils.extractExtension(fileName));
	}

	@Override public boolean validate(final HttpRequest request, final String fileName) {
		return true;
	}

	@Override protected void onMissingFilename(final HttpRequest request) {
		printError("onMissingFilename");
	}

	@Override protected void onForbiddenFilename(final HttpRequest request, final String fileName) {
		printError("onForbiddenFilename (" + fileName + ")");
	}

	@Override protected void onInexistentFile(final HttpRequest request, final String fileName) {
		printError("onInexistentFile (" + fileName + ")");
	}

	@Override protected void onWrongMethod(final HttpRequest request, final String methodName) {
		printError("onWrongMethod (" + methodName + ")");
	}

	@Override protected void onClientException(final HttpRequest request, final Throwable e, final byte[] body) {
		printError("onClientException");
	}

	private void printError(final String error) {
		debug("* DefaultStaticFileServingHandler@(" + getRoute() + "): " + error);
	}

}
