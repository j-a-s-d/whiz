/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net.clients;

import java.net.URL;
import whiz.net.HttpMethod;

public class HttpGetter extends HttpConnection {

	public HttpGetter(final URL url) {
		super(url, HttpMethod.GET, null);
	}

	public HttpGetter(final String url) {
		super(url, HttpMethod.GET, null);
	}

}
