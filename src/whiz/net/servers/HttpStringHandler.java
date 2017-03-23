/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net.servers;

import ace.interfaces.Treater;
import whiz.net.HttpMethod;

public abstract class HttpStringHandler extends HttpRequestHandler {

	private String _charset;

	public HttpStringHandler(final Class<?> clazz, final HttpMethod[] methods, final String route) {
		this(clazz, methods, route, null, null);
	}

	public HttpStringHandler(final Class<?> clazz, final HttpMethod[] methods, final String route, final Treater<byte[]> readingAdapter, final Treater<byte[]> writingAdapter) {
		super(clazz, methods, route, readingAdapter, writingAdapter);
		_charset = "UTF-8";
	}

	public String getCharset() {
		return _charset;
	}

	public void setCharset(final String value) {
		_charset = value;
	}

	@Override protected byte[] process(final HttpRequest request, final byte[] requestBody) throws Throwable {
		final String result = transact(request, requestBody != null ? new String(requestBody, _charset) : null);
		if (result == null) {
			if (hadException()) {
				throw getLastException();
			}
			return null;
		}
		return result.getBytes(_charset);
	}

	protected abstract String transact(final HttpRequest request, final String body);

}
