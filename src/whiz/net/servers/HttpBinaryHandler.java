/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net.servers;

import ace.interfaces.Treater;
import whiz.net.HttpMethod;

public abstract class HttpBinaryHandler extends HttpRequestHandler {

	public HttpBinaryHandler(final Class<?> clazz, final HttpMethod[] methods, final String route) {
		this(clazz, methods, route, null, null);
	}

	public HttpBinaryHandler(final Class<?> clazz, final HttpMethod[] methods, final String route, final Treater<byte[]> readingAdapter, final Treater<byte[]> writingAdapter) {
		super(clazz, methods, route, readingAdapter, writingAdapter);
	}

	@Override protected byte[] process(final HttpRequest request, final byte[] requestBody) throws Throwable {
		final byte[] result = transact(request, requestBody);
		if (result == null) {
			if (hadException()) {
				throw getLastException();
			}
			return null;
		}
		return result;
	}

	protected abstract byte[] transact(final HttpRequest request, final byte[] body);

}
