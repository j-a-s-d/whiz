/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net.clients;

import ace.arrays.ByteArrays;
import ace.interfaces.Treater;

public class TcpConnector extends TcpConnection {

	private final Treater<byte[]> _writingAdapter;
	private final Treater<byte[]> _readingAdapter;

	public TcpConnector(final String host, final int port) {
		this(host, port, null, null);
	}

	public TcpConnector(final String host, final int port, final Treater<byte[]> readingAdapter, final Treater<byte[]> writingAdapter) {
		super(host, port);
		_readingAdapter = readingAdapter;
		_writingAdapter = writingAdapter;
	}

	public final boolean open() {
		try {
			connect();
			return getSocket().isConnected() && assigned(getReader()) && assigned(getWriter());
		} catch (final Exception e) {
			setLastException(e);
			return false;
		}
	}

	public final boolean close() {
		try {
			disconnect();
			return getSocket().isClosed();
		} catch (final Exception e) {
			setLastException(e);
			return false;
		}
	}

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
