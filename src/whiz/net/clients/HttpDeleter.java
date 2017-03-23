/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net.clients;

import java.net.URL;
import whiz.net.HttpMethod;

public class HttpDeleter extends HttpConnection {

	public HttpDeleter(final URL url) {
		super(url, HttpMethod.DELETE, null);
	}

	public HttpDeleter(final String url) {
		super(url, HttpMethod.DELETE, null);
	}

}
