/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net;

// NOTE: built following RFC 2821
public class SMTPReplies extends MailReplies {

	public static final String OK = "250";
	public static final String INTERMEDIATE = "354";

	public static final boolean isValid(final String response) {
		return isValid(response, OK) || isValid(response, INTERMEDIATE);
	}

}
