/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net.clients;

import ace.constants.STRINGS;
import ace.containers.Lists;
import java.util.List;
import whiz.net.MailHeaders;
import whiz.net.SMTPCommands;
import whiz.net.SMTPReplies;

/**
 * Useful SMTP connector class.
 * 
 * NOTE: built following RFC 2821
 */
public class SMTPConnector extends MailConnection {

	public static final int PORT = 25;

	protected String _from = STRINGS.EMPTY;
	protected String _to = STRINGS.EMPTY;
	protected String _subject = STRINGS.EMPTY;
	protected String _date = STRINGS.EMPTY;
	protected List<String> _body = Lists.make();
	private String _lastReply;

	/**
	 * Constructor accepting a host.
	 * 
	 * @param host
	 */
	public SMTPConnector(final String host) {
		super(host, PORT);
	}

	/**
	 * Constructor accepting a host and a port.
	 * 
	 * @param host
	 * @param port 
	 */
	public SMTPConnector(final String host, final int port) {
		super(host, port);
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
			send(SMTPCommands.QUIT);
			result = assigned(receive());
			disconnect();
		} catch (final Exception e) {
			setLastException(e);
		}
		return result;
	}

	@SuppressWarnings("PMD.SimplifyStartsWith")
	private String ensureAddressFormat(final String address) {
		String result = address;
		if (!address.startsWith("<")) {
			result = "<" + address;
		}
		if (!address.endsWith(">")) {
			result += ">";
		}
		return result;
	}

	/**
	 * Sets the from address information to the specified string value.
	 * 
	 * @param from 
	 */
	public final void setFrom(final String from) {
		_from = ensureAddressFormat(from);
	}

	/**
	 * Sets the to address information to the specified string value.
	 * 
	 * @param to 
	 */
	public final void setTo(final String to) {
		_to = ensureAddressFormat(to);
	}

	/**
	 * Sets the to subject information to the specified string value.
	 * 
	 * @param subject 
	 */
	public final void setSubject(final String subject) {
		_subject = subject;
	}

	/**
	 * Sets the to date information to the specified string value.
	 * 
	 * @param date 
	 */
	public final void setDate(final String date) {
		_date = date;
	}

	/**
	 * Sets the message body content from the specified list of strings.
	 * 
	 * @param body 
	 */
	public final void setBody(final List<String> body) {
		_body = body;
	}

	/**
	 * Gets the last SMTP message reply string.
	 * 
	 * @return the last SMTP message reply string
	 */
	public final String getLastSMTPMessageReply() {
		return _lastReply;
	}

	/**
	 * Sets the SMTP message.
	 * 
	 * @return <tt>true</tt> if the operation was successful, <tt>false</tt> otherwise
	 */
	public final boolean sendMessage() {
		boolean result = false;
		if (assigned(_from) && assigned(_to) && assigned(_subject) && assigned(_date)) {
			try {
				send(SMTPCommands.HELO + STRINGS.SPACE + getHost());
				_lastReply = receive();
				if (!SMTPReplies.isValid(_lastReply)) {
					return result;
				}
				send(SMTPCommands.MAIL_FROM + STRINGS.SPACE + _from);
				_lastReply = receive();
				if (!SMTPReplies.isValid(_lastReply)) {
					return result;
				}
				send(SMTPCommands.RCPT_TO + STRINGS.SPACE + _to);
				_lastReply = receive();
				if (!SMTPReplies.isValid(_lastReply)) {
					return result;
				}
				send(SMTPCommands.DATA);
				_lastReply = receive();
				if (!SMTPReplies.isValid(_lastReply)) {
					return result;
				}
				send(MailHeaders.FROM + ": " + _from);
				send(MailHeaders.TO + ": " + _to);
				send(MailHeaders.SUBJECT + ": " + _subject);
				send(MailHeaders.DATE + ": " + _date);
				send(STRINGS.EMPTY);
				for (final String s : _body) {
					send(s);
				}
				send(TERMINAL_OCTET);
				_lastReply = receive();
				if (!SMTPReplies.isValid(_lastReply)) {
					return result;
				}
				result = true;
			} catch (final Exception e) {
				setLastException(e);
			}
		}
		return result;
	}

}
