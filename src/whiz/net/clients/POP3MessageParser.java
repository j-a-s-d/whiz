/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net.clients;

import ace.constants.STRINGS;
import ace.containers.Lists;
import ace.containers.Maps;
import ace.text.Strings;
import java.util.List;
import java.util.Map;
import whiz.WhizObject;

/**
 * POP3 message parser class.
 */
public class POP3MessageParser extends WhizObject {

	/**
	 * Default constructor.
	 */
	public POP3MessageParser() {
		super(POP3MessageParser.class);
	}

	private void process(final POP3Message message, final List<String> content) {
		final Map<String, String> headerMap = Maps.make();
		boolean parsingHeaders = true;
		final List<String> body = Lists.make();
		String line = STRINGS.EMPTY;
		for (final String item : content) {
			final String trimmedLine = item.trim();
			if (trimmedLine.equals(MailConnection.TERMINAL_OCTET)) {
				break;
			}
			if (trimmedLine.length() != 0 && parsingHeaders) {
				final String[] parts = item.split(STRINGS.COLON, 2);
				if (parts.length > 1) {
					headerMap.put(parts[0].trim(), parts[1].trim());
				} else {
					headerMap.put(parts[0].trim(), null);
				}
			} else {
				parsingHeaders = false;
			}
			if (!parsingHeaders) {
				if (item.endsWith(STRINGS.EQUAL)) {
					line += item.substring(1, item.length() - 1);
				} else {
					line += item;
					body.add(line);
					line = STRINGS.EMPTY;
				}
			}
		}
		if (Strings.hasText(line)) {
			body.add(line);
		}
		message.setHeaders(headerMap);
		message.setBody(body);
	}

	/**
	 * Parses the specified list of strings as a POP3 message instance.
	 * 
	 * @param rawContent
	 * @return the resulting POP3 message instance
	 */
	public POP3Message parse(final List<String> rawContent) {
		final POP3Message result = new POP3Message();
		try {
			result.setRawContent(rawContent);
			process(result, rawContent);
		} catch (final Exception e) {
			setLastException(e);
			result._hasErrors = true;
		}
		return result;
	}

}
