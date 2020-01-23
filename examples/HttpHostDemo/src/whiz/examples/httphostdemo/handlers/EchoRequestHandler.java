/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.examples.httphostdemo.handlers;

import whiz.net.HttpMethods;
import whiz.net.servers.HttpRequest;
import whiz.net.servers.HttpStringHandler;

/**
 * Simple echo handler.
 * Try it via:
 * curl -X POST -d"hello" http://127.0.0.1:5000/echo
 */
public class EchoRequestHandler extends HttpStringHandler {

	public EchoRequestHandler(final String route) {
		super(EchoRequestHandler.class, HttpMethods.POST_ONLY, route);
	}

	@Override protected String transact(final HttpRequest request, final String body) {
		return body;
	}

	@Override protected void onWrongMethod(final HttpRequest request, final String method) {
		//
	}

	@Override protected void onClientException(final HttpRequest request, final Throwable e, final byte[] content) {
		//
	}

}
