/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net;

/**
 * HTTP method enum.
 */
public enum HttpMethod {

	// NOTE: all are specified in RFC 2616
	DELETE,
	HEAD,
	GET,
	OPTIONS,
	//PATCH, // NOTE: besides it is specified in RFC 5789 it is not supported, https://bugs.openjdk.java.net/browse/JDK-7016595
	POST,
	PUT,
	TRACE;

	/**
	 * Determines if the invoked HTTP method instance is the same of the specified instance.
	 * 
	 * @param method
	 * @return <tt>true</tt> if they are the same, <tt>false</tt> otherwise
	 */
	public final boolean is(final HttpMethod method) {
		return this.name().equals(method.name());
	}

}
