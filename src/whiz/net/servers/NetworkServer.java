/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net.servers;

import java.net.InetSocketAddress;
import whiz.net.NetworkConnection;

abstract class NetworkServer extends NetworkConnection {

	public static boolean DEFAULT_AUTOSTART = false;

	private InetSocketAddress _address;
	private String _name;
	private Integer _threads;

	public final String getHost() {
		return assigned(_address) ? _address.getHostName() : null;
	}

	public final Integer getPort() {
		return assigned(_address) ? _address.getPort() : null;
	}

	protected final InetSocketAddress getAddress() {
		return _address;
	}

	protected final NetworkServer setAddress(final InetSocketAddress value) {
		_address = value;
		return this;
	}

	public String getName() {
		return _name;
	}

	public NetworkServer setName(final String value) {
		_name = value;
		return this;
	}

	protected Integer getExecutionThreadsMaxCount() {
		return _threads;
	}

	public NetworkServer setExecutionThreadsMaxCount(final Integer value) {
		_threads = value;
		return this;
	}

	abstract protected boolean initialize(final InetSocketAddress address);

	abstract public void onStartListening();

	abstract public void onStopListening();

}
