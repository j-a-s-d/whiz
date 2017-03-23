/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net.clients;

import java.net.URL;
import whiz.net.HttpMethod;

public class HttpPoster extends HttpConnection {

	public HttpPoster(final URL url, final String requestData) {
		super(url, HttpMethod.POST, requestData);
	}

	public HttpPoster(final String url, final String requestData) {
		super(url, HttpMethod.POST, requestData);
	}

}
