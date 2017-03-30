/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net;

import ace.Sandboxed;
import java.net.InetAddress;
import java.net.ServerSocket;
import whiz.Whiz;

/**
 * Utility class for working with the local host.
 */
public class LocalHost extends Whiz {

	public static final String LOOPBACK_IP = "127.0.0.1";

	public static final InetAddress getInetAddress() {
		try {
			return InetAddress.getLocalHost();
		} catch (final Exception e) {
			GEH.setLastException(e);
			return null;
		}
	}

	public static final String getName() {
		final InetAddress localHost = getInetAddress();
		return localHost != null ? localHost.getHostName() : null;
	}

	public static final Integer findFreePort() {
		return new Sandboxed<Integer>() {
			@Override public Integer run() throws Exception {
				ServerSocket socket;
				final Integer result = (socket = new ServerSocket(0) {{
					setReuseAddress(true);
				}}).getLocalPort();
				socket.close();
				return result;
			}
		}.go();
	}

}
