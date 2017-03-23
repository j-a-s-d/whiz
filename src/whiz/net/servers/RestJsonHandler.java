/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net.servers;

import ace.Ace;
import ace.gson.model.JsonModel;
import ace.interfaces.Treater;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import whiz.net.HttpMethod;

public abstract class RestJsonHandler extends RestJsonAbstractHandler {

	protected JsonModel _modelGet;
	protected JsonModel _modelPost;
	protected JsonModel _modelPut;
	protected JsonModel _modelDelete;

	public RestJsonHandler(final Class<?> clazz, final String route, final String template) {
		this(clazz, route, template, null, null);
	}

	public RestJsonHandler(final Class<?> clazz, final String route, final String template, final Treater<byte[]> readingAdapter, final Treater<byte[]> writingAdapter) {
		super(clazz, new HttpMethod[] { HttpMethod.POST, HttpMethod.GET, HttpMethod.PUT, HttpMethod.DELETE }, route, template, readingAdapter, writingAdapter);
		setupModels();
	}

	protected final void setupModels() {
		_modelDelete = new JsonModel();
		_modelGet = new JsonModel();
		_modelPost = modelPost();
		if (!assigned(_modelPost)) {
			_modelPost = new JsonModel();
		}
		_modelPut = modelPut();
		if (!assigned(_modelPut)) {
			_modelPut = new JsonModel();
		}
	}

	@Override final JsonModel getModel(final HttpRequest request) {
		switch (request.getMethod()) {
			case POST:
				return _modelPost;
			case GET:
				return _modelGet;
			case PUT:
				return _modelPut;
			case DELETE:
				return _modelDelete;
		}
		return null;
	}

	@Override final JsonElement derive(final HttpRequest request, final JsonObject body, final JsonObject parameters) {
		switch (request.getMethod()) {
			case POST:
				return onPost(request, body, parameters);
			case GET:
				return onGet(request, body, parameters);
			case PUT:
				return onPut(request, body, parameters);
			case DELETE:
				return onDelete(request, body, parameters);
		}
		return null;
	}

	@Override protected final void onWrongMethod(final HttpRequest request, final String methodName) {
		Ace.debug("RestJsonHandler called with method: " + methodName); // NOTE: it should never happen
	}

	protected abstract JsonModel modelPost();

	protected abstract JsonModel modelPut();

	protected abstract JsonObject onPost(final HttpRequest request, final JsonObject body, final JsonObject parameters);

	protected abstract JsonObject onGet(final HttpRequest request, final JsonObject body, final JsonObject parameters);

	protected abstract JsonObject onPut(final HttpRequest request, final JsonObject body, final JsonObject parameters);

	protected abstract JsonObject onDelete(final HttpRequest request, final JsonObject body, final JsonObject parameters);

}
