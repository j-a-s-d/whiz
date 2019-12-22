/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net.clients;

import java.net.URL;
import whiz.net.HttpMethod;

/**
 * Useful class for emitting HTTP DELETE requests.
 */
public class HttpDeleter extends HttpConnection {

	/**
	 * Constructor accepting the request URL instance.
	 * 
	 * @param url
	 */
	public HttpDeleter(final URL url) {
		super(url, HttpMethod.DELETE, null);
	}

	/**
	 * Constructor accepting the request URL string.
	 * 
	 * @param url
	 */
	public HttpDeleter(final String url) {
		super(url, HttpMethod.DELETE, null);
	}

}
