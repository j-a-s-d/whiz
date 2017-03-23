/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net;

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

	public final boolean is(final HttpMethod method) {
		return this.name().equals(method.name());
	}

}
