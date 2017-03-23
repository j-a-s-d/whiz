/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net;

import ace.constants.STRINGS;

// NOTE: built following RFC 1725
public class POP3Replies extends MailReplies {

	// NOTE: all commands except STAT, LIST, and UIDL receive this replies
	public static final String OK = "+OK";
	public static final String ERROR = "-ERR";

	public static final boolean isValid(final String response) {
		return isValid(response, OK);
	}

	public static final String extractErrorMessage(final String response) {
		return response.replaceFirst(POP3Replies.ERROR, STRINGS.EMPTY).trim();
	}

}
