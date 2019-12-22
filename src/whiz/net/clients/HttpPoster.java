/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net.clients;

import java.net.URL;
import whiz.net.HttpMethod;

/**
 * Useful class for emitting HTTP POST requests.
 */
public class HttpPoster extends HttpConnection {

	/**
	 * Constructor accepting the request URL instance and the request data.
	 * 
	 * @param url
	 * @param requestData 
	 */
	public HttpPoster(final URL url, final String requestData) {
		super(url, HttpMethod.POST, requestData);
	}

	/**
	 * Constructor accepting the request URL string and the request data.
	 * 
	 * @param url
	 * @param requestData 
	 */
	public HttpPoster(final String url, final String requestData) {
		super(url, HttpMethod.POST, requestData);
	}

}
