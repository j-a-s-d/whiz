/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.examples.httphostdemo.handlers;

import ace.concurrency.Threads;
import whiz.net.HttpMethods;
import whiz.net.servers.HttpRequest;
import whiz.net.servers.HttpStringHandler;

/**
 * Simple shutdown handler.
 * Try it via:
 * curl -X GET http://127.0.0.1:5000/shutdown
 */
public class ShutdownRequestHandler extends HttpStringHandler {

	public ShutdownRequestHandler(final String route) {
		super(ShutdownRequestHandler.class, HttpMethods.GET_ONLY, route);
	}

	@Override protected String transact(final HttpRequest request, final String body) {
		Threads.isolatedSpawn(new Runnable() {
			@Override public void run() {
				nap(1000);
				getStand().stop();
				print("shutted down.");
			}
		});
		return "shutting down ...";
	}

	@Override protected void onWrongMethod(final HttpRequest request, final String method) {
		//
	}

	@Override protected void onClientException(final HttpRequest request, final Throwable e, final byte[] content) {
		//
	}
	
}
