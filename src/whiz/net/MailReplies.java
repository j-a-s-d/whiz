/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net;

import ace.text.Strings;
import whiz.Whiz;

public class MailReplies extends Whiz {

	protected static final boolean isValid(final String response, final String ok) {
		return Strings.hasText(response) && response.startsWith(ok);
	}

}
