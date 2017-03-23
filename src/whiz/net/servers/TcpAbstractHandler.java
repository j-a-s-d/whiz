/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net.servers;

import ace.arrays.ByteArrays;
import ace.concurrency.Threads;
import ace.interfaces.Evaluable;
import ace.interfaces.Treater;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import whiz.WhizObject;

abstract class TcpAbstractHandler extends WhizObject implements Runnable {

	private final Socket _socket;
	private InputStream _inputStream;
	private OutputStream _outputStream;
	private final Treater<byte[]> _writingAdapter;
	private final Treater<byte[]> _readingAdapter;

	public TcpAbstractHandler(final Class<?> clazz, final Socket socket) {
		this(clazz, socket, null, null);
	}

	public TcpAbstractHandler(final Class<?> clazz, final Socket socket, final Treater<byte[]> readingAdapter, final Treater<byte[]> writingAdapter) {
		super(clazz);
		_socket = setSocket(socket);
		_readingAdapter = readingAdapter;
		_writingAdapter = writingAdapter;
	}

	final Socket setSocket(final Socket socket) {
		if (assigned(socket)) {
			try {
				_inputStream = socket.getInputStream();
			} catch (final Exception e) {
				setLastException(e);
			}
			try {
				_outputStream = socket.getOutputStream();
			} catch (final Exception e) {
				setLastException(e);
			}
			return socket;
		}
		return null;
	}

	public Socket getSocket() {
		return _socket;
	}

	public final void close() {
		try {
			_socket.close();
		} catch (final Exception e) {
			setLastException(e);
		}
	}

	final boolean isReadable() {
		return assigned(_inputStream) && !_socket.isInputShutdown();
	}

	final boolean isWritable() {
		return assigned(_outputStream) && !_socket.isOutputShutdown();
	}

	final synchronized boolean hasDataAvailable() throws Exception {
		return isReadable() && _inputStream.available() > 0;
	}

	public final boolean hasDataToReceive() {
		try {
			return hasDataAvailable();
		} catch (final Exception e) {
			setLastException(e);
			return false;
		}
	}

	public boolean hasDataToReceiveAfterWait(final long ensureDelay) {
		return Boolean.TRUE.equals(Threads.delayedSandboxedEvaluation(ensureDelay, Boolean.FALSE, new Evaluable() {
			@Override public Boolean evaluate(final Object... parameters) throws Exception {
				return hasDataAvailable();
			}
		}));
	}

	final byte[] readBuffer() {
		if (isReadable()) {
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
		}
		return null;
	}

	final boolean writeBuffer(final byte[] bytes) {
		if (isWritable()) {
			try {
				_outputStream.write(assigned(_writingAdapter) ? _writingAdapter.treat(bytes) : bytes);
				_outputStream.flush();
				return true;
			} catch (final Exception e) {
				setLastException(e);
			}
		}
		return false;
	}

}
