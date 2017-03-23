/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net;

import whiz.Whiz;

// NOTE: built following RFC 2821

public class SMTPCommands extends Whiz {

	public static final String HELO = "HELO"; // Parameters: <domain>
	public static final String EHLO = "EHLO"; // Parameters: <domain>
	public static final String MAIL_FROM = "MAIL FROM:"; // Parameters: <address> [Mail-parameters]
	public static final String RCPT_TO = "RCPT TO:"; // Parameters: <address> [Rcpt-parameters]
	public static final String RSET = "RSET";
	public static final String QUIT = "QUIT";
	public static final String DATA = "DATA";
	public static final String VRFY = "VRFY"; // Parameters: string
	public static final String EXPN = "EXPN"; // Parameters: string
	public static final String HELP = "HELP"; // Parameters: [string]
	public static final String NOOP = "NOOP"; // Parameters: [string]

}
