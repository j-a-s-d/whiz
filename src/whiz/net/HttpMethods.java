/* Whiz by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package whiz.net;

/**
 * Utility class for working with HTTP methods.
 */
public class HttpMethods {

	public static final HttpMethod[] POST_ONLY = new HttpMethod[] { HttpMethod.POST };
	public static final HttpMethod[] GET_ONLY = new HttpMethod[] { HttpMethod.GET };
	public static final HttpMethod[] PUT_ONLY = new HttpMethod[] { HttpMethod.PUT };
	public static final HttpMethod[] DELETE_ONLY = new HttpMethod[] { HttpMethod.DELETE };

	public static final HttpMethod[] CRUD_OPERATIONS = new HttpMethod[] { HttpMethod.POST, HttpMethod.GET, HttpMethod.PUT, HttpMethod.DELETE };

}
