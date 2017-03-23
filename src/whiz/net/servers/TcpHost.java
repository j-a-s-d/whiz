/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net.servers;

import java.net.InetSocketAddress;

public abstract class TcpHost extends TcpStand {

	public TcpHost(final int port) {
		this(TcpHost.class, port);
	}

	public TcpHost(final Class<?> clazz, final int port) {
		this(clazz, port, DEFAULT_AUTOSTART);
	}

	public TcpHost(final int port, final boolean autostart) {
		this(TcpHost.class, port, autostart);
	}

	public TcpHost(final Class<?> clazz, final int port, final boolean autostart) {
		this(clazz, new InetSocketAddress(port), autostart);
	}

	public TcpHost(final InetSocketAddress address) {
		this(TcpHost.class, address);
	}

	public TcpHost(final Class<?> clazz, final InetSocketAddress address) {
		this(clazz, address, DEFAULT_AUTOSTART);
	}

	public TcpHost(final InetSocketAddress address, final boolean autostart) {
		this(TcpHost.class, address, autostart);
	}

	public TcpHost(final Class<?> clazz, final InetSocketAddress address, final boolean autostart) {
		super(clazz);
		if (initialize(address) && autostart) {
			start();
		}
	}

	@Override public final String getName() {
		return super.getName();
	}

	@Override public TcpHost setName(final String value) {
		return (TcpHost) super.setName(value);
	}

	@Override public void onStartListening() {
		// NOTE: override this in inherited classes to control the event
	}

	@Override public void onStopListening() {
		// NOTE: override this in inherited classes to control the event
	}
}
