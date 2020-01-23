/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.examples.httphostdemo.handlers;

import ace.arrays.GenericArrays;
import ace.files.Directories;
import ace.platform.OS;
import ace.platform.User;
import whiz.net.HttpMethods;
import whiz.net.servers.HttpRequest;
import whiz.net.servers.HttpStringHandler;

/**
 * Simple echo handler.
 * Try it via:
 * curl -X POST -d"ls -l" http://127.0.0.1:5000/command
 */
public class CommandRequestHandler extends HttpStringHandler {

	public static final String[] ENVIRONMENT = GenericArrays.make("LC_ALL=en_US.UTF-8", "HOME=" + User.getHomeDirectoryName());
	public static final String PATH = Directories.CURRENT_PATH;

	public CommandRequestHandler(final String route) {
		super(CommandRequestHandler.class, HttpMethods.POST_ONLY, route);
	}

	@Override protected String transact(final HttpRequest request, final String body) {
		return OS.runCommand(body, ENVIRONMENT, PATH);
	}

	@Override protected void onWrongMethod(HttpRequest hr, String string) {
		//
	}

	@Override protected void onClientException(HttpRequest hr, Throwable thrwbl, byte[] bytes) {
		//
	}

}
