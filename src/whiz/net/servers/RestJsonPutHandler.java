/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net.servers;

import ace.gson.model.JsonModel;
import ace.interfaces.Treater;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import whiz.net.HttpMethod;

public abstract class RestJsonPutHandler extends RestJsonAbstractHandler {

	protected JsonModel _modelPut;

	public RestJsonPutHandler(final Class<?> clazz, final String route, final String template) {
		this(clazz, route, template, null, null);
	}

	public RestJsonPutHandler(final Class<?> clazz, final String route, final String template, final Treater<byte[]> readingAdapter, final Treater<byte[]> writingAdapter) {
		super(clazz, new HttpMethod[] { HttpMethod.PUT }, route, template, readingAdapter, writingAdapter);
		setupModel();
	}

	protected final void setupModel() {
		_modelPut = modelPut();
	}

	@Override final JsonModel getModel(final HttpRequest request) {
		return _modelPut;
	}

	@Override final JsonElement derive(final HttpRequest request, final JsonObject body, final JsonObject parameters) {
		return onPut(request, body, parameters);
	}

	protected abstract JsonModel modelPut();

	protected abstract JsonObject onPut(final HttpRequest request, final JsonObject body, final JsonObject parameters);

}
