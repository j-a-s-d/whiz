/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.examples.httphostdemo.handlers;

import whiz.net.servers.DefaultStaticFileServingHandler;

/**
 * Client handler.
 * Try it via:
 * curl -X GET http://127.0.0.1:5000/
 */
public class ClientRequestHandler extends DefaultStaticFileServingHandler {

	public ClientRequestHandler(final String route) {
		super(route, "client");
	}

}
