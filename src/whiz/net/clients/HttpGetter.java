/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net.clients;

import java.net.URL;
import whiz.net.HttpMethod;

/**
 * Useful class for emitting HTTP GET requests.
 */
public class HttpGetter extends HttpConnection {

	/**
	 * Constructor accepting the request URL instance.
	 * 
	 * @param url
	 */
	public HttpGetter(final URL url) {
		super(url, HttpMethod.GET, null);
	}

	/**
	 * Constructor accepting the request URL string.
	 * 
	 * @param url
	 */
	public HttpGetter(final String url) {
		super(url, HttpMethod.GET, null);
	}

}
