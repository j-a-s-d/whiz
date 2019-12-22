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

/**
 * Useful TCP connection class.
 */
public class TcpConnection extends NetworkClient {

	private int _port;
	private String _host;
	protected Socket _socket;
	protected InputStream _inputStream;
	protected OutputStream _outputStream;
	protected InputStreamReader _reader;
	protected OutputStreamWriter _writer;

	/**
	 * Constructor accepting a host and a port.
	 * 
	 * @param host
	 * @param port 
	 */
	public TcpConnection(final String host, final int port) {
		super(TcpConnection.class);
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

	/**
	 * Gets the connection destination host.
	 * 
	 * @return the connection destination host
	 */
	public final String getHost() {
		return _host;
	}

	/**
	 * Sets the connection destination host.
	 * 
	 * @param host 
	 */
	public final void setHost(final String host) {
		_host = host;
	}

	/**
	 * Gets the connection destination port.
	 * 
	 * @return the connection destination port
	 */
	public final int getPort() {
		return _port;
	}

	/**
	 * Sets the connection destination port.
	 * 
	 * @param port 
	 */
	public final void setPort(final int port) {
		_port = port;
	}

	/**
	 * Gets the connection socket.
	 * 
	 * @return the connection socket
	 */
	public final Socket getSocket() {
		return _socket;
	}

	/**
	 * Gets the connection socket reader.
	 * 
	 * @return the connection socket reader
	 */
	public final InputStreamReader getReader() {
		return _reader;
	}

	/**
	 * Gets the connection socket writer.
	 * 
	 * @return the connection socket writer
	 */
	public final OutputStreamWriter getWriter() {
		return _writer;
	}

	/**
	 * Determines if the connection has data available to be read.
	 * 
	 * @return <tt>true</tt> if the connection has data available to be read, <tt>false</tt> if not
	 * @throws IOException 
	 */
	public final boolean hasDataAvailable() throws IOException {
		return _socket.getInputStream().available() > 0;
	}

	/**
	 * Determines in a silent mode (catching exceptions) if the connection has data available to be read.
	 * 
	 * @return <tt>true</tt> if the connection has data available to be read, <tt>false</tt> otherwise
	 */
	public final boolean hasDataToReceive() {
		try {
			return _socket.getInputStream().available() > 0;
		} catch (final Exception e) {
			GEH.setLastException(e);
			return false;
		}
	}

	/**
	 * Determines in a silent mode (catching exceptions) if the connection has data available to be read after waiting the specified delay time.
	 * 
	 * @param ensureDelay
	 * @return <tt>true</tt> if the connection has data available to be read, <tt>false</tt> otherwise
	 */
	public boolean hasDataToReceiveAfterWait(final long ensureDelay) {
		return Boolean.TRUE.equals(Threads.delayedSandboxedEvaluation(ensureDelay, Boolean.FALSE, new Evaluable() {
			@Override public Boolean evaluate(final Object... parameters) throws Exception {
				return hasDataAvailable();
			}
		}));
	}

}
