/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net;

/**
 * POP3 replies class.
 * 
 * NOTE: built following RFC 2821
 */
public class SMTPReplies extends MailReplies {

	public static final String OK = "250";
	public static final String INTERMEDIATE = "354";

	/**
	 * Determines if the specified response is valid.
	 * 
	 * @param response
	 * @return <tt>true</tt> if the specified response is valid, <tt>false</tt> otherwise
	 */
	public static final boolean isValid(final String response) {
		return isValid(response, OK) || isValid(response, INTERMEDIATE);
	}

}
