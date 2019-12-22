/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net.servers;

import ace.interfaces.Treater;
import java.net.Socket;

/**
 * TCP string handler class.
 */
public abstract class TcpStringHandler extends TcpAbstractHandler {

	private String _charset;

	/**
	 * Constructor accepting a socket.
	 * 
	 * @param socket 
	 */
	public TcpStringHandler(final Socket socket) {
		this(TcpStringHandler.class, socket, null, null);
	}

	/**
	 * Constructor accepting a class instance and a socket.
	 * 
	 * @param clazz
	 * @param socket 
	 */
	public TcpStringHandler(final Class<?> clazz, final Socket socket) {
		this(clazz, socket, null, null);
	}

	/**
	 * Constructor accepting a socket and adapters for reading and writing.
	 * 
	 * @param socket 
	 * @param readingAdapter 
	 * @param writingAdapter 
	 */
	public TcpStringHandler(final Socket socket, final Treater<byte[]> readingAdapter, final Treater<byte[]> writingAdapter) {
		this(TcpStringHandler.class, socket, readingAdapter, writingAdapter);
	}

	/**
	 * Constructor accepting a class instance, a socket and adapters for reading and writing.
	 * 
	 * @param clazz
	 * @param socket 
	 * @param readingAdapter 
	 * @param writingAdapter 
	 */
	public TcpStringHandler(final Class<?> clazz, final Socket socket, final Treater<byte[]> readingAdapter, final Treater<byte[]> writingAdapter) {
		super(clazz, socket, readingAdapter, writingAdapter);
		_charset = "UTF-8";
	}

	/**
	 * Gets the character set being used to write/read to/from the connection.
	 * 
	 * @return the character set being used
	 */
	public String getCharset() {
		return _charset;
	}

	/**
	 * Sets the character set being used to write/read to/from the connection.
	 * 
	 * @param value
	 */
	public void setCharset(final String value) {
		_charset = value;
	}

	/**
	 * Reads a string from the connection.
	 * 
	 * @return the resulting string
	 */
	public final String read() {
		try {
			final byte[] data = readBuffer();
			if (assigned(data)) {
				return new String(data, _charset);
			}
		} catch (final Exception e) {
			setLastException(e);
		}
		return null;
	}

	/**
	 * Writes the specified string to the connection.
	 * 
	 * @param string
	 * @return <tt>true</tt> if the operation was successful, <tt>false</tt> otherwise
	 */
	public final boolean write(final String string) {
		try {
			if (assigned(string)) {
				return writeBuffer(string.getBytes(_charset));
			}
		} catch (final Exception e) {
			setLastException(e);
		}
		return false;
	}

}
