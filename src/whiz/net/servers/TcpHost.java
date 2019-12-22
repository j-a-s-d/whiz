/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net.servers;

import java.net.InetSocketAddress;

/**
 * TCP host class.
 */
public abstract class TcpHost extends TcpStand {

	/**
	 * Constructor accepting a port.
	 * 
	 * @param port 
	 */
	public TcpHost(final int port) {
		this(TcpHost.class, port);
	}

	/**
	 * Constructor accepting a class instance and a port.
	 * 
	 * @param clazz
	 * @param port 
	 */
	public TcpHost(final Class<?> clazz, final int port) {
		this(clazz, port, DEFAULT_AUTOSTART);
	}

	/**
	 * Constructor accepting a port and an auto start flag.
	 * 
	 * @param port 
	 * @param autostart 
	 */
	public TcpHost(final int port, final boolean autostart) {
		this(TcpHost.class, port, autostart);
	}

	/**
	 * Constructor accepting a class instance, a port and an auto start flag.
	 * 
	 * @param clazz
	 * @param port 
	 * @param autostart 
	 */
	public TcpHost(final Class<?> clazz, final int port, final boolean autostart) {
		this(clazz, new InetSocketAddress(port), autostart);
	}

	/**
	 * Constructor accepting an internet socket address.
	 * 
	 * @param address 
	 */
	public TcpHost(final InetSocketAddress address) {
		this(TcpHost.class, address);
	}

	/**
	 * Constructor accepting a class instance and an internet socket address.
	 * 
	 * @param clazz
	 * @param address 
	 */
	public TcpHost(final Class<?> clazz, final InetSocketAddress address) {
		this(clazz, address, DEFAULT_AUTOSTART);
	}

	/**
	 * Constructor accepting an internet socket address and an auto start flag.
	 * 
	 * @param address 
	 * @param autostart 
	 */
	public TcpHost(final InetSocketAddress address, final boolean autostart) {
		this(TcpHost.class, address, autostart);
	}

	/**
	 * Constructor accepting a class instance, an internet socket address and an auto start flag.
	 * 
	 * @param clazz
	 * @param address 
	 * @param autostart 
	 */
	public TcpHost(final Class<?> clazz, final InetSocketAddress address, final boolean autostart) {
		super(clazz);
		if (initialize(address) && autostart) {
			start();
		}
	}

	/**
	 * Gets the server name.
	 * 
	 * @return the server name
	 */
	@Override public final String getName() {
		return super.getName();
	}

	/**
	 * Sets the server name.
	 * 
	 * @param value
	 * @return itself
	 */
	@Override public TcpHost setName(final String value) {
		return (TcpHost) super.setName(value);
	}

	/**
	 * On start listening event handler.
	 * 
	 * NOTE: override this method in inherited classes to control the event
	 */
	@Override public void onStartListening() {
		//
	}

	/**
	 * On stop listening event handler.
	 * 
	 * NOTE: override this method in inherited classes to control the event
	 */
	@Override public void onStopListening() {
		//
	}

}
