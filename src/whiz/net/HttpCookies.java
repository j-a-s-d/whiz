/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net;

import ace.constants.STRINGS;
import java.net.HttpCookie;
import java.util.List;
import whiz.Whiz;

/**
 * Utility class for working with HTTP cookies.
 */
public class HttpCookies extends Whiz {

	public static final String COOKIE_OLD_REQUEST_HEADER = HttpRequestHeaders.COOKIE;
	public static final String COOKIE_NEW_REQUEST_HEADER = "Cookie2"; // NOTE: RFC 2965
	public static final String COOKIE_OLD_RESPONSE_HEADER = HttpResponseHeaders.SET_COOKIE;
	public static final String COOKIE_NEW_RESPONSE_HEADER = "Set-Cookie2"; // NOTE: RFC 2965

	public static final String COOKIE_ERASE_DATE = "Thu, 01 Jan 1970 00:00:00 GMT";
	public static final int COOKIE_MAX_AGE_DELETE = 0;
	public static final int COOKIE_MAX_AGE_SESSION = -1;

	public static final String COOKIE_FLAG_HTTP_ONLY = "HttpOnly"; // NOTE: not in RFC 2965
	public static final String COOKIE_FLAG_DISCARD = "Discard";
	public static final String COOKIE_FLAG_SECURE = "Secure";
	public static final String COOKIE_ATTRIBUTE_COMMENT = "Comment";
	public static final String COOKIE_ATTRIBUTE_COMMENT_URL = "CommentURL";
	public static final String COOKIE_ATTRIBUTE_DOMAIN = "Domain";
	public static final String COOKIE_ATTRIBUTE_MAX_AGE = "Max-Age";
	public static final String COOKIE_ATTRIBUTE_PATH = "Path";
	public static final String COOKIE_ATTRIBUTE_PORT = "Port";
	public static final String COOKIE_ATTRIBUTE_VERSION = "Version";
	public static final String COOKIE_ATTRIBUTE_EXPIRES = "Expires"; // NOTE: not in RFC 2965

	/**
	 * Determines if the specified header is a cookie header.
	 * 
	 * @param header
	 * @return <tt>true</tt> if the specified header is a cookie header, <tt>false</tt> otherwise
	 */
	public static final boolean isCookieHeader(final String header) {
		return isCookieRequestHeader(header) || isCookieResponseHeader(header);
	}

	/**
	 * Determines if the specified header is a cookie request header.
	 * 
	 * @param header
	 * @return <tt>true</tt> if the specified header is a cookie request header, <tt>false</tt> otherwise
	 */
	public static final boolean isCookieRequestHeader(final String header) {
		return COOKIE_NEW_REQUEST_HEADER.equalsIgnoreCase(header)
			|| COOKIE_OLD_REQUEST_HEADER.equalsIgnoreCase(header);
	}

	/**
	 * Determines if the specified header is a cookie response header.
	 * 
	 * @param header
	 * @return <tt>true</tt> if the specified header is a cookie response header, <tt>false</tt> otherwise
	 */
	public static final boolean isCookieResponseHeader(final String header) {
		return COOKIE_NEW_RESPONSE_HEADER.equalsIgnoreCase(header)
			|| COOKIE_OLD_RESPONSE_HEADER.equalsIgnoreCase(header);
	}

	/**
	 * Gets the specified list of HTTP cookies as a comma separated valid cookies string.
	 * 
	 * @param cookies
	 * @return the resulting string
	 */
	public static final String asString(final List<HttpCookie> cookies) {
		final StringBuilder sb = new StringBuilder();
		for (final HttpCookie cookie : cookies) {
			if (sb.length() > 0) {
				sb.append(", ");
			}
			sb.append(cookie.toString());
		}
		return sb.toString();
	}

	// NOTE: this Java 7 (ex. 1.7.0.17) bug autodetection is provided for convenience
	static final boolean _buggyCookieParsing = HttpCookie.parse("a=0,b=1").size() == 1;
	
	/**
	 * Parses the specified cookies string to a list of HTTP cookies objects instances.
	 * 
	 * From documentation:
	 *   Constructs cookies from set-cookie or set-cookie2 header string.
	 *   RFC 2965 section 3.2.2 set-cookie2 syntax indicates that
	 *   one header line may contain more than one cookie definitions,
	 *   so this is a static utility method instead of another constructor.
	 * 
	 * @param value
	 * @return the resulting list
	 */
	public static List<HttpCookie> parseCookies(final String value) {
		return HttpCookie.parse((_buggyCookieParsing ? COOKIE_NEW_RESPONSE_HEADER + STRINGS.COLON : STRINGS.EMPTY) + value);
	}

}
