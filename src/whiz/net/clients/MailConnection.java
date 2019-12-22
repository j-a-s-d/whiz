/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net.clients;

import ace.constants.STRINGS;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Abstract mail connection class.
 */
abstract class MailConnection extends TcpConnection {

	public static final String TERMINAL_OCTET = STRINGS.PERIOD;

	private String _eol = STRINGS.CR + STRINGS.LF;
	private OutputStreamWriter _localWriter;
	private BufferedReader _bufferedReader;

	/**
	 * Constructor accepting a host and a port.
	 * 
	 * @param host
	 * @param port 
	 */
	public MailConnection(final String host, final int port) {
		super(host, port);
	}

	/**
	 * Gets the EOL sequence being used in the connection.
	 * 
	 * @return the EOL sequence being used
	 */
	public final String getEndOfLine() {
		return _eol;
	}

	/**
	 * Sets the EOL sequence being used in the connection.
	 * 
	 * @param eol
	 */
	public final void setEndOfLine(final String eol) {
		_eol = eol;
	}

	protected final void initialize() throws IOException {
		connect();
		_bufferedReader = new BufferedReader(getReader());
		_localWriter = getWriter();
	}

	/**
	 * Receives a text line from the connection.
	 * 
	 * @return the resulting text line
	 * @throws IOException 
	 */
	public final String receive() throws IOException {
		return _bufferedReader.readLine();
	}

	/**
	 * Sends a text line to the connection.
	 * 
	 * @param command
	 * @throws IOException 
	 */
	public final void send(final String command) throws IOException {
		_localWriter.write(command + _eol);
		_localWriter.flush();
	}

	public abstract boolean open();

	public abstract boolean close();

}
