/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net.clients;

import ace.constants.STRINGS;
import ace.containers.Lists;
import ace.containers.Maps;
import java.io.IOException;
import java.util.List;
import java.util.TreeMap;
import whiz.net.POP3Commands;
import whiz.net.POP3Replies;

/**
 * Useful POP3 connector class.
 * 
 * NOTE: built following RFC 1725
 */
public class POP3Connector extends MailConnection {

	public static final int PORT = 110;

	private final POP3MessageParser _messageParser = new POP3MessageParser();
	private String _errorMessage;

	/**
	 * Constructor accepting a host.
	 * 
	 * @param host
	 */
	public POP3Connector(final String host) {
		super(host, PORT);
	}

	/**
	 * Constructor accepting a host and a port.
	 * 
	 * @param host
	 * @param port 
	 */
	public POP3Connector(final String host, final int port) {
		super(host, port);
	}

	/**
	 * Gets the last error message from server.
	 * 
	 * @return the last error message from server
	 */
	public final String getLastErrorMessageFromServer() {
		return _errorMessage;
	}

	/**
	 * Drops the last error message from server.
	 */
	public final void forgetLastErrorMessageFromServer() {
		_errorMessage = null;
	}

	/**
	 * Sends the specified command with the specified parameters to the destination.
	 * 
	 * @param command
	 * @param parameters
	 * @throws IOException 
	 */
	public final void send(final String command, final String parameters) throws IOException {
		send(command + STRINGS.SPACE + parameters);
	}

	/**
	 * Opens the connection to the destination.
	 * 
	 * @return <tt>true</tt> if the operation was successful, <tt>false</tt> otherwise
	 */
	@Override public final boolean open() {
		boolean result = false;
		try {
			initialize();
			result = assigned(receive());
		} catch (final Exception e) {
			setLastException(e);
		}
		return result;
	}

	/**
	 * Closes the connection to the destination.
	 * 
	 * @return <tt>true</tt> if the operation was successful, <tt>false</tt> otherwise
	 */
	@Override public final boolean close() {
		boolean result = false;
		try {
			send(POP3Commands.QUIT);
			final String r = receive();
			result = POP3Replies.isValid(r);
			if (!result) {
				_errorMessage = POP3Replies.extractErrorMessage(r);
			}
			disconnect();
		} catch (final Exception e) {
			setLastException(e);
		}
		return result;
	}

	/**
	 * Sends the specified credentials to the destination.
	 * 
	 * @param username
	 * @param password
	 * @return <tt>true</tt> if the operation was successful, <tt>false</tt> otherwise
	 */
	public final boolean sendCredentials(final String username, final String password) {
		boolean result = false;
		try {
			send(POP3Commands.USER, username);
			if (POP3Replies.isValid(receive())) {
				send(POP3Commands.PASS, password);
				if (POP3Replies.isValid(receive())) {
					result = true;
				}
			}
		} catch (final Exception e) {
			setLastException(e);
		}
		return result;
	}

	/**
	 * Gets the message count.
	 * 
	 * @return the message count
	 */
	public final int retrieveMessageCount() {
		int result = 0;
		try {
			send(POP3Commands.STAT);
			final String r = receive();
			if (POP3Replies.isValid(r)) {
				final String[] parts = r.split(STRINGS.SPACE);
				result = Integer.parseInt(parts[1]);
			} else {
				_errorMessage = POP3Replies.extractErrorMessage(r);
			}
		} catch (final Exception e) {
			setLastException(e);
		}
		return result;
	}

	/**
	 * Deletes the message at the specified index.
	 * 
	 * @param index
	 * @return <tt>true</tt> if the operation was successful, <tt>false</tt> otherwise
	 */
	public final boolean eraseMessage(final int index) {
		boolean result = false;
		try {
			send(POP3Commands.DELE, Integer.toString(index));
			final String r = receive();
			result = POP3Replies.isValid(r);
			if (!result) {
				_errorMessage = POP3Replies.extractErrorMessage(r);
			}
		} catch (final Exception e) {
			setLastException(e);
		}
		return result;
	}

	/**
	 * Gets the raw content as a list of strings using the specified timeout.
	 * 
	 * @param ensureDelay
	 * @return the raw content as a list of strings
	 * @throws IOException 
	 */
	private List<String> retrieveRawContent(final long ensureDelay) throws IOException {
		final List<String> content = Lists.make();
		boolean terminated = false;
		while (!terminated) {
			final String response = receive();
			final String trimmedResponse = response.trim();
			if (trimmedResponse.equals(TERMINAL_OCTET)) {
				terminated = !hasDataToReceiveAfterWait(ensureDelay);
			}
			content.add(response + STRINGS.EOL);
		}
		return content;
	}

	/**
	 * Retrieves the message at the specified index using the specified timeout
	 * @param index
	 * @param ensureDelay
	 * @return the resulting message
	 */
	public final POP3Message fetchMessage(final int index, final long ensureDelay) {
		try {
			send(POP3Commands.RETR, Integer.toString(index));
			final String r = receive();
			if (POP3Replies.isValid(r)) {
				return _messageParser.parse(retrieveRawContent(ensureDelay));
			} else {
				_errorMessage = POP3Replies.extractErrorMessage(r);
			}
		} catch (final Exception e) {
			setLastException(e);
		}
		return null;
	}

	/**
	 * Retrieves the message list as a tree map.
	 * 
	 * @return the message list as a tree map
	 */
	public final TreeMap<Integer, Integer> getMessageList() {
		final TreeMap<Integer, Integer> result = Maps.makeTree();
		try {
			send(POP3Commands.LIST);
			final String countResponse = receive();
			if (POP3Replies.isValid(countResponse)) {
				while (true) {
					final String response = receive();
					final String trimmedResponse = response.trim();
					if (trimmedResponse.equals(TERMINAL_OCTET)) {
						break;
					}
					final String[] parts = response.split(STRINGS.SPACE, 2);
					try {
						result.put(Integer.parseInt(parts[0].trim()), Integer.parseInt(parts[1].trim()));
					} catch (final Exception e) {
						setLastException(e);
					}
				}
			}
		} catch (final Exception e) {
			setLastException(e);
		}
		return result;
	}

}
