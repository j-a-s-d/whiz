/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net;

import java.net.InetAddress;
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

}
