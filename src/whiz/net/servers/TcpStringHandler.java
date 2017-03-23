/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net.servers;

import ace.interfaces.Treater;
import java.net.Socket;

public abstract class TcpStringHandler extends TcpAbstractHandler {

	private String _charset;

	public TcpStringHandler(final Socket socket) {
		this(socket, null, null);
	}

	public TcpStringHandler(final Socket socket, final Treater<byte[]> readingAdapter, final Treater<byte[]> writingAdapter) {
		super(socket, readingAdapter, writingAdapter);
		_charset = "UTF-8";
	}

	public String getCharset() {
		return _charset;
	}

	public void setCharset(final String value) {
		_charset = value;
	}

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
