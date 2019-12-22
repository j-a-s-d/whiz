/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net.servers;

import ace.interfaces.Treater;
import whiz.net.HttpMethod;

/**
 * HTTP string handler class.
 */
public abstract class HttpStringHandler extends HttpRequestHandler {

	private String _charset;

	/**
	 * Constructor accepting a class instance, an array of HTTP methods and a route.
	 * 
	 * @param clazz
	 * @param methods
	 * @param route 
	 */
	public HttpStringHandler(final Class<?> clazz, final HttpMethod[] methods, final String route) {
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
	public HttpStringHandler(final Class<?> clazz, final HttpMethod[] methods, final String route, final Treater<byte[]> readingAdapter, final Treater<byte[]> writingAdapter) {
		super(clazz, methods, route, readingAdapter, writingAdapter);
		_charset = "UTF-8";
	}

	/**
	 * Gets the character set used in the exchange.
	 * 
	 * @return the character set used in the exchange
	 */
	public String getCharset() {
		return _charset;
	}

	/**
	 * Sets the character set used in the exchange.
	 * 
	 * @param value
	 */
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

	/**
	 * Abstract method that contains the exchange processing accepting the HTTP request and the string body.
	 * 
	 * @param request
	 * @param body
	 * @return the resulting string
	 */
	protected abstract String transact(final HttpRequest request, final String body);

}
