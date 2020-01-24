/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net.clients;

import ace.arrays.ByteArrays;
import ace.interfaces.Treater;

/**
 * Useful TCP connector class.
 */
public class TcpConnector extends TcpConnection {

	private final Treater<byte[]> _writingAdapter;
	private final Treater<byte[]> _readingAdapter;

	/**
	 * Constructor accepting a host and a port.
	 * 
	 * @param host
	 * @param port 
	 */
	public TcpConnector(final String host, final int port) {
		this(host, port, null, null);
	}

	/**
	 * Constructor accepting a host, a port and adapters for reading and writing.
	 * 
	 * @param host
	 * @param port 
	 * @param readingAdapter 
	 * @param writingAdapter 
	 */
	public TcpConnector(final String host, final int port, final Treater<byte[]> readingAdapter, final Treater<byte[]> writingAdapter) {
		super(host, port);
		_readingAdapter = readingAdapter;
		_writingAdapter = writingAdapter;
	}

	/**
	 * Tries to open the connection to the destination.
	 * 
	 * @return <tt>true</tt> if the attempt was successful, <tt>false</tt> otherwise
	 */
	public final boolean open() {
		try {
			connect();
			return getSocket().isConnected() && assigned(getReader()) && assigned(getWriter());
		} catch (final Exception e) {
			setLastException(e);
			return false;
		}
	}

	/**
	 * Tries to open the connection to the destination accepting a timeout for the socket connect and read operations.
	 * 
	 * @param timeout
	 * @return <tt>true</tt> if the attempt was successful, <tt>false</tt> otherwise
	 */
	public final boolean open(final int timeout) {
		try {
			connect(timeout);
			return getSocket().isConnected() && assigned(getReader()) && assigned(getWriter());
		} catch (final Exception e) {
			setLastException(e);
			return false;
		}
	}

	/**
	 * Tries to close the connection to the destination.
	 * 
	 * @return <tt>true</tt> if the attempt was successful, <tt>false</tt> otherwise
	 */
	public final boolean close() {
		try {
			disconnect();
			return getSocket().isClosed();
		} catch (final Exception e) {
			setLastException(e);
			return false;
		}
	}

	/**
	 * Tries to write the specified byte array to the connection.
	 * 
	 * @param buffer
	 * @return <tt>true</tt> if the attempt was successful, <tt>false</tt> otherwise
	 */
	public final boolean write(final byte[] buffer) {
		try {
			_outputStream.write(assigned(_writingAdapter) ? _writingAdapter.treat(buffer) : buffer);
			_outputStream.flush();
			return true;
		} catch (final Exception e) {
			setLastException(e);
			return false;
		}
	}

	/**
	 * Tries to read a byte array from the connection.
	 * 
	 * @return the resulting byte array
	 */
	public final byte[] read() {
		try {
			final byte[] rawBuffer = new byte[_inputStream.available()];
			final int count = _inputStream.read(rawBuffer);
			if (count > 0) {
				final byte[] buffer = ByteArrays.copy(rawBuffer, 0, count);
				return assigned(_readingAdapter) ? _readingAdapter.treat(buffer) : buffer;
			}
		} catch (final Exception e) {
			setLastException(e);
		}
		return null;
	}

}
