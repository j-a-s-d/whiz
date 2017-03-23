/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net.servers;

import ace.gson.Json;
import ace.gson.model.JsonModel;
import ace.interfaces.Treater;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import whiz.net.HttpMethod;

/*public*/ abstract class RestJsonAbstractHandler extends HttpStringHandler implements HttpJsonExchanger {

	protected String _template;

	public RestJsonAbstractHandler(final Class<?> clazz, final HttpMethod[] methods, final String route, final String template) {
		this(clazz, methods, route, template, null, null);
	}

	public RestJsonAbstractHandler(final Class<?> clazz, final HttpMethod[] methods, final String route, final String template, final Treater<byte[]> readingAdapter, final Treater<byte[]> writingAdapter) {
		super(clazz, methods, route, readingAdapter, writingAdapter);
		_template = template;
	}

	@Override public String transact(final HttpRequest request, final String body) {
		final JsonElement result = exchange(request, body == null ? null : Json.readStringAsJsonElement(body));
		return result == null ? null : Json.JsonElementToString(result);
	}

	@Override public JsonElement exchange(final HttpRequest request, final JsonElement body) {
		if (!request.requestPathMatchesPattern(_template)) {
			return onBadUrl(request, request.getRequestPath());
		}
		JsonObject o = null;
		if (!request.getMethod().is(HttpMethod.GET) && !request.getMethod().is(HttpMethod.DELETE)) {
			if (!body.isJsonObject()) {
				return onBadMessage(request, body);
			}
			o = body.getAsJsonObject();
			final JsonModel model = getModel(request);
			if (model == null) {
				return null;
			}
			if (!model.readFieldValues(o)) {
				return onBadMessageField(request, body, model.getLastFieldRead());
			}
		}
		return derive(request, o, Json.convertHashMapToJsonObject(request.extractParametersFromPathByTemplateAsHashMap(_template)));
	}

	abstract JsonElement derive(final HttpRequest request, final JsonObject body, final JsonObject parameters);

	abstract JsonModel getModel(final HttpRequest request);

	protected abstract JsonElement onBadMessageField(final HttpRequest request, final JsonElement body, final String field);

	protected abstract JsonElement onBadMessage(final HttpRequest request, final JsonElement body);

	protected abstract JsonElement onBadUrl(final HttpRequest request, final String path);

}
