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

	/**
	 * The standard loopback ip address (127.0.0.1).
	 */
	public static final String LOOPBACK_IP = "127.0.0.1";

	/**
	 * Gets the local host internet address object instance.
	 * 
	 * @return the local host internet address object instance
	 */
	public static final InetAddress getInetAddress() {
		try {
			return InetAddress.getLocalHost();
		} catch (final Exception e) {
			GEH.setLastException(e);
			return null;
		}
	}

	/**
	 * Gets the host name for the local host.
	 * 
	 * @return the host name for the local host
	 */
	public static final String getName() {
		final InetAddress localHost = getInetAddress();
		return localHost != null ? localHost.getHostName() : null;
	}

	/**
	 * Finds a free TCP port.
	 * 
	 * @return a free TCP port
	 */
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
