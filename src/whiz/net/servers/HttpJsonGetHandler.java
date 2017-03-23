/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net.servers;

import ace.gson.Json;
import ace.interfaces.Treater;
import com.google.gson.JsonElement;
import whiz.net.HttpMethod;

public abstract class HttpJsonGetHandler extends HttpStringHandler implements HttpJsonExchanger {

	public HttpJsonGetHandler(final Class<?> clazz, final String route) {
		this(clazz, route, null, null);
	}

	public HttpJsonGetHandler(final Class<?> clazz, final String route, final Treater<byte[]> readingAdapter, final Treater<byte[]> writingAdapter) {
		super(clazz, new HttpMethod[] { HttpMethod.GET }, route, readingAdapter, writingAdapter);
	}

	@Override public String transact(final HttpRequest request, final String body) {
		final JsonElement result = exchange(request, Json.NULL);
		return result == null ? null : Json.JsonElementToString(result);
	}

}
