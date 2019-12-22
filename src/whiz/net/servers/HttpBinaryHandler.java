/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net.servers;

import ace.interfaces.Treater;
import whiz.net.HttpMethod;

/**
 * HTTP binary handler class.
 */
public abstract class HttpBinaryHandler extends HttpRequestHandler {

	/**
	 * Constructor accepting a class instance, an array of HTTP methods and a route.
	 * 
	 * @param clazz
	 * @param methods
	 * @param route 
	 */
	public HttpBinaryHandler(final Class<?> clazz, final HttpMethod[] methods, final String route) {
		this(clazz, methods, route, null, null);
	}

	/**
	 * Constructor accepting a class instance, an array of HTTP methods, a route and adapters for reading and writing.
	 * 
	 * @param clazz
	 * @param methods
	 * @param route
	 * @param readingAdapter
	 * @param writingAdapter 
	 */
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

	/**
	 * Abstract method that contains the exchange processing accepting the HTTP request and the byte array body.
	 * 
	 * @param request
	 * @param body
	 * @return the resulting byte array
	 */
	protected abstract byte[] transact(final HttpRequest request, final byte[] body);

}
