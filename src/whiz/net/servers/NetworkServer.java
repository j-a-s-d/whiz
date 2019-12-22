/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net.servers;

import java.net.InetSocketAddress;
import whiz.net.NetworkConnection;

/**
 * Abstract network server class.
 */
abstract class NetworkServer extends NetworkConnection {

	public static boolean DEFAULT_AUTOSTART = false;

	private InetSocketAddress _address;
	private String _name;
	private Integer _threads;

	/**
	 * Constructor accepting a class instance.
	 * 
	 * @param clazz 
	 */
	public NetworkServer(final Class<?> clazz) {
		super(clazz);
	}

	/**
	 * Gets the host name.
	 * 
	 * @return the host name
	 */
	public final String getHost() {
		return assigned(_address) ? _address.getHostName() : null;
	}

	/**
	 * Gets the port.
	 * 
	 * @return the port
	 */
	public final Integer getPort() {
		return assigned(_address) ? _address.getPort() : null;
	}

	/**
	 * Gets the internet socket address instance.
	 * 
	 * @return the internet socket address instance
	 */
	protected final InetSocketAddress getAddress() {
		return _address;
	}

	/**
	 * Sets the internet socket address instance.
	 * 
	 * @param value
	 */
	protected final NetworkServer setAddress(final InetSocketAddress value) {
		_address = value;
		return this;
	}

	/**
	 * Gets the server name.
	 * 
	 * @return the server name
	 */
	public String getName() {
		return _name;
	}

	/**
	 * Sets the server name.
	 * 
	 * @param value
	 * @return itself
	 */
	public NetworkServer setName(final String value) {
		_name = value;
		return this;
	}

	/**
	 * Gets the maximum amount of execution threads.
	 * 
	 * @return the maximum amount of execution threads
	 */
	protected Integer getExecutionThreadsMaxCount() {
		return _threads;
	}

	/**
	 * Sets the maximum amount of execution threads.
	 * 
	 * @param value
	 * @return itself
	 */
	public NetworkServer setExecutionThreadsMaxCount(final Integer value) {
		_threads = value;
		return this;
	}

	abstract protected boolean initialize(final InetSocketAddress address);

	abstract public void onStartListening();

	abstract public void onStopListening();

}
