/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net.servers;

import ace.interfaces.Treater;
import java.net.Socket;

/**
 * TCP binary handler class.
 */
public abstract class TcpBinaryHandler extends TcpAbstractHandler {

	/**
	 * Constructor accepting a socket.
	 * 
	 * @param socket 
	 */
	public TcpBinaryHandler(final Socket socket) {
		this(TcpBinaryHandler.class, socket, null, null);
	}

	/**
	 * Constructor accepting a class instance and a socket.
	 * 
	 * @param clazz
	 * @param socket 
	 */
	public TcpBinaryHandler(final Class<?> clazz, final Socket socket) {
		this(clazz, socket, null, null);
	}

	/**
	 * Constructor accepting a socket and adapters for reading and writing.
	 * 
	 * @param socket 
	 * @param readingAdapter 
	 * @param writingAdapter 
	 */
	public TcpBinaryHandler(final Socket socket, final Treater<byte[]> readingAdapter, final Treater<byte[]> writingAdapter) {
		this(TcpBinaryHandler.class, socket, readingAdapter, writingAdapter);
	}

	/**
	 * Constructor accepting a class instance, a socket and adapters for reading and writing.
	 * 
	 * @param clazz
	 * @param socket 
	 * @param readingAdapter 
	 * @param writingAdapter 
	 */
	public TcpBinaryHandler(final Class<?> clazz, final Socket socket, final Treater<byte[]> readingAdapter, final Treater<byte[]> writingAdapter) {
		super(clazz, socket, readingAdapter, writingAdapter);
	}

	/**
	 * Reads the available bytes from the connection.
	 * 
	 * @return the resulting byte array
	 */
	public final byte[] read() {
		return readBuffer();
	}

	/**
	 * Writes the specified byte array to the connection.
	 * 
	 * @param bytes
	 * @return <tt>true</tt> if the operation was successful, <tt>false</tt> otherwise
	 */
	public final boolean write(final byte[] bytes) {
		return writeBuffer(bytes);
	}

}
