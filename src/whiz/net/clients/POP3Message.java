/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net.clients;

import ace.containers.Lists;
import ace.containers.Maps;
import java.util.List;
import java.util.Map;
import whiz.WhizObject;
import whiz.net.MailHeaders;

// NOTE: built following RFC 1725
public class POP3Message extends WhizObject {

	boolean _hasErrors = false;
	private List<String> _rawContent = Lists.make();
	private Map<String, String> _headers = Maps.make();
	private List<String> _body = Lists.make();

	public boolean hasErrors() {
		return _hasErrors;
	}

	public List<String> getRawContent() {
		return _rawContent;
	}

	public void setRawContent(final List<String> rawContent) {
		_rawContent = rawContent;
	}

	public List<String> getBody() {
		return _body;
	}

	public void setBody(final List<String> body) {
		_body = body;
	}

	public void setHeaders(final Map<String, String> map) {
		_headers = map;
	}

	public Map<String, String> getHeaders() {
		return _headers;
	}

	public String getHeader(final String header) {
		return _headers.get(header);
	}

	public String getFrom() {
		return getHeader(MailHeaders.FROM);
	}

	public String getTo() {
		return getHeader(MailHeaders.TO);
	}

	public String getSubject() {
		return getHeader(MailHeaders.SUBJECT);
	}

	public String getDate() {
		return getHeader(MailHeaders.DATE);
	}

}
