/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net.servers;

import ace.gson.Json;
import ace.interfaces.Treater;
import com.google.gson.JsonElement;
import whiz.net.HttpMethod;

/**
 * HTTP json post handler.
 */
public abstract class HttpJsonPostHandler extends HttpStringHandler implements HttpJsonExchanger {

	/**
	 * Constructor accepting a class instance and a route.
	 * 
	 * @param clazz
	 * @param route 
	 */
	public HttpJsonPostHandler(final Class<?> clazz, final String route) {
		this(clazz, route, null, null);
	}

	/**
	 * Constructor accepting a class instance, a route and adapters for reading and writing.
	 * 
	 * @param clazz
	 * @param route 
	 * @param readingAdapter 
	 * @param writingAdapter 
	 */
	public HttpJsonPostHandler(final Class<?> clazz, final String route, final Treater<byte[]> readingAdapter, final Treater<byte[]> writingAdapter) {
		super(clazz, new HttpMethod[] { HttpMethod.POST }, route, readingAdapter, writingAdapter);
	}

	@Override public String transact(final HttpRequest request, final String body) {
		final JsonElement result = exchange(request, Json.readStringAsJsonElement(body));
		return result == null ? null : Json.JsonElementToString(result);
	}

}
