/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net.servers;

import java.net.InetSocketAddress;

public abstract class TcpHost extends TcpStand {

	public TcpHost(final int port) {
		this(port, DEFAULT_AUTOSTART);
	}

	public TcpHost(final int port, final boolean autostart) {
		this(new InetSocketAddress(port), autostart);
	}

	public TcpHost(final InetSocketAddress address) {
		this(address, DEFAULT_AUTOSTART);
	}

	public TcpHost(final InetSocketAddress address, final boolean autostart) {
		if (initialize(address) && autostart) {
			start();
		}
	}

	@Override public final String getName() {
		return super.getName();
	}

	@Override public final TcpHost setName(final String value) {
		return (TcpHost) super.setName(value);
	}

	@Override public void onStartListening() {
		// NOTE: override this in inherited classes to control the event
	}

	@Override public void onStopListening() {
		// NOTE: override this in inherited classes to control the event
	}
}
