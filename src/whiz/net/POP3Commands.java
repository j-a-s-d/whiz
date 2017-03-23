/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net;

// NOTE: built following RFC 1725
public class POP3Commands {

	// valid in the AUTHORIZATION & UPDATE states
	public static final String QUIT = "QUIT"; //

	// valid in the AUTHORIZATION state
	public static final String USER = "USER"; // Parameters: name
	public static final String PASS = "PASS"; // Parameters: string
	public static final String APOP = "APOP"; // [OPTIONAL] Parameters: name digest

	// valid in the TRANSACTION state
	public static final String TOP = "TOP"; // [OPTIONAL] Parameters: msg n
	public static final String UIDL = "UIDL"; // [OPTIONAL] Parameters: [msg]
	public static final String STAT = "STAT"; //
	public static final String LIST = "LIST"; // Parameters: [msg]
	public static final String RETR = "RETR"; // Parameters: msg
	public static final String DELE = "DELE"; // Parameters: msg
	public static final String NOOP = "NOOP"; //
	public static final String RSET = "RSET"; //

}
