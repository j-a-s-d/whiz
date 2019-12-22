/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net.servers;

import ace.Ace;
import ace.gson.model.JsonModel;
import ace.interfaces.Treater;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import whiz.net.HttpMethod;

/**
 * Abstract HTTP rest json handler class.
 */
public abstract class RestJsonHandler extends RestJsonAbstractHandler {

	protected JsonModel _modelGet;
	protected JsonModel _modelPost;
	protected JsonModel _modelPut;
	protected JsonModel _modelDelete;

	/**
	 * Constructor accepting a class instance, a route and a template.
	 * 
	 * @param clazz
	 * @param route
	 * @param template 
	 */
	public RestJsonHandler(final Class<?> clazz, final String route, final String template) {
		this(clazz, route, template, null, null);
	}

	/**
	 * Constructor accepting a class instance, a route, a template and adapters for reading and writing.
	 * 
	 * @param clazz
	 * @param route
	 * @param template 
	 * @param readingAdapter 
	 * @param writingAdapter 
	 */
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

	/**
	 * Asks for the json model that will validate the post requests this rest handler.
	 * 
	 * @return the json model
	 */
	protected abstract JsonModel modelPost();

	/**
	 * Asks for the json model that will validate the put requests this rest handler.
	 * 
	 * @return the json model
	 */
	protected abstract JsonModel modelPut();

	/**
	 * On post event handler accepting a HTTP request, a json body object and a json parameters object.
	 * 
	 * @param request
	 * @param body
	 * @param parameters
	 * @return the json resulting object
	 */
	protected abstract JsonObject onPost(final HttpRequest request, final JsonObject body, final JsonObject parameters);

	/**
	 * On get event handler accepting a HTTP request, a json body object (that should be empty) and a json parameters object.
	 * 
	 * @param request
	 * @param body
	 * @param parameters
	 * @return the json resulting object
	 */
	protected abstract JsonObject onGet(final HttpRequest request, final JsonObject body, final JsonObject parameters);

	/**
	 * On put event handler accepting a HTTP request, a json body object and a json parameters object.
	 * 
	 * @param request
	 * @param body
	 * @param parameters
	 * @return the json resulting object
	 */
	protected abstract JsonObject onPut(final HttpRequest request, final JsonObject body, final JsonObject parameters);

	/**
	 * On delete event handler accepting a HTTP request, a json body object (that should be empty) and a json parameters object.
	 * 
	 * @param request
	 * @param body
	 * @param parameters
	 * @return the json resulting object
	 */
	protected abstract JsonObject onDelete(final HttpRequest request, final JsonObject body, final JsonObject parameters);

}
