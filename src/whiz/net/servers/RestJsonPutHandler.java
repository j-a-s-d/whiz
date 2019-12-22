/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net.servers;

import ace.gson.model.JsonModel;
import ace.interfaces.Treater;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import whiz.net.HttpMethod;

/**
 * HTTP rest json put handler class.
 */
public abstract class RestJsonPutHandler extends RestJsonAbstractHandler {

	protected JsonModel _modelPut;

	/**
	 * Constructor accepting a class instance, a route and a template.
	 * 
	 * @param clazz
	 * @param route
	 * @param template 
	 */
	public RestJsonPutHandler(final Class<?> clazz, final String route, final String template) {
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

	/**
	 * Asks for the json model that will validate this put handler.
	 * 
	 * @return the json model
	 */
	protected abstract JsonModel modelPut();

	/**
	 * On put event handler accepting a HTTP request, a json body object and a json parameters object.
	 * 
	 * @param request
	 * @param body
	 * @param parameters
	 * @return the json resulting object
	 */
	protected abstract JsonObject onPut(final HttpRequest request, final JsonObject body, final JsonObject parameters);

}
