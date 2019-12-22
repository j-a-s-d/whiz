/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net.servers;

import ace.gson.model.JsonModel;
import ace.interfaces.Treater;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import whiz.net.HttpMethod;

/**
 * HTTP rest json get handler class.
 */
public abstract class RestJsonGetHandler extends RestJsonAbstractHandler {

	protected JsonModel _modelGet;

	/**
	 * Constructor accepting a class instance, a route and a template.
	 * 
	 * @param clazz
	 * @param route
	 * @param template 
	 */
	public RestJsonGetHandler(final Class<?> clazz, final String route, final String template) {
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
	public RestJsonGetHandler(final Class<?> clazz, final String route, final String template, final Treater<byte[]> readingAdapter, final Treater<byte[]> writingAdapter) {
		super(clazz, new HttpMethod[] { HttpMethod.GET }, route, template, readingAdapter, writingAdapter);
		setupModel();
	}

	protected final void setupModel() {
		_modelGet = new JsonModel();
	}

	@Override final JsonModel getModel(final HttpRequest request) {
		return _modelGet;
	}

	@Override final JsonElement derive(final HttpRequest request, final JsonObject body, final JsonObject parameters) {
		return onGet(request, body, parameters);
	}

	/**
	 * On get event handler accepting a HTTP request, a json body object (that should be empty) and a json parameters object.
	 * 
	 * @param request
	 * @param body
	 * @param parameters
	 * @return the json resulting object
	 */
	protected abstract JsonObject onGet(final HttpRequest request, final JsonObject body, final JsonObject parameters);

}
