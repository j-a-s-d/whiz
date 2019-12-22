/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net.clients;

import java.net.URL;
import whiz.net.HttpMethod;

/**
 * Useful class for emitting HTTP PUT requests.
 */
public class HttpPutter extends HttpConnection {

	/**
	 * Constructor accepting the request URL instance and the request data.
	 * 
	 * @param url
	 * @param requestData 
	 */
	public HttpPutter(final URL url, final String requestData) {
		super(url, HttpMethod.PUT, requestData);
	}

	/**
	 * Constructor accepting the request URL string and the request data.
	 * 
	 * @param url
	 * @param requestData 
	 */
	public HttpPutter(final String url, final String requestData) {
		super(url, HttpMethod.PUT, requestData);
	}

}
