/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net.clients;

import ace.concurrency.Threads;
import ace.interfaces.Evaluable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class TcpConnection extends NetworkClient {

	private int _port;
	private String _host;
	protected Socket _socket;
	protected InputStream _inputStream;
	protected OutputStream _outputStream;
	protected InputStreamReader _reader;
	protected OutputStreamWriter _writer;

	public TcpConnection(final String host, final int port) {
		_host = host;
		_port = port;
	}

	protected final void connect() throws IOException {
		_socket = new Socket(_host, _port);
		_reader = new InputStreamReader(_inputStream = _socket.getInputStream());
		_writer = new OutputStreamWriter(_outputStream = _socket.getOutputStream());
	}

	protected final void disconnect() throws IOException {
		_socket.close();
	}

	public final String getHost() {
		return _host;
	}

	public final void setHost(final String host) {
		_host = host;
	}

	public final int getPort() {
		return _port;
	}

	public final void setPort(final int port) {
		_port = port;
	}

	public final Socket getSocket() {
		return _socket;
	}

	public final InputStreamReader getReader() {
		return _reader;
	}

	public final OutputStreamWriter getWriter() {
		return _writer;
	}

	public final boolean hasDataAvailable() throws IOException {
		return _socket.getInputStream().available() > 0;
	}

	public final boolean hasDataToReceive() {
		try {
			return _socket.getInputStream().available() > 0;
		} catch (final Exception e) {
			GEH.setLastException(e);
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

}
