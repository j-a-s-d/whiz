/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.examples.httphostdemo;

import whiz.Whiz;
import whiz.examples.httphostdemo.handlers.ClientRequestHandler;
import whiz.examples.httphostdemo.handlers.CommandRequestHandler;
import whiz.examples.httphostdemo.handlers.EchoRequestHandler;
import whiz.examples.httphostdemo.handlers.ShutdownRequestHandler;
import whiz.net.servers.HttpHost;

/**
 * Simple http host demo.
 */
public class HttpHostDemo extends Whiz {

	static final HttpHost HOST = new HttpHost(5000);

    public static void main(final String[] args) {
		Whiz.DEVELOPMENT = true;
		HOST.registerHandlers(
			new ClientRequestHandler("/"),
			new EchoRequestHandler("/echo"),
			new CommandRequestHandler("/command"),
			new ShutdownRequestHandler("/shutdown")
		);
		HOST.start();
		print("listening at " + HOST.getPort() + " ...");
    }

}
