/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net.clients;

import java.net.URL;
import whiz.net.HttpMethod;

public class HttpPutter extends HttpConnection {

	public HttpPutter(final URL url, final String requestData) {
		super(url, HttpMethod.PUT, requestData);
	}

	public HttpPutter(final String url, final String requestData) {
		super(url, HttpMethod.PUT, requestData);
	}

}
