/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net.servers;

import ace.gson.model.JsonModel;
import ace.interfaces.Treater;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import whiz.net.HttpMethod;

public abstract class RestJsonDeleteHandler extends RestJsonAbstractHandler {

	protected JsonModel _modelDelete;

	public RestJsonDeleteHandler(final Class<?> clazz, final String route, final String template) {
		this(clazz, route, template, null, null);
	}

	public RestJsonDeleteHandler(final Class<?> clazz, final String route, final String template, final Treater<byte[]> readingAdapter, final Treater<byte[]> writingAdapter) {
		super(clazz, new HttpMethod[] { HttpMethod.DELETE }, route, template, readingAdapter, writingAdapter);
		setupModel();
	}

	protected final void setupModel() {
		_modelDelete = new JsonModel();
	}

	@Override final JsonModel getModel(final HttpRequest request) {
		return _modelDelete;
	}

	@Override final JsonElement derive(final HttpRequest request, final JsonObject body, final JsonObject parameters) {
		return onDelete(request, body, parameters);
	}

	protected abstract JsonObject onDelete(final HttpRequest request, final JsonObject body, final JsonObject parameters);

}
