/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net.servers;

import ace.gson.model.JsonModel;
import ace.interfaces.Treater;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import whiz.net.HttpMethod;

/**
 * HTTP rest json post handler class.
 */
public abstract class RestJsonPostHandler extends RestJsonAbstractHandler {

	protected JsonModel _modelPost;

	/**
	 * Constructor accepting a class instance, a route and a template.
	 * 
	 * @param clazz
	 * @param route
	 * @param template 
	 */
	public RestJsonPostHandler(final Class<?> clazz, final String route, final String template) {
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
	public RestJsonPostHandler(final Class<?> clazz, final String route, final String template, final Treater<byte[]> readingAdapter, final Treater<byte[]> writingAdapter) {
		super(clazz, new HttpMethod[] { HttpMethod.POST }, route, template, readingAdapter, writingAdapter);
		setupModel();
	}

	protected final void setupModel() {
		_modelPost = modelPost();
	}

	@Override final JsonModel getModel(final HttpRequest request) {
		return _modelPost;
	}

	@Override final JsonElement derive(final HttpRequest request, final JsonObject body, final JsonObject parameters) {
		return onPost(request, body, parameters);
	}

	/**
	 * Asks for the json model that will validate this post handler.
	 * 
	 * @return the json model
	 */
	protected abstract JsonModel modelPost();

	/**
	 * On post event handler accepting a HTTP request, a json body object and a json parameters object.
	 * 
	 * @param request
	 * @param body
	 * @param parameters
	 * @return the json resulting object
	 */
	protected abstract JsonObject onPost(final HttpRequest request, final JsonObject body, final JsonObject parameters);

}
