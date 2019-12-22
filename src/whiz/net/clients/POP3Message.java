/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net.clients;

import ace.containers.Lists;
import ace.containers.Maps;
import java.util.List;
import java.util.Map;
import whiz.WhizObject;
import whiz.net.MailHeaders;

/**
 * Useful POP3 message class.
 * 
 * NOTE: built following RFC 1725
 */
public class POP3Message extends WhizObject {

	boolean _hasErrors = false;
	private List<String> _rawContent = Lists.make();
	private Map<String, String> _headers = Maps.make();
	private List<String> _body = Lists.make();

	/**
	 * Default constructor.
	 */
	public POP3Message() {
		super(POP3Message.class);
	}

	/**
	 * Determines if there were errors.
	 * 
	 * @return <tt>true</tt> if there were errors, <tt>false</tt> otherwise
	 */
	public boolean hasErrors() {
		return _hasErrors;
	}

	/**
	 * Gets the raw POP3 message content as a list of strings.
	 * 
	 * @return the resulting list of strings
	 */
	public List<String> getRawContent() {
		return _rawContent;
	}

	/**
	 * Sets the raw POP3 message content from the specified list of strings.
	 * 
	 * @param rawContent
	 */
	public void setRawContent(final List<String> rawContent) {
		_rawContent = rawContent;
	}

	/**
	 * Gets the body as a list of strings.
	 * 
	 * @return the body as list of strings
	 */
	public List<String> getBody() {
		return _body;
	}

	/**
	 * Sets the body from the specified list of strings.
	 * 
	 * @param body
	 */
	public void setBody(final List<String> body) {
		_body = body;
	}

	/**
	 * Sets the headers from the specified map.
	 * 
	 * @param map 
	 */
	public void setHeaders(final Map<String, String> map) {
		_headers = map;
	}

	/**
	 * Gets the headers map.
	 * 
	 * @return the headers map
	 */
	public Map<String, String> getHeaders() {
		return _headers;
	}

	/**
	 * Gets the specified header value.
	 * 
	 * @param header
	 * @return the specified header value
	 */
	public String getHeader(final String header) {
		return _headers.get(header);
	}

	/**
	 * Gets the FROM header value.
	 * 
	 * @return the FROM header value
	 */
	public String getFrom() {
		return getHeader(MailHeaders.FROM);
	}

	/**
	 * Gets the TO header value.
	 * 
	 * @return the TO header value
	 */
	public String getTo() {
		return getHeader(MailHeaders.TO);
	}

	/**
	 * Gets the SUBJECT header value.
	 * 
	 * @return the SUBJECT header value
	 */
	public String getSubject() {
		return getHeader(MailHeaders.SUBJECT);
	}

	/**
	 * Gets the DATE header value.
	 * 
	 * @return the DATE header value
	 */
	public String getDate() {
		return getHeader(MailHeaders.DATE);
	}

}
