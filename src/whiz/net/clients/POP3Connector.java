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

// NOTE: built following RFC 1725
public class POP3Connector extends MailConnection {

	public static final int PORT = 110;

	private final POP3MessageParser _messageParser = new POP3MessageParser();
	private String _errorMessage;

	public POP3Connector(final String host) {
		super(host, PORT);
	}

	public POP3Connector(final String host, final int port) {
		super(host, port);
	}

	public final String getLastErrorMessageFromServer() {
		return _errorMessage;
	}

	public final void forgetLastErrorMessageFromServer() {
		_errorMessage = null;
	}

	public final void send(final String command, final String parameters) throws IOException {
		send(command + STRINGS.SPACE + parameters);
	}

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
