/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net.servers;

import ace.interfaces.Treater;
import java.net.Socket;

public abstract class TcpBinaryHandler extends TcpAbstractHandler {

	public TcpBinaryHandler(final Socket socket) {
		this(TcpBinaryHandler.class, socket, null, null);
	}

	public TcpBinaryHandler(final Class<?> clazz, final Socket socket) {
		this(clazz, socket, null, null);
	}

	public TcpBinaryHandler(final Socket socket, final Treater<byte[]> readingAdapter, final Treater<byte[]> writingAdapter) {
		this(TcpBinaryHandler.class, socket, readingAdapter, writingAdapter);
	}

	public TcpBinaryHandler(final Class<?> clazz, final Socket socket, final Treater<byte[]> readingAdapter, final Treater<byte[]> writingAdapter) {
		super(clazz, socket, readingAdapter, writingAdapter);
	}

	public final byte[] read() {
		return readBuffer();
	}

	public final boolean write(final byte[] bytes) {
		return writeBuffer(bytes);
	}

}
